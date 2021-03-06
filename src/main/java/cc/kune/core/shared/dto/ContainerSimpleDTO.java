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
package cc.kune.core.shared.dto;

import cc.kune.core.shared.domain.utils.StateToken;

/**
 * A item can be both a container or a content
 * 
 */
public class ContainerSimpleDTO extends AbstractContentSimpleDTO {
  private String name;
  private StateToken parentToken;

  public ContainerSimpleDTO() {
  }

  public ContainerSimpleDTO(String name, StateToken parentToken, StateToken token, String typeId) {
    this.name = name;
    this.parentToken = parentToken;
    setStateToken(token);
    setTypeId(typeId);
  }

  @Override
  public String getName() {
    return name;
  }

  public StateToken getParentToken() {
    return parentToken;
  }

  @Override
  public void setName(final String name) {
    this.name = name;
  }

  public void setParentToken(final StateToken parentToken) {
    this.parentToken = parentToken;
  }

}
