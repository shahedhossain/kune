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
package cc.kune.core.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class GoHomeEvent extends GwtEvent<GoHomeEvent.GoHomeHandler> {

  public interface HasGoHomeHandlers extends HasHandlers {
    HandlerRegistration addGoHomeHandler(GoHomeHandler handler);
  }

  public interface GoHomeHandler extends EventHandler {
    public void onGoHome(GoHomeEvent event);
  }

  private static final Type<GoHomeHandler> TYPE = new Type<GoHomeHandler>();

  public static void fire(HasHandlers source) {
    source.fireEvent(new GoHomeEvent());
  }

  public static Type<GoHomeHandler> getType() {
    return TYPE;
  }

  public GoHomeEvent() {
  }

  @Override
  public Type<GoHomeHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(GoHomeHandler handler) {
    handler.onGoHome(this);
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "GoHomeEvent[" + "]";
  }
}
