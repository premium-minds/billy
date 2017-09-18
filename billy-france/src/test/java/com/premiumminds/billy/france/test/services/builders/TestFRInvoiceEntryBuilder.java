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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoiceEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRProduct;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.entities.FRProductEntity;
import com.premiumminds.billy.france.services.entities.FRInvoiceEntry;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRInvoiceEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRInvoiceEntryEntity;

public class TestFRInvoiceEntryBuilder extends FRAbstractTest {

    private static final String FR_INVOICE_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "FRInvoiceEntry.yml";
    private static final String FR_INVOICE_YML = AbstractTest.YML_CONFIGS_DIR + "FRInvoice.yml";

    @Test
    public void doTest() {
        MockFRInvoiceEntryEntity mock =
                this.createMockEntity(MockFRInvoiceEntryEntity.class, TestFRInvoiceEntryBuilder.FR_INVOICE_ENTRY_YML);

        mock.currency = Currency.getInstance("EUR");

        Mockito.when(this.getInstance(DAOFRInvoiceEntry.class).getEntityInstance())
                .thenReturn(new MockFRInvoiceEntryEntity());

        MockFRInvoiceEntity mockInvoice =
                this.createMockEntity(MockFRInvoiceEntity.class, TestFRInvoiceEntryBuilder.FR_INVOICE_YML);

        Mockito.when(this.getInstance(DAOFRInvoice.class).get(Matchers.any(UID.class))).thenReturn(mockInvoice);

        Mockito.when(this.getInstance(DAOFRProduct.class).get(Matchers.any(UID.class)))
                .thenReturn((FRProductEntity) mock.getProduct());

        Mockito.when(this.getInstance(DAOFRRegionContext.class).isSubContext(Matchers.any(Context.class),
                Matchers.any(Context.class))).thenReturn(true);

        mock.getDocumentReferences().add(mockInvoice);

        FRInvoiceEntry.Builder builder = this.getInstance(FRInvoiceEntry.Builder.class);

        builder.setDescription(mock.getDescription())
                .addDocumentReferenceUID(mock.getDocumentReferences().get(0).getUID()).setQuantity(mock.getQuantity())
                .setShippingCostsAmount(mock.getShippingCostsAmount())
                .setUnitAmount(AmountType.WITH_TAX, mock.getUnitAmountWithTax())
                .setUnitOfMeasure(mock.getUnitOfMeasure()).setProductUID(mock.getProduct().getUID())
                .setTaxPointDate(mock.getTaxPointDate()).setCurrency(Currency.getInstance("EUR"));

        FRInvoiceEntry entry = builder.build();

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
