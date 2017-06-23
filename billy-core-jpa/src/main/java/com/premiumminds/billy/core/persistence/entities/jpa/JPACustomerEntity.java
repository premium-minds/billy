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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.services.entities.Contact;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "CUSTOMER")
public class JPACustomerEntity extends JPABaseEntity implements CustomerEntity {

  private static final long serialVersionUID = 1L;

  @Column(name = "NAME")
  protected String name;

  @Column(name = "TAX_ID")
  protected String taxId;

  @OneToMany(targetEntity = JPAAddressEntity.class, cascade = { CascadeType.PERSIST,
      CascadeType.MERGE })
  @JoinTable(name = Config.TABLE_PREFIX + "CUSTOMER_ADDRESS", joinColumns = {
      @JoinColumn(name = "ID_CUSTOMER", referencedColumnName = "ID") }, inverseJoinColumns = {
          @JoinColumn(name = "ID_ADDRESS", referencedColumnName = "ID", unique = true) })
  protected List<Address> addresses;

  @OneToOne(targetEntity = JPAAddressEntity.class, cascade = { CascadeType.PERSIST,
      CascadeType.MERGE })
  @JoinColumn(name = "ID_ADDRESS", referencedColumnName = "ID")
  protected Address mainAddress;

  @OneToOne(targetEntity = JPAAddressEntity.class, cascade = { CascadeType.PERSIST,
      CascadeType.MERGE })
  @JoinColumn(name = "ID_BILLING_ADDRESS", referencedColumnName = "ID")
  protected Address billingAddress;

  @OneToOne(targetEntity = JPAAddressEntity.class, cascade = { CascadeType.PERSIST,
      CascadeType.MERGE })
  @JoinColumn(name = "ID_SHIPPING_ADDRESS", referencedColumnName = "ID")
  protected Address shippingAddress;

  @OneToOne(targetEntity = JPAContactEntity.class, cascade = { CascadeType.PERSIST,
      CascadeType.MERGE })
  @JoinColumn(name = "ID_CONTACT", referencedColumnName = "ID")
  protected Contact mainContact;

  @OneToMany(targetEntity = JPAContactEntity.class, cascade = { CascadeType.PERSIST,
      CascadeType.MERGE })
  @JoinTable(name = Config.TABLE_PREFIX + "CUSTOMER_CONTACT", joinColumns = {
      @JoinColumn(name = "ID_CUSTOMER", referencedColumnName = "ID") }, inverseJoinColumns = {
          @JoinColumn(name = "ID_CONTACT", referencedColumnName = "ID", unique = true) })
  protected List<Contact> contacts;

  @Column(name = "SELF_BILLING")
  protected Boolean selfBilling;

  @OneToMany(targetEntity = JPABankAccountEntity.class, cascade = { CascadeType.PERSIST,
      CascadeType.MERGE })
  @JoinTable(name = Config.TABLE_PREFIX + "CUSTOMER_BANK_ACCOUNT", joinColumns = {
      @JoinColumn(name = "ID_CUSTOMER", referencedColumnName = "ID") }, inverseJoinColumns = {
          @JoinColumn(name = "ID_BANK_ACCOUNT", referencedColumnName = "ID", unique = true) })
  protected List<BankAccount> bankAccounts;

  public JPACustomerEntity() {
    this.addresses = new ArrayList<Address>();
    this.contacts = new ArrayList<Contact>();
    this.bankAccounts = new ArrayList<BankAccount>();
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
  public void setHasSelfBillingAgreement(boolean selfBilling) {
    this.selfBilling = selfBilling;
  }

}
