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
package cc.kune.core.server.rest;

import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.SearchResult;
import cc.kune.core.server.mapper.Mapper;
import cc.kune.core.server.rack.filters.rest.REST;
import cc.kune.core.shared.SearcherConstants;
import cc.kune.core.shared.dto.GroupResultDTO;
import cc.kune.core.shared.dto.SearchResultDTO;
import cc.kune.domain.Group;

import com.google.inject.Inject;

public class GroupJSONService {
  private final GroupManager manager;
  private final Mapper mapper;

  @Inject
  public GroupJSONService(final GroupManager manager, final Mapper mapper) {
    this.manager = manager;
    this.mapper = mapper;
  }

  @REST(params = { SearcherConstants.QUERY_PARAM })
  public SearchResultDTO<GroupResultDTO> search(final String search) {
    return search(search, null, null);
  }

  @REST(params = { SearcherConstants.QUERY_PARAM, SearcherConstants.START_PARAM,
      SearcherConstants.LIMIT_PARAM })
  public SearchResultDTO<GroupResultDTO> search(final String search, final Integer firstResult,
      final Integer maxResults) {
    final SearchResult<Group> results = manager.search(search, firstResult, maxResults);
    return mapper.mapSearchResult(results, GroupResultDTO.class);
  }

}
