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
package cc.kune.barters.client;

import cc.kune.barters.client.actions.BartersClientActions;
import cc.kune.barters.shared.BartersToolConstants;
import cc.kune.core.shared.SessionConstants;
import cc.kune.gspace.client.tool.ContentViewerSelector;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BartersParts {

  @Inject
  public BartersParts(final SessionConstants session, final Provider<BartersClientTool> clientTool,
      final ContentViewerSelector viewerSelector, final BartersClientActions bartersActions,
      final ContentViewerPresenter contentViewer, final FolderViewerPresenter folderViewer) {
    clientTool.get();
    viewerSelector.register(contentViewer, true, BartersToolConstants.TYPE_BARTER);
    viewerSelector.register(folderViewer, true, BartersToolConstants.TYPE_ROOT,
        BartersToolConstants.TYPE_FOLDER);
  }
}
