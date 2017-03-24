/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.services.export.pdf.simpleinvoice.PTSimpleInvoicePDFExportHandler;
import com.premiumminds.billy.portugal.services.export.pdf.simpleinvoice.PTSimpleInvoiceTemplateBundle;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTSimpleInvoiceTestUtil;

public class TestPTSimpleInvoicePDFExportHandler extends
	PTPersistencyAbstractTest {

	public static final int		NUM_ENTRIES					= 10;
	public static final String	XSL_PATH					= "src/main/resources/templates/pt_simpleinvoice.xsl";
	public static final String	LOGO_PATH					= "src/main/resources/logoBig.png";

	public static final String	SOFTWARE_CERTIFICATE_NUMBER	= "4321";

	@Test
	public void testPDFcreation() throws NoSuchAlgorithmException,
		ExportServiceException, URISyntaxException, IOException {

		File file = File.createTempFile("Result", ".pdf");

		InputStream xsl = new FileInputStream(
				TestPTSimpleInvoicePDFExportHandler.XSL_PATH);

		PTSimpleInvoiceTemplateBundle bundle = new PTSimpleInvoiceTemplateBundle(
				TestPTSimpleInvoicePDFExportHandler.LOGO_PATH, xsl,
				TestPTSimpleInvoicePDFExportHandler.SOFTWARE_CERTIFICATE_NUMBER);
		PTSimpleInvoicePDFExportHandler handler = new PTSimpleInvoicePDFExportHandler(
				PTAbstractTest.injector.getInstance(DAOPTSimpleInvoice.class));
		handler.toFile(file.toURI(),
				this.generatePTSimpleInvoice(PaymentMechanism.CASH), bundle);
	}

	private PTSimpleInvoiceEntity generatePTSimpleInvoice(
			PaymentMechanism paymentMechanism) {

		PTSimpleInvoiceEntity simpleInvoice = new PTSimpleInvoiceTestUtil(
				PTAbstractTest.injector).getSimpleInvoiceEntity();
		simpleInvoice
				.setHash("mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");

		return simpleInvoice;
	}
}
