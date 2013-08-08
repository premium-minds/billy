/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.services.builders.impl.BusinessBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.services.builders.PTBusinessBuilder;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;

public class PTBusinessBuilderImpl<TBuilder extends PTBusinessBuilderImpl<TBuilder, TBusiness>, TBusiness extends PTBusiness>
		extends BusinessBuilderImpl<TBuilder, TBusiness> implements
		PTBusinessBuilder<TBuilder, TBusiness> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/portugal/i18n/FieldNames_pt");

	@Inject
	public PTBusinessBuilderImpl(DAOPTBusiness daoBusiness,
			DAOPTRegionContext daoContext) {
		super(daoBusiness, daoContext);
	}

	@Override
	protected PTBusinessEntity getTypeInstance() {
		return (PTBusinessEntity) super.getTypeInstance();
	}

	@Override
	public TBuilder setCommercialName(String name) {
		BillyValidator.notBlank(name, PTBusinessBuilderImpl.LOCALIZER
				.getString("field.commercial_name"));
		this.getTypeInstance().setCommercialName(name);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		BusinessEntity b = this.getTypeInstance();
		BillyValidator.mandatory(b.getOperationalContext(),
				"field.business_context");
		BillyValidator
				.mandatory(b.getFinancialID(), PTBusinessBuilderImpl.LOCALIZER
						.getString("field.financial_id"));
		BillyValidator.mandatory(b.getName(), PTBusinessBuilderImpl.LOCALIZER
				.getString("field.business_name"));
		BillyValidator.mandatory(b.getAddress(),
				PTBusinessBuilderImpl.LOCALIZER
						.getString("field.business_address"));
		BillyValidator.mandatory(b.getBillingAddress(),
				PTBusinessBuilderImpl.LOCALIZER
						.getString("field.business_billing_address"));
		BillyValidator.notEmpty(b.getContacts(),
				PTBusinessBuilderImpl.LOCALIZER
						.getString("field.business_contact"));
		BillyValidator.notEmpty(b.getApplications(),
				PTBusinessBuilderImpl.LOCALIZER
						.getString("field.business_application"));
	}
}
