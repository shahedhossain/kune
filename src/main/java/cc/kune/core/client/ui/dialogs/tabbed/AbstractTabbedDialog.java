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
package cc.kune.core.client.ui.dialogs.tabbed;

import cc.kune.common.client.notify.NotifyLevel;

import com.google.gwt.user.client.ui.IsWidget;

public interface AbstractTabbedDialog {

  void activateTab(int index);

  void addTab(IsWidget tab, IsWidget tabTitle);

  void hide();

  public void hideMessages();

  void insertTab(IsWidget tab, IsWidget tabTitle, int position);

  public void setErrorMessage(String message, NotifyLevel level);

  void show();

}
