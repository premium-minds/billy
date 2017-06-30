/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core JPA.
 *
 * billy core JPA is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core JPA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core JPA. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.persistence.entities.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.ApplicationEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.services.entities.Contact;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "APPLICATION")
public class JPAApplicationEntity extends JPABaseEntity implements ApplicationEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "VERSION")
    protected String version;

    @Column(name = "DEVELOPER_NAME")
    protected String developerName;

    @Column(name = "DEVELOPER_TAX_ID")
    protected String developerTaxId;

    @Column(name = "WEBSITE")
    protected String website;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = JPAContactEntity.class,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_MAIN_CONTACT", referencedColumnName = "ID")
    protected Contact mainContact;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = JPAContactEntity.class,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = Config.TABLE_PREFIX + "APPLICATION_CONTACT",
            joinColumns = { @JoinColumn(name = "ID_APPLIATION", referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "ID_CONTACT", referencedColumnName = "ID", unique = true) })
    protected List<Contact> contacts;

    public JPAApplicationEntity() {
        this.contacts = new ArrayList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public String getDeveloperCompanyName() {
        return this.developerName;
    }

    @Override
    public String getDeveloperCompanyTaxIdentifier() {
        return this.developerTaxId;
    }

    @Override
    public String getWebsiteAddress() {
        return this.website;
    }

    @Override
    public <T extends Contact> Contact getMainContact() {
        return this.mainContact;
    }

    @Override
    public List<Contact> getContacts() {
        return this.contacts;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setDeveloperCompanyName(String name) {
        this.developerName = name;
    }

    @Override
    public void setDeveloperCompanyTaxIdentifier(String id) {
        this.developerTaxId = id;
    }

    @Override
    public <T extends ContactEntity> void setMainContact(T contact) {
        this.mainContact = contact;
    }

    @Override
    public void setWebsiteAddress(String website) {
        this.website = website;
    }

}
