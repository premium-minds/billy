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
package com.premiumminds.billy.spain.test.services.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.services.export.pdf.receipt.ESReceiptPDFExportHandler;
import com.premiumminds.billy.spain.services.export.pdf.receipt.ESReceiptTemplateBundle;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.util.ESReceiptTestUtil;

public class TestESReceiptPDFExportHandler extends ESPersistencyAbstractTest {
	
	private static final String XSL_PATH = "src/main/resources/templates/es_receipt.xsl";
	private static final String LOGO_PATH = "src/main/resources/logoBig.png";
	
	private InputStream xsl;
	private File file;
	private ESReceiptTemplateBundle bundle;
	private ESReceiptPDFExportHandler handler;
	private ESReceiptTestUtil receipts;
	
	@Before
	public void setUp() throws FileNotFoundException {
		xsl = new FileInputStream(XSL_PATH);
		bundle = new ESReceiptTemplateBundle(LOGO_PATH, xsl);
		handler = new ESReceiptPDFExportHandler(
				getInstance(DAOESReceipt.class));
		receipts = new ESReceiptTestUtil(injector);
	}
	
	@Test
	public void testPDFCreation() throws ExportServiceException, IOException{
		file = File.createTempFile("ReceiptCreation", ".pdf");
		handler.toFile(file.toURI()
				, receipts.getReceiptEntity()
				, bundle);
	}
}
