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
package cc.kune.tasks.client.actions;

import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.gspace.client.actions.NewContentAction;
import cc.kune.gspace.client.actions.NewContentMenuItem;
import cc.kune.tasks.shared.TasksToolConstants;

import com.google.inject.Inject;

public class NewTaskMenuItem extends NewContentMenuItem {

  @Inject
  public NewTaskMenuItem(final I18nTranslationService i18n, final NewContentAction action,
      final IconicResources res, final GlobalShortcutRegister shorcutReg,
      final TasksFolderNewMenu tasksNewMenu) {
    super(i18n, action, res.tasksAdd(), shorcutReg, i18n.t("New task"), i18n.t("Create a new task"),
        i18n.t("New task"), TasksToolConstants.TYPE_TASK, tasksNewMenu.get());
  }

}
