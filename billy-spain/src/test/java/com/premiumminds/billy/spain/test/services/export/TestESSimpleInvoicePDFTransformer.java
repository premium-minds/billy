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
import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.spain.SpainDependencyModule;
import com.premiumminds.billy.spain.persistence.dao.DAOESSimpleInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESSimpleInvoiceEntity;
import com.premiumminds.billy.spain.services.export.ESSimpleInvoiceData;
import com.premiumminds.billy.spain.services.export.ESSimpleInvoiceDataExtractor;
import com.premiumminds.billy.spain.services.export.pdf.simpleinvoice.ESSimpleInvoiceTemplateBundle;
import com.premiumminds.billy.spain.services.export.pdf.simpleinvoice.impl.ESSimpleInvoicePDFFOPTransformer;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.ESMockDependencyModule;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.util.ESSimpleInvoiceTestUtil;

public class TestESSimpleInvoicePDFTransformer extends ESPersistencyAbstractTest {

  public static final int NUM_ENTRIES = 10;
  public static final String XSL_PATH = "src/main/resources/templates/es_simpleinvoice.xsl";
  public static final String LOGO_PATH = "src/main/resources/logoBig.png";

  private Injector mockedInjector;
  private ESSimpleInvoicePDFFOPTransformer transformer;
  private ESSimpleInvoiceDataExtractor extractor;

  @Before
  public void setUp() throws FileNotFoundException {

    mockedInjector = Guice.createInjector(
        Modules.override(new SpainDependencyModule()).with(new ESMockDependencyModule()));

    InputStream xsl = new FileInputStream(XSL_PATH);

    transformer = new ESSimpleInvoicePDFFOPTransformer(LOGO_PATH, xsl);
    extractor = mockedInjector.getInstance(ESSimpleInvoiceDataExtractor.class);
  }

  @Test
  public void testPDFcreation()
      throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException, IOException {

    ESSimpleInvoiceEntity entity = generateESSimpleInvoice(PaymentMechanism.CASH);
    DAOESSimpleInvoice dao = mockedInjector.getInstance(DAOESSimpleInvoice.class);
    Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);

    OutputStream os = new FileOutputStream(File.createTempFile("Result", ".pdf"));

    ESSimpleInvoiceData entityData = extractor.extract(entity.getUID());
    transformer.transform(entityData, os);
  }

  @Test(expected = ExportServiceException.class)
  public void testNonExistentEntity() throws NoSuchAlgorithmException, ExportServiceException,
      URISyntaxException, DocumentIssuingException, IOException {

    UID uidEntity = UID.fromString("12345");

    extractor.extract(uidEntity);
  }

  @Test
  public void testPDFCreationFromBundle() throws ExportServiceException, IOException {
    ESSimpleInvoiceEntity entity = generateESSimpleInvoice(PaymentMechanism.CASH);
    DAOESSimpleInvoice dao = mockedInjector.getInstance(DAOESSimpleInvoice.class);
    Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);

    OutputStream os = new FileOutputStream(File.createTempFile("Result", ".pdf"));

    InputStream xsl = new FileInputStream(XSL_PATH);
    ESSimpleInvoiceTemplateBundle bundle = new ESSimpleInvoiceTemplateBundle(LOGO_PATH, xsl);
    ESSimpleInvoicePDFFOPTransformer transformerBundle = new ESSimpleInvoicePDFFOPTransformer(
        bundle);

    ESSimpleInvoiceData entityData = extractor.extract(entity.getUID());
    transformerBundle.transform(entityData, os);
  }

  private ESSimpleInvoiceEntity generateESSimpleInvoice(PaymentMechanism paymentMechanism) {

    ESSimpleInvoiceEntity simpleInvoice = new ESSimpleInvoiceTestUtil(ESAbstractTest.injector)
        .getSimpleInvoiceEntity();

    return simpleInvoice;
  }
}
