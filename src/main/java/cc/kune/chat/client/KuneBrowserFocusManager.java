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
package cc.kune.chat.client;

import cc.kune.core.client.events.WindowFocusEvent;

import com.calclab.hablar.chat.client.ui.ChatPresenter;
import com.calclab.hablar.chat.client.ui.PairChatPresenter;
import com.calclab.hablar.core.client.browser.BrowserFocusHandler;
import com.calclab.hablar.core.client.browser.BrowserFocusHandler.BrowserFocusListener;
import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.calclab.hablar.core.client.page.Page;
import com.calclab.hablar.core.client.page.PagePresenter.Visibility;
import com.calclab.hablar.core.client.page.events.VisibilityChangedEvent;
import com.calclab.hablar.core.client.page.events.VisibilityChangedHandler;
import com.calclab.hablar.rooms.client.room.RoomPresenter;
import com.calclab.hablar.signals.client.unattended.UnattendedPagesManager;
import com.google.gwt.event.shared.EventBus;

/**
 * This class is a workaround to clear the focus on the active chat page FIXME:
 * workaround to clear the focus <br/>
 * 
 * <br/>
 * TODO: change the page/header visibility system... quite a big job
 * 
 * 
 */
public class KuneBrowserFocusManager {

  protected ChatPresenter currentFocused;

  public KuneBrowserFocusManager(final EventBus kuneEventBus, final HablarEventBus eventBus,
      final UnattendedPagesManager unattendedManager, final BrowserFocusHandler handler) {

    eventBus.addHandler(VisibilityChangedEvent.TYPE, new VisibilityChangedHandler() {
      @Override
      public void onVisibilityChanged(final VisibilityChangedEvent event) {
        if (event.getVisibility() == Visibility.focused) {
          final Page<?> page = event.getPage();
          if (PairChatPresenter.TYPE.equals(page.getType()) || RoomPresenter.TYPE.equals(page.getType())) {
            currentFocused = (ChatPresenter) page;
          }
        }
      }
    });

    handler.setFocusListener(new BrowserFocusListener() {
      @Override
      public void onBrowserFocusChanged(final boolean hasFocus) {
        kuneEventBus.fireEvent(new WindowFocusEvent(hasFocus));
        if (currentFocused != null) {
          if (hasFocus == false) {
            currentFocused.getDisplay().setTextBoxFocus(false);
          }
        }
      }
    });
  }
}
