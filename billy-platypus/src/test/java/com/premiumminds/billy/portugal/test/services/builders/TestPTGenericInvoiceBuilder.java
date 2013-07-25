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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTGenericInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTGenericInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoiceEntry;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTGenericInvoiceEntryEntity;

public class TestPTGenericInvoiceBuilder extends PTAbstractTest {

	private static final String PT_GENERIC_INVOICE_YML = "src/test/resources/PTGenericInvoice.yml";
	private static final String PT_GENERIC_INVOICE_ENTRY_YML = "src/test/resources/PTGenericInvoiceEntry.yml";

	@Test
	public void doTest() {
		MockPTGenericInvoiceEntity mock = createMockEntity(
				MockPTGenericInvoiceEntity.class, PT_GENERIC_INVOICE_YML);

		Mockito.when(
				this.getInstance(DAOPTGenericInvoice.class).getEntityInstance())
				.thenReturn(new MockPTGenericInvoiceEntity());

		MockPTGenericInvoiceEntryEntity entryMock = createMockEntity(
				MockPTGenericInvoiceEntryEntity.class,
				PT_GENERIC_INVOICE_ENTRY_YML);

		Mockito.when(
				this.getInstance(DAOPTGenericInvoiceEntry.class).get(
						Matchers.any(UID.class))).thenReturn(entryMock);

		mock.getEntries().add(entryMock);

		ArrayList<PTGenericInvoiceEntry> entries = (ArrayList<PTGenericInvoiceEntry>) mock
				.getEntries();

		PTGenericInvoice.Builder builder = getInstance(PTGenericInvoice.Builder.class);

		PTGenericInvoiceEntry.Builder entry = this
				.getMock(PTGenericInvoiceEntry.Builder.class);

		Mockito.when(entry.build()).thenReturn(entries.get(0));

		builder.addEntry(entry).setBilled(mock.isBilled())
				.setCancelled(mock.isCancelled()).setHash(mock.getHash())
				.setBatchId(mock.getBatchId())
				.setCreditOrDebit(mock.getCreditOrDebit())
				.setDate(mock.getDate())
				.setGeneralLedgerDate(mock.getGeneralLedgerDate())
				.setOfficeNumber(mock.getOfficeNumber())
				.setPaymentTerms(mock.getPaymentTerms())
				.setSelfBilled(mock.selfBilled)
				.setSettlementDate(mock.getSettlementDate())
				.setSettlementDescription(mock.getSettlementDescription())
				.setSettlementDiscount(mock.getSettlementDiscount())
				.setSourceId(mock.getSourceId())
				.setTransactionId(mock.getTransactionId());

		PTGenericInvoice invoice = builder.build();

		assertTrue(invoice != null);
		assertTrue(invoice.getEntries() != null);
		assertEquals(invoice.getEntries().size(), mock.getEntries().size());

		assertTrue(invoice.isBilled() == mock.isBilled());
		assertTrue(invoice.isCancelled() == mock.isCancelled());

		assertEquals(mock.getCreditOrDebit(), invoice.getCreditOrDebit());
		assertEquals(mock.getGeneralLedgerDate(),
				invoice.getGeneralLedgerDate());
		assertEquals(mock.getBatchId(), invoice.getBatchId());
		assertEquals(mock.getDate(), invoice.getDate());
		assertEquals(mock.getPaymentTerms(), invoice.getPaymentTerms());

		assertTrue(mock.getAmountWithoutTax().compareTo(
				invoice.getAmountWithoutTax()) == 0);
		assertTrue(mock.getAmountWithTax()
				.compareTo(invoice.getAmountWithTax()) == 0);
		assertTrue(mock.getTaxAmount().compareTo(invoice.getTaxAmount()) == 0);
	}
}
