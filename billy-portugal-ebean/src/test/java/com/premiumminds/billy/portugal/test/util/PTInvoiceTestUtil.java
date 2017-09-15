/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.util;

import java.math.BigDecimal;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;

public class PTInvoiceTestUtil {

    protected static final Boolean BILLED = false;
    protected static final Boolean CANCELLED = false;
    protected static final Boolean SELFBILL = false;
    protected static final String SOURCE_ID = "SOURCE";
    protected static final String SERIE = "A";
    protected static final Integer SERIE_NUMBER = 1;
    protected static final int MAX_PRODUCTS = 5;

    protected TYPE INVOICE_TYPE;
    protected Injector injector;
    protected PTInvoiceEntryTestUtil invoiceEntry;
    protected PTBusinessTestUtil business;
    protected PTCustomerTestUtil customer;
    protected PTPaymentTestUtil payment;

    public PTInvoiceTestUtil(Injector injector) {
        this.injector = injector;
        this.INVOICE_TYPE = TYPE.FT;
        this.invoiceEntry = new PTInvoiceEntryTestUtil(injector);
        this.business = new PTBusinessTestUtil(injector);
        this.customer = new PTCustomerTestUtil(injector);
        this.payment = new PTPaymentTestUtil(injector);
    }

    public PTInvoiceEntity getInvoiceEntity() {
        return this.getInvoiceEntity(SourceBilling.P);
    }

    public PTInvoiceEntity getInvoiceEntity(SourceBilling billing) {
        PTInvoiceEntity invoice;
        switch (billing) {
            case M:
                invoice = (PTInvoiceEntity) this.getManualInvoiceBuilder(this.business.getBusinessEntity(), billing)
                        .build();
                break;
            case P:
            default:
                invoice = (PTInvoiceEntity) this.getInvoiceBuilder(this.business.getBusinessEntity(), billing).build();
                break;
        }

        invoice.setType(this.INVOICE_TYPE);

        return invoice;
    }

    public PTInvoice.Builder getInvoiceBuilder(PTBusinessEntity business, SourceBilling billing) {
        BigDecimal price = new BigDecimal("0.454545");
        PTInvoice.Builder invoiceBuilder = this.injector.getInstance(PTInvoice.Builder.class);

        DAOPTCustomer daoPTCustomer = this.injector.getInstance(DAOPTCustomer.class);

        PTCustomerEntity customerEntity = this.customer.getCustomerEntity();
        UID customerUID = daoPTCustomer.create(customerEntity).getUID();

        PTInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
        invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, price);
        invoiceBuilder.addEntry(invoiceEntryBuilder);

        return invoiceBuilder.setBilled(PTInvoiceTestUtil.BILLED).setCancelled(PTInvoiceTestUtil.CANCELLED)
                .setSelfBilled(PTInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(PTInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setSourceBilling(billing).setBusinessUID(business.getUID())
                .addPayment(this.payment.getPaymentBuilder());
    }

    public PTInvoice.ManualBuilder getManualInvoiceBuilder(PTBusinessEntity business, SourceBilling billing) {
        BigDecimal price = new BigDecimal("0.454545");
        BigDecimal tax = new BigDecimal("0.084996");
        PTInvoice.ManualBuilder invoiceBuilder = this.injector.getInstance(PTInvoice.ManualBuilder.class);

        DAOPTCustomer daoPTCustomer = this.injector.getInstance(DAOPTCustomer.class);

        PTCustomerEntity customerEntity = this.customer.getCustomerEntity();
        UID customerUID = daoPTCustomer.create(customerEntity).getUID();

        PTInvoiceEntry.ManualBuilder invoiceEntryBuilder = this.invoiceEntry.getManualInvoiceEntryBuilder()
                .setUnitAmount(AmountType.WITH_TAX, price).setUnitAmount(AmountType.WITHOUT_TAX, price.subtract(tax))
                .setUnitTaxAmount(tax).setAmount(AmountType.WITH_TAX, price)
                .setAmount(AmountType.WITHOUT_TAX, price.subtract(tax)).setTaxAmount(tax);
        invoiceBuilder.addEntry(invoiceEntryBuilder);

        return invoiceBuilder.setBilled(PTInvoiceTestUtil.BILLED).setCancelled(PTInvoiceTestUtil.CANCELLED)
                .setSelfBilled(PTInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(PTInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setSourceBilling(billing).setBusinessUID(business.getUID())
                .setAmount(AmountType.WITH_TAX, price).setAmount(AmountType.WITHOUT_TAX, price.subtract(tax))
                .setTaxAmount(tax).addPayment(this.payment.getPaymentBuilder());
    }

    public PTInvoiceEntity getDiferentRegionsInvoice() {
        BigDecimal price = new BigDecimal("0.2999");

        PTInvoice.Builder invoiceBuilder = this.injector.getInstance(PTInvoice.Builder.class);

        DAOPTCustomer daoPTCustomer = this.injector.getInstance(DAOPTCustomer.class);

        PTCustomerEntity customerEntity = this.customer.getCustomerEntity();
        UID customerUID = daoPTCustomer.create(customerEntity).getUID();

        PTInvoiceEntry.Builder invoiceEntryBuilder2 = this.invoiceEntry.getInvoiceOtherRegionsEntryBuilder("PT-20");
        invoiceEntryBuilder2.setUnitAmount(AmountType.WITH_TAX, price);
        invoiceBuilder.addEntry(invoiceEntryBuilder2);

        PTInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceOtherRegionsEntryBuilder("PT-30");
        invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, price);
        invoiceBuilder.addEntry(invoiceEntryBuilder);

        PTInvoiceEntry.Builder invoiceEntryBuilder3 = this.invoiceEntry.getInvoiceEntryBuilder();
        invoiceEntryBuilder3.setUnitAmount(AmountType.WITH_TAX, price);
        invoiceBuilder.addEntry(invoiceEntryBuilder3);

        invoiceBuilder.setBilled(PTInvoiceTestUtil.BILLED).setCancelled(PTInvoiceTestUtil.CANCELLED)
                .setSelfBilled(PTInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(PTInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setSourceBilling(SourceBilling.P)
                .setBusinessUID(this.business.getBusinessEntity().getUID())
                .addPayment(this.payment.getPaymentBuilder());

        return (PTInvoiceEntity) invoiceBuilder.build();
    }

    public PTInvoiceEntity getManyEntriesInvoice() {
        BigDecimal entriesPrice = new BigDecimal("16.0145");
        PTInvoice.Builder invoiceBuilder = this.injector.getInstance(PTInvoice.Builder.class);

        DAOPTCustomer daoPTCustomer = this.injector.getInstance(DAOPTCustomer.class);

        PTCustomerEntity customerEntity = this.customer.getCustomerEntity();
        UID customerUID = daoPTCustomer.create(customerEntity).getUID();

        for (int i = 0; i < 9; i++) {
            PTInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
            invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, entriesPrice);
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        invoiceBuilder.setBilled(PTInvoiceTestUtil.BILLED).setCancelled(PTInvoiceTestUtil.CANCELLED)
                .setSelfBilled(PTInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(PTInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setSourceBilling(SourceBilling.P)
                .setBusinessUID(this.business.getBusinessEntity().getUID())
                .addPayment(this.payment.getPaymentBuilder());

        return (PTInvoiceEntity) invoiceBuilder.build();
    }

    public PTInvoiceEntity getManyEntriesWithDiferentRegionsInvoice() {
        BigDecimal entriesPrice = new BigDecimal("0.355555");
        PTInvoice.Builder invoiceBuilder = this.injector.getInstance(PTInvoice.Builder.class);

        DAOPTCustomer daoPTCustomer = this.injector.getInstance(DAOPTCustomer.class);

        PTCustomerEntity customerEntity = this.customer.getCustomerEntity();
        UID customerUID = daoPTCustomer.create(customerEntity).getUID();

        for (int i = 0; i < 9; i++) {
            PTInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
            invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, entriesPrice);
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        for (int i = 0; i < 9; i++) {
            PTInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceOtherRegionsEntryBuilder("PT-20");
            invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, entriesPrice);
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        invoiceBuilder.setBilled(PTInvoiceTestUtil.BILLED).setCancelled(PTInvoiceTestUtil.CANCELLED)
                .setSelfBilled(PTInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(PTInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setSourceBilling(SourceBilling.P)
                .setBusinessUID(this.business.getBusinessEntity().getUID())
                .addPayment(this.payment.getPaymentBuilder());

        return (PTInvoiceEntity) invoiceBuilder.build();
    }

}
