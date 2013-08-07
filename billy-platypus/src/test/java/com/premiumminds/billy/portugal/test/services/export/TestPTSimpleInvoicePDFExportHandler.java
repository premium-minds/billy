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
package com.premiumminds.billy.portugal.test.services.export;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.junit.Test;

import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.export.pdf.simpleinvoice.PTSimpleInvoicePDFExportHandler;
import com.premiumminds.billy.portugal.services.export.pdf.simpleinvoice.PTSimpleInvoiceTemplateBundle;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;
import com.premiumminds.billy.portugal.test.util.PTSimpleInvoiceTestUtil;
import com.premiumminds.billy.portugal.util.PaymentMechanism;

public class TestPTSimpleInvoicePDFExportHandler extends
		PTPersistencyAbstractTest {
	public static final int NUM_ENTRIES = 10;
	public static final String XSL_PATH = "src/main/resources/pt_simpleinvoice.xsl";
	public static final String LOGO_PATH = "src/main/resources/logoBig.png";
	public static final String URI_PATH = "file://"
			+ System.getProperty("user.dir") + "/src/main/resources/Result.pdf";
	private static final TYPE DEFAULT_TYPE = TYPE.FS;
	private static final SourceBilling SOURCE_BILLING = SourceBilling.P;
	protected static final String PRODUCT_UID = "PRODUCT_ISSUE_UID";
	protected static final String BUSINESS_UID = "BUSINESS_UID_TEST";
	protected static final String CUSTOMER_UID = "CUSTOMER_UID_TEST";
	protected static final String INVOICE_UID = "INVOICE_ISSUE_UID";
	protected static final String DEFAULT_SERIES = "DEFAULT";
	protected static final String ENTRY_UID = "ENTRY_ISSUE_UID";

	public static final String SOFTWARE_CERTIFICATE_NUMBER = "4321";
	public static final byte[] SAMPLE_HASH = { 0xa, 0x1, 0x3, 0xf, 0x7, 0x5,
			0x4, 0xd, 0xa, 0x1, 0x3, 0xf, 0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4,
			0xd, 0xa, 0x1, 0x3, 0xf, 0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd,
			0xa, 0x1, 0x3, 0xf, 0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa,
			0x1, 0x3, 0xf, 0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1,
			0x3, 0xf, 0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3,
			0xf, 0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf,
			0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf, 0xa,
			0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf };

	@Test
	public void testPDFcreation() throws NoSuchAlgorithmException,
			ExportServiceException, FileNotFoundException, URISyntaxException {
		InputStream xsl = new FileInputStream(XSL_PATH);

		PTSimpleInvoiceTemplateBundle bundle = new PTSimpleInvoiceTemplateBundle(
				LOGO_PATH, xsl, SOFTWARE_CERTIFICATE_NUMBER);
		PTSimpleInvoicePDFExportHandler handler = new PTSimpleInvoicePDFExportHandler(
				injector.getInstance(DAOPTSimpleInvoice.class));
		handler.toFile(new URI(URI_PATH),
				generatePTSimpleInvoice(PaymentMechanism.CASH), bundle);
	}

	private PTSimpleInvoiceEntity generatePTSimpleInvoice(
			PaymentMechanism paymentMechanism) {
		
		PTSimpleInvoiceEntity simpleInvoice = new PTSimpleInvoiceTestUtil(
				injector).getSimpleInvoiceEntity(DEFAULT_TYPE, ENTRY_UID,
				INVOICE_UID, BUSINESS_UID, CUSTOMER_UID,
				Arrays.asList(PRODUCT_UID), SOURCE_BILLING);
		simpleInvoice.setPaymentMechanism(paymentMechanism);
		simpleInvoice
				.setHash("mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");

		return simpleInvoice;
	}
}
