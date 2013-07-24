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
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Currency;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntity;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntryEntity;

public class TestGenericInvoiceBuilder extends AbstractTest{

	private static final String INVOICE_YML = "src/test/resources/GenericInvoice.yml";
	private static final String ENTRY_YML = "src/test/resources/GenericInvoiceEntry.yml";
	
	@SuppressWarnings("deprecation")
	@Test
	public void doTest(){
	MockGenericInvoiceEntity mock = createMockEntity(MockGenericInvoiceEntity.class, INVOICE_YML);
	
	mock.setCurrency(Currency.getInstance("EUR"));

	Mockito.when(getInstance(DAOGenericInvoice.class).getEntityInstance()).thenReturn(new MockGenericInvoiceEntity());

	MockGenericInvoiceEntryEntity mockInvoice = createMockEntity(MockGenericInvoiceEntryEntity.class, ENTRY_YML);
	
	when(getInstance(DAOGenericInvoiceEntry.class).get(Matchers.any(UID.class)))
	.thenReturn(mockInvoice);
	
	mock.getEntries().add(mockInvoice);
	
	ArrayList<GenericInvoiceEntry> invoiceEntrys = (ArrayList<GenericInvoiceEntry>) mock.getEntries();
	
	GenericInvoice.Builder builder = getInstance(GenericInvoice.Builder.class);
	
	GenericInvoiceEntry.Builder invoice1 = this.getMock(GenericInvoiceEntry.Builder.class);
	Mockito.when(invoice1.build()).thenReturn(invoiceEntrys.get(0));
	
	builder.addEntry(invoice1)
	.setCreditOrDebit(mock.getCreditOrDebit())
	.setBatchId(mock.getBatchId())
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
	

	GenericInvoice invoice = builder.build();

	assertTrue(invoice != null);
	assertTrue(mock.getAmountWithoutTax().compareTo(invoice.getAmountWithoutTax()) == 0);
	assertTrue(mock.getAmountWithTax().compareTo(invoice.getAmountWithTax()) == 0);
	assertTrue(mock.getTaxAmount().compareTo(invoice.getTaxAmount()) == 0);
	
	assertEquals(mock.getCreditOrDebit(), invoice.getCreditOrDebit());
	assertEquals(mock.getGeneralLedgerDate(), invoice.getGeneralLedgerDate());
	assertEquals(mock.getBatchId(), invoice.getBatchId());
	assertEquals(mock.getDate(), invoice.getDate());
	assertEquals(mock.getPaymentTerms(), invoice.getPaymentTerms());
	
	assertTrue(invoice.getEntries() != null);
	assertEquals(invoice.getEntries().size(), mock.getEntries().size());
	
	for(int i = 0; i < invoice.getEntries().size(); i++){
		ArrayList<GenericInvoiceEntry> invoices = (ArrayList<GenericInvoiceEntry>) invoice.getEntries();
		ArrayList<GenericInvoiceEntry> mockInvoices = (ArrayList<GenericInvoiceEntry>) mock.getEntries();
		assertEquals(invoices.get(i).getUnitAmountWithoutTax(), mockInvoices.get(i).getUnitAmountWithoutTax());
		assertEquals(invoices.get(i).getUnitAmountWithTax(), mockInvoices.get(i).getUnitAmountWithTax());
		assertEquals(invoices.get(i).getUnitTaxAmount(), mockInvoices.get(i).getUnitTaxAmount());
		assertTrue(invoices.get(i).equals(mockInvoices.get(i)));
	}
	
	}
}