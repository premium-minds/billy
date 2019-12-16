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

import java.util.ArrayList;
import java.util.List;

import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.services.entities.Contact;

public class MockCustomerEntity extends MockBaseEntity implements CustomerEntity {

    private static final long serialVersionUID = 1L;

    public String name;
    public String taxRegistrationNumber;
    public List<Address> addresses;
    public AddressEntity mainAddress;
    public AddressEntity billingAddress;
    public AddressEntity shippingAddress;
    public List<Contact> contacts;
    public ContactEntity mainContact;
    public List<BankAccount> bankAccounts;
    public boolean hasSelfBillingAgreement;

    public MockCustomerEntity() {
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
        return this.hasSelfBillingAgreement;
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
    public void setHasSelfBillingAgreement(boolean selfBiling) {
        this.hasSelfBillingAgreement = selfBiling;
    }

}
