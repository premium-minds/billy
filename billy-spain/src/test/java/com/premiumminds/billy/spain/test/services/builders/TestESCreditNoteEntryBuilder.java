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
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNoteEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESProduct;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.entities.ESProductEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditNoteEntry;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESCreditNoteEntryEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESInvoiceEntity;

public class TestESCreditNoteEntryBuilder extends ESAbstractTest {

    private static final String ES_CREDIT_NOTE_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "ESCreditNoteEntry.yml";
    private static final String ES_INVOICE_YML = AbstractTest.YML_CONFIGS_DIR + "ESInvoice.yml";

    @Test
    public void doTest() {
        MockESCreditNoteEntryEntity mock = this.createMockEntity(MockESCreditNoteEntryEntity.class,
                TestESCreditNoteEntryBuilder.ES_CREDIT_NOTE_ENTRY_YML);

        mock.setCurrency(Currency.getInstance("EUR"));

        Mockito.when(this.getInstance(DAOESCreditNoteEntry.class).getEntityInstance())
                .thenReturn(new MockESCreditNoteEntryEntity());

        MockESInvoiceEntity mockInvoiceEntity =
                this.createMockEntity(MockESInvoiceEntity.class, TestESCreditNoteEntryBuilder.ES_INVOICE_YML);

        Mockito.when(this.getInstance(DAOESInvoice.class).get(Mockito.any(UID.class))).thenReturn(mockInvoiceEntity);

        Mockito.when(this.getInstance(DAOESProduct.class).get(Mockito.any(UID.class)))
                .thenReturn((ESProductEntity) mock.getProduct());

        Mockito.when(this.getInstance(DAOESRegionContext.class).isSameOrSubContext(Mockito.any(),
                Mockito.any(Context.class))).thenReturn(true);

        mock.setReference(mockInvoiceEntity);

        ESCreditNoteEntry.Builder builder = this.getInstance(ESCreditNoteEntry.Builder.class);

        builder.setDescription(mock.getDescription()).setReferenceUID(mock.getReference().getUID())
                .setReason(mock.getReason()).setQuantity(mock.getQuantity())
                .setShippingCostsAmount(mock.getShippingCostsAmount())
                .setUnitAmount(AmountType.WITH_TAX, mock.getUnitAmountWithTax())
                .setUnitOfMeasure(mock.getUnitOfMeasure()).setProductUID(mock.getProduct().getUID())
                .setTaxPointDate(mock.getTaxPointDate()).setCurrency(Currency.getInstance("EUR"));

        ESCreditNoteEntry entry = builder.build();

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
