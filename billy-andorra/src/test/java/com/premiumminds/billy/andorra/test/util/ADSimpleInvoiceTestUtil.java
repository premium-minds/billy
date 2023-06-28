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

import java.time.LocalDate;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.entities.ADBusinessEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADCustomerEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADSimpleInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADInvoiceEntry;
import com.premiumminds.billy.andorra.services.entities.ADSimpleInvoice;
import com.premiumminds.billy.andorra.services.entities.ADSimpleInvoice.CLIENTTYPE;

public class ADSimpleInvoiceTestUtil {

    private static final int MAX_PRODUCTS = 5;

    protected Injector injector;
    protected ADInvoiceEntryTestUtil invoiceEntry;
    protected ADBusinessTestUtil business;
    protected ADCustomerTestUtil customer;
    protected ADPaymentTestUtil payment;

    public ADSimpleInvoiceTestUtil(Injector injector) {
        this.injector = injector;
        this.invoiceEntry = new ADInvoiceEntryTestUtil(injector);
        this.business = new ADBusinessTestUtil(injector);
        this.customer = new ADCustomerTestUtil(injector);
        this.payment = new ADPaymentTestUtil(injector);
    }

    public ADSimpleInvoiceEntity getSimpleInvoiceEntity() {
        return this.getSimpleInvoiceEntity(CLIENTTYPE.CUSTOMER);
    }

    public ADSimpleInvoiceEntity getSimpleInvoiceEntity(CLIENTTYPE clientType) {
        ADSimpleInvoiceEntity invoice = (ADSimpleInvoiceEntity) this
                .getSimpleInvoiceBuilder(this.business.getBusinessEntity(), clientType).build();

        return invoice;
    }

    public ADSimpleInvoice.Builder getSimpleInvoiceBuilder(ADBusinessEntity businessEntity, CLIENTTYPE clientType) {
        ADSimpleInvoice.Builder invoiceBuilder = this.injector.getInstance(ADSimpleInvoice.Builder.class);

        DAOADCustomer daoADCustomer = this.injector.getInstance(DAOADCustomer.class);

        ADCustomerEntity customerEntity = this.customer.getCustomerEntity();
        StringID<Customer> customerUID = daoADCustomer.create(customerEntity).getUID();
        for (int i = 0; i < ADSimpleInvoiceTestUtil.MAX_PRODUCTS; ++i) {
            ADInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        return invoiceBuilder.setBilled(ADInvoiceTestUtil.BILLED).setCancelled(ADInvoiceTestUtil.CANCELLED)
                             .setSelfBilled(ADInvoiceTestUtil.SELFBILL).setDate(new Date())
                             .setSourceId(ADInvoiceTestUtil.SOURCE_ID)
                             .setCustomerUID(customerUID).setBusinessUID(businessEntity.getUID())
                             .addPayment(this.payment.getPaymentBuilder()).setClientType(clientType)
                             .setCreditOrDebit(GenericInvoice.CreditOrDebit.CREDIT)
                             .setLocalDate(LocalDate.now());
    }

}
