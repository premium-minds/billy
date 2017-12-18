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
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditNote;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditNoteEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.dao.DAOFRPayment;
import com.premiumminds.billy.france.persistence.dao.DAOFRSimpleInvoice;
import com.premiumminds.billy.france.services.entities.FRCreditNote;
import com.premiumminds.billy.france.services.entities.FRCreditNoteEntry;
import com.premiumminds.billy.france.services.entities.FRPayment;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRCreditNoteEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRCreditNoteEntryEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRCustomerEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRPaymentEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRSimpleInvoiceEntity;

public class TestFRCreditNoteBuilder extends FRAbstractTest {

    private static final String FR_CREDIT_NOTE_YML = AbstractTest.YML_CONFIGS_DIR + "FRCreditNote.yml";
    private static final String FR_CREDIT_NOTE_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "FRCreditNoteEntry.yml";

    private static final String FRCUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "FRCustomer.yml";

    private static final String FR_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "FRPayment.yml";

    @Test
    public void doTest() {
        MockFRCreditNoteEntity mock =
                this.createMockEntity(MockFRCreditNoteEntity.class, TestFRCreditNoteBuilder.FR_CREDIT_NOTE_YML);

        mock.setCurrency(Currency.getInstance("EUR"));

        MockFRCustomerEntity mockCustomerEntity =
                this.createMockEntity(MockFRCustomerEntity.class, TestFRCreditNoteBuilder.FRCUSTOMER_YML);

        Mockito.when(this.getInstance(DAOFRCustomer.class).get(Matchers.any(UID.class))).thenReturn(mockCustomerEntity);

        Mockito.when(this.getInstance(DAOFRSimpleInvoice.class).getEntityInstance())
                .thenReturn(new MockFRSimpleInvoiceEntity());

        Mockito.when(this.getInstance(DAOFRCreditNote.class).getEntityInstance())
                .thenReturn(new MockFRCreditNoteEntity());

        MockFRCreditNoteEntryEntity entryMock = this.createMockEntity(MockFRCreditNoteEntryEntity.class,
                TestFRCreditNoteBuilder.FR_CREDIT_NOTE_ENTRY_YML);

        Mockito.when(this.getInstance(DAOFRCreditNoteEntry.class).get(Matchers.any(UID.class))).thenReturn(entryMock);

        mock.getEntries().add(entryMock);

        ArrayList<FRCreditNoteEntry> creditNodeEntries = (ArrayList<FRCreditNoteEntry>) mock.getEntries();

        FRCreditNote.Builder builder = this.getInstance(FRCreditNote.Builder.class);

        FRCreditNoteEntry.Builder entry1 = this.getMock(FRCreditNoteEntry.Builder.class);
        Mockito.when(entry1.build()).thenReturn(creditNodeEntries.get(0));

        MockFRPaymentEntity mockPayment =
                this.createMockEntity(MockFRPaymentEntity.class, TestFRCreditNoteBuilder.FR_PAYMENT_YML);

        Mockito.when(this.getInstance(DAOFRPayment.class).getEntityInstance()).thenReturn(new MockFRPaymentEntity());

        FRPayment.Builder builderPayment = this.getInstance(FRPayment.Builder.class);

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

        FRCreditNote creditNote = builder.build();

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
