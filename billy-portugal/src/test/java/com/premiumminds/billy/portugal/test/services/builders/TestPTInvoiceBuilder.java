/*
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
package com.premiumminds.billy.portugal.test.services.builders;

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTPayment;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTPayment;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTCustomerEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTInvoiceEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTInvoiceEntryEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTPaymentEntity;
import java.util.ArrayList;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestPTInvoiceBuilder extends PTAbstractTest {

    private static final String PT_INVOICE_YML = AbstractTest.YML_CONFIGS_DIR + "PTInvoice.yml";
    private static final String PT_INVOICE_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "PTInvoiceEntry.yml";
    private static final String PTCUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "PTCustomer.yml";

    private static final String PT_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "PTPayment.yml";

    @Test
    public void testSuccess() {
        MockPTInvoiceEntity mock =
                this.createMockEntity(MockPTInvoiceEntity.class, TestPTInvoiceBuilder.PT_INVOICE_YML);
        mock.setCurrency(Currency.getInstance("EUR"));

        MockPTCustomerEntity mockCustomerEntity =
                this.createMockEntity(MockPTCustomerEntity.class, TestPTInvoiceBuilder.PTCUSTOMER_YML);

        Mockito.when(this.getInstance(DAOPTCustomer.class).get(Mockito.any())).thenReturn(mockCustomerEntity);

        Mockito.when(this.getInstance(DAOPTInvoice.class).getEntityInstance()).thenReturn(new MockPTInvoiceEntity());

        MockPTInvoiceEntryEntity entryMock =
                this.createMockEntity(MockPTInvoiceEntryEntity.class, TestPTInvoiceBuilder.PT_INVOICE_ENTRY_YML);

        Mockito.when(this.getInstance(DAOPTInvoiceEntry.class).get(Mockito.any())).thenReturn(entryMock);

        mock.getEntries().add(entryMock);

        ArrayList<PTInvoiceEntry> entries = (ArrayList<PTInvoiceEntry>) mock.getEntries();

        PTInvoice.Builder builder = this.getInstance(PTInvoice.Builder.class);

        PTInvoiceEntry.Builder entry = this.getMock(PTInvoiceEntry.Builder.class);

        MockPTPaymentEntity mockPayment =
                this.createMockEntity(MockPTPaymentEntity.class, TestPTInvoiceBuilder.PT_PAYMENT_YML);

        Mockito.when(this.getInstance(DAOPTPayment.class).getEntityInstance()).thenReturn(new MockPTPaymentEntity());

        PTPayment.Builder builderPayment = this.getInstance(PTPayment.Builder.class);

        builderPayment.setPaymentAmount(mockPayment.getPaymentAmount()).setPaymentDate(mockPayment.getPaymentDate())
                .setPaymentMethod(mockPayment.getPaymentMethod());

        Mockito.when(entry.build()).thenReturn(entries.get(0));

        builder.addEntry(entry).setBilled(mock.isBilled()).setCancelled(mock.isCancelled())
                .setBatchId(mock.getBatchId()).setDate(mock.getDate()).setGeneralLedgerDate(mock.getGeneralLedgerDate())
                .setOfficeNumber(mock.getOfficeNumber()).setPaymentTerms(mock.getPaymentTerms())
                .setSelfBilled(mock.selfBilled).setSettlementDate(mock.getSettlementDate())
                .setSettlementDescription(mock.getSettlementDescription())
                .setSettlementDiscount(mock.getSettlementDiscount()).setSourceId(mock.getSourceId())
                .setTransactionId(mock.getTransactionId()).setSourceBilling(mock.getSourceBilling())
                .setCustomerUID(mockCustomerEntity.getUID()).addPayment(builderPayment);

        PTInvoice invoice = builder.build();

        Assertions.assertNotNull(invoice);
        Assertions.assertNotNull(invoice.getEntries());
        Assertions.assertEquals(invoice.getEntries().size(), mock.getEntries().size());

        Assertions.assertEquals(invoice.isBilled(), mock.isBilled());
        Assertions.assertEquals(invoice.isCancelled(), mock.isCancelled());

        Assertions.assertEquals(mock.getGeneralLedgerDate(), invoice.getGeneralLedgerDate());
        Assertions.assertEquals(mock.getBatchId(), invoice.getBatchId());
        Assertions.assertEquals(mock.getDate(), invoice.getDate());
        Assertions.assertEquals(mock.getPaymentTerms(), invoice.getPaymentTerms());

        Assertions.assertEquals(0, mock.getAmountWithoutTax().compareTo(invoice.getAmountWithoutTax()));
        Assertions.assertEquals(0, mock.getAmountWithTax().compareTo(invoice.getAmountWithTax()));
        Assertions.assertEquals(0, mock.getTaxAmount().compareTo(invoice.getTaxAmount()));
    }
}
