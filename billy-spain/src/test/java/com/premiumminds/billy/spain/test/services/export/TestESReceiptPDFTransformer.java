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
import com.premiumminds.billy.spain.SpainDependencyModule;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntity;
import com.premiumminds.billy.spain.services.export.ESReceiptData;
import com.premiumminds.billy.spain.services.export.ESReceiptDataExtractor;
import com.premiumminds.billy.spain.services.export.pdf.receipt.ESReceiptPDFFOPTransformer;
import com.premiumminds.billy.spain.services.export.pdf.receipt.ESReceiptTemplateBundle;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.ESMockDependencyModule;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.util.ESReceiptTestUtil;

public class TestESReceiptPDFTransformer extends ESPersistencyAbstractTest {

    private static final String XSL_PATH = "src/main/resources/templates/es_receipt.xsl";
    private static final String LOGO_PATH = "src/main/resources/logoBig.png";

    private ESReceiptTestUtil receipts;
    private Injector mockedInjector;
    private ESReceiptPDFFOPTransformer transformer;
    private ESReceiptDataExtractor extractor;

    @Before
    public void setUp() throws FileNotFoundException {

        this.receipts = new ESReceiptTestUtil(ESAbstractTest.injector);

        this.mockedInjector =
                Guice.createInjector(Modules.override(new SpainDependencyModule()).with(new ESMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestESReceiptPDFTransformer.XSL_PATH);

        this.transformer = new ESReceiptPDFFOPTransformer(TestESReceiptPDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(ESReceiptDataExtractor.class);
    }

    @Test
    public void testPDFCreation() throws ExportServiceException, IOException {
        ESReceiptEntity entity = this.receipts.getReceiptEntity();
        DAOESReceipt dao = this.mockedInjector.getInstance(DAOESReceipt.class);
        Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);

        OutputStream os = new FileOutputStream(File.createTempFile("ReceiptCreation", ".pdf"));

        ESReceiptData entityData = this.extractor.extract(entity.getUID());
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
        ESReceiptEntity entity = this.receipts.getReceiptEntity();
        DAOESReceipt dao = this.mockedInjector.getInstance(DAOESReceipt.class);
        Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);

        OutputStream os = new FileOutputStream(File.createTempFile("ReceiptCreation", ".pdf"));

        InputStream xsl = new FileInputStream(TestESReceiptPDFTransformer.XSL_PATH);
        ESReceiptTemplateBundle bundle = new ESReceiptTemplateBundle(TestESReceiptPDFTransformer.LOGO_PATH, xsl);
        ESReceiptPDFFOPTransformer transformerBundle = new ESReceiptPDFFOPTransformer(bundle);

        ESReceiptData entityData = this.extractor.extract(entity.getUID());
        transformerBundle.transform(entityData, os);
    }
}
