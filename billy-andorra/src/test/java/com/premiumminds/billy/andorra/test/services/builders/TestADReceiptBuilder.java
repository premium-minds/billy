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

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADPayment;
import com.premiumminds.billy.andorra.persistence.dao.DAOADReceipt;
import com.premiumminds.billy.andorra.persistence.dao.DAOADReceiptEntry;
import com.premiumminds.billy.andorra.services.entities.ADPayment;
import com.premiumminds.billy.andorra.services.entities.ADReceipt;
import com.premiumminds.billy.andorra.services.entities.ADReceiptEntry;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.fixtures.MockADCustomerEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADPaymentEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADReceiptEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADReceiptEntryEntity;
import java.util.Currency;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestADReceiptBuilder extends ADAbstractTest {

    private static final String AD_RECEIPT_YML = AbstractTest.YML_CONFIGS_DIR + "ADInvoice.yml";
    private static final String AD_RECEIPT_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "ADInvoiceEntry.yml";
    private static final String AD_CUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "ADCustomer.yml";
    private static final String AD_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "ADPayment.yml";

    @Test
    public void doTest() {
        MockADReceiptEntity mock =
                this.createMockEntity(MockADReceiptEntity.class, TestADReceiptBuilder.AD_RECEIPT_YML);
        mock.setCurrency(Currency.getInstance("EUR"));

        MockADCustomerEntity mockCustomer =
                this.createMockEntity(MockADCustomerEntity.class, TestADReceiptBuilder.AD_CUSTOMER_YML);

        Mockito.when(this.getInstance(DAOADCustomer.class).get(Mockito.any())).thenReturn(mockCustomer);

        Mockito.when(this.getInstance(DAOADReceipt.class).getEntityInstance()).thenReturn(new MockADReceiptEntity());

        MockADReceiptEntryEntity mockEntry =
                this.createMockEntity(MockADReceiptEntryEntity.class, TestADReceiptBuilder.AD_RECEIPT_ENTRY_YML);

        Mockito.when(this.getInstance(DAOADReceiptEntry.class).get(Mockito.any())).thenReturn(mockEntry);

        @SuppressWarnings("unchecked")
        List<ADReceiptEntry> entries = (List<ADReceiptEntry>) (List<?>) mock.getEntries();

        entries.add(mockEntry);

        ADReceipt.Builder builder = this.getInstance(ADReceipt.Builder.class);
        ADReceiptEntry.Builder entry = this.getMock(ADReceiptEntry.Builder.class);

        MockADPaymentEntity mockPayment =
                this.createMockEntity(MockADPaymentEntity.class, TestADReceiptBuilder.AD_PAYMENT_YML);

        Mockito.when(this.getInstance(DAOADPayment.class).getEntityInstance()).thenReturn(new MockADPaymentEntity());

        ADPayment.Builder builderPayment = this.getInstance(ADPayment.Builder.class);

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

        ADReceipt receipt = builder.build();

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
