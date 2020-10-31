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

import com.premiumminds.billy.portugal.services.export.exceptions.RequiredFieldNotFoundException;
import com.premiumminds.billy.portugal.services.export.qrcode.QRCodeDataGenerator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.portugal.PortugalDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.export.PTInvoiceData;
import com.premiumminds.billy.portugal.services.export.PTInvoiceDataExtractor;
import com.premiumminds.billy.portugal.services.export.pdf.invoice.PTInvoicePDFFOPTransformer;
import com.premiumminds.billy.portugal.services.export.pdf.invoice.PTInvoiceTemplateBundle;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PTMockDependencyModule;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;

public class TestPTInvoicePDFTransformer extends PTPersistencyAbstractTest {

    public static final int NUM_ENTRIES = 10;
    public static final String XSL_PATH = "src/main/resources/templates/pt_invoice.xsl";
    public static final String LOGO_PATH = "src/main/resources/logoBig.png";
    public static final String SOFTWARE_CERTIFICATE_NUMBER = "4321";

    Injector mockedInjector;
    PTInvoiceDataExtractor extractor;
    PTInvoicePDFFOPTransformer transformer;
    PTInvoiceTestUtil test;

    @Before
    public void setUp() throws FileNotFoundException {

        this.mockedInjector = Guice
                .createInjector(Modules.override(new PortugalDependencyModule()).with(new PTMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestPTInvoicePDFTransformer.XSL_PATH);

        this.transformer = new PTInvoicePDFFOPTransformer(TestPTInvoicePDFTransformer.LOGO_PATH, xsl,
                TestPTInvoicePDFTransformer.SOFTWARE_CERTIFICATE_NUMBER);
        this.extractor = this.mockedInjector.getInstance(PTInvoiceDataExtractor.class);
        this.test = new PTInvoiceTestUtil(PTAbstractTest.injector);
    }

    @Test
    public void testPDFcreation()
            throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException, IOException {

        UID uidEntity = UID.fromString("12345");
        PTInvoiceEntity invoice = this.generatePTInvoice();
        DAOPTInvoice dao = this.mockedInjector.getInstance(DAOPTInvoice.class);
        Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(invoice);

        OutputStream os = new FileOutputStream(File.createTempFile("ResultCreation", ".pdf"));

        PTInvoiceData entityData = this.extractor.extract(uidEntity);
        this.transformer.transform(entityData, os);
    }

    @Test(expected = ExportServiceException.class)
    public void testNonExistentEntity()
            throws DocumentIssuingException, FileNotFoundException, IOException, ExportServiceException {

        UID uidEntity = UID.fromString("12345");
        this.extractor.extract(uidEntity);
    }

    @Test
    public void testDiferentRegion()
            throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException, IOException {

        UID uidEntity = UID.fromString("12345");
        PTInvoiceEntity invoice = this.generateOtherregionsInvoice();
        DAOPTInvoice dao = this.mockedInjector.getInstance(DAOPTInvoice.class);
        Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(invoice);

        OutputStream os = new FileOutputStream(File.createTempFile("ResultDiferentRegions", ".pdf"));

        PTInvoiceData entityData = this.extractor.extract(uidEntity);
        this.transformer.transform(entityData, os);
    }

    @Test
    public void testManyEntries()
            throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException, IOException {

        UID uidEntity = UID.fromString("12345");
        PTInvoiceEntity invoice = this.generateManyEntriesInvoice();
        DAOPTInvoice dao = this.mockedInjector.getInstance(DAOPTInvoice.class);
        Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(invoice);

        OutputStream os = new FileOutputStream(File.createTempFile("ResultManyEntries", ".pdf"));

        PTInvoiceData entityData = this.extractor.extract(uidEntity);
        this.transformer.transform(entityData, os);
    }

    @Test
    public void testManyEntriesWithDifrentRegions()
            throws NoSuchAlgorithmException, ExportServiceException, URISyntaxException, IOException {

        UID uidEntity = UID.fromString("12345");
        PTInvoiceEntity invoice = this.generateManyEntriesWithDiferentRegionsInvoice();
        DAOPTInvoice dao = this.mockedInjector.getInstance(DAOPTInvoice.class);
        Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(invoice);

        OutputStream os = new FileOutputStream(File.createTempFile("ResultManyEntriesWithDiferentRegions", ".pdf"));

        PTInvoiceData entityData = this.extractor.extract(uidEntity);
        this.transformer.transform(entityData, os);
    }

    @Test
    public void testPDFCreationFromBundle() throws ExportServiceException, IOException {
        UID uidEntity = UID.fromString("12345");
        PTInvoiceEntity invoice = this.generatePTInvoice();
        DAOPTInvoice dao = this.mockedInjector.getInstance(DAOPTInvoice.class);
        Mockito.when(dao.get(Matchers.eq(uidEntity))).thenReturn(invoice);

        OutputStream os = new FileOutputStream(File.createTempFile("ResultCreation", ".pdf"));

        InputStream xsl = new FileInputStream(TestPTInvoicePDFTransformer.XSL_PATH);
        PTInvoiceTemplateBundle bundle = new PTInvoiceTemplateBundle(TestPTInvoicePDFTransformer.LOGO_PATH, xsl,
                TestPTInvoicePDFTransformer.SOFTWARE_CERTIFICATE_NUMBER);
        PTInvoicePDFFOPTransformer transformerBundle = new PTInvoicePDFFOPTransformer(bundle);

        PTInvoiceData entityData = this.extractor.extract(uidEntity);
        transformerBundle.transform(entityData, os);
    }

    private PTInvoiceEntity generatePTInvoice() {
        PTInvoiceEntity invoice = this.test.getInvoiceEntity();
        invoice.setHash(
                "mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");
        mockQRCodeDataGenerator(invoice);
        return invoice;
    }

    private PTInvoiceEntity generateManyEntriesInvoice() {
        PTInvoiceEntity invoice = this.test.getManyEntriesInvoice();
        invoice.setHash(
                "mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");
        mockQRCodeDataGenerator(invoice);
        return invoice;
    }

    private PTInvoiceEntity generateOtherregionsInvoice() {
        PTInvoiceEntity invoice = this.test.getDiferentRegionsInvoice();
        invoice.setHash(
                "mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");
        mockQRCodeDataGenerator(invoice);
        return invoice;
    }

    private PTInvoiceEntity generateManyEntriesWithDiferentRegionsInvoice() {
        PTInvoiceEntity invoice = this.test.getManyEntriesWithDiferentRegionsInvoice();
        invoice.setHash(
                "mYJEv4iGwLcnQbRD7dPs2uD1mX08XjXIKcGg3GEHmwMhmmGYusffIJjTdSITLX+uujTwzqmL/U5nvt6S9s8ijN3LwkJXsiEpt099e1MET/J8y3+Y1bN+K+YPJQiVmlQS0fXETsOPo8SwUZdBALt0vTo1VhUZKejACcjEYJ9G6nI=");
        mockQRCodeDataGenerator(invoice);
        return invoice;
    }

    private void mockQRCodeDataGenerator(final PTInvoiceEntity invoice) {
        QRCodeDataGenerator qrCodeDataGenerator = this.mockedInjector.getInstance(QRCodeDataGenerator.class);
        try {
            Mockito.when(qrCodeDataGenerator.generateQRCodeData(invoice))
                   .thenReturn("A:123456789*B:123456789*C:PT*D:FT*E:N*F:20201029*G:FT DEFAULT/1*H:ATCUD12345-1*I1:PT*I7:0.37*I8:0.08*N:0.08*O:0.45*Q:nVyy*R:1");
        } catch (RequiredFieldNotFoundException e) {
            Assert.fail();
        }
    }
}
