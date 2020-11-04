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

import java.util.ArrayList;
import java.util.Currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNoteEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTPayment;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;
import com.premiumminds.billy.portugal.services.entities.PTPayment;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTCreditNoteEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTCustomerEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTPaymentEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTSimpleInvoiceEntity;

public class TestPTCreditNoteBuilder extends PTAbstractTest {

    private static final String PT_CREDIT_NOTE_YML = AbstractTest.YML_CONFIGS_DIR + "PTCreditNote.yml";
    private static final String PT_CREDIT_NOTE_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "PTCreditNoteEntry.yml";

    private static final String PTCUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "PTCustomer.yml";

    private static final String PT_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "PTPayment.yml";

    @Test
    public void doTest() {
        MockPTCreditNoteEntity mock =
                this.createMockEntity(MockPTCreditNoteEntity.class, TestPTCreditNoteBuilder.PT_CREDIT_NOTE_YML);

        mock.setCurrency(Currency.getInstance("EUR"));

        MockPTCustomerEntity mockCustomerEntity =
                this.createMockEntity(MockPTCustomerEntity.class, TestPTCreditNoteBuilder.PTCUSTOMER_YML);

        Mockito.when(this.getInstance(DAOPTCustomer.class).get(Matchers.any(UID.class))).thenReturn(mockCustomerEntity);

        Mockito.when(this.getInstance(DAOPTSimpleInvoice.class).getEntityInstance())
                .thenReturn(new MockPTSimpleInvoiceEntity());

        Mockito.when(this.getInstance(DAOPTCreditNote.class).getEntityInstance())
                .thenReturn(new MockPTCreditNoteEntity());

        MockPTCreditNoteEntryEntity entryMock = this.createMockEntity(MockPTCreditNoteEntryEntity.class,
                TestPTCreditNoteBuilder.PT_CREDIT_NOTE_ENTRY_YML);

        Mockito.when(this.getInstance(DAOPTCreditNoteEntry.class).get(Matchers.any(UID.class))).thenReturn(entryMock);

        mock.getEntries().add(entryMock);

        ArrayList<PTCreditNoteEntry> creditNodeEntries = (ArrayList<PTCreditNoteEntry>) mock.getEntries();

        PTCreditNote.Builder builder = this.getInstance(PTCreditNote.Builder.class);

        PTCreditNoteEntry.Builder entry1 = this.getMock(PTCreditNoteEntry.Builder.class);
        Mockito.when(entry1.build()).thenReturn(creditNodeEntries.get(0));

        MockPTPaymentEntity mockPayment =
                this.createMockEntity(MockPTPaymentEntity.class, TestPTCreditNoteBuilder.PT_PAYMENT_YML);

        Mockito.when(this.getInstance(DAOPTPayment.class).getEntityInstance()).thenReturn(new MockPTPaymentEntity());

        PTPayment.Builder builderPayment = this.getInstance(PTPayment.Builder.class);

        builderPayment.setPaymentAmount(mockPayment.getPaymentAmount()).setPaymentDate(mockPayment.getPaymentDate())
                .setPaymentMethod(mockPayment.getPaymentMethod());

        builder.addEntry(entry1).setBilled(mock.isBilled()).setCancelled(mock.isCancelled())
                .setBatchId(mock.getBatchId()).setDate(mock.getDate()).setGeneralLedgerDate(mock.getGeneralLedgerDate())
                .setOfficeNumber(mock.getOfficeNumber()).setPaymentTerms(mock.getPaymentTerms())
                .setSelfBilled(mock.selfBilled).setSettlementDate(mock.getSettlementDate())
                .setSettlementDescription(mock.getSettlementDescription())
                .setSettlementDiscount(mock.getSettlementDiscount()).setSourceId(mock.getSourceId())
                .setTransactionId(mock.getTransactionId()).setSourceBilling(mock.getSourceBilling())
                .setCustomerUID(mockCustomerEntity.getUID()).addPayment(builderPayment);

        PTCreditNote creditNote = builder.build();

        Assertions.assertTrue(creditNote != null);
        Assertions.assertTrue(creditNote.getEntries() != null);
        Assertions.assertEquals(creditNote.getEntries().size(), mock.getEntries().size());

        Assertions.assertTrue(creditNote.isBilled() == mock.isBilled());
        Assertions.assertTrue(creditNote.isCancelled() == mock.isCancelled());

        Assertions.assertEquals(mock.getGeneralLedgerDate(), creditNote.getGeneralLedgerDate());
        Assertions.assertEquals(mock.getBatchId(), creditNote.getBatchId());
        Assertions.assertEquals(mock.getDate(), creditNote.getDate());
        Assertions.assertEquals(mock.getPaymentTerms(), creditNote.getPaymentTerms());

        Assertions.assertTrue(mock.getAmountWithoutTax().compareTo(creditNote.getAmountWithoutTax()) == 0);
        Assertions.assertTrue(mock.getAmountWithTax().compareTo(creditNote.getAmountWithTax()) == 0);
        Assertions.assertTrue(mock.getTaxAmount().compareTo(creditNote.getTaxAmount()) == 0);

    }
}
