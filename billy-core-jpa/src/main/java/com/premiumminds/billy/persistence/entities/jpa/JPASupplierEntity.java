/*
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
package com.premiumminds.billy.persistence.entities.jpa;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.SupplierEntity;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Supplier;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "SUPPLIER")
@Inheritance(strategy = InheritanceType.JOINED)
public class JPASupplierEntity extends JPABaseEntity<Supplier> implements SupplierEntity {

    private static final long serialVersionUID = 1L;

    @OneToMany(targetEntity = JPAAddressEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = Config.TABLE_PREFIX + "SUPPLIER_ADDRESS",
            joinColumns = { @JoinColumn(name = "ID_SUPPLIER", referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "ID_ADDRESS", referencedColumnName = "ID", unique = true) })
    protected List<Address> addresses;

    @OneToMany(targetEntity = JPABankAccountEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = Config.TABLE_PREFIX + "SUPPLIER_BANK_ACCOUNT",
            joinColumns = { @JoinColumn(name = "ID_SUPPLIER", referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "ID_BANK_ACCOUNT", referencedColumnName = "ID", unique = true) })
    protected List<BankAccount> bankAccounts;

    @OneToOne(targetEntity = JPAAddressEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_BILLING_ADDRESS")
    protected Address billingAddress;

    @OneToMany(targetEntity = JPAContactEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = Config.TABLE_PREFIX + "SUPPLIER_CONTACT",
            joinColumns = { @JoinColumn(name = "ID_SUPPLIER", referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "ID_CONTACT", referencedColumnName = "ID", unique = true) })
    protected List<Contact> contacts;

    @OneToOne(targetEntity = JPAAddressEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_MAIN_ADDRESS")
    protected Address mainAddress;

    @OneToOne(targetEntity = JPAContactEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_MAIN_CONTACT")
    protected Contact mainContact;

    @Column(name = "NAME")
    protected String name;

    @OneToOne(targetEntity = JPAAddressEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_SHIPPING_ADDRESS")
    protected Address shippingAddress;

    @Column(name = "TAX_ID")
    protected String taxRegistrationNumber;

    @Column(name = "TAX_ID_COUNTRY")
    protected String taxRegistrationNumberISOCountryCode;

    @Column(name = "SELF_BILLING_AGREEMENT")
    protected Boolean selfBillingAgreement;

    public JPASupplierEntity() {
        this.addresses = new ArrayList<>();
        this.bankAccounts = new ArrayList<>();
        this.contacts = new ArrayList<>();
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
        return this.taxRegistrationNumberISOCountryCode;
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
    public List<BankAccount> getBankAccounts() {
        return this.bankAccounts;
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
        this.taxRegistrationNumberISOCountryCode = isoCountryCode;
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
    public void setSelfBillingAgreement(boolean selfBilling) {
        this.selfBillingAgreement = selfBilling;
    }

}
