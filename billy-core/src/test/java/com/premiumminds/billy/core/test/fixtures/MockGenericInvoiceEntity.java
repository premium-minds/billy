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
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.Supplier;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;

public class MockGenericInvoiceEntity extends MockBaseEntity implements
		GenericInvoiceEntity {

	private static final long serialVersionUID = 1L;

	public String number;
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
	public Enum<?> paymentMechanism;
	public CreditOrDebit creditOrDebit;

	public MockGenericInvoiceEntity(){
		this.entries = new ArrayList<GenericInvoiceEntry>();
		this.receiptNumbers = new ArrayList<String>();
	}
	
	@Override
	public String getNumber() {
		return number;
	}

	@Override
	public Business getBusiness() {
		return business;
	}

	@Override
	public Customer getCustomer() {
		return customer;
	}

	@Override
	public Supplier getSupplier() {
		return supplier;
	}

	@Override
	public String getOfficeNumber() {
		return officeNumber;
	}

	@Override
	public Date getDate() {
		return date;
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
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	@Override
	public BigDecimal getDiscountsAmount() {
		return discountsAmount;
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
	public String getPaymentTerms() {
		return paymentTerms;
	}

	@Override
	public boolean isSelfBilled() {
		return selfBilled;
	}

	@Override
	public String getSourceId() {
		return sourceId;
	}

	@Override
	public Date getGeneralLedgerDate() {
		return generalLedgerDate;
	}

	@Override
	public String getBatchId() {
		return batchId;
	}

	@Override
	public String getTransactionId() {
		return transactionsId;
	}

	@Override
	public Currency getCurrency() {
		return currency;
	}

	@Override
	public String getSettlementDescription() {
		return settlementDescription;
	}

	@Override
	public BigDecimal getSettlementDiscount() {
		return settlementDiscount;
	}

	@Override
	public Date getSettlementDate() {
		return settlementDate;
	}

	@Override
	public Enum<?> getPaymentMechanism() {
		return paymentMechanism;
	}

	@Override
	public CreditOrDebit getCreditOrDebit() {
		return creditOrDebit;
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
		return receiptNumbers;
	}

	@Override
	public List<GenericInvoiceEntry> getEntries() {
		return entries;
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
	public <T extends Enum<T>> void setPaymentMechanism(T mechanism) {
		this.paymentMechanism = mechanism;
	}

	@Override
	public void setCreditOrDebit(CreditOrDebit creditOrDebit) {
		this.creditOrDebit = creditOrDebit;
	}

}
