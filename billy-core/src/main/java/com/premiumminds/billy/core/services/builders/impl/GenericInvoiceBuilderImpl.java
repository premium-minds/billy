/**
 * Copyright (C) 2017 Premium Minds.
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
import java.util.Currency;
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
import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.DiscountType;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.core.util.NotOnUpdate;

public class GenericInvoiceBuilderImpl<TBuilder extends GenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends GenericInvoiceEntry, TDocument extends GenericInvoice>
        extends AbstractBuilder<TBuilder, TDocument> implements GenericInvoiceBuilder<TBuilder, TEntry, TDocument> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    protected DAOGenericInvoice daoGenericInvoice;
    protected DAOBusiness daoBusiness;
    protected DAOCustomer daoCustomer;
    protected DAOSupplier daoSupplier;

    @Inject
    public GenericInvoiceBuilderImpl(DAOGenericInvoice daoGenericInvoice, DAOBusiness daoBusiness,
            DAOCustomer daoCustomer, DAOSupplier daoSupplier) {
        super(daoGenericInvoice);
        this.daoGenericInvoice = daoGenericInvoice;
        this.daoBusiness = daoBusiness;
        this.daoCustomer = daoCustomer;
        this.daoSupplier = daoSupplier;
        this.getTypeInstance().setScale(2);
    }

    @Override
    @NotOnUpdate
    @NotImplemented
    public TBuilder setCurrency(Currency currency) {
        BillyValidator.notNull(currency, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.currency"));
        this.getTypeInstance().setCurrency(currency);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setBusinessUID(UID businessUID) {
        BillyValidator.notNull(businessUID, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.business"));
        BusinessEntity b = this.daoBusiness.get(businessUID);
        BillyValidator.found(b, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.business"));
        this.getTypeInstance().setBusiness(b);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setCustomerUID(UID customerUID) {
        BillyValidator.notNull(customerUID, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.customer"));
        CustomerEntity c = this.daoCustomer.get(customerUID);
        BillyValidator.found(c, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.customer"));
        this.getTypeInstance().setCustomer(c);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setSupplierUID(UID supplier) {
        BillyValidator.notNull(supplier, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.supplier"));
        SupplierEntity s = this.daoSupplier.get(supplier);
        BillyValidator.found(supplier, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.supplier"));
        this.getTypeInstance().setSupplier(s);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setOfficeNumber(String number) {
        BillyValidator.notBlank(number, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.office_number"));
        this.getTypeInstance().setOfficeNumber(number);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setDate(Date date) {
        BillyValidator.notNull(date, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.date"));
        this.getTypeInstance().setDate(date);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public <T extends ShippingPointEntity> TBuilder setShippingOrigin(Builder<T> originBuilder) {
        Validate.notNull(originBuilder, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.shipping_origin"));
        this.getTypeInstance().setShippingOrigin(originBuilder.build());
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public <T extends ShippingPointEntity> TBuilder setShippingDestination(Builder<T> destinationBuilder) {
        Validate.notNull(destinationBuilder,
                GenericInvoiceBuilderImpl.LOCALIZER.getString("field.shipping_destination"));
        this.getTypeInstance().setShippingDestination(destinationBuilder.build());
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setPaymentTerms(String terms) {
        Validate.notBlank(terms, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.terms"));
        this.getTypeInstance().setPaymentTerms(terms);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setSelfBilled(boolean selfBilled) {
        this.getTypeInstance().setSelfBilled(selfBilled);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setSourceId(String source) {
        Validate.notBlank(source, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.source"));
        this.getTypeInstance().setSourceId(source);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setGeneralLedgerDate(Date date) {
        Validate.notNull(date, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.general_ledger_date"));
        this.getTypeInstance().setGeneralLedgerDate(date);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setBatchId(String id) {
        Validate.notBlank(id, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.batch_id"));
        this.getTypeInstance().setBatchId(id);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setTransactionId(String id) {
        Validate.notBlank(id, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.transaction"));
        this.getTypeInstance().setTransactionId(id);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder addReceiptNumber(String number) {
        BillyValidator.notBlank(number, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.receipt_number"));
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public <T extends GenericInvoiceEntry> TBuilder addEntry(Builder<T> entryBuilder) {
        BillyValidator.notNull(entryBuilder, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.entry"));
        T entry = entryBuilder.build();
        Validate.isInstanceOf(GenericInvoiceEntryEntity.class, entry); // TODO
        // message
        GenericInvoiceEntity i = this.getTypeInstance();
        GenericInvoiceEntryEntity e = (GenericInvoiceEntryEntity) entry;
        i.getEntries().add(e);
        e.setEntryNumber(i.getEntries().size());
        this.validateValues();
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setSettlementDescription(String description) {
        Validate.notBlank(description, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.settlement_description"));
        this.getTypeInstance().setSettlementDescription(description);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setSettlementDiscount(BigDecimal discount) {
        Validate.isTrue(discount == null || discount.compareTo(BigDecimal.ZERO) >= 0,
                GenericInvoiceBuilderImpl.LOCALIZER.getString("field.discount")); // TODO
        // message
        this.getTypeInstance().setSettlementDiscount(discount);
        return this.getBuilder();
    }

    @NotImplemented
    @Deprecated
    @Override
    public TBuilder setDiscounts(DiscountType type, BigDecimal... discounts) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @NotOnUpdate
    public TBuilder setSettlementDate(Date date) {
        BillyValidator.notNull(date, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.settlement_date"));
        this.getTypeInstance().setSettlementDate(date);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public <T extends Payment> TBuilder addPayment(Builder<T> paymentBuilder) {
        BillyValidator.notNull(paymentBuilder, GenericInvoiceBuilderImpl.LOCALIZER.getString("field.payment"));
        this.getTypeInstance().getPayments().add(paymentBuilder.build());
        return this.getBuilder();
    }

    // @Override
    // @NotOnUpdate
    // public TBuilder setCreditOrDebit(CreditOrDebit creditOrDebit) {
    // BillyValidator.notNull(creditOrDebit,
    // GenericInvoiceBuilderImpl.LOCALIZER
    // .getString("field.credit_or_debit"));
    // this.getTypeInstance().setCreditOrDebit(creditOrDebit);
    // return this.getBuilder();
    // }

    @Override
    public TBuilder setScale(int scale) {
        Validate.notNull(scale);
        this.getTypeInstance().setScale(scale);
        return this.getBuilder();
    }

    @NotOnUpdate
    @Override
    protected void validateInstance() throws ValidationException {
        GenericInvoiceEntity i = this.getTypeInstance();
        if (i.isSelfBilled() != null) {
            i.setSelfBilled(false);
        }
        BillyValidator.mandatory(i.getCustomer(), GenericInvoiceBuilderImpl.LOCALIZER.getString("field.customer"));
        BillyValidator.mandatory(i.getSupplier(), GenericInvoiceBuilderImpl.LOCALIZER.getString("field.supplier"));
        this.validateDate();
        this.validateValues();
    }

    protected void validateDate() {
        // needed to avoid no date in the invoice
        GenericInvoiceEntity i = this.getTypeInstance();
        if (i.getDate() == null) {
            i.setDate(new Date());
        }
    }

    protected void validateValues() throws ValidationException {

        GenericInvoiceEntity i = this.getTypeInstance();
        i.setCurrency(Currency.getInstance("EUR"));

        MathContext mc = BillyMathContext.get();

        BigDecimal amountWithTax = BigDecimal.ZERO;
        BigDecimal taxAmount = BigDecimal.ZERO;
        BigDecimal amountWithoutTax = BigDecimal.ZERO;

        for (GenericInvoiceEntry e : this.getTypeInstance().getEntries()) {

            amountWithTax = amountWithTax.add(e.getUnitAmountWithTax().multiply(e.getQuantity(), mc), mc);
            taxAmount = taxAmount.add(e.getUnitTaxAmount().multiply(e.getQuantity(), mc), mc);
            amountWithoutTax = amountWithoutTax.add(e.getUnitAmountWithoutTax().multiply(e.getQuantity(), mc), mc);
            if (e.getCurrency() == null) {
                GenericInvoiceEntryEntity entry = (GenericInvoiceEntryEntity) e;
                entry.setCurrency(i.getCurrency());
                e = entry;
            } else {
                Validate.isTrue(i.getCurrency().getCurrencyCode().equals(e.getCurrency().getCurrencyCode()));
            }
        }

        i.setAmountWithTax(amountWithTax);
        i.setTaxAmount(taxAmount);
        i.setAmountWithoutTax(amountWithoutTax);

        Validate.isTrue(
                i.getAmountWithTax().subtract(i.getTaxAmount(), mc).setScale(7, mc.getRoundingMode())
                        .compareTo(i.getAmountWithoutTax().setScale(7, mc.getRoundingMode())) == 0,
                "The invoice values are invalid", // TODO message
                i.getAmountWithTax(), i.getAmountWithoutTax(), i.getTaxAmount());

        Validate.isTrue(
                i.getAmountWithTax().compareTo(BigDecimal.ZERO) > 0 &&
                        i.getAmountWithoutTax().compareTo(BigDecimal.ZERO) >= 0 &&
                        i.getTaxAmount().compareTo(BigDecimal.ZERO) > 0,
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

    @SuppressWarnings("unchecked")
    @Override
    protected GenericInvoiceEntity getTypeInstance() {
        return (GenericInvoiceEntity) super.getTypeInstance();
    }

}
