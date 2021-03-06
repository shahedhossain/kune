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
package cc.kune.events.shared;

import java.util.HashMap;
import java.util.Map;

import cc.kune.common.client.utils.DateUtils;
import cc.kune.common.shared.res.ICalConstants;

import com.bradrydzewski.gwt.calendar.client.Appointment;

/**
 * The Class EventsConversionUtil is used to convert Appointments to Gadgets
 * properties and viceversa
 */
public class EventsClientConversionUtil {

  public static Appointment toApp(final Map<String, String> properties) throws Exception {
    final Appointment app = new Appointment();
    app.setDescription(properties.get(ICalConstants.DESCRIPTION));
    app.setTitle(properties.get(ICalConstants.SUMMARY));
    app.setLocation(properties.get(ICalConstants.LOCATION));
    app.setCreatedBy(properties.get(ICalConstants.ORGANIZER));
    final String allDay = properties.get(ICalConstants._ALL_DAY);
    app.setId(properties.get(ICalConstants.UID));
    if (allDay != null) {
      app.setAllDay(Boolean.parseBoolean(allDay));
    }
    final String start = properties.get(ICalConstants.DATE_TIME_START);
    if (start != null) {
      app.setStart(DateUtils.toDate(start));
    }
    final String end = properties.get(ICalConstants.DATE_TIME_END);
    if (end != null) {
      app.setEnd(DateUtils.toDate(end));
    }
    return app;
  }

  public static Map<String, String> toMap(final Appointment app) {
    final Map<String, String> properties = new HashMap<String, String>();
    properties.put(ICalConstants.SUMMARY, app.getTitle());
    properties.put(ICalConstants.DESCRIPTION, app.getDescription());
    properties.put(ICalConstants.LOCATION, app.getLocation());
    properties.put(ICalConstants.ORGANIZER, app.getCreatedBy());
    properties.put(ICalConstants._ALL_DAY, Boolean.toString(app.isAllDay()));
    properties.put(ICalConstants.UID, app.getId());

    properties.put(ICalConstants.DATE_TIME_START, DateUtils.toString(app.getStart()));
    properties.put(ICalConstants.DATE_TIME_END, DateUtils.toString(app.getEnd()));
    return properties;
  }

}
