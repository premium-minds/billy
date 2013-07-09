/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-core.
 * 
 * billy-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.core.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.BankAccountEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.CustomerBuilder;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.services.entities.util.EntityFactory;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;

public class CustomerBuilderImpl<TBuilder extends CustomerBuilderImpl<TBuilder, TCustomer>, TCustomer extends Customer>
extends AbstractBuilder<TBuilder, TCustomer>
implements CustomerBuilder<TBuilder, TCustomer> {

	protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");
	
	protected DAOCustomer daoCustomer;
	protected DAOContact daoContact;
	
	@SuppressWarnings("unchecked")
	@Inject
	public CustomerBuilderImpl(
			DAOCustomer daoCustomer,
			DAOContact daoContact) {
		super((EntityFactory<? extends TCustomer>) daoCustomer);
		this.daoCustomer = daoCustomer;
		this.daoContact = daoContact;
	}

	@Override
	public TBuilder setName(String name) {
		BillyValidator.mandatory(name, LOCALIZER.getString("field.name"));
		getTypeInstance().setName(name);
		return getBuilder();
	}

	@Override
	public TBuilder setTaxRegistrationNumber(String number) {
		BillyValidator.mandatory(number, LOCALIZER.getString("field.tax_number"));
		getTypeInstance().setTaxRegistrationNumber(number);
		return getBuilder();
	}

	@Override
	public <T extends AddressEntity> TBuilder addAddress(Builder<T> addressBuilder,
			boolean mainAddress) {
		BillyValidator.notNull(addressBuilder, LOCALIZER.getString("field.address"));
		getTypeInstance().getAddresses().add(addressBuilder.build());
		return getBuilder();
	}

	@Override
	public <T extends AddressEntity> TBuilder setBillingAddress(Builder<T> addressBuilder) {
		BillyValidator.notNull(addressBuilder, LOCALIZER.getString("field.billing_address"));
		getTypeInstance().setBillingAddress(addressBuilder.build());
		return getBuilder();
	}

	@Override
	public <T extends AddressEntity> TBuilder setShippingAddress(Builder<T> addressBuilder) {
		BillyValidator.notNull(addressBuilder, LOCALIZER.getString("field.shipping_address"));
		getTypeInstance().setShippingAddress(addressBuilder.build());
		return getBuilder();
	}

	@Override
	public <T extends ContactEntity> TBuilder addContact(Builder<T> contactBuilder) {
		BillyValidator.notNull(contactBuilder, LOCALIZER.getString("field.contact"));
		getTypeInstance().getContacts().add(contactBuilder.build());
		return getBuilder();
	}

	@Override
	public TBuilder setMainContactUID(UID contactUID) {
		BillyValidator.notNull(contactUID, LOCALIZER.getString("field.main_contact"));
		
		Contact c = null;
		for(Contact contact : getTypeInstance().getContacts()) {
			if(contact.getUID().equals(contactUID)) {
				getTypeInstance().setMainContact((ContactEntity) contact);
				c = contact;
				break;
			}
		}
		BillyValidator.found(c, LOCALIZER.getString("field.main_contact"));
		return getBuilder();
	}

	@Override
	public <T extends BankAccountEntity> TBuilder addBankAccount(Builder<T> accountBuilder) {
		BillyValidator.notNull(accountBuilder, LOCALIZER.getString("field.bank_account"));
		getTypeInstance().getBankAccounts().add(accountBuilder.build());
		return getBuilder();
	}

	@Override
	public TBuilder setHasSelfBillingAgreement(boolean selfBiling) {
		getTypeInstance().setHasSelfBillingAgreement(selfBiling);
		return getBuilder();
	}

	@Override
	protected void validateInstance()
			throws javax.validation.ValidationException {
		CustomerEntity c = getTypeInstance();
		BillyValidator.mandatory(c.getName(), LOCALIZER.getString("field.name"));
		BillyValidator.mandatory(c.getTaxRegistrationNumber(), LOCALIZER.getString("field.tax_number"));
		BillyValidator.notEmpty(c.getAddresses(), LOCALIZER.getString("field.addresses"));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected CustomerEntity getTypeInstance() {
		return (CustomerEntity) super.getTypeInstance();
	}
	
}
