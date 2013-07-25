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

import java.util.ArrayList;
import java.util.Currency;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNoteEntry;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTCreditNoteEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTCreditNoteEntryEntity;

public class TestPTCreditNoteBuilder extends PTAbstractTest {

	private static final String PT_CREDIT_NOTE_YML = "src/test/resources/PTCreditNote.yml";
	private static final String PT_CREDIT_NOTE_ENTRY_YML = "src/test/resources/PTCreditNoteEntry.yml";

	@Test
	public void doTest() {
		MockPTCreditNoteEntity mock = this.createMockEntity(
				MockPTCreditNoteEntity.class,
				TestPTCreditNoteBuilder.PT_CREDIT_NOTE_YML);

		mock.setCurrency(Currency.getInstance("EUR"));

		Mockito.when(
				this.getInstance(DAOPTCreditNote.class).getEntityInstance())
				.thenReturn(new MockPTCreditNoteEntity());

		MockPTCreditNoteEntryEntity entryMock = this.createMockEntity(
				MockPTCreditNoteEntryEntity.class,
				TestPTCreditNoteBuilder.PT_CREDIT_NOTE_ENTRY_YML);

		Mockito.when(
				this.getInstance(DAOPTCreditNoteEntry.class).get(
						Matchers.any(UID.class))).thenReturn(entryMock);

		mock.getEntries().add(entryMock);

		ArrayList<PTCreditNoteEntry> creditNodeEntries = (ArrayList<PTCreditNoteEntry>) mock
				.getEntries();

		PTCreditNote.Builder builder = this
				.getInstance(PTCreditNote.Builder.class);

		PTCreditNoteEntry.Builder entry1 = this
				.getMock(PTCreditNoteEntry.Builder.class);
		Mockito.when(entry1.build()).thenReturn(creditNodeEntries.get(0));

		builder.addEntry(entry1).setBilled(mock.isBilled())
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
				.setTransactionId(mock.getTransactionId())
				.setSourceBilling(mock.getSourceBilling());

		PTCreditNote creditNote = builder.build();

		Assert.assertTrue(creditNote != null);
		Assert.assertTrue(creditNote.getEntries() != null);
		Assert.assertEquals(creditNote.getEntries().size(), mock.getEntries()
				.size());

		Assert.assertTrue(creditNote.isBilled() == mock.isBilled());
		Assert.assertTrue(creditNote.isCancelled() == mock.isCancelled());

		Assert.assertEquals(mock.getCreditOrDebit(),
				creditNote.getCreditOrDebit());
		Assert.assertEquals(mock.getGeneralLedgerDate(),
				creditNote.getGeneralLedgerDate());
		Assert.assertEquals(mock.getBatchId(), creditNote.getBatchId());
		Assert.assertEquals(mock.getDate(), creditNote.getDate());
		Assert.assertEquals(mock.getPaymentTerms(),
				creditNote.getPaymentTerms());

		Assert.assertTrue(mock.getAmountWithoutTax().compareTo(
				creditNote.getAmountWithoutTax()) == 0);
		Assert.assertTrue(mock.getAmountWithTax().compareTo(
				creditNote.getAmountWithTax()) == 0);
		Assert.assertTrue(mock.getTaxAmount().compareTo(
				creditNote.getTaxAmount()) == 0);

	}
}
