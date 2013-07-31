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

import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.services.documents.PTInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.PTIssuingParams;
import com.premiumminds.billy.portugal.services.documents.PTIssuingParamsImpl;
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
	private final static String SOURCE_ID = "P";
	private static final String INVOICE_UID = "invoice_issueHandler_uid";
	private static final String DEFAULT_SERIES = "DEFAULT";

	@Before
	public void setUpParamenters() {
		KeyGenerator generator = new KeyGenerator(PRIVATE_KEY_DIR);

		parameters = new PTIssuingParamsImpl();
		parameters.setPrivateKey(generator.getPrivateKey());
		parameters.setPublicKey(generator.getPublicKey());

		try {
			issueNewInvoice(INVOICE_UID, PRODUCT_UID, DEFAULT_SERIES,
					DEFAULT_TYPE);
		} catch (DocumentIssuingException e) {
			e.printStackTrace();
		}
	}

	private void issueNewInvoice(String uid, String productUID, String series,
			TYPE type) throws DocumentIssuingException {
		PTInvoiceIssuingHandler handler = getInstance(PTInvoiceIssuingHandler.class);
		parameters.setInvoiceSeries(series);
		handler.issue(new PTInvoiceTestUtil(injector).getSimpleInvoiceEntity(
				type, productUID, ENTRY_UID, uid), parameters);
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

	/**
	 * Test issue of invoice of different type in same series
	 * 
	 * @throws DocumentIssuingException
	 */
	@Test(expected = DocumentIssuingException.class)
	public void testIssuedInvoiceFailure() throws DocumentIssuingException {
		String series = "NEW_SERIES";
		String type = "FS";
		String UID1 = "invoice_uid_1";
		String UID2 = "invoice_uid_2";
		String PUID1 = "product_uid_1";
		String PUID2 = "product_uid_2";

		issueNewInvoice(UID1, PUID1, series, DEFAULT_TYPE);
		issueNewInvoice(UID2, PUID2, series, TYPE.FS);
	}
}
