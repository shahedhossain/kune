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
 \*/
package cc.kune.gspace.client.options.logo;

import cc.kune.common.client.utils.OnAcceptCallback;
import cc.kune.gspace.client.options.EntityOptionsTabView;
import cc.kune.gspace.client.options.EntityOptionsUploaderView;

public interface EntityOptLogoView extends EntityOptionsTabView, EntityOptionsUploaderView {

  OnAcceptCallback getOnSubmit();

  void reset();

  void setNormalGroupsLabels();

  void setPersonalGroupsLabels();

  void setUploadParams(String userHash, String token);

}
