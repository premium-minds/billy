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
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.Contact;

@Entity
@Inheritance
@DiscriminatorColumn(length = 255)
@DiscriminatorValue("JPACustomerEntity")
@Table(name = Config.TABLE_PREFIX + "CUSTOMER")
public class JPACustomerEntity extends JPABaseEntity implements CustomerEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "TAX_ID")
    protected String taxId;

    @OneToMany(targetEntity = JPAAddressEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE },
            mappedBy = "customer")
    protected List<JPAAddressEntity> addresses;

    @OneToOne(targetEntity = JPAAddressEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_ADDRESS", referencedColumnName = "ID")
    protected JPAAddressEntity mainAddress;

    @OneToOne(targetEntity = JPAAddressEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_BILLING_ADDRESS", referencedColumnName = "ID")
    protected JPAAddressEntity billingAddress;

    @OneToOne(targetEntity = JPAAddressEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_SHIPPING_ADDRESS", referencedColumnName = "ID")
    protected JPAAddressEntity shippingAddress;

    @OneToOne(targetEntity = JPAContactEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_CONTACT", referencedColumnName = "ID")
    protected JPAContactEntity mainContact;

    @OneToMany(targetEntity = JPAContactEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE },
            mappedBy = "customer")
    protected List<JPAContactEntity> contacts;

    @Column(name = "SELF_BILLING")
    protected Boolean selfBilling;

    @OneToMany(targetEntity = JPABankAccountEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_CUSTOMER")
    protected List<JPABankAccountEntity> bankAccounts;

    public JPACustomerEntity() {
        this.addresses = new ArrayList<>();
        this.contacts = new ArrayList<>();
        this.bankAccounts = new ArrayList<>();
        this.selfBilling = Boolean.FALSE;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTaxRegistrationNumber() {
        return this.taxId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Address getMainAddress() {
        return this.mainAddress;
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
    public boolean hasSelfBillingAgreement() {
        return this.selfBilling;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setTaxRegistrationNumber(String number) {
        this.taxId = number;
    }

    @Override
    public List<JPAAddressEntity> getAddresses() {
        return this.addresses;
    }

    @Override
    public <T extends AddressEntity> void setMainAddress(T address) {
        this.mainAddress = (JPAAddressEntity) address;
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
    public List<JPABankAccountEntity> getBankAccounts() {
        return this.bankAccounts;
    }

    @Override
    public void setHasSelfBillingAgreement(boolean selfBilling) {
        this.selfBilling = selfBilling;
    }

}
