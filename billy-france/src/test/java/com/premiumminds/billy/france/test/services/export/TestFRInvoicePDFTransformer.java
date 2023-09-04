/*
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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.france.FranceDependencyModule;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.services.export.FRInvoiceData;
import com.premiumminds.billy.france.services.export.FRInvoiceDataExtractor;
import com.premiumminds.billy.france.services.export.pdf.invoice.FRInvoicePDFFOPTransformer;
import com.premiumminds.billy.france.services.export.pdf.invoice.FRInvoiceTemplateBundle;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.FRMockDependencyModule;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.util.FRInvoiceTestUtil;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;

public class TestFRInvoicePDFTransformer extends FRPersistencyAbstractTest {

    public static final String XSL_PATH = "src/main/resources/templates/fr_invoice.xsl";
    public static final String LOGO_PATH = "src/main/resources/logoBig.png";

    FRInvoiceTestUtil test;
    private Injector mockedInjector;
    private FRInvoicePDFFOPTransformer transformer;
    private FRInvoiceDataExtractor extractor;

    @BeforeEach
    public void setUp() throws FileNotFoundException {

        this.mockedInjector =
                Guice.createInjector(Modules.override(new FranceDependencyModule()).with(new FRMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestFRInvoicePDFTransformer.XSL_PATH);

        this.transformer = new FRInvoicePDFFOPTransformer(TestFRInvoicePDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(FRInvoiceDataExtractor.class);
        this.test = new FRInvoiceTestUtil(FRAbstractTest.injector);
    }

    @Test
    public void testPdfCreation()
            throws ExportServiceException, IOException {

        FRInvoiceEntity entity = this.generateFRInvoice();
        DAOFRInvoice dao = this.mockedInjector.getInstance(DAOFRInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ResultCreation", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        FRInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);

        try (PDDocument doc = Loader.loadPDF(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    @Test
    public void testNonExistentEntity() {
        StringID<GenericInvoice> uidEntity = StringID.fromValue("12345");
        Assertions.assertThrows(ExportServiceException.class, () -> this.extractor.extract(uidEntity));
    }

    @Test
    public void testDifferentRegion()
            throws ExportServiceException, IOException {

        FRInvoiceEntity entity = this.generateOtherRegionsInvoice();
        DAOFRInvoice dao = this.mockedInjector.getInstance(DAOFRInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ResultDifferentRegions", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        FRInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);

        try (PDDocument doc = Loader.loadPDF(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    @Test
    public void testManyEntries()
            throws ExportServiceException, IOException {

        FRInvoiceEntity entity = this.generateManyEntriesInvoice();
        DAOFRInvoice dao = this.mockedInjector.getInstance(DAOFRInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ResultManyEntries", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        FRInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);

        try (PDDocument doc = Loader.loadPDF(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    @Test
    public void testManyEntriesWithDifferentRegions()
            throws ExportServiceException, IOException {

        FRInvoiceEntity entity = this.generateManyEntriesWithDifferentRegionsInvoice();
        DAOFRInvoice dao = this.mockedInjector.getInstance(DAOFRInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ResultManyEntriesWithDifferentRegions", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        FRInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);

        try (PDDocument doc = Loader.loadPDF(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }
    @Test
    public void testPdfCreationFromBundle() throws ExportServiceException, IOException {
        FRInvoiceEntity entity = this.generateFRInvoice();
        DAOFRInvoice dao = this.mockedInjector.getInstance(DAOFRInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ResultCreation", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        InputStream xsl = Files.newInputStream(Paths.get(TestFRInvoicePDFTransformer.XSL_PATH));
        FRInvoiceTemplateBundle bundle = new FRInvoiceTemplateBundle(TestFRInvoicePDFTransformer.LOGO_PATH, xsl);
        FRInvoicePDFFOPTransformer transformerBundle = new FRInvoicePDFFOPTransformer(bundle);

        FRInvoiceData entityData = this.extractor.extract(entity.getUID());
        transformerBundle.transform(entityData, os);

        try (PDDocument doc = Loader.loadPDF(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
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
        FRInvoiceEntity invoice = this.test.getDifferentRegionsInvoice();
        return invoice;
    }

    private FRInvoiceEntity generateManyEntriesWithDifferentRegionsInvoice() {
        FRInvoiceEntity invoice = this.test.getManyEntriesWithDifferentRegionsInvoice();
        return invoice;
    }
}
