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

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.impl.TaxBuilderImpl;
import com.premiumminds.billy.portugal.persistence.entities.IPTTaxEntity.PTVatCode;
import com.premiumminds.billy.portugal.services.builders.PTTaxBuilder;
import com.premiumminds.billy.portugal.services.entities.PTTax;
import com.premiumminds.billy.portugal.services.entities.impl.PTTaxImpl;

public class PTTaxBuilderImpl<TBuilder extends PTTaxBuilderImpl<TBuilder, TTax>, TTax extends PTTax>
extends TaxBuilderImpl<TBuilder, TTax>
implements PTTaxBuilder<TBuilder, TTax> {

	public PTTaxBuilderImpl() {
		this.tax = new PTTaxImpl();
	}
	
	protected PTTaxImpl getTaxImpl() {
		return (PTTaxImpl) this.tax;
	}

	@Override
	public TBuilder setVatCode(PTVatCode vatCode) {
		getTaxImpl().setPTVatCode(vatCode);
		return getBuilder();
	}

	@Override
	public TBuilder setRegionContextUID(UID regionContextUID) {
		getTaxImpl().setRegionContextUID(regionContextUID);
		return getBuilder();
	}
	
}
