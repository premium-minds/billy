/**
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

import static org.mockito.Mockito.when;

import java.util.Currency;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.spain.persistence.dao.DAOESProduct;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceiptEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.entities.ESProductEntity;
import com.premiumminds.billy.spain.services.entities.ESReceiptEntry;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESReceiptEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESReceiptEntryEntity;

public class TestESReceiptEntryBuilder extends ESAbstractTest{
	private static final String ES_RECEIPT_ENTRY_YML =
			AbstractTest.YML_CONFIGS_DIR + "ESInvoiceEntry.yml";
	private static final String ES_RECEIPT_YML =
			AbstractTest.YML_CONFIGS_DIR + "ESInvoice.yml";
	
	@Test
	public void doTest() {
		MockESReceiptEntryEntity mockEntry = createMockEntity(
				MockESReceiptEntryEntity.class, ES_RECEIPT_ENTRY_YML);
		mockEntry.setCurrency(Currency.getInstance("EUR"));
		
		when(getInstance(DAOESReceiptEntry.class).getEntityInstance())
			.thenReturn(new MockESReceiptEntryEntity());
		
		MockESReceiptEntity mockReceipt = createMockEntity(
				MockESReceiptEntity.class, ES_RECEIPT_YML);
		
		when(getInstance(DAOESReceipt.class).get(Matchers.any(UID.class)))
			.thenReturn(mockReceipt);
		
		when(getInstance(DAOESProduct.class).get(
				Matchers.any(UID.class)))
			.thenReturn((ESProductEntity) mockEntry.getProduct());
		
		when(getInstance(DAOESRegionContext.class)
				.isSubContext(Matchers.any(Context.class),
						Matchers.any(Context.class)))
			.thenReturn(true);
		
		mockEntry.getDocumentReferences().add(mockReceipt);
		
		ESReceiptEntry.Builder builder = this.getInstance(
				ESReceiptEntry.Builder.class);
		
		builder.setDescription(mockEntry.getDescription())
				.addDocumentReferenceUID(
						mockEntry.getDocumentReferences().get(0).getUID())
				.setQuantity(mockEntry.getQuantity())
				.setUnitAmount(AmountType.WITH_TAX,
						mockEntry.getUnitAmountWithTax())
				.setUnitOfMeasure(mockEntry.getUnitOfMeasure())
				.setProductUID(mockEntry.getProduct().getUID())
				.setTaxPointDate(mockEntry.getTaxPointDate())
				.setCurrency(Currency.getInstance("EUR"));
		
		ESReceiptEntry entry = builder.build();
		
		if (entry.getAmountType().compareTo(AmountType.WITHOUT_TAX) == 0) {
			Assert.assertTrue(mockEntry.getUnitAmountWithoutTax().compareTo(
					entry.getUnitAmountWithoutTax()) == 0);
		} else {
			Assert.assertTrue(mockEntry.getUnitAmountWithTax().compareTo(
					entry.getUnitAmountWithTax()) == 0);
		}

		Assert.assertTrue(mockEntry.getUnitDiscountAmount().compareTo(
				entry.getUnitDiscountAmount()) == 0);

		Assert.assertTrue(mockEntry.getUnitTaxAmount().compareTo(
				entry.getUnitTaxAmount()) == 0);
		Assert.assertTrue(mockEntry.getAmountWithTax().compareTo(
				entry.getAmountWithTax()) == 0);
		Assert.assertTrue(mockEntry.getAmountWithoutTax().compareTo(
				entry.getAmountWithoutTax()) == 0);
		Assert.assertTrue(mockEntry.getTaxAmount().compareTo(entry.getTaxAmount()) == 0);
		Assert.assertTrue(mockEntry.getDiscountAmount().compareTo(
				entry.getDiscountAmount()) == 0);
		
	}
}
