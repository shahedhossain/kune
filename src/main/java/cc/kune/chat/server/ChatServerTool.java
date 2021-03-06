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
package cc.kune.chat.server;

import static cc.kune.chat.shared.ChatToolConstants.*;

import java.util.Arrays;
import java.util.Collections;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.server.AbstractServerTool;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.tool.ServerToolTarget;
import cc.kune.domain.Container;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;

public class ChatServerTool extends AbstractServerTool {

  @Inject
  public ChatServerTool(final ToolConfigurationManager configurationManager,
      final ContentManager contentManager, final ContainerManager containerManager,
      final ChatManagerDefault chatManager, final I18nTranslationService i18n,
      final CreationService creationService) {
    super(TOOL_NAME, ROOT_NAME, TYPE_ROOT, Collections.<String> emptyList(),
        Collections.<String> emptyList(), Arrays.asList(TYPE_ROOM), Arrays.asList(TYPE_ROOT),
        contentManager, containerManager, creationService, configurationManager, i18n,
        ServerToolTarget.forGroups);
  }

  @Override
  public Group initGroup(final User user, final Group group, final Object... otherVars) {
    @SuppressWarnings("unused")
    final Container rootFolder = createRoot(group);
    // final String groupShortName = group.getShortName();
    // chatManager.addRoom("FIXME", user, rootFolder.getStateToken(),
    // groupShortName,
    // i18n.t("Welcome to the [%s] public chat room", groupShortName));
    return group;
  }

}
