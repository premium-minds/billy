/**
 * Copyright (C) 2017 Premium Minds.
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
import com.premiumminds.billy.portugal.PortugalDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.export.PTInvoiceData;
import com.premiumminds.billy.portugal.services.export.PTInvoiceDataExtractor;
import com.premiumminds.billy.portugal.services.export.pdf.invoice.PTInvoicePDFFOPTransformer;
import com.premiumminds.billy.portugal.services.export.pdf.invoice.PTInvoiceTemplateBundle;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PTMockDependencyModule;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;

public class TestPTInvoicePDFTransformer extends PTPersistencyAbstractTest {

  public static final int NUM_ENTRIES = 10;
  public static final String XSL_PATH = "src/main/resources/templates/pt_invoice.xsl";
  public static final String LOGO_PATH = "src/main/resources/logoBig.png";
  public static final String SOFTWARE_CERTIFICATE_NUMBER = "4321";

  Injector mockedInjector;
  PTInvoiceDataExtractor extractor;
  PTInvoicePDFFOPTransformer transformer;
  PTInvoiceTestUtil test;

  @Before
  public void setUp() throws FileNotFoundException {

    mockedInjector = Guice.createInjector(
        Modules.override(new PortugalDependencyModule()).with(new PTMockDependencyModule()));

    InputStream xsl = new FileInputStream(TestPTInvoicePDFTransformer.XSL_PATH);

    transformer = new PTInvoicePDFFOPTransformer(LOGO_PATH, xsl, SOFTWARE_CERTIFICATE_NUMBER);
    extractor = mockedInjector.getInstance(PTInvoiceDataExtractor.class);
    test = new PTInvoiceTestUtil(PTAbstractTest.injector);
  }

  @Test
  public void testPDFcreation()
      throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException, IOException {

    UID uidEntity = UID.fromString("12345");
    PTInvoiceEntity invoice = generatePTInvoice();
    DAOPTInvoice dao = mockedInjector.getInstance(DAOPTInvoice.class);
    Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(invoice);

    OutputStream os = new FileOutputStream(File.createTempFile("ResultCreation", ".pdf"));

    PTInvoiceData entityData = extractor.extract(uidEntity);
    transformer.transform(entityData, os);
  }

  @Test(expected = ExportServiceException.class)
  public void testNonExistentEntity()
      throws DocumentIssuingException, FileNotFoundException, IOException, ExportServiceException {

    UID uidEntity = UID.fromString("12345");
    extractor.extract(uidEntity);
  }

  @Test
  public void testDiferentRegion()
      throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException, IOException {

    UID uidEntity = UID.fromString("12345");
    PTInvoiceEntity invoice = generateOtherregionsInvoice();
    DAOPTInvoice dao = mockedInjector.getInstance(DAOPTInvoice.class);
    Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(invoice);

    OutputStream os = new FileOutputStream(File.createTempFile("ResultDiferentRegions", ".pdf"));

    PTInvoiceData entityData = extractor.extract(uidEntity);
    transformer.transform(entityData, os);
  }

  @Test
  public void testManyEntries()
      throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException, IOException {

    UID uidEntity = UID.fromString("12345");
    PTInvoiceEntity invoice = generateManyEntriesInvoice();
    DAOPTInvoice dao = mockedInjector.getInstance(DAOPTInvoice.class);
    Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(invoice);

    OutputStream os = new FileOutputStream(File.createTempFile("ResultManyEntries", ".pdf"));

    PTInvoiceData entityData = extractor.extract(uidEntity);
    transformer.transform(entityData, os);
  }

  @Test
  public void testManyEntriesWithDifrentRegions()
      throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException, IOException {

    UID uidEntity = UID.fromString("12345");
    PTInvoiceEntity invoice = generateManyEntriesWithDiferentRegionsInvoice();
    DAOPTInvoice dao = mockedInjector.getInstance(DAOPTInvoice.class);
    Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(invoice);

    OutputStream os = new FileOutputStream(
        File.createTempFile("ResultManyEntriesWithDiferentRegions", ".pdf"));

    PTInvoiceData entityData = extractor.extract(uidEntity);
    transformer.transform(entityData, os);
  }

  @Test
  public void testPDFCreationFromBundle() throws ExportServiceException, IOException {
    UID uidEntity = UID.fromString("12345");
    PTInvoiceEntity invoice = generatePTInvoice();
    DAOPTInvoice dao = mockedInjector.getInstance(DAOPTInvoice.class);
    Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(invoice);

    OutputStream os = new FileOutputStream(File.createTempFile("ResultCreation", ".pdf"));

    InputStream xsl = new FileInputStream(XSL_PATH);
    PTInvoiceTemplateBundle bundle = new PTInvoiceTemplateBundle(LOGO_PATH, xsl,
        SOFTWARE_CERTIFICATE_NUMBER);
    PTInvoicePDFFOPTransformer transformerBundle = new PTInvoicePDFFOPTransformer(bundle);

    PTInvoiceData entityData = extractor.extract(uidEntity);
    transformerBundle.transform(entityData, os);
  }

  private PTInvoiceEntity generatePTInvoice() {
    PTInvoiceEntity invoice = test.getInvoiceEntity();
    invoice.setHash(
        "mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");
    return invoice;
  }

  private PTInvoiceEntity generateManyEntriesInvoice() {
    PTInvoiceEntity invoice = test.getManyEntriesInvoice();
    invoice.setHash(
        "mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");
    return invoice;
  }

  private PTInvoiceEntity generateOtherregionsInvoice() {
    PTInvoiceEntity invoice = test.getDiferentRegionsInvoice();
    invoice.setHash(
        "mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");
    return invoice;
  }

  private PTInvoiceEntity generateManyEntriesWithDiferentRegionsInvoice() {
    PTInvoiceEntity invoice = test.getManyEntriesWithDiferentRegionsInvoice();
    invoice.setHash(
        "mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");
    return invoice;
  }
}
