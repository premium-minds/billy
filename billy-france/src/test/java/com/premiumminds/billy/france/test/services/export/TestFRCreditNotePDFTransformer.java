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
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.util.PaymentMechanism;
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
import com.premiumminds.billy.france.util.Services;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.FRMockDependencyModule;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.util.FRCreditNoteTestUtil;

public class TestFRCreditNotePDFTransformer extends FRPersistencyAbstractTest {

    public static final int NUM_ENTRIES = 10;
    public static final String XSL_PATH = "src/main/resources/templates/es_creditnote.xsl";
    public static final String LOGO_PATH = "src/main/resources/logoBig.png";
    private Injector mockedInjector;
    private FRCreditNotePDFFOPTransformer transformer;
    private FRCreditNoteDataExtractor extractor;

    @Before
    public void setUp() throws FileNotFoundException {

        this.mockedInjector =
                Guice.createInjector(Modules.override(new FranceDependencyModule()).with(new FRMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestFRCreditNotePDFTransformer.XSL_PATH);

        this.transformer = new FRCreditNotePDFFOPTransformer(TestFRCreditNotePDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(FRCreditNoteDataExtractor.class);
    }

    @Test
    public void testPDFcreation() throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException,
            DocumentIssuingException, IOException {

        UID uidEntity = UID.fromString("12345");
        FRInvoiceEntity invoice = this.getNewIssuedInvoice();
        FRCreditNoteEntity entity = this.generateFRCreditNote(PaymentMechanism.CASH, invoice);
        DAOFRCreditNote dao = this.mockedInjector.getInstance(DAOFRCreditNote.class);
        Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(entity);
        DAOFRInvoice daoInvoice = this.mockedInjector.getInstance(DAOFRInvoice.class);
        Mockito.when(daoInvoice.get(Matchers.eq(invoice.getUID()))).thenReturn(invoice);

        OutputStream os = new FileOutputStream(File.createTempFile("Result", ".pdf"));

        FRCreditNoteData entityData = this.extractor.extract(uidEntity);
        this.transformer.transform(entityData, os);
    }

    @Test(expected = ExportServiceException.class)
    public void testNonExistentEntity() throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException,
            DocumentIssuingException, IOException {

        UID uidEntity = UID.fromString("12345");

        this.extractor.extract(uidEntity);
    }

    @Test(expected = ExportServiceException.class)
    public void testNonExistentInvoice() throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException,
            DocumentIssuingException, IOException {

        UID uidEntity = UID.fromString("12345");
        FRInvoiceEntity invoice = this.getNewIssuedInvoice();
        FRCreditNoteEntity entity = this.generateFRCreditNote(PaymentMechanism.CASH, invoice);
        DAOFRCreditNote dao = this.mockedInjector.getInstance(DAOFRCreditNote.class);
        Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(entity);

        this.extractor.extract(uidEntity);
    }

    @Test
    public void testPDFCreationFromBundle() throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException,
            DocumentIssuingException, IOException {

        UID uidEntity = UID.fromString("12345");
        FRInvoiceEntity invoice = this.getNewIssuedInvoice();
        FRCreditNoteEntity entity = this.generateFRCreditNote(PaymentMechanism.CASH, invoice);
        DAOFRCreditNote dao = this.mockedInjector.getInstance(DAOFRCreditNote.class);
        Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(entity);
        DAOFRInvoice daoInvoice = this.mockedInjector.getInstance(DAOFRInvoice.class);
        Mockito.when(daoInvoice.get(Matchers.eq(invoice.getUID()))).thenReturn(invoice);

        OutputStream os = new FileOutputStream(File.createTempFile("Result", ".pdf"));

        InputStream xsl = new FileInputStream(TestFRCreditNotePDFTransformer.XSL_PATH);
        FRCreditNoteTemplateBundle bundle =
                new FRCreditNoteTemplateBundle(TestFRCreditNotePDFTransformer.LOGO_PATH, xsl);
        FRCreditNotePDFFOPTransformer transformerBundle = new FRCreditNotePDFFOPTransformer(bundle);

        FRCreditNoteData entityData = this.extractor.extract(uidEntity);
        transformerBundle.transform(entityData, os);
    }

    private FRCreditNoteEntity generateFRCreditNote(PaymentMechanism paymentMechanism, FRInvoiceEntity reference)
            throws DocumentIssuingException {

        Services services = new Services(FRAbstractTest.injector);

        FRIssuingParams params = this.getParameters("AC", "3000");

        FRCreditNoteEntity creditNote = null;
        creditNote = (FRCreditNoteEntity) services.issueDocument(
                new FRCreditNoteTestUtil(FRAbstractTest.injector).getCreditNoteBuilder(reference), params);

        creditNote.setCustomer((CustomerEntity) reference.getCustomer());
        creditNote.setBusiness((BusinessEntity) reference.getBusiness());
        creditNote.setCreditOrDebit(CreditOrDebit.CREDIT);

        return creditNote;
    }
}
