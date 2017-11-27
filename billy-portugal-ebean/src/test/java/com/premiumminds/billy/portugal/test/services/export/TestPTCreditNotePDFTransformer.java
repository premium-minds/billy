/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.export;

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
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.portugal.PortugalDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.export.PTCreditNoteData;
import com.premiumminds.billy.portugal.services.export.PTCreditNoteDataExtractor;
import com.premiumminds.billy.portugal.services.export.pdf.creditnote.PTCreditNotePDFFOPTransformer;
import com.premiumminds.billy.portugal.services.export.pdf.creditnote.PTCreditNoteTemplateBundle;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PTMockDependencyModule;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTCreditNoteTestUtil;
import com.premiumminds.billy.portugal.util.Services;

public class TestPTCreditNotePDFTransformer extends PTPersistencyAbstractTest {

    /*public static final int NUM_ENTRIES = 10;
    public static final String XSL_PATH = "src/main/resources/templates/pt_creditnote.xsl";
    public static final String LOGO_PATH = "src/main/resources/logoBig.png";

    public static final String SOFTWARE_CERTIFICATE_NUMBER = "4321";
    private Injector mockedInjector;
    private PTCreditNotePDFFOPTransformer transformer;
    private PTCreditNoteDataExtractor extractor;

    @Before
    public void setUp() throws FileNotFoundException {

        this.mockedInjector = Guice
                .createInjector(Modules.override(new PortugalDependencyModule()).with(new PTMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestPTCreditNotePDFTransformer.XSL_PATH);

        this.transformer = new PTCreditNotePDFFOPTransformer(TestPTCreditNotePDFTransformer.LOGO_PATH, xsl,
                TestPTCreditNotePDFTransformer.SOFTWARE_CERTIFICATE_NUMBER);
        this.extractor = this.mockedInjector.getInstance(PTCreditNoteDataExtractor.class);
    }

    @Test
    public void testPDFcreation() throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException,
            DocumentIssuingException, IOException {

        UID uidEntity = UID.fromString("12345");
        PTCreditNoteEntity invoice = this.generatePTCreditNote(PaymentMechanism.CASH, this.getNewIssuedInvoice());
        DAOPTCreditNote dao = this.mockedInjector.getInstance(DAOPTCreditNote.class);
        Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(invoice);

        OutputStream os = new FileOutputStream(File.createTempFile("Result", ".pdf"));

        PTCreditNoteData entityData = this.extractor.extract(uidEntity);
        this.transformer.transform(entityData, os);
    }

    @Test(expected = ExportServiceException.class)
    public void testNonExistentEntity()
            throws DocumentIssuingException, FileNotFoundException, IOException, ExportServiceException {

        UID uidEntity = UID.fromString("12345");
        this.extractor.extract(uidEntity);
    }

    @Test
    public void testPDFCreationFromBundle() throws ExportServiceException, IOException, DocumentIssuingException {
        UID uidEntity = UID.fromString("12345");
        PTCreditNoteEntity invoice = this.generatePTCreditNote(PaymentMechanism.CASH, this.getNewIssuedInvoice());
        DAOPTCreditNote dao = this.mockedInjector.getInstance(DAOPTCreditNote.class);
        Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(invoice);

        OutputStream os = new FileOutputStream(File.createTempFile("Result", ".pdf"));

        InputStream xsl = new FileInputStream(TestPTCreditNotePDFTransformer.XSL_PATH);
        PTCreditNoteTemplateBundle bundle = new PTCreditNoteTemplateBundle(TestPTCreditNotePDFTransformer.LOGO_PATH,
                xsl, TestPTCreditNotePDFTransformer.SOFTWARE_CERTIFICATE_NUMBER);
        PTCreditNotePDFFOPTransformer transformerBundle = new PTCreditNotePDFFOPTransformer(bundle);

        PTCreditNoteData entityData = this.extractor.extract(uidEntity);
        transformerBundle.transform(entityData, os);
    }

    private PTCreditNoteEntity generatePTCreditNote(PaymentMechanism paymentMechanism, PTInvoiceEntity reference)
            throws DocumentIssuingException {

        Services services = PTAbstractTest.injector.getInstance(Services.class);

        PTIssuingParams params = this.getParameters("AC", "3000", "1");

        PTCreditNoteEntity creditNote = null;
        creditNote = (PTCreditNoteEntity) services.issueDocument(
                new PTCreditNoteTestUtil(PTAbstractTest.injector).getCreditNoteBuilder(reference), params);

        creditNote.setCustomer((CustomerEntity) reference.getCustomer());
        creditNote.setBusiness((BusinessEntity) reference.getBusiness());
        creditNote.setCreditOrDebit(CreditOrDebit.CREDIT);
        creditNote.setHash(
                "mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");

        return creditNote;
    }*/
}
