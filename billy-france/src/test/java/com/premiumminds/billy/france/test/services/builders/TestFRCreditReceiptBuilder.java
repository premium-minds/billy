/*
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

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditReceipt;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditReceiptEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.dao.DAOFRPayment;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceipt;
import com.premiumminds.billy.france.services.entities.FRCreditReceipt;
import com.premiumminds.billy.france.services.entities.FRCreditReceiptEntry;
import com.premiumminds.billy.france.services.entities.FRPayment;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRCreditReceiptEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRCreditReceiptEntryEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRCustomerEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRPaymentEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRReceiptEntity;
import java.util.ArrayList;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestFRCreditReceiptBuilder extends FRAbstractTest {

    private static final String FR_CREDIT_RECEIPT_YML = AbstractTest.YML_CONFIGS_DIR + "FRCreditReceipt.yml";
    private static final String FR_CREDIT_RECEIPT_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "FRCreditReceiptEntry.yml";

    private static final String FR_CUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "FRCustomer.yml";

    private static final String FR_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "FRPayment.yml";

    @Test
    public void doTest() {
        MockFRCreditReceiptEntity mock = this.createMockEntity(MockFRCreditReceiptEntity.class,
                TestFRCreditReceiptBuilder.FR_CREDIT_RECEIPT_YML);

        mock.setCurrency(Currency.getInstance("EUR"));

        MockFRCustomerEntity mockCustomerEntity =
                this.createMockEntity(MockFRCustomerEntity.class, TestFRCreditReceiptBuilder.FR_CUSTOMER_YML);

        Mockito.when(this.getInstance(DAOFRCustomer.class).get(Mockito.any())).thenReturn(mockCustomerEntity);

        Mockito.when(this.getInstance(DAOFRReceipt.class).getEntityInstance()).thenReturn(new MockFRReceiptEntity());

        Mockito.when(this.getInstance(DAOFRCreditReceipt.class).getEntityInstance())
                .thenReturn(new MockFRCreditReceiptEntity());

        MockFRCreditReceiptEntryEntity entryMock = this.createMockEntity(MockFRCreditReceiptEntryEntity.class,
                TestFRCreditReceiptBuilder.FR_CREDIT_RECEIPT_ENTRY_YML);

        Mockito.when(this.getInstance(DAOFRCreditReceiptEntry.class).get(Mockito.any()))
                .thenReturn(entryMock);

        mock.getEntries().add(entryMock);

        ArrayList<FRCreditReceiptEntry> creditNodeEntries = (ArrayList<FRCreditReceiptEntry>) mock.getEntries();

        FRCreditReceipt.Builder builder = this.getInstance(FRCreditReceipt.Builder.class);

        FRCreditReceiptEntry.Builder entry1 = this.getMock(FRCreditReceiptEntry.Builder.class);
        Mockito.when(entry1.build()).thenReturn(creditNodeEntries.get(0));

        MockFRPaymentEntity mockPayment =
                this.createMockEntity(MockFRPaymentEntity.class, TestFRCreditReceiptBuilder.FR_PAYMENT_YML);

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

        FRCreditReceipt creditReceipt = builder.build();

        Assertions.assertTrue(creditReceipt != null);
        Assertions.assertTrue(creditReceipt.getEntries() != null);
        Assertions.assertEquals(creditReceipt.getEntries().size(), mock.getEntries().size());

        Assertions.assertTrue(creditReceipt.isBilled() == mock.isBilled());
        Assertions.assertTrue(creditReceipt.isCancelled() == mock.isCancelled());

        Assertions.assertEquals(mock.getGeneralLedgerDate(), creditReceipt.getGeneralLedgerDate());
        Assertions.assertEquals(mock.getBatchId(), creditReceipt.getBatchId());
        Assertions.assertEquals(mock.getDate(), creditReceipt.getDate());
        Assertions.assertEquals(mock.getPaymentTerms(), creditReceipt.getPaymentTerms());

        Assertions.assertTrue(mock.getAmountWithoutTax().compareTo(creditReceipt.getAmountWithoutTax()) == 0);
        Assertions.assertTrue(mock.getAmountWithTax().compareTo(creditReceipt.getAmountWithTax()) == 0);
        Assertions.assertTrue(mock.getTaxAmount().compareTo(creditReceipt.getTaxAmount()) == 0);

    }
}
