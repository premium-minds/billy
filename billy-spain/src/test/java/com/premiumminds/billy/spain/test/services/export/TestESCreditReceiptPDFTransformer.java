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
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.spain.SpainDependencyModule;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditReceipt;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.persistence.entities.ESCreditReceiptEntity;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntity;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;
import com.premiumminds.billy.spain.services.export.ESCreditReceiptData;
import com.premiumminds.billy.spain.services.export.ESCreditReceiptDataExtractor;
import com.premiumminds.billy.spain.services.export.pdf.creditreceipt.ESCreditReceiptPDFFOPTransformer;
import com.premiumminds.billy.spain.services.export.pdf.creditreceipt.ESCreditReceiptTemplateBundle;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.ESMockDependencyModule;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.util.ESCreditReceiptTestUtil;
import com.premiumminds.billy.spain.util.Services;

public class TestESCreditReceiptPDFTransformer extends ESPersistencyAbstractTest {

    public static final int NUM_ENTRIES = 10;
    public static final String XSL_PATH = "src/main/resources/templates/es_creditreceipt.xsl";
    public static final String LOGO_PATH = "src/main/resources/logoBig.png";

    private Injector mockedInjector;
    private ESCreditReceiptPDFFOPTransformer transformer;
    private ESCreditReceiptDataExtractor extractor;

    @Before
    public void setUp() throws FileNotFoundException {

        this.mockedInjector =
                Guice.createInjector(Modules.override(new SpainDependencyModule()).with(new ESMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestESCreditReceiptPDFTransformer.XSL_PATH);

        this.transformer = new ESCreditReceiptPDFFOPTransformer(TestESCreditReceiptPDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(ESCreditReceiptDataExtractor.class);
    }

    @Test
    public void testPDFcreation() throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException,
            DocumentIssuingException, IOException {

        UID uidEntity = UID.fromString("12345");
        ESReceiptEntity receipt = this.getNewIssuedReceipt((new UID()).toString());
        ESCreditReceiptEntity entity = this.generateESCreditReceipt(PaymentMechanism.CASH, receipt);
        DAOESCreditReceipt dao = this.mockedInjector.getInstance(DAOESCreditReceipt.class);
        Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(entity);
        DAOESReceipt daoReceipt = this.mockedInjector.getInstance(DAOESReceipt.class);
        Mockito.when(daoReceipt.get(Matchers.eq(receipt.getUID()))).thenReturn(receipt);

        OutputStream os = new FileOutputStream(File.createTempFile("Result", ".pdf"));

        ESCreditReceiptData entityData = this.extractor.extract(uidEntity);
        this.transformer.transform(entityData, os);
    }

    @Test(expected = ExportServiceException.class)
    public void testNonExistentEntity() throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException,
            DocumentIssuingException, IOException {

        UID uidEntity = UID.fromString("12345");

        this.extractor.extract(uidEntity);
    }

    @Test
    public void testPDFCreationFromBundle() throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException,
            DocumentIssuingException, IOException {

        UID uidEntity = UID.fromString("12345");
        ESReceiptEntity receipt = this.getNewIssuedReceipt((new UID()).toString());
        ESCreditReceiptEntity entity = this.generateESCreditReceipt(PaymentMechanism.CASH, receipt);
        DAOESCreditReceipt dao = this.mockedInjector.getInstance(DAOESCreditReceipt.class);
        Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(entity);
        DAOESReceipt daoReceipt = this.mockedInjector.getInstance(DAOESReceipt.class);
        Mockito.when(daoReceipt.get(Matchers.eq(receipt.getUID()))).thenReturn(receipt);

        OutputStream os = new FileOutputStream(File.createTempFile("Result", ".pdf"));

        InputStream xsl = new FileInputStream(TestESCreditReceiptPDFTransformer.XSL_PATH);
        ESCreditReceiptTemplateBundle bundle =
                new ESCreditReceiptTemplateBundle(TestESCreditReceiptPDFTransformer.LOGO_PATH, xsl);
        ESCreditReceiptPDFFOPTransformer transformerBundle = new ESCreditReceiptPDFFOPTransformer(bundle);

        ESCreditReceiptData entityData = this.extractor.extract(uidEntity);
        transformerBundle.transform(entityData, os);
    }

    private ESCreditReceiptEntity generateESCreditReceipt(PaymentMechanism paymentMechanism, ESReceiptEntity reference)
            throws DocumentIssuingException {

        Services services = new Services(ESAbstractTest.injector);

        ESIssuingParams params = this.getParameters("AC", "3000");

        ESCreditReceiptEntity creditReceipt = (ESCreditReceiptEntity) services.issueDocument(
                new ESCreditReceiptTestUtil(ESAbstractTest.injector).getCreditReceiptBuilder(reference), params);

        creditReceipt.setBusiness((BusinessEntity) reference.getBusiness());
        creditReceipt.setCreditOrDebit(CreditOrDebit.CREDIT);

        return creditReceipt;
    }
}
