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

import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.sitebar.SitebarActions;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SiteInvitationBtn extends ButtonDescriptor {

  @Inject
  SiteInvitationBtn(final Session session, final SiteInvitationAction action,
      final I18nUITranslationService i18n, final IconicResources icons) {
    super(action);
    withText(i18n.t("Invite")).withToolTip(
        i18n.t("Invite others to [%s] via email", i18n.getSiteCommonName())).withStyles(
        ActionStyles.SITEBAR_STYLE_FL).withParent(SitebarActions.RIGHT_TOOLBAR);
    // .withIcon(icons.listsPostGrey())
    // .withParent(SitebarActions.MORE_MENU);

    final ToolbarSeparatorDescriptor separator = new ToolbarSeparatorDescriptor(Type.separator,
        SitebarActions.RIGHT_TOOLBAR);
    session.onUserSignInOrSignOut(true, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        final boolean logged = event.isLogged();
        separator.setVisible(logged);
        SiteInvitationBtn.this.setVisible(logged);
      }
    });
  }
}
