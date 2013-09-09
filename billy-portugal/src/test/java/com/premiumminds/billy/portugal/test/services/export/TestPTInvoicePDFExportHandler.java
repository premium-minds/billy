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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.export.pdf.invoice.PTInvoicePDFExportHandler;
import com.premiumminds.billy.portugal.services.export.pdf.invoice.PTInvoiceTemplateBundle;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;

public class TestPTInvoicePDFExportHandler extends PTPersistencyAbstractTest {

	public static final int NUM_ENTRIES = 10;
	public static final String XSL_PATH = "src/main/resources/templates/pt_invoice.xsl";
	public static final String LOGO_PATH = "src/main/resources/logoBig.png";
	private static final String RESULT_FILE_PATH = System
			.getProperty("java.io.tmpdir") + "/Invoice.pdf";
	public static final String SOFTWARE_CERTIFICATE_NUMBER = "4321";
	private InputStream xsl;
	File file;
	PTInvoiceTemplateBundle bundle;
	PTInvoicePDFExportHandler handler;
	PTInvoiceTestUtil test;

	@Before
	public void setUp() throws FileNotFoundException {
		xsl = new FileInputStream(TestPTInvoicePDFExportHandler.XSL_PATH);

		bundle = new PTInvoiceTemplateBundle(
				TestPTInvoicePDFExportHandler.LOGO_PATH, xsl, RESULT_FILE_PATH,
				TestPTInvoicePDFExportHandler.SOFTWARE_CERTIFICATE_NUMBER);

		handler = new PTInvoicePDFExportHandler(
				PTAbstractTest.injector.getInstance(DAOPTInvoice.class));

		test = new PTInvoiceTestUtil(PTAbstractTest.injector);
	}

	@Test
	public void testPDFcreation() throws NoSuchAlgorithmException,
			ExportServiceException, URISyntaxException, IOException {

		file = new File(RESULT_FILE_PATH);
		handler.toFile(file.toURI(), this.generatePTInvoice(), bundle);
	}

	@Test
	public void testDiferentRegion() throws NoSuchAlgorithmException,
			ExportServiceException, URISyntaxException, IOException {

		File file = new File(System.getProperty("java.io.tmpdir")
				+ "/DiferentRegions.pdf");

		InputStream xsl = new FileInputStream(
				TestPTInvoicePDFExportHandler.XSL_PATH);

		PTInvoiceTemplateBundle bundle = new PTInvoiceTemplateBundle(
				TestPTInvoicePDFExportHandler.LOGO_PATH, xsl, RESULT_FILE_PATH,
				TestPTInvoicePDFExportHandler.SOFTWARE_CERTIFICATE_NUMBER);
		PTInvoicePDFExportHandler handler = new PTInvoicePDFExportHandler(
				PTAbstractTest.injector.getInstance(DAOPTInvoice.class));
		handler.toFile(file.toURI(), this.generateOtherregionsInvoice(), bundle);
	}

	@Test
	public void testManyEntries() throws NoSuchAlgorithmException,
			ExportServiceException, URISyntaxException, IOException {

		File file = new File(System.getProperty("java.io.tmpdir")
				+ "/ManyEntries.pdf");

		InputStream xsl = new FileInputStream(
				TestPTInvoicePDFExportHandler.XSL_PATH);

		PTInvoiceTemplateBundle bundle = new PTInvoiceTemplateBundle(
				TestPTInvoicePDFExportHandler.LOGO_PATH, xsl, RESULT_FILE_PATH,
				TestPTInvoicePDFExportHandler.SOFTWARE_CERTIFICATE_NUMBER);
		PTInvoicePDFExportHandler handler = new PTInvoicePDFExportHandler(
				PTAbstractTest.injector.getInstance(DAOPTInvoice.class));
		handler.toFile(file.toURI(), this.generateManyEntriesInvoice(), bundle);
	}

	@Test
	public void testManyEntriesWithDifrentRegions()
			throws NoSuchAlgorithmException, ExportServiceException,
			URISyntaxException, IOException {

		File file = new File(System.getProperty("java.io.tmpdir")
				+ "/ManyEntriesWithDiferentRegions.pdf");

		InputStream xsl = new FileInputStream(
				TestPTInvoicePDFExportHandler.XSL_PATH);

		PTInvoiceTemplateBundle bundle = new PTInvoiceTemplateBundle(
				TestPTInvoicePDFExportHandler.LOGO_PATH, xsl, RESULT_FILE_PATH,
				TestPTInvoicePDFExportHandler.SOFTWARE_CERTIFICATE_NUMBER);
		PTInvoicePDFExportHandler handler = new PTInvoicePDFExportHandler(
				PTAbstractTest.injector.getInstance(DAOPTInvoice.class));
		handler.toFile(file.toURI(),
				this.generateManyEntriesWithDiferentRegionsInvoice(), bundle);
	}

	private PTInvoiceEntity generatePTInvoice() {
		PTInvoiceEntity invoice = test.getInvoiceEntity();
		invoice.setHash("mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");
		return invoice;
	}

	private PTInvoiceEntity generateManyEntriesInvoice() {
		PTInvoiceEntity invoice = test.getManyEntriesInvoice();
		invoice.setHash("mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");
		return invoice;
	}

	private PTInvoiceEntity generateOtherregionsInvoice() {
		PTInvoiceEntity invoice = test.getDiferentRegionsInvoice();
		invoice.setHash("mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");
		return invoice;
	}

	private PTInvoiceEntity generateManyEntriesWithDiferentRegionsInvoice() {
		PTInvoiceEntity invoice = test
				.getManyEntriesWithDiferentRegionsInvoice();
		invoice.setHash("mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");
		return invoice;
	}
}
