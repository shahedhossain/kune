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
package cc.kune.domain.finders;

import java.util.ArrayList;
import java.util.List;

import cc.kune.domain.User;
import cc.kune.domain.UserSignInLog;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;

public interface UserSignInLogFinder {

  @Finder(query = "SELECT count(*) FROM UserSignInLog u")
  public Long count();

  @Finder(query = "select count (*) from UserSignInLog where user = :user")
  public Long countByUser(@Named("user") final User user);

  @Finder(query = "from UserSignInLog", returnAs = ArrayList.class)
  public List<UserSignInLog> getAll();
}
