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
package com.premiumminds.billy.core.services.builders.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.ValidationException;

import org.apache.commons.lang3.Validate;

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
import com.premiumminds.billy.core.services.entities.ShippingPoint;
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
		extends AbstractBuilder<TBuilder, TDocument> implements
		GenericInvoiceBuilder<TBuilder, TEntry, TDocument> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/core/i18n/FieldNames");

	protected DAOGenericInvoice daoGenericInvoice;
	protected DAOBusiness daoBusiness;
	protected DAOCustomer daoCustomer;
	protected DAOSupplier daoSupplier;

	@SuppressWarnings("unchecked")
	@Inject
	public GenericInvoiceBuilderImpl(DAOGenericInvoice daoGenericInvoice,
			DAOBusiness daoBusiness, DAOCustomer daoCustomer,
			DAOSupplier daoSupplier) {
		super((EntityFactory<? extends TDocument>) daoGenericInvoice);
		this.daoGenericInvoice = daoGenericInvoice;
		this.daoBusiness = daoBusiness;
		this.daoCustomer = daoCustomer;
		this.daoSupplier = daoSupplier;
	}

	@Override
	public TBuilder setBusinessUID(UID businessUID) {
		BillyValidator
				.mandatory(businessUID, GenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.business"));
		BusinessEntity b = this.daoBusiness.get(businessUID);
		BillyValidator
				.found(b, GenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.business"));
		this.getTypeInstance().setBusiness(b);
		return this.getBuilder();
	}

	@Override
	public TBuilder setCustomerUID(UID customerUID) {
		BillyValidator
				.notNull(customerUID, GenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.customer"));
		CustomerEntity c = this.daoCustomer.get(customerUID);
		BillyValidator
				.found(c, GenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.customer"));
		this.getTypeInstance().setCustomer(c);
		return this.getBuilder();
	}

	@Override
	public TBuilder setSupplierUID(UID supplier) {
		BillyValidator
				.notNull(supplier, GenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.supplier"));
		SupplierEntity s = this.daoSupplier.get(supplier);
		BillyValidator
				.found(supplier, GenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.supplier"));
		this.getTypeInstance().setSupplier(s);
		return this.getBuilder();
	}

	@Override
	public TBuilder setOfficeNumber(String number) {
		BillyValidator.notNull(number, GenericInvoiceBuilderImpl.LOCALIZER
				.getString("field.office_number"));
		this.getTypeInstance().setOfficeNumber(number);
		return this.getBuilder();
	}

	@Override
	public TBuilder setDate(Date date) {
		BillyValidator.notNull(date,
				GenericInvoiceBuilderImpl.LOCALIZER.getString("field.date"));
		this.getTypeInstance().setDate(date);
		return this.getBuilder();
	}

	@Override
	public <T extends ShippingPoint> TBuilder setShippingOrigin(
			Builder<T> originBuilder) {
		this.getTypeInstance().setShippingOrigin(
				(ShippingPointEntity) originBuilder.build());
		return this.getBuilder();
	}

	@Override
	public <T extends ShippingPoint> TBuilder setShippingDestination(
			Builder<T> destinationBuilder) {
		this.getTypeInstance().setShippingDestination(
				(ShippingPointEntity) destinationBuilder.build());
		return this.getBuilder();
	}

	@Override
	public TBuilder setPaymentTerms(String terms) {
		Validate.notEmpty(terms,
				GenericInvoiceBuilderImpl.LOCALIZER.getString("field.terms"));
		this.getTypeInstance().setPaymentTerms(terms);
		return this.getBuilder();
	}

	@Override
	public TBuilder setSelfBilled(boolean selfBilled) {
		this.getTypeInstance().setSelfBilled(selfBilled);
		return this.getBuilder();
	}

	@Override
	public TBuilder setSourceId(String source) {
		Validate.notEmpty(source,
				GenericInvoiceBuilderImpl.LOCALIZER.getString("field.source"));
		return this.getBuilder();
	}

	@Override
	public TBuilder setGeneralLedgerDate(Date date) {
		this.getTypeInstance().setGeneralLedgerDate(date);
		return this.getBuilder();
	}

	@Override
	public TBuilder setBatchId(String id) {
		this.getTypeInstance().setBatchId(id);
		return this.getBuilder();
	}

	@Override
	public TBuilder setTransactionId(String id) {
		Validate.notEmpty(id, GenericInvoiceBuilderImpl.LOCALIZER
				.getString("field.transaction"));
		this.getTypeInstance().setTransactionId(id);
		return this.getBuilder();
	}

	@Override
	public TBuilder addReceiptNumber(String number) {
		BillyValidator.notNull(number, GenericInvoiceBuilderImpl.LOCALIZER
				.getString("field.receipt_number"));
		BillyValidator.notBlank(number, GenericInvoiceBuilderImpl.LOCALIZER
				.getString("field.receipt_number"));
		return this.getBuilder();
	}

	@Override
	public <T extends GenericInvoiceEntry> TBuilder addEntry(
			Builder<T> entryBuilder) {
		BillyValidator.notNull(entryBuilder,
				GenericInvoiceBuilderImpl.LOCALIZER.getString("field.entry"));
		T entry = entryBuilder.build();
		Validate.isInstanceOf(GenericInvoiceEntryEntity.class, entry); // TODO
																		// message
		GenericInvoiceEntity i = this.getTypeInstance();
		GenericInvoiceEntryEntity e = (GenericInvoiceEntryEntity) entry;
		e.setEntryNumber(i.getEntries().size());
		i.getEntries().add(e);
		this.validateValues();
		return this.getBuilder();
	}

	@Override
	public TBuilder setSettlementDescription(String description) {
		Validate.notEmpty(description, GenericInvoiceBuilderImpl.LOCALIZER
				.getString("field.settlement_description"));
		this.getTypeInstance().setSettlementDescription(description);
		return this.getBuilder();
	}

	@Override
	public TBuilder setSettlementDiscount(BigDecimal discount) {
		Validate.isTrue(discount == null
				|| discount.compareTo(BigDecimal.ZERO) >= 0,
				GenericInvoiceBuilderImpl.LOCALIZER.getString("field.discount")); // TODO
																					// message
		this.getTypeInstance().setSettlementDiscount(discount);
		return this.getBuilder();
	}

	@NotImplemented
	@Override
	public TBuilder setDiscounts(DiscountType type, BigDecimal... discounts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TBuilder setSettlementDate(Date date) {
		BillyValidator.notNull(date, GenericInvoiceBuilderImpl.LOCALIZER
				.getString("field.settlement_date"));
		this.getTypeInstance().setSettlementDate(date);
		return this.getBuilder();
	}

	@Override
	public <T extends Enum<T>> TBuilder setPaymentMechanism(T mechanism) {
		BillyValidator.notNull(mechanism, GenericInvoiceBuilderImpl.LOCALIZER
				.getString("field.payment_mechanism"));
		this.getTypeInstance().setPaymentMechanism(mechanism);
		return this.getBuilder();
	}

	@Override
	public TBuilder setCreditOrDebit(CreditOrDebit creditOrDebit) {
		BillyValidator.notNull(creditOrDebit,
				GenericInvoiceBuilderImpl.LOCALIZER
						.getString("credit_or_debit"));
		this.getTypeInstance().setCreditOrDebit(creditOrDebit);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance() throws ValidationException {
		// TODO Auto-generated method stub
		this.validateValues();
		this.validateDates();
	}

	protected void validateValues() throws ValidationException {
		GenericInvoiceEntity i = this.getTypeInstance();

		MathContext mc = BillyMathContext.get();

		BigDecimal amountWithTax = new BigDecimal("0", mc);
		BigDecimal taxAmount = new BigDecimal("0", mc);
		BigDecimal amountWithoutTax = new BigDecimal("0", mc);

		for (GenericInvoiceEntry e : this.getTypeInstance().getEntries()) {
			amountWithTax = amountWithTax.add(e.getAmountWithTax(), mc);
			taxAmount = taxAmount.add(e.getTaxAmount(), mc);
			amountWithoutTax = amountWithoutTax
					.add(e.getAmountWithoutTax(), mc);
		}

		i.setAmountWithTax(amountWithTax);
		i.setTaxAmount(taxAmount);
		i.setAmountWithoutTax(amountWithoutTax);

		Validate.isTrue(i.getAmountWithTax().add(i.getAmountWithoutTax(), mc)
				.compareTo(i.getTaxAmount()) == 0,
				"The invoice values are invalid", // TODO message
				i.getAmountWithTax(), i.getAmountWithoutTax(), i.getTaxAmount());

		Validate.isTrue(i.getAmountWithTax().compareTo(BigDecimal.ZERO) > 0
				&& i.getAmountWithoutTax().compareTo(BigDecimal.ZERO) >= 0
				&& i.getTaxAmount().compareTo(BigDecimal.ZERO) > 0,
				"The invoice values are lower than zero", // TODO
															// message
				i.getAmountWithTax(), i.getAmountWithoutTax(), i.getTaxAmount());
	}

	@Deprecated
	@NotImplemented
	/**
	 * Applies the settlement discount
	 */
	protected void applyDiscounts() {
		GenericInvoiceEntity i = this.getTypeInstance();
		i.getSettlementDiscount();
	}

	protected void validateDates() throws ValidationException {
		// TODO
	}

	@SuppressWarnings("unchecked")
	@Override
	protected GenericInvoiceEntity getTypeInstance() {
		return (GenericInvoiceEntity) super.getTypeInstance();
	}

}
