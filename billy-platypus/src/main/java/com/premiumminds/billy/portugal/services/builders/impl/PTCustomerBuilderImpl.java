/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.builders.impl.CustomerBuilderImpl;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTContact;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.services.builders.PTCustomerBuilder;
import com.premiumminds.billy.portugal.services.entities.PTCustomer;

public class PTCustomerBuilderImpl<TBuilder extends PTCustomerBuilderImpl<TBuilder, TCustomer>, TCustomer extends PTCustomer>
		extends CustomerBuilderImpl<TBuilder, TCustomer> implements
		PTCustomerBuilder<TBuilder, TCustomer> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/portugal/i18n/FieldNames");

	@Inject
	protected PTCustomerBuilderImpl(DAOPTCustomer daoPTCustomer,
			DAOPTContact daoPTContact) {
		super(daoPTCustomer, daoPTContact);
	}

	@Override
	protected PTCustomerEntity getTypeInstance() {
		return (PTCustomerEntity) super.getTypeInstance();
	}

	@Override
	public <T extends Address> TBuilder setBillingAddress(
			Builder<T> addressBuilder) {
		BillyValidator.mandatory(addressBuilder,
				LOCALIZER.getString("field.billing_address"));
		this.getTypeInstance().setBillingAddress(
				(AddressEntity) addressBuilder.build());
		return this.getBuilder();
	}

	@Override
	public TBuilder setHasSelfBillingAgreement(boolean selfBiling) {
		BillyValidator.mandatory(selfBiling,
				LOCALIZER.getString("field.self_billing_agreement"));
		this.getTypeInstance().setHasSelfBillingAgreement(selfBiling);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		PTCustomerEntity c = this.getTypeInstance();
		BillyValidator
				.mandatory(c.getName(), LOCALIZER.getString("field.name"));
		BillyValidator.mandatory(c.getTaxRegistrationNumber(),
				LOCALIZER.getString("field.tax_number"));
		BillyValidator.mandatory(c.getMainAddress(),
				LOCALIZER.getString("field.main_address"));
		BillyValidator.mandatory(c.getBillingAddress(),
				LOCALIZER.getString("field.billing_address"));
		BillyValidator.mandatory(c.hasSelfBillingAgreement(),
				LOCALIZER.getString("field.self_billing_agreement"));
		BillyValidator.notEmpty(c.getAddresses(),
				LOCALIZER.getString("field.addresses"));
	}
}
