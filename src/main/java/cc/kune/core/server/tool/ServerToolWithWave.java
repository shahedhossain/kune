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
package cc.kune.core.server.tool;

import cc.kune.domain.Container;

public interface ServerToolWithWave extends ServerTool {

  /**
   * Gets the new content additional participants. Use this if you want to add
   * some wave participant in each new content
   * 
   * @param containerParent
   *          the container parent of the new content
   * 
   * @return the new content additional parts
   */
  String[] getNewContentAdditionalParts(Container containerParent);

}
