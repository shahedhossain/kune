/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.core.server;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.google.inject.matcher.AbstractMatcher;

public class NotInObject extends AbstractMatcher<Method> {

  private final List<String> excluded;

  public NotInObject() {
    super();
    // FIXME exclude password
    excluded = Arrays.asList(new String[] { "finalize", "toString", "hashCode", "getClass", "wait",
        "equals" });
  }

  @Override
  public boolean matches(final Method method) {
    final String name = method.getName();
    // http://code.google.com/p/google-guice/issues/detail?id=640
    final boolean isSynth = method.isSynthetic();
    final boolean isGetter = name.startsWith("set");
    final boolean isExcluded = excluded.contains(name);
    return !isSynth && (!isGetter || !isExcluded);
  }
}
