/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.services.builders.impl.ContextBuilderImpl;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.entities.ESRegionContextEntity;
import com.premiumminds.billy.spain.services.builders.ESRegionContextBuilder;
import com.premiumminds.billy.spain.services.entities.ESRegionContext;

public class ESRegionContextBuilderImpl<TBuilder extends ESRegionContextBuilderImpl<TBuilder, TContext>, TContext extends ESRegionContext>
	extends ContextBuilderImpl<TBuilder, TContext> implements
	ESRegionContextBuilder<TBuilder, TContext> {

	protected static final Localizer	LOCALIZER	= new Localizer(
			"com/premiumminds/billy/core/i18n/FieldNames");

	@Inject
	public ESRegionContextBuilderImpl(DAOESRegionContext daoESContext) {
		super(daoESContext);
	}

	@Override
	protected ESRegionContextEntity getTypeInstance() {
		return (ESRegionContextEntity) super.getTypeInstance();
	}
}
