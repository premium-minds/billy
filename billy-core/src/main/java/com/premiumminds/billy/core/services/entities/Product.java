/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-core.
 * 
 * billy-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.core.services.entities;

import java.util.Collection;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.services.builders.impl.ProductBuilderImpl;

public interface Product extends Entity {
	
	public static class Builder extends ProductBuilderImpl<Builder, Product> {
		@Inject
		public Builder(DAOProduct daoProduct) {
			super(daoProduct);
		}
		
	}
	
	public enum ProductType {
		GOODS,
		SERVICE,
		OTHER,
		CHARGE //Taxes, tax rates and parafiscal charges
	}
	
	public String getProductCode();
	
	public String getProductGroup();
	
	public String getDescription();
	
	public ProductType getType();

	public String getCommodityCode();
	
	public String getNumberCode();

	public String getValuationMethod();
	
	public String getUnitOfMeasure();
	
	public <T extends Tax> Collection<T> getTaxes();
	
}
