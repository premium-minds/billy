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
import com.premiumminds.billy.core.services.builders.impl.TaxBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.services.builders.PTTaxBuilder;
import com.premiumminds.billy.portugal.services.entities.PTTax;
import com.premiumminds.billy.portugal.services.entities.PTTax.PTVATCode;

public class PTTaxBuilderImpl<TBuilder extends PTTaxBuilderImpl<TBuilder, TTax>, TTax extends PTTax>
		extends TaxBuilderImpl<TBuilder, TTax> implements
		PTTaxBuilder<TBuilder, TTax> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/portugal/i18n/FieldNames");

	@Inject
	public PTTaxBuilderImpl(DAOPTTax daoPTTax, DAOPTRegionContext daoPTContext) {
		super(daoPTTax, daoPTContext);
	}

	@Override
	public TBuilder setVATCode(PTVATCode code) {
		BillyValidator.mandatory(code, LOCALIZER.getString("field.VATCode"));
		this.getTypeInstance().setVATCode(code);
		return this.getBuilder();
	}

	@Override
	protected PTTaxEntity getTypeInstance() {
		return (PTTaxEntity) super.getTypeInstance();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		PTTaxEntity e = this.getTypeInstance();

		BillyValidator.mandatory(e.getVATCode(),
				LOCALIZER.getString("field.VATCode"));

		if (!((DAOPTTax) daoTax).getTaxes(
				(PTRegionContextEntity) e.getContext(), e.getValidFrom(),
				e.getValidTo(), e.getUID()).isEmpty())

			throw new BillyValidationException();
		super.validateInstance();
	}

}
