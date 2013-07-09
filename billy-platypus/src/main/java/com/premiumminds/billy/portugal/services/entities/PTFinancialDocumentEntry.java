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
package com.premiumminds.billy.portugal.services.entities;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.FinancialDocumentEntry;
import com.premiumminds.billy.portugal.services.builders.impl.PTFinancialDocumentEntryBuilderImpl;

public interface PTFinancialDocumentEntry extends FinancialDocumentEntry {

	public static class Builder extends PTFinancialDocumentEntryBuilderImpl<Builder, PTFinancialDocumentEntry> {
		public static Builder create() {
			return new Builder();
		}
	}
	
	public UID getReferencedDocumentUID();

	public String getExemptionLegalMotiveDescription();
	
	@NotNull
	@Override
	public UID getProductUID();
	
	@NotNull
	@Override
	public BigDecimal getProductQuantity();
	
}
