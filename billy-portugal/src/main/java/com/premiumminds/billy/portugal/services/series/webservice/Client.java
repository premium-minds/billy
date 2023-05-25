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
package com.premiumminds.billy.portugal.services.series.webservice;

import com.premiumminds.billy.portugal.webservices.series.ConsultSeriesResp;
import com.premiumminds.billy.portugal.webservices.series.OperationResultInfo;
import com.premiumminds.billy.portugal.webservices.series.SeriesResp;
import com.premiumminds.billy.portugal.webservices.series.SeriesWS;
import com.premiumminds.billy.portugal.webservices.series.SeriesWSService;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.List;
import java.util.stream.Collectors;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

public class Client {

    private static final int CREATE_SUCCESS_CODE = 2001;
    private static final int CONSULT_SUCCESS_CODE = 2002;
    private static final int REVOKE_SUCCESS_CODE = 2003;
    private static final int FINALIZE_SUCCESS_CODE = 2004;
    private final URL url;
    private final WebserviceCredentials webserviceCredentials;
    private final WebserviceKeys webserviceKeys;
    private final SslClientCertificate sslClientCertificate;

    public Client(
        final URL url,
        final WebserviceCredentials webserviceCredentials,
        final WebserviceKeys webserviceKeys,
        final SslClientCertificate sslClientCertificate)
    {
        this.url = url;
        this.webserviceCredentials = webserviceCredentials;
        this.webserviceKeys = webserviceKeys;
        this.sslClientCertificate = sslClientCertificate;
    }

    public SingleSeriesResponse createSeries(CreateSeriesRequest request){

        final SeriesWS seriesWS = getSerieswsPortSoap();
        final SeriesResp response = seriesWS.registarSerie(
            request.getSerie(),
            convert(request.getTipoSerie()),
            convert(request.getClasseDoc()),
            convert(request.getTipoDoc()),
            request.getNumInicialSeq(),
            convertToXMLGregorianCalendar(request.getDataInicioPrevUtiliz()),
            new BigInteger(request.getNumCertSWFatur()),
            convert(request.getMeioProcessamento()));

        return new SingleSeriesResponse(
            convert(response.getInfoResultOper(), CREATE_SUCCESS_CODE),
            convert(response.getInfoSerie())
        );
    }

    public SingleSeriesResponse finalizeSeries(FinalizeSeriesRequest request){

        final SeriesWS seriesWS = getSerieswsPortSoap();
        final SeriesResp response = seriesWS.finalizarSerie(
            request.getSerie(),
            convert(request.getClasseDoc()),
            convert(request.getTipoDoc()),
            request.getCodValidacaoSerie(),
            request.getSeqUltimoDocEmitido(),
            request.getJustificacao());

        return new SingleSeriesResponse(
            convert(response.getInfoResultOper(), FINALIZE_SUCCESS_CODE),
            convert(response.getInfoSerie())
        );
    }

    public SingleSeriesResponse revokeSeries(RevokeSeriesRequest request){

        final SeriesWS seriesWS = getSerieswsPortSoap();
        final SeriesResp response = seriesWS.anularSerie(
            request.getSerie(),
            convert(request.getClasseDoc()),
            convert(request.getTipoDoc()),
            request.getCodValidacaoSerie(),
            convert(request.getMotivo()),
            request.isDeclaracaoNaoEmissao());

        return new SingleSeriesResponse(
            convert(response.getInfoResultOper(), REVOKE_SUCCESS_CODE),
            convert(response.getInfoSerie())
        );
    }

    public MultipleSeriesResponse consultSeries(ConsultSeriesRequest request){

        final SeriesWS seriesWS = getSerieswsPortSoap();
        final ConsultSeriesResp response = seriesWS.consultarSeries(
            request.getSerie(),
            convert(request.getTipoSerie()),
            convert(request.getClasseDoc()),
            convert(request.getTipoDoc()),
            request.getCodValidacaoSerie(),
            convertToXMLGregorianCalendar(request.getDataRegistoDe()),
            convertToXMLGregorianCalendar(request.getDataRegistoAte()),
            request.getEstado(),
            convert(request.getMeioProcessamento()));

        return new MultipleSeriesResponse(
            convert(response.getInfoResultOper(), CONSULT_SUCCESS_CODE),
            response.getInfoSerie().stream().map(this::convert).collect(Collectors.toList()));
    }

    private String convert(RevokeJustification revokeJustification){
        if (revokeJustification != null){
            switch (revokeJustification){
                case REGISTRATION_ERROR: return "ER";
                default:
                    throw new RuntimeException("unknown justification: " + revokeJustification);
            }
        }
        return null;
    }

    private String convert(DocumentType documentType){
        if (documentType != null){
            switch (documentType){
                case INVOICE: return "FT";
                case SIMPLE_INVOICE: return "FS";
                case INVOICE_RECEIPT: return "FR";
                case DEBIT_NOTE: return "ND";
                case CREDIT_NOTE: return "NC";
                default:
                    throw new RuntimeException("unknown document type: " + documentType);
            }
        }
        return null;
    }

    private String convert(DocumentClass documentClass){
        if (documentClass != null){
            switch (documentClass){
                case INVOICING_DOCUMENTS: return "SI";
                case TRANSPORT_DOCUMENTS: return "MG";
                case CONFERENCE_DOCUMENTS: return "WD";
                case RECEIPTS: return "PY";
                default:
                    throw new RuntimeException("unknown document class: " + documentClass);
            }
        }
        return null;
    }
    private String convert(SeriesType seriesType){
        if (seriesType != null){
            switch (seriesType){
                case NORMAL: return "N";
                case RECOVERY: return "F";
                case TRAINING: return "R";
                default:
                    throw new RuntimeException("unknown series type: " + seriesType);
            }
        }
        return null;
    }

    private String convert(SeriesProcessingMedium processingMedium){
        if (processingMedium != null){
            switch (processingMedium){
                case COMPUTER_PROGRAM: return "PI";
                case FINANCAS_WEBPORTAL: return "PF";
                case OTHER_ELECTRONIC: return "OM";
                default:
                    throw new RuntimeException("unknown processing medium: " + processingMedium);
            }
        }
        return null;
    }

    private ResultInfo convert(OperationResultInfo resultInfo, int successCode) {
        if (resultInfo != null) {
            return new ResultInfo(
                resultInfo.getCodResultOper().intValue(),
                resultInfo.getMsgResultOper(),
                resultInfo.getCodResultOper().intValue() == successCode);
        }
        return null;
    }

    private SeriesInfo convert(com.premiumminds.billy.portugal.webservices.series.SeriesInfo seriesInfos){
        if (seriesInfos != null){
            final SeriesInfo rvalue = new SeriesInfo();
            rvalue.setSerie(seriesInfos.getSerie());
            rvalue.setTipoSerie(seriesInfos.getTipoSerie());
            rvalue.setClasseDoc(seriesInfos.getClasseDoc());
            rvalue.setTipoDoc(seriesInfos.getTipoDoc());
            rvalue.setNumInicialSeq(seriesInfos.getNumInicialSeq());
            rvalue.setNumFinalSeq(seriesInfos.getNumFinalSeq());
            rvalue.setDataInicioPrevUtiliz(convertLocalDate(seriesInfos.getDataInicioPrevUtiliz()));
            rvalue.setSeqUltimoDocEmitido(seriesInfos.getSeqUltimoDocEmitido());
            rvalue.setMeioProcessamento(seriesInfos.getMeioProcessamento());
            rvalue.setNumCertSWFatur(seriesInfos.getNumCertSWFatur());
            rvalue.setCodValidacaoSerie(seriesInfos.getCodValidacaoSerie());
            rvalue.setDataRegisto(convertLocalDate(seriesInfos.getDataRegisto()));
            rvalue.setEstado(seriesInfos.getEstado());
            rvalue.setMotivoEstado(seriesInfos.getMotivoEstado());
            rvalue.setJustificacao(seriesInfos.getJustificacao());
            rvalue.setDataEstado(convertLocalDateTime(seriesInfos.getDataEstado()));
            rvalue.setNifComunicou(seriesInfos.getNifComunicou());
            return rvalue;
        }
        return null;
    }

    private LocalDateTime convertLocalDateTime(XMLGregorianCalendar date) {
        if (date != null) {
            return date.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
        }
        return null;
    }

    private LocalDate convertLocalDate(XMLGregorianCalendar date) {
        if (date != null) {
            return date.toGregorianCalendar().toZonedDateTime().toLocalDate();
        }
        return null;
    }

    private XMLGregorianCalendar convertToXMLGregorianCalendar(final LocalDate date) {
        if (date != null){
            try {
                return DatatypeFactory.newInstance().newXMLGregorianCalendar(
                        date.toString());
            } catch (DatatypeConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private SeriesWS getSerieswsPortSoap(){

        final SeriesWSService seriesWSService = new SeriesWSService();
        final SeriesWS seriesWSPort = seriesWSService.getSeriesWSPort();
        final BindingProvider bindingProvider = (BindingProvider) seriesWSPort;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url.toString());
        final List<Handler> chain = bindingProvider.getBinding().getHandlerChain();

        AuthenticationHandler
            soapClientHeaderHandler = new AuthenticationHandler(webserviceCredentials, webserviceKeys);
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
        return seriesWSPort;
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
