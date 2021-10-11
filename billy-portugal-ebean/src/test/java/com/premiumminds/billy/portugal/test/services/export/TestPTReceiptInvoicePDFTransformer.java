/*
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.portugal.PortugalDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.services.export.PTReceiptInvoiceData;
import com.premiumminds.billy.portugal.services.export.PTReceiptInvoiceDataExtractor;
import com.premiumminds.billy.portugal.services.export.pdf.receiptinvoice.PTReceiptInvoicePDFFOPTransformer;
import com.premiumminds.billy.portugal.services.export.pdf.receiptinvoice.PTReceiptInvoiceTemplateBundle;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PTMockDependencyModule;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTReceiptInvoiceTestUtil;

public class TestPTReceiptInvoicePDFTransformer extends PTPersistencyAbstractTest {

    /*public static final int NUM_ENTRIES = 10;
    public static final String XSL_PATH = "src/main/resources/templates/pt_receiptinvoice.xsl";
    public static final String LOGO_PATH = "src/main/resources/logoBig.png";
    public static final String SOFTWARE_CERTIFICATE_NUMBER = "4321";

    private Injector mockedInjector;
    private PTReceiptInvoicePDFFOPTransformer transformer;
    private PTReceiptInvoiceDataExtractor extractor;

    @BeforeEach
    public void setUp() throws FileNotFoundException {

        this.mockedInjector = Guice
                .createInjector(Modules.override(new PortugalDependencyModule()).with(new PTMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestPTReceiptInvoicePDFTransformer.XSL_PATH);

        this.transformer = new PTReceiptInvoicePDFFOPTransformer(TestPTReceiptInvoicePDFTransformer.LOGO_PATH, xsl,
                TestPTReceiptInvoicePDFTransformer.SOFTWARE_CERTIFICATE_NUMBER);
        this.extractor = this.mockedInjector.getInstance(PTReceiptInvoiceDataExtractor.class);
    }

    @Test
    public void testPDFcreation()
            throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException, IOException {

        UID uidEntity = UID.fromString("12345");
        PTReceiptInvoiceEntity invoice = this.generatePTReceiptInvoice(PaymentMechanism.CASH);
        DAOPTReceiptInvoice dao = this.mockedInjector.getInstance(DAOPTReceiptInvoice.class);
        Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(invoice);

        OutputStream os = new FileOutputStream(File.createTempFile("Result", ".pdf"));

        PTReceiptInvoiceData entityData = this.extractor.extract(uidEntity);
        this.transformer.transform(entityData, os);
    }

    @Test
    public void testNonExistentEntity()
            throws DocumentIssuingException, FileNotFoundException, IOException, ExportServiceException {

        UID uidEntity = UID.fromString("12345");
        Assertions.assertThrows(ExportServiceException.class, () -> this.extractor.extract(uidEntity));
    }

    @Test
    public void testPDFCreationFromBundle() throws ExportServiceException, IOException, DocumentIssuingException {
        UID uidEntity = UID.fromString("12345");
        PTReceiptInvoiceEntity invoice = this.generatePTReceiptInvoice(PaymentMechanism.CASH);
        DAOPTReceiptInvoice dao = this.mockedInjector.getInstance(DAOPTReceiptInvoice.class);
        Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(invoice);

        OutputStream os = new FileOutputStream(File.createTempFile("Result", ".pdf"));

        InputStream xsl = new FileInputStream(TestPTReceiptInvoicePDFTransformer.XSL_PATH);
        PTReceiptInvoiceTemplateBundle bundle =
                new PTReceiptInvoiceTemplateBundle(TestPTReceiptInvoicePDFTransformer.LOGO_PATH, xsl,
                        TestPTReceiptInvoicePDFTransformer.SOFTWARE_CERTIFICATE_NUMBER);
        PTReceiptInvoicePDFFOPTransformer transformerBundle = new PTReceiptInvoicePDFFOPTransformer(bundle);

        PTReceiptInvoiceData entityData = this.extractor.extract(uidEntity);
        transformerBundle.transform(entityData, os);
    }

    private PTReceiptInvoiceEntity generatePTReceiptInvoice(PaymentMechanism paymentMechanism) {

        PTReceiptInvoiceEntity receiptInvoice =
                new PTReceiptInvoiceTestUtil(PTAbstractTest.injector).getReceiptInvoiceEntity();
        receiptInvoice.setHash(
                "mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");

        return receiptInvoice;
    }*/
}
