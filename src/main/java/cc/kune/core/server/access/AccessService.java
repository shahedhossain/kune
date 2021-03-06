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
package cc.kune.core.server.access;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.User;

public interface AccessService {

  Container accessToContainer(Container container, User user, AccessRol accessRol);

  Container accessToContainer(Long folderId, User user, AccessRol accessRol) throws DefaultException;

  Content accessToContent(Content content, User user, AccessRol accessRol);

  Content accessToContent(Long contentId, User user, AccessRol accessRol) throws DefaultException;

}
