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
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.spain.SpainDependencyModule;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNote;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntity;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;
import com.premiumminds.billy.spain.services.export.ESCreditNoteData;
import com.premiumminds.billy.spain.services.export.ESCreditNoteDataExtractor;
import com.premiumminds.billy.spain.services.export.pdf.creditnote.ESCreditNotePDFFOPTransformer;
import com.premiumminds.billy.spain.services.export.pdf.creditnote.ESCreditNoteTemplateBundle;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.ESMockDependencyModule;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.util.ESCreditNoteTestUtil;
import com.premiumminds.billy.spain.util.Services;
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

class TestESCreditNotePDFTransformer extends ESPersistencyAbstractTest {

    private static final String XSL_PATH = "src/main/resources/templates/es_creditnote.xsl";
    private static final String LOGO_PATH = "src/main/resources/logoBig.png";
    private Injector mockedInjector;
    private ESCreditNotePDFFOPTransformer transformer;
    private ESCreditNoteDataExtractor extractor;

    @BeforeEach public void setUp() throws FileNotFoundException {

        this.mockedInjector =
                Guice.createInjector(Modules.override(new SpainDependencyModule()).with(new ESMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestESCreditNotePDFTransformer.XSL_PATH);

        this.transformer = new ESCreditNotePDFFOPTransformer(TestESCreditNotePDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(ESCreditNoteDataExtractor.class);
    }

    @Test
    void testPdfCreation()
            throws ExportServiceException, DocumentIssuingException, IOException, SeriesUniqueCodeNotFilled,
            DocumentSeriesDoesNotExistException {

        StringID<GenericInvoice> uidEntity = StringID.fromValue("12345");
        final StringID<Business> businessUID = StringID.fromValue(UUID.randomUUID().toString());
        this.createSeries(businessUID);
        ESInvoiceEntity invoice = this.getNewIssuedInvoice(businessUID);
        ESCreditNoteEntity entity = this.generateESCreditNote(invoice);
        DAOESCreditNote dao = this.mockedInjector.getInstance(DAOESCreditNote.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(uidEntity))).thenReturn(entity);
        DAOESInvoice daoInvoice = this.mockedInjector.getInstance(DAOESInvoice.class);
        Mockito.when(daoInvoice.get(ArgumentMatchers.eq(invoice.getUID()))).thenReturn(invoice);

        final File result = File.createTempFile("Result", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        ESCreditNoteData entityData = this.extractor.extract(uidEntity);
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
        ESInvoiceEntity invoice = this.getNewIssuedInvoice(businessUID);
        ESCreditNoteEntity entity = this.generateESCreditNote(invoice);
        DAOESCreditNote dao = this.mockedInjector.getInstance(DAOESCreditNote.class);
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
        ESInvoiceEntity invoice = this.getNewIssuedInvoice(businessUID);
        ESCreditNoteEntity entity = this.generateESCreditNote(invoice);
        DAOESCreditNote dao = this.mockedInjector.getInstance(DAOESCreditNote.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(uidEntity))).thenReturn(entity);
        DAOESInvoice daoInvoice = this.mockedInjector.getInstance(DAOESInvoice.class);
        Mockito.when(daoInvoice.get(ArgumentMatchers.eq(invoice.getUID()))).thenReturn(invoice);

        final File result = File.createTempFile("Result", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        InputStream xsl = Files.newInputStream(Paths.get(TestESCreditNotePDFTransformer.XSL_PATH));
        ESCreditNoteTemplateBundle bundle =
                new ESCreditNoteTemplateBundle(TestESCreditNotePDFTransformer.LOGO_PATH, xsl);
        ESCreditNotePDFFOPTransformer transformerBundle = new ESCreditNotePDFFOPTransformer(bundle);

        ESCreditNoteData entityData = this.extractor.extract(uidEntity);
        transformerBundle.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    private ESCreditNoteEntity generateESCreditNote(ESInvoiceEntity reference)
            throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException {

        Services services = new Services(ESAbstractTest.injector);

        ESIssuingParams params = this.getParameters("AC", "3000");

        this.createSeries(reference, "AC");

        ESCreditNoteEntity creditNote = (ESCreditNoteEntity) services.issueDocument(
            new ESCreditNoteTestUtil(ESAbstractTest.injector).getCreditNoteBuilder(reference),
            params);

        creditNote.setCustomer(reference.getCustomer());
        creditNote.setBusiness((BusinessEntity) reference.getBusiness());
        creditNote.setCreditOrDebit(CreditOrDebit.DEBIT);

        return creditNote;
    }
}
