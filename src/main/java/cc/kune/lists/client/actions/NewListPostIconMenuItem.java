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
package cc.kune.lists.client.actions;

import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;

import com.google.inject.Inject;

public class NewListPostIconMenuItem extends MenuItemDescriptor {

  @Inject
  public NewListPostIconMenuItem(final I18nTranslationService i18n, final NewListPostAction action,
      final IconicResources res, final GlobalShortcutRegister shorcutReg, final ListsNewMenu menu) {
    super(menu.get(), false, action);
    this.withText(i18n.t("New post")).withToolTip(i18n.t("Create a new post")).withIcon(
        res.listsPostAdd()).withStyles("k-def-docbtn, k-fr");
  }
}
