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
package cc.kune.core.server.utils;

import org.apache.commons.lang.StringUtils;

import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.errors.NameNotPermittedException;

public class FilenameUtils {

  /**
   * Check filename is not empty, or '.', or '..'
   * 
   * @param filename
   */
  public static void checkBasicFilename(final String filename) {
    if (TextUtils.empty(StringUtils.trimToEmpty(filename)) || filename.equals(".")
        || filename.equals("..")) {
      throw new NameNotPermittedException();
    }
  }

  /**
   * Chomp the filename using {@link StringUtils.chomp}
   * 
   * @param filename
   *          the filename
   * @return the filename chomped
   */
  public static String chomp(final String filename) {
    return StringUtils.chomp(filename);
  }
}
