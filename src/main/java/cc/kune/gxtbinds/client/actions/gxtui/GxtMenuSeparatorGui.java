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
package cc.kune.gxtbinds.client.actions.gxtui;

import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;

import com.google.gwt.user.client.ui.UIObject;

public class GxtMenuSeparatorGui extends AbstractChildGuiItem implements ParentWidget {

  @Override
  public void add(final UIObject uiObject) {
  }

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.create(descriptor);
    final GxtMenuGui menu = (GxtMenuGui) parent;
    menu.addSeparator();
    return menu;
  }

  @Override
  public void insert(final int position, final UIObject widget) {
  }

  @Override
  protected void setEnabled(final boolean enabled) {
  }

  @Override
  public void setIconBackground(final String back) {
  }

  @Override
  protected void setIconStyle(final String style) {
  }

  @Override
  public void setIconUrl(final String url) {
  }

  @Override
  protected void setText(final String text) {
  }

  @Override
  public boolean shouldBeAdded() {
    return false;
  }
}
