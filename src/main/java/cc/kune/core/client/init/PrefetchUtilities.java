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
package cc.kune.core.client.init;

import cc.kune.core.shared.FileConstants;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.user.client.ui.Image;

public class PrefetchUtilities {
  public PrefetchUtilities() {
  }

  public void doTasksDeferred() {

    Scheduler.get().scheduleIncremental(new RepeatingCommand() {
      String[] ext = { "default/shadow-c.png", "default/shadow-lr.png", "default/shadow.png",
          "gray/window/left-corners.png", "gray/window/left-right.png", "gray/window/right-corners.png",
          "gray/window/top-bottom.png" };
      int i = 0;

      int j = 0;

      String[] lic = { "gnu-fdl.gif", "bynd80x15.png", "byncsa80x15.png", "byncnd80x15.png",
          "bync80x15.png", "by80x15.png", "fal-license.gif" };

      @Override
      public boolean execute() {

        while (i < lic.length) {
          final String licImg = lic[i];
          Image.prefetch(FileConstants.ASITE_PREFIX + "images/lic/" + licImg);
          i++;
        }

        while (j < ext.length) {
          final String extImg = ext[j];
          Image.prefetch(FileConstants.ASITE_PREFIX + "gxt/images/" + extImg);
          j++;
        }

        final boolean notFinished = i + j < lic.length + ext.length;

        final boolean finished = !notFinished;

        if (finished) {
          // Nothing currently
        }

        return notFinished;
      }
    });
  }

  public void preFetchImpImages() {
    final String[] imgs = { "images/corner.png", "images/hborder.png" };

    for (final String img : imgs) {
      Image.prefetch(FileConstants.ASITE_PREFIX + img);
    }
  }
}
