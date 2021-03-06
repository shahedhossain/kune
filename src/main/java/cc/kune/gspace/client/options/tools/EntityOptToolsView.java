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
package cc.kune.gspace.client.options.tools;

import cc.kune.core.shared.dto.ToolSimpleDTO;
import cc.kune.gspace.client.options.EntityOptionsTabView;

import com.google.gwt.event.dom.client.ClickHandler;

public interface EntityOptToolsView extends EntityOptionsTabView {

  void add(ToolSimpleDTO tool, boolean enabled, boolean checked, ClickHandler clickHandler);

  void clear();

  boolean isChecked(String tool);

  void mask();

  void setChecked(String tool, boolean checked);

  void setEnabled(String tool, boolean enabled);

  void setTooltip(String tool, String tip);

  void unmask();
}
