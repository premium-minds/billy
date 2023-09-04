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
import com.premiumminds.billy.andorra.persistence.entities.ADReceiptEntity;
import com.premiumminds.billy.andorra.services.export.ADReceiptDataExtractor;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.ADMockDependencyModule;
import com.premiumminds.billy.andorra.test.util.ADReceiptTestUtil;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.andorra.persistence.dao.DAOADReceipt;
import com.premiumminds.billy.andorra.services.export.ADReceiptData;
import com.premiumminds.billy.andorra.services.export.pdf.receipt.ADReceiptPDFFOPTransformer;
import com.premiumminds.billy.andorra.services.export.pdf.receipt.ADReceiptTemplateBundle;
import com.premiumminds.billy.andorra.test.ADPersistencyAbstractTest;
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

class TestADReceiptPDFTransformer extends ADPersistencyAbstractTest {

    private static final String XSL_PATH = "src/main/resources/templates/ad_receipt.xsl";
    private static final String LOGO_PATH = "src/main/resources/logoBig.png";

    private ADReceiptTestUtil receipts;
    private Injector mockedInjector;
    private ADReceiptPDFFOPTransformer transformer;
    private ADReceiptDataExtractor extractor;

    @BeforeEach
    public void setUp() throws FileNotFoundException {

        this.receipts = new ADReceiptTestUtil(ADAbstractTest.injector);

        this.mockedInjector =
                Guice.createInjector(Modules.override(new AndorraDependencyModule()).with(new ADMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestADReceiptPDFTransformer.XSL_PATH);

        this.transformer = new ADReceiptPDFFOPTransformer(TestADReceiptPDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(ADReceiptDataExtractor.class);
    }

    @Test
    void testPdfCreation() throws ExportServiceException, IOException {
        ADReceiptEntity entity = this.receipts.getReceiptEntity();
        DAOADReceipt dao = this.mockedInjector.getInstance(DAOADReceipt.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ReceiptCreation", ".pdf");
        OutputStream os = new FileOutputStream(result);

        ADReceiptData entityData = this.extractor.extract(entity.getUID());
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
        ADReceiptEntity entity = this.receipts.getReceiptEntity();
        DAOADReceipt dao = this.mockedInjector.getInstance(DAOADReceipt.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ReceiptCreation", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        InputStream xsl = Files.newInputStream(Paths.get(TestADReceiptPDFTransformer.XSL_PATH));
        ADReceiptTemplateBundle bundle = new ADReceiptTemplateBundle(TestADReceiptPDFTransformer.LOGO_PATH, xsl);
        ADReceiptPDFFOPTransformer transformerBundle = new ADReceiptPDFFOPTransformer(bundle);

        ADReceiptData entityData = this.extractor.extract(entity.getUID());
        transformerBundle.transform(entityData, os);

        try (PDDocument doc = Loader.loadPDF(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }
}
