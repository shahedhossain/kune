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
package cc.kune.events.client.viewer;

import java.util.Date;

import cc.kune.gspace.client.tool.ContentViewer;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.CalendarViews;

public interface CalendarViewer extends ContentViewer {
  public static final Appointment NO_APPOINT = new Appointment();

  void addAppointment(Appointment appt);

  void decrement();

  /**
   * Gets the current Appointment that can be edit.
   * 
   * @return the appointment to edit
   */
  Appointment getAppToEdit();

  Date getDate();

  /**
   * @return if you click on the calendar, this get the date you clicked
   */
  Date getOnOverDate();

  void goToday();

  void increment();

  void removeAppointment(Appointment app);

  void setDate(Date date);

  void setView(CalendarViews view);

  void setView(CalendarViews view, int days);
}
