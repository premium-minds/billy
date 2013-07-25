/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy platypus (PT Pack).
 * 
 * billy platypus (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy platypus (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.builders.impl.SupplierBuilderImpl;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTSupplierEntity;
import com.premiumminds.billy.portugal.services.builders.PTSupplierBuilder;
import com.premiumminds.billy.portugal.services.entities.PTSupplier;

public class PTSupplierBuilderImpl<TBuilder extends PTSupplierBuilderImpl<TBuilder, TSupplier>, TSupplier extends PTSupplier>
		extends SupplierBuilderImpl<TBuilder, TSupplier> implements
		PTSupplierBuilder<TBuilder, TSupplier> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/portugal/i18n/FieldNames");

	@Inject
	public PTSupplierBuilderImpl(DAOPTSupplier daoPTSupplier) {
		super(daoPTSupplier);
	}

	@Override
	protected PTSupplierEntity getTypeInstance() {
		return (PTSupplierEntity) super.getTypeInstance();
	}

	@Override
	public TBuilder setTaxRegistrationNumber(String number) {
		BillyValidator.mandatory(number,
				PTSupplierBuilderImpl.LOCALIZER.getString("field.tax_id"));
		this.getTypeInstance().setTaxRegistrationNumber(number);
		return this.getBuilder();
	}

	@Override
	public <T extends Address> TBuilder setBillingAddress(
			Builder<T> addressBuilder) {
		BillyValidator.mandatory(addressBuilder,
				PTSupplierBuilderImpl.LOCALIZER
						.getString("field.billing_address"));
		this.getTypeInstance().setBillingAddress(
				(AddressEntity) addressBuilder.build());
		return this.getBuilder();
	}

	@Override
	public TBuilder setSelfBillingAgreement(boolean selfBilling) {
		BillyValidator.mandatory(selfBilling, PTSupplierBuilderImpl.LOCALIZER
				.getString("field.self_billing_agreement"));
		this.getTypeInstance().setSelfBillingAgreement(selfBilling);
		return this.getBuilder();
	}

	@Deprecated
	@NotImplemented
	public TBuilder setAccountID(String accountID) {
		// TODO Accounting support
		return null;
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		super.validateInstance();
		PTSupplier s = this.getTypeInstance();
		BillyValidator.mandatory(s.getTaxRegistrationNumber(),
				PTSupplierBuilderImpl.LOCALIZER.getString("field.tax_id"));
		BillyValidator.mandatory(s.getBillingAddress(),
				PTSupplierBuilderImpl.LOCALIZER
						.getString("field.billing_address"));
		BillyValidator.mandatory(s.hasSelfBillingAgreement(),
				PTSupplierBuilderImpl.LOCALIZER
						.getString("field.self_billing_agreement"));
	}
}
