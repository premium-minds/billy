/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.export;

import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
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
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.portugal.PortugalDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.export.PTCreditNoteData;
import com.premiumminds.billy.portugal.services.export.PTCreditNoteDataExtractor;
import com.premiumminds.billy.portugal.services.export.exceptions.RequiredFieldNotFoundException;
import com.premiumminds.billy.portugal.services.export.pdf.creditnote.PTCreditNotePDFFOPTransformer;
import com.premiumminds.billy.portugal.services.export.pdf.creditnote.PTCreditNoteTemplateBundle;
import com.premiumminds.billy.portugal.services.export.qrcode.QRCodeStringGenerator;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PTMockDependencyModule;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTCreditNoteTestUtil;
import com.premiumminds.billy.portugal.util.Services;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPTCreditNotePDFTransformer extends PTPersistencyAbstractTest {

    public static final String XSL_PATH = "src/main/resources/templates/pt_creditnote.xsl";
    public static final String LOGO_PATH = "src/main/resources/logoBig.png";

    public static final String SOFTWARE_CERTIFICATE_NUMBER = "4321";
    private Injector mockedInjector;
    private PTCreditNotePDFFOPTransformer transformer;
    private PTCreditNoteDataExtractor extractor;

    @BeforeEach public void setUp() throws FileNotFoundException {

        this.mockedInjector = Guice.createInjector(
            Modules.override(new PortugalDependencyModule()).with(new PTMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestPTCreditNotePDFTransformer.XSL_PATH);

        this.transformer = new PTCreditNotePDFFOPTransformer(TestPTCreditNotePDFTransformer.LOGO_PATH, xsl,
            TestPTCreditNotePDFTransformer.SOFTWARE_CERTIFICATE_NUMBER);
        this.extractor = this.mockedInjector.getInstance(PTCreditNoteDataExtractor.class);
    }

    @Test public void testPdfCreation()
        throws ExportServiceException, DocumentIssuingException, IOException, SeriesUniqueCodeNotFilled,
        DocumentSeriesDoesNotExistException {

        UID uidEntity = UID.fromString("12345");
        final String uid = new UID().toString();
        this.createSeries(uid);
        PTInvoiceEntity invoice = this.getNewIssuedInvoice(uid);
        PTCreditNoteEntity creditNote = this.generatePTCreditNote(PaymentMechanism.CASH, invoice);
        DAOPTCreditNote dao = this.mockedInjector.getInstance(DAOPTCreditNote.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(uidEntity))).thenReturn(creditNote);
        DAOPTInvoice daoPTInvoice = this.mockedInjector.getInstance(DAOPTInvoice.class);
        Mockito.when(daoPTInvoice.get(invoice.getUID())).thenReturn(invoice);

        final File result = File.createTempFile("Result", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        PTCreditNoteData entityData = this.extractor.extract(uidEntity);
        this.transformer.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    @Test public void testNonExistentEntity() {

        UID uidEntity = UID.fromString("12345");
        Assertions.assertThrows(ExportServiceException.class, () -> this.extractor.extract(uidEntity));
    }

    @Test public void testPdfCreationFromBundle()
        throws ExportServiceException, IOException, DocumentIssuingException, SeriesUniqueCodeNotFilled,
        DocumentSeriesDoesNotExistException {
        UID uidEntity = UID.fromString("12345");
        final String uid = new UID().toString();
        this.createSeries(uid);
        PTInvoiceEntity invoice = this.getNewIssuedInvoice(uid);
        PTCreditNoteEntity creditNote = this.generatePTCreditNote(PaymentMechanism.CASH, invoice);
        DAOPTCreditNote dao = this.mockedInjector.getInstance(DAOPTCreditNote.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(uidEntity))).thenReturn(creditNote);
        DAOPTInvoice daoPTInvoice = this.mockedInjector.getInstance(DAOPTInvoice.class);
        Mockito.when(daoPTInvoice.get(invoice.getUID())).thenReturn(invoice);

        final File result = File.createTempFile("Result", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        InputStream xsl = Files.newInputStream(Paths.get(TestPTCreditNotePDFTransformer.XSL_PATH));
        PTCreditNoteTemplateBundle bundle = new PTCreditNoteTemplateBundle(TestPTCreditNotePDFTransformer.LOGO_PATH,
            xsl, TestPTCreditNotePDFTransformer.SOFTWARE_CERTIFICATE_NUMBER);
        PTCreditNotePDFFOPTransformer transformerBundle = new PTCreditNotePDFFOPTransformer(bundle);

        PTCreditNoteData entityData = this.extractor.extract(uidEntity);
        transformerBundle.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    private PTCreditNoteEntity generatePTCreditNote(PaymentMechanism paymentMechanism, PTInvoiceEntity reference)
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException {

        Services services = new Services(PTAbstractTest.injector);

        PTIssuingParams params = this.getParameters("AC", "3000", "1");

        this.createSeries(reference, "AC");

        PTCreditNoteEntity creditNote = null;
        creditNote = (PTCreditNoteEntity) services.issueDocument(
            new PTCreditNoteTestUtil(PTAbstractTest.injector).getCreditNoteBuilder(reference), params);

        creditNote.setCustomer((CustomerEntity) reference.getCustomer());
        creditNote.setBusiness((BusinessEntity) reference.getBusiness());
        creditNote.setCreditOrDebit(CreditOrDebit.DEBIT);
        creditNote.setHash(
            "mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET" +
                "/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");
        mockQRCodeDataGenerator(creditNote);
        creditNote.setATCUD("12345");
        return creditNote;
    }

    private void mockQRCodeDataGenerator(final PTCreditNoteEntity creditNote) {
        QRCodeStringGenerator qrCodeStringGenerator = this.mockedInjector.getInstance(QRCodeStringGenerator.class);
        try {
            Mockito.when(qrCodeStringGenerator.generateQRCodeData(creditNote)).thenReturn(
                "A:123456789*B:123456789*C:PT*D:FT*E:N*F:20201029*G:FT DEFAULT/1*H:ATCUD12345-1*I1:PT*I7:0.37*I8:0" +
                    ".08*N:0.08*O:0.45*Q:nVyy*R:1");
        } catch (RequiredFieldNotFoundException e) {
            Assertions.fail();
        }
    }
}
