/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.chat.client;

import org.ourproject.kune.chat.client.ui.ChatFactory;
import org.ourproject.kune.chat.client.ui.cnt.ChatContent;
import org.ourproject.kune.chat.client.ui.ctx.ChatContext;
import org.ourproject.kune.platf.client.tool.AbstractClientTool;

class ChatToolComponents {
    private ChatContent content;
    private ChatContext context;

    public ChatToolComponents(final AbstractClientTool chatClientTool) {
    }

    public ChatContent getContent() {
	if (content == null) {
	    content = ChatFactory.createChatContent();
	}
	return content;
    }

    public ChatContext getContext() {
	if (context == null) {
	    context = ChatFactory.createChatContext();
	}
	return context;
    }

}