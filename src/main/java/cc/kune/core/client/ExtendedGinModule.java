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
package cc.kune.core.client;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public abstract class ExtendedGinModule extends AbstractPresenterModule {

  protected void eagle(final Class<?> type) {
    bind(type).asEagerSingleton();
  }

  protected void s(final Class<?> type) {
    bind(type).in(Singleton.class);
  }

  protected <V, W> void s(final Class<V> type, final Class<? extends V> typeImpl) {
    bind(type).to(typeImpl).in(Singleton.class);
  }

}
