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
package cc.kune.common.client.actions.ui;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Provider;

public class DefaultGuiProvider implements GuiProvider {

  private final Map<Class<?>, Provider<?>> map;

  public DefaultGuiProvider() {
    map = new HashMap<Class<?>, Provider<?>>();
  }

  @Override
  public <T> GuiBinding get(final Class<T> classType) {
    // If this return a NPE is because any GuiProvider has register bindings
    return (GuiBinding) map.get(classType).get();
  }

  @Override
  public <T, Z> void register(final Class<T> classType, final Provider<Z> binding) {
    map.put(classType, binding);
  }
}
