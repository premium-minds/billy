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
package com.premiumminds.billy.portugal.services.export.webservice;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Endpoint;

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
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.PortugalBootstrap;
import com.premiumminds.billy.portugal.PortugalDependencyModule;
import com.premiumminds.billy.portugal.PortugalPersistenceDependencyModule;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;
import com.premiumminds.billy.portugal.services.entities.PTCustomer;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTProduct;
import com.premiumminds.billy.portugal.services.entities.PTTax;
import com.premiumminds.billy.portugal.util.KeyGenerator;
import com.premiumminds.billy.portugal.ws.client.DeleteInvoiceResponse;
import com.premiumminds.billy.portugal.ws.client.InvoiceDataType;
import com.premiumminds.billy.portugal.ws.client.RegisterInvoiceResponse;
import com.premiumminds.billy.portugal.ws.client.ResponseType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ClientTest {

    public static final String PRIVATE_KEY_DIR = "/keys/private.pem";

    public static final Integer SOFTWARE_CERTIFICATION = 12;

    private Injector injector;
    private PTApplication application;
    private PTInvoice invoice;
    private PTCreditNote creditNote;

    private StubFatcorewsPort webService;

    private URL stubServerUrl;
    private Endpoint publish;

    @BeforeEach
    public void setUp() throws Exception {
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
        application = createPtApplication(billyPortugal, applicationBuilder);
        PTBusiness business = createPtBusiness(billyPortugal, applicationBuilder);
        PTCustomer customer = createPtCustomer(billyPortugal);

        createSeries(invoiceParameters.getInvoiceSeries(), business, "CCCC2345");
        createSeries(creditNoteParameters.getInvoiceSeries(), business, "CCCC2346");

        final PTTax flatTax = createFlatTax(billyPortugal);

        PTProduct productNormal = createPtProductNormal(billyPortugal);
        PTProduct productIntermediate = createPtProductIntermediate(billyPortugal);
        PTProduct productReduced = createPtProductReduced(billyPortugal);
        PTProduct productExempt = createPtProductExempt(billyPortugal);
        PTProduct productFlat = createPtProductFlat(billyPortugal, flatTax);
        invoice = createPtInvoice(dateFormat, billyPortugal, business, invoiceParameters, customer, productNormal, productIntermediate, productReduced, productExempt, productFlat);
        creditNote = createPtCreditNote(dateFormat, billyPortugal, business, creditNoteParameters, customer, productNormal, invoice);

        stubServerUrl = new URL("http", "localhost", getFreePort(), "/dummy");
        final String bindingURI = stubServerUrl.toString();

        webService = new StubFatcorewsPort();

        publish = Endpoint.publish(bindingURI, webService);
    }

    @AfterEach
    public void tearDown(){
        publish.stop();
    }

    @Test
    public void testSendInvoice() throws Exception {
        webService.setRegisterInvoiceCallback(registerInvoiceRequest -> {

            final List<InvoiceDataType.LineSummary> summaryList =
                registerInvoiceRequest.getInvoiceData().getLineSummary();

            /*
                Deve existir uma, e uma só linha, por cada taxa (TaxType, TaxCountryRegion,
                TaxCode), por cada motivo de isenção ou não liquidação (TaxExemptionCode), por
                cada data de envio de mercadoria ou prestação de serviço (TaxPointDate) por
                cada documento retificado (Reference), por cada nº de documento de origem
                (OriginatingOn) e por indicador de linha a crédito ou a débito (DebitCreditIndicator).
             */
            final Map<LineSummaryKey, Long> map = summaryList.stream().flatMap(lineSummary -> {
                final List<String> reference = lineSummary.getReference();
                if (reference == null || reference.isEmpty()) {
                    return Stream.of(
                        new LineSummaryKey(
                            lineSummary.getTax().getTaxType(), lineSummary.getTax().getTaxCountryRegion(),
                            lineSummary.getTax().getTaxCode(), lineSummary.getTaxExemptionCode(), lineSummary.getTaxPointDate(), null, lineSummary.getDebitCreditIndicator()));
                } else {
                    return lineSummary.getReference().stream()
                                      .map(y -> new LineSummaryKey(
                                          lineSummary.getTax().getTaxType(), lineSummary.getTax().getTaxCountryRegion(),
                                          lineSummary.getTax().getTaxCode(), lineSummary.getTaxExemptionCode(), lineSummary.getTaxPointDate(), y, lineSummary.getDebitCreditIndicator()));
                }
            }).collect(groupingBy(Function.identity(), counting()));
            map.forEach((k, v) -> Assertions.assertEquals(1, v));

            final ResponseType responseType = new ResponseType();
            responseType.setCodigoResposta(-1);
            responseType.setMensagem("Documento inválido");
            try {
                responseType.setDataOperacao(DatatypeFactory.newInstance().newXMLGregorianCalendar("2023-05-18T19:36:43Z"));
            } catch (DatatypeConfigurationException e) {
                throw new RuntimeException(e);
            }
            final RegisterInvoiceResponse response = new RegisterInvoiceResponse();
            response.setResponse(responseType);
            return response;
        });


        WebserviceCredentials webserviceCredentials = new WebserviceCredentials("599999993/37", "testes1234");
        WebserviceKeys webserviceKeys =
                new WebserviceKeys(Objects.requireNonNull(getClass().getResource("/saPubKey.jks")).toURI(), "saKeyPubPass", "sapubkey.prod");

        SslClientCertificate sslClientCertificate =
                new SslClientCertificate(Objects.requireNonNull(getClass().getResource("/TesteWebservices.pfx")).toURI(), "TESTEwebservice");

        Config config = new Config();
        final var client = new Client(config, stubServerUrl, webserviceCredentials, webserviceKeys, sslClientCertificate);

        final Response responseInvoice = client.sendInvoice(application, invoice);
        Assertions.assertNotNull(responseInvoice);
        Assertions.assertEquals(-1, responseInvoice.getCode());
        Assertions.assertFalse(responseInvoice.isSuccess());
        Assertions.assertEquals(LocalDateTime.parse("2023-05-18T19:36:43"), responseInvoice.getDate().toLocalDateTime());
        Assertions.assertEquals("Documento inválido", responseInvoice.getMessage());

    }

    @Test
    public void testSendCreditNote() throws Exception {
        webService.setRegisterInvoiceCallback(registerInvoiceRequest -> {

            final List<InvoiceDataType.LineSummary> summaryList =
                registerInvoiceRequest.getInvoiceData().getLineSummary();

            /*
                Deve existir uma, e uma só linha, por cada taxa (TaxType, TaxCountryRegion,
                TaxCode), por cada motivo de isenção ou não liquidação (TaxExemptionCode), por
                cada data de envio de mercadoria ou prestação de serviço (TaxPointDate) por
                cada documento retificado (Reference), por cada nº de documento de origem
                (OriginatingOn) e por indicador de linha a crédito ou a débito (DebitCreditIndicator).
             */
            final Map<LineSummaryKey, Long> map = summaryList.stream().flatMap(lineSummary -> {
                final List<String> reference = lineSummary.getReference();
                if (reference == null || reference.isEmpty()) {
                    return Stream.of(
                        new LineSummaryKey(
                            lineSummary.getTax().getTaxType(), lineSummary.getTax().getTaxCountryRegion(),
                            lineSummary.getTax().getTaxCode(), lineSummary.getTaxExemptionCode(), lineSummary.getTaxPointDate(), null, lineSummary.getDebitCreditIndicator()));
                } else {
                    return lineSummary.getReference().stream()
                                      .map(y -> new LineSummaryKey(
                                          lineSummary.getTax().getTaxType(), lineSummary.getTax().getTaxCountryRegion(),
                                          lineSummary.getTax().getTaxCode(), lineSummary.getTaxExemptionCode(), lineSummary.getTaxPointDate(), y, lineSummary.getDebitCreditIndicator()));
                }
            }).collect(groupingBy(Function.identity(), counting()));
            map.forEach((k, v) -> Assertions.assertEquals(1, v));

            final ResponseType responseType = new ResponseType();
            responseType.setCodigoResposta(-1);
            responseType.setMensagem("Documento inválido");
            try {
                responseType.setDataOperacao(DatatypeFactory.newInstance().newXMLGregorianCalendar("2023-05-18T19:36:43Z"));
            } catch (DatatypeConfigurationException e) {
                throw new RuntimeException(e);
            }
            final RegisterInvoiceResponse response = new RegisterInvoiceResponse();
            response.setResponse(responseType);
            return response;
        });


        WebserviceCredentials webserviceCredentials = new WebserviceCredentials("599999993/37", "testes1234");
        WebserviceKeys webserviceKeys =
            new WebserviceKeys(Objects.requireNonNull(getClass().getResource("/saPubKey.jks")).toURI(), "saKeyPubPass", "sapubkey.prod");

        SslClientCertificate sslClientCertificate =
            new SslClientCertificate(Objects.requireNonNull(getClass().getResource("/TesteWebservices.pfx")).toURI(), "TESTEwebservice");

        Config config = new Config();
        final var client = new Client(config, stubServerUrl, webserviceCredentials, webserviceKeys, sslClientCertificate);

        final Response responseInvoice = client.sendInvoice(application, creditNote);
        Assertions.assertNotNull(responseInvoice);
        Assertions.assertEquals(-1, responseInvoice.getCode());
        Assertions.assertFalse(responseInvoice.isSuccess());
        Assertions.assertEquals(LocalDateTime.parse("2023-05-18T19:36:43"), responseInvoice.getDate().toLocalDateTime());
        Assertions.assertEquals("Documento inválido", responseInvoice.getMessage());

    }


    @Test
    public void testDeleteInvoice() throws Exception {
        webService.setDeleteInvoiceCallback(deleteInvoiceRequest -> {
            Assertions.assertEquals(1, deleteInvoiceRequest.getDocumentsList().getInvoice().size());
            Assertions.assertEquals("some reason 1", deleteInvoiceRequest.getReason());

            final ResponseType responseType = new ResponseType();
            responseType.setCodigoResposta(-1);
            responseType.setMensagem("Documento inválido");
            try {
                responseType.setDataOperacao(DatatypeFactory.newInstance().newXMLGregorianCalendar("2023-05-18T19:36:43Z"));
            } catch (DatatypeConfigurationException e) {
                throw new RuntimeException(e);
            }
            final DeleteInvoiceResponse response = new DeleteInvoiceResponse();
            response.setResponse(responseType);
            return response;
        });


        WebserviceCredentials webserviceCredentials = new WebserviceCredentials("599999993/37", "testes1234");
        WebserviceKeys webserviceKeys =
            new WebserviceKeys(Objects.requireNonNull(getClass().getResource("/saPubKey.jks")).toURI(), "saKeyPubPass", "sapubkey.prod");

        SslClientCertificate sslClientCertificate =
            new SslClientCertificate(Objects.requireNonNull(getClass().getResource("/TesteWebservices.pfx")).toURI(), "TESTEwebservice");

        Config config = new Config();
        final var client = new Client(config, stubServerUrl, webserviceCredentials, webserviceKeys, sslClientCertificate);

        final Response responseInvoice = client.deleteInvoice(application, Arrays.asList(invoice), "some reason 1");
        Assertions.assertNotNull(responseInvoice);
        Assertions.assertEquals(-1, responseInvoice.getCode());
        Assertions.assertFalse(responseInvoice.isSuccess());
        Assertions.assertEquals(LocalDateTime.parse("2023-05-18T19:36:43"), responseInvoice.getDate().toLocalDateTime());
        Assertions.assertEquals("Documento inválido", responseInvoice.getMessage());

    }

    private int getFreePort() throws IOException {
        try (ServerSocket s = new ServerSocket(0)) {
            return s.getLocalPort();
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
                .setContextUID(billyPortugal.contexts().continent().allContinentRegions().getUID()).setCode("code1")
                .setDescription("description 1").setValidFrom(new Date(0)).setCurrency(Currency.getInstance("EUR"))
                .setValue(new BigDecimal("5.55"));

        return billyPortugal.taxes().persistence().create(taxBuilder);
    }

    private PTApplication.Builder getPTApplicationBuilder(BillyPortugal billyPortugal) throws MalformedURLException {
        PTContact.Builder contactBuilder = billyPortugal.contacts().builder();
        contactBuilder.setName("Developer 1").setTelephone("2000000001");

        PTApplication.Builder applicationBuilder = billyPortugal.applications().builder();
        applicationBuilder.setDeveloperCompanyName("Developer Company Name")
                .setDeveloperCompanyTaxIdentifier("1000000001", "PT")
                .setSoftwareCertificationNumber(SOFTWARE_CERTIFICATION).setName("Billy").setVersion("1.0")
                .setApplicationKeysPath(URI.create("http://www.keys.path").toURL()).addContact(contactBuilder)
                .setMainContact(contactBuilder);
        return applicationBuilder;
    }

    private PTApplication createPtApplication(BillyPortugal billyPortugal, PTApplication.Builder applicationBuilder) {
        return billyPortugal.applications().persistence().create(applicationBuilder);
    }

    private PTBusiness createPtBusiness(BillyPortugal billyPortugal, PTApplication.Builder applicationBuilder) {
        PTContact.Builder contactBuilder = billyPortugal.contacts().builder();
        contactBuilder.setName("CEO 1").setTelephone("200000002");

        PTAddress.Builder addressBuilder = billyPortugal.addresses().builder();
        addressBuilder.setStreetName("Street name 2").setNumber("2").setPostalCode("1000-100").setCity("Lisbon")
                .setISOCountry("PT").setDetails("Av. 5 de Outubro Nº 2 1000-100 Lisboa");

        PTBusiness.Builder businessBuilder = billyPortugal.businesses().builder();
        businessBuilder.addApplication(applicationBuilder).addContact(contactBuilder, true)
                .setMainContactUID(contactBuilder.build().getUID()).setName("Business 1")
                .setCommercialName("Business, INC").setFinancialID("599999993", "PT").setAddress(addressBuilder)
                .setBillingAddress(addressBuilder)
                .setTimezone(ZoneId.of("Europe/Lisbon"));

        return billyPortugal.businesses().persistence().create(businessBuilder);
    }

    private PTInvoice createPtInvoice(
        SimpleDateFormat dateFormat,
        BillyPortugal billyPortugal,
        PTBusiness business,
        PTIssuingParams invoiceParameters,
        PTCustomer customer,
        PTProduct productNormal,
        PTProduct productIntermediate,
        PTProduct productReduced,
        PTProduct productExempt,
        PTProduct productTax)
        throws ParseException, DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        PTInvoice.Builder invoiceBuilder = billyPortugal.invoices().builder();

        Date invoiceDate = dateFormat.parse("01-03-2023");
        invoiceBuilder.setSelfBilled(false).setCancelled(false).setBilled(false).setDate(invoiceDate)
                .setSourceId("User 1").setSourceBilling(PTGenericInvoice.SourceBilling.P)
                .setBusinessUID(business.getUID()).setCustomerUID(customer.getUID());

        // IVA normal - date 1
        for (int i = 0; i < 5; i++) {
            invoiceBuilder.addEntry(billyPortugal.invoices().entryBuilder()
                                                 .setCurrency(Currency.getInstance("EUR")).setQuantity(new BigDecimal("10"))
                                                 .setTaxPointDate(dateFormat.parse("01-02-2023"))
                                                 .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                                                 .setContextUID(billyPortugal.contexts().continent().allContinentRegions().getUID())
                                                 .setProductUID(productNormal.getUID()).setDescription(productNormal.getDescription())
                                                 .setUnitOfMeasure(productNormal.getUnitOfMeasure()));
        }

        // IVA normal - date 2
        for (int i = 0; i < 5; i++) {
            invoiceBuilder.addEntry(billyPortugal.invoices().entryBuilder()
                                                 .setCurrency(Currency.getInstance("EUR")).setQuantity(new BigDecimal("10"))
                                                 .setTaxPointDate(dateFormat.parse("02-02-2023"))
                                                 .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                                                 .setContextUID(billyPortugal.contexts().continent().allContinentRegions().getUID())
                                                 .setProductUID(productNormal.getUID()).setDescription(productNormal.getDescription())
                                                 .setUnitOfMeasure(productNormal.getUnitOfMeasure()));
        }

        // IVA intermediate
        for (int i = 0; i < 5; i++) {
            invoiceBuilder.addEntry(billyPortugal.invoices().entryBuilder()
                                                 .setCurrency(Currency.getInstance("EUR")).setQuantity(new BigDecimal("10"))
                                                 .setTaxPointDate(dateFormat.parse("01-02-2023"))
                                                 .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                                                 .setContextUID(billyPortugal.contexts().continent().allContinentRegions().getUID())
                                                 .setProductUID(productIntermediate.getUID()).setDescription(productIntermediate.getDescription())
                                                 .setUnitOfMeasure(productIntermediate.getUnitOfMeasure()));
        }

        // IVA reduced
        for (int i = 0; i < 5; i++) {
            invoiceBuilder.addEntry(billyPortugal.invoices().entryBuilder()
                                                 .setCurrency(Currency.getInstance("EUR")).setQuantity(new BigDecimal("10"))
                                                 .setTaxPointDate(dateFormat.parse("01-02-2023"))
                                                 .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                                                 .setContextUID(billyPortugal.contexts().continent().allContinentRegions().getUID())
                                                 .setProductUID(productReduced.getUID()).setDescription(productReduced.getDescription())
                                                 .setUnitOfMeasure(productReduced.getUnitOfMeasure()));
        }

        // IVA exempt - motive 1, date 1
        for (int i = 0; i < 5; i++) {
            invoiceBuilder.addEntry(billyPortugal.invoices().entryBuilder()
                                                 .setCurrency(Currency.getInstance("EUR")).setQuantity(new BigDecimal("10.0"))
                                                 .setTaxPointDate(dateFormat.parse("01-02-2023"))
                                                 .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                                                 .setContextUID(billyPortugal.contexts().continent().allContinentRegions().getUID())
                                                 .setProductUID(productExempt.getUID()).setDescription(productExempt.getDescription())
                                                 .setUnitOfMeasure(productExempt.getUnitOfMeasure()).setTaxExemptionCode("M04")
                                                 .setTaxExemptionReason("reason 04"));
        }

        // IVA exempt - motive 1, date 2
        for (int i = 0; i < 5; i++) {
            invoiceBuilder.addEntry(billyPortugal.invoices().entryBuilder()
                                                 .setCurrency(Currency.getInstance("EUR")).setQuantity(new BigDecimal("10.0"))
                                                 .setTaxPointDate(dateFormat.parse("02-02-2023"))
                                                 .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                                                 .setContextUID(billyPortugal.contexts().continent().allContinentRegions().getUID())
                                                 .setProductUID(productExempt.getUID()).setDescription(productExempt.getDescription())
                                                 .setUnitOfMeasure(productExempt.getUnitOfMeasure()).setTaxExemptionCode("M04")
                                                 .setTaxExemptionReason("reason 04"));
        }

        // IVA exempt - motive 2
        for (int i = 0; i < 5; i++) {
            invoiceBuilder.addEntry(billyPortugal.invoices().entryBuilder()
                                                 .setCurrency(Currency.getInstance("EUR")).setQuantity(new BigDecimal("10.0"))
                                                 .setTaxPointDate(dateFormat.parse("01-02-2023"))
                                                 .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                                                 .setContextUID(billyPortugal.contexts().continent().allContinentRegions().getUID())
                                                 .setProductUID(productExempt.getUID()).setDescription(productExempt.getDescription())
                                                 .setUnitOfMeasure(productExempt.getUnitOfMeasure()).setTaxExemptionCode("M99")
                                                 .setTaxExemptionReason("reason 99"));
        }

        // IVA normal - azores
        for (int i = 0; i < 5; i++) {
            invoiceBuilder.addEntry(billyPortugal.invoices().entryBuilder()
                                                 .setCurrency(Currency.getInstance("EUR")).setQuantity(new BigDecimal("10"))
                                                 .setTaxPointDate(dateFormat.parse("01-02-2023"))
                                                 .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                                                 .setContextUID(billyPortugal.contexts().azores().azores().getUID())
                                                 .setProductUID(productNormal.getUID()).setDescription(productNormal.getDescription())
                                                 .setUnitOfMeasure(productNormal.getUnitOfMeasure()));
        }

        // IVA normal - madeira
        for (int i = 0; i < 5; i++) {
            invoiceBuilder.addEntry(billyPortugal.invoices().entryBuilder()
                                                 .setCurrency(Currency.getInstance("EUR")).setQuantity(new BigDecimal("10"))
                                                 .setTaxPointDate(dateFormat.parse("01-02-2023"))
                                                 .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                                                 .setContextUID(billyPortugal.contexts().madeira().madeira().getUID())
                                                 .setProductUID(productNormal.getUID()).setDescription(productNormal.getDescription())
                                                 .setUnitOfMeasure(productNormal.getUnitOfMeasure()));
        }

        // Flat tax - IS
        for (int i = 0; i < 5; i++) {
            invoiceBuilder.addEntry(billyPortugal.invoices().entryBuilder()
                                                 .setCurrency(Currency.getInstance("EUR")).setQuantity(new BigDecimal("10.0"))
                                                 .setTaxPointDate(dateFormat.parse("01-02-2023"))
                                                 .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                                                 .setContextUID(billyPortugal.contexts().continent().allContinentRegions().getUID())
                                                 .setProductUID(productTax.getUID()).setDescription(productTax.getDescription())
                                                 .setUnitOfMeasure(productTax.getUnitOfMeasure()));
        }

        return billyPortugal.invoices().issue(invoiceBuilder, invoiceParameters);
    }

    private PTCreditNote createPtCreditNote(SimpleDateFormat dateFormat, BillyPortugal billyPortugal,
            PTBusiness business, PTIssuingParams invoiceParameters, PTCustomer customer, PTProduct product,
            PTInvoice invoice) throws ParseException, DocumentIssuingException, SeriesUniqueCodeNotFilled,
            DocumentSeriesDoesNotExistException {
        PTCreditNote.Builder creditNoteBuilder = billyPortugal.creditNotes().builder();

        Date creditNoteDate = dateFormat.parse("01-03-2023");
        creditNoteBuilder.setSelfBilled(false).setCancelled(false).setBilled(false).setDate(creditNoteDate)
                .setSourceId("User 2").setSourceBilling(PTGenericInvoice.SourceBilling.P)
                .setBusinessUID(business.getUID()).setCustomerUID(customer.getUID());

        PTCreditNoteEntry.Builder entryBuilder = billyPortugal.creditNotes().entryBuilder();
        entryBuilder.setAmountType(GenericInvoiceEntryBuilder.AmountType.WITH_TAX)
                .setCurrency(Currency.getInstance("EUR")).setQuantity(new BigDecimal("10"))
                .setTaxPointDate(dateFormat.parse("01-02-2023"))
                .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                .setContextUID(billyPortugal.contexts().continent().allContinentRegions().getUID())
                .setProductUID(product.getUID()).setDescription(product.getDescription())
                .setUnitOfMeasure(product.getUnitOfMeasure()).setReferenceUID(invoice.getUID())
                .setReason("some reason 1");

        creditNoteBuilder.addEntry(entryBuilder);

        return billyPortugal.creditNotes().issue(creditNoteBuilder, invoiceParameters);
    }

    private PTProduct createPtProductNormal(BillyPortugal billyPortugal) {
        PTProduct.Builder productBuilder = billyPortugal.products().builder();
        productBuilder.setDescription("product 1")
                      .setNumberCode("1")
                      .setProductCode("1")
                      .setType(Product.ProductType.GOODS)
                      .setUnitOfMeasure("kg")
                      .setProductGroup("group 1")
                      .addTaxUID(billyPortugal.taxes().continent().normal().getUID())
                      .addTaxUID(billyPortugal.taxes().azores().normal().getUID())
                      .addTaxUID(billyPortugal.taxes().madeira().normal().getUID());

        return billyPortugal.products().persistence().create(productBuilder);
    }

    private PTProduct createPtProductIntermediate(BillyPortugal billyPortugal) {
        PTProduct.Builder productBuilder = billyPortugal.products().builder();
        productBuilder.setDescription("product 1")
                      .setNumberCode("1")
                      .setProductCode("1")
                      .setType(Product.ProductType.GOODS)
                      .setUnitOfMeasure("kg")
                      .setProductGroup("group 1")
                      .addTaxUID(billyPortugal.taxes().continent().intermediate().getUID())
                      .addTaxUID(billyPortugal.taxes().azores().intermediate().getUID())
                      .addTaxUID(billyPortugal.taxes().madeira().intermediate().getUID());;

        return billyPortugal.products().persistence().create(productBuilder);
    }

    private PTProduct createPtProductReduced(BillyPortugal billyPortugal) {
        PTProduct.Builder productBuilder = billyPortugal.products().builder();
        productBuilder.setDescription("product 1")
                      .setNumberCode("1")
                      .setProductCode("1")
                      .setType(Product.ProductType.GOODS)
                      .setUnitOfMeasure("kg")
                      .setProductGroup("group 1")
                      .addTaxUID(billyPortugal.taxes().continent().reduced().getUID())
                      .addTaxUID(billyPortugal.taxes().azores().reduced().getUID())
                      .addTaxUID(billyPortugal.taxes().madeira().reduced().getUID());;

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
        contactBuilder.setName("Customer 1").setTelephone("telephone 1");

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
