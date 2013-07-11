package com.premiumminds.billy.core.test.fixtures;

import java.util.ArrayList;
import java.util.List;

import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.SupplierEntity;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.services.entities.Contact;

public class MockSupplierEntity extends MockBaseEntity implements
		SupplierEntity {

	private String name;
	private String taxRegistrationNumber;
	private Address mainAddress;
	private Address billingAddress;
	private Address shippingAddress;
	private Contact mainContact;
	private boolean selfBillingAgreement;
	private List<Address> addresses;
	private List<Contact> contacts;
	private List<BankAccount> bankAccounts;

	public MockSupplierEntity() {
		this.addresses = new ArrayList<Address>();
		this.contacts = new ArrayList<Contact>();
		this.bankAccounts = new ArrayList<BankAccount>();
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
