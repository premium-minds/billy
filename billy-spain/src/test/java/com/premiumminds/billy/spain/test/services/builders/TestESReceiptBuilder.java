/**
 * Copyright (C) 2013 Premium Minds.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Currency;
import java.util.List;

import org.junit.Test;
import org.mockito.Matchers;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.dao.DAOESPayment;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceiptEntry;
import com.premiumminds.billy.spain.services.entities.ESPayment;
import com.premiumminds.billy.spain.services.entities.ESReceipt;
import com.premiumminds.billy.spain.services.entities.ESReceiptEntry;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESCustomerEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESPaymentEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESReceiptEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESReceiptEntryEntity;

public class TestESReceiptBuilder extends ESAbstractTest {
	private static final String ES_RECEIPT_YML = YML_CONFIGS_DIR
			+ "ESInvoice.yml";
	private static final String ES_RECEIPT_ENTRY_YML = YML_CONFIGS_DIR
			+ "ESInvoiceEntry.yml";
	private static final String ES_CUSTOMER_YML = YML_CONFIGS_DIR
			+ "ESCustomer.yml";
	private static final String ES_PAYMENT_YML = YML_CONFIGS_DIR
			+ "ESPayment.yml";
	
	@Test
	public void doTest(){
		MockESReceiptEntity mock = createMockEntity(
				MockESReceiptEntity.class, ES_RECEIPT_YML);
		mock.setCurrency(Currency.getInstance("EUR"));
		
		MockESCustomerEntity mockCustomer = createMockEntity(
				MockESCustomerEntity.class, ES_CUSTOMER_YML);
		
		when(getInstance(DAOESCustomer.class).get(Matchers.any(UID.class)))
			.thenReturn(mockCustomer);
		
		when(getInstance(DAOESReceipt.class).getEntityInstance())
			.thenReturn(new MockESReceiptEntity());
		
		MockESReceiptEntryEntity mockEntry = this.createMockEntity(
				MockESReceiptEntryEntity.class, ES_RECEIPT_ENTRY_YML);
		
		when(getInstance(DAOESReceiptEntry.class).get(Matchers.any(UID.class)))
			.thenReturn(mockEntry);
		
		@SuppressWarnings("unchecked")
		List<ESReceiptEntry> entries = (List<ESReceiptEntry>)(List<?>) mock
			.getEntries();
		
		entries.add(mockEntry);
		
		ESReceipt.Builder builder = getInstance(ESReceipt.Builder.class);
		ESReceiptEntry.Builder entry = getMock(ESReceiptEntry.Builder.class);
		
		MockESPaymentEntity mockPayment = this.createMockEntity(
				MockESPaymentEntity.class,
				ES_PAYMENT_YML);

		when(this.getInstance(DAOESPayment.class).getEntityInstance())
				.thenReturn(new MockESPaymentEntity());

		ESPayment.Builder builderPayment = this
				.getInstance(ESPayment.Builder.class);

		builderPayment.setPaymentAmount(mockPayment.getPaymentAmount())
				.setPaymentDate(mockPayment.getPaymentDate())
				.setPaymentMethod(mockPayment.getPaymentMethod());

		when(entry.build()).thenReturn(entries.get(0));

		builder.addEntry(entry)
				.setBilled(mock.isBilled()).setCancelled(mock.isCancelled())
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
				.setTransactionId(mock.getTransactionId())
				.addPayment(builderPayment);
		
		ESReceipt receipt = builder.build();
		
		assertTrue(receipt != null);
		assertTrue(receipt.getEntries() != null);
		assertEquals(receipt.getEntries().size(), mock.getEntries()
				.size());

		assertTrue(receipt.isBilled() == mock.isBilled());
		assertTrue(receipt.isCancelled() == mock.isCancelled());

		assertEquals(mock.getGeneralLedgerDate(),
				receipt.getGeneralLedgerDate());
		assertEquals(mock.getBatchId(), receipt.getBatchId());
		assertEquals(mock.getDate(), receipt.getDate());
		assertEquals(mock.getPaymentTerms(), receipt.getPaymentTerms());

		assertTrue(mock.getAmountWithoutTax().compareTo(
				receipt.getAmountWithoutTax()) == 0);
		assertTrue(mock.getAmountWithTax().compareTo(
				receipt.getAmountWithTax()) == 0);
		assertTrue(mock.getTaxAmount().compareTo(receipt.getTaxAmount()) == 0);
		
		
		builder.setCustomerUID(mockCustomer.getUID());
		
		receipt = builder.build();
		
		assertTrue(receipt.getCustomer().getUID()
				.equals(mockCustomer.getUID()));
	}
}
