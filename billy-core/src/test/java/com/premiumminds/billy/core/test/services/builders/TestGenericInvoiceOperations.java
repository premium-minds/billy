/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.test.services.builders;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.dao.DAOSupplier;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockCustomerEntity;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntity;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntryEntity;
import com.premiumminds.billy.core.test.fixtures.MockSupplierEntity;
import com.premiumminds.billy.core.util.BillyMathContext;

public class TestGenericInvoiceOperations extends AbstractTest {

    private static final String INVOICE_YML = AbstractTest.YML_CONFIGS_DIR + "GenericInvoice.yml";
    private static final String CUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "Customer.yml";
    private static final String SUPPLIER_YML = AbstractTest.YML_CONFIGS_DIR + "Supplier.yml";
    private static final String ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "GenericInvoiceEntry.yml";
    private MathContext mc = BillyMathContext.get();
    private BigDecimal qnt = new BigDecimal("46");
    private BigDecimal tax = new BigDecimal("0.23");
    private BigDecimal testValue = new BigDecimal("0.12345");
    private BigDecimal testValue2 = new BigDecimal("0.98765");
    private MockGenericInvoiceEntity mockInvoiceEntity;
    private MockCustomerEntity mockCustomerEntity;
    private MockSupplierEntity mockSupplierEntity;

    @BeforeEach
    public void setUp() {
        this.mockInvoiceEntity =
                this.createMockEntity(MockGenericInvoiceEntity.class, TestGenericInvoiceOperations.INVOICE_YML);

        Mockito.when(this.getInstance(DAOGenericInvoice.class).getEntityInstance())
                .thenReturn(new MockGenericInvoiceEntity());
        Mockito.when(this.getInstance(DAOContext.class).isSameOrSubContext(Mockito.any(Context.class),
																		   Mockito.any(Context.class))).thenReturn(true);

        this.mockCustomerEntity =
                this.createMockEntity(MockCustomerEntity.class, TestGenericInvoiceOperations.CUSTOMER_YML);
        this.mockSupplierEntity =
                this.createMockEntity(MockSupplierEntity.class, TestGenericInvoiceOperations.SUPPLIER_YML);
        this.mockInvoiceEntity.setCustomer(this.mockCustomerEntity);
        Mockito.when(this.getInstance(DAOCustomer.class).get(Mockito.any(UID.class)))
                .thenReturn(this.mockCustomerEntity);
        Mockito.when(this.getInstance(DAOSupplier.class).get(Mockito.any(UID.class)))
                .thenReturn(this.mockSupplierEntity);
    }

    @Test
    public void simpleOperationsTest() {
        MockGenericInvoiceEntryEntity mockEntry = this.getMockEntryEntity(this.mockInvoiceEntity, this.testValue2);

        Mockito.when(this.getInstance(DAOGenericInvoiceEntry.class).get(Mockito.any(UID.class))).thenReturn(mockEntry);
        this.mockInvoiceEntity.getEntries().clear();
        this.mockInvoiceEntity.getEntries().add(mockEntry);

        GenericInvoiceEntry.Builder invoiceEntry = this.getMock(GenericInvoiceEntry.Builder.class);
        Mockito.when(invoiceEntry.build()).thenReturn(mockEntry);

        GenericInvoice.Builder builder = this.getBuilder();
        builder.addEntry(invoiceEntry);

        GenericInvoice invoice = builder.build();

        Assertions.assertTrue(invoice != null);
        Assertions.assertTrue(invoice.getAmountWithoutTax().compareTo(mockEntry.getAmountWithoutTax()) == 0);
        Assertions.assertTrue(invoice.getAmountWithTax().compareTo(mockEntry.getAmountWithTax()) == 0);
        Assertions.assertTrue(invoice.getTaxAmount().compareTo(mockEntry.getTaxAmount()) == 0);

    }

    @Test
    public void operationsTest() {
        MockGenericInvoiceEntryEntity mockEntry = this.getMockEntryEntity(this.mockInvoiceEntity, this.testValue);

        MockGenericInvoiceEntryEntity mockEntry2 = this.getMockEntryEntity(this.mockInvoiceEntity, this.testValue2);

        Mockito.when(this.getInstance(DAOGenericInvoiceEntry.class).get(Mockito.any(UID.class))).thenReturn(mockEntry);
        this.mockInvoiceEntity.getEntries().clear();
        this.mockInvoiceEntity.getEntries().add(mockEntry);

        GenericInvoiceEntry.Builder invoiceEntry = this.getMock(GenericInvoiceEntry.Builder.class);
        Mockito.when(invoiceEntry.build()).thenReturn(mockEntry);

        GenericInvoiceEntry.Builder invoiceEntry2 = this.getMock(GenericInvoiceEntry.Builder.class);
        Mockito.when(invoiceEntry2.build()).thenReturn(mockEntry2);

        GenericInvoice.Builder builder = this.getBuilder();
        builder.addEntry(invoiceEntry).addEntry(invoiceEntry2);

        GenericInvoice invoice = builder.build();

        Assertions.assertTrue(invoice != null);

        Assertions.assertTrue(invoice.getAmountWithoutTax()
                .compareTo(mockEntry.getAmountWithoutTax().add(mockEntry2.getAmountWithoutTax(), this.mc)) == 0);
        Assertions.assertTrue(invoice.getAmountWithTax()
                .compareTo(mockEntry.getAmountWithTax().add(mockEntry2.getAmountWithTax(), this.mc)) == 0);
        Assertions.assertTrue(invoice.getTaxAmount()
                .compareTo(mockEntry.getTaxAmount().add(mockEntry2.getTaxAmount(), this.mc)) == 0);

    }

    @Test
    public void manyEntriesTest() {
        BigDecimal taxAmount = BigDecimal.ZERO;
        BigDecimal amountWithTax = BigDecimal.ZERO;
        BigDecimal amountWithoutTax = BigDecimal.ZERO;
        this.mockInvoiceEntity.getEntries().clear();

        GenericInvoice.Builder builder = this.getBuilder();

        for (int i = 0; i < 20; i++) {
            MockGenericInvoiceEntryEntity mockEntry =
                    this.getMockEntryEntity(this.mockInvoiceEntity, new BigDecimal("5.977"));
            taxAmount = taxAmount.add(mockEntry.getTaxAmount(), this.mc);
            amountWithTax = amountWithTax.add(mockEntry.getAmountWithTax(), this.mc);
            amountWithoutTax = amountWithoutTax.add(mockEntry.getAmountWithoutTax(), this.mc);

            Mockito.when(this.getInstance(DAOGenericInvoiceEntry.class).get(Mockito.any(UID.class)))
                    .thenReturn(mockEntry);
            this.mockInvoiceEntity.getEntries().add(mockEntry);

            GenericInvoiceEntry.Builder invoiceEntry = this.getMock(GenericInvoiceEntry.Builder.class);
            Mockito.when(invoiceEntry.build()).thenReturn(mockEntry);

            builder.addEntry(invoiceEntry);

        }

        GenericInvoice invoice = builder.build();

        Assertions.assertTrue(invoice != null);
        Assertions.assertTrue(invoice.getAmountWithoutTax().compareTo(amountWithoutTax) == 0);
        Assertions.assertTrue(invoice.getAmountWithTax().compareTo(amountWithTax) == 0);
        Assertions.assertTrue(invoice.getTaxAmount().compareTo(taxAmount) == 0);

    }

    @Test
    public void testRoundingValues() {
        this.mockInvoiceEntity.getEntries().clear();
        MockGenericInvoiceEntryEntity mockEntry1 =
                this.getMockEntryEntity(this.mockInvoiceEntity, new BigDecimal("0.33"));
        MockGenericInvoiceEntryEntity mockEntry2 =
                this.getMockEntryEntity(this.mockInvoiceEntity, new BigDecimal("0.33"));

        this.mockInvoiceEntity.getEntries().add(mockEntry1);
        this.mockInvoiceEntity.getEntries().add(mockEntry2);

        Mockito.when(this.getInstance(DAOGenericInvoiceEntry.class).get(Mockito.any(UID.class)))
                .thenReturn(mockEntry1);

        GenericInvoice.Builder builder = this.getBuilder();

        GenericInvoiceEntry.Builder invoiceEntry1 = this.getMock(GenericInvoiceEntry.Builder.class);
        Mockito.when(invoiceEntry1.build()).thenReturn(mockEntry1);

        GenericInvoiceEntry.Builder invoiceEntry2 = this.getMock(GenericInvoiceEntry.Builder.class);
        Mockito.when(invoiceEntry2.build()).thenReturn(mockEntry2);

        builder.addEntry(invoiceEntry1).addEntry(invoiceEntry2);

        GenericInvoice invoice = builder.build();

        Assertions.assertTrue(invoice.getTaxAmount().setScale(BillyMathContext.SCALE, this.mc.getRoundingMode()).compareTo(
                invoice.getAmountWithTax().setScale(BillyMathContext.SCALE, this.mc.getRoundingMode()).subtract(invoice
                        .getAmountWithoutTax().setScale(BillyMathContext.SCALE, this.mc.getRoundingMode()))) == 0);
    }

    public MockGenericInvoiceEntryEntity getMockEntryEntity(MockGenericInvoiceEntity invoice, BigDecimal unitValue) {

        MockGenericInvoiceEntryEntity result =
                this.createMockEntity(MockGenericInvoiceEntryEntity.class, TestGenericInvoiceOperations.ENTRY_YML);

        result.setCurrency(Currency.getInstance("EUR"));
        result.getDocumentReferences().add(invoice);

        result.unitAmountWithoutTax = unitValue;

        result.unitTaxAmount = result.unitAmountWithoutTax.multiply(this.tax, this.mc);

        result.unitAmountWithTax = result.unitAmountWithoutTax.add(result.unitTaxAmount, this.mc);

        result.amountWithoutTax = result.unitAmountWithoutTax.multiply(this.qnt, this.mc);
        result.amountWithTax = result.unitAmountWithTax.multiply(this.qnt, this.mc);
        result.taxAmount = result.unitTaxAmount.multiply(this.qnt, this.mc);

        return result;
    }

    public GenericInvoice.Builder getBuilder() {
        GenericInvoice.Builder builder = this.getInstance(GenericInvoice.Builder.class);

        builder.setBatchId(this.mockInvoiceEntity.getBatchId()).setDate(this.mockInvoiceEntity.getDate())
                .setGeneralLedgerDate(this.mockInvoiceEntity.getGeneralLedgerDate())
                .setOfficeNumber(this.mockInvoiceEntity.getOfficeNumber())
                .setPaymentTerms(this.mockInvoiceEntity.getPaymentTerms())
                .setSelfBilled(this.mockInvoiceEntity.selfBilled)
                .setSettlementDate(this.mockInvoiceEntity.getSettlementDate())
                .setSettlementDescription(this.mockInvoiceEntity.getSettlementDescription())
                .setSettlementDiscount(this.mockInvoiceEntity.getSettlementDiscount())
                .setSourceId(this.mockInvoiceEntity.getSourceId())
                .setTransactionId(this.mockInvoiceEntity.getTransactionId())
                .setCustomerUID(this.mockCustomerEntity.getUID()).setSupplierUID(this.mockSupplierEntity.getUID())
                .setCreditOrDebit(GenericInvoice.CreditOrDebit.CREDIT);

        return builder;
    }
}
