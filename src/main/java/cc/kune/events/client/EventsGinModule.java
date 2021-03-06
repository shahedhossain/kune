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
package cc.kune.events.client;

import cc.kune.core.client.ExtendedGinModule;
import cc.kune.events.client.actions.Calendar1DayViewSelectBtn;
import cc.kune.events.client.actions.Calendar3DaysViewSelectBtn;
import cc.kune.events.client.actions.Calendar7DaysViewSelectBtn;
import cc.kune.events.client.actions.CalendarGoNextBtn;
import cc.kune.events.client.actions.CalendarGoPrevBtn;
import cc.kune.events.client.actions.CalendarGoTodayBtn;
import cc.kune.events.client.actions.CalendarMonthViewSelectBtn;
import cc.kune.events.client.actions.CalendarOnOverMenu;
import cc.kune.events.client.actions.EventsClientActions;
import cc.kune.events.client.viewer.CalendarViewer;
import cc.kune.events.client.viewer.CalendarViewerPanel;
import cc.kune.events.client.viewer.CalendarViewerPresenter;

import com.google.inject.Singleton;

public class EventsGinModule extends ExtendedGinModule {

  @Override
  protected void configure() {
    s(EventsClientTool.class);
    s(EventsClientActions.class);
    s(Calendar1DayViewSelectBtn.class);
    s(Calendar3DaysViewSelectBtn.class);
    s(Calendar7DaysViewSelectBtn.class);
    s(CalendarMonthViewSelectBtn.class);
    s(CalendarGoPrevBtn.class);
    s(CalendarGoNextBtn.class);
    s(CalendarGoTodayBtn.class);
    s(CalendarOnOverMenu.class);
    bindPresenter(CalendarViewerPresenter.class, CalendarViewerPresenter.CalendarViewerView.class,
        CalendarViewerPanel.class, CalendarViewerPresenter.CalendarViewerProxy.class);
    bind(CalendarViewer.class).to(CalendarViewerPresenter.class).in(Singleton.class);
  }

}
