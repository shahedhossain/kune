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
package cc.kune.gspace.client.options.style;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.options.UserOptions;
import cc.kune.gspace.client.style.GSpaceBackgroundManager;
import cc.kune.gspace.client.themes.GSpaceThemeSelectorPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserOptStylePresenter extends EntityOptStylePresenter implements UserOptStyle {

  // private final WsThemeSelector themeSelector;

  @Inject
  public UserOptStylePresenter(final EventBus eventBus, final Session session,
      final StateManager stateManager, final UserOptions entityOptions,
      final Provider<GroupServiceAsync> groupService, final GSpaceBackgroundManager backManager,
      final UserOptStyleView view, final GSpaceThemeSelectorPresenter styleSelector,
      final I18nTranslationService i18n, final ClientFileDownloadUtils fileDownUtils) {
    super(eventBus, session, stateManager, entityOptions, groupService, backManager, styleSelector,
        i18n, fileDownUtils);
    init(view);
  }
}
