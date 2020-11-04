/*
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

import java.util.Currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditReceiptEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESProduct;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.entities.ESProductEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditReceiptEntry;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESCreditReceiptEntryEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESReceiptEntity;

public class TestESCreditReceiptEntryBuilder extends ESAbstractTest {

    private static final String ES_CREDIT_RECEIPT_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "ESCreditReceiptEntry.yml";
    private static final String ES_RECEIPT_YML = AbstractTest.YML_CONFIGS_DIR + "ESReceipt.yml";

    @Test
    public void doTest() {
        MockESCreditReceiptEntryEntity mock = this.createMockEntity(MockESCreditReceiptEntryEntity.class,
                TestESCreditReceiptEntryBuilder.ES_CREDIT_RECEIPT_ENTRY_YML);

        mock.setCurrency(Currency.getInstance("EUR"));

        Mockito.when(this.getInstance(DAOESCreditReceiptEntry.class).getEntityInstance())
                .thenReturn(new MockESCreditReceiptEntryEntity());

        MockESReceiptEntity mockReceiptEntity =
                this.createMockEntity(MockESReceiptEntity.class, TestESCreditReceiptEntryBuilder.ES_RECEIPT_YML);

        Mockito.when(this.getInstance(DAOESReceipt.class).get(Matchers.any(UID.class))).thenReturn(mockReceiptEntity);

        Mockito.when(this.getInstance(DAOESProduct.class).get(Matchers.any(UID.class)))
                .thenReturn((ESProductEntity) mock.getProduct());

        Mockito.when(this.getInstance(DAOESRegionContext.class).isSameOrSubContext(Matchers.any(Context.class),
																				   Matchers.any(Context.class))).thenReturn(true);

        mock.setReference(mockReceiptEntity);

        ESCreditReceiptEntry.Builder builder = this.getInstance(ESCreditReceiptEntry.Builder.class);

        builder.setDescription(mock.getDescription()).setReferenceUID(mock.getReference().getUID())
                .setReason(mock.getReason()).setQuantity(mock.getQuantity())
                .setShippingCostsAmount(mock.getShippingCostsAmount())
                .setUnitAmount(AmountType.WITH_TAX, mock.getUnitAmountWithTax())
                .setUnitOfMeasure(mock.getUnitOfMeasure()).setProductUID(mock.getProduct().getUID())
                .setTaxPointDate(mock.getTaxPointDate()).setCurrency(Currency.getInstance("EUR"));

        ESCreditReceiptEntry entry = builder.build();

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
