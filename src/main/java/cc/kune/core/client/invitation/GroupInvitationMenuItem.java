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

package cc.kune.core.client.invitation;

import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.sn.actions.GroupSNOptionsMenu;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GroupInvitationMenuItem extends MenuItemDescriptor {

  @Inject
  GroupInvitationMenuItem(final GroupInvitationAction action, final IconicResources icons,
      final GroupSNOptionsMenu optionsMenu) {
    super(action);
    withText(I18n.t("Invite others to this group via email")).withIcon(icons.listsPostGrey());
    setParent(optionsMenu);
    setPosition(0);
  }
}
