/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.site.rpc;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.UserBuddiesVisibilityDTO;
import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {

    void createUser(UserDTO user, boolean wantPersonalHomepage, AsyncCallback<UserInfoDTO> asyncCallback);

    void getUserAvatarBaser64(String userHash, StateToken userToken, AsyncCallback<?> asyncCallback);

    void login(String nickOrEmail, String passwd, AsyncCallback<UserInfoDTO> asyncCallback);

    void logout(String userHash, AsyncCallback<?> asyncCallback);

    void onlyCheckSession(String userHash, AsyncCallback<?> asyncCallback);

    void reloadUserInfo(String userHash, AsyncCallback<UserInfoDTO> asyncCallback);

    void setBuddiesVisibility(String userHash, StateToken groupToken, UserBuddiesVisibilityDTO visibility,
            AsyncCallback<?> asyncCallback);

}
