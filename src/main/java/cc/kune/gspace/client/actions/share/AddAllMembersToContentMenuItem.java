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
package cc.kune.gspace.client.actions.share;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.sn.actions.conditions.IsCurrentStateAGroupCondition;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;
import cc.kune.gspace.client.actions.AddMembersToContentAction;

import com.google.inject.Inject;

public class AddAllMembersToContentMenuItem extends AddMembersToContentMenuItem {

  @Inject
  public AddAllMembersToContentMenuItem(final I18nTranslationService i18n,
      final IsCurrentStateAGroupCondition isAGroupCondition, final AddMembersToContentAction action,
      final ContentViewerShareMenu menu, final IconicResources res) {
    super(i18n.t("Share with all group members"), SocialNetworkSubGroup.ALL_GROUP_MEMBERS, action, menu,
        res);
    add(isAGroupCondition);
  }

}
