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
package cc.kune.gspace.client.actions;

import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;

public abstract class AbstractFoldableToolActions {

  protected final ActionRegistryByType actionsRegistry;
  protected final I18nUITranslationService i18n;
  protected final Session session;
  protected final StateManager stateManager;

  public AbstractFoldableToolActions(final Session session, final StateManager stateManager,
      final I18nUITranslationService i18n, final ActionRegistryByType actionsRegistry) {
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.actionsRegistry = actionsRegistry;
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        createPostSessionInitActions();
      }
    });
  }

  protected abstract void createPostSessionInitActions();

}
