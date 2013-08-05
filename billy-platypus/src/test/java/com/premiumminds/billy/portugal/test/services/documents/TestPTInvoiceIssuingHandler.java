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
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.exceptions.InvalidInvoiceDateException;
import com.premiumminds.billy.portugal.services.documents.exceptions.InvalidInvoiceTypeException;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;

public class TestPTInvoiceIssuingHandler extends PTDocumentAbstractTest {

	private static final TYPE DEFAULT_TYPE = TYPE.FT;
	private static final SourceBilling SOURCE_BILLING = SourceBilling.P;

	private PTInvoiceIssuingHandler handler;

	protected PTInvoiceEntity newInvoice(String invoiceUID, String productUID,
			TYPE type, String businessUID, String customerUID) {

		return new PTInvoiceTestUtil(injector).getSimpleInvoiceEntity(type,
				ENTRY_UID, invoiceUID, businessUID, customerUID,
				Arrays.asList(productUID));
	}

	@Before
	public void setUpNewInvoice() {
		handler = getInstance(PTInvoiceIssuingHandler.class);

		try {
			PTInvoiceEntity invoice = newInvoice(INVOICE_UID, PRODUCT_UID,
					DEFAULT_TYPE, BUSINESS_UID, CUSTOMER_UID);

			issueNewInvoice(handler, invoice, DEFAULT_SERIES);
		} catch (DocumentIssuingException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testIssuedInvoiceSimple() throws DocumentIssuingException {
		PTInvoice issuedInvoice = (PTInvoice) getInstance(DAOPTInvoice.class)
				.get(new UID(INVOICE_UID));

		assertEquals(DEFAULT_SERIES, issuedInvoice.getSeries());
		assertTrue(1 == issuedInvoice.getSeriesNumber());
		String formatedNumber = DEFAULT_TYPE + " " + DEFAULT_SERIES + "/1";
		assertEquals(formatedNumber, issuedInvoice.getNumber());
		assertEquals(SOURCE_BILLING, issuedInvoice.getSourceBilling());
	}

	@Test
	public void testIssuedInvoiceSameSeries() throws DocumentIssuingException {
		String UID1 = "invoice_uid_1";
		String PUID1 = "product_uid_1";
		String BUID1 = "business_uid_1";
		String CUID1 = "customer_uid_1";
		Integer nextNumber = 2;
		issueNewInvoice(handler,
				newInvoice(UID1, PUID1, DEFAULT_TYPE, BUID1, CUID1),
				DEFAULT_SERIES);

		PTInvoice issuedInvoice = (PTInvoice) getInstance(DAOPTInvoice.class)
				.get(new UID(UID1));

		assertEquals(DEFAULT_SERIES, issuedInvoice.getSeries());
		assertEquals(nextNumber, issuedInvoice.getSeriesNumber());
		String formatedNumber = DEFAULT_TYPE + " " + DEFAULT_SERIES + "/"
				+ nextNumber;
		assertEquals(formatedNumber, issuedInvoice.getNumber());
	}

	@Test
	public void testIssuedInvoiceDifferentSeries()
			throws DocumentIssuingException {
		String UID1 = "invoice_uid_1";
		String PUID1 = "product_uid_1";
		Integer nextNumber = 1;
		String newSeries = "NEW_SERIES";
		String BUID1 = "business_uid_1";
		String CUID1 = "customer_uid_1";

		issueNewInvoice(handler,
				newInvoice(UID1, PUID1, DEFAULT_TYPE, BUID1, CUID1), newSeries);

		PTInvoice issuedInvoice = (PTInvoice) getInstance(DAOPTInvoice.class)
				.get(new UID(UID1));

		assertEquals(newSeries, issuedInvoice.getSeries());
		assertEquals(nextNumber, issuedInvoice.getSeriesNumber());
		String formatedNumber = DEFAULT_TYPE + " " + newSeries + "/"
				+ nextNumber;
		assertEquals(formatedNumber, issuedInvoice.getNumber());
	}

	/**
	 * Test issue of invoice of different type in same series
	 * 
	 * @throws DocumentIssuingException
	 */
	@Test(expected = InvalidInvoiceTypeException.class)
	public void testIssuedInvoiceFailure() throws DocumentIssuingException {
		String series = "NEW_SERIES";
		String UID1 = "invoice_uid_1";
		String UID2 = "invoice_uid_2";
		String PUID1 = "product_uid_1";
		String PUID2 = "product_uid_2";
		String BUID1 = "business_uid_1";
		String CUID1 = "customer_uid_1";

		issueNewInvoice(handler,
				newInvoice(UID1, PUID1, DEFAULT_TYPE, BUID1, CUID1), series);
		issueNewInvoice(handler,
				newInvoice(UID2, PUID2, TYPE.FS, BUID1, CUID1), series);
	}

	@Test(expected = InvalidInvoiceDateException.class)
	public void testIssuedInvoiceBeforeDate() throws DocumentIssuingException {
		String UID1 = "invoice_uid_1";
		String PUID1 = "product_uid_1";
		String BUID1 = "business_uid_1";
		String CUID1 = "customer_uid_1";

		issueNewInvoice(handler,
				newInvoice(UID1, PUID1, DEFAULT_TYPE, BUID1, CUID1),
				DEFAULT_SERIES, new Date(0));
	}

	@Test
	public void testIssuedInvoiceSameSourceBilling()
			throws DocumentIssuingException {
		String UID1 = "invoice_uid_1";
		String PUID1 = "product_uid_1";
		String BUID1 = "business_uid_1";
		String CUID1 = "customer_uid_1";

		issueNewInvoice(handler,
				newInvoice(UID1, PUID1, DEFAULT_TYPE, BUID1, CUID1),
				DEFAULT_SERIES);

		PTInvoice issuedInvoice = (PTInvoice) getInstance(DAOPTInvoice.class)
				.get(new UID(UID1));

		assertEquals(SOURCE_BILLING, issuedInvoice.getSourceBilling());
	}
}
