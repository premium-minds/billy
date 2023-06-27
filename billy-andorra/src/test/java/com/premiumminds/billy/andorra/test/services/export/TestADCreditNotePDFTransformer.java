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
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditNote;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.persistence.entities.ADCreditNoteEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParams;
import com.premiumminds.billy.andorra.services.export.ADCreditNoteData;
import com.premiumminds.billy.andorra.services.export.ADCreditNoteDataExtractor;
import com.premiumminds.billy.andorra.services.export.pdf.creditnote.ADCreditNotePDFFOPTransformer;
import com.premiumminds.billy.andorra.services.export.pdf.creditnote.ADCreditNoteTemplateBundle;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.ADMockDependencyModule;
import com.premiumminds.billy.andorra.test.ADPersistencyAbstractTest;
import com.premiumminds.billy.andorra.test.util.ADCreditNoteTestUtil;
import com.premiumminds.billy.andorra.util.Services;
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
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

public class TestADCreditNotePDFTransformer extends ADPersistencyAbstractTest {

    private static final String XSL_PATH = "src/main/resources/templates/ad_creditnote.xsl";
    private static final String LOGO_PATH = "src/main/resources/logoBig.png";
    private Injector mockedInjector;
    private ADCreditNotePDFFOPTransformer transformer;
    private ADCreditNoteDataExtractor extractor;

    @BeforeEach public void setUp() throws FileNotFoundException {

        this.mockedInjector =
                Guice.createInjector(Modules.override(new AndorraDependencyModule()).with(new ADMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestADCreditNotePDFTransformer.XSL_PATH);

        this.transformer = new ADCreditNotePDFFOPTransformer(TestADCreditNotePDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(ADCreditNoteDataExtractor.class);
    }

    @Test
    void testPdfCreation()
            throws ExportServiceException, DocumentIssuingException, IOException, SeriesUniqueCodeNotFilled,
            DocumentSeriesDoesNotExistException {

        StringID<GenericInvoice> uidEntity = StringID.fromValue("12345");
        final StringID<Business> businessUID = StringID.fromValue(UUID.randomUUID().toString());
        this.createSeries(businessUID);
        ADInvoiceEntity invoice = this.getNewIssuedInvoice(businessUID);
        ADCreditNoteEntity entity = this.generateESCreditNote(invoice);
        DAOADCreditNote dao = this.mockedInjector.getInstance(DAOADCreditNote.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(uidEntity))).thenReturn(entity);
        DAOADInvoice daoInvoice = this.mockedInjector.getInstance(DAOADInvoice.class);
        Mockito.when(daoInvoice.get(ArgumentMatchers.eq(invoice.getUID()))).thenReturn(invoice);

        final File result = File.createTempFile("Result", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        ADCreditNoteData entityData = this.extractor.extract(uidEntity);
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
    void testNonExistentInvoice()
            throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException {

        StringID<GenericInvoice> uidEntity = StringID.fromValue("12345");
        final StringID<Business> businessUID = StringID.fromValue(UUID.randomUUID().toString());
        this.createSeries(businessUID);
        ADInvoiceEntity invoice = this.getNewIssuedInvoice(businessUID);
        ADCreditNoteEntity entity = this.generateESCreditNote(invoice);
        DAOADCreditNote dao = this.mockedInjector.getInstance(DAOADCreditNote.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(uidEntity))).thenReturn(entity);

        Assertions.assertThrows(ExportServiceException.class, () -> this.extractor.extract(uidEntity));
    }

    @Test
    void testPdfCreationFromBundle()
            throws ExportServiceException, DocumentIssuingException, IOException, SeriesUniqueCodeNotFilled,
            DocumentSeriesDoesNotExistException {

        StringID<GenericInvoice> uidEntity = StringID.fromValue("12345");
        final StringID<Business> businessUID = StringID.fromValue(UUID.randomUUID().toString());
        this.createSeries(businessUID);
        ADInvoiceEntity invoice = this.getNewIssuedInvoice(businessUID);
        ADCreditNoteEntity entity = this.generateESCreditNote(invoice);
        DAOADCreditNote dao = this.mockedInjector.getInstance(DAOADCreditNote.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(uidEntity))).thenReturn(entity);
        DAOADInvoice daoInvoice = this.mockedInjector.getInstance(DAOADInvoice.class);
        Mockito.when(daoInvoice.get(ArgumentMatchers.eq(invoice.getUID()))).thenReturn(invoice);

        final File result = File.createTempFile("Result", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        InputStream xsl = Files.newInputStream(Paths.get(TestADCreditNotePDFTransformer.XSL_PATH));
        ADCreditNoteTemplateBundle bundle =
                new ADCreditNoteTemplateBundle(TestADCreditNotePDFTransformer.LOGO_PATH, xsl);
        ADCreditNotePDFFOPTransformer transformerBundle = new ADCreditNotePDFFOPTransformer(bundle);

        ADCreditNoteData entityData = this.extractor.extract(uidEntity);
        transformerBundle.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    private ADCreditNoteEntity generateESCreditNote(ADInvoiceEntity reference)
            throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException {

        Services services = new Services(ADAbstractTest.injector);

        ADIssuingParams params = this.getParameters("AC", "3000");

        this.createSeries(reference, "AC");

        ADCreditNoteEntity creditNote = (ADCreditNoteEntity) services.issueDocument(
            new ADCreditNoteTestUtil(ADAbstractTest.injector).getCreditNoteBuilder(reference), params);

        creditNote.setCustomer(reference.getCustomer());
        creditNote.setBusiness((BusinessEntity) reference.getBusiness());
        creditNote.setCreditOrDebit(CreditOrDebit.DEBIT);

        return creditNote;
    }
}
