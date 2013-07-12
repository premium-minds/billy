/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.test.fixtures;

import java.util.ArrayList;
import java.util.List;

import com.premiumminds.billy.core.persistence.entities.ProductEntity;
import com.premiumminds.billy.core.services.entities.Tax;

public class MockProductEntity extends MockBaseEntity implements ProductEntity {

	private static final long serialVersionUID = 1L;

	public String productCode;
	public String productGroup;
	public String unitOfMeasure;
	public String valuationMethod;
	public String numberCode;
	public String comodityCode;
	public ProductType type;
	public String description;
	public List<Tax> taxes;

	public MockProductEntity() {
		this.taxes = new ArrayList<Tax>();
	}

	@Override
	public String getProductCode() {
		return this.productCode;
	}

	@Override
	public String getProductGroup() {
		return this.productGroup;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public ProductType getType() {
		return this.type;
	}

	@Override
	public String getCommodityCode() {
		return this.comodityCode;
	}

	@Override
	public String getNumberCode() {
		return this.numberCode;
	}

	@Override
	public String getValuationMethod() {
		return this.valuationMethod;
	}

	@Override
	public String getUnitOfMeasure() {
		return this.unitOfMeasure;
	}

	@Override
	public void setProductCode(String code) {
		this.productCode = code;

	}

	@Override
	public void setProductGroup(String group) {
		this.productGroup = group;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setType(ProductType type) {
		this.type = type;
	}

	@Override
	public void setCommodityCode(String code) {
		this.comodityCode = code;
	}

	@Override
	public void setNumberCode(String code) {
		this.numberCode = code;
	}

	@Override
	public void setValuationMethod(String method) {
		this.valuationMethod = method;
	}

	@Override
	public void setUnitOfMeasure(String unit) {
		this.unitOfMeasure = unit;
	}

	@Override
	public List<Tax> getTaxes() {
		return this.taxes;
	}

}
