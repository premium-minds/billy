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

import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.ShippingPointEntity;
import com.premiumminds.billy.core.persistence.entities.SupplierEntity;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.Supplier;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;

public class MockGenericInvoiceEntity extends MockBaseEntity implements
		GenericInvoiceEntity {

	private static final long serialVersionUID = 1L;

	public String number;

	public String series;
	public Integer seriesNumber;
	public Business business;
	public CustomerEntity customer;
	public SupplierEntity supplier;
	public String officeNumber;
	public Date date;
	public BigDecimal amountWithTax;
	public BigDecimal amountWithoutTax;
	public BigDecimal taxAmount;
	public BigDecimal discountsAmount;
	public ShippingPointEntity shippingOrigin;
	public ShippingPointEntity shippingDestination;
	public String paymentTerms;
	public boolean selfBilled;
	public String sourceId;
	public Date generalLedgerDate;
	public String batchId;
	public String transactionsId;
	public List<String> receiptNumbers;
	public List<GenericInvoiceEntry> entries;
	public Currency currency;
	public String settlementDescription;
	public BigDecimal settlementDiscount;
	public Date settlementDate;
	public Enum paymentMechanism;
	public CreditOrDebit creditOrDebit;
	public List<Payment> payments;

	public MockGenericInvoiceEntity() {
		this.entries = new ArrayList<GenericInvoiceEntry>();
		this.receiptNumbers = new ArrayList<String>();
		this.payments = new ArrayList<Payment>();
	}

	@Override
	public String getNumber() {
		return this.number;
	}

	@Override
	public Business getBusiness() {
		return this.business;
	}

	@Override
	public Customer getCustomer() {
		return this.customer;
	}

	@Override
	public Supplier getSupplier() {
		return this.supplier;
	}

	@Override
	public String getOfficeNumber() {
		return this.officeNumber;
	}

	@Override
	public Date getDate() {
		return this.date;
	}

	@Override
	public BigDecimal getAmountWithTax() {
		return this.amountWithTax;
	}

	@Override
	public BigDecimal getAmountWithoutTax() {
		return this.amountWithoutTax;
	}

	@Override
	public BigDecimal getTaxAmount() {
		return this.taxAmount;
	}

	@Override
	public BigDecimal getDiscountsAmount() {
		return this.discountsAmount;
	}

	@Override
	public ShippingPoint getShippingOrigin() {
		return this.shippingOrigin;
	}

	@Override
	public ShippingPoint getShippingDestination() {
		return this.shippingDestination;
	}

	@Override
	public String getPaymentTerms() {
		return this.paymentTerms;
	}

	@Override
	public boolean isSelfBilled() {
		return this.selfBilled;
	}

	@Override
	public String getSourceId() {
		return this.sourceId;
	}

	@Override
	public Date getGeneralLedgerDate() {
		return this.generalLedgerDate;
	}

	@Override
	public String getBatchId() {
		return this.batchId;
	}

	@Override
	public String getTransactionId() {
		return this.transactionsId;
	}

	@Override
	public Currency getCurrency() {
		return this.currency;
	}

	@Override
	public String getSettlementDescription() {
		return this.settlementDescription;
	}

	@Override
	public BigDecimal getSettlementDiscount() {
		return this.settlementDiscount;
	}

	@Override
	public Date getSettlementDate() {
		return this.settlementDate;
	}

	@Override
	public CreditOrDebit getCreditOrDebit() {
		return this.creditOrDebit;
	}

	@Override
	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public <T extends Business> void setBusiness(T business) {
		this.business = business;
	}

	@Override
	public <T extends CustomerEntity> void setCustomer(T customer) {
		this.customer = customer;
	}

	@Override
	public <T extends SupplierEntity> void setSupplier(T supplier) {
		this.supplier = supplier;
	}

	@Override
	public void setOfficeNumber(String number) {
		this.officeNumber = number;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
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
	public void setTaxAmount(BigDecimal amount) {
		this.taxAmount = amount;
	}

	@Override
	public void setDiscountsAmount(BigDecimal amount) {
		this.discountsAmount = amount;
	}

	@Override
	public <T extends ShippingPointEntity> void setShippingOrigin(T origin) {
		this.shippingOrigin = origin;
	}

	@Override
	public <T extends ShippingPointEntity> void setShippingDestination(
			T destination) {
		this.shippingDestination = destination;
	}

	@Override
	public void setPaymentTerms(String terms) {
		this.paymentTerms = terms;
	}

	@Override
	public void setSelfBilled(boolean selfBilled) {
		this.selfBilled = selfBilled;
	}

	@Override
	public void setSourceId(String source) {
		this.sourceId = source;
	}

	@Override
	public void setGeneralLedgerDate(Date date) {
		this.generalLedgerDate = date;
	}

	@Override
	public void setBatchId(String id) {
		this.batchId = id;
	}

	@Override
	public void setTransactionId(String id) {
		this.transactionsId = id;
	}

	@Override
	public List<String> getReceiptNumbers() {
		return this.receiptNumbers;
	}

	@Override
	public <T extends GenericInvoiceEntry> List<T> getEntries() {
		return (List<T>) this.entries;
	}

	@Override
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
	public void setSettlementDescription(String description) {
		this.settlementDescription = description;
	}

	@Override
	public void setSettlementDiscount(BigDecimal discount) {
		this.settlementDiscount = discount;
	}

	@Override
	public void setSettlementDate(Date date) {
		this.settlementDate = date;
	}

	@Override
	public void setCreditOrDebit(CreditOrDebit creditOrDebit) {
		this.creditOrDebit = creditOrDebit;
	}

	@Override
	public String getSeries() {
		return this.series;
	}

	@Override
	public void setSeries(String series) {
		this.series = series;
	}

	@Override
	public Integer getSeriesNumber() {
		return this.seriesNumber;
	}

	@Override
	public void setSeriesNumber(Integer seriesNumber) {
		this.seriesNumber = seriesNumber;
	}


	@Override
	public <T extends Payment> List<T> getPayments() {
		// TODO Auto-generated method stub
		return null;
	}

}
