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
package com.premiumminds.billy.spain.test.services.export;

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
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.export.pdf.invoice.ESInvoicePDFExportHandler;
import com.premiumminds.billy.spain.services.export.pdf.invoice.ESInvoiceTemplateBundle;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.util.ESInvoiceTestUtil;

public class TestESInvoicePDFExportHandler extends ESPersistencyAbstractTest {

	public static final int NUM_ENTRIES = 10;
	public static final String XSL_PATH = "src/main/resources/templates/es_invoice.xsl";
	public static final String LOGO_PATH = "src/main/resources/logoBig.png";

	public static final String SOFTWARE_CERTIFICATE_NUMBER = "4321";
	private InputStream xsl;
	File file;
	ESInvoiceTemplateBundle bundle;
	ESInvoicePDFExportHandler handler;
	ESInvoiceTestUtil test;

	@Before
	public void setUp() throws FileNotFoundException {
		xsl = new FileInputStream(TestESInvoicePDFExportHandler.XSL_PATH);

		bundle = new ESInvoiceTemplateBundle(
				TestESInvoicePDFExportHandler.LOGO_PATH, xsl);

		handler = new ESInvoicePDFExportHandler(
				ESAbstractTest.injector.getInstance(DAOESInvoice.class));

		test = new ESInvoiceTestUtil(ESAbstractTest.injector);
	}

	@Test
	public void testPDFcreation() throws NoSuchAlgorithmException,
			ExportServiceException, URISyntaxException, IOException {

		file = File.createTempFile("ResultCreation", ".pdf");
		handler.toFile(file.toURI(), this.generateESInvoice(), bundle);
	}

	@Test
	public void testDiferentRegion() throws NoSuchAlgorithmException,
			ExportServiceException, URISyntaxException, IOException {

		File file = File.createTempFile("ResultDiferentRegions", ".pdf");

		InputStream xsl = new FileInputStream(
				TestESInvoicePDFExportHandler.XSL_PATH);

		ESInvoiceTemplateBundle bundle = new ESInvoiceTemplateBundle(
				TestESInvoicePDFExportHandler.LOGO_PATH, xsl);
		ESInvoicePDFExportHandler handler = new ESInvoicePDFExportHandler(
				ESAbstractTest.injector.getInstance(DAOESInvoice.class));
		handler.toFile(file.toURI(), this.generateOtherregionsInvoice(), bundle);
	}

	@Test
	public void testManyEntries() throws NoSuchAlgorithmException,
			ExportServiceException, URISyntaxException, IOException {

		File file = File.createTempFile("ResultManyEntries", ".pdf");

		InputStream xsl = new FileInputStream(
				TestESInvoicePDFExportHandler.XSL_PATH);

		ESInvoiceTemplateBundle bundle = new ESInvoiceTemplateBundle(
				TestESInvoicePDFExportHandler.LOGO_PATH, xsl);
		ESInvoicePDFExportHandler handler = new ESInvoicePDFExportHandler(
				ESAbstractTest.injector.getInstance(DAOESInvoice.class));
		handler.toFile(file.toURI(), this.generateManyEntriesInvoice(), bundle);
	}
	
	@Test
	public void testManyEntriesWithDifrentRegions() throws NoSuchAlgorithmException,
			ExportServiceException, URISyntaxException, IOException {

		File file = File.createTempFile("ResultManyEntriesWithDiferentRegions", ".pdf");

		InputStream xsl = new FileInputStream(
				TestESInvoicePDFExportHandler.XSL_PATH);

		ESInvoiceTemplateBundle bundle = new ESInvoiceTemplateBundle(
				TestESInvoicePDFExportHandler.LOGO_PATH, xsl);
		ESInvoicePDFExportHandler handler = new ESInvoicePDFExportHandler(
				ESAbstractTest.injector.getInstance(DAOESInvoice.class));
		handler.toFile(file.toURI(), this.generateManyEntriesWithDiferentRegionsInvoice(), bundle);
	}

	private ESInvoiceEntity generateESInvoice() {
		ESInvoiceEntity invoice = test.getInvoiceEntity();
		return invoice;
	}
	
	private ESInvoiceEntity generateManyEntriesInvoice() {
		ESInvoiceEntity invoice = test.getManyEntriesInvoice();
		return invoice;
	}
	
	private ESInvoiceEntity generateOtherregionsInvoice() {
		ESInvoiceEntity invoice = test.getDiferentRegionsInvoice();
		return invoice;
	}
	
	private ESInvoiceEntity generateManyEntriesWithDiferentRegionsInvoice() {
		ESInvoiceEntity invoice = test.getManyEntriesWithDiferentRegionsInvoice();
		return invoice;
	}
}
