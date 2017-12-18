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
package com.premiumminds.billy.france.test.services.builders;

import java.util.ArrayList;
import java.util.Currency;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.dao.DAOFRGenericInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRGenericInvoiceEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRPayment;
import com.premiumminds.billy.france.services.entities.FRGenericInvoice;
import com.premiumminds.billy.france.services.entities.FRGenericInvoiceEntry;
import com.premiumminds.billy.france.services.entities.FRPayment;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRCustomerEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRGenericInvoiceEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRGenericInvoiceEntryEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRPaymentEntity;

public class TestFRGenericInvoiceBuilder extends FRAbstractTest {

    private static final String FR_GENERIC_INVOICE_YML = AbstractTest.YML_CONFIGS_DIR + "FRGenericInvoice.yml";
    private static final String FR_GENERIC_INVOICE_ENTRY_YML =
            AbstractTest.YML_CONFIGS_DIR + "FRGenericInvoiceEntry.yml";
    private static final String FRCUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "FRCustomer.yml";

    private static final String FR_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "FRPayment.yml";

    @Test
    public void doTest() {
        MockFRGenericInvoiceEntity mock = this.createMockEntity(MockFRGenericInvoiceEntity.class,
                TestFRGenericInvoiceBuilder.FR_GENERIC_INVOICE_YML);

        MockFRCustomerEntity mockCustomerEntity =
                this.createMockEntity(MockFRCustomerEntity.class, TestFRGenericInvoiceBuilder.FRCUSTOMER_YML);

        mock.setCurrency(Currency.getInstance("EUR"));

        Mockito.when(this.getInstance(DAOFRGenericInvoice.class).getEntityInstance())
                .thenReturn(new MockFRGenericInvoiceEntity());

        Mockito.when(this.getInstance(DAOFRCustomer.class).get(Matchers.any(UID.class))).thenReturn(mockCustomerEntity);

        MockFRGenericInvoiceEntryEntity entryMock = this.createMockEntity(MockFRGenericInvoiceEntryEntity.class,
                TestFRGenericInvoiceBuilder.FR_GENERIC_INVOICE_ENTRY_YML);

        Mockito.when(this.getInstance(DAOFRGenericInvoiceEntry.class).get(Matchers.any(UID.class)))
                .thenReturn(entryMock);

        mock.getEntries().add(entryMock);

        ArrayList<FRGenericInvoiceEntry> entries = (ArrayList<FRGenericInvoiceEntry>) mock.getEntries();

        FRGenericInvoice.Builder builder = this.getInstance(FRGenericInvoice.Builder.class);

        FRGenericInvoiceEntry.Builder entry = this.getMock(FRGenericInvoiceEntry.Builder.class);

        Mockito.when(entry.build()).thenReturn(entries.get(0));

        MockFRPaymentEntity mockPayment =
                this.createMockEntity(MockFRPaymentEntity.class, TestFRGenericInvoiceBuilder.FR_PAYMENT_YML);

        Mockito.when(this.getInstance(DAOFRPayment.class).getEntityInstance()).thenReturn(new MockFRPaymentEntity());

        FRPayment.Builder builderPayment = this.getInstance(FRPayment.Builder.class);

        builderPayment.setPaymentAmount(mockPayment.getPaymentAmount()).setPaymentDate(mockPayment.getPaymentDate())
                .setPaymentMethod(mockPayment.getPaymentMethod());

        builder.addEntry(entry).setBilled(mock.isBilled()).setCancelled(mock.isCancelled())
                .setBatchId(mock.getBatchId()).setDate(mock.getDate()).setGeneralLedgerDate(mock.getGeneralLedgerDate())
                .setOfficeNumber(mock.getOfficeNumber()).setPaymentTerms(mock.getPaymentTerms())
                .setSelfBilled(mock.selfBilled).setSettlementDate(mock.getSettlementDate())
                .setSettlementDescription(mock.getSettlementDescription())
                .setSettlementDiscount(mock.getSettlementDiscount()).setSourceId(mock.getSourceId())
                .setTransactionId(mock.getTransactionId()).setCustomerUID(mockCustomerEntity.getUID())
                .addPayment(builderPayment);

        FRGenericInvoice invoice = builder.build();

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
    }
}
