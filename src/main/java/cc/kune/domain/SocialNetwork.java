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
package cc.kune.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cc.kune.core.shared.domain.SocialNetworkVisibility;

@Entity
@Table(name = "social_networks")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SocialNetwork {
  public static final SocialNetwork EMPTY = new SocialNetwork(SocialNetworkVisibility.onlyadmins);

  @OneToOne(cascade = CascadeType.ALL)
  AccessLists accessLists;

  @Id
  @GeneratedValue
  Long id;

  @OneToOne(cascade = CascadeType.ALL)
  GroupList pendingCollaborators;

  @Enumerated(EnumType.STRING)
  SocialNetworkVisibility visibility;

  public SocialNetwork() {
    accessLists = new AccessLists();
    pendingCollaborators = new GroupList();
    visibility = SocialNetworkVisibility.anyone;
  }

  public SocialNetwork(final SocialNetworkVisibility visibility) {
    accessLists = new AccessLists();
    pendingCollaborators = new GroupList();
    this.visibility = visibility;
  }

  public void addAdmin(final Group group) {
    accessLists.addAdmin(group);
  }

  public void addCollaborator(final Group group) {
    accessLists.addEditor(group);
  }

  public void addPendingCollaborator(final Group group) {
    pendingCollaborators.add(group);
  }

  public void addViewer(final Group group) {
    accessLists.addViewer(group);
  }

  public AccessLists getAccessLists() {
    return accessLists;
  }

  public Long getId() {
    return id;
  }

  public GroupList getPendingCollaborators() {
    return pendingCollaborators;
  }

  public SocialNetworkVisibility getVisibility() {
    return visibility;
  }

  public boolean isAdmin(final Group group) {
    return accessLists.getAdmins().includes(group);
  }

  public boolean isCollab(final Group group) {
    return accessLists.getEditors().includes(group);
  }

  public boolean isPendingCollab(final Group group) {
    return pendingCollaborators.getList().contains(group);
  }

  public boolean isViewer(final Group group) {
    return accessLists.getViewers().includes(group);
  }

  public void removeAdmin(final Group group) {
    accessLists.removeAdmin(group);
  }

  public void removeCollaborator(final Group group) {
    accessLists.removeEditor(group);
  }

  public void removePendingCollaborator(final Group group) {
    pendingCollaborators.getList().remove(group);
  }

  public void removeViewer(final Group group) {
    accessLists.removeViewer(group);
  }

  public void setAccessLists(final AccessLists accessList) {
    this.accessLists = accessList;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public void setPendingCollaborators(final GroupList pendingCollaborators) {
    this.pendingCollaborators = pendingCollaborators;
  }

  public void setVisibility(final SocialNetworkVisibility visibility) {
    this.visibility = visibility;
  }

  @Override
  public String toString() {
    return "SocialNetwork[accessList: " + accessLists + "; pendingsCollabs: " + pendingCollaborators
        + "]";
  }
}
