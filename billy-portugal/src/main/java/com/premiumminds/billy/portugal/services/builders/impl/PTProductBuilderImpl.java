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
import com.premiumminds.billy.core.services.builders.impl.ProductBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.services.builders.PTProductBuilder;
import com.premiumminds.billy.portugal.services.entities.PTProduct;

public class PTProductBuilderImpl<TBuilder extends PTProductBuilderImpl<TBuilder, TProduct>, TProduct extends PTProduct>
		extends ProductBuilderImpl<TBuilder, TProduct> implements
		PTProductBuilder<TBuilder, TProduct> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/portugal/i18n/FieldNames_pt");

	@Inject
	public PTProductBuilderImpl(DAOPTProduct daoPTProduct, DAOPTTax daoPTTax) {
		super(daoPTProduct, daoPTTax);
	}

	@Override
	protected PTProductEntity getTypeInstance() {
		return (PTProductEntity) super.getTypeInstance();
	}

	@Override
	public TBuilder setNumberCode(String code) {
		BillyValidator.mandatory(code, PTProductBuilderImpl.LOCALIZER
				.getString("field.product_number_code"));
		this.getTypeInstance().setNumberCode(code);
		return this.getBuilder();
	}

	@Override
	public TBuilder setUnitOfMeasure(String unit) {
		BillyValidator.mandatory(unit, PTProductBuilderImpl.LOCALIZER
				.getString("field.unit_of_measure"));
		this.getTypeInstance().setUnitOfMeasure(unit);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		super.validateInstance();
		PTProduct p = this.getTypeInstance();
		BillyValidator.mandatory(p.getNumberCode(),
				PTProductBuilderImpl.LOCALIZER
						.getString("field.product_number_code"));
		BillyValidator.mandatory(p.getUnitOfMeasure(),
				PTProductBuilderImpl.LOCALIZER
						.getString("field.unit_of_measure"));
	}
}
