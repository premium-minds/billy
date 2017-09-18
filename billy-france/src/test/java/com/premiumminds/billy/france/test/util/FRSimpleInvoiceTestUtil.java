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

import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.entities.FRBusinessEntity;
import com.premiumminds.billy.france.persistence.entities.FRCustomerEntity;
import com.premiumminds.billy.france.persistence.entities.FRSimpleInvoiceEntity;
import com.premiumminds.billy.france.services.entities.FRInvoiceEntry;
import com.premiumminds.billy.france.services.entities.FRSimpleInvoice;
import com.premiumminds.billy.france.services.entities.FRSimpleInvoice.CLIENTTYPE;

public class FRSimpleInvoiceTestUtil {

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

    public FRSimpleInvoiceTestUtil(Injector injector) {
        this.injector = injector;
        this.invoiceEntry = new FRInvoiceEntryTestUtil(injector);
        this.business = new FRBusinessTestUtil(injector);
        this.customer = new FRCustomerTestUtil(injector);
        this.payment = new FRPaymentTestUtil(injector);
    }

    public FRSimpleInvoiceEntity getSimpleInvoiceEntity() {
        return this.getSimpleInvoiceEntity(CLIENTTYPE.CUSTOMER);
    }

    public FRSimpleInvoiceEntity getSimpleInvoiceEntity(CLIENTTYPE clientType) {
        FRSimpleInvoiceEntity invoice = (FRSimpleInvoiceEntity) this
                .getSimpleInvoiceBuilder(this.business.getBusinessEntity(), clientType).build();

        return invoice;
    }

    public FRSimpleInvoice.Builder getSimpleInvoiceBuilder(FRBusinessEntity businessEntity, CLIENTTYPE clientType) {
        FRSimpleInvoice.Builder invoiceBuilder = this.injector.getInstance(FRSimpleInvoice.Builder.class);

        DAOFRCustomer daoFRCustomer = this.injector.getInstance(DAOFRCustomer.class);

        FRCustomerEntity customerEntity = this.customer.getCustomerEntity();
        UID customerUID = daoFRCustomer.create(customerEntity).getUID();
        for (int i = 0; i < FRSimpleInvoiceTestUtil.MAX_PRODUCTS; ++i) {
            FRInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry.getInvoiceEntryBuilder();
            invoiceBuilder.addEntry(invoiceEntryBuilder);
        }

        return invoiceBuilder.setBilled(FRInvoiceTestUtil.BILLED).setCancelled(FRInvoiceTestUtil.CANCELLED)
                .setSelfBilled(FRInvoiceTestUtil.SELFBILL).setDate(new Date()).setSourceId(FRInvoiceTestUtil.SOURCE_ID)
                .setCustomerUID(customerUID).setBusinessUID(businessEntity.getUID())
                .addPayment(this.payment.getPaymentBuilder()).setClientType(clientType);
    }

}
