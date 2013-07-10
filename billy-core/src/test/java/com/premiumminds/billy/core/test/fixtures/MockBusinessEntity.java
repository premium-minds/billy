package com.premiumminds.billy.core.test.fixtures;

import java.util.ArrayList;
import java.util.List;

import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ApplicationEntity;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Context;

public class MockBusinessEntity extends MockBaseEntity implements BusinessEntity {
	private static final long serialVersionUID = 1L;

	public ContextEntity operationalContext;
	public String taxId;
	public String name;
	public String commercialName;
	public AddressEntity address;
	public AddressEntity billingAddress;
	public AddressEntity shippingAddress;
	public ContactEntity mainContact;
	public List<ContactEntity> contacts;
	public String website;
	public List<ApplicationEntity> applications;

	public MockBusinessEntity() {
		contacts = new ArrayList<ContactEntity>();
		applications = new ArrayList<ApplicationEntity>();
	}
	
	@Override
	public Context getOperationalContext() {
		return operationalContext;
	}

	@Override
	public String getFinancialID() {
		return taxId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getCommercialName() {
		return commercialName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Address getAddress() {
		return address;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Address getBillingAddress() {
		return billingAddress;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Address getShippingAddress() {
		return shippingAddress;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Contact getMainContact() {
		return mainContact;
	}

	@Override
	public String getWebsiteAddress() {
		return website;
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
	public List<ContactEntity> getContacts() {
		return contacts;
	}

	@Override
	public <T extends ContactEntity> void setMainContact(T contact) {
		this.mainContact = contact;
	}

	@Override
	public List<ApplicationEntity> getApplications() {
		return this.applications;
	}

}
