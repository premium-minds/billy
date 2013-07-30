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

import java.util.Currency;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNoteEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTInvoiceEntity;

public class TestPTCreditNoteEntryBuilder extends PTAbstractTest {

	private static final String PT_CREDIT_NOTE_ENTRY_YML = YML_CONFIGS_DIR + "PTCreditNoteEntry.yml";
	private static final String PT_INVOICE_YML = YML_CONFIGS_DIR + "PTInvoice.yml";

	@Test
	public void doTest() {
		MockPTCreditNoteEntryEntity mock = this.createMockEntity(
				MockPTCreditNoteEntryEntity.class,
				TestPTCreditNoteEntryBuilder.PT_CREDIT_NOTE_ENTRY_YML);

		mock.setCurrency(Currency.getInstance("EUR"));

		Mockito.when(
				this.getInstance(DAOPTCreditNoteEntry.class)
						.getEntityInstance()).thenReturn(
				new MockPTCreditNoteEntryEntity());

		MockPTInvoiceEntity mockInvoiceEntity = this.createMockEntity(
				MockPTInvoiceEntity.class,
				TestPTCreditNoteEntryBuilder.PT_INVOICE_YML);

		Mockito.when(
				this.getInstance(DAOPTInvoice.class).get(
						Matchers.any(UID.class))).thenReturn(mockInvoiceEntity);

		Mockito.when(
				this.getInstance(DAOPTProduct.class).get(
						Matchers.any(UID.class))).thenReturn(
				(PTProductEntity) mock.getProduct());

		Mockito.when(
				this.getInstance(DAOPTRegionContext.class).isSubContext(
						Matchers.any(Context.class),
						Matchers.any(Context.class))).thenReturn(true);

		mock.setReference(mockInvoiceEntity);

		PTCreditNoteEntry.Builder builder = this
				.getInstance(PTCreditNoteEntry.Builder.class);

		builder.setCreditOrDebit(mock.getCreditOrDebit())
				.setDescription(mock.getDescription())
				.setReference(mock.getReference())
				.setReason(mock.getReason())
				.setQuantity(mock.getQuantity())
				.setShippingCostsAmount(mock.getShippingCostsAmount())
				.setUnitAmount(AmountType.WITH_TAX,
						mock.getUnitAmountWithTax(),
						Currency.getInstance("EUR"))
				.setUnitOfMeasure(mock.getUnitOfMeasure())
				.setProductUID(mock.getProduct().getUID())
				.setTaxPointDate(mock.getTaxPointDate());

		PTCreditNoteEntry entry = builder.build();

		if (entry.getAmountType().compareTo(AmountType.WITHOUT_TAX) == 0) {
			Assert.assertTrue(mock.getUnitAmountWithoutTax().compareTo(
					entry.getUnitAmountWithoutTax()) == 0);
		} else {
			Assert.assertTrue(mock.getUnitAmountWithTax().compareTo(
					entry.getUnitAmountWithTax()) == 0);
		}

		Assert.assertTrue(mock.getUnitDiscountAmount().compareTo(
				entry.getUnitDiscountAmount()) == 0);

		Assert.assertTrue(mock.getUnitTaxAmount().compareTo(
				entry.getUnitTaxAmount()) == 0);
		Assert.assertTrue(mock.getAmountWithTax().compareTo(
				entry.getAmountWithTax()) == 0);
		Assert.assertTrue(mock.getAmountWithoutTax().compareTo(
				entry.getAmountWithoutTax()) == 0);
		Assert.assertTrue(mock.getTaxAmount().compareTo(entry.getTaxAmount()) == 0);
		Assert.assertTrue(mock.getDiscountAmount().compareTo(
				entry.getDiscountAmount()) == 0);

		Assert.assertTrue(entry.getReason().equals(mock.getReason()));
		Assert.assertTrue(entry.getReference().equals(mock.getReference()));

	}
}
