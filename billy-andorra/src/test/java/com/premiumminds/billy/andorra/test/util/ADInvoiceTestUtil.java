/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.test.util;

import com.google.inject.Injector;
import com.premiumminds.billy.andorra.persistence.entities.ADBusinessEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADCustomerEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.services.entities.ADInvoice;
import com.premiumminds.billy.andorra.services.entities.ADInvoiceEntry;
import com.premiumminds.billy.andorra.test.services.documents.ADDocumentAbstractTest.SOURCE_BILLING;
import java.math.BigDecimal;
import java.util.Date;

public class ADInvoiceTestUtil {

    protected static final Boolean BILLED = false;
    protected static final Boolean CANCELLED = false;
    protected static final Boolean SELFBILL = false;
    protected static final String SOURCE_ID = "SOURCE";
    protected static final String SERIE = "A";
    protected static final Integer SERIE_NUMBER = 1;
    protected static final int MAX_PRODUCTS = 5;

    protected Injector injector;
    protected ADInvoiceEntryTestUtil invoiceEntry;
    protected ADBusinessTestUtil business;
    protected ADCustomerTestUtil customer;
    protected ADPaymentTestUtil payment;

    public ADInvoiceTestUtil(Injector injector) {
        this.injector = injector;
        this.invoiceEntry = new ADInvoiceEntryTestUtil(injector);
        this.business = new ADBusinessTestUtil(injector);
        this.customer = new ADCustomerTestUtil(injector);
        this.payment = new ADPaymentTestUtil(injector);
    }

    public ADInvoiceEntity getInvoiceEntity() {
        return this.getInvoiceEntity(SOURCE_BILLING.APPLICATION);
    }

    public ADInvoiceEntity getInvoiceEntity(SOURCE_BILLING billing) {
        ADInvoiceEntity invoice;
        switch (billing) {
            case MANUAL:
                invoice = (ADInvoiceEntity) this.getManualInvoiceBuilder(this.business.getBusinessEntity()).build();
                break;
            case APPLICATION:
            default:
                invoice = (ADInvoiceEntity) this.getInvoiceBuilder(this.business.getBusinessEntity()).build();
                break;
        }

        return invoice;
    }

    public ADInvoice.Builder getInvoiceBuilder(ADBusinessEntity business) {
        BigDecimal price = new BigDecimal("0.450");
        ADInvoice.Builder invoiceBuilder = this.injector.getInstance(ADInvoice.Builder.class);

        DAOADCustomer daoADCustomer = this.injector.getInstance(DAOADCustomer.class);

        ADCustomerEntity customerEntity = this.customer.getCustomerEntity();
        StringID<Customer> customerUID = daoADCustomer.create(customerEntity).getUID();

        ADInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
        invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, price);
        invoiceBuilder.addEntry(invoiceEntryBuilder);

        return invoiceBuilder.setBilled(ADInvoiceTestUtil.BILLED).setCancelled(ADInvoiceTestUtil.CANCELLED)
							 .setSelfBilled(ADInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(
				ADInvoiceTestUtil.SOURCE_ID)
							 .setCustomerUID(customerUID).setBusinessUID(business.getUID())
							 .addPayment(this.payment.getPaymentBuilder()).setCreditOrDebit(GenericInvoice.CreditOrDebit.CREDIT);
    }

    public ADInvoice.ManualBuilder getManualInvoiceBuilder(ADBusinessEntity business) {
        BigDecimal price = new BigDecimal("0.450");
        BigDecimal tax = new BigDecimal("0.078");
        ADInvoice.ManualBuilder invoiceBuilder = this.injector.getInstance(ADInvoice.ManualBuilder.class);

        DAOADCustomer daoADCustomer = this.injector.getInstance(DAOADCustomer.class);

        ADCustomerEntity customerEntity = this.customer.getCustomerEntity();
        StringID<Customer> customerUID = daoADCustomer.create(customerEntity).getUID();

        ADInvoiceEntry.ManualBuilder invoiceEntryBuilder = this.invoiceEntry.getManualInvoiceEntryBuilder()
																			.setUnitAmount(AmountType.WITH_TAX, price).setUnitAmount(AmountType.WITHOUT_TAX, price.subtract(tax))
																			.setUnitTaxAmount(tax).setAmount(AmountType.WITH_TAX, price)
																			.setAmount(AmountType.WITHOUT_TAX, price.subtract(tax)).setTaxAmount(tax);
        invoiceBuilder.addEntry(invoiceEntryBuilder);

        return invoiceBuilder.setBilled(ADInvoiceTestUtil.BILLED).setCancelled(ADInvoiceTestUtil.CANCELLED)
							 .setSelfBilled(ADInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(
				ADInvoiceTestUtil.SOURCE_ID)
							 .setCustomerUID(customerUID).setBusinessUID(business.getUID()).setAmount(AmountType.WITH_TAX, price)
							 .setAmount(AmountType.WITHOUT_TAX, price.subtract(tax)).setTaxAmount(tax)
							 .addPayment(this.payment.getPaymentBuilder());
    }

    public ADInvoiceEntity getDifferentRegionsInvoice() {
        BigDecimal entriesPrice = new BigDecimal("16.0145");
        ADInvoice.Builder invoiceBuilder = this.injector.getInstance(ADInvoice.Builder.class);

        DAOADCustomer daoADCustomer = this.injector.getInstance(DAOADCustomer.class);

        ADCustomerEntity customerEntity = this.customer.getCustomerEntity();
        StringID<Customer> customerUID = daoADCustomer.create(customerEntity).getUID();

        for (int i = 0; i < 9; i++) {
            ADInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
            invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, entriesPrice);
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        invoiceBuilder.setBilled(ADInvoiceTestUtil.BILLED).setCancelled(ADInvoiceTestUtil.CANCELLED)
					  .setSelfBilled(ADInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(ADInvoiceTestUtil.SOURCE_ID)
					  .setCustomerUID(customerUID).setBusinessUID(this.business.getBusinessEntity().getUID())
					  .addPayment(this.payment.getPaymentBuilder());

        return (ADInvoiceEntity) invoiceBuilder.build();
    }

    public ADInvoiceEntity getManyEntriesInvoice() {
        BigDecimal entriesPrice = new BigDecimal("16.0145");
        ADInvoice.Builder invoiceBuilder = this.injector.getInstance(ADInvoice.Builder.class);

        DAOADCustomer daoADCustomer = this.injector.getInstance(DAOADCustomer.class);

        ADCustomerEntity customerEntity = this.customer.getCustomerEntity();
        StringID<Customer> customerUID = daoADCustomer.create(customerEntity).getUID();

        for (int i = 0; i < 9; i++) {
            ADInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
            invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, entriesPrice);
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        invoiceBuilder.setBilled(ADInvoiceTestUtil.BILLED).setCancelled(ADInvoiceTestUtil.CANCELLED)
					  .setSelfBilled(ADInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(ADInvoiceTestUtil.SOURCE_ID)
					  .setCustomerUID(customerUID).setBusinessUID(this.business.getBusinessEntity().getUID())
					  .addPayment(this.payment.getPaymentBuilder());

        return (ADInvoiceEntity) invoiceBuilder.build();
    }

    public ADInvoiceEntity getManyEntriesWithDifferentRegionsInvoice() {
        BigDecimal entriesPrice = new BigDecimal("0.355555");
        ADInvoice.Builder invoiceBuilder = this.injector.getInstance(ADInvoice.Builder.class);

        DAOADCustomer daoADCustomer = this.injector.getInstance(DAOADCustomer.class);

        ADCustomerEntity customerEntity = this.customer.getCustomerEntity();
        StringID<Customer> customerUID = daoADCustomer.create(customerEntity).getUID();

        for (int i = 0; i < 9; i++) {
            ADInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
            invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, entriesPrice);
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        for (int i = 0; i < 9; i++) {
            ADInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceOtherRegionsEntryBuilder();
            invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, entriesPrice);
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        invoiceBuilder.setBilled(ADInvoiceTestUtil.BILLED).setCancelled(ADInvoiceTestUtil.CANCELLED)
					  .setSelfBilled(ADInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(ADInvoiceTestUtil.SOURCE_ID)
					  .setCustomerUID(customerUID).setBusinessUID(this.business.getBusinessEntity().getUID())
					  .addPayment(this.payment.getPaymentBuilder());

        return (ADInvoiceEntity) invoiceBuilder.build();
    }

}
