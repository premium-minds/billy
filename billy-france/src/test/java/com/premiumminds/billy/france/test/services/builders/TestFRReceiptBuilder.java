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

import java.util.Currency;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.dao.DAOFRPayment;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceipt;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceiptEntry;
import com.premiumminds.billy.france.services.entities.FRPayment;
import com.premiumminds.billy.france.services.entities.FRReceipt;
import com.premiumminds.billy.france.services.entities.FRReceiptEntry;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRCustomerEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRPaymentEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRReceiptEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRReceiptEntryEntity;

public class TestFRReceiptBuilder extends FRAbstractTest {

    private static final String FR_RECEIPT_YML = AbstractTest.YML_CONFIGS_DIR + "FRInvoice.yml";
    private static final String FR_RECEIPT_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "FRInvoiceEntry.yml";
    private static final String FR_CUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "FRCustomer.yml";
    private static final String FR_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "FRPayment.yml";

    @Test
    public void doTest() {
        MockFRReceiptEntity mock =
                this.createMockEntity(MockFRReceiptEntity.class, TestFRReceiptBuilder.FR_RECEIPT_YML);
        mock.setCurrency(Currency.getInstance("EUR"));

        MockFRCustomerEntity mockCustomer =
                this.createMockEntity(MockFRCustomerEntity.class, TestFRReceiptBuilder.FR_CUSTOMER_YML);

        Mockito.when(this.getInstance(DAOFRCustomer.class).get(Matchers.any(UID.class))).thenReturn(mockCustomer);

        Mockito.when(this.getInstance(DAOFRReceipt.class).getEntityInstance()).thenReturn(new MockFRReceiptEntity());

        MockFRReceiptEntryEntity mockEntry =
                this.createMockEntity(MockFRReceiptEntryEntity.class, TestFRReceiptBuilder.FR_RECEIPT_ENTRY_YML);

        Mockito.when(this.getInstance(DAOFRReceiptEntry.class).get(Matchers.any(UID.class))).thenReturn(mockEntry);

        @SuppressWarnings("unchecked")
        List<FRReceiptEntry> entries = (List<FRReceiptEntry>) (List<?>) mock.getEntries();

        entries.add(mockEntry);

        FRReceipt.Builder builder = this.getInstance(FRReceipt.Builder.class);
        FRReceiptEntry.Builder entry = this.getMock(FRReceiptEntry.Builder.class);

        MockFRPaymentEntity mockPayment =
                this.createMockEntity(MockFRPaymentEntity.class, TestFRReceiptBuilder.FR_PAYMENT_YML);

        Mockito.when(this.getInstance(DAOFRPayment.class).getEntityInstance()).thenReturn(new MockFRPaymentEntity());

        FRPayment.Builder builderPayment = this.getInstance(FRPayment.Builder.class);

        builderPayment.setPaymentAmount(mockPayment.getPaymentAmount()).setPaymentDate(mockPayment.getPaymentDate())
                .setPaymentMethod(mockPayment.getPaymentMethod());

        Mockito.when(entry.build()).thenReturn(entries.get(0));

        builder.addEntry(entry).setBilled(mock.isBilled()).setCancelled(mock.isCancelled())
                .setBatchId(mock.getBatchId()).setDate(mock.getDate()).setGeneralLedgerDate(mock.getGeneralLedgerDate())
                .setOfficeNumber(mock.getOfficeNumber()).setPaymentTerms(mock.getPaymentTerms())
                .setSelfBilled(mock.selfBilled).setSettlementDate(mock.getSettlementDate())
                .setSettlementDescription(mock.getSettlementDescription())
                .setSettlementDiscount(mock.getSettlementDiscount()).setSourceId(mock.getSourceId())
                .setTransactionId(mock.getTransactionId()).addPayment(builderPayment);

        FRReceipt receipt = builder.build();

        Assert.assertTrue(receipt != null);
        Assert.assertTrue(receipt.getEntries() != null);
        Assert.assertEquals(receipt.getEntries().size(), mock.getEntries().size());

        Assert.assertTrue(receipt.isBilled() == mock.isBilled());
        Assert.assertTrue(receipt.isCancelled() == mock.isCancelled());

        Assert.assertEquals(mock.getGeneralLedgerDate(), receipt.getGeneralLedgerDate());
        Assert.assertEquals(mock.getBatchId(), receipt.getBatchId());
        Assert.assertEquals(mock.getDate(), receipt.getDate());
        Assert.assertEquals(mock.getPaymentTerms(), receipt.getPaymentTerms());

        Assert.assertTrue(mock.getAmountWithoutTax().compareTo(receipt.getAmountWithoutTax()) == 0);
        Assert.assertTrue(mock.getAmountWithTax().compareTo(receipt.getAmountWithTax()) == 0);
        Assert.assertTrue(mock.getTaxAmount().compareTo(receipt.getTaxAmount()) == 0);

        builder.setCustomerUID(mockCustomer.getUID());

        receipt = builder.build();

        Assert.assertTrue(receipt.getCustomer().getUID().equals(mockCustomer.getUID()));
    }
}
