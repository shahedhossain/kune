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

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;

public class ChatOptions {
  public String domain;
  public String httpBase;
  public String passwd;
  public String resource;
  public String roomHost;
  public String username;
  public XmppURI useruri;

  public XmppURI uriFrom(final String shortName) {
    return XmppURI.jid(shortName + "@" + domain);
  }
}
