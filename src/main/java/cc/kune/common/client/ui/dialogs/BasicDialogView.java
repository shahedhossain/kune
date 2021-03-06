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
package cc.kune.common.client.ui.dialogs;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasDirectionalText;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;

public interface BasicDialogView {

  ForIsWidget getBottomPanel();

  HasClickHandlers getCloseBtn();

  HasClickHandlers getFirstBtn();

  ForIsWidget getInnerPanel();

  HasClickHandlers getSecondBtn();

  HasDirectionalText getTitleText();

  void setCloseBtnTooltip(String tooltip);

  void setCloseBtnVisible(boolean visible);

  void setFirstBtnText(String text);

  void setFirstBtnTitle(String title);

  void setFirstBtnVisible(boolean visible);

  void setSecondBtnText(String text);

  void setSecondBtnTitle(String title);

  void setSecondBtnVisible(boolean visible);

}
