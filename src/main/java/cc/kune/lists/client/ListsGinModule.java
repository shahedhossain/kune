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
package cc.kune.lists.client;

import cc.kune.core.client.ExtendedGinModule;
import cc.kune.lists.client.actions.ListsClientActions;
import cc.kune.lists.client.actions.ListsNewMenu;
import cc.kune.lists.client.actions.OptionsListMenu;
import cc.kune.lists.client.actions.PostNewMenu;

public class ListsGinModule extends ExtendedGinModule {

  @Override
  protected void configure() {
    s(OptionsListMenu.class);
    s(ListsNewMenu.class);
    s(PostNewMenu.class);
    s(ListsClientTool.class);
    s(ListsClientActions.class);
  }

}
