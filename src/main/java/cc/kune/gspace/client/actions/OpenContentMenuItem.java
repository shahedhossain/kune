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

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AbstractContentSimpleDTO;

import com.google.inject.Inject;

public class OpenContentMenuItem extends MenuItemDescriptor {

  public static class OpenContentAction extends AbstractExtendedAction {

    private final StateManager stateManager;

    @Inject
    public OpenContentAction(final StateManager stateManager) {
      this.stateManager = stateManager;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      stateManager.gotoStateToken(((AbstractContentSimpleDTO) event.getTarget()).getStateToken());
    }

  }

  public OpenContentMenuItem(final I18nTranslationService i18n, final OpenContentAction action,
      final IconicResources res) {
    super(action);
    this.withText(i18n.t("Open")).withIcon(res.rightArrow());
  }

}
