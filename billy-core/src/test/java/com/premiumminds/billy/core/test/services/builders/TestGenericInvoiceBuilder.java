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

import java.util.Arrays;
import java.util.Currency;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOBusiness;
import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.dao.DAOSupplier;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.persistence.entities.ShippingPointEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockBusinessEntity;
import com.premiumminds.billy.core.test.fixtures.MockCustomerEntity;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntity;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntryEntity;
import com.premiumminds.billy.core.test.fixtures.MockSupplierEntity;

public class TestGenericInvoiceBuilder extends AbstractTest {

	private static final String GENERIC_INVOICE_YML = "src/test/resources/GenericInvoice.yml";

	@Test
	public void doTest() {

		MockGenericInvoiceEntity mockGenericInvoice = loadFixture(MockGenericInvoiceEntity.class);

		Mockito.when(getInstance(DAOGenericInvoice.class).getEntityInstance())
				.thenReturn(new MockGenericInvoiceEntity());

		Mockito.when(getInstance(DAOGenericInvoiceEntry.class).getEntityInstance())
		.thenReturn(new MockGenericInvoiceEntryEntity());
		
		GenericInvoice.Builder builder = getInstance(GenericInvoice.Builder.class);

		GenericInvoiceEntry.Builder mockGenericInvoiceEntry = this
				.getMock(GenericInvoiceEntry.Builder.class);
		
		Mockito.when(mockGenericInvoiceEntry.build()).thenReturn(
				Mockito.mock(GenericInvoiceEntryEntity.class));

		ShippingPoint.Builder mockShippingPoint = this
				.getMock(ShippingPoint.Builder.class);
		Mockito.when(mockShippingPoint.build()).thenReturn(
				Mockito.mock(ShippingPointEntity.class));

		builder.addEntry(mockGenericInvoiceEntry)
				.addReceiptNumber(mockGenericInvoice.getReceiptNumbers().get(0))
				.addReceiptNumber(mockGenericInvoice.getReceiptNumbers().get(1))
				.setBatchId(mockGenericInvoice.getBatchId())
				.setBusinessUID(mockGenericInvoice.getBusiness().getUID())
				.setCreditOrDebit(mockGenericInvoice.getCreditOrDebit())
				.setCustomerUID(mockGenericInvoice.getCustomer().getUID())
				.setDate(mockGenericInvoice.getDate())
//				NOT IMPLEMENTED
//				.setDiscounts(DiscountType.VALUE,
//						mockGenericInvoice.getDiscountsAmount())
				.setGeneralLedgerDate(mockGenericInvoice.getGeneralLedgerDate())
				.setOfficeNumber(mockGenericInvoice.getOfficeNumber())
				.setPaymentTerms(mockGenericInvoice.getPaymentTerms())
				.setSelfBilled(mockGenericInvoice.isSelfBilled())
				.setSettlementDate(mockGenericInvoice.getSettlementDate())
				.setSettlementDescription(
						mockGenericInvoice.getSettlementDescription())
				.setSettlementDiscount(
						mockGenericInvoice.getSettlementDiscount())
				.setShippingDestination(mockShippingPoint)
				.setShippingOrigin(mockShippingPoint)
				.setSourceId(mockGenericInvoice.getSourceId())
				.setSupplierUID(mockGenericInvoice.getSupplier().getUID())
				.setTransactionId(mockGenericInvoice.getTransactionId());
		
		GenericInvoice genericInvoice = builder.build();
	}

	public MockGenericInvoiceEntity loadFixture(
			Class<MockGenericInvoiceEntity> clazz) {
		MockGenericInvoiceEntity result = (MockGenericInvoiceEntity) createMockEntity(
				generateMockEntityConstructor(MockGenericInvoiceEntity.class),
				GENERIC_INVOICE_YML);

		MockBusinessEntity business = new MockBusinessEntity();
		business.uid = new UID("uid_business");
		Mockito.when(
				getInstance(DAOBusiness.class).get(Matchers.any(UID.class)))
				.thenReturn(business);
		result.business = business;

		MockCustomerEntity customer = new MockCustomerEntity();
		customer.uid = new UID("uid_customer");
		Mockito.when(
				getInstance(DAOCustomer.class).get(Matchers.any(UID.class)))
				.thenReturn(customer);
		result.customer = customer;

		MockSupplierEntity supplier = new MockSupplierEntity();
		supplier.uid = new UID("uid_supplier");
		Mockito.when(
				getInstance(DAOSupplier.class).get(Matchers.any(UID.class)))
				.thenReturn(supplier);
		result.supplier = supplier;

		result.receiptNumbers = Arrays.asList(new String[] { "123", "124" });

		MockGenericInvoiceEntryEntity invoiceEntry = new MockGenericInvoiceEntryEntity();
		invoiceEntry.uid = new UID("uid_invoice");
		Mockito.when(
				getInstance(DAOGenericInvoiceEntry.class).get(
						Matchers.any(UID.class))).thenReturn(invoiceEntry);
		result.entries = Arrays
				.asList(new GenericInvoiceEntry[] { invoiceEntry });

		result.currency = Currency.getInstance("EUR");

		return result;
	}
}
