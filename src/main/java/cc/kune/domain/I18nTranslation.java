/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

import cc.kune.domain.utils.HasId;

/**
 * GlobalizeTranslations generated by hbm2java from original rails globalize
 * schema
 */
@Entity
@Indexed
@Table(name = "globalize_translations")
public class I18nTranslation implements HasId {

    public static final String DEF_NAMESPACE = "kune_core";
    public static final Integer DEF_PLUR_INDEX = 1;
    public static final String DEFAULT_LANG = "en";

    public static final String UNTRANSLATED_VALUE = null;

    @Column(name = "facet")
    private String facet;

    @Id
    @GeneratedValue
    @DocumentId
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "item_id")
    private Integer itemId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "language_id")
    private I18nLanguage language;

    @Column(name = "pluralization_index")
    private Integer pluralizationIndex;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "text")
    private String text;

    @Column(name = "tr_key")
    private String trKey;

    @Column(name = "gtype")
    private String type;

    public I18nTranslation() {
        this(null, null, null, null, null, null, null, null);
    }

    public I18nTranslation(final String trKey, final I18nLanguage language, final String text) {
        this("", null, DEF_PLUR_INDEX, "", text, trKey, DEF_NAMESPACE, language);
    }

    public I18nTranslation(final String facet, final Integer itemId, final Integer pluralizationIndex,
            final String tableName, final String text, final String trKey, final String type,
            final I18nLanguage language) {
        this.type = type;
        this.trKey = trKey;
        this.tableName = tableName;
        this.itemId = itemId;
        this.facet = facet;
        this.language = language;
        this.pluralizationIndex = pluralizationIndex;
        this.text = text;
    }

    public I18nTranslation cloneForNewLanguage() {
        final I18nTranslation clone = new I18nTranslation();
        clone.type = type;
        clone.trKey = trKey;
        clone.tableName = tableName;
        clone.itemId = itemId;
        clone.facet = facet;
        clone.language = null;
        clone.pluralizationIndex = pluralizationIndex;
        clone.text = null;
        return clone;
    }

    public String getFacet() {
        return this.facet;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public Integer getItemId() {
        return this.itemId;
    }

    public I18nLanguage getLanguage() {
        return language;
    }

    public Integer getPluralizationIndex() {
        return this.pluralizationIndex;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getText() {
        return this.text;
    }

    public String getTrKey() {
        return this.trKey;
    }

    public String getType() {
        return this.type;
    }

    public void setFacet(final String facet) {
        this.facet = facet;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public void setItemId(final Integer itemId) {
        this.itemId = itemId;
    }

    public void setLanguage(final I18nLanguage language) {
        this.language = language;
    }

    public void setPluralizationIndex(final Integer pluralizationIndex) {
        this.pluralizationIndex = pluralizationIndex;
    }

    public void setTableName(final String tableName) {
        this.tableName = tableName;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public void setTrKey(final String trKey) {
        this.trKey = trKey;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "I18nTranslation[" + trKey + " (" + language + ") " + text + "]";
    }

}