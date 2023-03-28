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
import com.premiumminds.billy.andorra.persistence.entities.ADSimpleInvoiceEntity;
import com.premiumminds.billy.andorra.services.export.ADSimpleInvoiceData;
import com.premiumminds.billy.andorra.test.util.ADSimpleInvoiceTestUtil;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSimpleInvoice;
import com.premiumminds.billy.andorra.services.export.ADSimpleInvoiceDataExtractor;
import com.premiumminds.billy.andorra.services.export.pdf.simpleinvoice.ADSimpleInvoiceTemplateBundle;
import com.premiumminds.billy.andorra.services.export.pdf.simpleinvoice.impl.ADSimpleInvoicePDFFOPTransformer;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.ADMockDependencyModule;
import com.premiumminds.billy.andorra.test.ADPersistencyAbstractTest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class TestADSimpleInvoicePDFTransformer extends ADPersistencyAbstractTest {

    public static final String XSL_PATH = "src/main/resources/templates/ad_simpleinvoice.xsl";
    public static final String LOGO_PATH = "src/main/resources/logoBig.png";

    private Injector mockedInjector;
    private ADSimpleInvoicePDFFOPTransformer transformer;
    private ADSimpleInvoiceDataExtractor extractor;

    @BeforeEach
    public void setUp() throws FileNotFoundException {

        this.mockedInjector =
                Guice.createInjector(Modules.override(new AndorraDependencyModule()).with(new ADMockDependencyModule()));

        InputStream xsl = new FileInputStream(TestADSimpleInvoicePDFTransformer.XSL_PATH);

        this.transformer = new ADSimpleInvoicePDFFOPTransformer(TestADSimpleInvoicePDFTransformer.LOGO_PATH, xsl);
        this.extractor = this.mockedInjector.getInstance(ADSimpleInvoiceDataExtractor.class);
    }

    @Test
    public void testPdfCreation()
            throws ExportServiceException, IOException {

        ADSimpleInvoiceEntity entity = this.generateESSimpleInvoice(PaymentMechanism.CASH);
        DAOADSimpleInvoice dao = this.mockedInjector.getInstance(DAOADSimpleInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("Result", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        ADSimpleInvoiceData entityData = this.extractor.extract(entity.getUID());
        this.transformer.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    @Test
    public void testNonExistentEntity() {
        StringID<GenericInvoice> uidEntity = StringID.fromValue("12345");
        Assertions.assertThrows(ExportServiceException.class, () -> this.extractor.extract(uidEntity));
    }

    @Test
    public void testPdfCreationFromBundle() throws ExportServiceException, IOException {
        ADSimpleInvoiceEntity entity = this.generateESSimpleInvoice(PaymentMechanism.CASH);
        DAOADSimpleInvoice dao = this.mockedInjector.getInstance(DAOADSimpleInvoice.class);
        Mockito.when(dao.get(ArgumentMatchers.eq(entity.getUID()))).thenReturn(entity);

        final File result = File.createTempFile("Result", ".pdf");
        OutputStream os = Files.newOutputStream(result.toPath());

        InputStream xsl = Files.newInputStream(Paths.get(TestADSimpleInvoicePDFTransformer.XSL_PATH));
        ADSimpleInvoiceTemplateBundle bundle =
                new ADSimpleInvoiceTemplateBundle(TestADSimpleInvoicePDFTransformer.LOGO_PATH, xsl);
        ADSimpleInvoicePDFFOPTransformer transformerBundle = new ADSimpleInvoicePDFFOPTransformer(bundle);

        ADSimpleInvoiceData entityData = this.extractor.extract(entity.getUID());
        transformerBundle.transform(entityData, os);

        try (PDDocument doc = PDDocument.load(result)) {
            assertEquals(1, doc.getNumberOfPages());
        }
    }

    private ADSimpleInvoiceEntity generateESSimpleInvoice(PaymentMechanism paymentMechanism) {

        ADSimpleInvoiceEntity simpleInvoice =
                new ADSimpleInvoiceTestUtil(ADAbstractTest.injector).getSimpleInvoiceEntity();

        return simpleInvoice;
    }
}
