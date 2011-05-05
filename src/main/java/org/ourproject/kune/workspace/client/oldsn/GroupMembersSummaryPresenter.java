/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
 \*/
package org.ourproject.kune.workspace.client.oldsn;

import java.util.List;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuRadioDescriptor;
import org.ourproject.kune.platf.client.actions.RadioMustBeChecked;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.ui.MenuItem;
import org.ourproject.kune.platf.client.ui.gridmenu.GridGroup;
import org.ourproject.kune.workspace.client.oldsn.toolbar.ActionGroupSummaryToolbar;
import org.ourproject.kune.workspace.client.search.GroupLiveSearcher;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.services.ImageDescriptor;
import cc.kune.core.client.services.ImageUtils;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.domain.SocialNetworkVisibility;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessListsDTO;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.InitDataDTO;
import cc.kune.core.shared.dto.LinkDTO;
import cc.kune.core.shared.dto.SocialNetworkDTO;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.suco.client.events.Listener;
import com.google.inject.Provider;

public class GroupMembersSummaryPresenter extends SocialNetworkPresenter implements GroupMembersSummary {

    public static final String MEMBERS_VISIBILITY_GROUP = "k-gmsp-memb-visib";
    public static final String NEW_MEMBERS_POLICY_GROUP = "k-gmsp-new-memb-pol";

    private final GridGroup adminCategory;
    private final GridGroup collabCategory;
    private final GroupActionRegistry groupActionRegistry;
    private final Provider<GroupServiceAsync> groupServiceProvider;
    private final I18nUITranslationService i18n;
    private final Provider<GroupLiveSearcher> liveSearcherProvider;
    private final GridGroup pendigCategory;
    private final Session session;
    private final Provider<SocialNetworkServiceAsync> snServiceProvider;
    private final StateManager stateManager;
    private GroupMembersSummaryView view;

    public GroupMembersSummaryPresenter(final I18nUITranslationService i18n, final StateManager stateManager,
            final ImageUtils imageUtils, final Session session,
            final Provider<SocialNetworkServiceAsync> snServiceProvider,
            final Provider<GroupServiceAsync> groupServiceProvider,
            final Provider<GroupLiveSearcher> liveSearcherProvider, final Provider<ChatEngine> chatEngineProvider,
            final GroupActionRegistry groupActionRegistry, final ActionGroupSummaryToolbar toolbar,
            final Provider<FileDownloadUtils> downloadProvider, final AccessRightsClientManager accessRightsManager,
            final IconResources img) {
        super(i18n, stateManager, accessRightsManager, session, snServiceProvider, groupActionRegistry,
                downloadProvider, img);
        this.i18n = i18n;
        this.stateManager = stateManager;
        this.session = session;
        this.snServiceProvider = snServiceProvider;
        this.groupServiceProvider = groupServiceProvider;
        this.liveSearcherProvider = liveSearcherProvider;
        this.groupActionRegistry = groupActionRegistry;
        final Listener<StateAbstractDTO> setStateListener = new Listener<StateAbstractDTO>() {
            @Override
            public void onEvent(final StateAbstractDTO state) {
                setState(state);
                toolbar.disableMenusAndClearButtons();
                toolbar.addActions(groupActionRegistry.getCurrentActions(state.getGroup().getStateToken(),
                        session.isLogged(), state.getGroupRights(), true), ActionToolbar.IN_ANY);
                toolbar.attach();
            }
        };
        stateManager.onStateChanged(setStateListener);
        stateManager.onSocialNetworkChanged(setStateListener);
        session.onInitDataReceived(new Listener<InitDataDTO>() {
            @Override
            public void onEvent(final InitDataDTO initData) {
                addUserOperation(new MenuItem<GroupDTO>("images/new-chat.gif", i18n.t("Start a chat with this member"),
                        new Listener<GroupDTO>() {
                            @Override
                            public void onEvent(final GroupDTO group) {
                                chatEngineProvider.get().show();
                                if (chatEngineProvider.get().isLoggedIn()) {
                                    chatEngineProvider.get().chat(
                                            XmppURI.jid(group.getShortName() + "@" + initData.getChatDomain()));
                                } else {
                                    NotifyUser.important(i18n.t("To start a chat you need to be 'online'"));
                                }
                            }
                        }), true);
            }
        });
        final String adminsTitle = i18n.t("Admins");
        final String collabsTitle = i18n.t("Collaborators");
        final String pendingTitle = i18n.t("Pending");
        adminCategory = new GridGroup(adminsTitle, adminsTitle, i18n.t("People that can admin this group"), true);
        collabCategory = new GridGroup(collabsTitle, collabsTitle,
                i18n.t("Other people that collaborate with this group"), true);
        pendigCategory = new GridGroup(pendingTitle, pendingTitle,
                i18n.t("People pending to be accepted in this group by the admins"),
                imageUtils.getImageHtml(ImageDescriptor.alert), true);
        super.addGroupOperation(gotoGroupMenuItem, false);
        super.addUserOperation(gotoMemberMenuItem, false);
        createActions();
    }

    public void addCollab(final String groupShortName) {
        NotifyUser.showProgressProcessing();
        snServiceProvider.get().addCollabMember(session.getUserHash(), session.getCurrentState().getStateToken(),
                groupShortName, new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                    @Override
                    public void onSuccess(final SocialNetworkDataDTO result) {
                        NotifyUser.hideProgress();
                        NotifyUser.info(i18n.t("Member added as collaborator"));
                        stateManager.setSocialNetwork(result);
                    }

                });
    }

    private void createActions() {
        final ActionToolbarMenuDescriptor<StateToken> addMember = new ActionToolbarMenuDescriptor<StateToken>(
                AccessRolDTO.Administrator, membersBottom, new Listener<StateToken>() {
                    @Override
                    public void onEvent(final StateToken parameter) {
                        liveSearcherProvider.get().onSelection(new Listener<LinkDTO>() {
                            @Override
                            public void onEvent(final LinkDTO link) {
                                view.confirmAddCollab(link.getShortName(), link.getLongName());
                            }
                        });
                        liveSearcherProvider.get().show();
                    }
                });
        addMember.setIconUrl("images/add-green.gif");
        addMember.setTextDescription(i18n.t("Add member"));
        addMember.setToolTip(i18n.t("Add an user or a group as member of this group"));
        addMember.setParentMenuTitle(i18n.t("Options"));

        groupActionRegistry.addAction(addMember);
        // groupActionRegistry.addAction(unJoin);

        // groupActionRegistry.addAction(participate);
        createSetMembersVisibilityAction(i18n.t("anyone"), SocialNetworkVisibility.anyone);
        createSetMembersVisibilityAction(i18n.t("only members"), SocialNetworkVisibility.onlymembers);
        createSetMembersVisibilityAction(i18n.t("only admins"), SocialNetworkVisibility.onlyadmins);
        createNewMembersPolicyAction(i18n.t("moderate request to join"), AdmissionType.Moderated);
        createNewMembersPolicyAction(i18n.t("auto accept request to join"), AdmissionType.Open);
    }

    private void createNewMembersPolicyAction(final String textDescription, final AdmissionType admissionPolicy) {
        final ActionToolbarMenuRadioDescriptor<StateToken> newMembersPolicy = new ActionToolbarMenuRadioDescriptor<StateToken>(
                AccessRolDTO.Administrator, membersBottom, new Listener<StateToken>() {
                    @Override
                    public void onEvent(final StateToken parameter) {
                        groupServiceProvider.get().setGroupNewMembersJoiningPolicy(session.getUserHash(),
                                session.getCurrentState().getGroup().getStateToken(), admissionPolicy,
                                new AsyncCallbackSimple<Void>() {
                                    @Override
                                    public void onSuccess(final Void result) {
                                        NotifyUser.info(i18n.t("Members joining policy changed"));
                                    }
                                });
                    }
                }, NEW_MEMBERS_POLICY_GROUP, new RadioMustBeChecked() {
                    @Override
                    public boolean mustBeChecked() {
                        final StateAbstractDTO currentState = session.getCurrentState();
                        return currentState.getGroup().getAdmissionType().equals(admissionPolicy);
                    }
                });
        newMembersPolicy.setTextDescription(textDescription);
        newMembersPolicy.setParentMenuTitle(i18n.t("Options"));
        newMembersPolicy.setParentSubMenuTitle(i18n.t("New members policy"));
        groupActionRegistry.addAction(newMembersPolicy);
    }

    private void createSetMembersVisibilityAction(final String textDescription, final SocialNetworkVisibility visibility) {
        final ActionToolbarMenuRadioDescriptor<StateToken> showMembers = new ActionToolbarMenuRadioDescriptor<StateToken>(
                AccessRolDTO.Administrator, membersBottom, new Listener<StateToken>() {
                    @Override
                    public void onEvent(final StateToken parameter) {
                        groupServiceProvider.get().setSocialNetworkVisibility(session.getUserHash(),
                                session.getCurrentState().getGroup().getStateToken(), visibility,
                                new AsyncCallbackSimple<Void>() {
                                    @Override
                                    public void onSuccess(final Void result) {
                                        NotifyUser.info(i18n.t("Members visibility changed"));
                                    }
                                });
                    }
                }, MEMBERS_VISIBILITY_GROUP, new RadioMustBeChecked() {
                    @Override
                    public boolean mustBeChecked() {
                        final StateAbstractDTO currentState = session.getCurrentState();
                        if (!currentState.getGroup().isPersonal()) {
                            final SocialNetworkDataDTO socialNetworkData = currentState.getSocialNetworkData();
                            return socialNetworkData.getSocialNetworkVisibility().equals(visibility);
                        }
                        return false;
                    }
                });
        showMembers.setTextDescription(textDescription);
        showMembers.setParentMenuTitle(i18n.t("Options"));
        showMembers.setParentSubMenuTitle(i18n.t("Those who can view this member list"));
        groupActionRegistry.addAction(showMembers);
    }

    public void init(final GroupMembersSummaryView view) {
        this.view = view;
    }

    @SuppressWarnings("unchecked")
    private void setGroupMembers(final SocialNetworkDTO socialNetwork, final AccessRights rights) {
        final AccessListsDTO accessLists = socialNetwork.getAccessLists();

        final List<GroupDTO> adminsList = accessLists.getAdmins().getList();
        final List<GroupDTO> collabList = accessLists.getEditors().getList();
        final List<GroupDTO> pendingCollabsList = socialNetwork.getPendingCollaborators().getList();

        // final int numAdmins = adminsList.size();

        final boolean userIsAdmin = rights.isAdministrable();
        final boolean userCanView = rights.isVisible();

        view.clear();

        view.setDraggable(session.isLogged());

        if (userCanView) {
            for (final GroupDTO admin : adminsList) {
                view.addItem(createGridItem(adminCategory, admin, rights, changeToCollabMenuItem, removeMemberMenuItem));
            }
            for (final GroupDTO collab : collabList) {
                view.addItem(createGridItem(collabCategory, collab, rights, changeToAdminMenuItem, removeMemberMenuItem));
            }
            if (userIsAdmin) {
                for (final GroupDTO pendingCollab : pendingCollabsList) {
                    view.addItem(createGridItem(pendigCategory, pendingCollab, rights, acceptJoinGroupMenuItem,
                            denyJoinGroupMenuItem));
                }
            }
        }
        view.setVisible(true);
    }

    private void setState(final StateAbstractDTO state) {
        if (state.getGroup().isPersonal()) {
            view.setVisible(false);
        } else {
            if (state.getSocialNetworkData().isMembersVisible()) {
                setGroupMembers(state.getGroupMembers(), state.getGroupRights());
            } else {
                view.clear();
                view.showMembersNotVisible();
                view.setVisible(true);
            }
        }
    }
}