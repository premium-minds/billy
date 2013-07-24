/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.builders;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Currency;

import org.junit.Test;
import org.mockito.Matchers;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTGenericInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTGenericInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTGenericInvoiceEntryEntity;

public class TestPTGenericInvoiceEntryBuilder extends PTAbstractTest{
	private static final String PT_GENERIC_INVOICE_ENTRY_YML = "src/test/resources/PTGenericInvoiceEntry.yml";
	private static final String PT_GENERIC_INVOICE_YML = "src/test/resources/PTGenericInvoice.yml";
	
	@Test
	public void doTest(){
		MockPTGenericInvoiceEntryEntity mock = createMockEntity(
				MockPTGenericInvoiceEntryEntity.class, PT_GENERIC_INVOICE_ENTRY_YML);

		mock.currency = Currency.getInstance("EUR");

		when(getInstance(DAOPTGenericInvoiceEntry.class).getEntityInstance())
				.thenReturn(new MockPTGenericInvoiceEntryEntity());

		MockPTGenericInvoiceEntity mockInvoice = createMockEntity(
				MockPTGenericInvoiceEntity.class, PT_GENERIC_INVOICE_YML);

		when(getInstance(DAOPTGenericInvoice.class).get(Matchers.any(UID.class)))
				.thenReturn(mockInvoice);

		when(getInstance(DAOPTProduct.class).get(Matchers.any(UID.class)))
				.thenReturn((PTProductEntity) mock.getProduct());
		
		when(getInstance(DAOPTRegionContext.class).isSubContext(Matchers.any(PTRegionContext.class), Matchers.any(PTRegionContext.class))).thenReturn(true);
		
		mock.getDocumentReferences().add(mockInvoice);
		
		PTGenericInvoiceEntry.Builder builder = getInstance(PTGenericInvoiceEntry.Builder.class);

		builder	.setCreditOrDebit(mock.getCreditOrDebit())
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

		PTGenericInvoiceEntry entry = builder.build();
		
		if(entry.getAmountType().compareTo(AmountType.WITHOUT_TAX) == 0) {
			assertTrue(mock.getUnitAmountWithoutTax().compareTo(
					entry.getUnitAmountWithoutTax()) == 0);
		} else {
			assertTrue(mock.getUnitAmountWithTax().compareTo(
					entry.getUnitAmountWithTax()) == 0);
		}

		assertTrue(mock.getUnitDiscountAmount().compareTo(
				entry.getUnitDiscountAmount()) == 0);
		
		assertTrue(mock.getUnitTaxAmount().compareTo(
				entry.getUnitTaxAmount()) == 0);
		assertTrue(mock.getAmountWithTax().compareTo(
				entry.getAmountWithTax()) == 0);
		assertTrue(mock.getAmountWithoutTax().compareTo(
				entry.getAmountWithoutTax()) == 0);
		assertTrue(mock.getTaxAmount().compareTo(entry.getTaxAmount()) == 0);
		assertTrue(mock.getDiscountAmount().compareTo(
				entry.getDiscountAmount()) == 0);
	}
}
