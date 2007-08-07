package org.ourproject.kune.platf.server.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ajos")
public class Ajo implements HasId, HasLicense, HasTags, HasAccessRights, HasRate {
    @Id
    @GeneratedValue
    private Long id;
    private String locale;
    @Embedded
    private AccessRights accessRights;

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public String getLocale() {
	return locale;
    }

    public void setLocale(final String locale) {
	this.locale = locale;
    }

    public AccessRights getAccessRights() {
	return accessRights;
    }

    public void setAccessRights(final AccessRights accessRights) {
	this.accessRights = accessRights;
    }

}