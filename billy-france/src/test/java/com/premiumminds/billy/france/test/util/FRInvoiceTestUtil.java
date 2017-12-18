/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.test.util;

import java.math.BigDecimal;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.entities.FRBusinessEntity;
import com.premiumminds.billy.france.persistence.entities.FRCustomerEntity;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.services.entities.FRInvoice;
import com.premiumminds.billy.france.services.entities.FRInvoiceEntry;
import com.premiumminds.billy.france.test.services.documents.FRDocumentAbstractTest.SOURCE_BILLING;

public class FRInvoiceTestUtil {

    protected static final Boolean BILLED = false;
    protected static final Boolean CANCELLED = false;
    protected static final Boolean SELFBILL = false;
    protected static final String SOURCE_ID = "SOURCE";
    protected static final String SERIE = "A";
    protected static final Integer SERIE_NUMBER = 1;
    protected static final int MAX_PRODUCTS = 5;

    protected Injector injector;
    protected FRInvoiceEntryTestUtil invoiceEntry;
    protected FRBusinessTestUtil business;
    protected FRCustomerTestUtil customer;
    protected FRPaymentTestUtil payment;

    public FRInvoiceTestUtil(Injector injector) {
        this.injector = injector;
        this.invoiceEntry = new FRInvoiceEntryTestUtil(injector);
        this.business = new FRBusinessTestUtil(injector);
        this.customer = new FRCustomerTestUtil(injector);
        this.payment = new FRPaymentTestUtil(injector);
    }

    public FRInvoiceEntity getInvoiceEntity() {
        return this.getInvoiceEntity(SOURCE_BILLING.APPLICATION);
    }

    public FRInvoiceEntity getInvoiceEntity(SOURCE_BILLING billing) {
        FRInvoiceEntity invoice;
        switch (billing) {
            case MANUAL:
                invoice = (FRInvoiceEntity) this.getManualInvoiceBuilder(this.business.getBusinessEntity()).build();
                break;
            case APPLICATION:
            default:
                invoice = (FRInvoiceEntity) this.getInvoiceBuilder(this.business.getBusinessEntity()).build();
                break;
        }

        return invoice;
    }

    public FRInvoice.Builder getInvoiceBuilder(FRBusinessEntity business) {
        BigDecimal price = new BigDecimal("0.450");
        FRInvoice.Builder invoiceBuilder = this.injector.getInstance(FRInvoice.Builder.class);

        DAOFRCustomer daoFRCustomer = this.injector.getInstance(DAOFRCustomer.class);

        FRCustomerEntity customerEntity = this.customer.getCustomerEntity();
        UID customerUID = daoFRCustomer.create(customerEntity).getUID();

        FRInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
        invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, price);
        invoiceBuilder.addEntry(invoiceEntryBuilder);

        return invoiceBuilder.setBilled(FRInvoiceTestUtil.BILLED).setCancelled(FRInvoiceTestUtil.CANCELLED)
                .setSelfBilled(FRInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(FRInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setBusinessUID(business.getUID())
                .addPayment(this.payment.getPaymentBuilder());
    }

    public FRInvoice.ManualBuilder getManualInvoiceBuilder(FRBusinessEntity business) {
        BigDecimal price = new BigDecimal("0.450");
        BigDecimal tax = new BigDecimal("0.078");
        FRInvoice.ManualBuilder invoiceBuilder = this.injector.getInstance(FRInvoice.ManualBuilder.class);

        DAOFRCustomer daoFRCustomer = this.injector.getInstance(DAOFRCustomer.class);

        FRCustomerEntity customerEntity = this.customer.getCustomerEntity();
        UID customerUID = daoFRCustomer.create(customerEntity).getUID();

        FRInvoiceEntry.ManualBuilder invoiceEntryBuilder = this.invoiceEntry.getManualInvoiceEntryBuilder()
                .setUnitAmount(AmountType.WITH_TAX, price).setUnitAmount(AmountType.WITHOUT_TAX, price.subtract(tax))
                .setUnitTaxAmount(tax).setAmount(AmountType.WITH_TAX, price)
                .setAmount(AmountType.WITHOUT_TAX, price.subtract(tax)).setTaxAmount(tax);
        invoiceBuilder.addEntry(invoiceEntryBuilder);

        return invoiceBuilder.setBilled(FRInvoiceTestUtil.BILLED).setCancelled(FRInvoiceTestUtil.CANCELLED)
                .setSelfBilled(FRInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(FRInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setBusinessUID(business.getUID()).setAmount(AmountType.WITH_TAX, price)
                .setAmount(AmountType.WITHOUT_TAX, price.subtract(tax)).setTaxAmount(tax)
                .addPayment(this.payment.getPaymentBuilder());
    }

    public FRInvoiceEntity getDiferentRegionsInvoice() {
        BigDecimal entriesPrice = new BigDecimal("16.0145");
        FRInvoice.Builder invoiceBuilder = this.injector.getInstance(FRInvoice.Builder.class);

        DAOFRCustomer daoFRCustomer = this.injector.getInstance(DAOFRCustomer.class);

        FRCustomerEntity customerEntity = this.customer.getCustomerEntity();
        UID customerUID = daoFRCustomer.create(customerEntity).getUID();

        for (int i = 0; i < 9; i++) {
            FRInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
            invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, entriesPrice);
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        invoiceBuilder.setBilled(FRInvoiceTestUtil.BILLED).setCancelled(FRInvoiceTestUtil.CANCELLED)
                .setSelfBilled(FRInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(FRInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setBusinessUID(this.business.getBusinessEntity().getUID())
                .addPayment(this.payment.getPaymentBuilder());

        return (FRInvoiceEntity) invoiceBuilder.build();
    }

    public FRInvoiceEntity getManyEntriesInvoice() {
        BigDecimal entriesPrice = new BigDecimal("16.0145");
        FRInvoice.Builder invoiceBuilder = this.injector.getInstance(FRInvoice.Builder.class);

        DAOFRCustomer daoFRCustomer = this.injector.getInstance(DAOFRCustomer.class);

        FRCustomerEntity customerEntity = this.customer.getCustomerEntity();
        UID customerUID = daoFRCustomer.create(customerEntity).getUID();

        for (int i = 0; i < 9; i++) {
            FRInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
            invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, entriesPrice);
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        invoiceBuilder.setBilled(FRInvoiceTestUtil.BILLED).setCancelled(FRInvoiceTestUtil.CANCELLED)
                .setSelfBilled(FRInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(FRInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setBusinessUID(this.business.getBusinessEntity().getUID())
                .addPayment(this.payment.getPaymentBuilder());

        return (FRInvoiceEntity) invoiceBuilder.build();
    }

    public FRInvoiceEntity getManyEntriesWithDiferentRegionsInvoice() {
        BigDecimal entriesPrice = new BigDecimal("0.355555");
        FRInvoice.Builder invoiceBuilder = this.injector.getInstance(FRInvoice.Builder.class);

        DAOFRCustomer daoFRCustomer = this.injector.getInstance(DAOFRCustomer.class);

        FRCustomerEntity customerEntity = this.customer.getCustomerEntity();
        UID customerUID = daoFRCustomer.create(customerEntity).getUID();

        for (int i = 0; i < 9; i++) {
            FRInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
            invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, entriesPrice);
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        invoiceBuilder.setBilled(FRInvoiceTestUtil.BILLED).setCancelled(FRInvoiceTestUtil.CANCELLED)
                .setSelfBilled(FRInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(FRInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setBusinessUID(this.business.getBusinessEntity().getUID())
                .addPayment(this.payment.getPaymentBuilder());

        return (FRInvoiceEntity) invoiceBuilder.build();
    }

}
