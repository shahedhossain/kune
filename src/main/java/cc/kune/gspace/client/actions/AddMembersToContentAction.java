/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AbstractContentSimpleDTO;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;

import com.google.inject.Inject;

public class AddMembersToContentAction extends RolAction {

  private final ContentServiceHelper contentService;
  private final Session session;
  private SocialNetworkSubGroup subGroup;

  @Inject
  public AddMembersToContentAction(final Session session, final ContentServiceHelper contentService) {
    super(AccessRolDTO.Editor, true);
    this.session = session;
    this.contentService = contentService;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    final StateToken token = session.getCurrentStateToken().hasAll() ? session.getCurrentStateToken()
        : ((AbstractContentSimpleDTO) event.getTarget()).getStateToken();
    contentService.addParticipants(token, subGroup);
  }

  public void setSubGroup(final SocialNetworkSubGroup subGroup) {
    this.subGroup = subGroup;
  }

}