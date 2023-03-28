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

import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditReceipt;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditReceiptEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADPayment;
import com.premiumminds.billy.andorra.persistence.dao.DAOADReceipt;
import com.premiumminds.billy.andorra.services.entities.ADCreditReceipt;
import com.premiumminds.billy.andorra.services.entities.ADCreditReceipt.Builder;
import com.premiumminds.billy.andorra.services.entities.ADCreditReceiptEntry;
import com.premiumminds.billy.andorra.services.entities.ADPayment;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.fixtures.MockADCreditReceiptEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADCreditReceiptEntryEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADCustomerEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADPaymentEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADReceiptEntity;
import com.premiumminds.billy.core.test.AbstractTest;
import java.util.ArrayList;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestADCreditReceiptBuilder extends ADAbstractTest {

    private static final String AD_CREDIT_RECEIPT_YML = AbstractTest.YML_CONFIGS_DIR + "ADCreditReceipt.yml";
    private static final String AD_CREDIT_RECEIPT_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "ADCreditReceiptEntry.yml";

    private static final String AD_CUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "ADCustomer.yml";

    private static final String AD_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "ADPayment.yml";

    @Test
    public void doTest() {
        MockADCreditReceiptEntity mock = this.createMockEntity(
            MockADCreditReceiptEntity.class,
            TestADCreditReceiptBuilder.AD_CREDIT_RECEIPT_YML);

        mock.setCurrency(Currency.getInstance("EUR"));

        MockADCustomerEntity mockCustomerEntity =
                this.createMockEntity(MockADCustomerEntity.class, TestADCreditReceiptBuilder.AD_CUSTOMER_YML);

        Mockito.when(this.getInstance(DAOADCustomer.class).get(Mockito.any())).thenReturn(mockCustomerEntity);

        Mockito.when(this.getInstance(DAOADReceipt.class).getEntityInstance()).thenReturn(new MockADReceiptEntity());

        Mockito.when(this.getInstance(DAOADCreditReceipt.class).getEntityInstance())
                .thenReturn(new MockADCreditReceiptEntity());

        MockADCreditReceiptEntryEntity entryMock = this.createMockEntity(
            MockADCreditReceiptEntryEntity.class,
            TestADCreditReceiptBuilder.AD_CREDIT_RECEIPT_ENTRY_YML);

        Mockito.when(this.getInstance(DAOADCreditReceiptEntry.class).get(Mockito.any()))
                .thenReturn(entryMock);

        mock.getEntries().add(entryMock);

        ArrayList<ADCreditReceiptEntry> creditNodeEntries = (ArrayList<ADCreditReceiptEntry>) mock.getEntries();

        Builder builder = this.getInstance(Builder.class);

        ADCreditReceiptEntry.Builder entry1 = this.getMock(ADCreditReceiptEntry.Builder.class);
        Mockito.when(entry1.build()).thenReturn(creditNodeEntries.get(0));

        MockADPaymentEntity mockPayment =
                this.createMockEntity(MockADPaymentEntity.class, TestADCreditReceiptBuilder.AD_PAYMENT_YML);

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

        ADCreditReceipt creditReceipt = builder.build();

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
