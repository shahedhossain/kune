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
package cc.kune.common.client.notify;

import cc.kune.common.client.resources.CommonResources;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;

public class NotifyLevelImages {

  private final CommonResources res;

  @Inject
  public NotifyLevelImages(final CommonResources res) {
    this.res = res;
  }

  public ImageResource getImage(final NotifyLevel level) {
    switch (level) {
    case info:
      return res.info();
    case important:
      return res.important();
    case veryImportant:
      return res.alert();
    case error:
    default:
      return res.error();
    }
  }
}
