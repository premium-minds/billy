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
import com.premiumminds.billy.france.persistence.dao.DAOFRProduct;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceipt;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceiptEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.entities.FRProductEntity;
import com.premiumminds.billy.france.services.entities.FRReceiptEntry;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRReceiptEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRReceiptEntryEntity;

public class TestFRReceiptEntryBuilder extends FRAbstractTest {

    private static final String FR_RECEIPT_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "FRInvoiceEntry.yml";
    private static final String FR_RECEIPT_YML = AbstractTest.YML_CONFIGS_DIR + "FRInvoice.yml";

    @Test
    public void doTest() {
        MockFRReceiptEntryEntity mockEntry =
                this.createMockEntity(MockFRReceiptEntryEntity.class, TestFRReceiptEntryBuilder.FR_RECEIPT_ENTRY_YML);
        mockEntry.setCurrency(Currency.getInstance("EUR"));

        Mockito.when(this.getInstance(DAOFRReceiptEntry.class).getEntityInstance())
                .thenReturn(new MockFRReceiptEntryEntity());

        MockFRReceiptEntity mockReceipt =
                this.createMockEntity(MockFRReceiptEntity.class, TestFRReceiptEntryBuilder.FR_RECEIPT_YML);

        Mockito.when(this.getInstance(DAOFRReceipt.class).get(Matchers.any(UID.class))).thenReturn(mockReceipt);

        Mockito.when(this.getInstance(DAOFRProduct.class).get(Matchers.any(UID.class)))
                .thenReturn((FRProductEntity) mockEntry.getProduct());

        Mockito.when(this.getInstance(DAOFRRegionContext.class).isSubContext(Matchers.any(Context.class),
                Matchers.any(Context.class))).thenReturn(true);

        mockEntry.getDocumentReferences().add(mockReceipt);

        FRReceiptEntry.Builder builder = this.getInstance(FRReceiptEntry.Builder.class);

        builder.setDescription(mockEntry.getDescription())
                .addDocumentReferenceUID(mockEntry.getDocumentReferences().get(0).getUID())
                .setQuantity(mockEntry.getQuantity())
                .setUnitAmount(AmountType.WITH_TAX, mockEntry.getUnitAmountWithTax())
                .setUnitOfMeasure(mockEntry.getUnitOfMeasure()).setProductUID(mockEntry.getProduct().getUID())
                .setTaxPointDate(mockEntry.getTaxPointDate()).setCurrency(Currency.getInstance("EUR"));

        FRReceiptEntry entry = builder.build();

        if (entry.getAmountType().compareTo(AmountType.WITHOUT_TAX) == 0) {
            Assert.assertTrue(mockEntry.getUnitAmountWithoutTax().compareTo(entry.getUnitAmountWithoutTax()) == 0);
        } else {
            Assert.assertTrue(mockEntry.getUnitAmountWithTax().compareTo(entry.getUnitAmountWithTax()) == 0);
        }

        Assert.assertTrue(mockEntry.getUnitDiscountAmount().compareTo(entry.getUnitDiscountAmount()) == 0);

        Assert.assertTrue(mockEntry.getUnitTaxAmount().compareTo(entry.getUnitTaxAmount()) == 0);
        Assert.assertTrue(mockEntry.getAmountWithTax().compareTo(entry.getAmountWithTax()) == 0);
        Assert.assertTrue(mockEntry.getAmountWithoutTax().compareTo(entry.getAmountWithoutTax()) == 0);
        Assert.assertTrue(mockEntry.getTaxAmount().compareTo(entry.getTaxAmount()) == 0);
        Assert.assertTrue(mockEntry.getDiscountAmount().compareTo(entry.getDiscountAmount()) == 0);

    }
}
