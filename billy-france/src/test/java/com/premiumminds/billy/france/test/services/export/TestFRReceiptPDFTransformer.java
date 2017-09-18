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
import com.premiumminds.billy.france.persistence.dao.DAOFRReceipt;
import com.premiumminds.billy.france.persistence.entities.FRReceiptEntity;
import com.premiumminds.billy.france.services.export.FRReceiptData;
import com.premiumminds.billy.france.services.export.FRReceiptDataExtractor;
import com.premiumminds.billy.france.services.export.pdf.receipt.FRReceiptPDFFOPTransformer;
import com.premiumminds.billy.france.services.export.pdf.receipt.FRReceiptTemplateBundle;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.FRMockDependencyModule;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.util.FRReceiptTestUtil;

public class TestFRReceiptPDFTransformer extends FRPersistencyAbstractTest {

    private static final String XSL_PATH = "src/main/resources/templates/es_receipt.xsl";
    private static final String LOGO_PATH = "src/main/resources/logoBig.png";

    private FRReceiptTestUtil receipts;
    private Injector mockedInjector;
    private FRReceiptPDFFOPTransformer transformer;
    private FRReceiptDataExtractor extractor;

    @Before
    public void setUp() throws FileNotFoundException {

        this.receipts = new FRReceiptTestUtil(FRAbstractTest.injector);

        this.mockedInjector =
                Guice.createInjector(Modules.override(new FranceDependencyModule()).with(new FRMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestFRReceiptPDFTransformer.XSL_PATH);

        this.transformer = new FRReceiptPDFFOPTransformer(TestFRReceiptPDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(FRReceiptDataExtractor.class);
    }

    @Test
    public void testPDFCreation() throws ExportServiceException, IOException {
        FRReceiptEntity entity = this.receipts.getReceiptEntity();
        DAOFRReceipt dao = this.mockedInjector.getInstance(DAOFRReceipt.class);
        Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);

        OutputStream os = new FileOutputStream(File.createTempFile("ReceiptCreation", ".pdf"));

        FRReceiptData entityData = this.extractor.extract(entity.getUID());
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
        FRReceiptEntity entity = this.receipts.getReceiptEntity();
        DAOFRReceipt dao = this.mockedInjector.getInstance(DAOFRReceipt.class);
        Mockito.when(dao.get(Matchers.eq(entity.getUID()))).thenReturn(entity);

        OutputStream os = new FileOutputStream(File.createTempFile("ReceiptCreation", ".pdf"));

        InputStream xsl = new FileInputStream(TestFRReceiptPDFTransformer.XSL_PATH);
        FRReceiptTemplateBundle bundle = new FRReceiptTemplateBundle(TestFRReceiptPDFTransformer.LOGO_PATH, xsl);
        FRReceiptPDFFOPTransformer transformerBundle = new FRReceiptPDFFOPTransformer(bundle);

        FRReceiptData entityData = this.extractor.extract(entity.getUID());
        transformerBundle.transform(entityData, os);
    }
}
