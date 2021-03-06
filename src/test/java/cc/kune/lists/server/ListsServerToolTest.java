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
package cc.kune.lists.server;

import static cc.kune.lists.shared.ListsToolConstants.*;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.client.errors.ContainerNotPermittedException;
import cc.kune.core.client.errors.ContentNotPermittedException;

public class ListsServerToolTest { // extends PersistenceTest {

  private ListsServerTool serverTool;

  @Before
  public void before() {
    serverTool = new ListsServerTool(null, null, null, null, null, null);
  }

  @Test
  public void testCreateContainerInCorrectContainer() {
    serverTool.checkTypesBeforeContainerCreation(TYPE_ROOT, TYPE_LIST);
  }

  @Test(expected = ContainerNotPermittedException.class)
  public void testCreateContainerInIncorrectContainer7() {
    serverTool.checkTypesBeforeContainerCreation(TYPE_LIST, TYPE_LIST);
  }

  @Test
  public void testCreateContentInCorrectContainer() {
    serverTool.checkTypesBeforeContentCreation(TYPE_LIST, TYPE_POST);
  }

  @Test(expected = ContentNotPermittedException.class)
  public void testCreateContentInIncorrectContainer1() {
    serverTool.checkTypesBeforeContentCreation(TYPE_ROOT, TYPE_POST);
  }

}
