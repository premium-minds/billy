/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.test.fixtures;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ApplicationEntity;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Context;

public class MockBusinessEntity extends MockBaseEntity<Business> implements BusinessEntity {

    private static final long serialVersionUID = 1L;

    public ContextEntity operationalContext;
    public String taxId;
    public String taxIdCountry;
    public String name;
    public String commercialName;
    public AddressEntity address;
    public AddressEntity billingAddress;
    public AddressEntity shippingAddress;
    public ContactEntity mainContact;
    public List<ContactEntity> contacts;
    public String website;
    public List<ApplicationEntity> applications;
    public ZoneId timezone;

    public MockBusinessEntity() {
        this.contacts = new ArrayList<>();
        this.applications = new ArrayList<>();
    }

    @Override
    public Context getOperationalContext() {
        return this.operationalContext;
    }

    @Override
    public String getFinancialID() {
        return this.taxId;
    }

    @Override
    public String getFinancialIdISOCountryCode() {
        return this.taxIdCountry;
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
    public ZoneId getTimezone() {
        return this.timezone;
    }

    @Override
    public <T extends ContextEntity> void setOperationalContext(T context) {
        this.operationalContext = context;
    }

    @Override
    public void setFinancialID(String id) {
        this.taxId = id;
    }

    @Override
    public void setFinancialIdISOCountryCode(final String isoCountryCode) {
        this.taxIdCountry = isoCountryCode;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCommercialName(String name) {
        this.commercialName = name;
    }

    @Override
    public <T extends AddressEntity> void setAddress(T address) {
        this.address = address;
    }

    @Override
    public <T extends AddressEntity> void setBillingAddress(T address) {
        this.billingAddress = address;
    }

    @Override
    public <T extends AddressEntity> void setShippingAddress(T address) {
        this.shippingAddress = address;
    }

    @Override
    public void setWebsiteAddress(String website) {
        this.website = website;
    }

    @Override
    public List<ContactEntity> getContacts() {
        return this.contacts;
    }

    @Override
    public <T extends ContactEntity> void setMainContact(T contact) {
        this.mainContact = contact;
    }

    @Override
    public void setTimezone(ZoneId timezone) {
        this.timezone = timezone;
    }

    @Override
    public List<ApplicationEntity> getApplications() {
        return this.applications;
    }

}
