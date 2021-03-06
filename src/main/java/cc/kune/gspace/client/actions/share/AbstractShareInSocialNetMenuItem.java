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

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.ui.KuneWindowUtils;
import cc.kune.common.client.utils.ClientFormattedString;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateTokenUtils;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.gwt.resources.client.ImageResource;

public class AbstractShareInSocialNetMenuItem extends MenuItemDescriptor {

  public static class AbstractShareInSocialNetAction extends AbstractExtendedAction {

    private ClientFormattedString url;

    public AbstractShareInSocialNetAction() {

    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      KuneWindowUtils.open(url.getString());
    }

    public void setUrl(final ClientFormattedString url) {
      this.url = url;
    }

  }

  protected static String getCurrentUrl(final Session session) {
    return StateTokenUtils.getGroupSpaceUrl(session.getCurrentState().getStateToken());
  }

  protected static String getTitle(final Session session) {
    final StateAbstractDTO state = session.getCurrentState();
    final String prefix = session.getCurrentGroupShortName() + ", ";
    if (!(state instanceof StateContentDTO)) {
      return prefix
          + (((StateContainerDTO) state).getContainer().isRoot() ? I18n.t(state.getTitle())
              : state.getTitle());
    } else {
      return prefix + session.getCurrentState().getTitle();
    }
  }

  protected final Session session;

  public AbstractShareInSocialNetMenuItem(final AbstractShareInSocialNetAction action,
      final Session session, final ContentViewerShareMenu menu, final String text,
      final ImageResource icon, final ClientFormattedString url) {
    super(action);
    this.session = session;
    withText(text).withIcon(icon).withParent(menu, false);
    action.setUrl(url);
    setEnabled(session.getCurrentState().getGroupRights().isVisible());
  }

}