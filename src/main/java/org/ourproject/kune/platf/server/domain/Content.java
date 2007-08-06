package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "contents")
public class Content extends Ajo implements HasContent {
    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private List<Tag> tags;
    private License license;
    private Rate rate;
    private String uuid;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Translation> translations;

    public Content() {
	translations = new ArrayList<Translation>();
	tags = new ArrayList<Tag>();
    }

    public List<Tag> getTags() {
	return tags;
    }

    public void setTags(final List<Tag> tags) {
	this.tags = tags;
    }

    public License getLicense() {
	return license;
    }

    public void setLicense(final License license) {
	this.license = license;
    }

    public Rate getRate() {
	return rate;
    }

    public void setRate(final Rate rate) {
	this.rate = rate;
    }

    public String getUuid() {
	return uuid;
    }

    public void setUuid(final String uuid) {
	this.uuid = uuid;
    }

    public List<Translation> getTranslations() {
	return translations;
    }

    public void setTranslations(final List<Translation> translations) {
	this.translations = translations;
    }

}
