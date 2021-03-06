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
package cc.kune.gspace.client.tool;

import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.tool.selector.ToolSelector;
import cc.kune.gspace.client.tool.selector.ToolSelectorItemPanel;
import cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter;

import com.google.gwt.resources.client.ImageResource;

public abstract class AbstractClientTool {

  public AbstractClientTool(final String shortName, final String longName, final String tooltip,
      final ImageResource icon, final AccessRolDTO visibleForRol, final ToolSelector toolSelector,
      HistoryWrapper history) {
    final ToolSelectorItemPresenter presenter = new ToolSelectorItemPresenter(shortName, longName,
        tooltip, visibleForRol, toolSelector, history);
    final ToolSelectorItemPanel panel = new ToolSelectorItemPanel(shortName, icon);
    presenter.init(panel);
  }

  public abstract String getName();
}
