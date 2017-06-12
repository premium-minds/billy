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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.spain.SpainDependencyModule;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.export.ESInvoiceData;
import com.premiumminds.billy.spain.services.export.ESInvoiceExtractor;
import com.premiumminds.billy.spain.services.export.pdf.invoice.impl.ESInvoicePDFFOPTransformer;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.ESMockDependencyModule;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.util.ESInvoiceTestUtil;

public class TestESInvoicePDFTransformer extends ESPersistencyAbstractTest {

	public static final int NUM_ENTRIES = 10;
	public static final String XSL_PATH = "src/main/resources/templates/es_invoice.xsl";
	public static final String LOGO_PATH = "src/main/resources/logoBig.png";

	ESInvoiceTestUtil test;
	private Injector mockedInjector;
	private ESInvoicePDFFOPTransformer transformer;
	private ESInvoiceExtractor extractor;

	@Before
	public void setUp() throws FileNotFoundException {
		
		mockedInjector = Guice.createInjector(Modules.override(
				new SpainDependencyModule()).with(
				new ESMockDependencyModule()));
		
		InputStream xsl = new FileInputStream(XSL_PATH);

		transformer = new ESInvoicePDFFOPTransformer(LOGO_PATH, xsl);
		extractor = mockedInjector.getInstance(ESInvoiceExtractor.class);
		test = new ESInvoiceTestUtil(ESAbstractTest.injector);
	}

	@Test
	public void testPDFcreation() throws NoSuchAlgorithmException,
			ExportServiceException, URISyntaxException, IOException {

		ESInvoiceEntity entity = generateESInvoice();
		DAOESInvoice dao = mockedInjector.getInstance(DAOESInvoice.class);
		Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);
		
		OutputStream os = new FileOutputStream(File.createTempFile("ResultCreation", ".pdf"));
		
		ESInvoiceData entityData = extractor.extract(entity.getUID());
		transformer.transform(entityData, os);
	}
	
	@Test(expected = ExportServiceException.class)
	public void testNonExistentEntity() throws NoSuchAlgorithmException,
		ExportServiceException, URISyntaxException, DocumentIssuingException,
		IOException {

		UID uidEntity = UID.fromString("12345");
		
		extractor.extract(uidEntity);
	}

	@Test
	public void testDiferentRegion() throws NoSuchAlgorithmException,
			ExportServiceException, URISyntaxException, IOException {

		ESInvoiceEntity entity = generateOtherRegionsInvoice();
		DAOESInvoice dao = mockedInjector.getInstance(DAOESInvoice.class);
		Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);
		
		OutputStream os = new FileOutputStream(File.createTempFile("ResultDiferentRegions", ".pdf"));
		
		ESInvoiceData entityData = extractor.extract(entity.getUID());
		transformer.transform(entityData, os);
	}

	@Test
	public void testManyEntries() throws NoSuchAlgorithmException,
			ExportServiceException, URISyntaxException, IOException {

		ESInvoiceEntity entity = generateManyEntriesInvoice();
		DAOESInvoice dao = mockedInjector.getInstance(DAOESInvoice.class);
		Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);
		
		OutputStream os = new FileOutputStream(File.createTempFile("ResultManyEntries", ".pdf"));
		
		ESInvoiceData entityData = extractor.extract(entity.getUID());
		transformer.transform(entityData, os);
	}
	
	@Test
	public void testManyEntriesWithDifrentRegions() throws NoSuchAlgorithmException,
			ExportServiceException, URISyntaxException, IOException {

		ESInvoiceEntity entity = generateManyEntriesWithDiferentRegionsInvoice();
		DAOESInvoice dao = mockedInjector.getInstance(DAOESInvoice.class);
		Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);
		
		OutputStream os = new FileOutputStream(File.createTempFile("ResultManyEntriesWithDiferentRegions", ".pdf"));
		
		ESInvoiceData entityData = extractor.extract(entity.getUID());
		transformer.transform(entityData, os);
	}

	private ESInvoiceEntity generateESInvoice() {
		ESInvoiceEntity invoice = test.getInvoiceEntity();
		return invoice;
	}
	
	private ESInvoiceEntity generateManyEntriesInvoice() {
		ESInvoiceEntity invoice = test.getManyEntriesInvoice();
		return invoice;
	}
	
	private ESInvoiceEntity generateOtherRegionsInvoice() {
		ESInvoiceEntity invoice = test.getDiferentRegionsInvoice();
		return invoice;
	}
	
	private ESInvoiceEntity generateManyEntriesWithDiferentRegionsInvoice() {
		ESInvoiceEntity invoice = test.getManyEntriesWithDiferentRegionsInvoice();
		return invoice;
	}
}
