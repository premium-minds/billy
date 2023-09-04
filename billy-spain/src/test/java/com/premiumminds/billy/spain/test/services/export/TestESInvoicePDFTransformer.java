/*
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

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.spain.SpainDependencyModule;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.export.ESInvoiceData;
import com.premiumminds.billy.spain.services.export.ESInvoiceDataExtractor;
import com.premiumminds.billy.spain.services.export.pdf.invoice.ESInvoicePDFFOPTransformer;
import com.premiumminds.billy.spain.services.export.pdf.invoice.ESInvoiceTemplateBundle;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.ESMockDependencyModule;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.util.ESInvoiceTestUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

class TestESInvoicePDFTransformer extends ESPersistencyAbstractTest {

    private static final String XSL_PATH = "src/main/resources/templates/es_invoice.xsl";
    private static final String LOGO_PATH = "src/main/resources/logoBig.png";

    private ESInvoiceTestUtil test;
    private Injector mockedInjector;
    private ESInvoicePDFFOPTransformer transformer;
    private ESInvoiceDataExtractor extractor;

    @BeforeEach
    public void setUp() throws FileNotFoundException {

        this.mockedInjector =
                Guice.createInjector(Modules.override(new SpainDependencyModule()).with(new ESMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestESInvoicePDFTransformer.XSL_PATH);

        this.transformer = new ESInvoicePDFFOPTransformer(TestESInvoicePDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(ESInvoiceDataExtractor.class);
        this.test = new ESInvoiceTestUtil(ESAbstractTest.injector);
    }

    @Test
    void testPdfCreation()
            throws ExportServiceException, IOException {

        ESInvoiceEntity entity = this.test.getInvoiceEntity();
        DAOESInvoice dao = this.mockedInjector.getInstance(DAOESInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ResultCreation", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        ESInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);

        try (PDDocument doc = Loader.loadPDF(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    @Test
    void testNonExistentEntity() {
        StringID<GenericInvoice> uidEntity = StringID.fromValue("12345");
        Assertions.assertThrows(ExportServiceException.class, () -> this.extractor.extract(uidEntity));
    }

    @Test
    void testDifferentRegion()
            throws ExportServiceException, IOException {

        ESInvoiceEntity entity = this.test.getDifferentRegionsInvoice();
        DAOESInvoice dao = this.mockedInjector.getInstance(DAOESInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ResultDifferentRegions", ".pdf");
        OutputStream os = new FileOutputStream(result);

        ESInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);

        try (PDDocument doc = Loader.loadPDF(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    @Test
    void testManyEntries()
            throws ExportServiceException, IOException {

        ESInvoiceEntity entity = this.test.getManyEntriesInvoice();
        DAOESInvoice dao = this.mockedInjector.getInstance(DAOESInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ResultManyEntries", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        ESInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);

        try (PDDocument doc = Loader.loadPDF(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    @Test
    void testManyEntriesWithDifrentRegions()
            throws ExportServiceException, IOException {

        ESInvoiceEntity entity = this.test.getManyEntriesWithDifferentRegionsInvoice();
        DAOESInvoice dao = this.mockedInjector.getInstance(DAOESInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ResultManyEntriesWithDifferentRegions", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        ESInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);

        try (PDDocument doc = Loader.loadPDF(result)) {
            assertEquals(2, doc.getNumberOfPages());
        }
    }

    @Test
    void testPdfCreationFromBundle() throws ExportServiceException, IOException {
        ESInvoiceEntity entity = this.test.getInvoiceEntity();
        DAOESInvoice dao = this.mockedInjector.getInstance(DAOESInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ResultCreation", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        InputStream xsl = Files.newInputStream(Paths.get(TestESInvoicePDFTransformer.XSL_PATH));
        ESInvoiceTemplateBundle bundle = new ESInvoiceTemplateBundle(TestESInvoicePDFTransformer.LOGO_PATH, xsl);
        ESInvoicePDFFOPTransformer transformerBundle = new ESInvoicePDFFOPTransformer(bundle);

        ESInvoiceData entityData = this.extractor.extract(entity.getUID());
        transformerBundle.transform(entityData, os);

        try (PDDocument doc = Loader.loadPDF(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }
}
