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
package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.ui.AbstractComposedGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.shared.i18n.HasRTL;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class GwtSimpleGuiItem extends AbstractComposedGuiItem {

  private final HorizontalPanel bar;

  public GwtSimpleGuiItem(final GuiProvider provider, final HasRTL i18n) {
    super(provider, i18n);
    bar = new HorizontalPanel();
    initWidget(bar);
  }

  @Override
  protected void addWidget(final AbstractGuiItem item) {
    bar.add(item);
  }

  @Override
  public void clear() {
    super.clear();
    bar.clear();
  }

  @Override
  protected void insertWidget(final AbstractGuiItem item, final int position) {
    final int count = bar.getWidgetCount();
    bar.insert(item, count < position ? count : position);
  }

}
