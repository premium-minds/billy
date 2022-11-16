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

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.france.FranceDependencyModule;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditNote;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.entities.FRCreditNoteEntity;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.services.documents.util.FRIssuingParams;
import com.premiumminds.billy.france.services.export.FRCreditNoteData;
import com.premiumminds.billy.france.services.export.FRCreditNoteDataExtractor;
import com.premiumminds.billy.france.services.export.pdf.creditnote.FRCreditNotePDFFOPTransformer;
import com.premiumminds.billy.france.services.export.pdf.creditnote.FRCreditNoteTemplateBundle;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.FRMockDependencyModule;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.util.FRCreditNoteTestUtil;
import com.premiumminds.billy.france.util.Services;
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

public class TestFRCreditNotePDFTransformer extends FRPersistencyAbstractTest {

    public static final String XSL_PATH = "src/main/resources/templates/fr_creditnote.xsl";
    public static final String LOGO_PATH = "src/main/resources/logoBig.png";
    private Injector mockedInjector;
    private FRCreditNotePDFFOPTransformer transformer;
    private FRCreditNoteDataExtractor extractor;

    @BeforeEach public void setUp() throws FileNotFoundException {

        this.mockedInjector = Guice.createInjector(
            Modules.override(new FranceDependencyModule()).with(new FRMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestFRCreditNotePDFTransformer.XSL_PATH);

        this.transformer = new FRCreditNotePDFFOPTransformer(TestFRCreditNotePDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(FRCreditNoteDataExtractor.class);
    }

    @Test public void testPdfCreation()
        throws ExportServiceException, DocumentIssuingException, IOException, SeriesUniqueCodeNotFilled,
        DocumentSeriesDoesNotExistException {

        StringID<GenericInvoice> uidEntity = StringID.fromValue("12345");
        final String businessUID = UUID.randomUUID().toString();
        this.createSeries(businessUID);
        FRInvoiceEntity invoice = this.getNewIssuedInvoice(businessUID);
        FRCreditNoteEntity entity = this.generateFRCreditNote(invoice);
        DAOFRCreditNote dao = this.mockedInjector.getInstance(DAOFRCreditNote.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(uidEntity))).thenReturn(entity);
        DAOFRInvoice daoInvoice = this.mockedInjector.getInstance(DAOFRInvoice.class);
        Mockito.when(daoInvoice.get(ArgumentMatchers.eq(invoice.getUID()))).thenReturn(invoice);

        final File result = File.createTempFile("Result", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        FRCreditNoteData entityData = this.extractor.extract(uidEntity);
        this.transformer.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    @Test public void testNonExistentEntity() {

        StringID<GenericInvoice> uidEntity = StringID.fromValue("12345");

        Assertions.assertThrows(ExportServiceException.class, () -> this.extractor.extract(uidEntity));
    }

    @Test public void testNonExistentInvoice()
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException {

        StringID<GenericInvoice> uidEntity = StringID.fromValue("12345");
        final String businessUID = UUID.randomUUID().toString();
        this.createSeries(businessUID);
        FRInvoiceEntity invoice = this.getNewIssuedInvoice(businessUID);
        FRCreditNoteEntity entity = this.generateFRCreditNote(invoice);
        DAOFRCreditNote dao = this.mockedInjector.getInstance(DAOFRCreditNote.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(uidEntity))).thenReturn(entity);

        Assertions.assertThrows(ExportServiceException.class, () -> this.extractor.extract(uidEntity));
    }

    @Test public void testPdfCreationFromBundle()
        throws ExportServiceException, DocumentIssuingException, IOException, SeriesUniqueCodeNotFilled,
        DocumentSeriesDoesNotExistException {

        StringID<GenericInvoice> uidEntity = StringID.fromValue("12345");
        final String businessUID = UUID.randomUUID().toString();
        this.createSeries(businessUID);
        FRInvoiceEntity invoice = this.getNewIssuedInvoice(businessUID);
        FRCreditNoteEntity entity = this.generateFRCreditNote(invoice);
        DAOFRCreditNote dao = this.mockedInjector.getInstance(DAOFRCreditNote.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(uidEntity))).thenReturn(entity);
        DAOFRInvoice daoInvoice = this.mockedInjector.getInstance(DAOFRInvoice.class);
        Mockito.when(daoInvoice.get(ArgumentMatchers.eq(invoice.getUID()))).thenReturn(invoice);

        final File result = File.createTempFile("Result", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        InputStream xsl = Files.newInputStream(Paths.get(TestFRCreditNotePDFTransformer.XSL_PATH));
        FRCreditNoteTemplateBundle bundle = new FRCreditNoteTemplateBundle(TestFRCreditNotePDFTransformer.LOGO_PATH,
            xsl);
        FRCreditNotePDFFOPTransformer transformerBundle = new FRCreditNotePDFFOPTransformer(bundle);

        FRCreditNoteData entityData = this.extractor.extract(uidEntity);
        transformerBundle.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    private FRCreditNoteEntity generateFRCreditNote(FRInvoiceEntity reference)
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException {

        Services services = new Services(FRAbstractTest.injector);

        FRIssuingParams params = this.getParameters("AC", "3000");
        this.createSeries(reference.getBusiness().getUID().toString(), "AC");

        FRCreditNoteEntity creditNote = null;
        creditNote = (FRCreditNoteEntity) services.issueDocument(
            new FRCreditNoteTestUtil(FRAbstractTest.injector).getCreditNoteBuilder(reference), params);

        creditNote.setCustomer((CustomerEntity) reference.getCustomer());
        creditNote.setBusiness((BusinessEntity) reference.getBusiness());
        creditNote.setCreditOrDebit(CreditOrDebit.DEBIT);

        return creditNote;
    }
}
