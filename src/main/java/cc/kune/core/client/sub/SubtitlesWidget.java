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
package cc.kune.core.client.sub;

import org.adamtacy.client.ui.effects.events.EffectCompletedEvent;
import org.adamtacy.client.ui.effects.events.EffectCompletedHandler;
import org.adamtacy.client.ui.effects.examples.Show;
import org.adamtacy.client.ui.effects.examples.SlideRight;

import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.sub.SubtitlesManager.SubtitlesView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class SubtitlesWidget extends ViewImpl implements SubtitlesView {

  interface SubtitlesWidgetUiBinder extends UiBinder<Widget, SubtitlesWidget> {
  }

  public static final String SUBTITLE_MANAGER_ID = "k-subt-widget";

  private static SubtitlesWidgetUiBinder uiBinder = GWT.create(SubtitlesWidgetUiBinder.class);

  private SimpleCallback callback;
  @UiField
  InlineLabel description;
  private final PopupPanel popup;
  private boolean showing;
  @UiField
  InlineLabel title;

  private final Widget widget;

  public SubtitlesWidget() {
    popup = new PopupPanel(false, false);
    popup.ensureDebugId(SUBTITLE_MANAGER_ID);
    widget = uiBinder.createAndBindUi(this);
    popup.addDomHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        if (showing) {
          final SlideRight slideAtEnd = new SlideRight(widget.getElement());
          slideAtEnd.invert();
          slideAtEnd.setDuration(2);
          slideAtEnd.play();
          // final Fade fadeAtEnd = new Fade(popup.getElement());
          // fadeAtEnd.setDuration(2);
          // fadeAtEnd.play();
          slideAtEnd.addEffectCompletedHandler(new EffectCompletedHandler() {
            @Override
            public void onEffectCompleted(final EffectCompletedEvent event) {
              popup.hide();
              callback.onCallback();
            }
          });
          showing = false;
        }
      }
    }, ClickEvent.getType());
    Window.addResizeHandler(new ResizeHandler() {
      @Override
      public void onResize(final ResizeEvent event) {
        setSize(event.getWidth(), event.getHeight());
      }
    });
  }

  @Override
  public Widget asWidget() {
    return popup;
  }

  @Override
  public void setDescription(final String descr) {
    description.setText(descr);
  }

  private void setSize(final int width, final int height) {
    popup.setSize(width + "px", height + "px");
  }

  @Override
  public void setTitleText(final String text) {
    title.setText(text);
  }

  @Override
  public void show(final SimpleCallback callback) {
    this.callback = callback;
    popup.setWidget(widget);
    setSize(Window.getClientWidth(), Window.getClientHeight());
    popup.sinkEvents(Event.MOUSEEVENTS);
    popup.show();
    final Show showAtIni = new Show(widget.getElement());
    showAtIni.setDuration(2);
    showAtIni.play();
    final SlideRight slideAtIni = new SlideRight(widget.getElement());
    slideAtIni.setDuration(2);
    slideAtIni.play();
    this.showing = true;
  }

}
