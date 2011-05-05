package cc.kune.gspace.client.viewers;

import javax.annotation.Nonnull;

import cc.kune.blogs.shared.BlogsConstants;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.ui.EditEvent;
import cc.kune.common.client.ui.EditEvent.EditHandler;
import cc.kune.common.client.ui.HasEditHandler;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.registry.IconsRegistry;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.services.ImageSize;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.BasicMimeTypeDTO;
import cc.kune.core.shared.dto.ContainerDTO;
import cc.kune.core.shared.dto.ContainerSimpleDTO;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.docs.shared.DocsConstants;
import cc.kune.gspace.client.actions.ActionGroups;
import cc.kune.gspace.client.actions.RenameAction;
import cc.kune.gspace.client.actions.RenameListener;
import cc.kune.gspace.client.tool.ContentViewer;
import cc.kune.gspace.client.tool.ContentViewerSelector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class FolderViewerPresenter extends
    Presenter<FolderViewerPresenter.FolderViewerView, FolderViewerPresenter.FolderViewerProxy> implements
    ContentViewer {

  @ProxyCodeSplit
  public interface FolderViewerProxy extends Proxy<FolderViewerPresenter> {
  }

  public interface FolderViewerView extends View {

    long NO_DATE = 0;

    void addItem(FolderItemDescriptor item, ClickHandler clickHandler,
        DoubleClickHandler doubleClickHandler);

    void attach();

    void clear();

    void detach();

    HasEditHandler getEditTitle();

    void setActions(GuiActionDescCollection actions);

    void setContainer(StateContainerDTO state);

    void setEditableTitle(String title);

    void showEmptyMsg();
  }

  private final ActionRegistryByType actionsRegistry;

  private final ContentCapabilitiesRegistry capabilitiesRegistry;

  private final Provider<FileDownloadUtils> downloadUtilsProvider;
  private HandlerRegistration editHandler;
  private final I18nTranslationService i18n;
  private final IconsRegistry iconsRegistry;
  private final Provider<RenameAction> renameAction;
  private final Session session;
  private final StateManager stateManager;
  private final boolean useGenericImageIcon;

  @Inject
  public FolderViewerPresenter(final EventBus eventBus, final FolderViewerView view,
      final FolderViewerProxy proxy, final Session session, final StateManager stateManager,
      final I18nTranslationService i18n, final ContentViewerSelector viewerSelector,
      final ActionRegistryByType actionsRegistry,
      final Provider<FileDownloadUtils> downloadUtilsProvider,
      final ContentCapabilitiesRegistry capabilitiesRegistry, final Provider<RenameAction> renameAction) {
    super(eventBus, view, proxy);
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.actionsRegistry = actionsRegistry;
    this.downloadUtilsProvider = downloadUtilsProvider;
    this.capabilitiesRegistry = capabilitiesRegistry;
    iconsRegistry = capabilitiesRegistry.getIconsRegistry();
    viewerSelector.register(this, true, DocsConstants.TYPE_ROOT, DocsConstants.TYPE_FOLDER);
    viewerSelector.register(this, true, BlogsConstants.TYPE_ROOT, BlogsConstants.TYPE_BLOG);
    useGenericImageIcon = false;
    this.renameAction = renameAction;
  }

  private void addItem(final String title, final String contentTypeId, final BasicMimeTypeDTO mimeType,
      final ContentStatus status, final StateToken stateToken, final StateToken parentStateToken,
      final AccessRights rights, final long modifiedOn) {
    final Object icon = getIcon(stateToken, contentTypeId, mimeType);
    final String tooltip = getTooltip(stateToken, mimeType);
    final FolderItemDescriptor item = new FolderItemDescriptor(genId(stateToken),
        genId(parentStateToken), icon, title, tooltip, status, stateToken, modifiedOn,
        capabilitiesRegistry.isDragable(contentTypeId) && rights.isAdministrable(),
        capabilitiesRegistry.isDropable(contentTypeId) && rights.isAdministrable(),
        actionsRegistry.getCurrentActions(stateToken, contentTypeId, session.isLogged(), rights,
            ActionGroups.MENUITEM));
    if (status.equals(ContentStatus.inTheDustbin) && !session.getShowDeletedContent()) {
      // Don't show
      // NotifyUser.info("Deleted, don't show");
    } else {
      getView().addItem(item, new ClickHandler() {
        @Override
        public void onClick(final ClickEvent event) {
          stateManager.gotoStateToken(stateToken);
        }
      }, new DoubleClickHandler() {
        @Override
        public void onDoubleClick(final DoubleClickEvent event) {
          stateManager.gotoStateToken(stateToken);
        }
      });
    }
  }

  @Override
  public void attach() {
    getView().attach();
    if (editHandler == null) {
      createEditHandler();
    }
  }

  private void createChildItems(final ContainerDTO container, final AccessRights containerRights) {
    if (container.getContents().size() + container.getChilds().size() == 0) {
      getView().showEmptyMsg();
    } else {
      // Folders
      for (final ContainerSimpleDTO childFolder : container.getChilds()) {
        addItem(childFolder.getName(), childFolder.getTypeId(), null, ContentStatus.publishedOnline,
            childFolder.getStateToken(),
            childFolder.getStateToken().copy().setFolder(childFolder.getParentFolderId()),
            containerRights, FolderViewerView.NO_DATE);
      }
      // Other contents (docs, etc)
      for (final ContentSimpleDTO content : container.getContents()) {
        addItem(content.getTitle(), content.getTypeId(), content.getMimeType(), content.getStatus(),
            content.getStateToken(), content.getStateToken().copy().clearDocument(),
            content.getRights(), content.getModifiedOn());
      }
    }
  }

  private void createEditHandler() {
    // Duplicate in DocViewerPresenter
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

  private String genId(final StateToken token) {
    return "k-cnav-" + token.toString().replace(StateToken.SEPARATOR, "-");
  }

  private Object getIcon(final StateToken token, final String contentTypeId,
      final BasicMimeTypeDTO mimeType) {
    if (!useGenericImageIcon && mimeType != null && mimeType.getType().equals("image")) {
      return downloadUtilsProvider.get().getImageResizedUrl(token, ImageSize.ico);
    } else {
      return iconsRegistry.getContentTypeIcon(contentTypeId, mimeType);
    }
  }

  private String getTooltip(final StateToken token, final BasicMimeTypeDTO mimeType) {
    if (mimeType != null && (mimeType.isImage() || mimeType.isPdf())) {
      // Used for previews
      return null;
    } else {
      return i18n.t("Double click to open");
    }
  }

  public void refreshState() {
    setContent((HasContent) session.getCurrentState());
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  @Override
  public void setContent(@Nonnull final HasContent state) {
    getView().clear();
    final StateContainerDTO stateContainer = (StateContainerDTO) state;
    getView().setContainer(stateContainer);
    final AccessRights rights = stateContainer.getContainerRights();
    final GuiActionDescCollection actions = actionsRegistry.getCurrentActions(stateContainer.getGroup(),
        stateContainer.getTypeId(), session.isLogged(), rights, ActionGroups.VIEW);
    getView().setActions(actions);
    createChildItems(stateContainer.getContainer(), stateContainer.getContainerRights());
    // view.setEditable(rights.isEditable());
  }
}