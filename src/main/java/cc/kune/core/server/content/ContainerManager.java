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
package cc.kune.core.server.content;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.manager.Manager;
import cc.kune.core.server.manager.SearchResult;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Container;
import cc.kune.domain.Group;
import cc.kune.domain.I18nLanguage;

public interface ContainerManager extends Manager<Container, Long> {

  Container createFolder(Group group, Container parent, String name, I18nLanguage language, String typeId);

  Container createRootFolder(Group group, String toolName, String name, final String rootType);

  boolean findIfExistsTitle(Container container, String title);

  Container getTrashFolder(Group group);

  boolean hasTrashFolder(Group group);

  Container moveContainer(Container container, Container newContainer);

  Container purgeAll(Container container);

  Container purgeContainer(Container container);

  Container renameFolder(Group group, Container container, String newName) throws DefaultException;

  SearchResult<Container> search(String search);

  SearchResult<Container> search(String search, Integer firstResult, Integer maxResults);

  void setAccessList(Container container, AccessLists accessList);
}
