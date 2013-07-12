/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy-core.
 * 
 * billy-core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * billy-core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.core.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.CustomerBuilder;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.services.entities.util.EntityFactory;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;

public class CustomerBuilderImpl<TBuilder extends CustomerBuilderImpl<TBuilder, TCustomer>, TCustomer extends Customer>
		extends AbstractBuilder<TBuilder, TCustomer> implements
		CustomerBuilder<TBuilder, TCustomer> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/core/i18n/FieldNames");

	protected DAOCustomer daoCustomer;
	protected DAOContact daoContact;

	@SuppressWarnings("unchecked")
	@Inject
	public CustomerBuilderImpl(DAOCustomer daoCustomer, DAOContact daoContact) {
		super((EntityFactory<? extends TCustomer>) daoCustomer);
		this.daoCustomer = daoCustomer;
		this.daoContact = daoContact;
	}

	@Override
	public TBuilder setName(String name) {
		BillyValidator.mandatory(name,
				CustomerBuilderImpl.LOCALIZER.getString("field.name"));
		this.getTypeInstance().setName(name);
		return this.getBuilder();
	}

	@Override
	public TBuilder setTaxRegistrationNumber(String number) {
		BillyValidator.mandatory(number,
				CustomerBuilderImpl.LOCALIZER.getString("field.tax_number"));
		this.getTypeInstance().setTaxRegistrationNumber(number);
		return this.getBuilder();
	}

	@Override
	public <T extends Address> TBuilder addAddress(
			Builder<T> addressBuilder, boolean mainAddress) {
		BillyValidator.notNull(addressBuilder,
				CustomerBuilderImpl.LOCALIZER.getString("field.address"));
		this.getTypeInstance().getAddresses().add(addressBuilder.build());
		return this.getBuilder();
	}

	@Override
	public <T extends Address> TBuilder setBillingAddress(
			Builder<T> addressBuilder) {
		BillyValidator.notNull(addressBuilder, CustomerBuilderImpl.LOCALIZER
				.getString("field.billing_address"));
		this.getTypeInstance().setBillingAddress((AddressEntity) addressBuilder.build());
		return this.getBuilder();
	}

	@Override
	public <T extends Address> TBuilder setShippingAddress(
			Builder<T> addressBuilder) {
		BillyValidator.notNull(addressBuilder, CustomerBuilderImpl.LOCALIZER
				.getString("field.shipping_address"));
		this.getTypeInstance().setShippingAddress((AddressEntity)addressBuilder.build());
		return this.getBuilder();
	}

	@Override
	public <T extends Contact> TBuilder addContact(
			Builder<T> contactBuilder) {
		BillyValidator.notNull(contactBuilder,
				CustomerBuilderImpl.LOCALIZER.getString("field.contact"));
		this.getTypeInstance().getContacts().add(contactBuilder.build());
		return this.getBuilder();
	}

	@Override
	public TBuilder setMainContactUID(UID contactUID) {
		BillyValidator.notNull(contactUID,
				CustomerBuilderImpl.LOCALIZER.getString("field.main_contact"));

		Contact c = null;
		for (Contact contact : this.getTypeInstance().getContacts()) {
			if (contact.getUID().equals(contactUID)) {
				this.getTypeInstance().setMainContact((ContactEntity) contact);
				c = contact;
				break;
			}
		}
		BillyValidator.found(c,
				CustomerBuilderImpl.LOCALIZER.getString("field.main_contact"));
		return this.getBuilder();
	}

	@Override
	public <T extends BankAccount> TBuilder addBankAccount(
			Builder<T> accountBuilder) {
		BillyValidator.notNull(accountBuilder,
				CustomerBuilderImpl.LOCALIZER.getString("field.bank_account"));
		this.getTypeInstance().getBankAccounts().add(accountBuilder.build());
		return this.getBuilder();
	}

	@Override
	public TBuilder setHasSelfBillingAgreement(boolean selfBiling) {
		this.getTypeInstance().setHasSelfBillingAgreement(selfBiling);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance()
			throws javax.validation.ValidationException {
		CustomerEntity c = this.getTypeInstance();
		BillyValidator.mandatory(c.getName(),
				CustomerBuilderImpl.LOCALIZER.getString("field.name"));
		BillyValidator.mandatory(c.getTaxRegistrationNumber(),
				CustomerBuilderImpl.LOCALIZER.getString("field.tax_number"));
		BillyValidator.notEmpty(c.getAddresses(),
				CustomerBuilderImpl.LOCALIZER.getString("field.addresses"));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected CustomerEntity getTypeInstance() {
		return (CustomerEntity) super.getTypeInstance();
	}

}
