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
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.entities.ProductEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntity;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntryEntity;
import com.premiumminds.billy.core.util.BillyMathContext;

public class TestGenericInvoiceEntryOperations extends AbstractTest {

    private MathContext mc = BillyMathContext.get();
    private BigDecimal qnt = new BigDecimal("46");
    private static final String INVOICE_YML = AbstractTest.YML_CONFIGS_DIR + "GenericInvoice.yml";
    private static final String ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "GenericInvoiceEntry.yml";
    MockGenericInvoiceEntryEntity mock;
    MockGenericInvoiceEntity invoiceMock;
    GenericInvoiceEntry.Builder builder;
    GenericInvoiceEntry entry;

    @BeforeEach
    public void setUp() {

        this.invoiceMock =
                this.createMockEntity(MockGenericInvoiceEntity.class, TestGenericInvoiceEntryOperations.INVOICE_YML);
        this.invoiceMock.setCurrency(Currency.getInstance("EUR"));
        this.mock = this.getMockEntryEntity(this.invoiceMock, new BigDecimal("0.33333"));

        Mockito.when(this.getInstance(DAOGenericInvoiceEntry.class).getEntityInstance())
                .thenReturn(new MockGenericInvoiceEntryEntity());

        Mockito.when(this.getInstance(DAOContext.class).isSameOrSubContext(Matchers.any(Context.class),
																		   Matchers.any(Context.class))).thenReturn(true);

        Mockito.when(this.getInstance(DAOGenericInvoice.class).getEntityInstance()).thenReturn(this.invoiceMock);
        Mockito.when(this.getInstance(DAOContext.class).isSameOrSubContext(Matchers.any(Context.class),
																		   Matchers.any(Context.class))).thenReturn(true);
        Mockito.when(this.getInstance(DAOGenericInvoice.class).get(Matchers.any(UID.class)))
                .thenReturn(this.invoiceMock);
        Mockito.when(this.getInstance(DAOProduct.class).get(Matchers.any(UID.class)))
                .thenReturn((ProductEntity) this.mock.getProduct());

        this.builder = this.getEntryEntityBuilder(this.invoiceMock, this.mock);

        this.entry = this.getEntryEntityBuilder(this.invoiceMock, this.mock).build();

    }

    @Test
    public void testOperations() {

        Assertions.assertTrue(this.entry.getAmountWithoutTax().compareTo(this.mock.getAmountWithoutTax()) == 0);

        Assertions.assertTrue(this.entry.getAmountWithoutTax()
                .compareTo(this.mock.getUnitAmountWithoutTax().multiply(this.qnt, this.mc)) == 0);

        Assertions.assertTrue(this.entry.getAmountWithoutTax()
                .compareTo((this.mock.getAmountWithTax().subtract(this.mock.getTaxAmount(), this.mc))) == 0);

        Assertions.assertTrue(this.entry.getAmountWithTax()
                .compareTo(this.mock.getAmountWithTax().setScale(7, this.mc.getRoundingMode())) == 0);

        Assertions.assertTrue(this.entry.getAmountWithTax()
                .compareTo(this.mock.getUnitAmountWithTax().multiply(this.qnt, this.mc)) == 0);

        Assertions.assertTrue(this.entry.getAmountWithTax()
                .compareTo((this.mock.getTaxAmount().add(this.mock.getAmountWithoutTax(), this.mc))) == 0);

        Assertions.assertTrue(this.entry.getTaxAmount().compareTo(this.mock.getTaxAmount()) == 0);

        Assertions.assertTrue(this.entry.getTaxAmount()
                .compareTo((this.mock.getAmountWithTax().subtract(this.mock.getAmountWithoutTax()))) == 0);

        Assertions.assertTrue(
                this.entry.getTaxAmount().compareTo(this.mock.getUnitTaxAmount().multiply(this.qnt, this.mc)) == 0);

        Assertions.assertTrue(this.entry.getUnitAmountWithTax().compareTo(this.mock.getUnitAmountWithTax()) == 0);

        Assertions.assertTrue(this.entry.getUnitAmountWithTax().compareTo((this.mock.getUnitAmountWithoutTax()
                .add(this.mock.getUnitAmountWithoutTax().multiply(new BigDecimal("0.23"), this.mc), this.mc))) == 0);

        Assertions.assertTrue(this.entry.getUnitAmountWithoutTax().compareTo(this.mock.getUnitAmountWithoutTax()) == 0);

        Assertions.assertTrue(this.entry.getUnitAmountWithoutTax()
                .compareTo((this.mock.getUnitAmountWithTax().subtract(this.mock.getUnitTaxAmount(), this.mc))) == 0);

        Assertions.assertTrue(this.entry.getUnitTaxAmount().compareTo(this.mock.getUnitTaxAmount()) == 0);

        Assertions.assertTrue(this.entry.getUnitTaxAmount().compareTo(
                (this.mock.getUnitAmountWithTax().subtract(this.mock.getUnitAmountWithoutTax(), this.mc))) == 0);

        try {
            this.builder.setQuantity(new BigDecimal("-1"));
            this.entry = this.builder.build();
        } catch (IllegalArgumentException e) {
        }

        Assertions.assertTrue(this.entry.getUnitAmountWithoutTax().compareTo(this.mock.getUnitAmountWithoutTax()) == 0);
        Assertions.assertTrue(this.entry.getUnitAmountWithTax().compareTo(this.mock.getUnitAmountWithTax()) == 0);
        Assertions.assertTrue(this.entry.getUnitTaxAmount().compareTo(this.mock.getUnitTaxAmount()) == 0);
        Assertions.assertTrue(this.entry.getTaxAmount().compareTo(this.mock.getTaxAmount()) == 0);
        Assertions.assertTrue(this.entry.getAmountWithoutTax().compareTo(this.mock.getAmountWithoutTax()) == 0);
        Assertions.assertTrue(this.entry.getAmountWithTax().compareTo(this.mock.getAmountWithTax()) == 0);

        return;
    }

    public MockGenericInvoiceEntryEntity getMockEntryEntity(MockGenericInvoiceEntity invoice, BigDecimal unitValue) {
        BigDecimal tax = new BigDecimal("0.23");

        MockGenericInvoiceEntryEntity result =
                this.createMockEntity(MockGenericInvoiceEntryEntity.class, TestGenericInvoiceEntryOperations.ENTRY_YML);

        result.setCurrency(Currency.getInstance("EUR"));
        result.getDocumentReferences().add(invoice);

        result.unitAmountWithoutTax = unitValue;
        result.unitTaxAmount = result.unitAmountWithoutTax.multiply(tax, this.mc);
        result.unitAmountWithTax = result.unitAmountWithoutTax.add(result.unitTaxAmount, this.mc);
        result.amountWithoutTax = result.unitAmountWithoutTax.multiply(this.qnt, this.mc);
        result.amountWithTax = result.unitAmountWithTax.multiply(this.qnt, this.mc);
        result.taxAmount = result.unitTaxAmount.multiply(this.qnt, this.mc);

        return result;
    }

    public GenericInvoiceEntry.Builder getEntryEntityBuilder(MockGenericInvoiceEntity invoice,
            MockGenericInvoiceEntryEntity mockEntry) {

        GenericInvoiceEntry.Builder builder = this.getInstance(GenericInvoiceEntry.Builder.class);

        builder.setDescription(mockEntry.getDescription())
                .addDocumentReferenceUID(mockEntry.getDocumentReferences().get(0).getUID())
                .setQuantity(mockEntry.getQuantity()).setShippingCostsAmount(mockEntry.getShippingCostsAmount())
                .setUnitAmount(AmountType.WITHOUT_TAX, mockEntry.getUnitAmountWithoutTax())
                .setUnitOfMeasure(mockEntry.getUnitOfMeasure()).setProductUID(mockEntry.getProduct().getUID())
                .setTaxPointDate(mockEntry.getTaxPointDate()).setCurrency(mockEntry.getCurrency());

        return builder;
    }

}
