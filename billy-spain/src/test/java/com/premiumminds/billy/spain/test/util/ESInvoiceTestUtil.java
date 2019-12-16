/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.test.util;

import java.math.BigDecimal;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.entities.ESBusinessEntity;
import com.premiumminds.billy.spain.persistence.entities.ESCustomerEntity;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.entities.ESInvoice;
import com.premiumminds.billy.spain.services.entities.ESInvoiceEntry;
import com.premiumminds.billy.spain.test.services.documents.ESDocumentAbstractTest.SOURCE_BILLING;

public class ESInvoiceTestUtil {

    protected static final Boolean BILLED = false;
    protected static final Boolean CANCELLED = false;
    protected static final Boolean SELFBILL = false;
    protected static final String SOURCE_ID = "SOURCE";
    protected static final String SERIE = "A";
    protected static final Integer SERIE_NUMBER = 1;
    protected static final int MAX_PRODUCTS = 5;

    protected Injector injector;
    protected ESInvoiceEntryTestUtil invoiceEntry;
    protected ESBusinessTestUtil business;
    protected ESCustomerTestUtil customer;
    protected ESPaymentTestUtil payment;

    public ESInvoiceTestUtil(Injector injector) {
        this.injector = injector;
        this.invoiceEntry = new ESInvoiceEntryTestUtil(injector);
        this.business = new ESBusinessTestUtil(injector);
        this.customer = new ESCustomerTestUtil(injector);
        this.payment = new ESPaymentTestUtil(injector);
    }

    public ESInvoiceEntity getInvoiceEntity() {
        return this.getInvoiceEntity(SOURCE_BILLING.APPLICATION);
    }

    public ESInvoiceEntity getInvoiceEntity(SOURCE_BILLING billing) {
        ESInvoiceEntity invoice;
        switch (billing) {
            case MANUAL:
                invoice = (ESInvoiceEntity) this.getManualInvoiceBuilder(this.business.getBusinessEntity()).build();
                break;
            case APPLICATION:
            default:
                invoice = (ESInvoiceEntity) this.getInvoiceBuilder(this.business.getBusinessEntity()).build();
                break;
        }

        return invoice;
    }

    public ESInvoice.Builder getInvoiceBuilder(ESBusinessEntity business) {
        BigDecimal price = new BigDecimal("0.450");
        ESInvoice.Builder invoiceBuilder = this.injector.getInstance(ESInvoice.Builder.class);

        DAOESCustomer daoESCustomer = this.injector.getInstance(DAOESCustomer.class);

        ESCustomerEntity customerEntity = this.customer.getCustomerEntity();
        UID customerUID = daoESCustomer.create(customerEntity).getUID();

        ESInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
        invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, price);
        invoiceBuilder.addEntry(invoiceEntryBuilder);

        return invoiceBuilder.setBilled(ESInvoiceTestUtil.BILLED).setCancelled(ESInvoiceTestUtil.CANCELLED)
                .setSelfBilled(ESInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(ESInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setBusinessUID(business.getUID())
                .addPayment(this.payment.getPaymentBuilder());
    }

    public ESInvoice.ManualBuilder getManualInvoiceBuilder(ESBusinessEntity business) {
        BigDecimal price = new BigDecimal("0.450");
        BigDecimal tax = new BigDecimal("0.078");
        ESInvoice.ManualBuilder invoiceBuilder = this.injector.getInstance(ESInvoice.ManualBuilder.class);

        DAOESCustomer daoESCustomer = this.injector.getInstance(DAOESCustomer.class);

        ESCustomerEntity customerEntity = this.customer.getCustomerEntity();
        UID customerUID = daoESCustomer.create(customerEntity).getUID();

        ESInvoiceEntry.ManualBuilder invoiceEntryBuilder = this.invoiceEntry.getManualInvoiceEntryBuilder()
                .setUnitAmount(AmountType.WITH_TAX, price).setUnitAmount(AmountType.WITHOUT_TAX, price.subtract(tax))
                .setUnitTaxAmount(tax).setAmount(AmountType.WITH_TAX, price)
                .setAmount(AmountType.WITHOUT_TAX, price.subtract(tax)).setTaxAmount(tax);
        invoiceBuilder.addEntry(invoiceEntryBuilder);

        return invoiceBuilder.setBilled(ESInvoiceTestUtil.BILLED).setCancelled(ESInvoiceTestUtil.CANCELLED)
                .setSelfBilled(ESInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(ESInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setBusinessUID(business.getUID()).setAmount(AmountType.WITH_TAX, price)
                .setAmount(AmountType.WITHOUT_TAX, price.subtract(tax)).setTaxAmount(tax)
                .addPayment(this.payment.getPaymentBuilder());
    }

    public ESInvoiceEntity getDiferentRegionsInvoice() {
        BigDecimal entriesPrice = new BigDecimal("16.0145");
        ESInvoice.Builder invoiceBuilder = this.injector.getInstance(ESInvoice.Builder.class);

        DAOESCustomer daoESCustomer = this.injector.getInstance(DAOESCustomer.class);

        ESCustomerEntity customerEntity = this.customer.getCustomerEntity();
        UID customerUID = daoESCustomer.create(customerEntity).getUID();

        for (int i = 0; i < 9; i++) {
            ESInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
            invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, entriesPrice);
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        invoiceBuilder.setBilled(ESInvoiceTestUtil.BILLED).setCancelled(ESInvoiceTestUtil.CANCELLED)
                .setSelfBilled(ESInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(ESInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setBusinessUID(this.business.getBusinessEntity().getUID())
                .addPayment(this.payment.getPaymentBuilder());

        return (ESInvoiceEntity) invoiceBuilder.build();
    }

    public ESInvoiceEntity getManyEntriesInvoice() {
        BigDecimal entriesPrice = new BigDecimal("16.0145");
        ESInvoice.Builder invoiceBuilder = this.injector.getInstance(ESInvoice.Builder.class);

        DAOESCustomer daoESCustomer = this.injector.getInstance(DAOESCustomer.class);

        ESCustomerEntity customerEntity = this.customer.getCustomerEntity();
        UID customerUID = daoESCustomer.create(customerEntity).getUID();

        for (int i = 0; i < 9; i++) {
            ESInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
            invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, entriesPrice);
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        invoiceBuilder.setBilled(ESInvoiceTestUtil.BILLED).setCancelled(ESInvoiceTestUtil.CANCELLED)
                .setSelfBilled(ESInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(ESInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setBusinessUID(this.business.getBusinessEntity().getUID())
                .addPayment(this.payment.getPaymentBuilder());

        return (ESInvoiceEntity) invoiceBuilder.build();
    }

    public ESInvoiceEntity getManyEntriesWithDiferentRegionsInvoice() {
        BigDecimal entriesPrice = new BigDecimal("0.355555");
        ESInvoice.Builder invoiceBuilder = this.injector.getInstance(ESInvoice.Builder.class);

        DAOESCustomer daoESCustomer = this.injector.getInstance(DAOESCustomer.class);

        ESCustomerEntity customerEntity = this.customer.getCustomerEntity();
        UID customerUID = daoESCustomer.create(customerEntity).getUID();

        for (int i = 0; i < 9; i++) {
            ESInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
            invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, entriesPrice);
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        for (int i = 0; i < 9; i++) {
            ESInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceOtherRegionsEntryBuilder();
            invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, entriesPrice);
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        invoiceBuilder.setBilled(ESInvoiceTestUtil.BILLED).setCancelled(ESInvoiceTestUtil.CANCELLED)
                .setSelfBilled(ESInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(ESInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setBusinessUID(this.business.getBusinessEntity().getUID())
                .addPayment(this.payment.getPaymentBuilder());

        return (ESInvoiceEntity) invoiceBuilder.build();
    }

}
