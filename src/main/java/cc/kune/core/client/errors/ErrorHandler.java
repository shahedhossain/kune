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
package cc.kune.core.client.errors;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.notify.ProgressHideEvent;
import cc.kune.common.client.notify.UserNotifyEvent;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.events.GoHomeEvent;
import cc.kune.core.client.events.StackErrorEvent;
import cc.kune.core.client.events.UserMustBeLoggedEvent;
import cc.kune.core.client.i18n.I18nUITranslationService;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.inject.Inject;

public class ErrorHandler {

  private final EventBus eventBus;
  private final I18nUITranslationService i18n;

  @Inject
  public ErrorHandler(final I18nUITranslationService i18n, final EventBus eventBus) {
    this.i18n = i18n;
    this.eventBus = eventBus;
  }

  public void doSessionExpired() {
    eventBus.fireEvent(new SessionExpiredEvent());
  }

  private void goHome() {
    GoHomeEvent.fire(eventBus);
  }

  private void logException(final Throwable caught) {
    logException(caught, false);
  }

  private void logException(final Throwable caught, final boolean showException) {
    if (showException) {
      Log.debug("Exception in KuneErrorHandler", caught);
    } else {
      Log.debug("Exception in KuneErrorHandler: " + caught.getMessage());
    }
  }

  public void process(final Throwable caught) {
    eventBus.fireEvent(new ProgressHideEvent());
    if (caught instanceof AccessViolationException) {
      logException(caught);
      final String msg = caught.getMessage();
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("You do not have rights to perform that action")
              + (!TextUtils.empty(msg) && msg.length() > 2 ? ". " + i18n.t(msg) : "")));
      goHome();
    } else if (caught instanceof SessionExpiredException) {
      logException(caught);
      doSessionExpired();
    } else if (caught instanceof UserMustBeLoggedException) {
      logException(caught);
      UserMustBeLoggedEvent.fire(eventBus);
    } else if (caught instanceof GroupNotFoundException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.veryImportant, i18n.t("Group not found")));
      goHome();
    } else if (caught instanceof IncompatibleRemoteServiceException) {
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("Your browser is outdated with the server software. Please reload this page.")));
    } else if (caught instanceof ContentNotFoundException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.veryImportant, i18n.t("Content not found")));
      goHome();
    } else if (caught instanceof ContentNotPermittedException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("Action not permitted in this location")));
      goHome();
    } else if (caught instanceof ContainerNotPermittedException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("Action not permitted in this location")));
      goHome();
    } else if (caught instanceof NameInUseException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("A content with the same name already exist. Please rename it")));
    } else if (caught instanceof LastAdminInGroupException) {
      logException(caught);
      NotifyUser.showAlertMessage(i18n.t("Warning"),
          i18n.t("Sorry, you are the last admin of this group."
              + " Look for someone to substitute you appropriately as admin before leaving this group."));

    } else if (caught instanceof InvalidSNOperationException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, i18n.t("Invalid operation")));
    } else if (caught instanceof AlreadyGroupMemberException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("This group is already a group member")));
    } else if (caught instanceof AlreadyUserMemberException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("This user is already a group member")));
    } else if (caught instanceof EmailAddressInUseException) {
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, i18n.t(
          "This email is already used in [%s]. Please choose another.", i18n.getSiteCommonName())));
    } else if (caught instanceof EmailHashInvalidException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("This confirmation email is invalid")));
    } else if (caught instanceof EmailHashExpiredException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(
          NotifyLevel.error,
          i18n.t("This email verification is expired. In your preferences, resend you the confirmation email")));
    } else if (caught instanceof MoveOnSameContainerException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.info,
          i18n.t("You are trying to move this to the same location")));
    } else if (caught instanceof NameNotPermittedException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.info, i18n.t("This name is not permitted")));
    } else if (caught instanceof ContainerNotEmptyException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, i18n.t("This is not empty")));
    } else if (caught instanceof CannotDeleteDefaultContentException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("This is the default content of the group, you cannot delete it. "
              + "Please select other content as default group content before delete this")));
    } else if (caught instanceof UnderDevelopmentException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.info, i18n.t(TextUtils.IN_DEVELOPMENT)));
    } else {
      logException(caught, true);
      // FIXME: Remore "with our servers"
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("Oops! Something has gone wrong with our servers. Retry later, please.")));
      final String error = "Other kind of exception received in ErrorHandler (" + caught.getMessage()
          + ")";
      Log.error(error, caught);
      StackErrorEvent.fire(eventBus, caught);
    }
  }

}
