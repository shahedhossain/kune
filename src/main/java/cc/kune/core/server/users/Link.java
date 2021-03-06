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
package cc.kune.core.server.users;

import java.util.List;

import cc.kune.domain.Tag;

public class Link {
  private String shortName;
  private String longName;
  private String iconUrl;
  private String link;
  private Double rate;
  private List<Tag> tags;

  public Double getRate() {
    return rate;
  }

  public void setRate(final Double rate) {
    this.rate = rate;
  }

  public Link() {
    this(null, null, null, null);
  }

  public Link(final String shortName, final String longName, final String iconUrl, final String link) {
    this.shortName = shortName;
    this.longName = longName;
    this.iconUrl = iconUrl;
    this.link = link;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(final String shortName) {
    this.shortName = shortName;
  }

  public String getLink() {
    return link;
  }

  public void setLink(final String link) {
    this.link = link;
  }

  public String getLongName() {
    return longName;
  }

  public void setLongName(final String longName) {
    this.longName = longName;
  }

  public String getIconUrl() {
    return iconUrl;
  }

  public void setIconUrl(final String iconUrl) {
    this.iconUrl = iconUrl;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(final List<Tag> tags) {
    this.tags = tags;
  }

}
