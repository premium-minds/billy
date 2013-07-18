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
import com.premiumminds.billy.core.services.builders.impl.ProductBuilderImpl;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.services.builders.PTProductBuilder;
import com.premiumminds.billy.portugal.services.entities.PTProduct;

public class PTProductBuilderImpl<TBuilder extends PTProductBuilderImpl<TBuilder, TProduct>, TProduct extends PTProduct>
		extends ProductBuilderImpl<TBuilder, TProduct> implements
		PTProductBuilder<TBuilder, TProduct> {

	@Inject
	public PTProductBuilderImpl(DAOPTProduct daoPTProduct, DAOPTTax daoPTTax) {
		super(daoPTProduct, daoPTTax);
	}

	@Override
	protected PTProductEntity getTypeInstance() {
		return (PTProductEntity) super.getTypeInstance();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		super.validateInstance();
	}

}
