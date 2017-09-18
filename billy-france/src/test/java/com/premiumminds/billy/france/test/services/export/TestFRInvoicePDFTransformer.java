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
import com.premiumminds.billy.france.FranceDependencyModule;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.services.export.FRInvoiceData;
import com.premiumminds.billy.france.services.export.FRInvoiceDataExtractor;
import com.premiumminds.billy.france.services.export.pdf.invoice.FRInvoicePDFFOPTransformer;
import com.premiumminds.billy.france.services.export.pdf.invoice.FRInvoiceTemplateBundle;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.FRMockDependencyModule;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.util.FRInvoiceTestUtil;

public class TestFRInvoicePDFTransformer extends FRPersistencyAbstractTest {

    public static final int NUM_ENTRIES = 10;
    public static final String XSL_PATH = "src/main/resources/templates/es_invoice.xsl";
    public static final String LOGO_PATH = "src/main/resources/logoBig.png";

    FRInvoiceTestUtil test;
    private Injector mockedInjector;
    private FRInvoicePDFFOPTransformer transformer;
    private FRInvoiceDataExtractor extractor;

    @Before
    public void setUp() throws FileNotFoundException {

        this.mockedInjector =
                Guice.createInjector(Modules.override(new FranceDependencyModule()).with(new FRMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestFRInvoicePDFTransformer.XSL_PATH);

        this.transformer = new FRInvoicePDFFOPTransformer(TestFRInvoicePDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(FRInvoiceDataExtractor.class);
        this.test = new FRInvoiceTestUtil(FRAbstractTest.injector);
    }

    @Test
    public void testPDFcreation()
            throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException, IOException {

        FRInvoiceEntity entity = this.generateFRInvoice();
        DAOFRInvoice dao = this.mockedInjector.getInstance(DAOFRInvoice.class);
        Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);

        OutputStream os = new FileOutputStream(File.createTempFile("ResultCreation", ".pdf"));

        FRInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);
    }

    @Test(expected = ExportServiceException.class)
    public void testNonExistentEntity() throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException,
            DocumentIssuingException, IOException {

        UID uidEntity = UID.fromString("12345");

        this.extractor.extract(uidEntity);
    }

    @Test
    public void testDiferentRegion()
            throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException, IOException {

        FRInvoiceEntity entity = this.generateOtherRegionsInvoice();
        DAOFRInvoice dao = this.mockedInjector.getInstance(DAOFRInvoice.class);
        Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);

        OutputStream os = new FileOutputStream(File.createTempFile("ResultDiferentRegions", ".pdf"));

        FRInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);
    }

    @Test
    public void testManyEntries()
            throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException, IOException {

        FRInvoiceEntity entity = this.generateManyEntriesInvoice();
        DAOFRInvoice dao = this.mockedInjector.getInstance(DAOFRInvoice.class);
        Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);

        OutputStream os = new FileOutputStream(File.createTempFile("ResultManyEntries", ".pdf"));

        FRInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);
    }

    @Test
    public void testManyEntriesWithDifrentRegions()
            throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException, IOException {

        FRInvoiceEntity entity = this.generateManyEntriesWithDiferentRegionsInvoice();
        DAOFRInvoice dao = this.mockedInjector.getInstance(DAOFRInvoice.class);
        Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);

        OutputStream os = new FileOutputStream(File.createTempFile("ResultManyEntriesWithDiferentRegions", ".pdf"));

        FRInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);
    }

    @Test
    public void testPDFCreationFromBundle() throws ExportServiceException, IOException {
        FRInvoiceEntity entity = this.generateFRInvoice();
        DAOFRInvoice dao = this.mockedInjector.getInstance(DAOFRInvoice.class);
        Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);

        OutputStream os = new FileOutputStream(File.createTempFile("ResultCreation", ".pdf"));

        InputStream xsl = new FileInputStream(TestFRInvoicePDFTransformer.XSL_PATH);
        FRInvoiceTemplateBundle bundle = new FRInvoiceTemplateBundle(TestFRInvoicePDFTransformer.LOGO_PATH, xsl);
        FRInvoicePDFFOPTransformer transformerBundle = new FRInvoicePDFFOPTransformer(bundle);

        FRInvoiceData entityData = this.extractor.extract(entity.getUID());
        transformerBundle.transform(entityData, os);
    }

    private FRInvoiceEntity generateFRInvoice() {
        FRInvoiceEntity invoice = this.test.getInvoiceEntity();
        return invoice;
    }

    private FRInvoiceEntity generateManyEntriesInvoice() {
        FRInvoiceEntity invoice = this.test.getManyEntriesInvoice();
        return invoice;
    }

    private FRInvoiceEntity generateOtherRegionsInvoice() {
        FRInvoiceEntity invoice = this.test.getDiferentRegionsInvoice();
        return invoice;
    }

    private FRInvoiceEntity generateManyEntriesWithDiferentRegionsInvoice() {
        FRInvoiceEntity invoice = this.test.getManyEntriesWithDiferentRegionsInvoice();
        return invoice;
    }
}
