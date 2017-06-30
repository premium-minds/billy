/**
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

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTCreditNoteIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.PTInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.PTReceiptInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.PTSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParamsImpl;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTCustomer;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice.CLIENTTYPE;
import com.premiumminds.billy.portugal.services.entities.PTSupplier;
import com.premiumminds.billy.portugal.services.export.exceptions.SAFTPTExportException;
import com.premiumminds.billy.portugal.services.export.saftpt.PTSAFTFileGenerator;
import com.premiumminds.billy.portugal.services.export.saftpt.PTSAFTFileGenerator.SAFTVersion;
import com.premiumminds.billy.portugal.services.persistence.PTCustomerPersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTSupplierPersistenceService;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTAddressTestUtil;
import com.premiumminds.billy.portugal.test.util.PTApplicationTestUtil;
import com.premiumminds.billy.portugal.test.util.PTBusinessTestUtil;
import com.premiumminds.billy.portugal.test.util.PTContactTestUtil;
import com.premiumminds.billy.portugal.test.util.PTCreditNoteTestUtil;
import com.premiumminds.billy.portugal.test.util.PTCustomerTestUtil;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;
import com.premiumminds.billy.portugal.test.util.PTReceiptInvoiceTestUtil;
import com.premiumminds.billy.portugal.test.util.PTSimpleInvoiceTestUtil;
import com.premiumminds.billy.portugal.test.util.PTSupplierTestUtil;
import com.premiumminds.billy.portugal.util.KeyGenerator;

public class SAFTExportTest extends PTPersistencyAbstractTest {

    private static final String SAFT_OUTPUT = System.getProperty("java.io.tmpdir") + "/";

    private DocumentIssuingService service;
    protected PTIssuingParams parameters;

    @Before
    public void setUp() {
        this.service = this.getInstance(DocumentIssuingService.class);
        this.service.addHandler(PTInvoiceEntity.class,
                PTAbstractTest.injector.getInstance(PTInvoiceIssuingHandler.class));
        this.service.addHandler(PTSimpleInvoiceEntity.class,
                PTAbstractTest.injector.getInstance(PTSimpleInvoiceIssuingHandler.class));
        this.service.addHandler(PTReceiptInvoiceEntity.class,
                PTAbstractTest.injector.getInstance(PTReceiptInvoiceIssuingHandler.class));
        this.service.addHandler(PTCreditNoteEntity.class,
                PTAbstractTest.injector.getInstance(PTCreditNoteIssuingHandler.class));

        KeyGenerator generator = new KeyGenerator(PTPersistencyAbstractTest.PRIVATE_KEY_DIR);

        this.parameters = new PTIssuingParamsImpl();
        this.parameters.setPrivateKey(generator.getPrivateKey());
        this.parameters.setPublicKey(generator.getPublicKey());
        this.parameters.setPrivateKeyVersion("1");
        this.parameters.setEACCode("31400");

    }

    @Test
    public void doTest() throws Exception {
        PTContactTestUtil contact = new PTContactTestUtil(PTAbstractTest.injector);
        PTAddressTestUtil address = new PTAddressTestUtil(PTAbstractTest.injector);
        PTApplicationTestUtil application = new PTApplicationTestUtil(PTAbstractTest.injector);
        PTBusinessTestUtil business = new PTBusinessTestUtil(PTAbstractTest.injector);
        PTCustomerTestUtil customer = new PTCustomerTestUtil(PTAbstractTest.injector);
        PTSupplierTestUtil supplier = new PTSupplierTestUtil(PTAbstractTest.injector);
        PTInvoiceTestUtil invoice = new PTInvoiceTestUtil(PTAbstractTest.injector);
        PTCreditNoteTestUtil creditNote = new PTCreditNoteTestUtil(PTAbstractTest.injector);
        PTSimpleInvoiceTestUtil simpleInvoice = new PTSimpleInvoiceTestUtil(PTAbstractTest.injector);
        PTReceiptInvoiceTestUtil receiptInvoice = new PTReceiptInvoiceTestUtil(PTAbstractTest.injector);

        PTCustomerPersistenceService customerPersistenceService = this.getInstance(PTCustomerPersistenceService.class);
        PTSupplierPersistenceService supplierPersistenceService = this.getInstance(PTSupplierPersistenceService.class);

        DAOPTRegionContext daoPTRegionContext = PTAbstractTest.injector.getInstance(DAOPTRegionContext.class);

        /* ADDRESSES */
        // PTAddress.Builder addressBuilder1 = address.getAddressBuilder(
        // "Av. Republica", "Nº 3 - 3º Esq.",
        // "Av. Republica Nº 3 - 3º Esq.", "", "Lisboa", "1700-232", "",
        // "PT");
        PTAddress.Builder addressBuilder2 = address.getAddressBuilder("Av. Liberdade", "Nº 4 - 5º Dir.",
                "Av. Liberdade, Nº 4 - 5º Dir.", "nr building", "Lisboa", "1500-123", "Lisboa", "PT");
        PTAddress.Builder addressBuilder3 = address.getAddressBuilder("Campo Grande", "Condomínio X",
                "Lote 20, Andar 3", "Building nr K", "Lisboa", "1000-253", "Lisboa", "PT");

        /* CONTACTS */
        PTContact.Builder contactBuilder = contact.getContactBuilder("My Business", "299999999", "999999999",
                "299999998", "mybusiness@email.me", "http://www.mybusiness.web");

        PTContact.Builder contactBuilder2 = contact.getContactBuilder("Zé", "299999991", "999999991", "299999992",
                "maildoze@email.me", "http://www.zenaweb.web");

        PTContact.Builder contactBuilder3 = contact.getContactBuilder("YourSupplier", "299999993", "999999993",
                "299999994", "maildoyourbusiness@email.me", "http://www.yourbusinessnaweb.web");

        /* APPLICATION */
        PTApplication.Builder applicationBuilder = application.getApplicationBuilder("APP", "1.0", "My Business",
                "500001758", "hhtp://www.app.mybusiness.web", 1, "http://here", contactBuilder);
        PTApplicationEntity applicationEntity = (PTApplicationEntity) applicationBuilder.build();

        /* BUSINESS */
        PTBusinessEntity businessEntity = business.getBusinessEntity();

        /* CUSTOMERS */
        DAOPTCustomer daoPTCustomer = PTAbstractTest.injector.getInstance(DAOPTCustomer.class);
        PTCustomer.Builder customerBuilder =
                customer.getCustomerBuilder("Zé", "213512351", false, addressBuilder2, contactBuilder2);
        customerPersistenceService.create(customerBuilder);

        /* SUPPLIERS */
        DAOPTSupplier daoPTSupplier = PTAbstractTest.injector.getInstance(DAOPTSupplier.class);

        PTSupplier.Builder supplierBuilder =
                supplier.getSupplierBuilder("YourSupplier", "500001758", false, addressBuilder3, contactBuilder3);
        supplierPersistenceService.create(supplierBuilder);

        /* TAXES */
        DAOPTTax daoPTTax = PTAbstractTest.injector.getInstance(DAOPTTax.class);

        /* PRODUCTS */
        DAOPTProduct daoPTProduct = PTAbstractTest.injector.getInstance(DAOPTProduct.class);

        // INVOICES
        DAOPTInvoice daoPTInvoice = PTAbstractTest.injector.getInstance(DAOPTInvoice.class);

        // INVOICE
        this.parameters.setInvoiceSeries("F");
        PTInvoiceEntity invoiceEntity = (PTInvoiceEntity) this.service
                .issue(invoice.getInvoiceBuilder(businessEntity, SourceBilling.P), this.parameters);

        // SIMPLE INVOICE
        DAOPTSimpleInvoice daoPTSimpleInvoice = PTAbstractTest.injector.getInstance(DAOPTSimpleInvoice.class);
        this.parameters.setInvoiceSeries("S");
        this.service.issue(simpleInvoice.getSimpleInvoiceBuilder(businessEntity, SourceBilling.P, CLIENTTYPE.CUSTOMER),
                this.parameters);

        // MANUAL INVOICE
        this.parameters.setInvoiceSeries("M");
        this.service.issue(invoice.getManualInvoiceBuilder(businessEntity, SourceBilling.M), this.parameters);

        // RECEIPT INVOICE
        DAOPTReceiptInvoice daoPTReceiptInvoice = PTAbstractTest.injector.getInstance(DAOPTReceiptInvoice.class);
        this.parameters.setInvoiceSeries("R");
        this.service.issue(receiptInvoice.getReceiptInvoiceBuilder(businessEntity, SourceBilling.P), this.parameters);

        // CREDIT NOTE
        DAOPTCreditNote daoPTCreditNote = PTAbstractTest.injector.getInstance(DAOPTCreditNote.class);
        this.parameters.setInvoiceSeries("C");
        this.service.issue(creditNote.getCreditNoteBuilder(invoiceEntity), this.parameters);

        this.exportSAFT(daoPTRegionContext, applicationEntity, businessEntity, daoPTCustomer, daoPTSupplier, daoPTTax,
                daoPTProduct, daoPTInvoice, daoPTSimpleInvoice, daoPTReceiptInvoice, daoPTCreditNote);
    }

    private void exportSAFT(DAOPTRegionContext daoPTRegionContext, PTApplicationEntity applicationEntity,
            PTBusinessEntity businessEntity, DAOPTCustomer daoPTCustomer, DAOPTSupplier daoPTSupplier,
            DAOPTTax daoPTTax, DAOPTProduct daoPTProduct, DAOPTInvoice daoPTInvoice,
            DAOPTSimpleInvoice daoPTSimpleInvoice, DAOPTReceiptInvoice daoPTReceiptInvoice,
            DAOPTCreditNote daoPTCreditNote) throws FileNotFoundException, SAFTPTExportException {

        PTSAFTFileGenerator generator = this.getInstance(PTSAFTFileGenerator.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2013, 1, 1);

        PrintStream stream = new PrintStream(SAFTExportTest.SAFT_OUTPUT + "SAFT_10201.xml");
        generator.generateSAFTFile(stream, businessEntity, applicationEntity, "1234", calendar.getTime(), new Date(),
                SAFTVersion.V10201);

        stream = new PrintStream(SAFTExportTest.SAFT_OUTPUT + "SAFT_10301.xml");
        generator.generateSAFTFile(stream, businessEntity, applicationEntity, "1234", calendar.getTime(), new Date(),
                SAFTVersion.V10301);
    }
}
