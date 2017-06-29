/**
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

import org.junit.Assert;
import org.junit.Test;
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

public class TestGenericInvoiceEntryBuilder extends AbstractTest {

    private static final String GEN_INVOICE_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "GenericInvoiceEntry.yml";
    private static final String GEN_INVOICE_YML = AbstractTest.YML_CONFIGS_DIR + "GenericInvoice.yml";

    @Test
    public void doTest() {
        MockGenericInvoiceEntryEntity mock = this.createMockEntity(MockGenericInvoiceEntryEntity.class,
                TestGenericInvoiceEntryBuilder.GEN_INVOICE_ENTRY_YML);

        MockGenericInvoiceEntity mockInvoice =
                this.createMockEntity(MockGenericInvoiceEntity.class, TestGenericInvoiceEntryBuilder.GEN_INVOICE_YML);

        mock.currency = Currency.getInstance("EUR");

        Mockito.when(this.getInstance(DAOGenericInvoiceEntry.class).getEntityInstance())
                .thenReturn(new MockGenericInvoiceEntryEntity());

        Mockito.when(this.getInstance(DAOGenericInvoice.class).get(Matchers.any(UID.class))).thenReturn(mockInvoice);

        Mockito.when(this.getInstance(DAOProduct.class).get(Matchers.any(UID.class)))
                .thenReturn((ProductEntity) mock.getProduct());

        Mockito.when(this.getInstance(DAOContext.class).isSubContext(Matchers.any(Context.class),
                Matchers.any(Context.class))).thenReturn(true);

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
            Assert.assertTrue(mock.getUnitAmountWithoutTax().compareTo(entry.getUnitAmountWithoutTax()) == 0);
        } else {
            Assert.assertTrue(mock.getUnitAmountWithTax().compareTo(entry.getUnitAmountWithTax()) == 0);
        }

        Assert.assertTrue(mock.getUnitDiscountAmount().compareTo(entry.getUnitDiscountAmount()) == 0);

        Assert.assertTrue(mock.getUnitTaxAmount().compareTo(entry.getUnitTaxAmount()) == 0);
        Assert.assertTrue(mock.getAmountWithTax().compareTo(entry.getAmountWithTax()) == 0);
        Assert.assertTrue(mock.getAmountWithoutTax().compareTo(entry.getAmountWithoutTax()) == 0);
        Assert.assertTrue(mock.getTaxAmount().compareTo(entry.getTaxAmount()) == 0);
        Assert.assertTrue(mock.getDiscountAmount().compareTo(entry.getDiscountAmount()) == 0);

    }
}
