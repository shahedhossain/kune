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
package cc.kune.core.client.rpcservices;

import cc.kune.core.client.errors.IncorrectHashException;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.InvitationDTO;
import cc.kune.core.shared.dto.StateContainerDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("InvitationService")
public interface InvitationService extends RemoteService {

  void confirmationInvitationToGroup(String userHash, String invitationHash)
      throws IncorrectHashException;

  void confirmationInvitationToSite(String userHash, String invitationHash)
      throws IncorrectHashException;

  StateContainerDTO confirmInvitationToList(String userHash, String invitationHash)
      throws IncorrectHashException;

  InvitationDTO getInvitation(String invitationHash) throws IncorrectHashException;

  void inviteToGroup(String userHash, StateToken token, String[] emails);

  void inviteToList(String userHash, StateToken token, String[] emails);

  void inviteToSite(String userHash, StateToken token, String[] emails);

}
