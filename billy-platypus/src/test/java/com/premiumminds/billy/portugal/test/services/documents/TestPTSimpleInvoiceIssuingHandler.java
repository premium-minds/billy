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
package com.premiumminds.billy.portugal.test.services.documents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice;
import com.premiumminds.billy.portugal.test.util.PTSimpleInvoiceTestUtil;

public class TestPTSimpleInvoiceIssuingHandler extends PTDocumentAbstractTest {

	private static final TYPE DEFAULT_TYPE = TYPE.FS;
	private static final SourceBilling SOURCE_BILLING = SourceBilling.P;

	private PTSimpleInvoiceIssuingHandler handler;

	protected PTSimpleInvoiceEntity newInvoice(String invoiceUID,
			String productUID, TYPE type, String businessUID, String customerUID) {

		return new PTSimpleInvoiceTestUtil(injector).getSimpleInvoiceEntity(
				type, ENTRY_UID, invoiceUID, businessUID, customerUID,
				Arrays.asList(productUID));
	}

	@Before
	public void setUpNewSimpleInvoice() {
		handler = getInstance(PTSimpleInvoiceIssuingHandler.class);

		try {
			PTSimpleInvoiceEntity invoice = newInvoice(INVOICE_UID,
					PRODUCT_UID, DEFAULT_TYPE, BUSINESS_UID, CUSTOMER_UID);

			issueNewInvoice(handler, invoice, DEFAULT_SERIES);
		} catch (DocumentIssuingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIssuedInvoiceSimple() throws DocumentIssuingException {
		PTSimpleInvoice issuedInvoice = (PTSimpleInvoice) getInstance(
				DAOPTSimpleInvoice.class).get(new UID(INVOICE_UID));

		assertEquals(DEFAULT_SERIES, issuedInvoice.getSeries());
		assertTrue(1 == issuedInvoice.getSeriesNumber());
		String formatedNumber = DEFAULT_TYPE + " " + DEFAULT_SERIES + "/1";
		assertEquals(formatedNumber, issuedInvoice.getNumber());
		assertEquals(SOURCE_BILLING, issuedInvoice.getSourceBilling());
	}
}
