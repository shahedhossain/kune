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
package cc.kune.lists.client;

import static cc.kune.lists.shared.ListsToolConstants.*;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

public class ListsClientTool extends FoldableAbstractClientTool {

  private final IconicResources icons;

  @Inject
  public ListsClientTool(final I18nTranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final IconicResources icons,
      final HistoryWrapper history) {
    super(
        TOOL_NAME,
        i18n.t(ROOT_NAME),
        i18n.t("Lists behave similarly to a mailing list or a forum (but minimizing emails). You can subscribe and discuss about specific topics"),
        icons.listsWhite(), AccessRolDTO.Viewer, toolSelector, cntCapRegistry, i18n, history);
    this.icons = icons;

    // registerAclEditableTypes();
    registerAuthorableTypes(TYPE_POST);
    registerDragableTypes();
    registerDropableTypes(TYPE_POST);
    registerPublishModerableTypes();
    registerRateableTypes(TYPE_POST);
    registerRenamableTypes(TYPE_LIST, TYPE_POST);
    registerTageableTypes(TYPE_LIST, TYPE_POST);
    // registerTranslatableTypes();
    registerIcons();
  }

  @Override
  public String getName() {
    return TOOL_NAME;
  }

  private void registerIcons() {
    registerEmptyFolderTutorial(TYPE_ROOT);
    // registerTutorial(TYPE_LIST);
    registerContentTypeIcon(TYPE_ROOT, icons.listsGrey());
    registerContentTypeIcon(TYPE_LIST, icons.listsGrey());
    registerContentTypeIcon(TYPE_POST, icons.listsPostGrey());
    registerContentTypeIconLight(TYPE_ROOT, icons.listsWhite());
    registerContentTypeIconLight(TYPE_LIST, icons.listsWhite());
    registerContentTypeIconLight(TYPE_POST, icons.listsPostWhite());
    final String noWave = i18n.t("There is nothing posted yet. Post something");
    final String noList = i18n.t("There isn't any list, you can create one");
    final String noWaveNotLogged = i18n.t("There is nothing posted yet");
    final String noListNotLogged = i18n.t("There isn't any list");
    registerEmptyMessages(TYPE_ROOT, noList);
    registerEmptyMessages(TYPE_LIST, noWave);
    registerEmptyMessagesNotLogged(TYPE_ROOT, noListNotLogged);
    registerEmptyMessagesNotLogged(TYPE_LIST, noWaveNotLogged);
    // registerShowDeleted();
  }

}
