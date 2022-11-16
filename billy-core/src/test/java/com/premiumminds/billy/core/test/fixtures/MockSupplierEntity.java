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

import com.premiumminds.billy.core.services.entities.Supplier;
import java.util.ArrayList;
import java.util.List;

import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.SupplierEntity;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.services.entities.Contact;

public class MockSupplierEntity extends MockBaseEntity<Supplier> implements SupplierEntity {

    private static final long serialVersionUID = 1L;

    public String name;
    public String taxRegistrationNumber;
    public String taxRegistrationNumberCountry;
    public Address mainAddress;
    public Address billingAddress;
    public Address shippingAddress;
    public Contact mainContact;
    public boolean selfBillingAgreement;
    public List<Address> addresses;
    public List<Contact> contacts;
    public List<BankAccount> bankAccounts;

    public MockSupplierEntity() {
        this.addresses = new ArrayList<>();
        this.contacts = new ArrayList<>();
        this.bankAccounts = new ArrayList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTaxRegistrationNumber() {
        return this.taxRegistrationNumber;
    }

    @Override
    public String getTaxRegistrationNumberISOCountryCode() {
        return this.taxRegistrationNumberCountry;
    }

    @Override
    public Address getMainAddress() {
        return this.mainAddress;
    }

    @Override
    public Address getBillingAddress() {
        return this.billingAddress;
    }

    @Override
    public Address getShippingAddress() {
        return this.shippingAddress;
    }

    @Override
    public Contact getMainContact() {
        return this.mainContact;
    }

    @Override
    public boolean hasSelfBillingAgreement() {
        return this.selfBillingAgreement;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setTaxRegistrationNumber(String number) {
        this.taxRegistrationNumber = number;
    }

    @Override
    public void setTaxRegistrationNumberISOCountryCode(final String isoCountryCode) {
        this.taxRegistrationNumberCountry = isoCountryCode;
    }

    @Override
    public List<Address> getAddresses() {
        return this.addresses;
    }

    @Override
    public <T extends AddressEntity> void setMainAddress(T address) {
        this.mainAddress = address;
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
    public List<Contact> getContacts() {
        return this.contacts;
    }

    @Override
    public <T extends ContactEntity> void setMainContact(T contact) {
        this.mainContact = contact;
    }

    @Override
    public List<BankAccount> getBankAccounts() {
        return this.bankAccounts;
    }

    @Override
    public void setSelfBillingAgreement(boolean selfBilling) {
        this.selfBillingAgreement = selfBilling;
    }

}
