package com.premiumminds.billy.portugal.test.fixtures;

import java.util.ArrayList;
import java.util.List;

import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.Product.ProductType;
import com.premiumminds.billy.core.test.fixtures.MockBaseEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;


public class MockPTProductEntity extends MockBaseEntity implements
		PTProductEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String productCode;
	public String productGroup;
	public String unitOfMeasure;
	public String numberCode;
	public ProductType type;
	public String description;
	public List<Tax> taxes;

	public MockPTProductEntity() {
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
		return null;
	}

	@Override
	public String getNumberCode() {
		return this.numberCode;
	}

	@Override
	public String getValuationMethod() {
		return null;
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
		
	}

	@Override
	public void setNumberCode(String code) {
		this.numberCode = code;
	}

	@Override
	public void setValuationMethod(String method) {
		
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
