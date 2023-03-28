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

import com.premiumminds.billy.andorra.persistence.dao.DAOADProduct;
import com.premiumminds.billy.andorra.persistence.dao.DAOADReceipt;
import com.premiumminds.billy.andorra.persistence.dao.DAOADReceiptEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.persistence.entities.ADProductEntity;
import com.premiumminds.billy.andorra.services.entities.ADReceiptEntry;
import com.premiumminds.billy.andorra.services.entities.ADReceiptEntry.Builder;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.fixtures.MockADReceiptEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADReceiptEntryEntity;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.test.AbstractTest;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestADReceiptEntryBuilder extends ADAbstractTest {

    private static final String AD_RECEIPT_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "ADInvoiceEntry.yml";
    private static final String AD_RECEIPT_YML = AbstractTest.YML_CONFIGS_DIR + "ADInvoice.yml";

    @Test
    public void doTest() {
        MockADReceiptEntryEntity mockEntry =
                this.createMockEntity(MockADReceiptEntryEntity.class, TestADReceiptEntryBuilder.AD_RECEIPT_ENTRY_YML);
        mockEntry.setCurrency(Currency.getInstance("EUR"));

        Mockito.when(this.getInstance(DAOADReceiptEntry.class).getEntityInstance())
                .thenReturn(new MockADReceiptEntryEntity());

        MockADReceiptEntity mockReceipt =
                this.createMockEntity(MockADReceiptEntity.class, TestADReceiptEntryBuilder.AD_RECEIPT_YML);

        Mockito.when(this.getInstance(DAOADReceipt.class).get(Mockito.any())).thenReturn(mockReceipt);

        Mockito.when(this.getInstance(DAOADProduct.class).get(Mockito.any()))
                .thenReturn((ADProductEntity) mockEntry.getProduct());

        Mockito.when(this.getInstance(DAOADRegionContext.class).isSameOrSubContext(Mockito.any(),
                                                                                   Mockito.any(Context.class))).thenReturn(true);

        mockEntry.getDocumentReferences().add(mockReceipt);

        Builder builder = this.getInstance(Builder.class);

        builder.setDescription(mockEntry.getDescription())
                .addDocumentReferenceUID(mockEntry.getDocumentReferences().get(0).getUID())
                .setQuantity(mockEntry.getQuantity())
                .setUnitAmount(AmountType.WITH_TAX, mockEntry.getUnitAmountWithTax())
                .setUnitOfMeasure(mockEntry.getUnitOfMeasure()).setProductUID(mockEntry.getProduct().getUID())
                .setTaxPointDate(mockEntry.getTaxPointDate()).setCurrency(Currency.getInstance("EUR"));

        ADReceiptEntry entry = builder.build();

        if (entry.getAmountType().compareTo(AmountType.WITHOUT_TAX) == 0) {
            Assertions.assertTrue(mockEntry.getUnitAmountWithoutTax().compareTo(entry.getUnitAmountWithoutTax()) == 0);
        } else {
            Assertions.assertTrue(mockEntry.getUnitAmountWithTax().compareTo(entry.getUnitAmountWithTax()) == 0);
        }

        Assertions.assertTrue(mockEntry.getUnitDiscountAmount().compareTo(entry.getUnitDiscountAmount()) == 0);

        Assertions.assertTrue(mockEntry.getUnitTaxAmount().compareTo(entry.getUnitTaxAmount()) == 0);
        Assertions.assertTrue(mockEntry.getAmountWithTax().compareTo(entry.getAmountWithTax()) == 0);
        Assertions.assertTrue(mockEntry.getAmountWithoutTax().compareTo(entry.getAmountWithoutTax()) == 0);
        Assertions.assertTrue(mockEntry.getTaxAmount().compareTo(entry.getTaxAmount()) == 0);
        Assertions.assertTrue(mockEntry.getDiscountAmount().compareTo(entry.getDiscountAmount()) == 0);

    }
}
