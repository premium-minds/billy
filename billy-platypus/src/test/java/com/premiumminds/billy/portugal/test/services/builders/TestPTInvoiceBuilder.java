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
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTInvoiceEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTInvoiceEntryEntity;

public class TestPTInvoiceBuilder extends PTAbstractTest{
	private static final String PT_INVOICE_YML = "src/test/resources/PTInvoice.yml";
	private static final String PT_INVOICE_ENTRY_YML = "src/test/resources/PTInvoiceEntry.yml"; 
	
	@Test
	public void doTest(){
		MockPTInvoiceEntity mock = createMockEntity(MockPTInvoiceEntity.class, PT_INVOICE_YML);
		
		Mockito.when(this.getInstance(DAOPTInvoice.class).getEntityInstance()).thenReturn(new MockPTInvoiceEntity());
		
		MockPTInvoiceEntryEntity entryMock = createMockEntity(MockPTInvoiceEntryEntity.class, PT_INVOICE_ENTRY_YML);
		
		Mockito.when(this.getInstance(DAOPTInvoiceEntry.class).get(Matchers.any(UID.class))).thenReturn(entryMock);
		
		mock.getEntries().add(entryMock);
		
		ArrayList<PTInvoiceEntry> entries = (ArrayList<PTInvoiceEntry>) mock.getEntries();
		
		PTInvoice.Builder builder = getInstance(PTInvoice.Builder.class);
		
		PTInvoiceEntry.Builder entry = this.getMock(PTInvoiceEntry.Builder.class);
		
		Mockito.when(entry.build()).thenReturn(entries.get(0));
		
		builder.addEntry(entry)
		.setBilled(mock.isBilled())
		.setCancelled(mock.isCancelled())
		.setHash(mock.getHash())
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
		
		PTInvoice invoice = builder.build();
		
		assertTrue(invoice != null);
		assertTrue(invoice.getEntries() != null);
		assertEquals(invoice.getEntries().size(), mock.getEntries().size());
		
		assertTrue(invoice.isBilled() == mock.isBilled());
		assertTrue(invoice.isCancelled() == mock.isCancelled());
		
		assertEquals(mock.getCreditOrDebit(), invoice.getCreditOrDebit());
		assertEquals(mock.getGeneralLedgerDate(), invoice.getGeneralLedgerDate());
		assertEquals(mock.getBatchId(), invoice.getBatchId());
		assertEquals(mock.getDate(), invoice.getDate());
		assertEquals(mock.getPaymentTerms(), invoice.getPaymentTerms());
		
		assertTrue(mock.getAmountWithoutTax().compareTo(invoice.getAmountWithoutTax()) == 0);
		assertTrue(mock.getAmountWithTax().compareTo(invoice.getAmountWithTax()) == 0);
		assertTrue(mock.getTaxAmount().compareTo(invoice.getTaxAmount()) == 0);
	}
}
