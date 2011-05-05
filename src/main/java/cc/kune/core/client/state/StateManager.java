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
package cc.kune.core.client.state;

import cc.kune.common.client.actions.BeforeActionListener;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener2;

public interface StateManager {

    void addBeforeStateChangeListener(BeforeActionListener listener);

    void addSiteToken(String token, HistoryTokenCallback historyTokenCallback);

    void gotoHistoryToken(String newToken);

    void gotoHistoryTokenButRedirectToCurrent(String signin);

    void gotoStateToken(StateToken newToken);

    void gotoStateToken(StateToken token, boolean useCache);

    void onGroupChanged(boolean fireNow, GroupChangedEvent.GroupChangedHandler handler);

    @Deprecated
    void onGroupChanged(Listener2<String, String> listener);

    void onSocialNetworkChanged(boolean fireNow, SocialNetworkChangedEvent.SocialNetworkChangedHandler handler);

    @Deprecated
    void onSocialNetworkChanged(Listener<StateAbstractDTO> listener);

    /**
     * @param fireNow
     *            if true, fire handler with current state
     * @param handler
     */
    void onStateChanged(boolean fireNow, StateChangedEvent.StateChangedHandler handler);

    @Deprecated
    void onStateChanged(Listener<StateAbstractDTO> listener);

    void onToolChanged(boolean fireNow, ToolChangedEvent.ToolChangedHandler handler);

    @Deprecated
    void onToolChanged(Listener2<String, String> listener);

    void redirectOrRestorePreviousToken();

    void refreshCurrentGroupState();

    void reload();

    void removeBeforeStateChangeListener(BeforeActionListener listener);

    void removeSiteToken(String token);

    void restorePreviousToken();

    void resumeTokenChange();

    void setRetrievedState(StateAbstractDTO state);

    void setRetrievedStateAndGo(StateAbstractDTO state);

    void setSocialNetwork(SocialNetworkDataDTO socialNet);

}