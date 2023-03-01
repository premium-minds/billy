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

import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoiceEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADPayment;
import com.premiumminds.billy.andorra.services.entities.ADInvoice;
import com.premiumminds.billy.andorra.services.entities.ADInvoice.Builder;
import com.premiumminds.billy.andorra.services.entities.ADInvoiceEntry;
import com.premiumminds.billy.andorra.services.entities.ADPayment;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.fixtures.MockADCustomerEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADInvoiceEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADInvoiceEntryEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADPaymentEntity;
import com.premiumminds.billy.core.test.AbstractTest;
import java.util.ArrayList;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestADInvoiceBuilder extends ADAbstractTest {

    private static final String AD_INVOICE_YML = AbstractTest.YML_CONFIGS_DIR + "ADInvoice.yml";
    private static final String AD_INVOICE_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "ADInvoiceEntry.yml";
    private static final String AD_CUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "ADCustomer.yml";

    private static final String AD_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "ADPayment.yml";

    @Test
    public void doTest() {
        MockADInvoiceEntity mock =
                this.createMockEntity(MockADInvoiceEntity.class, TestADInvoiceBuilder.AD_INVOICE_YML);
        mock.setCurrency(Currency.getInstance("EUR"));

        MockADCustomerEntity mockCustomerEntity =
                this.createMockEntity(MockADCustomerEntity.class, TestADInvoiceBuilder.AD_CUSTOMER_YML);

        Mockito.when(this.getInstance(DAOADCustomer.class).get(Mockito.any())).thenReturn(mockCustomerEntity);

        Mockito.when(this.getInstance(DAOADInvoice.class).getEntityInstance()).thenReturn(new MockADInvoiceEntity());

        MockADInvoiceEntryEntity entryMock =
                this.createMockEntity(MockADInvoiceEntryEntity.class, TestADInvoiceBuilder.AD_INVOICE_ENTRY_YML);

        Mockito.when(this.getInstance(DAOADInvoiceEntry.class).get(Mockito.any())).thenReturn(entryMock);

        mock.getEntries().add(entryMock);

        ArrayList<ADInvoiceEntry> entries = (ArrayList<ADInvoiceEntry>) mock.getEntries();

        Builder builder = this.getInstance(Builder.class);

        ADInvoiceEntry.Builder entry = this.getMock(ADInvoiceEntry.Builder.class);

        MockADPaymentEntity mockPayment =
                this.createMockEntity(MockADPaymentEntity.class, TestADInvoiceBuilder.AD_PAYMENT_YML);

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
                .setTransactionId(mock.getTransactionId()).setCustomerUID(mockCustomerEntity.getUID())
                .addPayment(builderPayment);

        ADInvoice invoice = builder.build();

        Assertions.assertTrue(invoice != null);
        Assertions.assertTrue(invoice.getEntries() != null);
        Assertions.assertEquals(invoice.getEntries().size(), mock.getEntries().size());

        Assertions.assertTrue(invoice.isBilled() == mock.isBilled());
        Assertions.assertTrue(invoice.isCancelled() == mock.isCancelled());

        Assertions.assertEquals(mock.getGeneralLedgerDate(), invoice.getGeneralLedgerDate());
        Assertions.assertEquals(mock.getBatchId(), invoice.getBatchId());
        Assertions.assertEquals(mock.getDate(), invoice.getDate());
        Assertions.assertEquals(mock.getPaymentTerms(), invoice.getPaymentTerms());

        Assertions.assertTrue(mock.getAmountWithoutTax().compareTo(invoice.getAmountWithoutTax()) == 0);
        Assertions.assertTrue(mock.getAmountWithTax().compareTo(invoice.getAmountWithTax()) == 0);
        Assertions.assertTrue(mock.getTaxAmount().compareTo(invoice.getTaxAmount()) == 0);
    }
}
