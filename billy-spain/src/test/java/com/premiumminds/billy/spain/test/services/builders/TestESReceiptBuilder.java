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

import java.util.Currency;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.dao.DAOESPayment;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceiptEntry;
import com.premiumminds.billy.spain.services.entities.ESPayment;
import com.premiumminds.billy.spain.services.entities.ESReceipt;
import com.premiumminds.billy.spain.services.entities.ESReceiptEntry;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESCustomerEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESPaymentEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESReceiptEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESReceiptEntryEntity;

public class TestESReceiptBuilder extends ESAbstractTest {

    private static final String ES_RECEIPT_YML = AbstractTest.YML_CONFIGS_DIR + "ESInvoice.yml";
    private static final String ES_RECEIPT_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "ESInvoiceEntry.yml";
    private static final String ES_CUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "ESCustomer.yml";
    private static final String ES_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "ESPayment.yml";

    @Test
    public void doTest() {
        MockESReceiptEntity mock =
                this.createMockEntity(MockESReceiptEntity.class, TestESReceiptBuilder.ES_RECEIPT_YML);
        mock.setCurrency(Currency.getInstance("EUR"));

        MockESCustomerEntity mockCustomer =
                this.createMockEntity(MockESCustomerEntity.class, TestESReceiptBuilder.ES_CUSTOMER_YML);

        Mockito.when(this.getInstance(DAOESCustomer.class).get(Matchers.any(UID.class))).thenReturn(mockCustomer);

        Mockito.when(this.getInstance(DAOESReceipt.class).getEntityInstance()).thenReturn(new MockESReceiptEntity());

        MockESReceiptEntryEntity mockEntry =
                this.createMockEntity(MockESReceiptEntryEntity.class, TestESReceiptBuilder.ES_RECEIPT_ENTRY_YML);

        Mockito.when(this.getInstance(DAOESReceiptEntry.class).get(Matchers.any(UID.class))).thenReturn(mockEntry);

        @SuppressWarnings("unchecked")
        List<ESReceiptEntry> entries = (List<ESReceiptEntry>) (List<?>) mock.getEntries();

        entries.add(mockEntry);

        ESReceipt.Builder builder = this.getInstance(ESReceipt.Builder.class);
        ESReceiptEntry.Builder entry = this.getMock(ESReceiptEntry.Builder.class);

        MockESPaymentEntity mockPayment =
                this.createMockEntity(MockESPaymentEntity.class, TestESReceiptBuilder.ES_PAYMENT_YML);

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
                .setTransactionId(mock.getTransactionId()).addPayment(builderPayment);

        ESReceipt receipt = builder.build();

        Assertions.assertTrue(receipt != null);
        Assertions.assertTrue(receipt.getEntries() != null);
        Assertions.assertEquals(receipt.getEntries().size(), mock.getEntries().size());

        Assertions.assertTrue(receipt.isBilled() == mock.isBilled());
        Assertions.assertTrue(receipt.isCancelled() == mock.isCancelled());

        Assertions.assertEquals(mock.getGeneralLedgerDate(), receipt.getGeneralLedgerDate());
        Assertions.assertEquals(mock.getBatchId(), receipt.getBatchId());
        Assertions.assertEquals(mock.getDate(), receipt.getDate());
        Assertions.assertEquals(mock.getPaymentTerms(), receipt.getPaymentTerms());

        Assertions.assertTrue(mock.getAmountWithoutTax().compareTo(receipt.getAmountWithoutTax()) == 0);
        Assertions.assertTrue(mock.getAmountWithTax().compareTo(receipt.getAmountWithTax()) == 0);
        Assertions.assertTrue(mock.getTaxAmount().compareTo(receipt.getTaxAmount()) == 0);

        builder.setCustomerUID(mockCustomer.getUID());

        receipt = builder.build();

        Assertions.assertTrue(receipt.getCustomer().getUID().equals(mockCustomer.getUID()));
    }
}
