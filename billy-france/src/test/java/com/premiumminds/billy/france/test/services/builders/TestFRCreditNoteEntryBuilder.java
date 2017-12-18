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
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditNoteEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRProduct;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.entities.FRProductEntity;
import com.premiumminds.billy.france.services.entities.FRCreditNoteEntry;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRCreditNoteEntryEntity;
import com.premiumminds.billy.france.test.fixtures.MockFRInvoiceEntity;

public class TestFRCreditNoteEntryBuilder extends FRAbstractTest {

    private static final String FR_CREDIT_NOTE_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "FRCreditNoteEntry.yml";
    private static final String FR_INVOICE_YML = AbstractTest.YML_CONFIGS_DIR + "FRInvoice.yml";

    @Test
    public void doTest() {
        MockFRCreditNoteEntryEntity mock = this.createMockEntity(MockFRCreditNoteEntryEntity.class,
                TestFRCreditNoteEntryBuilder.FR_CREDIT_NOTE_ENTRY_YML);

        mock.setCurrency(Currency.getInstance("EUR"));

        Mockito.when(this.getInstance(DAOFRCreditNoteEntry.class).getEntityInstance())
                .thenReturn(new MockFRCreditNoteEntryEntity());

        MockFRInvoiceEntity mockInvoiceEntity =
                this.createMockEntity(MockFRInvoiceEntity.class, TestFRCreditNoteEntryBuilder.FR_INVOICE_YML);

        Mockito.when(this.getInstance(DAOFRInvoice.class).get(Matchers.any(UID.class))).thenReturn(mockInvoiceEntity);

        Mockito.when(this.getInstance(DAOFRProduct.class).get(Matchers.any(UID.class)))
                .thenReturn((FRProductEntity) mock.getProduct());

        Mockito.when(this.getInstance(DAOFRRegionContext.class).isSubContext(Matchers.any(Context.class),
                Matchers.any(Context.class))).thenReturn(true);

        mock.setReference(mockInvoiceEntity);

        FRCreditNoteEntry.Builder builder = this.getInstance(FRCreditNoteEntry.Builder.class);

        builder.setDescription(mock.getDescription()).setReferenceUID(mock.getReference().getUID())
                .setReason(mock.getReason()).setQuantity(mock.getQuantity())
                .setShippingCostsAmount(mock.getShippingCostsAmount())
                .setUnitAmount(AmountType.WITH_TAX, mock.getUnitAmountWithTax())
                .setUnitOfMeasure(mock.getUnitOfMeasure()).setProductUID(mock.getProduct().getUID())
                .setTaxPointDate(mock.getTaxPointDate()).setCurrency(Currency.getInstance("EUR"));

        FRCreditNoteEntry entry = builder.build();

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

        Assert.assertTrue(entry.getReason().equals(mock.getReason()));
        Assert.assertTrue(entry.getReference().equals(mock.getReference()));

    }
}
