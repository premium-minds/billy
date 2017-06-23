/**
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
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditReceipt;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditReceiptEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.dao.DAOESPayment;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.services.entities.ESCreditReceipt;
import com.premiumminds.billy.spain.services.entities.ESCreditReceiptEntry;
import com.premiumminds.billy.spain.services.entities.ESPayment;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESCreditReceiptEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESCreditReceiptEntryEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESCustomerEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESPaymentEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESReceiptEntity;

public class TestESCreditReceiptBuilder extends ESAbstractTest {

  private static final String ES_CREDIT_RECEIPT_YML = AbstractTest.YML_CONFIGS_DIR
      + "ESCreditReceipt.yml";
  private static final String ES_CREDIT_RECEIPT_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR
      + "ESCreditReceiptEntry.yml";

  private static final String ES_CUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "ESCustomer.yml";

  private static final String ES_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "ESPayment.yml";

  @Test
  public void doTest() {
    MockESCreditReceiptEntity mock = this.createMockEntity(MockESCreditReceiptEntity.class,
        TestESCreditReceiptBuilder.ES_CREDIT_RECEIPT_YML);

    mock.setCurrency(Currency.getInstance("EUR"));

    MockESCustomerEntity mockCustomerEntity = this.createMockEntity(MockESCustomerEntity.class,
        ES_CUSTOMER_YML);

    Mockito.when(this.getInstance(DAOESCustomer.class).get(Matchers.any(UID.class)))
        .thenReturn(mockCustomerEntity);

    Mockito.when(this.getInstance(DAOESReceipt.class).getEntityInstance())
        .thenReturn(new MockESReceiptEntity());

    Mockito.when(this.getInstance(DAOESCreditReceipt.class).getEntityInstance())
        .thenReturn(new MockESCreditReceiptEntity());

    MockESCreditReceiptEntryEntity entryMock = this.createMockEntity(
        MockESCreditReceiptEntryEntity.class,
        TestESCreditReceiptBuilder.ES_CREDIT_RECEIPT_ENTRY_YML);

    Mockito.when(this.getInstance(DAOESCreditReceiptEntry.class).get(Matchers.any(UID.class)))
        .thenReturn(entryMock);

    mock.getEntries().add(entryMock);

    ArrayList<ESCreditReceiptEntry> creditNodeEntries = (ArrayList<ESCreditReceiptEntry>) mock
        .getEntries();

    ESCreditReceipt.Builder builder = this.getInstance(ESCreditReceipt.Builder.class);

    ESCreditReceiptEntry.Builder entry1 = this.getMock(ESCreditReceiptEntry.Builder.class);
    Mockito.when(entry1.build()).thenReturn(creditNodeEntries.get(0));

    MockESPaymentEntity mockPayment = this.createMockEntity(MockESPaymentEntity.class,
        TestESCreditReceiptBuilder.ES_PAYMENT_YML);

    Mockito.when(this.getInstance(DAOESPayment.class).getEntityInstance())
        .thenReturn(new MockESPaymentEntity());

    ESPayment.Builder builderPayment = this.getInstance(ESPayment.Builder.class);

    builderPayment.setPaymentAmount(mockPayment.getPaymentAmount())
        .setPaymentDate(mockPayment.getPaymentDate())
        .setPaymentMethod(mockPayment.getPaymentMethod());

    builder.addEntry(entry1).setBilled(mock.isBilled()).setCancelled(mock.isCancelled())
        .setBatchId(mock.getBatchId()).setDate(mock.getDate())
        .setGeneralLedgerDate(mock.getGeneralLedgerDate()).setOfficeNumber(mock.getOfficeNumber())
        .setPaymentTerms(mock.getPaymentTerms()).setSelfBilled(mock.selfBilled)
        .setSettlementDate(mock.getSettlementDate())
        .setSettlementDescription(mock.getSettlementDescription())
        .setSettlementDiscount(mock.getSettlementDiscount()).setSourceId(mock.getSourceId())
        .setTransactionId(mock.getTransactionId()).setCustomerUID(mockCustomerEntity.getUID())
        .addPayment(builderPayment);

    ESCreditReceipt creditReceipt = builder.build();

    Assert.assertTrue(creditReceipt != null);
    Assert.assertTrue(creditReceipt.getEntries() != null);
    Assert.assertEquals(creditReceipt.getEntries().size(), mock.getEntries().size());

    Assert.assertTrue(creditReceipt.isBilled() == mock.isBilled());
    Assert.assertTrue(creditReceipt.isCancelled() == mock.isCancelled());

    Assert.assertEquals(mock.getGeneralLedgerDate(), creditReceipt.getGeneralLedgerDate());
    Assert.assertEquals(mock.getBatchId(), creditReceipt.getBatchId());
    Assert.assertEquals(mock.getDate(), creditReceipt.getDate());
    Assert.assertEquals(mock.getPaymentTerms(), creditReceipt.getPaymentTerms());

    Assert
        .assertTrue(mock.getAmountWithoutTax().compareTo(creditReceipt.getAmountWithoutTax()) == 0);
    Assert.assertTrue(mock.getAmountWithTax().compareTo(creditReceipt.getAmountWithTax()) == 0);
    Assert.assertTrue(mock.getTaxAmount().compareTo(creditReceipt.getTaxAmount()) == 0);

  }
}
