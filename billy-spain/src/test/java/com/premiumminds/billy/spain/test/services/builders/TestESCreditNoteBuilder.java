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

import java.util.ArrayList;
import java.util.Currency;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNote;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNoteEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.dao.DAOESPayment;
import com.premiumminds.billy.spain.persistence.dao.DAOESSimpleInvoice;
import com.premiumminds.billy.spain.services.entities.ESCreditNote;
import com.premiumminds.billy.spain.services.entities.ESCreditNoteEntry;
import com.premiumminds.billy.spain.services.entities.ESPayment;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESCreditNoteEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESCreditNoteEntryEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESCustomerEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESPaymentEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESSimpleInvoiceEntity;

public class TestESCreditNoteBuilder extends ESAbstractTest {

    private static final String ES_CREDIT_NOTE_YML = AbstractTest.YML_CONFIGS_DIR + "ESCreditNote.yml";
    private static final String ES_CREDIT_NOTE_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "ESCreditNoteEntry.yml";

    private static final String ESCUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "ESCustomer.yml";

    private static final String ES_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "ESPayment.yml";

    @Test
    public void doTest() {
        MockESCreditNoteEntity mock =
                this.createMockEntity(MockESCreditNoteEntity.class, TestESCreditNoteBuilder.ES_CREDIT_NOTE_YML);

        mock.setCurrency(Currency.getInstance("EUR"));

        MockESCustomerEntity mockCustomerEntity =
                this.createMockEntity(MockESCustomerEntity.class, TestESCreditNoteBuilder.ESCUSTOMER_YML);

        Mockito.when(this.getInstance(DAOESCustomer.class).get(Matchers.any(UID.class))).thenReturn(mockCustomerEntity);

        Mockito.when(this.getInstance(DAOESSimpleInvoice.class).getEntityInstance())
                .thenReturn(new MockESSimpleInvoiceEntity());

        Mockito.when(this.getInstance(DAOESCreditNote.class).getEntityInstance())
                .thenReturn(new MockESCreditNoteEntity());

        MockESCreditNoteEntryEntity entryMock = this.createMockEntity(MockESCreditNoteEntryEntity.class,
                TestESCreditNoteBuilder.ES_CREDIT_NOTE_ENTRY_YML);

        Mockito.when(this.getInstance(DAOESCreditNoteEntry.class).get(Matchers.any(UID.class))).thenReturn(entryMock);

        mock.getEntries().add(entryMock);

        ArrayList<ESCreditNoteEntry> creditNodeEntries = (ArrayList<ESCreditNoteEntry>) mock.getEntries();

        ESCreditNote.Builder builder = this.getInstance(ESCreditNote.Builder.class);

        ESCreditNoteEntry.Builder entry1 = this.getMock(ESCreditNoteEntry.Builder.class);
        Mockito.when(entry1.build()).thenReturn(creditNodeEntries.get(0));

        MockESPaymentEntity mockPayment =
                this.createMockEntity(MockESPaymentEntity.class, TestESCreditNoteBuilder.ES_PAYMENT_YML);

        Mockito.when(this.getInstance(DAOESPayment.class).getEntityInstance()).thenReturn(new MockESPaymentEntity());

        ESPayment.Builder builderPayment = this.getInstance(ESPayment.Builder.class);

        builderPayment.setPaymentAmount(mockPayment.getPaymentAmount()).setPaymentDate(mockPayment.getPaymentDate())
                .setPaymentMethod(mockPayment.getPaymentMethod());

        builder.addEntry(entry1).setBilled(mock.isBilled()).setCancelled(mock.isCancelled())
                .setBatchId(mock.getBatchId()).setDate(mock.getDate()).setGeneralLedgerDate(mock.getGeneralLedgerDate())
                .setOfficeNumber(mock.getOfficeNumber()).setPaymentTerms(mock.getPaymentTerms())
                .setSelfBilled(mock.selfBilled).setSettlementDate(mock.getSettlementDate())
                .setSettlementDescription(mock.getSettlementDescription())
                .setSettlementDiscount(mock.getSettlementDiscount()).setSourceId(mock.getSourceId())
                .setTransactionId(mock.getTransactionId()).setCustomerUID(mockCustomerEntity.getUID())
                .addPayment(builderPayment);

        ESCreditNote creditNote = builder.build();

        Assert.assertTrue(creditNote != null);
        Assert.assertTrue(creditNote.getEntries() != null);
        Assert.assertEquals(creditNote.getEntries().size(), mock.getEntries().size());

        Assert.assertTrue(creditNote.isBilled() == mock.isBilled());
        Assert.assertTrue(creditNote.isCancelled() == mock.isCancelled());

        Assert.assertEquals(mock.getGeneralLedgerDate(), creditNote.getGeneralLedgerDate());
        Assert.assertEquals(mock.getBatchId(), creditNote.getBatchId());
        Assert.assertEquals(mock.getDate(), creditNote.getDate());
        Assert.assertEquals(mock.getPaymentTerms(), creditNote.getPaymentTerms());

        Assert.assertTrue(mock.getAmountWithoutTax().compareTo(creditNote.getAmountWithoutTax()) == 0);
        Assert.assertTrue(mock.getAmountWithTax().compareTo(creditNote.getAmountWithTax()) == 0);
        Assert.assertTrue(mock.getTaxAmount().compareTo(creditNote.getTaxAmount()) == 0);

    }
}
