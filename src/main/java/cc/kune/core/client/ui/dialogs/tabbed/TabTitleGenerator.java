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

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.shared.utils.TextUtils;

import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.resources.client.ImageResource;

public class TabTitleGenerator {

  private static String format(final String title, final int maxLength) {
    return TextUtils.ellipsis(title, maxLength);
  }

  public static IconLabel generate(final ImageResource res, final String title, final int maxLength,
      final String id) {
    final IconLabel tabTitle = new IconLabel(res, format(title, maxLength));
    setTooltip(title, maxLength, tabTitle);
    tabTitle.ensureDebugId(id);
    return tabTitle;
  }

  public static IconLabel generate(final ImageResource res, final String title, final String id) {
    final IconLabel tabTitle = new IconLabel(res, title);
    tabTitle.ensureDebugId(id);
    return tabTitle;
  }

  public static void setText(final IconLabel tabTitle, final String title, final int maxLength,
      final Direction direction) {
    tabTitle.setText(format(title, maxLength), direction);
    setTooltip(title, maxLength, tabTitle);
  }

  private static void setTooltip(final String title, final int maxLength, final IconLabel tabTitle) {
    if (title.length() > maxLength) {
      Tooltip.to(tabTitle, title);
    }
  }

}
