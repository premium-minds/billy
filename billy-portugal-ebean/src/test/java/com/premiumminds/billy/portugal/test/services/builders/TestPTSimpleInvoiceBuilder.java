/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.builders;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTPayment;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTPayment;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice.CLIENTTYPE;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTCustomerEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTInvoiceEntryEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTPaymentEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTSimpleInvoiceEntity;

public class TestPTSimpleInvoiceBuilder extends PTAbstractTest {

    /*private static final String PT_INVOICE_YML = AbstractTest.YML_CONFIGS_DIR + "PTInvoice.yml";
    private static final String PT_INVOICE_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "PTInvoiceEntry.yml";

    private static final String PTCUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "PTCustomer.yml";

    private static final String PT_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "PTPayment.yml";

    @Test
    public void doTest() {
        MockPTSimpleInvoiceEntity mock =
                this.createMockEntity(MockPTSimpleInvoiceEntity.class, TestPTSimpleInvoiceBuilder.PT_INVOICE_YML);

        MockPTCustomerEntity mockCustomerEntity =
                this.createMockEntity(MockPTCustomerEntity.class, TestPTSimpleInvoiceBuilder.PTCUSTOMER_YML);

        mock.setClientType(CLIENTTYPE.CUSTOMER);

        Mockito.when(this.getInstance(DAOPTSimpleInvoice.class).getEntityInstance())
                .thenReturn(new MockPTSimpleInvoiceEntity());

        Mockito.when(this.getInstance(DAOPTCustomer.class).get(Matchers.any(UID.class))).thenReturn(mockCustomerEntity);

        MockPTInvoiceEntryEntity entryMock =
                this.createMockEntity(MockPTInvoiceEntryEntity.class, TestPTSimpleInvoiceBuilder.PT_INVOICE_ENTRY_YML);

        Mockito.when(this.getInstance(DAOPTInvoiceEntry.class).get(Matchers.any(UID.class))).thenReturn(entryMock);

        mock.getEntries().add(entryMock);

        ArrayList<PTInvoiceEntry> entries = (ArrayList<PTInvoiceEntry>) mock.getEntries();

        PTSimpleInvoice.Builder builder = this.getInstance(PTSimpleInvoice.Builder.class);

        PTInvoiceEntry.Builder entry = this.getMock(PTInvoiceEntry.Builder.class);

        Mockito.when(entry.build()).thenReturn(entries.get(0));

        MockPTPaymentEntity mockPayment =
                this.createMockEntity(MockPTPaymentEntity.class, TestPTSimpleInvoiceBuilder.PT_PAYMENT_YML);

        Mockito.when(this.getInstance(DAOPTPayment.class).getEntityInstance()).thenReturn(new MockPTPaymentEntity());

        PTPayment.Builder builderPayment = this.getInstance(PTPayment.Builder.class);

        builderPayment.setPaymentAmount(mockPayment.getPaymentAmount()).setPaymentDate(mockPayment.getPaymentDate())
                .setPaymentMethod(mockPayment.getPaymentMethod());

        builder.addEntry(entry).setBilled(mock.isBilled()).setCancelled(mock.isCancelled())
                .setBatchId(mock.getBatchId()).setDate(mock.getDate()).setGeneralLedgerDate(mock.getGeneralLedgerDate())
                .setOfficeNumber(mock.getOfficeNumber()).setPaymentTerms(mock.getPaymentTerms())
                .setSelfBilled(mock.selfBilled).setSettlementDate(mock.getSettlementDate())
                .setSettlementDescription(mock.getSettlementDescription())
                .setSettlementDiscount(mock.getSettlementDiscount()).setSourceId(mock.getSourceId())
                .setTransactionId(mock.getTransactionId()).setSourceBilling(mock.getSourceBilling())
                .setClientType(mock.getClientType()).setCustomerUID(mockCustomerEntity.getUID())
                .addPayment(builderPayment);

        PTSimpleInvoice invoice = builder.build();

        Assert.assertTrue(invoice != null);
        Assert.assertTrue(invoice.getEntries() != null);
        Assert.assertEquals(invoice.getEntries().size(), mock.getEntries().size());

        Assert.assertTrue(invoice.isBilled() == mock.isBilled());
        Assert.assertTrue(invoice.isCancelled() == mock.isCancelled());

        Assert.assertEquals(mock.getGeneralLedgerDate(), invoice.getGeneralLedgerDate());
        Assert.assertEquals(mock.getBatchId(), invoice.getBatchId());
        Assert.assertEquals(mock.getDate(), invoice.getDate());
        Assert.assertEquals(mock.getPaymentTerms(), invoice.getPaymentTerms());

        Assert.assertTrue(mock.getAmountWithoutTax().compareTo(invoice.getAmountWithoutTax()) == 0);
        Assert.assertTrue(mock.getAmountWithTax().compareTo(invoice.getAmountWithTax()) == 0);
        Assert.assertTrue(mock.getTaxAmount().compareTo(invoice.getTaxAmount()) == 0);
    }*/

}
