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

import com.premiumminds.billy.core.services.builders.impl.BusinessBuilderImpl;
import com.premiumminds.billy.portugal.services.builders.PTBusinessBuilder;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import com.premiumminds.billy.portugal.services.entities.impl.PTBusinessImpl;

public class PTBusinessBuilderImpl<TBuilder extends PTBusinessBuilderImpl<TBuilder, TBusiness>, TBusiness extends PTBusiness>
extends BusinessBuilderImpl<TBuilder, TBusiness>
implements PTBusinessBuilder<TBuilder, TBusiness> {

	public PTBusinessBuilderImpl() {
		this.business = new PTBusinessImpl();
	}
	
	protected PTBusinessImpl getBusinessImpl() {
		return (PTBusinessImpl) this.business;
	}
	
	@Override
	public TBuilder setCentralCommercialRegistryName(String registryName) {
		getBusinessImpl().setCentralCommercialRegistryName(registryName);
		return getBuilder();
	}

	@Override
	public TBuilder setCommercialRegistryNumber(String number) {
		getBusinessImpl().setCommercialRegistryNumber(number);
		return getBuilder();
	}
	
}
