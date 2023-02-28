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

import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditNoteEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADProduct;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.persistence.entities.ADProductEntity;
import com.premiumminds.billy.andorra.services.entities.ADCreditNoteEntry;
import com.premiumminds.billy.andorra.services.entities.ADCreditNoteEntry.Builder;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.fixtures.MockADCreditNoteEntryEntity;
import com.premiumminds.billy.andorra.test.fixtures.MockADInvoiceEntity;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.test.AbstractTest;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestADCreditNoteEntryBuilder extends ADAbstractTest {

    private static final String ES_CREDIT_NOTE_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "ESCreditNoteEntry.yml";
    private static final String ES_INVOICE_YML = AbstractTest.YML_CONFIGS_DIR + "ESInvoice.yml";

    @Test
    public void doTest() {
        MockADCreditNoteEntryEntity mock = this.createMockEntity(
			MockADCreditNoteEntryEntity.class,
			TestADCreditNoteEntryBuilder.ES_CREDIT_NOTE_ENTRY_YML);

        mock.setCurrency(Currency.getInstance("EUR"));

        Mockito.when(this.getInstance(DAOADCreditNoteEntry.class).getEntityInstance())
                .thenReturn(new MockADCreditNoteEntryEntity());

        MockADInvoiceEntity mockInvoiceEntity =
                this.createMockEntity(MockADInvoiceEntity.class, TestADCreditNoteEntryBuilder.ES_INVOICE_YML);

        Mockito.when(this.getInstance(DAOADInvoice.class).get(Mockito.any())).thenReturn(mockInvoiceEntity);

        Mockito.when(this.getInstance(DAOADProduct.class).get(Mockito.any()))
                .thenReturn((ADProductEntity) mock.getProduct());

        Mockito.when(this.getInstance(DAOADRegionContext.class).isSameOrSubContext(Mockito.any(),
																				   Mockito.any(Context.class))).thenReturn(true);

        mock.setReference(mockInvoiceEntity);

        Builder builder = this.getInstance(Builder.class);

        builder.setDescription(mock.getDescription()).setReferenceUID(mock.getReference().getUID())
                .setReason(mock.getReason()).setQuantity(mock.getQuantity())
                .setShippingCostsAmount(mock.getShippingCostsAmount())
                .setUnitAmount(AmountType.WITH_TAX, mock.getUnitAmountWithTax())
                .setUnitOfMeasure(mock.getUnitOfMeasure()).setProductUID(mock.getProduct().getUID())
                .setTaxPointDate(mock.getTaxPointDate()).setCurrency(Currency.getInstance("EUR"));

        ADCreditNoteEntry entry = builder.build();

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
