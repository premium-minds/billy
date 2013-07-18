/**
 * Copyright (C) 2013 Premium Minds.
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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Currency;

import org.junit.Test;
import org.mockito.Matchers;

import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.entities.ProductEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntity;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntryEntity;

public class TestGenericInvoiceEntryBuilder extends AbstractTest {

	private static final String GEN_INVOICE_ENTRY_YML = "src/test/resources/GenericInvoiceEntry.yml";
	private static final String GEN_INVOICE_YML = "src/test/resources/GenericInvoice.yml";

	@Test
	public void doTest() {
		MockGenericInvoiceEntryEntity mock = createMockEntity(
				MockGenericInvoiceEntryEntity.class, GEN_INVOICE_ENTRY_YML);

		mock.currency = Currency.getInstance("EUR");

		when(getInstance(DAOGenericInvoiceEntry.class).getEntityInstance())
				.thenReturn(new MockGenericInvoiceEntryEntity());

		MockGenericInvoiceEntity mockInvoice = createMockEntity(
				MockGenericInvoiceEntity.class, GEN_INVOICE_YML);

		when(getInstance(DAOGenericInvoice.class).get(Matchers.any(UID.class)))
				.thenReturn(mockInvoice);

		when(getInstance(DAOProduct.class).get(Matchers.any(UID.class)))
				.thenReturn((ProductEntity) mock.getProduct());

		mock.getDocumentReferences().add(mockInvoice);

		GenericInvoiceEntry.Builder builder = getInstance(GenericInvoiceEntry.Builder.class);

		builder.setCreditOrDebit(mock.getCreditOrDebit())
				.setDescription(mock.getDescription())
				.addDocumentReferenceUID(
						mock.getDocumentReferences().get(0).getUID())
				.setQuantity(mock.getQuantity())
				.setShippingCostsAmount(mock.getShippingCostsAmount())
				.setUnitAmount(AmountType.WITH_TAX,
						mock.getUnitAmountWithTax(),
						Currency.getInstance("EUR"))
				.setUnitOfMeasure(mock.getUnitOfMeasure())
				.setProductUID(mock.getProduct().getUID())
				.setTaxPointDate(mock.getTaxPointDate());

		GenericInvoiceEntry entry = builder.build();

		assertTrue(mock.getUnitAmountWithTax().compareTo(
				entry.getUnitAmountWithTax()) == 0);

		assertTrue(mock.getUnitAmountWithoutTax().compareTo(
				entry.getUnitAmountWithoutTax()) == 0);

		assertTrue(mock.getUnitDiscountAmount().compareTo(
				entry.getUnitDiscountAmount()) == 0);
		assertTrue(mock.getUnitTaxAmount().compareTo(entry.getUnitTaxAmount()) == 0);
		assertTrue(mock.getAmountWithTax().compareTo(entry.getAmountWithTax()) == 0);
		assertTrue(mock.getAmountWithoutTax().compareTo(
				entry.getAmountWithoutTax()) == 0);
		assertTrue(mock.getTaxAmount().compareTo(entry.getTaxAmount()) == 0);
		assertTrue(mock.getDiscountAmount()
				.compareTo(entry.getDiscountAmount()) == 0);

	}
}
