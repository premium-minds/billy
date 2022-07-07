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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.france.FranceDependencyModule;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceipt;
import com.premiumminds.billy.france.persistence.entities.FRReceiptEntity;
import com.premiumminds.billy.france.services.export.FRReceiptData;
import com.premiumminds.billy.france.services.export.FRReceiptDataExtractor;
import com.premiumminds.billy.france.services.export.pdf.receipt.FRReceiptPDFFOPTransformer;
import com.premiumminds.billy.france.services.export.pdf.receipt.FRReceiptTemplateBundle;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.FRMockDependencyModule;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.util.FRReceiptTestUtil;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFRReceiptPDFTransformer extends FRPersistencyAbstractTest {

    private static final String XSL_PATH = "src/main/resources/templates/fr_receipt.xsl";
    private static final String LOGO_PATH = "src/main/resources/logoBig.png";

    private FRReceiptTestUtil receipts;
    private Injector mockedInjector;
    private FRReceiptPDFFOPTransformer transformer;
    private FRReceiptDataExtractor extractor;

    @BeforeEach
    public void setUp() throws FileNotFoundException {

        this.receipts = new FRReceiptTestUtil(FRAbstractTest.injector);

        this.mockedInjector =
                Guice.createInjector(Modules.override(new FranceDependencyModule()).with(new FRMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestFRReceiptPDFTransformer.XSL_PATH);

        this.transformer = new FRReceiptPDFFOPTransformer(TestFRReceiptPDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(FRReceiptDataExtractor.class);
    }

    @Test
    public void testPdfCreation() throws ExportServiceException, IOException {
        FRReceiptEntity entity = this.receipts.getReceiptEntity();
        DAOFRReceipt dao = this.mockedInjector.getInstance(DAOFRReceipt.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ReceiptCreation", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        FRReceiptData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    @Test
    public void testNonExistentEntity() {

        UID uidEntity = UID.fromString("12345");

        Assertions.assertThrows(ExportServiceException.class, () -> this.extractor.extract(uidEntity));
    }

    @Test
    public void testPdfCreationFromBundle() throws ExportServiceException, IOException {
        FRReceiptEntity entity = this.receipts.getReceiptEntity();
        DAOFRReceipt dao = this.mockedInjector.getInstance(DAOFRReceipt.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("ReceiptCreation", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        InputStream xsl = Files.newInputStream(Paths.get(TestFRReceiptPDFTransformer.XSL_PATH));
        FRReceiptTemplateBundle bundle = new FRReceiptTemplateBundle(TestFRReceiptPDFTransformer.LOGO_PATH, xsl);
        FRReceiptPDFFOPTransformer transformerBundle = new FRReceiptPDFFOPTransformer(bundle);

        FRReceiptData entityData = this.extractor.extract(entity.getUID());
        transformerBundle.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }
}
