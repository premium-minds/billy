/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.test.services.builders;

import java.util.ArrayList;
import java.util.Currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.dao.DAOSupplier;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockCustomerEntity;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntity;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntryEntity;
import com.premiumminds.billy.core.test.fixtures.MockSupplierEntity;

public class TestGenericInvoiceBuilder extends AbstractTest {

    private static final String INVOICE_YML = AbstractTest.YML_CONFIGS_DIR + "GenericInvoice.yml";
    private static final String ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "GenericInvoiceEntry.yml";
    private static final String CUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "Customer.yml";
    private static final String SUPPLIER_YML = AbstractTest.YML_CONFIGS_DIR + "Supplier.yml";

    @Test
    public void doTest() {
        MockGenericInvoiceEntity mock =
                this.createMockEntity(MockGenericInvoiceEntity.class, TestGenericInvoiceBuilder.INVOICE_YML);

        mock.setCurrency(Currency.getInstance("EUR"));

        Mockito.when(this.getInstance(DAOGenericInvoice.class).getEntityInstance())
                .thenReturn(new MockGenericInvoiceEntity());

        MockCustomerEntity mockCustomerEntity =
                this.createMockEntity(MockCustomerEntity.class, TestGenericInvoiceBuilder.CUSTOMER_YML);
        MockSupplierEntity mockSupplierEntity =
                this.createMockEntity(MockSupplierEntity.class, TestGenericInvoiceBuilder.SUPPLIER_YML);

        Mockito.when(this.getInstance(DAOCustomer.class).get(Mockito.any())).thenReturn(mockCustomerEntity);
        Mockito.when(this.getInstance(DAOSupplier.class).get(Mockito.any())).thenReturn(mockSupplierEntity);

        MockGenericInvoiceEntryEntity mockInvoice =
                this.createMockEntity(MockGenericInvoiceEntryEntity.class, TestGenericInvoiceBuilder.ENTRY_YML);

        Mockito.when(this.getInstance(DAOGenericInvoiceEntry.class).get(Mockito.any()))
                .thenReturn(mockInvoice);

        mock.getEntries().add(mockInvoice);

        ArrayList<GenericInvoiceEntry> invoiceEntrys = (ArrayList<GenericInvoiceEntry>) mock.getEntries();

        GenericInvoice.Builder builder = this.getInstance(GenericInvoice.Builder.class);

        GenericInvoiceEntry.Builder invoice1 = this.getMock(GenericInvoiceEntry.Builder.class);
        Mockito.when(invoice1.build()).thenReturn(invoiceEntrys.get(0));

        builder.addEntry(invoice1).setBatchId(mock.getBatchId()).setDate(mock.getDate())
                .setGeneralLedgerDate(mock.getGeneralLedgerDate()).setOfficeNumber(mock.getOfficeNumber())
                .setPaymentTerms(mock.getPaymentTerms()).setSelfBilled(mock.selfBilled)
                .setSettlementDate(mock.getSettlementDate()).setSettlementDescription(mock.getSettlementDescription())
                .setSettlementDiscount(mock.getSettlementDiscount()).setSourceId(mock.getSourceId())
                .setTransactionId(mock.getTransactionId()).setCustomerUID(mockCustomerEntity.getUID())
                .setSupplierUID(mockSupplierEntity.getUID()).setCreditOrDebit(GenericInvoice.CreditOrDebit.CREDIT);

        GenericInvoice invoice = builder.build();

        Assertions.assertTrue(invoice != null);
        Assertions.assertTrue(mock.getAmountWithoutTax().compareTo(invoice.getAmountWithoutTax()) == 0);
        Assertions.assertTrue(mock.getAmountWithTax().compareTo(invoice.getAmountWithTax()) == 0);
        Assertions.assertTrue(mock.getTaxAmount().compareTo(invoice.getTaxAmount()) == 0);

        Assertions.assertEquals(mock.getGeneralLedgerDate(), invoice.getGeneralLedgerDate());
        Assertions.assertEquals(mock.getBatchId(), invoice.getBatchId());
        Assertions.assertEquals(mock.getDate(), invoice.getDate());
        Assertions.assertEquals(mock.getPaymentTerms(), invoice.getPaymentTerms());

        Assertions.assertTrue(invoice.getEntries() != null);
        Assertions.assertEquals(invoice.getEntries().size(), mock.getEntries().size());

        for (int i = 0; i < invoice.getEntries().size(); i++) {
            ArrayList<GenericInvoiceEntry> invoices = (ArrayList<GenericInvoiceEntry>) invoice.getEntries();
            ArrayList<GenericInvoiceEntry> mockInvoices = (ArrayList<GenericInvoiceEntry>) mock.getEntries();
            Assertions.assertEquals(invoices.get(i).getUnitAmountWithoutTax(),
                    mockInvoices.get(i).getUnitAmountWithoutTax());
            Assertions.assertEquals(invoices.get(i).getUnitAmountWithTax(), mockInvoices.get(i).getUnitAmountWithTax());
            Assertions.assertEquals(invoices.get(i).getUnitTaxAmount(), mockInvoices.get(i).getUnitTaxAmount());
            Assertions.assertTrue(invoices.get(i).equals(mockInvoices.get(i)));
        }

    }
}
