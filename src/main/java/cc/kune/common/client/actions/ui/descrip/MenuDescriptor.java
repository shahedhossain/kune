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
package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.BaseAction;

import com.google.gwt.resources.client.ImageResource;

public class MenuDescriptor extends AbstractParentGuiActionDescrip {

  public static final String MENU_CLEAR = "menuclear";
  public static final String MENU_HIDE = "hidemenu";
  public static final String MENU_ONHIDE = "menuonhide";
  public static final String MENU_ONSHOW = "menuonshow";
  public static final String MENU_RIGHTICON = "menurighticon";
  public static final String MENU_SELECT_ITEM = "menuselitem";
  public static final String MENU_SELECTION_DOWN = "menuseldown";
  public static final String MENU_SELECTION_UP = "menuselup";
  public static final String MENU_SHOW = "showmenu";
  public static final String MENU_SHOW_NEAR_TO = "showmenunearto";
  protected static final String MENU_STANDALONE = "menustandalone";

  public MenuDescriptor() {
    this(new BaseAction(null, null));
  }

  public MenuDescriptor(final AbstractAction action) {
    this(NO_PARENT, action);
  }

  public MenuDescriptor(final GuiActionDescrip parent, final AbstractAction action) {
    super(action);
    setParent(parent);
    putValue(MENU_HIDE, false);
    putValue(MENU_SHOW, false);
    putValue(MENU_CLEAR, false);
    putValue(MENU_STANDALONE, false);
    putValue(MENU_SELECTION_DOWN, false);
    putValue(MENU_SELECTION_UP, false);
  }

  public MenuDescriptor(final String text) {
    this(new BaseAction(text, null));
  }

  public MenuDescriptor(final String text, final ImageResource icon) {
    this(new BaseAction(text, null, icon));
  }

  public MenuDescriptor(final String text, final String tooltip) {
    this(new BaseAction(text, tooltip));
  }

  public MenuDescriptor(final String text, final String tooltip, final ImageResource icon) {
    this(new BaseAction(text, tooltip, icon));
  }

  public MenuDescriptor(final String text, final String tooltip, final String icon) {
    this(new BaseAction(text, tooltip, icon));
  }

  @Override
  public void clear() {
    toggle(MENU_CLEAR);
    super.clear();
  }

  public ImageResource getRightIcon() {
    return (ImageResource) super.getValue(MENU_RIGHTICON);
  }

  @Override
  public Class<?> getType() {
    return MenuDescriptor.class;
  }

  public void hide() {
    toggle(MENU_HIDE);
  }

  public boolean isStandalone() {
    return (Boolean) super.getValue(MENU_STANDALONE);
  }

  public void moveSelectionDown() {
    toggle(MENU_SELECTION_DOWN);
  }

  public void moveSelectionUp() {
    toggle(MENU_SELECTION_UP);
  }

  public void selectMenu(final MenuItemDescriptor item) {
    putValue(MENU_SELECT_ITEM, item);
  }

  public void setMenuPosition(final Position position) {
    putValue(MENU_SHOW_NEAR_TO, position);
  }

  public void setRightIcon(final ImageResource icon) {
    putValue(MENU_RIGHTICON, icon);
  }

  /**
   * Sets the standalone property (if the menu should have button (for a
   * toolbar) or is a menu independent.
   * 
   * @param standalone
   *          the new standalone
   */
  public void setStandalone(final boolean standalone) {
    putValue(MENU_STANDALONE, standalone);
  }

  public void setText(final String text) {
    putValue(Action.NAME, text);
  }

  /**
   * Show the menu (remember to set the menu position before)
   */
  public void show() {
    toggle(MENU_SHOW);
  }

  /**
   * Show the menu near the Element object specified
   * 
   * @param object
   *          the element to show menu near of it
   */
  public void show(final Object object) {
    putValue(MENU_SHOW_NEAR_TO, object);
    show();
  }

  /**
   * Show the menu near the position specified
   * 
   * @param position
   *          the position to show menu near of it
   */
  public void show(final Position position) {
    setMenuPosition(position);
    show();
  }

  /**
   * Show the menu near the Element id specified
   * 
   * @param id
   *          the element id to show menu near of it
   */
  public void show(final String id) {
    putValue(MENU_SHOW_NEAR_TO, id);
    show();
  }
}
