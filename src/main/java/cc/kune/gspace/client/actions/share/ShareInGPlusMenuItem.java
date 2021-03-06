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

import cc.kune.common.client.utils.ClientFormattedString;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateTokenUtils;

import com.google.gwt.http.client.URL;
import com.google.inject.Inject;

public class ShareInGPlusMenuItem extends AbstractShareInSocialNetMenuItem {

  private static final String URL_TEMPLATE = "https://plus.google.com/share?url=%s";

  @Inject
  public ShareInGPlusMenuItem(final AbstractShareInSocialNetAction action, final IconicResources iconic,
      final Session session, final ContentViewerShareMenu menu, final I18nUITranslationService i18n) {
    super(
        action,
        session,
        menu,
        i18n.t("Share this in google+"),
        iconic.googlePlus(),
        ClientFormattedString.build(
            false,
            URL_TEMPLATE,
            URL.encodeQueryString(StateTokenUtils.getGroupSpaceUrl(session.getCurrentState().getStateToken()))));
  }
}
