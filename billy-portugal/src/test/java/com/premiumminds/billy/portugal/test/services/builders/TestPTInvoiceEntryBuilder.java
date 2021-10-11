/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.builders;

import java.util.Currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTInvoiceEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTInvoiceEntryEntity;

public class TestPTInvoiceEntryBuilder extends PTAbstractTest {

    private static final String PT_INVOICE_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR + "PTInvoiceEntry.yml";
    private static final String PT_INVOICE_YML = AbstractTest.YML_CONFIGS_DIR + "PTInvoice.yml";

    @Test
    public void doTest() {
        MockPTInvoiceEntryEntity mock =
                this.createMockEntity(MockPTInvoiceEntryEntity.class, TestPTInvoiceEntryBuilder.PT_INVOICE_ENTRY_YML);

        mock.currency = Currency.getInstance("EUR");

        Mockito.when(this.getInstance(DAOPTInvoiceEntry.class).getEntityInstance())
                .thenReturn(new MockPTInvoiceEntryEntity());

        MockPTInvoiceEntity mockInvoice =
                this.createMockEntity(MockPTInvoiceEntity.class, TestPTInvoiceEntryBuilder.PT_INVOICE_YML);

        Mockito.when(this.getInstance(DAOPTInvoice.class).get(Mockito.any(UID.class))).thenReturn(mockInvoice);

        Mockito.when(this.getInstance(DAOPTProduct.class).get(Mockito.any(UID.class)))
                .thenReturn((PTProductEntity) mock.getProduct());

        Mockito.when(this.getInstance(DAOPTRegionContext.class).isSameOrSubContext(Mockito.any(),
                Mockito.any(Context.class))).thenReturn(true);

        mock.getDocumentReferences().add(mockInvoice);

        PTInvoiceEntry.Builder builder = this.getInstance(PTInvoiceEntry.Builder.class);

        builder.setDescription(mock.getDescription())
                .addDocumentReferenceUID(mock.getDocumentReferences().get(0).getUID()).setQuantity(mock.getQuantity())
                .setShippingCostsAmount(mock.getShippingCostsAmount())
                .setUnitAmount(AmountType.WITH_TAX, mock.getUnitAmountWithTax())
                .setUnitOfMeasure(mock.getUnitOfMeasure()).setProductUID(mock.getProduct().getUID())
                .setTaxPointDate(mock.getTaxPointDate()).setCurrency(Currency.getInstance("EUR"));

        PTInvoiceEntry entry = builder.build();

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

    }

	@Test
	public void doTestFailWithInvalidTaxExemptionCode() {
		MockPTInvoiceEntryEntity mock =
			this.createMockEntity(MockPTInvoiceEntryEntity.class, TestPTInvoiceEntryBuilder.PT_INVOICE_ENTRY_YML);

		mock.currency = Currency.getInstance("EUR");

		Mockito.when(this.getInstance(DAOPTInvoiceEntry.class).getEntityInstance())
			   .thenReturn(new MockPTInvoiceEntryEntity());

		MockPTInvoiceEntity mockInvoice =
			this.createMockEntity(MockPTInvoiceEntity.class, TestPTInvoiceEntryBuilder.PT_INVOICE_YML);

		Mockito.when(this.getInstance(DAOPTInvoice.class).get(Matchers.any(UID.class))).thenReturn(mockInvoice);

		Mockito.when(this.getInstance(DAOPTProduct.class).get(Matchers.any(UID.class)))
			   .thenReturn((PTProductEntity) mock.getProduct());

		Mockito.when(this.getInstance(DAOPTRegionContext.class).isSameOrSubContext(Matchers.any(Context.class),
																				   Matchers.any(Context.class))).thenReturn(true);

		mock.getDocumentReferences().add(mockInvoice);

		PTInvoiceEntry.Builder builder = this.getInstance(PTInvoiceEntry.Builder.class);

		builder
			.setDescription(mock.getDescription())
			.addDocumentReferenceUID(mock.getDocumentReferences().get(0).getUID()).setQuantity(mock.getQuantity())
			.setShippingCostsAmount(mock.getShippingCostsAmount())
			.setUnitAmount(AmountType.WITH_TAX, mock.getUnitAmountWithTax())
			.setUnitOfMeasure(mock.getUnitOfMeasure()).setProductUID(mock.getProduct().getUID())
			.setTaxPointDate(mock.getTaxPointDate()).setCurrency(Currency.getInstance("EUR"))
			.setTaxExemptionReason(mock.getTaxExemptionReason())
			.setTaxExemptionCode("M 9 9");

		try {
			builder.build();
			Assertions.fail();

		} catch (IllegalArgumentException ex) {
			Assertions.assertEquals("Tax Exemption Code", ex.getMessage());
		}
	}
}
