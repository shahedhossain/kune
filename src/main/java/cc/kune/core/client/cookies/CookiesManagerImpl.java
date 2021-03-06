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
package cc.kune.core.client.cookies;

import java.util.Date;

import cc.kune.common.client.log.Log;
import cc.kune.core.shared.SessionConstants;

import com.google.gwt.user.client.Cookies;

public class CookiesManagerImpl implements CookiesManager {

  private static final String ANON = "annon";

  public CookiesManagerImpl() {
  }

  @Override
  public String getAnonCookie() {
    return Cookies.getCookie(ANON);
  }

  @Override
  public String getAuthCookie() {
    return Cookies.getCookie(SessionConstants.USERHASH);
  }

  @Override
  public void removeAnonCookie() {
    Cookies.removeCookie(ANON);
    Cookies.setCookie(ANON, null, new Date(0), null, "/", false);
  }

  @Override
  public void removeAuthCookie() {
    // FIXME: Remove cookie doesn't works in all browsers, know
    // issue:
    // http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/ded86778ee56690/515dc513c7d085eb?lnk=st&q=remove+cookie#515dc513c7d085eb
    // http://code.google.com/p/google-web-toolkit/issues/detail?id=1735&q=removeCookie
    Cookies.removeCookie(SessionConstants.USERHASH);
    Cookies.removeCookie(SessionConstants.JSESSIONID);
    // Workaround:
    Cookies.setCookie(SessionConstants.USERHASH, null, new Date(0), null, "/", false);
    Cookies.setCookie(SessionConstants.JSESSIONID, null, new Date(0), null, "/", false);
  }

  @Override
  public void setAnonCookie(final Boolean userRegister) {
    final Date expires = new Date(System.currentTimeMillis()
        + (userRegister ? SessionConstants.ANON_SESSION_DURATION_AFTER_REG
            : SessionConstants.ANON_SESSION_DURATION));
    Cookies.setCookie(ANON, userRegister.toString(), expires, null, "/", false);
  }

  @Override
  public void setAuthCookie(final String userHash) {
    // http://code.google.com/p/google-web-toolkit-incubator/wiki/LoginSecurityFAQ
    final Date expires = new Date(System.currentTimeMillis() + SessionConstants.SESSION_DURATION);
    Cookies.setCookie(SessionConstants.USERHASH, userHash, expires, null, "/", false);
    Cookies.setCookie(SessionConstants.JSESSIONID, userHash, expires, null, "/", false);
    Log.info("Received hash: " + userHash, null);
  }
}
