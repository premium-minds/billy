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
package com.premiumminds.billy.core.persistence.entities;

import java.util.List;

import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.core.services.entities.Tax;

public interface ProductEntity extends Product, BaseEntity {

	public void setProductCode(String code);

	public void setProductGroup(String group);

	public void setDescription(String description);

	public void setType(ProductType type);

	public void setCommodityCode(String code);

	public void setNumberCode(String code);

	public void setValuationMethod(String method);

	public void setUnitOfMeasure(String unit);

	@Override
	public <T extends Tax> List<T> getTaxes();
	
}
