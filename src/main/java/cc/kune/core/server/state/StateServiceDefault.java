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
package cc.kune.core.server.state;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.util.escapers.jvm.JavaWaverefEncoder;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.server.access.AccessRightsService;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.SocialNetworkManager;
import cc.kune.core.server.manager.TagUserContentManager;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.License;
import cc.kune.domain.Revision;
import cc.kune.domain.User;
import cc.kune.events.server.utils.EventsServerConversionUtil;
import cc.kune.events.shared.EventsToolConstants;
import cc.kune.wave.server.kspecific.KuneWaveService;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.wave.api.Wavelet;

@Singleton
public class StateServiceDefault implements StateService {
  public static final Log LOG = LogFactory.getLog(StateServiceDefault.class);
  private final GroupManager groupManager;
  private final I18nTranslationService i18n;
  private final KuneWaveService kuneWaveService;
  private final RenderedWavesCache renderedWaves;
  private final AccessRightsService rightsService;
  private final SocialNetworkManager socialNetworkManager;
  private final TagUserContentManager tagManager;

  @Inject
  public StateServiceDefault(final GroupManager groupManager,
      final SocialNetworkManager socialNetworkManager, final TagUserContentManager tagManager,
      final AccessRightsService rightsService, final I18nTranslationService i18n,
      final KuneWaveService kuneWaveService, final RenderedWavesCache renderedWaves) {
    this.groupManager = groupManager;
    this.socialNetworkManager = socialNetworkManager;
    this.tagManager = tagManager;
    this.rightsService = rightsService;
    this.i18n = i18n;
    this.kuneWaveService = kuneWaveService;
    this.renderedWaves = renderedWaves;
  }

  private Container calculateRootContainer(final Container container) {
    return container.isRoot() ? container : container.getAbsolutePath().get(0);
  }

  @Override
  public StateContainer create(final User userLogged, final Container container) {
    final boolean isCalendar = container.getTypeId().equals(EventsToolConstants.TYPE_ROOT);
    final StateContainer state = isCalendar ? new StateEventContainer() : new StateContainer();
    state.setTitle(container.getName());
    state.setTypeId(container.getTypeId());
    state.setLanguage(container.getLanguage());
    state.setStateToken(container.getStateToken());
    state.setLicense(container.getOwner().getDefaultLicense());
    state.setAccessLists(container.getAccessLists());
    final Group group = container.getOwner();
    setCommon(state, userLogged, group, container);
    if (isCalendar) {
      ((StateEventContainer) state).setAppointments(EventsServerConversionUtil.getAppointments(container));
    }
    return state;
  }

  @Override
  public StateContent create(final User userLogged, final Content content) {
    final StateContent state = new StateContent();
    final String typeId = content.getTypeId();
    state.setTypeId(typeId);
    state.setMimeType(content.getMimeType());
    state.setDocumentId(content.getId().toString());
    state.setLanguage(content.getLanguage());
    state.setPublishedOn(content.getPublishedOn());
    state.setAuthors(content.getAuthors());
    state.setTags(tagManager.getTagsAsString(userLogged, content));
    state.setStatus(content.getStatus());
    state.setStateToken(content.getStateToken());
    final Revision revision = content.getLastRevision();
    state.setVersion(content.getVersion());
    final char[] text = revision.getBody();
    final String textBody = text == null ? null : new String(text);
    if (content.isWave()) {
      final String waveRef = content.getWaveId();
      state.setWaveRef(waveRef);
      try {
        // FIXME if we remove the authors this fails...
        final Wavelet wavelet = kuneWaveService.fetchWave(
            JavaWaverefEncoder.decodeWaveRefFromPath(waveRef),
            content.getAuthors().get(0).getShortName());
        // final String currentContent = wavelet.getRootBlip().getContent();

        state.setContent(renderedWaves.getOrRender(wavelet));
        // Well we "cache" the last modified time of waves in db (w'll find
        // another way to do it better in the future with db persitence of
        // waves)
        // FIXME, how to do this
        // contentManager.setModifiedOn(content, wavelet.getLastModifiedTime());
        // contentManager.save(userLogged, content, currentContent);
        state.setTitle(wavelet.getTitle());
        state.setIsParticipant(userLogged != User.UNKNOWN_USER ? kuneWaveService.isParticipant(wavelet,
            userLogged.getShortName()) : false);
      } catch (final Exception e) {
        LOG.error("Error accessing wave " + waveRef, e);
        String waveUrl = null;
        try {
          waveUrl = TextUtils.generateHtmlLink(
              "#"
                  + JavaWaverefEncoder.encodeToUriPathSegment(JavaWaverefEncoder.decodeWaveRefFromPath(waveRef)),
              waveRef, false);
        } catch (final InvalidWaveRefException invalidEx) {
        }
        state.setContent(i18n.t("Error accessing this document. "
            + "Please contact the administrators providing this reference ([%s]) "
            + "and any other relevant info.", (waveUrl == null ? waveRef : waveUrl)));
        state.setTitle(revision.getTitle());
      }
    } else {
      state.setContent(textBody);
      state.setTitle(revision.getTitle());
    }
    final Container container = content.getContainer();

    final License license = content.getLicense();
    final Group group = container.getOwner();
    state.setLicense(license == null ? group.getDefaultLicense() : license);
    state.setContentRights(rightsService.get(userLogged, content.getAccessLists()));
    state.setAccessLists(content.getAccessLists());
    setCommon(state, userLogged, group, container);
    // if (userLogged != User.UNKNOWN_USER) {
    // state.setCurrentUserRate(contentManager.getRateContent(userLogged,
    // content));
    // }
    // FIXME: user RateResult
    // final Double rateAvg = contentManager.getRateAvg(content);
    // state.setRate(rateAvg != null ? rateAvg : 0D);
    // final Long rateByUsers = contentManager.getRateByUsers(content);
    // state.setRateByUsers(rateByUsers != null ? rateByUsers.intValue() : 0);
    return state;
  }

  @Override
  public StateNoContent createNoHome(final User userLogged, final String groupShortName) {
    final Group group = groupManager.findByShortName(groupShortName);
    assert (group.isPersonal());
    final StateNoContent state = new StateNoContent();
    state.setGroup(group);
    state.setEnabledTools(groupManager.findEnabledTools(group.getId()));
    setSocialNetwork(state, userLogged, group);
    state.setStateToken(group.getStateToken());
    state.setTitle("<h2>" + i18n.t("This user does not have a homepage") + "</h2>");
    return state;
  }

  private void setCommon(final StateContainer state, final User userLogged, final Group group,
      final Container container) {
    final Container root = calculateRootContainer(container);
    state.setRootContainer(root);
    state.setToolName(root.getToolName());
    state.setGroup(group);
    state.setContainer(container);
    state.setContainerRights(rightsService.get(userLogged, container.getAccessLists()));
    state.setEnabledTools(groupManager.findEnabledTools(group.getId()));
    // state.setTagCloudResult(tagManager.getTagCloudResultByGroup(group));
    setSocialNetwork(state, userLogged, group);
  }

  private void setSocialNetwork(final StateAbstract state, final User userLogged, final Group group) {
    state.setSocialNetworkData(socialNetworkManager.getSocialNetworkData(userLogged, group));
  }
}
