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

public class ESSimpleInvoiceTestUtil {

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

    public ESSimpleInvoiceTestUtil(Injector injector) {
        this.injector = injector;
        this.invoiceEntry = new ESInvoiceEntryTestUtil(injector);
        this.business = new ESBusinessTestUtil(injector);
        this.customer = new ESCustomerTestUtil(injector);
        this.payment = new ESPaymentTestUtil(injector);
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

        DAOADCustomer daoESCustomer = this.injector.getInstance(DAOADCustomer.class);

        ADCustomerEntity customerEntity = this.customer.getCustomerEntity();
        StringID<Customer> customerUID = daoESCustomer.create(customerEntity).getUID();
        for (int i = 0; i < ESSimpleInvoiceTestUtil.MAX_PRODUCTS; ++i) {
            ADInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        return invoiceBuilder.setBilled(ESInvoiceTestUtil.BILLED).setCancelled(ESInvoiceTestUtil.CANCELLED)
                .setSelfBilled(ESInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(ESInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setBusinessUID(businessEntity.getUID())
                .addPayment(this.payment.getPaymentBuilder()).setClientType(clientType)
                .setCreditOrDebit(GenericInvoice.CreditOrDebit.CREDIT);
    }

}
