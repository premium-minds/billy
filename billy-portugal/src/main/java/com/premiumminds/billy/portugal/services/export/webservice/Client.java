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
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.webservices.documents.DateRangeType;
import com.premiumminds.billy.portugal.webservices.documents.DebitCreditIndicator;
import com.premiumminds.billy.portugal.webservices.documents.DeleteInvoiceRequest;
import com.premiumminds.billy.portugal.webservices.documents.DocumentTotals;
import com.premiumminds.billy.portugal.webservices.documents.FatcorewsPort;
import com.premiumminds.billy.portugal.webservices.documents.FatcorewsPortService;
import com.premiumminds.billy.portugal.webservices.documents.InvoiceDataType;
import com.premiumminds.billy.portugal.webservices.documents.InvoiceDataType.LineSummary;
import com.premiumminds.billy.portugal.webservices.documents.InvoiceStatus;
import com.premiumminds.billy.portugal.webservices.documents.InvoiceTypeType;
import com.premiumminds.billy.portugal.webservices.documents.ListInvoiceDocumentsType;
import com.premiumminds.billy.portugal.webservices.documents.RegisterInvoiceRequest;
import com.premiumminds.billy.portugal.webservices.documents.ResponseType;
import com.premiumminds.billy.portugal.webservices.documents.Tax;
import com.premiumminds.billy.portugal.webservices.documents.TaxType;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import static java.util.stream.Collectors.groupingBy;

public class Client {

    private static final String EFATURA_MD_VERSION = "0.0.1";
    private static final String AUDIT_FILE_VERSION = "1.04_01";
    private static final int SUCCESS_CODE = 0;

    private final Config config;
    private final URL url;
    private final WebserviceCredentials webserviceCredentials;
    private final WebserviceKeys webserviceKeys;
    private final SslClientCertificate sslClientCertificate;

    public Client(Config config, URL url, WebserviceCredentials webserviceCredentials, WebserviceKeys webserviceKeys,
            SslClientCertificate sslClientCertificate) {
        this.config = config;
        this.url = url;
        this.webserviceCredentials = webserviceCredentials;
        this.webserviceKeys = webserviceKeys;
        this.sslClientCertificate = sslClientCertificate;
    }

    public Response sendInvoice(PTApplication application, PTGenericInvoice ptGenericInvoice){
        try {
            final FatcorewsPort fatcorewsPortSoap = getFatcorewsPortSoap();
            final RegisterInvoiceRequest request = getRequest(ptGenericInvoice, application);
            final ResponseType response = fatcorewsPortSoap.registerInvoice(request).getResponse();

            return new Response(
                    response.getCodigoResposta(),
                    response.getDataOperacao().toGregorianCalendar().toZonedDateTime(),
                    response.getMensagem(),
                    response.getCodigoResposta() == SUCCESS_CODE);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public Response deleteInvoice(PTApplication application, List<PTGenericInvoice> ptGenericInvoices, String reason){
        try{
            final FatcorewsPort fatcorewsPortSoap = getFatcorewsPortSoap();

            final DeleteInvoiceRequest request = new DeleteInvoiceRequest();
            request.setEFaturaMDVersion(EFATURA_MD_VERSION);
            request.setTaxRegistrationNumber(application.getSoftwareCertificationNumber());
            request.setReason(reason);

            final ListInvoiceDocumentsType invoiceDocumentsType = new ListInvoiceDocumentsType();
            for (PTGenericInvoice ptGenericInvoice : ptGenericInvoices) {

                final InvoiceDataType invoiceData = new InvoiceDataType();
                invoiceData.setInvoiceNo(ptGenericInvoice.getNumber());
                invoiceData.setATCUD(ptGenericInvoice.getATCUD());
                invoiceData.setInvoiceDate(formatLocalDateTime(
                        LocalDateTime.ofInstant(ptGenericInvoice.getDate().toInstant(), ptGenericInvoice.getBusiness().getTimezone())));
                invoiceData.setInvoiceType(processInvoiceType(ptGenericInvoice));
                invoiceData.setSelfBillingIndicator(BigInteger.ZERO);
                if (this.config.getUID(Config.Key.Customer.Generic.UUID).equals(ptGenericInvoice.getCustomer().getUID())) {
                    invoiceData.setCustomerTaxID("999999990");
                    invoiceData.setCustomerTaxIDCountry("PT");
                } else {
                    invoiceData.setCustomerTaxID(ptGenericInvoice.getCustomer().getTaxRegistrationNumber());
                    invoiceData.setCustomerTaxIDCountry(ptGenericInvoice.getCustomer().getTaxRegistrationNumberISOCountryCode());
                }
                invoiceData.setDocumentStatus(processStatus(ptGenericInvoice));
                invoiceDocumentsType.getInvoice().add(invoiceData);
            }
            request.setDocumentsList(invoiceDocumentsType);

            final ResponseType response = fatcorewsPortSoap.deleteInvoice(request).getResponse();

            return new Response(
                    response.getCodigoResposta(),
                    response.getDataOperacao().toGregorianCalendar().toZonedDateTime(),
                    response.getMensagem(),
                    response.getCodigoResposta() == SUCCESS_CODE);
        } catch (DatatypeConfigurationException e){
            throw new RuntimeException(e);
        }
    }

    public Response deleteInvoice(PTApplication application, LocalDate from, LocalDate to, String reason){

        try {
            final FatcorewsPort fatcorewsPortSoap = getFatcorewsPortSoap();

            final DeleteInvoiceRequest request = new DeleteInvoiceRequest();
            request.setEFaturaMDVersion(EFATURA_MD_VERSION);
            request.setTaxRegistrationNumber(application.getSoftwareCertificationNumber());
            request.setReason(reason);
            final DateRangeType dateRangeType = new DateRangeType();
            dateRangeType.setStartDate(formatLocalDate(from));
            dateRangeType.setEndDate(formatLocalDate(to));
            request.setDateRange(dateRangeType);
            final ResponseType response = fatcorewsPortSoap.deleteInvoice(request).getResponse();

            return new Response(
                    response.getCodigoResposta(),
                    response.getDataOperacao().toGregorianCalendar().toZonedDateTime(),
                    response.getMensagem(),
                    response.getCodigoResposta() == SUCCESS_CODE);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private FatcorewsPort getFatcorewsPortSoap(){

        final FatcorewsPortService fatcorewsPortService = new FatcorewsPortService();
        final FatcorewsPort fatcorewsPortSoap = fatcorewsPortService.getFatcorewsPortSoap11();
        final BindingProvider bindingProvider = (BindingProvider) fatcorewsPortSoap;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url.toString());
        final List<Handler> chain = bindingProvider.getBinding().getHandlerChain();

        AuthenticationHandler soapClientHeaderHandler = new AuthenticationHandler(webserviceCredentials, webserviceKeys);
        chain.add(soapClientHeaderHandler);
        bindingProvider.getBinding().setHandlerChain(chain);

        try {
            bindingProvider.getRequestContext().put(
                    "com.sun.xml.ws.transport.https.client.SSLSocketFactory",
                    getSslContext(sslClientCertificate).getSocketFactory());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException |
                UnrecoverableKeyException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
        return fatcorewsPortSoap;
    }

    private RegisterInvoiceRequest getRequest(PTGenericInvoice ptGenericInvoice, PTApplication application) throws DatatypeConfigurationException {
        final RegisterInvoiceRequest registerInvoiceRequest = new RegisterInvoiceRequest();
        registerInvoiceRequest.setEFaturaMDVersion(EFATURA_MD_VERSION);
        registerInvoiceRequest.setAuditFileVersion(AUDIT_FILE_VERSION);
        registerInvoiceRequest.setTaxRegistrationNumber(Integer.parseInt(ptGenericInvoice.getBusiness().getFinancialID()));
        registerInvoiceRequest.setTaxEntity("Global");
        registerInvoiceRequest.setSoftwareCertificateNumber(BigInteger.valueOf(application.getSoftwareCertificationNumber()));
        final InvoiceDataType invoiceData = new InvoiceDataType();
        invoiceData.setInvoiceNo(ptGenericInvoice.getNumber());
        invoiceData.setATCUD(ptGenericInvoice.getATCUD());
        invoiceData.setInvoiceDate(formatLocalDateTime(
                LocalDateTime.ofInstant(ptGenericInvoice.getDate().toInstant(), ptGenericInvoice.getBusiness().getTimezone())));
        invoiceData.setInvoiceType(processInvoiceType(ptGenericInvoice));
        invoiceData.setSelfBillingIndicator(BigInteger.ZERO);
        if (this.config.getUID(Config.Key.Customer.Generic.UUID).equals(ptGenericInvoice.getCustomer().getUID())) {
            invoiceData.setCustomerTaxID("999999990");
            invoiceData.setCustomerTaxIDCountry("PT");
        } else {
            invoiceData.setCustomerTaxID(ptGenericInvoice.getCustomer().getTaxRegistrationNumber());
            invoiceData.setCustomerTaxIDCountry(ptGenericInvoice.getCustomer().getTaxRegistrationNumberISOCountryCode());
        }
        invoiceData.setDocumentStatus(processStatus(ptGenericInvoice));
        invoiceData.setHashCharacters(getVerificationHashString(ptGenericInvoice.getHash()));
        if (ptGenericInvoice.isCashVATEndorser() != null) {
            invoiceData.setCashVATSchemeIndicator(ptGenericInvoice.isCashVATEndorser() ? 1 : 0);
        } else {
            invoiceData.setCashVATSchemeIndicator(0);
        }
        invoiceData.setPaperLessIndicator(0);
        invoiceData.setSystemEntryDate(formatLocalDateTime(
                LocalDateTime.ofInstant(ptGenericInvoice.getCreateTimestamp().toInstant(), ptGenericInvoice.getBusiness().getTimezone())));
        invoiceData.getLineSummary().addAll(processEntries(ptGenericInvoice));
        final DocumentTotals documentTotals = new DocumentTotals();

        ptGenericInvoice.getAmountWithoutTax();
        documentTotals.setTaxPayable(ptGenericInvoice.getTaxAmount());
        documentTotals.setNetTotal(ptGenericInvoice.getAmountWithoutTax());
        documentTotals.setGrossTotal(ptGenericInvoice.getAmountWithTax());
        invoiceData.setDocumentTotals(documentTotals);
        registerInvoiceRequest.setInvoiceData(invoiceData);
        return registerInvoiceRequest;
    }

    static class LineSummaryValue {

        final BigDecimal lineAmount;
        final BigDecimal taxPercentage;
        final BigDecimal totalTaxAmount;

        public LineSummaryValue(
            final BigDecimal lineAmount,
            final BigDecimal taxPercentage,
            final BigDecimal totalTaxAmount)
        {
            this.lineAmount = lineAmount;
            this.taxPercentage = taxPercentage;
            this.totalTaxAmount = totalTaxAmount;
        }
    }

    private List<InvoiceDataType.LineSummary> processEntries(PTGenericInvoice ptGenericInvoice) {

        final Map<LineSummaryKey, LineSummaryValue> collect = ptGenericInvoice
            .getEntries()
            .stream()
            .map((GenericInvoiceEntry entry) -> processEntry(entry, ptGenericInvoice))
            .collect(groupingBy(x -> Objects.requireNonNull(x).getLeft(),
                                Collectors.reducing(new LineSummaryValue(BigDecimal.ZERO, null, BigDecimal.ZERO),
                                                    Pair::getRight,
                                                    (a,b) -> new LineSummaryValue(a.lineAmount.add(b.lineAmount),
                                                                                  b.taxPercentage,
                                                                                  a.totalTaxAmount.add(b.totalTaxAmount)))));

        return collect.entrySet().stream().map(x -> {
            final LineSummary lineSummary = new LineSummary();
            lineSummary.setTaxPointDate(x.getKey().taxPointDate);
            lineSummary.setDebitCreditIndicator(x.getKey().creditOrDebit);
            lineSummary.setAmount(x.getValue().lineAmount);
            Tax tax = new Tax();
            tax.setTaxType(x.getKey().taxType);
            tax.setTaxCountryRegion(x.getKey().taxCountryRegion);
            tax.setTaxCode(x.getKey().taxCode);

            switch (x.getKey().taxType) {
                case IVA:
                    tax.setTaxPercentage(x.getValue().taxPercentage);
                    break;
                case IS:
                    tax.setTotalTaxAmount(x.getValue().totalTaxAmount);
                    break;
                case NS:
                    tax.setTaxPercentage(BigDecimal.ZERO);
                    break;
            }
            lineSummary.setTax(tax);
            lineSummary.setTaxExemptionCode(x.getKey().taxExemptionCode);
            lineSummary.getReference().add(x.getKey().reference);
            return lineSummary;
        }).collect(Collectors.toList());
    }

    private Pair<LineSummaryKey, LineSummaryValue> processEntry(GenericInvoiceEntry entry, PTGenericInvoice document) {
        if (entry.getTaxes().size() > 0) {
            PTTaxEntity billyTax = (PTTaxEntity) entry.getTaxes().iterator().next();
            try {
                final LineSummaryKey lineSummary = new LineSummaryKey(
                    getTaxType(billyTax),
                    getRegionCodeFromISOCode(((PTRegionContext) billyTax.getContext()).getRegionCode()),
                    billyTax.getCode(),
                    entry.getTaxExemptionCode(),
                    formatLocalDate(
                                LocalDate.ofInstant(entry.getTaxPointDate().toInstant(), document.getBusiness().getTimezone())),
                    getReference(entry),
                    getDebitCreditIndicator(entry));
                return ImmutablePair.of(
                    lineSummary,
                    new LineSummaryValue(entry.getAmountWithoutTax(),
                                         billyTax.getValue(),
                                         billyTax.getValue().multiply(entry.getQuantity())));
            } catch (DatatypeConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private DebitCreditIndicator getDebitCreditIndicator(final GenericInvoiceEntry entry) {
        final DebitCreditIndicator debitCreditIndicator;
        switch (entry.getCreditOrDebit()) {
            case DEBIT:
                debitCreditIndicator = DebitCreditIndicator.D;
                break;
            case CREDIT:
                debitCreditIndicator = DebitCreditIndicator.C;
                break;
            default:
                throw new RuntimeException("unknow value for: " + entry.getCreditOrDebit());
        }
        return debitCreditIndicator;
    }

    private String getReference(final GenericInvoiceEntry entry) {
        String reference = null;
        if (entry instanceof PTCreditNoteEntryEntity) {
            reference = ((PTCreditNoteEntryEntity) entry).getReference().getNumber();
        }
        return reference;
    }

    private String getRegionCodeFromISOCode(String regionCode) {
        if (regionCode.equals("PT-20")) {
            return "PT-AC";
        }
        if (regionCode.equals("PT-30")) {
            return "PT-MA";
        }
        return "PT";
    }

    private TaxType getTaxType(PTTaxEntity entity) {
        switch (entity.getTaxRateType()) {
            case PERCENTAGE:
                return TaxType.IVA;
            case FLAT:
                return TaxType.IS;
            case NONE:
                return TaxType.NS;
            default:
                throw new RuntimeException(entity.getTaxRateType().toString());
        }
    }

    private InvoiceTypeType processInvoiceType(PTGenericInvoice ptGenericInvoice) {
        switch (ptGenericInvoice.getType()) {
            case FT:
                return InvoiceTypeType.FT;
            case FS:
                return InvoiceTypeType.FS;
            case FR:
                return InvoiceTypeType.FR;
            case NC:
                return InvoiceTypeType.NC;
            case ND:
                return InvoiceTypeType.ND;
            default:
                throw new RuntimeException(ptGenericInvoice.getType().toString());
        }
    }

    private InvoiceStatus processStatus(PTGenericInvoice ptGenericInvoice) throws DatatypeConfigurationException {
        final InvoiceStatus invoiceStatus = new InvoiceStatus();
        if (ptGenericInvoice.isCancelled()) {
            invoiceStatus.setInvoiceStatus("A");
        } else if (ptGenericInvoice.isBilled()) {
            invoiceStatus.setInvoiceStatus("F");
        } else if (ptGenericInvoice.isSelfBilled()) {
            invoiceStatus.setInvoiceStatus("S");
        } else {
            invoiceStatus.setInvoiceStatus("N");
        }
        invoiceStatus.setInvoiceStatusDate(formatLocalDateTime(
                LocalDateTime.ofInstant(ptGenericInvoice.getDate().toInstant(), ptGenericInvoice.getBusiness().getTimezone())));
        return invoiceStatus;
    }

    private String getVerificationHashString(String hash) {
        return String.valueOf(hash.charAt(0))
                + hash.charAt(10)
                + hash.charAt(20)
                + hash.charAt(30);
    }

    private XMLGregorianCalendar formatLocalDateTime(LocalDateTime date) throws DatatypeConfigurationException {
        return DatatypeFactory
                .newInstance()
                .newXMLGregorianCalendar(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(date));
    }

    private XMLGregorianCalendar formatLocalDate(LocalDate date) throws DatatypeConfigurationException {
        return DatatypeFactory
                .newInstance()
                .newXMLGregorianCalendar(DateTimeFormatter.ISO_LOCAL_DATE.format(date));
    }

    private SSLContext getSslContext(SslClientCertificate sslClientCertificate)
            throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException,
            UnrecoverableKeyException, KeyManagementException
    {
        try (InputStream keyStoreInputStream = sslClientCertificate.getPath().toURL().openStream()) {

            final KeyStore keyStore= KeyStore.getInstance("pkcs12");
            keyStore.load(keyStoreInputStream, sslClientCertificate.getPassword().toCharArray());

            final KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(keyStore, sslClientCertificate.getPassword().toCharArray());

            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, null);

            return sslContext;
        }
    }
}
