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
package com.premiumminds.billy.portugal.its;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.persistence.entities.jpa.JPAInvoiceSeriesEntity;
import com.premiumminds.billy.portugal.BillyPortugal;
import com.premiumminds.billy.portugal.PortugalBootstrap;
import com.premiumminds.billy.portugal.PortugalDependencyModule;
import com.premiumminds.billy.portugal.PortugalPersistenceDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.entities.*;
import com.premiumminds.billy.portugal.services.export.saftpt.PTSAFTFileGenerator;
import com.premiumminds.billy.portugal.util.KeyGenerator;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Currency;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvoiceIT {

    public static final String PRIVATE_KEY_DIR = "/keys/private.pem";

    public static final Integer SOFTWARE_CERTIFICATION = 12;

    private Injector injector;

    @Test
    void test() throws Exception {

        injector = Guice.createInjector(new PortugalDependencyModule(),
                new PortugalPersistenceDependencyModule("BillyPortugalTestPersistenceUnit"));
        injector.getInstance(PortugalDependencyModule.Initializer.class);
        injector.getInstance(PortugalPersistenceDependencyModule.Initializer.class);
        PortugalBootstrap.execute(injector);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        BillyPortugal billyPortugal = new BillyPortugal(injector);
        PTIssuingParams invoiceParameters = getPtInvoiceIssuingParams();
        PTIssuingParams creditNoteParameters = getPtCreditNoteIssuingParams();

        PTApplication.Builder applicationBuilder = getPTApplicationBuilder(billyPortugal);
        PTApplication application = createPtApplication(billyPortugal, applicationBuilder);
        PTBusiness business = createPtBusiness(billyPortugal, applicationBuilder);
        PTCustomer customer = createPtCustomer(billyPortugal);

        createSeries(invoiceParameters.getInvoiceSeries(), business, "CCCC2345");
        createSeries(creditNoteParameters.getInvoiceSeries(), business, "CCCC2346");

        final PTTax flatTax = createFlatTax(billyPortugal);

        PTProduct product = createPtProduct(billyPortugal);
        PTProduct productExempt = createPtProductExempt(billyPortugal);
        PTProduct productFlat = createPtProductFlat(billyPortugal, flatTax);
        PTInvoice invoice = createPtInvoice(dateFormat, billyPortugal, business, invoiceParameters, customer, product, productExempt, productFlat);
        PTCreditNote creditNote = createPtCreditNote(dateFormat,
                billyPortugal,
                business,
                creditNoteParameters,
                customer,
                product,
                invoice);

        final DAOPTInvoice daoInvoice = injector.getInstance(DAOPTInvoice.class);
        final DAOPTCreditNote daoCreditNote = injector.getInstance(DAOPTCreditNote.class);
        assertTrue(daoInvoice.exists(invoice.getUID()));
        assertTrue(daoCreditNote.exists(creditNote.getUID()));

        Date startDate = dateFormat.parse("01-01-2013");
        Date endDate = dateFormat.parse("01-01-2014");

        try (InputStream inputStream = billyPortugal.saft().export(application.getUID(),
                business.getUID(),
                startDate,
                endDate,
                PTSAFTFileGenerator.SAFTVersion.CURRENT, true)) {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(inputStream);

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();

            assertEquals("2", xpath.evaluate("//SourceDocuments/SalesInvoices/NumberOfEntries", document, XPathConstants.STRING));
            assertEquals("813.01", xpath.evaluate("//SourceDocuments/SalesInvoices/TotalDebit", document, XPathConstants.STRING));
            assertEquals("2757.51", xpath.evaluate("//SourceDocuments/SalesInvoices/TotalCredit", document, XPathConstants.STRING));
        }
    }

    private void createSeries(String series, PTBusiness business, String uniqueCode) {
        InvoiceSeriesEntity entity = new JPAInvoiceSeriesEntity();
        entity.setBusiness(business);
        entity.setSeries(series);
        entity.setSeriesUniqueCode(uniqueCode);
        DAOInvoiceSeries daoInvoiceSeries = injector.getInstance(DAOInvoiceSeries.class);
        daoInvoiceSeries.create(entity);
    }


    private PTTax createFlatTax(BillyPortugal billyPortugal) {
        final PTTax.Builder taxBuilder = this.injector.getInstance(PTTax.Builder.class);
        taxBuilder.setTaxRate(Tax.TaxRateType.FLAT, new BigDecimal("3.14"))
                .setContextUID(billyPortugal.contexts().continent().allContinentRegions().getUID())
                .setCode("code1")
                .setDescription("description 1")
                .setValidFrom(new Date(0))
                .setCurrency(Currency.getInstance("EUR"))
                .setValue(new BigDecimal("5.55"));

        return billyPortugal.taxes().persistence().create(taxBuilder);
    }

    private PTApplication.Builder getPTApplicationBuilder(BillyPortugal billyPortugal) throws MalformedURLException {
        PTContact.Builder contactBuilder = billyPortugal.contacts().builder();
        contactBuilder.setName("Developer 1")
                .setTelephone("2000000001");

        PTApplication.Builder applicationBuilder = billyPortugal.applications().builder();
        applicationBuilder.setDeveloperCompanyName("Developer Company Name")
                .setDeveloperCompanyTaxIdentifier("1000000001", "PT")
                .setSoftwareCertificationNumber(SOFTWARE_CERTIFICATION)
                .setName("Billy")
                .setVersion("1.0")
                .setApplicationKeysPath(new URL("http://www.keys.path"))
                .addContact(contactBuilder)
                .setMainContact(contactBuilder);
        return applicationBuilder;
    }

    private PTApplication createPtApplication(BillyPortugal billyPortugal, PTApplication.Builder applicationBuilder) {
        return billyPortugal.applications().persistence().create(applicationBuilder);
    }

    private PTBusiness createPtBusiness(BillyPortugal billyPortugal, PTApplication.Builder applicationBuilder) {
        PTContact.Builder contactBuilder = billyPortugal.contacts().builder();
        contactBuilder.setName("CEO 1")
                .setTelephone("200000002");

        PTAddress.Builder addressBuilder = billyPortugal.addresses().builder();
        addressBuilder.setStreetName("Street name 2")
                .setNumber("2")
                .setPostalCode("1000-100")
                .setCity("Lisbon")
                .setISOCountry("PT")
                .setDetails("Av. 5 de Outubro Nº 2 1000-100 Lisboa");

        PTBusiness.Builder businessBuilder = billyPortugal.businesses().builder();
        businessBuilder.addApplication(applicationBuilder)
                .addContact(contactBuilder, true)
                .setMainContactUID(contactBuilder.build().getUID())
                .setName("Business 1")
                .setCommercialName("Business, INC")
                .setFinancialID("500003564", "PT")
                .setAddress(addressBuilder)
                .setBillingAddress(addressBuilder)
                .setTimezone(ZoneId.of("Europe/Lisbon"));

        return billyPortugal.businesses().persistence().create(businessBuilder);
    }

    private PTInvoice createPtInvoice(SimpleDateFormat dateFormat,
                                      BillyPortugal billyPortugal,
                                      PTBusiness business,
                                      PTIssuingParams invoiceParameters,
                                      PTCustomer customer,
                                      PTProduct product,
                                      PTProduct productExempt,
                                      PTProduct productTax)
        throws ParseException, DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        PTInvoice.Builder invoiceBuilder = billyPortugal.invoices().builder();

        Date invoiceDate = dateFormat.parse("01-03-2013");
        invoiceBuilder.setSelfBilled(false)
                .setCancelled(false)
                .setBilled(false)
                .setDate(invoiceDate)
                .setSourceId("User 1")
                .setSourceBilling(PTGenericInvoice.SourceBilling.P)
                .setBusinessUID(business.getUID())
                .setCustomerUID(customer.getUID());

        PTInvoiceEntry.Builder entryBuilder = billyPortugal.invoices().entryBuilder();
        entryBuilder
                .setCurrency(Currency.getInstance("EUR"))
                .setQuantity(new BigDecimal("10"))
                .setTaxPointDate(dateFormat.parse("01-02-2013"))
                .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                .setContextUID(billyPortugal.contexts().continent().allContinentRegions().getUID())
                .setProductUID(product.getUID())
                .setDescription(product.getDescription())
                .setUnitOfMeasure(product.getUnitOfMeasure());

        invoiceBuilder.addEntry(entryBuilder);

        entryBuilder = billyPortugal.invoices().entryBuilder();
        entryBuilder
                .setCurrency(Currency.getInstance("EUR"))
                .setQuantity(new BigDecimal("10.0"))
                .setTaxPointDate(dateFormat.parse("01-02-2013"))
                .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                .setContextUID(billyPortugal.contexts().continent().allContinentRegions().getUID())
                .setProductUID(productExempt.getUID())
                .setDescription(productExempt.getDescription())
                .setUnitOfMeasure(productExempt.getUnitOfMeasure())
                .setTaxExemptionCode("M99")
                .setTaxExemptionReason("reason 1");

        invoiceBuilder.addEntry(entryBuilder);

        entryBuilder = billyPortugal.invoices().entryBuilder();
        entryBuilder
                .setCurrency(Currency.getInstance("EUR"))
                .setQuantity(new BigDecimal("10.0"))
                .setTaxPointDate(dateFormat.parse("01-02-2013"))
                .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                .setContextUID(billyPortugal.contexts().continent().allContinentRegions().getUID())
                .setProductUID(productTax.getUID())
                .setDescription(productTax.getDescription())
                .setUnitOfMeasure(productTax.getUnitOfMeasure())
                .setTaxExemptionCode("M99")
                .setTaxExemptionReason("reason 1");

        invoiceBuilder.addEntry(entryBuilder);

        return billyPortugal.invoices().issue(invoiceBuilder, invoiceParameters);
    }

    private PTCreditNote createPtCreditNote(SimpleDateFormat dateFormat,
                                            BillyPortugal billyPortugal,
                                            PTBusiness business,
                                            PTIssuingParams invoiceParameters,
                                            PTCustomer customer,
                                            PTProduct product,
                                            PTInvoice invoice)
        throws ParseException, DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        PTCreditNote.Builder creditNoteBuilder = billyPortugal.creditNotes().builder();

        Date creditNoteDate = dateFormat.parse("01-03-2013");
        creditNoteBuilder.setSelfBilled(false)
                .setCancelled(false)
                .setBilled(false)
                .setDate(creditNoteDate)
                .setSourceId("User 2")
                .setSourceBilling(PTGenericInvoice.SourceBilling.P)
                .setBusinessUID(business.getUID())
                .setCustomerUID(customer.getUID());

        PTCreditNoteEntry.Builder entryBuilder = billyPortugal.creditNotes().entryBuilder();
        entryBuilder.setAmountType(GenericInvoiceEntryBuilder.AmountType.WITH_TAX)
                .setCurrency(Currency.getInstance("EUR"))
                .setQuantity(new BigDecimal("10"))
                .setTaxPointDate(dateFormat.parse("01-02-2013"))
                .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                .setContextUID(billyPortugal.contexts().continent().allContinentRegions().getUID())
                .setProductUID(product.getUID())
                .setDescription(product.getDescription())
                .setUnitOfMeasure(product.getUnitOfMeasure())
                .setReferenceUID(invoice.getUID())
                .setReason("some reason 1");

        creditNoteBuilder.addEntry(entryBuilder);

        return billyPortugal.creditNotes().issue(creditNoteBuilder, invoiceParameters);
    }

    private PTProduct createPtProduct(BillyPortugal billyPortugal) {
        PTProduct.Builder productBuilder = billyPortugal.products().builder();
        productBuilder.setDescription("product 1")
                .setNumberCode("1")
                .setProductCode("1")
                .setType(Product.ProductType.GOODS)
                .setUnitOfMeasure("kg")
                .setProductGroup("group 1")
                .addTaxUID(billyPortugal.taxes().continent().normal().getUID());

        return billyPortugal.products().persistence().create(productBuilder);
    }

    private PTProduct createPtProductExempt(BillyPortugal billyPortugal) {
        PTProduct.Builder productBuilder = billyPortugal.products().builder();
        productBuilder.setDescription("product 2")
                .setNumberCode("2")
                .setProductCode("2")
                .setType(Product.ProductType.GOODS)
                .setUnitOfMeasure("kg")
                .setProductGroup("group 1")
                .addTaxUID(billyPortugal.taxes().exempt().getUID());

        return billyPortugal.products().persistence().create(productBuilder);
    }

    private PTProduct createPtProductFlat(BillyPortugal billyPortugal, PTTax flatTax) {
        PTProduct.Builder productBuilder = billyPortugal.products().builder();
        productBuilder.setDescription("product 3")
                .setNumberCode("3")
                .setProductCode("3")
                .setType(Product.ProductType.GOODS)
                .setUnitOfMeasure("kg")
                .setProductGroup("group 1")
                .addTaxUID(flatTax.getUID());

        return billyPortugal.products().persistence().create(productBuilder);
    }

    private PTIssuingParams getPtInvoiceIssuingParams() {
        PTIssuingParams invoiceParameters = PTIssuingParams.Util.newInstance();

        KeyGenerator gen = new KeyGenerator(getClass().getResource(PRIVATE_KEY_DIR));

        invoiceParameters.setPrivateKey(gen.getPrivateKey());
        invoiceParameters.setPublicKey(gen.getPublicKey());
        invoiceParameters.setInvoiceSeries("A");
        invoiceParameters.setPrivateKeyVersion("1");
        return invoiceParameters;
    }

    private PTIssuingParams getPtCreditNoteIssuingParams() {
        PTIssuingParams invoiceParameters = PTIssuingParams.Util.newInstance();

        KeyGenerator gen = new KeyGenerator(getClass().getResource(PRIVATE_KEY_DIR));

        invoiceParameters.setPrivateKey(gen.getPrivateKey());
        invoiceParameters.setPublicKey(gen.getPublicKey());
        invoiceParameters.setInvoiceSeries("B");
        invoiceParameters.setPrivateKeyVersion("1");
        return invoiceParameters;
    }

    private PTCustomer createPtCustomer(BillyPortugal billyPortugal) {
        PTContact.Builder contactBuilder = billyPortugal.contacts().builder();
        contactBuilder.setName("Customer 1")
                .setTelephone("telephone 1");

        PTAddress.Builder addressBuilder = billyPortugal.addresses().builder();
        addressBuilder.setStreetName("Customer 1 street name 1")
                .setNumber("2")
                .setPostalCode("1000-100")
                .setCity("Lisbon")
                .setISOCountry("PT")
                .setDetails("Av. 5 de Outubro Nº 2 1000-100 Lisboa");

        PTCustomer.Builder customerBuilder = billyPortugal.customers().builder();
        customerBuilder.setName("Customer name 1")
                .setTaxRegistrationNumber("123456789", "PT")
                .addAddress(addressBuilder, true)
                .setBillingAddress(addressBuilder)
                .setShippingAddress(addressBuilder)
                .setHasSelfBillingAgreement(false)
                .addContact(contactBuilder);

        return billyPortugal.customers().persistence().create(customerBuilder);
    }
}
