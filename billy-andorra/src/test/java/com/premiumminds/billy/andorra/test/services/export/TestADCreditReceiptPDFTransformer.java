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
import com.premiumminds.billy.andorra.persistence.entities.ADCreditReceiptEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADReceiptEntity;
import com.premiumminds.billy.andorra.test.util.ADCreditReceiptTestUtil;
import com.premiumminds.billy.andorra.util.Services;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditReceipt;
import com.premiumminds.billy.andorra.persistence.dao.DAOADReceipt;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParams;
import com.premiumminds.billy.andorra.services.export.ADCreditReceiptData;
import com.premiumminds.billy.andorra.services.export.ADCreditReceiptDataExtractor;
import com.premiumminds.billy.andorra.services.export.pdf.creditreceipt.ADCreditReceiptPDFFOPTransformer;
import com.premiumminds.billy.andorra.services.export.pdf.creditreceipt.ADCreditReceiptTemplateBundle;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.ADMockDependencyModule;
import com.premiumminds.billy.andorra.test.ADPersistencyAbstractTest;

class TestADCreditReceiptPDFTransformer extends ADPersistencyAbstractTest {

    private static final String XSL_PATH = "src/main/resources/templates/ad_creditreceipt.xsl";
    private static final String LOGO_PATH = "src/main/resources/logoBig.png";

    private Injector mockedInjector;
    private ADCreditReceiptPDFFOPTransformer transformer;
    private ADCreditReceiptDataExtractor extractor;

    @BeforeEach public void setUp() throws FileNotFoundException {

        this.mockedInjector = Guice.createInjector(
            Modules.override(new AndorraDependencyModule()).with(new ADMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestADCreditReceiptPDFTransformer.XSL_PATH);

        this.transformer = new ADCreditReceiptPDFFOPTransformer(TestADCreditReceiptPDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(ADCreditReceiptDataExtractor.class);
    }

    @Test
    void testPdfCreation()
        throws ExportServiceException, DocumentIssuingException, IOException, SeriesUniqueCodeNotFilled,
        DocumentSeriesDoesNotExistException {

        StringID<GenericInvoice> uidEntity = StringID.fromValue("12345");
        final StringID<Business> businessUID = StringID.fromValue(UUID.randomUUID().toString());
        this.createSeries(businessUID);
        ADReceiptEntity receipt = this.getNewIssuedReceipt(businessUID);
        ADCreditReceiptEntity entity = this.generateESCreditReceipt(receipt);
        DAOADCreditReceipt dao = this.mockedInjector.getInstance(DAOADCreditReceipt.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(uidEntity))).thenReturn(entity);
        DAOADReceipt daoReceipt = this.mockedInjector.getInstance(DAOADReceipt.class);
        Mockito.when(daoReceipt.get(ArgumentMatchers.eq(receipt.getUID()))).thenReturn(receipt);

        final File result = File.createTempFile("Result", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        ADCreditReceiptData entityData = this.extractor.extract(uidEntity);
        this.transformer.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    @Test
    void testNonExistentEntity() {
        StringID<GenericInvoice> uidEntity = StringID.fromValue("12345");
        Assertions.assertThrows(ExportServiceException.class, () -> this.extractor.extract(uidEntity));
    }

    @Test
    void testPdfCreationFromBundle()
        throws ExportServiceException, DocumentIssuingException, IOException, SeriesUniqueCodeNotFilled,
        DocumentSeriesDoesNotExistException {

        StringID<GenericInvoice> uidEntity = StringID.fromValue("12345");
        final StringID<Business> businessUID = StringID.fromValue(UUID.randomUUID().toString());
        this.createSeries(businessUID);
        ADReceiptEntity receipt = this.getNewIssuedReceipt(businessUID);
        ADCreditReceiptEntity entity = this.generateESCreditReceipt(receipt);
        DAOADCreditReceipt dao = this.mockedInjector.getInstance(DAOADCreditReceipt.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(uidEntity))).thenReturn(entity);
        DAOADReceipt daoReceipt = this.mockedInjector.getInstance(DAOADReceipt.class);
        Mockito.when(daoReceipt.get(ArgumentMatchers.eq(receipt.getUID()))).thenReturn(receipt);

        final File result = File.createTempFile("Result", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        InputStream xsl = Files.newInputStream(Paths.get(TestADCreditReceiptPDFTransformer.XSL_PATH));
        ADCreditReceiptTemplateBundle bundle = new ADCreditReceiptTemplateBundle(
            TestADCreditReceiptPDFTransformer.LOGO_PATH, xsl);
        ADCreditReceiptPDFFOPTransformer transformerBundle = new ADCreditReceiptPDFFOPTransformer(bundle);

        ADCreditReceiptData entityData = this.extractor.extract(uidEntity);
        transformerBundle.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    private ADCreditReceiptEntity generateESCreditReceipt(ADReceiptEntity reference)
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException {

        Services services = new Services(ADAbstractTest.injector);

        ADIssuingParams params = this.getParameters("AC", "3000");

        this.createSeries(reference, "AC");

        ADCreditReceiptEntity creditReceipt = (ADCreditReceiptEntity) services.issueDocument(
            new ADCreditReceiptTestUtil(ADAbstractTest.injector).getCreditReceiptBuilder(reference), params);

        creditReceipt.setBusiness((BusinessEntity) reference.getBusiness());
        creditReceipt.setCreditOrDebit(CreditOrDebit.DEBIT);

        return creditReceipt;
    }
}
