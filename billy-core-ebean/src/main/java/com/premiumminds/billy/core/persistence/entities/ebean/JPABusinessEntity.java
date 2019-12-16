/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core Ebean.
 *
 * billy core Ebean is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core Ebean is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core Ebean. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.persistence.entities.ebean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Context;

@Entity
@Inheritance
@DiscriminatorColumn(length = 255)
@DiscriminatorValue("JPABusinessEntity")
@Table(name = Config.TABLE_PREFIX + "BUSINESS")
public class JPABusinessEntity extends JPABaseEntity implements BusinessEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne(targetEntity = JPAContextEntity.class)
    @JoinColumn(name = "ID_OPERATIONAL_CONTEXT", referencedColumnName = "ID")
    protected JPAContextEntity operationalContext;

    @Column(name = "TAX_ID")
    protected String taxId;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "COMMERCIAL_NAME")
    protected String commercialName;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = JPAAddressEntity.class,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_ADDRESS", referencedColumnName = "ID")
    protected JPAAddressEntity address;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = JPAAddressEntity.class,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_BILLING_ADDRESS", referencedColumnName = "ID")
    protected JPAAddressEntity billingAddress;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = JPAAddressEntity.class,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_SHIPPING_ADDRESS", referencedColumnName = "ID")
    protected JPAAddressEntity shippingAddress;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = JPAContactEntity.class,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_MAIN_CONTACT", referencedColumnName = "ID")
    protected JPAContactEntity mainContact;

    @OneToMany(targetEntity = JPAContactEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE },
            mappedBy = "business")
    protected List<JPAContactEntity> contacts;

    @Column(name = "WEBSITE")
    protected String website;

    @OneToMany(targetEntity = JPAApplicationEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_BUSINESS")
    protected List<JPAApplicationEntity> applications;

    public JPABusinessEntity() {
        this.contacts = new ArrayList<>();
        this.applications = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Context getOperationalContext() {
        return this.operationalContext;
    }

    @Override
    public String getFinancialID() {
        return this.taxId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getCommercialName() {
        return this.commercialName;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Address getAddress() {
        return this.address;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Address getBillingAddress() {
        return this.billingAddress;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Address getShippingAddress() {
        return this.shippingAddress;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Contact getMainContact() {
        return this.mainContact;
    }

    @Override
    public String getWebsiteAddress() {
        return this.website;
    }

    @Override
    public <T extends ContextEntity> void setOperationalContext(T context) {
        this.operationalContext = (JPAContextEntity) context;
    }

    @Override
    public void setFinancialID(String id) {
        this.taxId = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setWebsiteAddress(String address) {
        this.website = address;
    }

    @Override
    public void setCommercialName(String name) {
        this.commercialName = name;
    }

    @Override
    public <T extends AddressEntity> void setAddress(T address) {
        this.address = (JPAAddressEntity) address;
    }

    @Override
    public <T extends AddressEntity> void setBillingAddress(T address) {
        this.billingAddress = (JPAAddressEntity) address;
    }

    @Override
    public <T extends AddressEntity> void setShippingAddress(T address) {
        this.shippingAddress = (JPAAddressEntity) address;
    }

    @Override
    public List<JPAContactEntity> getContacts() {
        return this.contacts;
    }

    @Override
    public <T extends ContactEntity> void setMainContact(T contact) {
        this.mainContact = (JPAContactEntity) contact;
    }

    @Override
    public List<JPAApplicationEntity> getApplications() {
        return this.applications;
    }
}
