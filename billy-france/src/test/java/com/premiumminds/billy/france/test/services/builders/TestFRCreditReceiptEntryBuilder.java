/*
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

import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditReceiptEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRProduct;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceipt;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.entities.FRProductEntity;
import com.premiumminds.billy.france.services.entities.FRCreditReceiptEntry;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRCreditReceiptEntryEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRReceiptEntity;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestFRCreditReceiptEntryBuilder extends FRAbstractTest {

    private static final String FR_CREDIT_RECEIPT_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "FRCreditReceiptEntry.yml";
    private static final String FR_RECEIPT_YML = AbstractTest.YML_CONFIGS_DIR + "FRReceipt.yml";

    @Test
    public void doTest() {
        MockFRCreditReceiptEntryEntity mock = this.createMockEntity(MockFRCreditReceiptEntryEntity.class,
                TestFRCreditReceiptEntryBuilder.FR_CREDIT_RECEIPT_ENTRY_YML);

        mock.setCurrency(Currency.getInstance("EUR"));

        Mockito.when(this.getInstance(DAOFRCreditReceiptEntry.class).getEntityInstance())
                .thenReturn(new MockFRCreditReceiptEntryEntity());

        MockFRReceiptEntity mockReceiptEntity =
                this.createMockEntity(MockFRReceiptEntity.class, TestFRCreditReceiptEntryBuilder.FR_RECEIPT_YML);

        Mockito.when(this.getInstance(DAOFRReceipt.class).get(Mockito.any())).thenReturn(mockReceiptEntity);

        Mockito.when(this.getInstance(DAOFRProduct.class).get(Mockito.any()))
                .thenReturn((FRProductEntity) mock.getProduct());

        Mockito.when(this.getInstance(DAOFRRegionContext.class).isSameOrSubContext(Mockito.any(),
                Mockito.any(Context.class))).thenReturn(true);

        mock.setReference(mockReceiptEntity);

        FRCreditReceiptEntry.Builder builder = this.getInstance(FRCreditReceiptEntry.Builder.class);

        builder.setDescription(mock.getDescription()).setReferenceUID(mock.getReference().getUID())
                .setReason(mock.getReason()).setQuantity(mock.getQuantity())
                .setShippingCostsAmount(mock.getShippingCostsAmount())
                .setUnitAmount(AmountType.WITH_TAX, mock.getUnitAmountWithTax())
                .setUnitOfMeasure(mock.getUnitOfMeasure()).setProductUID(mock.getProduct().getUID())
                .setTaxPointDate(mock.getTaxPointDate()).setCurrency(Currency.getInstance("EUR"));

        FRCreditReceiptEntry entry = builder.build();

        if (entry.getAmountType().compareTo(AmountType.WITHOUT_TAX) == 0) {
            Assertions.assertTrue(mock.getUnitAmountWithoutTax().compareTo(entry.getUnitAmountWithoutTax()) == 0);
        } else {
            Assertions.assertTrue(mock.getUnitAmountWithTax().compareTo(entry.getUnitAmountWithTax()) == 0);
        }

        Assertions.assertTrue(mock.getUnitDiscountAmount().compareTo(entry.getUnitDiscountAmount()) == 0);

        Assertions.assertTrue(mock.getUnitTaxAmount().compareTo(entry.getUnitTaxAmount()) == 0);
        Assertions.assertTrue(mock.getAmountWithTax().compareTo(entry.getAmountWithTax()) == 0);
        Assertions.assertTrue(mock.getAmountWithoutTax().compareTo(entry.getAmountWithoutTax()) == 0);
        Assertions.assertTrue(mock.getTaxAmount().compareTo(entry.getTaxAmount()) == 0);
        Assertions.assertTrue(mock.getDiscountAmount().compareTo(entry.getDiscountAmount()) == 0);

        Assertions.assertTrue(entry.getReason().equals(mock.getReason()));
        Assertions.assertTrue(entry.getReference().equals(mock.getReference()));

    }
}
