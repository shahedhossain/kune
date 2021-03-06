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
package cc.kune.events.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.common.shared.utils.Url;
import cc.kune.common.shared.utils.UrlParam;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.actions.ContentViewerOptionsMenu;

import com.google.inject.Inject;

public class ExportCalendarMenuItem extends MenuItemDescriptor {

  public static class ExportCalendarAction extends RolAction {
    private final I18nTranslationService i18n;
    private final Session session;

    @Inject
    public ExportCalendarAction(final Session session, final I18nTranslationService i18n) {
      super(AccessRolDTO.Editor, false);
      this.session = session;
      this.i18n = i18n;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      final StateToken token = session.getCurrentStateToken().copy().clearDocument();
      final String url = new Url(session.getSiteUrl() + FileConstants.EVENTSSERVLET, new UrlParam(
          FileConstants.TOKEN, token.toString())).toString();
      NotifyUser.info(
          i18n.t("Calendar exporting"),
          i18n.t(
              "Open or use this address in your prefered calendar program for instance in your mobile: [%s]",
              TextUtils.generateHtmlLink(url, url, true)), ID, true);
    }

  }

  public static final String ID = "k-export-cal-menuid";

  @Inject
  public ExportCalendarMenuItem(final I18nTranslationService i18n, final ExportCalendarAction action,
      final ContentViewerOptionsMenu optionsMenu, final IconicResources res) {
    super(action);
    this.withText(i18n.t("Export this calendar")).withIcon(res.mobile()).withParent(optionsMenu, false);
  }

}
