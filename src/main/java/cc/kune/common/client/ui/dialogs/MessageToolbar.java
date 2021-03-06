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

import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyLevelImages;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class MessageToolbar extends Composite {
  private final Image errorIcon;
  private final Label errorLabel;
  private final NotifyLevelImages images;
  private final FlowPanel toolbar;

  public MessageToolbar(final NotifyLevelImages images, final String errorLabelId) {
    this.images = images;
    toolbar = new FlowPanel();
    errorLabel = new Label("");
    errorLabel.setWordWrap(true);
    errorLabel.ensureDebugId(errorLabelId);
    errorIcon = new Image();
    errorIcon.setResource(images.getImage(NotifyLevel.error));

    toolbar.add(errorIcon);
    toolbar.setStyleName("k-error-tb");

    toolbar.add(errorLabel);
    errorIcon.setVisible(false);
    toolbar.setVisible(false);
    initWidget(toolbar);
  }

  public FlowPanel getToolbar() {
    return toolbar;
  }

  public void hideErrorMessage() {
    errorIcon.setVisible(false);
    errorLabel.setText("");
    toolbar.setVisible(false);
  }

  public void setErrorMessage(final String message, final NotifyLevel level) {
    errorLabel.setText(message);
    final ImageResource icon = images.getImage(level);
    errorIcon.setResource(icon);
    errorIcon.setVisible(true);
    toolbar.setVisible(true);
  }
}
