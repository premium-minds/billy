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

        Mockito.when(this.getInstance(DAOFRCustomer.class).get(Matchers.any(UID.class))).thenReturn(mockCustomerEntity);

        Mockito.when(this.getInstance(DAOFRReceipt.class).getEntityInstance()).thenReturn(new MockFRReceiptEntity());

        Mockito.when(this.getInstance(DAOFRCreditReceipt.class).getEntityInstance())
                .thenReturn(new MockFRCreditReceiptEntity());

        MockFRCreditReceiptEntryEntity entryMock = this.createMockEntity(MockFRCreditReceiptEntryEntity.class,
                TestFRCreditReceiptBuilder.FR_CREDIT_RECEIPT_ENTRY_YML);

        Mockito.when(this.getInstance(DAOFRCreditReceiptEntry.class).get(Matchers.any(UID.class)))
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

        Assert.assertTrue(creditReceipt != null);
        Assert.assertTrue(creditReceipt.getEntries() != null);
        Assert.assertEquals(creditReceipt.getEntries().size(), mock.getEntries().size());

        Assert.assertTrue(creditReceipt.isBilled() == mock.isBilled());
        Assert.assertTrue(creditReceipt.isCancelled() == mock.isCancelled());

        Assert.assertEquals(mock.getGeneralLedgerDate(), creditReceipt.getGeneralLedgerDate());
        Assert.assertEquals(mock.getBatchId(), creditReceipt.getBatchId());
        Assert.assertEquals(mock.getDate(), creditReceipt.getDate());
        Assert.assertEquals(mock.getPaymentTerms(), creditReceipt.getPaymentTerms());

        Assert.assertTrue(mock.getAmountWithoutTax().compareTo(creditReceipt.getAmountWithoutTax()) == 0);
        Assert.assertTrue(mock.getAmountWithTax().compareTo(creditReceipt.getAmountWithTax()) == 0);
        Assert.assertTrue(mock.getTaxAmount().compareTo(creditReceipt.getTaxAmount()) == 0);

    }
}
