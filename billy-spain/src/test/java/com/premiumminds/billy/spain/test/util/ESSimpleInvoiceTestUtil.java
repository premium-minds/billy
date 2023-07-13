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

import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.entities.ESBusinessEntity;
import com.premiumminds.billy.spain.persistence.entities.ESCustomerEntity;
import com.premiumminds.billy.spain.persistence.entities.ESSimpleInvoiceEntity;
import com.premiumminds.billy.spain.services.entities.ESInvoiceEntry;
import com.premiumminds.billy.spain.services.entities.ESSimpleInvoice;
import com.premiumminds.billy.spain.services.entities.ESSimpleInvoice.CLIENTTYPE;

public class ESSimpleInvoiceTestUtil {

    private static final int MAX_PRODUCTS = 5;

    protected Injector injector;
    protected ESInvoiceEntryTestUtil invoiceEntry;
    protected ESBusinessTestUtil business;
    protected ESCustomerTestUtil customer;
    protected ESPaymentTestUtil payment;

    public ESSimpleInvoiceTestUtil(Injector injector) {
        this.injector = injector;
        this.invoiceEntry = new ESInvoiceEntryTestUtil(injector);
        this.business = new ESBusinessTestUtil(injector);
        this.customer = new ESCustomerTestUtil(injector);
        this.payment = new ESPaymentTestUtil(injector);
    }

    public ESSimpleInvoiceEntity getSimpleInvoiceEntity() {
        return this.getSimpleInvoiceEntity(CLIENTTYPE.CUSTOMER);
    }

    public ESSimpleInvoiceEntity getSimpleInvoiceEntity(CLIENTTYPE clientType) {
        return (ESSimpleInvoiceEntity) this
            .getSimpleInvoiceBuilder(this.business.getBusinessEntity(), clientType).build();
    }

    public ESSimpleInvoice.Builder getSimpleInvoiceBuilder(ESBusinessEntity businessEntity, CLIENTTYPE clientType) {
        ESSimpleInvoice.Builder invoiceBuilder = this.injector.getInstance(ESSimpleInvoice.Builder.class);

        DAOESCustomer daoESCustomer = this.injector.getInstance(DAOESCustomer.class);

        ESCustomerEntity customerEntity = this.customer.getCustomerEntity();
        StringID<Customer> customerUID = daoESCustomer.create(customerEntity).getUID();
        for (int i = 0; i < ESSimpleInvoiceTestUtil.MAX_PRODUCTS; ++i) {
            ESInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        return invoiceBuilder.setBilled(ESInvoiceTestUtil.BILLED).setCancelled(ESInvoiceTestUtil.CANCELLED)
                .setSelfBilled(ESInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(ESInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setBusinessUID(businessEntity.getUID())
                .addPayment(this.payment.getPaymentBuilder()).setClientType(clientType)
                .setCreditOrDebit(GenericInvoice.CreditOrDebit.CREDIT)
                .setLocalDate(LocalDate.now());
    }

    public ESSimpleInvoiceEntity getSimpleInvoiceEntityOverMaxForCustomer() {
        return (ESSimpleInvoiceEntity) this
            .getSimpleInvoiceBuilderOverMaxForCustomer(this.business.getBusinessEntity()).build();
    }

    public ESSimpleInvoice.Builder getSimpleInvoiceBuilderOverMaxForCustomer(ESBusinessEntity businessEntity) {
        ESSimpleInvoice.Builder invoiceBuilder = this.injector.getInstance(ESSimpleInvoice.Builder.class);

        DAOESCustomer daoESCustomer = this.injector.getInstance(DAOESCustomer.class);

        ESCustomerEntity customerEntity = this.customer.getCustomerEntity();
        StringID<Customer> customerUID = daoESCustomer.create(customerEntity).getUID();
        ESInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
        invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, new BigDecimal("3001"));
        invoiceBuilder.addEntry(invoiceEntryBuilder);

        return invoiceBuilder
            .setBilled(ESInvoiceTestUtil.BILLED)
            .setCancelled(ESInvoiceTestUtil.CANCELLED)
            .setSelfBilled(ESInvoiceTestUtil.SELFBILL)
            .setDate(new Date())
            .setSourceId(ESInvoiceTestUtil.SOURCE_ID)
            .setCustomerUID(customerUID)
            .setBusinessUID(businessEntity.getUID())
            .addPayment(this.payment.getPaymentBuilder())
            .setClientType(CLIENTTYPE.CUSTOMER)
            .setCreditOrDebit(GenericInvoice.CreditOrDebit.CREDIT)
            .setLocalDate(LocalDate.now());
    }

}
