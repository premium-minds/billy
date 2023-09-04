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

class TestESSimpleInvoicePDFTransformer extends ESPersistencyAbstractTest {

    private static final String XSL_PATH = "src/main/resources/templates/es_simpleinvoice.xsl";
    private static final String LOGO_PATH = "src/main/resources/logoBig.png";

    private Injector mockedInjector;
    private ESSimpleInvoicePDFFOPTransformer transformer;
    private ESSimpleInvoiceDataExtractor extractor;

    @BeforeEach
    public void setUp() throws FileNotFoundException {

        this.mockedInjector =
                Guice.createInjector(Modules.override(new SpainDependencyModule()).with(new ESMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestESSimpleInvoicePDFTransformer.XSL_PATH);

        this.transformer = new ESSimpleInvoicePDFFOPTransformer(TestESSimpleInvoicePDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(ESSimpleInvoiceDataExtractor.class);
    }

    @Test
    void testPdfCreation()
            throws ExportServiceException, IOException {

        ESSimpleInvoiceEntity entity = this.generateESSimpleInvoice();
        DAOESSimpleInvoice dao = this.mockedInjector.getInstance(DAOESSimpleInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("Result", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        ESSimpleInvoiceData entityData = this.extractor.extract(entity.getUID());
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
    void testPdfCreationFromBundle() throws ExportServiceException, IOException {
        ESSimpleInvoiceEntity entity = this.generateESSimpleInvoice();
        DAOESSimpleInvoice dao = this.mockedInjector.getInstance(DAOESSimpleInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("Result", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        InputStream xsl = Files.newInputStream(Paths.get(TestESSimpleInvoicePDFTransformer.XSL_PATH));
        ESSimpleInvoiceTemplateBundle bundle =
                new ESSimpleInvoiceTemplateBundle(TestESSimpleInvoicePDFTransformer.LOGO_PATH, xsl);
        ESSimpleInvoicePDFFOPTransformer transformerBundle = new ESSimpleInvoicePDFFOPTransformer(bundle);

        ESSimpleInvoiceData entityData = this.extractor.extract(entity.getUID());
        transformerBundle.transform(entityData, os);

        try (PDDocument doc = Loader.loadPDF(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    private ESSimpleInvoiceEntity generateESSimpleInvoice() {
        return new ESSimpleInvoiceTestUtil(ESAbstractTest.injector).getSimpleInvoiceEntity();
    }
}
