/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.test.services.export;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.premiumminds.billy.andorra.AndorraDependencyModule;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.ADMockDependencyModule;
import com.premiumminds.billy.andorra.test.ADPersistencyAbstractTest;
import com.premiumminds.billy.andorra.test.util.ADInvoiceTestUtil;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.services.export.ADInvoiceData;
import com.premiumminds.billy.andorra.services.export.ADInvoiceDataExtractor;
import com.premiumminds.billy.andorra.services.export.pdf.invoice.ADInvoicePDFFOPTransformer;
import com.premiumminds.billy.andorra.services.export.pdf.invoice.ADInvoiceTemplateBundle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class TestADInvoicePDFTransformer extends ADPersistencyAbstractTest {

    public static final String XSL_PATH = "src/main/resources/templates/es_invoice.xsl";
    public static final String LOGO_PATH = "src/main/resources/logoBig.png";

    ADInvoiceTestUtil test;
    private Injector mockedInjector;
    private ADInvoicePDFFOPTransformer transformer;
    private ADInvoiceDataExtractor extractor;

    @BeforeEach
    public void setUp() throws FileNotFoundException {

        this.mockedInjector =
                Guice.createInjector(Modules.override(new AndorraDependencyModule()).with(new ADMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestADInvoicePDFTransformer.XSL_PATH);

        this.transformer = new ADInvoicePDFFOPTransformer(TestADInvoicePDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(ADInvoiceDataExtractor.class);
        this.test = new ADInvoiceTestUtil(ADAbstractTest.injector);
    }

    @Test
    public void testPdfCreation()
            throws ExportServiceException, IOException {

        ADInvoiceEntity entity = this.generateESInvoice();
        DAOADInvoice dao = this.mockedInjector.getInstance(DAOADInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ResultCreation", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        ADInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
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

        ADInvoiceEntity entity = this.generateOtherRegionsInvoice();
        DAOADInvoice dao = this.mockedInjector.getInstance(DAOADInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ResultDifferentRegions", ".pdf");
        OutputStream os = new FileOutputStream(result);

        ADInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    @Test
    public void testManyEntries()
            throws ExportServiceException, IOException {

        ADInvoiceEntity entity = this.generateManyEntriesInvoice();
        DAOADInvoice dao = this.mockedInjector.getInstance(DAOADInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ResultManyEntries", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        ADInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    @Test
    public void testManyEntriesWithDifrentRegions()
            throws ExportServiceException, IOException {

        ADInvoiceEntity entity = this.generateManyEntriesWithDifferentRegionsInvoice();
        DAOADInvoice dao = this.mockedInjector.getInstance(DAOADInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ResultManyEntriesWithDifferentRegions", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        ADInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
            assertEquals(2, doc.getNumberOfPages());
        }
    }

    @Test
    public void testPdfCreationFromBundle() throws ExportServiceException, IOException {
        ADInvoiceEntity entity = this.generateESInvoice();
        DAOADInvoice dao = this.mockedInjector.getInstance(DAOADInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ResultCreation", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        InputStream xsl = Files.newInputStream(Paths.get(TestADInvoicePDFTransformer.XSL_PATH));
        ADInvoiceTemplateBundle bundle = new ADInvoiceTemplateBundle(TestADInvoicePDFTransformer.LOGO_PATH, xsl);
        ADInvoicePDFFOPTransformer transformerBundle = new ADInvoicePDFFOPTransformer(bundle);

        ADInvoiceData entityData = this.extractor.extract(entity.getUID());
        transformerBundle.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    private ADInvoiceEntity generateESInvoice() {
        ADInvoiceEntity invoice = this.test.getInvoiceEntity();
        return invoice;
    }

    private ADInvoiceEntity generateManyEntriesInvoice() {
        ADInvoiceEntity invoice = this.test.getManyEntriesInvoice();
        return invoice;
    }

    private ADInvoiceEntity generateOtherRegionsInvoice() {
        ADInvoiceEntity invoice = this.test.getDifferentRegionsInvoice();
        return invoice;
    }

    private ADInvoiceEntity generateManyEntriesWithDifferentRegionsInvoice() {
        ADInvoiceEntity invoice = this.test.getManyEntriesWithDifferentRegionsInvoice();
        return invoice;
    }
}
