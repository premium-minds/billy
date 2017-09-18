/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.test.services.export;

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
import com.premiumminds.billy.france.FranceDependencyModule;
import com.premiumminds.billy.france.persistence.dao.DAOFRSimpleInvoice;
import com.premiumminds.billy.france.persistence.entities.FRSimpleInvoiceEntity;
import com.premiumminds.billy.france.services.export.FRSimpleInvoiceData;
import com.premiumminds.billy.france.services.export.FRSimpleInvoiceDataExtractor;
import com.premiumminds.billy.france.services.export.pdf.simpleinvoice.FRSimpleInvoiceTemplateBundle;
import com.premiumminds.billy.france.services.export.pdf.simpleinvoice.impl.FRSimpleInvoicePDFFOPTransformer;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.FRMockDependencyModule;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.util.FRSimpleInvoiceTestUtil;

public class TestFRSimpleInvoicePDFTransformer extends FRPersistencyAbstractTest {

    public static final int NUM_ENTRIES = 10;
    public static final String XSL_PATH = "src/main/resources/templates/es_simpleinvoice.xsl";
    public static final String LOGO_PATH = "src/main/resources/logoBig.png";

    private Injector mockedInjector;
    private FRSimpleInvoicePDFFOPTransformer transformer;
    private FRSimpleInvoiceDataExtractor extractor;

    @Before
    public void setUp() throws FileNotFoundException {

        this.mockedInjector =
                Guice.createInjector(Modules.override(new FranceDependencyModule()).with(new FRMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestFRSimpleInvoicePDFTransformer.XSL_PATH);

        this.transformer = new FRSimpleInvoicePDFFOPTransformer(TestFRSimpleInvoicePDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(FRSimpleInvoiceDataExtractor.class);
    }

    @Test
    public void testPDFcreation()
            throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException, IOException {

        FRSimpleInvoiceEntity entity = this.generateFRSimpleInvoice(PaymentMechanism.CASH);
        DAOFRSimpleInvoice dao = this.mockedInjector.getInstance(DAOFRSimpleInvoice.class);
        Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);

        OutputStream os = new FileOutputStream(File.createTempFile("Result", ".pdf"));

        FRSimpleInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);
    }

    @Test(expected = ExportServiceException.class)
    public void testNonExistentEntity() throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException,
            DocumentIssuingException, IOException {

        UID uidEntity = UID.fromString("12345");

        this.extractor.extract(uidEntity);
    }

    @Test
    public void testPDFCreationFromBundle() throws ExportServiceException, IOException {
        FRSimpleInvoiceEntity entity = this.generateFRSimpleInvoice(PaymentMechanism.CASH);
        DAOFRSimpleInvoice dao = this.mockedInjector.getInstance(DAOFRSimpleInvoice.class);
        Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);

        OutputStream os = new FileOutputStream(File.createTempFile("Result", ".pdf"));

        InputStream xsl = new FileInputStream(TestFRSimpleInvoicePDFTransformer.XSL_PATH);
        FRSimpleInvoiceTemplateBundle bundle =
                new FRSimpleInvoiceTemplateBundle(TestFRSimpleInvoicePDFTransformer.LOGO_PATH, xsl);
        FRSimpleInvoicePDFFOPTransformer transformerBundle = new FRSimpleInvoicePDFFOPTransformer(bundle);

        FRSimpleInvoiceData entityData = this.extractor.extract(entity.getUID());
        transformerBundle.transform(entityData, os);
    }

    private FRSimpleInvoiceEntity generateFRSimpleInvoice(PaymentMechanism paymentMechanism) {

        FRSimpleInvoiceEntity simpleInvoice =
                new FRSimpleInvoiceTestUtil(FRAbstractTest.injector).getSimpleInvoiceEntity();

        return simpleInvoice;
    }
}
