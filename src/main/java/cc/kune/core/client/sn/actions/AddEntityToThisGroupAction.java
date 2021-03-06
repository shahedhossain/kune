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
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SocialNetServiceAsync;
import cc.kune.core.client.sitebar.search.OnEntitySelectedInSearch;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class AddEntityToThisGroupAction extends SNRolAction {

  public static final String ADD_NEW_MEMBER_TEXTBOX = "kune-add-newMember-tbox";
  private final AddMemberSearchPanel searchPanel;

  @Inject
  public AddEntityToThisGroupAction(final StateManager stateManager, final Session session,
      final I18nTranslationService i18n, final IconicResources res,
      final Provider<SocialNetServiceAsync> snServiceProvider, final AddMemberSearchPanel searchPanel,
      final AccessRightsClientManager rightsClientManager) {
    super(stateManager, session, i18n, res, snServiceProvider, rightsClientManager,
        AccessRolDTO.Administrator, true, false, true);
    this.searchPanel = searchPanel;
    putValue(NAME, i18n.t("Add new member"));
    putValue(Action.SMALL_ICON, res.add());
    putValue(Action.STYLES, "k-sn-join");
    searchPanel.init(// In the future, with this to false, we'll be add groups
                     // to groups!
        true, ADD_NEW_MEMBER_TEXTBOX, new OnEntitySelectedInSearch() {
          @Override
          public void onSeleted(final String shortName) {
            NotifyUser.askConfirmation(
                i18n.t("Are you sure?"),
                i18n.t("Do you want to add '[%s]' as a member of '[%s]'?", shortName,
                    session.getCurrentGroupShortName()), new SimpleResponseCallback() {
                  @Override
                  public void onCancel() {
                  }

                  @Override
                  public void onSuccess() {
                    NotifyUser.showProgress();
                    snServiceProvider.get().addCollabMember(session.getUserHash(),
                        session.getCurrentStateToken(), shortName,
                        new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                          @Override
                          public void onSuccess(final SocialNetworkDataDTO result) {
                            NotifyUser.hideProgress();
                            NotifyUser.info(i18n.t("Member added as collaborator"));
                            stateManager.setSocialNetwork(result);
                          }
                        });
                  }
                });
          }

        });

  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    searchPanel.show();
    searchPanel.focus();
  }

}
