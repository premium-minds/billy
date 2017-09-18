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
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.france.FranceDependencyModule;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditReceipt;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceipt;
import com.premiumminds.billy.france.persistence.entities.FRCreditReceiptEntity;
import com.premiumminds.billy.france.persistence.entities.FRReceiptEntity;
import com.premiumminds.billy.france.services.documents.util.FRIssuingParams;
import com.premiumminds.billy.france.services.export.FRCreditReceiptData;
import com.premiumminds.billy.france.services.export.FRCreditReceiptDataExtractor;
import com.premiumminds.billy.france.services.export.pdf.creditreceipt.FRCreditReceiptPDFFOPTransformer;
import com.premiumminds.billy.france.services.export.pdf.creditreceipt.FRCreditReceiptTemplateBundle;
import com.premiumminds.billy.france.util.Services;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.FRMockDependencyModule;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.util.FRCreditReceiptTestUtil;

public class TestFRCreditReceiptPDFTransformer extends FRPersistencyAbstractTest {

    public static final int NUM_ENTRIES = 10;
    public static final String XSL_PATH = "src/main/resources/templates/es_creditreceipt.xsl";
    public static final String LOGO_PATH = "src/main/resources/logoBig.png";

    private Injector mockedInjector;
    private FRCreditReceiptPDFFOPTransformer transformer;
    private FRCreditReceiptDataExtractor extractor;

    @Before
    public void setUp() throws FileNotFoundException {

        this.mockedInjector =
                Guice.createInjector(Modules.override(new FranceDependencyModule()).with(new FRMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestFRCreditReceiptPDFTransformer.XSL_PATH);

        this.transformer = new FRCreditReceiptPDFFOPTransformer(TestFRCreditReceiptPDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(FRCreditReceiptDataExtractor.class);
    }

    @Test
    public void testPDFcreation() throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException,
            DocumentIssuingException, IOException {

        UID uidEntity = UID.fromString("12345");
        FRReceiptEntity receipt = this.getNewIssuedReceipt((new UID()).toString());
        FRCreditReceiptEntity entity = this.generateFRCreditReceipt(PaymentMechanism.CASH, receipt);
        DAOFRCreditReceipt dao = this.mockedInjector.getInstance(DAOFRCreditReceipt.class);
        Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(entity);
        DAOFRReceipt daoReceipt = this.mockedInjector.getInstance(DAOFRReceipt.class);
        Mockito.when(daoReceipt.get(Matchers.eq(receipt.getUID()))).thenReturn(receipt);

        OutputStream os = new FileOutputStream(File.createTempFile("Result", ".pdf"));

        FRCreditReceiptData entityData = this.extractor.extract(uidEntity);
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
        FRReceiptEntity receipt = this.getNewIssuedReceipt((new UID()).toString());
        FRCreditReceiptEntity entity = this.generateFRCreditReceipt(PaymentMechanism.CASH, receipt);
        DAOFRCreditReceipt dao = this.mockedInjector.getInstance(DAOFRCreditReceipt.class);
        Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(entity);
        DAOFRReceipt daoReceipt = this.mockedInjector.getInstance(DAOFRReceipt.class);
        Mockito.when(daoReceipt.get(Matchers.eq(receipt.getUID()))).thenReturn(receipt);

        OutputStream os = new FileOutputStream(File.createTempFile("Result", ".pdf"));

        InputStream xsl = new FileInputStream(TestFRCreditReceiptPDFTransformer.XSL_PATH);
        FRCreditReceiptTemplateBundle bundle =
                new FRCreditReceiptTemplateBundle(TestFRCreditReceiptPDFTransformer.LOGO_PATH, xsl);
        FRCreditReceiptPDFFOPTransformer transformerBundle = new FRCreditReceiptPDFFOPTransformer(bundle);

        FRCreditReceiptData entityData = this.extractor.extract(uidEntity);
        transformerBundle.transform(entityData, os);
    }

    private FRCreditReceiptEntity generateFRCreditReceipt(PaymentMechanism paymentMechanism, FRReceiptEntity reference)
            throws DocumentIssuingException {

        Services services = new Services(FRAbstractTest.injector);

        FRIssuingParams params = this.getParameters("AC", "3000");

        FRCreditReceiptEntity creditReceipt = (FRCreditReceiptEntity) services.issueDocument(
                new FRCreditReceiptTestUtil(FRAbstractTest.injector).getCreditReceiptBuilder(reference), params);

        creditReceipt.setBusiness((BusinessEntity) reference.getBusiness());
        creditReceipt.setCreditOrDebit(CreditOrDebit.CREDIT);

        return creditReceipt;
    }
}
