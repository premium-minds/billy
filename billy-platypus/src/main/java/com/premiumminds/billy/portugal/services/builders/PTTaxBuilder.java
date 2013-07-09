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
package com.premiumminds.billy.portugal.services.builders;

import javax.validation.constraints.NotNull;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.TaxBuilder;
import com.premiumminds.billy.portugal.persistence.entities.IPTTaxEntity.PTVatCode;
import com.premiumminds.billy.portugal.services.entities.PTTax;

public interface PTTaxBuilder<TBuilder extends PTTaxBuilder<TBuilder, TTax>, TTax extends PTTax> extends TaxBuilder<TBuilder, TTax> {

	public TBuilder setVatCode(@NotNull PTVatCode vatCode);
	
	public TBuilder setRegionContextUID(@NotNull UID regionContextUID);
	
}
