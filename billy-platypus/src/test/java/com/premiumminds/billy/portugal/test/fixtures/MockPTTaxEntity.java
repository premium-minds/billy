package com.premiumminds.billy.portugal.test.fixtures;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.test.fixtures.MockTaxEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;

public class MockPTTaxEntity extends MockTaxEntity implements PTTaxEntity {

	/**
	 * 
	 */
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

	public MockPTTaxEntity() {

	}

	@Override
	public Context getContext() {
		return super.getContext();
	}

	@Override
	public String getDesignation() {
		return this.designation;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public BigDecimal getValue() {
		return this.value;
	}

	@Override
	public Date getValidFrom() {
		return this.validFrom;
	}

	@Override
	public Date getValidTo() {
		return this.validTo;
	}

	@Override
	public TaxRateType getTaxRateType() {
		return this.taxRateType;
	}

	@Override
	public BigDecimal getPercentageRateValue() {
		return this.percentageRateValue;
	}

	@Override
	public BigDecimal getFlatRateAmount() {
		return this.flatRateAmount;
	}

	@Override
	public Currency getCurrency() {
		return this.currency;
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

	@Override
	public void setValue(BigDecimal value) {
		this.value = value;
	}
}