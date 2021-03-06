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
package cc.kune.gspace.client.viewers;

import javax.annotation.Nonnull;

import cc.kune.common.client.actions.BeforeActionListener;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.ui.EditEvent;
import cc.kune.common.client.ui.EditEvent.EditHandler;
import cc.kune.common.client.ui.HasEditHandler;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.events.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.gspace.client.actions.ActionGroups;
import cc.kune.gspace.client.actions.RenameAction;
import cc.kune.gspace.client.actions.RenameListener;
import cc.kune.gspace.client.tool.ContentViewer;
import cc.kune.wave.client.kspecific.WaveClientManager;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class ContentViewerPresenter extends
    Presenter<ContentViewerPresenter.ContentViewerView, ContentViewerPresenter.ContentViewerProxy>
    implements ContentViewer {

  @ProxyCodeSplit
  public interface ContentViewerProxy extends Proxy<ContentViewerPresenter> {
  }

  public interface ContentViewerView extends View {

    void attach();

    void blinkTitle();

    void clear();

    void detach();

    HasEditHandler getEditTitle();

    void setContent(StateContentDTO state);

    void setEditableContent(StateContentDTO state);

    void setEditableTitle(String title);

    void setFooterActions(GuiActionDescCollection actions);

    void setSubheaderActions(GuiActionDescCollection actions);

    void signIn();

    void signOut();

  }

  private final ActionRegistryByType actionsRegistry;
  private HandlerRegistration editHandler;
  private final PathToolbarUtils pathToolbarUtils;
  private final Provider<RenameAction> renameAction;
  private final Session session;

  @Inject
  public ContentViewerPresenter(final EventBus eventBus, final ContentViewerView view,
      final StateManager stateManager, final ContentViewerProxy proxy, final Session session,
      final ActionRegistryByType actionsRegistry, final Provider<RenameAction> renameAction,
      final PathToolbarUtils pathToolbarUtils, final WaveClientManager wavClientManager) {
    super(eventBus, view, proxy);
    this.session = session;
    this.actionsRegistry = actionsRegistry;
    this.renameAction = renameAction;
    this.pathToolbarUtils = pathToolbarUtils;
    session.onUserSignOut(true, new UserSignOutHandler() {
      @Override
      public void onUserSignOut(final UserSignOutEvent event) {
        getView().signOut();
      }
    });
    session.onUserSignIn(true, new UserSignInHandler() {
      @Override
      public void onUserSignIn(final UserSignInEvent event) {
        getView().signIn();
      }
    });
    stateManager.addBeforeStateChangeListener(new BeforeActionListener() {
      @Override
      public boolean beforeAction() {
        getView().detach();
        return true;
      }
    });
  }

  @Override
  public void attach() {
    getView().attach();
    if (editHandler == null) {
      createEditHandler();
    }
  }

  public void blinkTitle() {
    getView().blinkTitle();
  }

  private void createEditHandler() {
    editHandler = getView().getEditTitle().addEditHandler(new EditHandler() {
      @Override
      public void fire(final EditEvent event) {
        renameAction.get().rename(session.getCurrentStateToken(), session.getCurrentState().getTitle(),
            event.getText(), new RenameListener() {
              @Override
              public void onFail(final StateToken token, final String oldTitle) {
                getView().setEditableTitle(oldTitle);
              }

              @Override
              public void onSuccess(final StateToken token, final String title) {
                getView().setEditableTitle(title);
              }
            });
      }
    });
  }

  @Override
  public void detach() {
    getView().detach();
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  @Override
  public void setContent(@Nonnull final HasContent state) {
    getView().clear();
    final StateContentDTO stateContent = (StateContentDTO) state;
    final AccessRights rights = stateContent.getContentRights();
    Log.info("Content rights: " + rights);
    if (session.isLogged() && rights.isEditable()) {
      if (stateContent.isParticipant()) {
        // is already participant, show wave editor
        final org.waveprotocol.box.webclient.client.Session waveSession = org.waveprotocol.box.webclient.client.Session.get();
        if (waveSession != null && waveSession.isLoggedIn()) {
          getView().setEditableContent(stateContent);
        } else {
          getView().setContent(stateContent);
          // When logged setEditable!
        }
      } else {
        // add "participate" action
        getView().setContent(stateContent);
      }
    } else {
      if (rights.isVisible()) {
        // Show contents
        getView().setContent(stateContent);
      } else {
        throw new UIException("Unexpected status in Viewer");
      }
    }
    final GuiActionDescCollection topActions = actionsRegistry.getCurrentActions(
        stateContent.getToolName(), stateContent.getGroup(), stateContent.getTypeId(),
        session.isLogged(), rights, ActionGroups.TOPBAR);
    final GuiActionDescCollection bottomActions = actionsRegistry.getCurrentActions(
        stateContent.getToolName(), stateContent.getGroup(), stateContent.getTypeId(),
        session.isLogged(), rights, ActionGroups.BOTTOMBAR);
    final GuiActionDescCollection pathActions = pathToolbarUtils.createPath(stateContent.getGroup(),
        stateContent.getContainer(), true, false);
    bottomActions.addAll(pathActions);
    getView().setSubheaderActions(topActions);
    getView().setFooterActions(bottomActions);
  }
}
