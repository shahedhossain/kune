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

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.viewers.TutorialViewer.OnTutorialClose;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class TutorialBtn extends ButtonDescriptor {

  @Singleton
  public static class ShowTutorialAction extends RolAction {

    private final EventBus bus;
    private final StateManager stateManager;

    @Inject
    public ShowTutorialAction(final EventBus eventBus, final StateManager stateManager) {
      super(AccessRolDTO.Editor, true);
      this.bus = eventBus;
      this.stateManager = stateManager;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      ShowHelpContainerEvent.fire(bus, new OnTutorialClose() {
        @Override
        public void onClose() {
          stateManager.refreshCurrentState();
        }
      });
    }

  }

  public static final String INFO_CONTAINER_ID = "k-container-info-id";

  @Inject
  public TutorialBtn(final I18nTranslationService i18n, final ShowTutorialAction action,
      final IconicResources res) {
    super(action);
    this.withToolTip(i18n.t("New to this tool? Here there is some help")).withIcon(res.info()).withStyles(
        "k-btn-min, k-fr");
    this.withId(INFO_CONTAINER_ID);
  }

}
