/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-platypus.
 * 
 * billy-platypus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-platypus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-platypus.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.portugal.services.builders.impl;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.services.builders.impl.ContextBuilderImpl;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.services.builders.PTRegionContextBuilder;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.entities.impl.PTRegionContextImpl;

public class PTRegionContextBuilderImpl<TBuilder extends PTRegionContextBuilderImpl<TBuilder, TContext>, TContext extends PTRegionContext>
extends ContextBuilderImpl<TBuilder, TContext>
implements PTRegionContextBuilder<TBuilder, TContext> {

	protected DAOPTRegionContext daoPTRegionContext;
	
	@Inject
	public PTRegionContextBuilderImpl(
			DAOContext daoContext,
			DAOPTRegionContext daoPTRegionContext) {

		super(daoContext);
		Validate.notNull(daoPTRegionContext);
		this.daoPTRegionContext = daoPTRegionContext;
		this.context = daoPTRegionContext.getEntityInstance();
	}

	protected PTRegionContextImpl getContextImpl() {
		return (PTRegionContextImpl) this.context;
	}

	@Override
	public TBuilder setRegionCode(String regionCode) {
		Validate.notNull(regionCode);
		Validate.notBlank(regionCode);
		getContextImpl().setRegionCode(regionCode);
		return getBuilder();
	}
	
}
