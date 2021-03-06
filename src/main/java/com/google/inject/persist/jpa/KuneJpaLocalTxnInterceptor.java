/**
 * Copyright (C) 2010 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.inject.persist.jpa;

import java.lang.reflect.Method;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import cc.kune.core.server.persist.KuneTransactional;

import com.google.inject.Inject;
import com.google.inject.persist.UnitOfWork;

/**
 * @author Dhanji R. Prasanna (dhanji@gmail.com)
 */
public class KuneJpaLocalTxnInterceptor implements MethodInterceptor {

  @KuneTransactional
  private static class Internal {
  }

  // Tracks if the unit of work was begun implicitly by this transaction.
  private final ThreadLocal<Boolean> didWeStartWork = new ThreadLocal<Boolean>();

  @Inject
  private final JpaPersistService emProvider = null;

  @Inject
  private final UnitOfWork unitOfWork = null;

  @Override
  public Object invoke(final MethodInvocation methodInvocation) throws Throwable {

    // Should we start a unit of work?
    if (!emProvider.isWorking()) {
      emProvider.begin();
      didWeStartWork.set(true);
    }

    final KuneTransactional transactional = readTransactionMetadata(methodInvocation);
    final EntityManager em = this.emProvider.get();

    // Allow 'joining' of transactions if there is an enclosing
    // @KuneTransactional method.
    if (em.getTransaction().isActive()) {
      return methodInvocation.proceed();
    }

    final EntityTransaction txn = em.getTransaction();
    txn.begin();

    Object result;
    try {
      result = methodInvocation.proceed();

    } catch (final Exception e) {
      // commit transaction only if rollback didnt occur
      if (rollbackIfNecessary(transactional, e, txn)) {
        txn.commit();
      }

      // propagate whatever exception is thrown anyway
      throw e;
    } finally {
      // Close the em if necessary (guarded so this code doesn't run unless
      // catch fired).
      if (null != didWeStartWork.get() && !txn.isActive()) {
        didWeStartWork.remove();
        unitOfWork.end();
      }
    }

    // everything was normal so commit the txn (do not move into try block above
    // as it
    // interferes with the advised method's throwing semantics)
    try {
      txn.commit();
    } finally {
      // close the em if necessary
      if (null != didWeStartWork.get()) {
        didWeStartWork.remove();
        unitOfWork.end();
      }
    }

    // or return result
    return result;
  }

  // TODO(dhanji): Cache this method's results.
  private KuneTransactional readTransactionMetadata(final MethodInvocation methodInvocation) {
    KuneTransactional transactional;
    final Method method = methodInvocation.getMethod();
    final Class<?> targetClass = methodInvocation.getThis().getClass();

    transactional = method.getAnnotation(KuneTransactional.class);
    if (null == transactional) {
      // If none on method, try the class.
      transactional = targetClass.getAnnotation(KuneTransactional.class);
    }
    if (null == transactional) {
      // If there is no transactional annotation present, use the default
      transactional = Internal.class.getAnnotation(KuneTransactional.class);
    }

    return transactional;
  }

  /**
   * Returns True if rollback DID NOT HAPPEN (i.e. if commit should continue).
   * 
   * @param transactional
   *          The metadata annotaiton of the method
   * @param e
   *          The exception to test for rollback
   * @param txn
   *          A JPA Transaction to issue rollbacks on
   */
  private boolean rollbackIfNecessary(final KuneTransactional transactional, final Exception e,
      final EntityTransaction txn) {
    boolean commit = true;

    // check rollback clauses
    for (final Class<? extends Exception> rollBackOn : transactional.rollbackOn()) {

      // if one matched, try to perform a rollback
      if (rollBackOn.isInstance(e)) {
        commit = false;

        // check ignore clauses (supercedes rollback clause)
        for (final Class<? extends Exception> exceptOn : transactional.ignore()) {
          // An exception to the rollback clause was found, DON'T rollback
          // (i.e. commit and throw anyway)
          if (exceptOn.isInstance(e)) {
            commit = true;
            break;
          }
        }

        // rollback only if nothing matched the ignore check
        if (!commit) {
          txn.rollback();
        }
        // otherwise continue to commit

        break;
      }
    }

    return commit;
  }
}
