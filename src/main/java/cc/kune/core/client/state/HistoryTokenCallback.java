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
package cc.kune.core.client.state;

/**
 * The Interface HistoryTokenCallback is used make relations between browser
 * hashs like #inbox #signin etc, with its actions
 */
public interface HistoryTokenCallback {

  /**
   * Auth The user should be logged (mandatory).
   * 
   * @return true, if yes
   */
  boolean authMandatory();

  /**
   * Info message used to show some message to the user (like
   * "sign-in to create a new group");
   * 
   * @return the string
   */
  String getInfoMessage();

  /**
   * On history token do some action (Example #inbox, #newgroup)
   * 
   * @param token
   *          the token
   */
  void onHistoryToken(String token);

}
