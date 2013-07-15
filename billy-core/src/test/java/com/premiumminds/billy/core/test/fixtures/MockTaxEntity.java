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

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.services.entities.Context;

public class MockTaxEntity extends MockBaseEntity implements TaxEntity {

	private static final long serialVersionUID = 1L;

	public ContextEntity context;
	public String designation;
	public String description;
	public String code;
	public BigDecimal value;
	public Date validFrom;
	public Date validTo;
	public TaxRateType taxRateType;
	public BigDecimal percentageRateValue;
	public BigDecimal flatRateAmount;
	public Currency currency;

	public MockTaxEntity() {

	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public String getDesignation() {
		return designation;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public BigDecimal getValue() {
		return value;
	}

	@Override
	public Date getValidFrom() {
		return validFrom;
	}

	@Override
	public Date getValidTo() {
		return validTo;
	}

	@Override
	public TaxRateType getTaxRateType() {
		return taxRateType;
	}

	@Override
	public BigDecimal getPercentageRateValue() {
		return percentageRateValue;
	}

	@Override
	public BigDecimal getFlatRateAmount() {
		return flatRateAmount;
	}

	@Override
	public Currency getCurrency() {
		return currency;
	}

	@Override
	public <T extends ContextEntity> void setContext(T context) {
		this.context = context;
	}

	@Override
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public void setValidFrom(Date from) {
		this.validFrom = from;
	}

	@Override
	public void setValidTo(Date to) {
		this.validTo = to;
	}

	@Override
	public void setTaxRateType(TaxRateType type) {
		this.taxRateType = type;
	}

	@Override
	public void setPercentageRateValue(BigDecimal percentage) {
		this.percentageRateValue = percentage;
	}

	@Override
	public void setFlatRateAmount(BigDecimal amount) {
		this.flatRateAmount = amount;
	}

	@Override
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
