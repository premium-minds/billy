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
package com.premiumminds.billy.core.services.builders.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.ValidationException;

import com.premiumminds.billy.core.persistence.dao.DAOBusiness;
import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOSupplier;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.persistence.entities.ShippingPointEntity;
import com.premiumminds.billy.core.persistence.entities.SupplierEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceBuilder;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.services.entities.util.EntityFactory;
import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.DiscountType;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotImplemented;

@Deprecated
@NotImplemented
public class GenericInvoiceBuilderImpl<TBuilder extends GenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends GenericInvoiceEntry, TDocument extends GenericInvoice>
extends AbstractBuilder<TBuilder, TDocument>
implements GenericInvoiceBuilder<TBuilder, TEntry, TDocument> {

	protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

	protected DAOGenericInvoice daoGenericInvoice;
	protected DAOBusiness daoBusiness;
	protected DAOCustomer daoCustomer;
	protected DAOSupplier daoSupplier;
	
	@SuppressWarnings("unchecked")
	@Inject
	public GenericInvoiceBuilderImpl(
			DAOGenericInvoice daoGenericInvoice,
			DAOBusiness daoBusiness,
			DAOCustomer daoCustomer,
			DAOSupplier daoSupplier) {
		super((EntityFactory<? extends TDocument>) daoGenericInvoice);
		this.daoGenericInvoice = daoGenericInvoice;
		this.daoBusiness = daoBusiness;
		this.daoCustomer = daoCustomer;
		this.daoSupplier = daoSupplier;
	}

	@Override
	public TBuilder setBusinessUID(UID businessUID) {
		BillyValidator.mandatory(businessUID, LOCALIZER.getString("field.business"));
		BusinessEntity b = daoBusiness.get(businessUID);
		BillyValidator.found(b, LOCALIZER.getString("field.business"));
		getTypeInstance().setBusiness((BusinessEntity) b);
		return getBuilder();
	}
	
	@Override
	public TBuilder setCustomerUID(UID customerUID) {
		BillyValidator.notNull(customerUID, LOCALIZER.getString("field.customer"));
		CustomerEntity c = daoCustomer.get(customerUID);
		BillyValidator.found(c, LOCALIZER.getString("field.customer"));
		getTypeInstance().setCustomer((CustomerEntity) c);
		return getBuilder();
	}

	@Override
	public TBuilder setSupplierUID(UID supplier) {
		BillyValidator.notNull(supplier, LOCALIZER.getString("field.supplier"));
		SupplierEntity s = daoSupplier.get(supplier);
		BillyValidator.found(supplier, LOCALIZER.getString("field.supplier"));
		getTypeInstance().setSupplier(s);
		return getBuilder();
	}

	@Override
	public TBuilder setOfficeNumber(String number) {
		BillyValidator.notNull(number, LOCALIZER.getString("field.office_number"));
		getTypeInstance().setOfficeNumber(number);
		return getBuilder();
	}

	@Override
	public TBuilder setDate(Date date) {
		BillyValidator.notNull(date, LOCALIZER.getString("field.date"));
		getTypeInstance().setDate(date);
		return getBuilder();
	}

	@Override
	public <T extends ShippingPointEntity> TBuilder setShippingOrigin(Builder<T> originBuilder) {
		getTypeInstance().setShippingOrigin(originBuilder.build());
		return getBuilder();
	}

	@Override
	public <T extends ShippingPointEntity> TBuilder setShippingDestination(
			Builder<T> destinationBuilder) {
		getTypeInstance().setShippingDestination(destinationBuilder.build());
		return getBuilder();
	}

	@Override
	public TBuilder setPaymentTerms(String terms) {
		BillyValidator.notEmpty(terms, LOCALIZER.getString("field.terms"));
		getTypeInstance().setPaymentTerms(terms);
		return getBuilder();
	}

	@Override
	public TBuilder setSelfBilled(boolean selfBilled) {
		getTypeInstance().setSelfBilled(selfBilled);
		return getBuilder();
	}

	@Override
	public TBuilder setSourceId(String source) {
		BillyValidator.notEmpty(source, LOCALIZER.getString("field.source"));
		return getBuilder();
	}

	@Override
	public TBuilder setGeneralLedgerDate(Date date) {
		getTypeInstance().setGeneralLedgerDate(date);
		return getBuilder();
	}

	@Override
	public TBuilder setBatchId(String id) {
		getTypeInstance().setBatchId(id);
		return getBuilder();
	}

	@Override
	public TBuilder setTransactionId(String id) {
		BillyValidator.notEmpty(id, LOCALIZER.getString("field.transaction"));
		getTypeInstance().setTransactionId(id);
		return getBuilder();
	}

	@Override
	public TBuilder addReceiptNumber(String number) {
		BillyValidator.notNull(number, LOCALIZER.getString("field.receipt_number"));
		BillyValidator.notBlank(number, LOCALIZER.getString("field.receipt_number"));
		return getBuilder();
	}

	@Override
	public <T extends GenericInvoiceEntry> TBuilder addEntry(Builder<T> entryBuilder) {
		BillyValidator.notNull(entryBuilder, LOCALIZER.getString("field.entry"));
		T entry = entryBuilder.build();
		BillyValidator.isInstanceOf(GenericInvoiceEntryEntity.class, entry); //TODO message
		GenericInvoiceEntity i = getTypeInstance();
		GenericInvoiceEntryEntity e = (GenericInvoiceEntryEntity) entry;
		e.setEntryNumber(i.getEntries().size());
		i.getEntries().add(e);
		validateValues();
		return getBuilder();
	}

	@Override
	public TBuilder setSettlementDescription(String description) {
		BillyValidator.notEmpty(description, LOCALIZER.getString("field.settlement_description"));
		getTypeInstance().setSettlementDescription(description);
		return getBuilder();
	}

	@Override
	public TBuilder setSettlementDiscount(BigDecimal discount) {
		BillyValidator.isTrue(discount == null || discount.compareTo(BigDecimal.ZERO) >= 0,
				LOCALIZER.getString("field.discount")); //TODO message
		getTypeInstance().setSettlementDiscount(discount);
		return getBuilder();
	}
	
	@NotImplemented
	@Override
	public TBuilder setDiscounts(DiscountType type, BigDecimal... discounts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TBuilder setSettlementDate(Date date) {
		BillyValidator.notNull(date, LOCALIZER.getString("field.settlement_date"));
		getTypeInstance().setSettlementDate(date);
		return getBuilder();
	}

	@Override
	public <T extends Enum<T>> TBuilder setPaymentMechanism(T mechanism) {
		BillyValidator.notNull(mechanism, LOCALIZER.getString("field.payment_mechanism"));
		getTypeInstance().setPaymentMechanism(mechanism);
		return getBuilder();
	}

	@Override
	public TBuilder setCreditOrDebit(CreditOrDebit creditOrDebit) {
		BillyValidator.notNull(creditOrDebit, LOCALIZER.getString("credit_or_debit"));
		getTypeInstance().setCreditOrDebit(creditOrDebit);
		return getBuilder();
	}

	@Override
	protected void validateInstance() throws ValidationException {
		// TODO Auto-generated method stub
		validateValues();
		validateDates();
	}
	
	protected void validateValues() throws ValidationException {
		GenericInvoiceEntity i = getTypeInstance();
		
		MathContext mc = BillyMathContext.get();
		
		BigDecimal netAmount = new BigDecimal("0", mc);
		BigDecimal taxAmount = new BigDecimal("0", mc);
		BigDecimal grossAmount = new BigDecimal("0", mc);
		
		for(GenericInvoiceEntry e : getTypeInstance().getEntries()) {
			netAmount = netAmount.add(e.getNetAmount(), mc);
			taxAmount = taxAmount.add(e.getTaxAmount(), mc);
			grossAmount = grossAmount.add(e.getGrossAmount(), mc);
		}
		
		i.setNetAmount(netAmount);
		i.setTaxAmount(taxAmount);
		i.setGrossAmount(grossAmount);
		
		BillyValidator.isTrue(
				i.getNetAmount()
				.add(i.getTaxAmount(), mc)
				.compareTo(i.getGrossAmount()) == 0,
				"The invoice values are invalid", //TODO message
				i.getNetAmount(),
				i.getTaxAmount(),
				i.getGrossAmount()); 

		BillyValidator.isTrue(
				i.getNetAmount().compareTo(BigDecimal.ZERO) > 0 &&
				i.getTaxAmount().compareTo(BigDecimal.ZERO) >= 0 &&
				i.getGrossAmount().compareTo(BigDecimal.ZERO) > 0,
				"The invoice values are lower than zero", //TODO message
				i.getNetAmount(),
				i.getTaxAmount(),
				i.getGrossAmount());
	}
	
	@Deprecated
	@NotImplemented
	/**
	 * Applies the settlement discount
	 */
	protected void applyDiscounts() {
		GenericInvoiceEntity i = getTypeInstance();
		i.getSettlementDiscount();
	}
	
	protected void validateDates() throws ValidationException {
		//TODO
	}

	@SuppressWarnings("unchecked")
	@Override
	protected GenericInvoiceEntity getTypeInstance() {
		return (GenericInvoiceEntity) super.getTypeInstance();
	}
	
}
