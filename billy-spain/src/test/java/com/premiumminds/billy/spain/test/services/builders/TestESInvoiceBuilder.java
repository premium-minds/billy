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
package com.premiumminds.billy.spain.test.services.builders;

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoiceEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESPayment;
import com.premiumminds.billy.spain.services.entities.ESInvoice;
import com.premiumminds.billy.spain.services.entities.ESInvoiceEntry;
import com.premiumminds.billy.spain.services.entities.ESPayment;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESCustomerEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESInvoiceEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESInvoiceEntryEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESPaymentEntity;
import java.util.ArrayList;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestESInvoiceBuilder extends ESAbstractTest {

    private static final String ES_INVOICE_YML = AbstractTest.YML_CONFIGS_DIR + "ESInvoice.yml";
    private static final String ES_INVOICE_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "ESInvoiceEntry.yml";
    private static final String ESCUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "ESCustomer.yml";

    private static final String ES_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "ESPayment.yml";

    @Test
    public void doTest() {
        MockESInvoiceEntity mock =
                this.createMockEntity(MockESInvoiceEntity.class, TestESInvoiceBuilder.ES_INVOICE_YML);
        mock.setCurrency(Currency.getInstance("EUR"));

        MockESCustomerEntity mockCustomerEntity =
                this.createMockEntity(MockESCustomerEntity.class, TestESInvoiceBuilder.ESCUSTOMER_YML);

        Mockito.when(this.getInstance(DAOESCustomer.class).get(Mockito.any())).thenReturn(mockCustomerEntity);

        Mockito.when(this.getInstance(DAOESInvoice.class).getEntityInstance()).thenReturn(new MockESInvoiceEntity());

        MockESInvoiceEntryEntity entryMock =
                this.createMockEntity(MockESInvoiceEntryEntity.class, TestESInvoiceBuilder.ES_INVOICE_ENTRY_YML);

        Mockito.when(this.getInstance(DAOESInvoiceEntry.class).get(Mockito.any())).thenReturn(entryMock);

        mock.getEntries().add(entryMock);

        ArrayList<ESInvoiceEntry> entries = (ArrayList<ESInvoiceEntry>) mock.getEntries();

        ESInvoice.Builder builder = this.getInstance(ESInvoice.Builder.class);

        ESInvoiceEntry.Builder entry = this.getMock(ESInvoiceEntry.Builder.class);

        MockESPaymentEntity mockPayment =
                this.createMockEntity(MockESPaymentEntity.class, TestESInvoiceBuilder.ES_PAYMENT_YML);

        Mockito.when(this.getInstance(DAOESPayment.class).getEntityInstance()).thenReturn(new MockESPaymentEntity());

        ESPayment.Builder builderPayment = this.getInstance(ESPayment.Builder.class);

        builderPayment.setPaymentAmount(mockPayment.getPaymentAmount()).setPaymentDate(mockPayment.getPaymentDate())
                .setPaymentMethod(mockPayment.getPaymentMethod());

        Mockito.when(entry.build()).thenReturn(entries.get(0));

        builder.addEntry(entry).setBilled(mock.isBilled()).setCancelled(mock.isCancelled())
                .setBatchId(mock.getBatchId()).setDate(mock.getDate()).setGeneralLedgerDate(mock.getGeneralLedgerDate())
                .setOfficeNumber(mock.getOfficeNumber()).setPaymentTerms(mock.getPaymentTerms())
                .setSelfBilled(mock.selfBilled).setSettlementDate(mock.getSettlementDate())
                .setSettlementDescription(mock.getSettlementDescription())
                .setSettlementDiscount(mock.getSettlementDiscount()).setSourceId(mock.getSourceId())
                .setTransactionId(mock.getTransactionId()).setCustomerUID(mockCustomerEntity.getUID())
                .addPayment(builderPayment);

        ESInvoice invoice = builder.build();

        Assertions.assertTrue(invoice != null);
        Assertions.assertTrue(invoice.getEntries() != null);
        Assertions.assertEquals(invoice.getEntries().size(), mock.getEntries().size());

        Assertions.assertTrue(invoice.isBilled() == mock.isBilled());
        Assertions.assertTrue(invoice.isCancelled() == mock.isCancelled());

        Assertions.assertEquals(mock.getGeneralLedgerDate(), invoice.getGeneralLedgerDate());
        Assertions.assertEquals(mock.getBatchId(), invoice.getBatchId());
        Assertions.assertEquals(mock.getDate(), invoice.getDate());
        Assertions.assertEquals(mock.getPaymentTerms(), invoice.getPaymentTerms());

        Assertions.assertTrue(mock.getAmountWithoutTax().compareTo(invoice.getAmountWithoutTax()) == 0);
        Assertions.assertTrue(mock.getAmountWithTax().compareTo(invoice.getAmountWithTax()) == 0);
        Assertions.assertTrue(mock.getTaxAmount().compareTo(invoice.getTaxAmount()) == 0);
    }
}
