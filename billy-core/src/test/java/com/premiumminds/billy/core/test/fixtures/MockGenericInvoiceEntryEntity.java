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
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;

public class MockGenericInvoiceEntryEntity extends MockBaseEntity implements
		GenericInvoiceEntryEntity {

	private static final long serialVersionUID = 1L;

	public CreditOrDebit creditOrDebit;
	public Currency currency;
	public String description;
	public List<GenericInvoice> references;
	public Integer number;
	public BigDecimal exchangeRateToDocumentCurrency;
	public BigDecimal amountWithoutTax;
	public BigDecimal amountWithTax;
	public BigDecimal taxAmount;
	public BigDecimal discountAmount;
	public Product product;
	public BigDecimal quantity;
	public BigDecimal shippingCostsAmount;
	public ShippingPoint shippingDestination;
	public ShippingPoint shippingOrigin;
	public List<Tax> taxes;
	public String taxExemptionReason;
	public Date taxPointDate;
	public BigDecimal unitAmountWithoutTax;
	public BigDecimal unitAmountWithTax;
	public BigDecimal unitTaxAmount;
	public BigDecimal unitDiscountAmount;
	public String unitOfMeasure;
	public AmountType type;

	public MockGenericInvoiceEntryEntity() {
		this.references = new ArrayList<GenericInvoice>();
		this.taxes = new ArrayList<Tax>();
	}

	@Override
	public Integer getEntryNumber() {
		return number;
	}

	@Override
	public ShippingPoint getShippingOrigin() {
		return shippingOrigin;
	}

	@Override
	public ShippingPoint getShippingDestination() {
		return shippingDestination;
	}

	@Override
	public Product getProduct() {
		return product;
	}

	@Override
	public BigDecimal getQuantity() {
		return quantity;
	}

	@Override
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	@Override
	public BigDecimal getUnitAmountWithTax() {
		return unitAmountWithTax;
	}

	@Override
	public BigDecimal getUnitAmountWithoutTax() {
		return unitAmountWithoutTax;
	}

	@Override
	public BigDecimal getUnitDiscountAmount() {
		return unitDiscountAmount;
	}

	@Override
	public BigDecimal getUnitTaxAmount() {
		return unitTaxAmount;
	}

	@Override
	public BigDecimal getAmountWithTax() {
		return amountWithTax;
	}

	@Override
	public BigDecimal getAmountWithoutTax() {
		return amountWithoutTax;
	}

	@Override
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	@Override
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	@Override
	public Date getTaxPointDate() {
		return taxPointDate;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public CreditOrDebit getCreditOrDebit() {
		return creditOrDebit;
	}

	@Override
	public BigDecimal getShippingCostsAmount() {
		return shippingCostsAmount;
	}

	@Override
	public Currency getCurrency() {
		return currency;
	}

	@Override
	public BigDecimal getExchangeRateToDocumentCurrency() {
		return exchangeRateToDocumentCurrency;
	}

	@Override
	public String getTaxExemptionReason() {
		return taxExemptionReason;
	}
	
	@Override
	public AmountType getAmountType() {
		return type;
	}

	@Override
	public void setEntryNumber(Integer number) {
		this.number = number;
	}

	@Override
	public <T extends ShippingPoint> void setShippingOrigin(T origin) {
		this.shippingOrigin = origin;
	}

	@Override
	public <T extends ShippingPoint> void setShippingDestination(T destination) {
		this.shippingDestination = destination;
	}

	@Override
	public <T extends Product> void setProduct(T product) {
		this.product = product;
	}

	@Override
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	@Override
	public void setUnitOfMeasure(String unit) {
		this.unitOfMeasure = unit;
	}

	@Override
	public void setUnitAmountWithTax(BigDecimal amount) {
		this.unitAmountWithTax = amount;
	}

	@Override
	public void setUnitAmountWithoutTax(BigDecimal amount) {
		this.unitAmountWithoutTax = amount;
	}

	@Override
	public void setUnitDiscountAmount(BigDecimal amount) {
		this.unitDiscountAmount = amount;
	}

	@Override
	public void setUnitTaxAmount(BigDecimal amount) {
		this.unitTaxAmount = amount;
	}

	@Override
	public void setAmountWithTax(BigDecimal amount) {
		this.amountWithTax = amount;
	}

	@Override
	public void setAmountWithoutTax(BigDecimal amount) {
		this.amountWithoutTax = amount;
	}

	@Override
	public void setDiscountAmount(BigDecimal amount) {
		this.discountAmount = amount;
	}

	@Override
	public void setTaxAmount(BigDecimal amount) {
		this.taxAmount = amount;
	}

	@Override
	public void setTaxPointDate(Date date) {
		this.taxPointDate = date;
	}

	@Override
	public List<GenericInvoice> getDocumentReferences() {
		return this.references;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setCreditOrDebit(CreditOrDebit creditOrDebit) {
		this.creditOrDebit = creditOrDebit;
	}

	@Override
	public void setShippingCostsAmount(BigDecimal amount) {
		this.shippingCostsAmount = amount;
	}

	@Override
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
	public void setExchangeRateToDocumentCurrency(BigDecimal rate) {
		this.exchangeRateToDocumentCurrency = rate;
	}

	@Override
	public List<Tax> getTaxes() {
		return taxes;
	}

	@Override
	public void setTaxExemptionReason(String exemptionReason) {
		this.taxExemptionReason = exemptionReason;
	}

	@Override
	public void setAmountType(AmountType type) {
		this.type = type;
	}
}
