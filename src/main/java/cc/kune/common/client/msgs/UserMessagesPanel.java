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
package cc.kune.common.client.msgs;

import cc.kune.common.client.msgs.UserMessagesPresenter.UserMessagesView;
import cc.kune.common.client.notify.NotifyLevel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserMessagesPanel extends Composite implements UserMessagesView {

  interface MessagesPanelUiBinder extends UiBinder<Widget, UserMessagesPanel> {
  }
  private static MessagesPanelUiBinder uiBinder = GWT.create(MessagesPanelUiBinder.class);

  @UiField
  FlowPanel bottom;

  @UiField
  VerticalPanel panel;

  public UserMessagesPanel() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public UserMessage add(final NotifyLevel level, final String title, final String message,
      final String id, final Boolean closeable, final CloseCallback closeCallback) {

    final UserMessageWidget msg = new UserMessageWidget(level, title, message, id, closeable,
        closeCallback);

    // msg.getText().

    panel.add(msg);
    if (panel.getWidgetCount() == 1) {
      bottom.setVisible(true);
    }
    msg.addAttachHandler(new Handler() {
      @Override
      public void onAttachOrDetach(final AttachEvent event) {
        if (!event.isAttached() && panel.getWidgetCount() == 1) {
          bottom.setVisible(false);
        }
      }
    });
    return msg;
  }

  public int getCurrentMsgCount() {
    return panel.getWidgetCount();
  }
}
