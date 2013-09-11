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
import com.premiumminds.billy.core.services.builders.impl.ContextBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.services.builders.PTRegionContextBuilder;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;

public class PTRegionContextBuilderImpl<TBuilder extends PTRegionContextBuilderImpl<TBuilder, TContext>, TContext extends PTRegionContext>
	extends ContextBuilderImpl<TBuilder, TContext> implements
	PTRegionContextBuilder<TBuilder, TContext> {

	protected static final Localizer	LOCALIZER	= new Localizer(
			"com/premiumminds/billy/core/i18n/FieldNames");

	@Inject
	public PTRegionContextBuilderImpl(DAOPTRegionContext daoPTContext) {
		super(daoPTContext);
	}

	@Override
	public TBuilder setRegionCode(String regionCode) {
		BillyValidator.mandatory(regionCode,
				PTRegionContextBuilderImpl.LOCALIZER
						.getString("field.region_code"));
		this.getTypeInstance().setRegionCode(regionCode);
		return this.getBuilder();
	}

	@Override
	protected PTRegionContextEntity getTypeInstance() {
		return (PTRegionContextEntity) super.getTypeInstance();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		super.validateInstance();
		PTRegionContextEntity c = this.getTypeInstance();
		BillyValidator.mandatory(c.getRegionCode(),
				PTRegionContextBuilderImpl.LOCALIZER
						.getString("field.region_code"));
	}

}
