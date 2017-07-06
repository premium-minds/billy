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

import java.util.ArrayList;
import java.util.Currency;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.dao.DAOESGenericInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESGenericInvoiceEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESPayment;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoice;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoiceEntry;
import com.premiumminds.billy.spain.services.entities.ESPayment;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESCustomerEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESGenericInvoiceEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESGenericInvoiceEntryEntity;
import com.premiumminds.billy.spain.test.fixtures.MockESPaymentEntity;

public class TestESGenericInvoiceBuilder extends ESAbstractTest {

	private static final String ES_GENERIC_INVOICE_YML = AbstractTest.YML_CONFIGS_DIR
			+ "ESGenericInvoice.yml";
	private static final String ES_GENERIC_INVOICE_ENTRY_YML = AbstractTest.YML_CONFIGS_DIR
			+ "ESGenericInvoiceEntry.yml";
	private static final String ESCUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR
			+ "ESCustomer.yml";

	private static final String ES_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR
			+ "ESPayment.yml";

	@Test
	public void doTest() {
		MockESGenericInvoiceEntity mock = this.createMockEntity(
				MockESGenericInvoiceEntity.class,
				TestESGenericInvoiceBuilder.ES_GENERIC_INVOICE_YML);

		MockESCustomerEntity mockCustomerEntity = this.createMockEntity(
				MockESCustomerEntity.class, ESCUSTOMER_YML);


		mock.setCurrency(Currency.getInstance("EUR"));

		Mockito.when(
				this.getInstance(DAOESGenericInvoice.class).getEntityInstance())
				.thenReturn(new MockESGenericInvoiceEntity());

		Mockito.when(
				this.getInstance(DAOESCustomer.class).get(
						Matchers.any(UID.class)))
				.thenReturn(mockCustomerEntity);

		MockESGenericInvoiceEntryEntity entryMock = this.createMockEntity(
				MockESGenericInvoiceEntryEntity.class,
				TestESGenericInvoiceBuilder.ES_GENERIC_INVOICE_ENTRY_YML);

		Mockito.when(
				this.getInstance(DAOESGenericInvoiceEntry.class).get(
						Matchers.any(UID.class))).thenReturn(entryMock);

		mock.getEntries().add(entryMock);

		ArrayList<ESGenericInvoiceEntry> entries = (ArrayList<ESGenericInvoiceEntry>) mock
				.getEntries();

		ESGenericInvoice.Builder builder = this
				.getInstance(ESGenericInvoice.Builder.class);

		ESGenericInvoiceEntry.Builder entry = this
				.getMock(ESGenericInvoiceEntry.Builder.class);

		Mockito.when(entry.build()).thenReturn(entries.get(0));

		MockESPaymentEntity mockPayment = this.createMockEntity(
				MockESPaymentEntity.class,
				TestESGenericInvoiceBuilder.ES_PAYMENT_YML);

		Mockito.when(this.getInstance(DAOESPayment.class).getEntityInstance())
				.thenReturn(new MockESPaymentEntity());

		ESPayment.Builder builderPayment = this
				.getInstance(ESPayment.Builder.class);

		builderPayment.setPaymentAmount(mockPayment.getPaymentAmount())
				.setPaymentDate(mockPayment.getPaymentDate())
				.setPaymentMethod(mockPayment.getPaymentMethod());

		builder.addEntry(entry).setBilled(mock.isBilled())
				.setCancelled(mock.isCancelled()).setBatchId(mock.getBatchId())
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
				.setCustomerUID(mockCustomerEntity.getUID())
				.addPayment(builderPayment);

		ESGenericInvoice invoice = builder.build();

		Assert.assertTrue(invoice != null);
		Assert.assertTrue(invoice.getEntries() != null);
		Assert.assertEquals(invoice.getEntries().size(), mock.getEntries()
				.size());

		Assert.assertTrue(invoice.isBilled() == mock.isBilled());
		Assert.assertTrue(invoice.isCancelled() == mock.isCancelled());

		Assert.assertEquals(mock.getGeneralLedgerDate(),
				invoice.getGeneralLedgerDate());
		Assert.assertEquals(mock.getBatchId(), invoice.getBatchId());
		Assert.assertEquals(mock.getDate(), invoice.getDate());
		Assert.assertEquals(mock.getPaymentTerms(), invoice.getPaymentTerms());

		Assert.assertTrue(mock.getAmountWithoutTax().compareTo(
				invoice.getAmountWithoutTax()) == 0);
		Assert.assertTrue(mock.getAmountWithTax().compareTo(
				invoice.getAmountWithTax()) == 0);
		Assert.assertTrue(mock.getTaxAmount().compareTo(invoice.getTaxAmount()) == 0);
	}
}
