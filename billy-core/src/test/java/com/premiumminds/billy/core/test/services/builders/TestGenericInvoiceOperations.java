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

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Currency;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntity;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntryEntity;
import com.premiumminds.billy.core.util.BillyMathContext;

public class TestGenericInvoiceOperations extends AbstractTest {

	private static final String	INVOICE_YML	= AbstractTest.YML_CONFIGS_DIR
													+ "GenericInvoice.yml";
	private static final String	ENTRY_YML	= AbstractTest.YML_CONFIGS_DIR
													+ "GenericInvoiceEntry.yml";
	private MathContext			mc			= BillyMathContext.get();
	private BigDecimal			qnt			= new BigDecimal("46");
	private BigDecimal			tax			= new BigDecimal("0.23");

	@Test
	public void doTest() {
		MockGenericInvoiceEntity mock = this.createMockEntity(
				MockGenericInvoiceEntity.class,
				TestGenericInvoiceOperations.INVOICE_YML);
		mock.setCurrency(Currency.getInstance("EUR"));

		Mockito.when(
				this.getInstance(DAOGenericInvoice.class).getEntityInstance())
				.thenReturn(new MockGenericInvoiceEntity());
		// Mockito.when(getInstance(DAOGenericInvoice.class).get(Matchers.any(UID.class))).thenReturn(mock);
		Mockito.when(
				this.getInstance(DAOContext.class).isSubContext(
						Matchers.any(Context.class),
						Matchers.any(Context.class))).thenReturn(true);

		MockGenericInvoiceEntryEntity entryMock1 = this.createMockEntity(
				MockGenericInvoiceEntryEntity.class,
				TestGenericInvoiceOperations.ENTRY_YML);
		entryMock1.setCurrency(Currency.getInstance("EUR"));
		entryMock1.getDocumentReferences().add(mock);
		entryMock1.unitAmountWithoutTax = (new BigDecimal("1")).divide(
				new BigDecimal("3"), this.mc);
		entryMock1.unitTaxAmount = entryMock1.unitAmountWithoutTax.multiply(
				this.tax, this.mc);
		entryMock1.unitAmountWithTax = entryMock1.unitAmountWithoutTax.add(
				entryMock1.unitTaxAmount, this.mc);
		entryMock1.amountWithoutTax = entryMock1.unitAmountWithoutTax.multiply(
				this.qnt, this.mc);
		entryMock1.amountWithTax = entryMock1.unitAmountWithTax.multiply(
				this.qnt, this.mc);
		entryMock1.taxAmount = entryMock1.unitTaxAmount.multiply(
				this.qnt, this.mc);

		Mockito.when(
				this.getInstance(DAOGenericInvoiceEntry.class).get(
						Matchers.any(UID.class))).thenReturn(entryMock1);

		MockGenericInvoiceEntryEntity entryMock2 = this.createMockEntity(
				MockGenericInvoiceEntryEntity.class,
				TestGenericInvoiceOperations.ENTRY_YML);
		entryMock2.setCurrency(Currency.getInstance("EUR"));
		entryMock2.getDocumentReferences().add(mock);

		MockGenericInvoiceEntryEntity entryMock3 = this.createMockEntity(
				MockGenericInvoiceEntryEntity.class,
				TestGenericInvoiceOperations.ENTRY_YML);
		entryMock3.setCurrency(Currency.getInstance("EUR"));
		entryMock3.getDocumentReferences().add(mock);
		entryMock3.unitAmountWithoutTax = (new BigDecimal("1")).divide(
				new BigDecimal("7"), this.mc);
		entryMock3.unitTaxAmount = entryMock3.unitAmountWithoutTax.multiply(
				this.tax, this.mc);
		entryMock3.unitAmountWithTax = entryMock3.unitAmountWithoutTax.add(
				entryMock3.unitTaxAmount, this.mc);
		entryMock3.amountWithoutTax = entryMock3.unitAmountWithoutTax.multiply(
				this.qnt, this.mc);
		entryMock3.amountWithTax = entryMock3.unitAmountWithTax.multiply(
				this.qnt, this.mc);
		entryMock3.taxAmount = entryMock3.unitTaxAmount.multiply(
				this.qnt, this.mc);

		mock.getEntries().add(entryMock1);
		mock.getEntries().add(entryMock2);
		mock.getEntries().add(entryMock3);

		ArrayList<GenericInvoiceEntry> entrys = (ArrayList<GenericInvoiceEntry>) mock.getEntries();

		GenericInvoice.Builder builder = this.getInstance(GenericInvoice.Builder.class);

		GenericInvoiceEntry.Builder invoice1 = this.getMock(GenericInvoiceEntry.Builder.class);
		Mockito.when(invoice1.build()).thenReturn(entrys.get(0));

		GenericInvoiceEntry.Builder invoice2 = this.getMock(GenericInvoiceEntry.Builder.class);
		Mockito.when(invoice2.build()).thenReturn(entrys.get(1));

		GenericInvoiceEntry.Builder invoice3 = this.getMock(GenericInvoiceEntry.Builder.class);
		Mockito.when(invoice3.build()).thenReturn(entrys.get(2));

		builder.addEntry(invoice1).addEntry(invoice2).addEntry(invoice3)
				.setCreditOrDebit(mock.getCreditOrDebit())
				.setBatchId(mock.getBatchId()).setDate(mock.getDate())
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

		Assert.assertTrue(invoice != null);

		Assert.assertTrue(invoice
									.getAmountWithoutTax()
									.setScale(7, this.mc.getRoundingMode())
									.compareTo(
											(entryMock1
														.getAmountWithoutTax()
														.add(
																entryMock2.getAmountWithoutTax()).add(
													entryMock3.amountWithoutTax,
													this.mc)).setScale(
													7,
													this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(invoice
									.getAmountWithTax()
									.setScale(7, this.mc.getRoundingMode())
									.compareTo(
											(entryMock1
														.getAmountWithTax()
														.add(
																entryMock2.getAmountWithTax(),
																this.mc).add(
													entryMock3.amountWithTax,
													this.mc)).setScale(
													7,
													this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(invoice
									.getTaxAmount()
									.setScale(7, this.mc.getRoundingMode())
									.compareTo(
											(entryMock1.getTaxAmount().add(
													entryMock2.getTaxAmount(),
													this.mc).add(
													entryMock3.taxAmount,
													this.mc)).setScale(
													7,
													this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(invoice
									.getAmountWithoutTax()
									.add(invoice.getTaxAmount())
									.setScale(7, this.mc.getRoundingMode())
									.compareTo(
											(entryMock1
														.getAmountWithTax()
														.add(
																entryMock2.getAmountWithTax(),
																this.mc).add(
													entryMock3.amountWithTax,
													this.mc)).setScale(
													7,
													this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(invoice
									.getAmountWithTax()
									.subtract(invoice.getTaxAmount())
									.setScale(7, this.mc.getRoundingMode())
									.compareTo(
											(entryMock1
														.getAmountWithoutTax()
														.add(
																entryMock2.getAmountWithoutTax()).add(
													entryMock3.amountWithoutTax,
													this.mc)).setScale(
													7,
													this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(invoice
									.getAmountWithTax()
									.subtract(invoice.getAmountWithoutTax())
									.setScale(7, this.mc.getRoundingMode())
									.compareTo(
											(entryMock1.getTaxAmount().add(
													entryMock2.getTaxAmount(),
													this.mc).add(
													entryMock3.taxAmount,
													this.mc)).setScale(
													7,
													this.mc.getRoundingMode())) == 0);

	}
}
