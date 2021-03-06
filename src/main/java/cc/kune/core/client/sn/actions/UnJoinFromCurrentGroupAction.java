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
package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.SocialNetServiceAsync;
import cc.kune.core.client.rpcservices.SocialNetServiceHelper;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class UnJoinFromCurrentGroupAction extends SNRolAction {
  private final Provider<SocialNetServiceHelper> snServiceHelper;

  @Inject
  public UnJoinFromCurrentGroupAction(final StateManager stateManager, final Session session,
      final I18nTranslationService i18n, final IconicResources res,
      final Provider<SocialNetServiceHelper> snServiceHelper,
      final AccessRightsClientManager rightsClientManager,
      final Provider<SocialNetServiceAsync> snService) {
    super(stateManager, session, i18n, res, snService, rightsClientManager, AccessRolDTO.Editor, true,
        false, true);
    this.snServiceHelper = snServiceHelper;
    putValue(NAME, i18n.t("Leave this group"));
    // putValue(TOOLTIP, i18n.t("I want to leave this group"));
    putValue(Action.SMALL_ICON, res.del());
    putValue(Action.STYLES, "k-sn-join");
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    snServiceHelper.get().unJoinGroup(session.getCurrentState().getStateToken());
  }

}
