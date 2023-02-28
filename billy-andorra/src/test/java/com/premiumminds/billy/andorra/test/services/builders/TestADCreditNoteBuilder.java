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
package com.premiumminds.billy.andorra.test.services.builders;

import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditNote;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditNoteEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADPayment;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSimpleInvoice;
import com.premiumminds.billy.andorra.services.entities.ADCreditNote;
import com.premiumminds.billy.andorra.services.entities.ADCreditNote.Builder;
import com.premiumminds.billy.andorra.services.entities.ADCreditNoteEntry;
import com.premiumminds.billy.andorra.services.entities.ADPayment;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.fixtures.MockADCreditNoteEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADCreditNoteEntryEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADCustomerEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADPaymentEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADSimpleInvoiceEntity;
import com.premiumminds.billy.core.test.AbstractTest;
import java.util.ArrayList;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestADCreditNoteBuilder extends ADAbstractTest {

    private static final String ES_CREDIT_NOTE_YML = AbstractTest.YML_CONFIGS_DIR + "ESCreditNote.yml";
    private static final String ES_CREDIT_NOTE_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "ESCreditNoteEntry.yml";

    private static final String ESCUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "ESCustomer.yml";

    private static final String ES_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "ESPayment.yml";

    @Test
    public void doTest() {
        MockADCreditNoteEntity mock =
                this.createMockEntity(MockADCreditNoteEntity.class, TestADCreditNoteBuilder.ES_CREDIT_NOTE_YML);

        mock.setCurrency(Currency.getInstance("EUR"));

        MockADCustomerEntity mockCustomerEntity =
                this.createMockEntity(MockADCustomerEntity.class, TestADCreditNoteBuilder.ESCUSTOMER_YML);

        Mockito.when(this.getInstance(DAOADCustomer.class).get(Mockito.any())).thenReturn(mockCustomerEntity);

        Mockito.when(this.getInstance(DAOADSimpleInvoice.class).getEntityInstance())
                .thenReturn(new MockADSimpleInvoiceEntity());

        Mockito.when(this.getInstance(DAOADCreditNote.class).getEntityInstance())
                .thenReturn(new MockADCreditNoteEntity());

        MockADCreditNoteEntryEntity entryMock = this.createMockEntity(
			MockADCreditNoteEntryEntity.class,
			TestADCreditNoteBuilder.ES_CREDIT_NOTE_ENTRY_YML);

        Mockito.when(this.getInstance(DAOADCreditNoteEntry.class).get(Mockito.any())).thenReturn(entryMock);

        mock.getEntries().add(entryMock);

        ArrayList<ADCreditNoteEntry> creditNodeEntries = (ArrayList<ADCreditNoteEntry>) mock.getEntries();

        Builder builder = this.getInstance(Builder.class);

        ADCreditNoteEntry.Builder entry1 = this.getMock(ADCreditNoteEntry.Builder.class);
        Mockito.when(entry1.build()).thenReturn(creditNodeEntries.get(0));

        MockADPaymentEntity mockPayment =
                this.createMockEntity(MockADPaymentEntity.class, TestADCreditNoteBuilder.ES_PAYMENT_YML);

        Mockito.when(this.getInstance(DAOADPayment.class).getEntityInstance()).thenReturn(new MockADPaymentEntity());

        ADPayment.Builder builderPayment = this.getInstance(ADPayment.Builder.class);

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

        ADCreditNote creditNote = builder.build();

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
