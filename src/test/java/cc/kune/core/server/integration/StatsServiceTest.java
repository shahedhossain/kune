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
package cc.kune.core.server.integration;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.stats.HomeStats;
import cc.kune.core.server.stats.StatsService;
import cc.kune.domain.Content;
import cc.kune.domain.finders.GroupFinder;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class StatsServiceTest extends IntegrationTest {
  @Inject
  GroupFinder groupFinder;
  @Inject
  StatsService statsService;

  private void checkStats(final HomeStats homeStats) {
    assertTrue(homeStats.getTotalGroups() > 0);
    assertTrue(homeStats.getTotalUsers() > 0);
    assertTrue(homeStats.getLastGroups().size() > 0);
    assertTrue(homeStats.getLastPublishedContents().size() > 0);
    final List<Content> lastContentsOfMyGroups = homeStats.getLastContentsOfMyGroups();
    assertTrue(lastContentsOfMyGroups == null || lastContentsOfMyGroups.size() > 0);
  }

  @Transactional
  @Before
  public void init() {
    new IntegrationTestHelper(true, this);
  }

  @Test
  public void testBasicStats() {
    final HomeStats homeStats = statsService.getHomeStats();
    checkStats(homeStats);
  }

  @Test
  public void testLoggedStats() {
    final HomeStats homeStats = statsService.getHomeStats(groupFinder.findByShortName(getSiteAdminShortName()));
    checkStats(homeStats);
  }

}
