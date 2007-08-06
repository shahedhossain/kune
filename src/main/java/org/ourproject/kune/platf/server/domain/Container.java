package org.ourproject.kune.platf.server.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "containers")
public class Container extends Ajo {
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Alias> aliases;
    private License defaultLicense;

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public List<Alias> getAliases() {
	return aliases;
    }

    public void setAliases(final List<Alias> aliases) {
	this.aliases = aliases;
    }

    public License getDefaultLicense() {
	return defaultLicense;
    }

    public void setDefaultLicense(final License defaultLicense) {
	this.defaultLicense = defaultLicense;
    }

}
