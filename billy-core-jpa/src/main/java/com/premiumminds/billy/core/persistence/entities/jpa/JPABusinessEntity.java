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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Context;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "BUSINESS")
public class JPABusinessEntity extends JPABaseEntity implements BusinessEntity {

	private static final long	serialVersionUID	= 1L;

	@ManyToOne(targetEntity = JPAContextEntity.class)
	@JoinColumn(name = "ID_OPERATIONAL_CONTEXT", referencedColumnName = "ID")
	protected Context			operationalContext;

	@Column(name = "TAX_ID")
	protected String			taxId;

	@Column(name = "NAME")
	protected String			name;

	@Column(name = "COMMERCIAL_NAME")
	protected String			commercialName;

	@OneToOne(fetch = FetchType.EAGER, targetEntity = JPAAddressEntity.class,
				cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "ID_ADDRESS", referencedColumnName = "ID")
	protected Address			address;

	@OneToOne(fetch = FetchType.EAGER, targetEntity = JPAAddressEntity.class,
				cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "ID_BILLING_ADDRESS", referencedColumnName = "ID")
	protected Address			billingAddress;

	@OneToOne(fetch = FetchType.EAGER, targetEntity = JPAAddressEntity.class,
				cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "ID_SHIPPING_ADDRESS", referencedColumnName = "ID")
	protected Address			shippingAddress;

	@OneToOne(fetch = FetchType.EAGER, targetEntity = JPAContactEntity.class,
				cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "ID_MAIN_CONTACT", referencedColumnName = "ID")
	protected Contact			mainContact;

	@OneToMany(targetEntity = JPAContactEntity.class, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
				name = Config.TABLE_PREFIX + "BUSINESS_CONTACT",
				joinColumns = { @JoinColumn(name = "ID_BUSINESS",
											referencedColumnName = "ID") },
				inverseJoinColumns = { @JoinColumn(
													name = "ID_CONTACT",
													referencedColumnName = "ID",
													unique = true) })
	protected List<Contact>		contacts;

	@Column(name = "WEBSITE")
	protected String			website;

	@OneToMany(targetEntity = JPAApplicationEntity.class, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
				name = Config.TABLE_PREFIX + "BUSINESS_APPLICATION",
				joinColumns = { @JoinColumn(name = "ID_BUSINESS",
											referencedColumnName = "ID") },
				inverseJoinColumns = { @JoinColumn(
													name = "ID_APPLICATION",
													referencedColumnName = "ID",
													unique = true) })
	protected List<Application>	applications;

	public JPABusinessEntity() {
		this.contacts = new ArrayList<Contact>();
		this.applications = new ArrayList<Application>();
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
	public void setWebsiteAddress(String address) {
		this.website = address;
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
	public List<Contact> getContacts() {
		return this.contacts;
	}

	@Override
	public <T extends ContactEntity> void setMainContact(T contact) {
		this.mainContact = contact;
	}

	@Override
	public List<Application> getApplications() {
		return this.applications;
	}

}
