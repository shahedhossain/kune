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
package cc.kune.common.client.ui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;

public class PopupTopPanel extends AbstractAtBorderPopupPanel {
  public PopupTopPanel() {
    this(false, false);
  }

  public PopupTopPanel(final boolean autohide) {
    this(autohide, false);
  }

  public PopupTopPanel(final boolean autohide, final boolean modal) {
    super(autohide, modal);
    defaultStyleImpl();
  }

  @Override
  public void defaultStyle() {
    defaultStyleImpl();
  }

  private void defaultStyleImpl() {
    setStyleName("k-popup-top-centered");
    super.defaultStyle();
    addStyleName("k-bottom-10corners");
  }

  @Override
  protected void setCenterPositionImpl() {
    setPopupPositionAndShow(new PopupPanel.PositionCallback() {
      @Override
      public void setPosition(final int offsetWidth, final int offsetHeight) {
        final int x = (Window.getClientWidth() - (getWidget() != null ? getWidget().getOffsetWidth() : 0)) / 2;
        final int y = 0;
        PopupTopPanel.this.setPopupPosition(x, y);
      }
    });
  }

}
