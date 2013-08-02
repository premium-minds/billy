/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy platypus (PT Pack).
 * 
 * billy platypus (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy platypus (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.documents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.exceptions.InvalidInvoiceDateException;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParamsImpl;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;
import com.premiumminds.billy.portugal.util.KeyGenerator;

public class TestPTInvoiceIssuingHandler extends PTPersistencyAbstractTest {

	private static final String PRIVATE_KEY_DIR = "src/test/resources/keys/private.pem";
	private PTIssuingParams parameters;
	private final static String PRODUCT_UID = "Prod_invoiceHandle_uid";
	private final static String ENTRY_UID = "Entry_invoiceHandle_uid";
	private final static TYPE DEFAULT_TYPE = TYPE.FT;
	private static final String INVOICE_UID = "invoice_issueHandler_uid";
	private static final String DEFAULT_SERIES = "DEFAULT";
	private static final String BUSINESS_UID = "BUSINESS_UID_TEST";
	private static final String Customer_UID = "CUSTOMER_UID_TEST";

	@Before
	public void setUpParamenters() {
		KeyGenerator generator = new KeyGenerator(PRIVATE_KEY_DIR);

		parameters = new PTIssuingParamsImpl();
		parameters.setPrivateKey(generator.getPrivateKey());
		parameters.setPublicKey(generator.getPublicKey());

		try {
			issueNewInvoice(INVOICE_UID, PRODUCT_UID, DEFAULT_SERIES,
					DEFAULT_TYPE, BUSINESS_UID, Customer_UID);
		} catch (DocumentIssuingException e) {
			e.printStackTrace();
		}
	}

	private void issueNewInvoice(String uid, String productUID, String series,
			TYPE type, String businessUID, String customerUID)
			throws DocumentIssuingException {
		PTInvoiceIssuingHandler handler = getInstance(PTInvoiceIssuingHandler.class);
		parameters.setInvoiceSeries(series);
		PTInvoiceEntity invoice = new PTInvoiceTestUtil(injector)
				.getSimpleInvoiceEntity(type, ENTRY_UID, uid, businessUID,
						customerUID, productUID);

		invoice.setDate(new Date(invoice.getCreateTimestamp().getTime() + 100));

		handler.issue(invoice, parameters);
	}

	private void issueNewInvoice(String uid, String productUID, String series,
			TYPE type, Date invoiceDate, String businessUID, String customerUID)
			throws DocumentIssuingException {
		PTInvoiceIssuingHandler handler = getInstance(PTInvoiceIssuingHandler.class);
		parameters.setInvoiceSeries(series);
		PTInvoiceEntity invoice = new PTInvoiceTestUtil(injector)
				.getSimpleInvoiceEntity(type, ENTRY_UID, uid, businessUID,
						customerUID, productUID);

		invoice.setDate(invoiceDate);

		handler.issue(invoice, parameters);
	}

	@Test
	public void testIssuedInvoiceSimple() throws DocumentIssuingException {
		PTInvoice issuedInvoice = (PTInvoice) getInstance(DAOPTInvoice.class)
				.get(new UID(INVOICE_UID));

		assertEquals(DEFAULT_SERIES, issuedInvoice.getSeries());
		assertTrue(1 == issuedInvoice.getSeriesNumber());
		String formatedNumber = DEFAULT_TYPE + " " + DEFAULT_SERIES + "/1";
		assertEquals(formatedNumber, issuedInvoice.getNumber());
	}

	@Test
	public void testIssuedInvoiceSameSeries() throws DocumentIssuingException {
		String UID1 = "invoice_uid_1";
		String PUID1 = "product_uid_1";
		String BUID1 = "business_uid_1";
		String CUID1 = "customer_uid_1";
		Integer nextNumber = 2;
		issueNewInvoice(UID1, PUID1, DEFAULT_SERIES, DEFAULT_TYPE, BUID1, CUID1);

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
		issueNewInvoice(UID1, PUID1, newSeries, DEFAULT_TYPE, BUID1, CUID1);

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
	@Test(expected = DocumentIssuingException.class)
	public void testIssuedInvoiceFailure() throws DocumentIssuingException {
		String series = "NEW_SERIES";
		String UID1 = "invoice_uid_1";
		String UID2 = "invoice_uid_2";
		String PUID1 = "product_uid_1";
		String PUID2 = "product_uid_2";
		String BUID1 = "business_uid_1";
		String CUID1 = "customer_uid_1";

		issueNewInvoice(UID1, PUID1, series, DEFAULT_TYPE, BUID1, CUID1);
		issueNewInvoice(UID2, PUID2, series, TYPE.FS, BUID1, CUID1);
	}

	@Test(expected = InvalidInvoiceDateException.class)
	public void testIssuedInvoiceBeforeDate() throws DocumentIssuingException {
		String UID1 = "invoice_uid_1";
		String PUID1 = "product_uid_1";
		String BUID1 = "business_uid_1";
		String CUID1 = "customer_uid_1";

		issueNewInvoice(UID1, PUID1, DEFAULT_SERIES, DEFAULT_TYPE, new Date(0),
				BUID1, CUID1);
	}

	public void testIssuedInvoiceSameSourceBilling()
			throws DocumentIssuingException {
		String UID1 = "invoice_uid_1";
		String PUID1 = "product_uid_1";
		String BUID1 = "business_uid_1";
		String CUID1 = "customer_uid_1";

		issueNewInvoice(UID1, PUID1, DEFAULT_SERIES, DEFAULT_TYPE, BUID1, CUID1);
	}

}
