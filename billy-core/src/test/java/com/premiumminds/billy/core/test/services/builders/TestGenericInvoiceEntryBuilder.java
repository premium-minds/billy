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

import java.util.Currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.entities.ProductEntity;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntity;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntryEntity;

class TestGenericInvoiceEntryBuilder extends AbstractTest {

    private static final String GEN_INVOICE_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "GenericInvoiceEntry.yml";
    private static final String GEN_INVOICE_ENTRY_REUSE_TAXES_YML =
        AbstractTest.YML_CONFIGS_DIR + "GenericInvoiceEntryReuseTaxes.yml";
    private static final String GEN_INVOICE_YML = AbstractTest.YML_CONFIGS_DIR + "GenericInvoice.yml";

    @Test
    void testSuccess() {
        MockGenericInvoiceEntryEntity mock = this.createMockEntity(MockGenericInvoiceEntryEntity.class,
                TestGenericInvoiceEntryBuilder.GEN_INVOICE_ENTRY_YML);

        MockGenericInvoiceEntity mockInvoice =
                this.createMockEntity(MockGenericInvoiceEntity.class, TestGenericInvoiceEntryBuilder.GEN_INVOICE_YML);

        mock.currency = Currency.getInstance("EUR");

        Mockito.when(this.getInstance(DAOGenericInvoiceEntry.class).getEntityInstance())
                .thenReturn(new MockGenericInvoiceEntryEntity());

        Mockito.when(this.getInstance(DAOGenericInvoice.class).get(Mockito.any())).thenReturn(mockInvoice);

        Mockito.when(this.getInstance(DAOProduct.class).get(Mockito.any()))
                .thenReturn((ProductEntity) mock.getProduct());

        Mockito.when(this.getInstance(DAOContext.class).isSameOrSubContext(Mockito.any(),
                Mockito.any(Context.class))).thenReturn(true);

        mock.getDocumentReferences().add(mockInvoice);

        GenericInvoiceEntry.Builder builder = this.getInstance(GenericInvoiceEntry.Builder.class);

        builder.setDescription(mock.getDescription())
                .addDocumentReferenceUID(mock.getDocumentReferences().get(0).getUID()).setQuantity(mock.getQuantity())
                .setShippingCostsAmount(mock.getShippingCostsAmount())
                .setUnitAmount(AmountType.WITH_TAX, mock.getUnitAmountWithTax())
                .setUnitOfMeasure(mock.getUnitOfMeasure()).setProductUID(mock.getProduct().getUID())
                .setTaxPointDate(mock.getTaxPointDate()).setCurrency(Currency.getInstance("EUR"));

        GenericInvoiceEntry entry = builder.build();

        if (entry.getAmountType().compareTo(AmountType.WITHOUT_TAX) == 0) {
            Assertions.assertEquals(0, mock.getUnitAmountWithoutTax().compareTo(entry.getUnitAmountWithoutTax()));
        } else {
            Assertions.assertEquals(0, mock.getUnitAmountWithTax().compareTo(entry.getUnitAmountWithTax()));
        }

        Assertions.assertEquals(0, mock.getUnitDiscountAmount().compareTo(entry.getUnitDiscountAmount()));

        Assertions.assertEquals(0, mock.getUnitTaxAmount().compareTo(entry.getUnitTaxAmount()));
        Assertions.assertEquals(0, mock.getAmountWithTax().compareTo(entry.getAmountWithTax()));
        Assertions.assertEquals(0, mock.getAmountWithoutTax().compareTo(entry.getAmountWithoutTax()));
        Assertions.assertEquals(0, mock.getTaxAmount().compareTo(entry.getTaxAmount()));
        Assertions.assertEquals(0, mock.getDiscountAmount().compareTo(entry.getDiscountAmount()));

    }

    @Test
    void testSuccessWithGivenTaxes() {
        MockGenericInvoiceEntryEntity mock = this.createMockEntity(MockGenericInvoiceEntryEntity.class,
                                                                   TestGenericInvoiceEntryBuilder.GEN_INVOICE_ENTRY_REUSE_TAXES_YML);

        MockGenericInvoiceEntity mockInvoice =
            this.createMockEntity(MockGenericInvoiceEntity.class, TestGenericInvoiceEntryBuilder.GEN_INVOICE_YML);

        mock.currency = Currency.getInstance("EUR");

        Mockito.when(this.getInstance(DAOGenericInvoiceEntry.class).getEntityInstance())
               .thenReturn(new MockGenericInvoiceEntryEntity());

        Mockito.when(this.getInstance(DAOGenericInvoice.class).get(Mockito.any())).thenReturn(mockInvoice);

        Mockito.when(this.getInstance(DAOProduct.class).get(Mockito.any()))
               .thenReturn((ProductEntity) mock.getProduct());

        Mockito.when(this.getInstance(DAOContext.class).isSameOrSubContext(Mockito.any(), Mockito.any(Context.class)))
               .thenThrow(new RuntimeException());

        mock.getDocumentReferences().add(mockInvoice);

        GenericInvoiceEntry.Builder builder = this.getInstance(GenericInvoiceEntry.Builder.class);

        builder.setDescription(mock.getDescription())
               .addDocumentReferenceUID(mock.getDocumentReferences().get(0).getUID()).setQuantity(mock.getQuantity())
               .setShippingCostsAmount(mock.getShippingCostsAmount())
               .setUnitAmount(AmountType.WITH_TAX, mock.getUnitAmountWithTax())
               .setUnitOfMeasure(mock.getUnitOfMeasure()).setProductUID(mock.getProduct().getUID())
               .setTaxPointDate(mock.getTaxPointDate()).setCurrency(Currency.getInstance("EUR"))
            .setTaxes(mock.getTaxes());

        GenericInvoiceEntry entry = builder.build();

        if (entry.getAmountType().compareTo(AmountType.WITHOUT_TAX) == 0) {
            Assertions.assertEquals(0, mock.getUnitAmountWithoutTax().compareTo(entry.getUnitAmountWithoutTax()));
        } else {
            Assertions.assertEquals(0, mock.getUnitAmountWithTax().compareTo(entry.getUnitAmountWithTax()));
        }

        Assertions.assertEquals(0, mock.getUnitDiscountAmount().compareTo(entry.getUnitDiscountAmount()));

        Assertions.assertEquals(0, mock.getUnitTaxAmount().compareTo(entry.getUnitTaxAmount()));
        Assertions.assertEquals(0, mock.getAmountWithTax().compareTo(entry.getAmountWithTax()));
        Assertions.assertEquals(0, mock.getAmountWithoutTax().compareTo(entry.getAmountWithoutTax()));
        Assertions.assertEquals(0, mock.getTaxAmount().compareTo(entry.getTaxAmount()));
        Assertions.assertEquals(0, mock.getDiscountAmount().compareTo(entry.getDiscountAmount()));

        Assertions.assertFalse(entry.getTaxes().isEmpty());
        Assertions.assertEquals(1, entry.getTaxes().size());
        entry.getTaxes().forEach(t -> Assertions.assertEquals(mock.getTaxes().get(0).getUID(), t.getUID()));

    }
}
