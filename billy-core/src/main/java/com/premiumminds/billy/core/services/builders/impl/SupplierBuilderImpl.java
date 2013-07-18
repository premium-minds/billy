/**
 * Copyright (C) 2013 Premium Minds.
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
package com.premiumminds.billy.core.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.dao.DAOSupplier;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.SupplierEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.builders.SupplierBuilder;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Supplier;
import com.premiumminds.billy.core.services.entities.util.EntityFactory;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;

public class SupplierBuilderImpl<TBuilder extends SupplierBuilderImpl<TBuilder, TSupplier>, TSupplier extends Supplier>
		extends AbstractBuilder<TBuilder, TSupplier> implements
		SupplierBuilder<TBuilder, TSupplier> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/core/i18n/FieldNames");

	protected DAOSupplier daoSupplier;

	@SuppressWarnings("unchecked")
	@Inject
	public SupplierBuilderImpl(DAOSupplier daoSupplier) {
		super((EntityFactory<? extends TSupplier>) daoSupplier);
	}

	@Override
	public TBuilder setName(String name) {
		BillyValidator.mandatory(name,
				SupplierBuilderImpl.LOCALIZER.getString("field.name"));
		this.getTypeInstance().setName(name);
		return this.getBuilder();
	}

	@Override
	public TBuilder setTaxRegistrationNumber(String number) {
		this.getTypeInstance().setTaxRegistrationNumber(number);
		return this.getBuilder();
	}

	@Override
	public <T extends Address> TBuilder addAddress(Builder<T> addressBuilder) {
		this.getTypeInstance().getAddresses().add(addressBuilder.build());
		return this.getBuilder();
	}

	@Override
	public TBuilder setMainAddress(Builder<Address> addressBuilder) {
		BillyValidator.mandatory(addressBuilder,
				SupplierBuilderImpl.LOCALIZER.getString("field.address"));
		this.getTypeInstance().setMainAddress(
				(AddressEntity) addressBuilder.build());
		return this.getBuilder();
	}

	@Override
	public <T extends Address> TBuilder setBillingAddress(
			Builder<T> addressBuilder) {
		this.getTypeInstance().setBillingAddress(
				(AddressEntity) addressBuilder.build());
		return this.getBuilder();
	}

	@Override
	public <T extends Address> TBuilder setShippingAddress(
			Builder<T> addressBuilder) {
		this.getTypeInstance().setShippingAddress(
				(AddressEntity) addressBuilder.build());
		return this.getBuilder();
	}

	@Override
	public <T extends Contact> TBuilder addContact(Builder<T> contactBuilder) {
		this.getTypeInstance().getContacts().add(contactBuilder.build());
		return this.getBuilder();
	}

	@Override
	public <T extends Contact> TBuilder setMainContact(Builder<T> contactBuilder) {
		this.getTypeInstance().setMainContact(
				(ContactEntity) contactBuilder.build());
		return this.getBuilder();
	}

	@Override
	public <T extends BankAccount> TBuilder addBankAccount(
			Builder<T> accountBuilder) {
		this.getTypeInstance().getBankAccounts().add(accountBuilder.build());
		return this.getBuilder();
	}

	@Override
	public TBuilder setSelfBillingAgreement(boolean selfBilling) {
		this.getTypeInstance().setSelfBillingAgreement(selfBilling);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		Supplier s = this.getTypeInstance();
		BillyValidator.mandatory(s.getName(),
				SupplierBuilderImpl.LOCALIZER.getString("field.name"));
		BillyValidator.mandatory(s.getMainAddress(),
				SupplierBuilderImpl.LOCALIZER.getString("field.address"));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected SupplierEntity getTypeInstance() {
		return (SupplierEntity) super.getTypeInstance();
	}

}
