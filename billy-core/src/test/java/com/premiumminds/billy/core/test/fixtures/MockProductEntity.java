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
