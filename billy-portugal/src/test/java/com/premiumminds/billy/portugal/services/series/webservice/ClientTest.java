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
import com.premiumminds.billy.portugal.webservices.series.SeriesInfo;
import com.premiumminds.billy.portugal.webservices.series.SeriesResp;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import javax.xml.ws.Endpoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientTest {

    private StubSeriesWS webService;
    private URL stubServerUrl;
    private Endpoint publish;

    @BeforeEach
    void setUp() throws IOException {
        stubServerUrl = new URL("http", "localhost", getFreePort(), "/dummy");
        final String bindingURI = stubServerUrl.toString();

        webService = new StubSeriesWS();

        publish = Endpoint.publish(bindingURI, webService);
    }

    @AfterEach
    void tearDown(){
        publish.stop();
    }


    @Test
    void createSeries() throws Exception {

        webService.setRegistarSerieCallback(serie -> {
            final SeriesResp seriesResp = new SeriesResp();
            final OperationResultInfo infoResultOper = new OperationResultInfo();
            infoResultOper.setCodResultOper(BigInteger.valueOf(2001));
            infoResultOper.setMsgResultOper("success");
            seriesResp.setInfoResultOper(infoResultOper);
            final SeriesInfo info = new SeriesInfo();
            info.setSerie(serie);
            info.setCodValidacaoSerie("AAJFFSPSWJ");
            seriesResp.setInfoSerie(info);
            return seriesResp;
        });

        WebserviceCredentials
            webserviceCredentials = new WebserviceCredentials("599999993/37", "testes1234");
        WebserviceKeys webserviceKeys =
            new WebserviceKeys(Objects.requireNonNull(getClass().getResource("/saPubKey.jks")).toURI(), "saKeyPubPass", "sapubkey.prod");

        SslClientCertificate sslClientCertificate =
            new SslClientCertificate(Objects.requireNonNull(getClass().getResource("/TesteWebservices.pfx")).toURI(), "TESTEwebservice");

        var client = new Client(stubServerUrl, webserviceCredentials, webserviceKeys, sslClientCertificate);

        CreateSeriesRequest request = new CreateSeriesRequest();
        request.setSerie("SFJSJDFLS");
        request.setMeioProcessamento(SeriesProcessingMedium.COMPUTER_PROGRAM);
        request.setNumCertSWFatur("999");
        request.setClasseDoc(DocumentClass.INVOICING_DOCUMENTS);
        request.setTipoSerie(SeriesType.NORMAL);
        request.setDataInicioPrevUtiliz(LocalDate.now().plusDays(1));
        request.setTipoDoc(DocumentType.INVOICE);
        request.setNumInicialSeq(BigInteger.ONE);
        final SingleSeriesResponse response = client.createSeries(request);

        Assertions.assertTrue(response.getResultInfo().isSuccess());
        Assertions.assertEquals("SFJSJDFLS", response.getInfoSerie().getSerie());
        Assertions.assertEquals("AAJFFSPSWJ", response.getInfoSerie().getCodValidacaoSerie());

    }

    @Test
    void finalizeSeries() throws Exception {

        webService.setFinalizeSerieCallback(serie -> {
            final SeriesResp seriesResp = new SeriesResp();
            final OperationResultInfo infoResultOper = new OperationResultInfo();
            infoResultOper.setCodResultOper(BigInteger.valueOf(2004));
            infoResultOper.setMsgResultOper("success");
            seriesResp.setInfoResultOper(infoResultOper);
            final SeriesInfo info = new SeriesInfo();
            info.setSerie(serie);
            info.setCodValidacaoSerie("AAJFFSPSWJ");
            seriesResp.setInfoSerie(info);
            return seriesResp;
        });

        WebserviceCredentials
            webserviceCredentials = new WebserviceCredentials("599999993/37", "testes1234");
        WebserviceKeys webserviceKeys =
            new WebserviceKeys(Objects.requireNonNull(getClass().getResource("/saPubKey.jks")).toURI(), "saKeyPubPass", "sapubkey.prod");

        SslClientCertificate sslClientCertificate =
            new SslClientCertificate(Objects.requireNonNull(getClass().getResource("/TesteWebservices.pfx")).toURI(), "TESTEwebservice");

        var client = new Client(stubServerUrl, webserviceCredentials, webserviceKeys, sslClientCertificate);

        FinalizeSeriesRequest request = new FinalizeSeriesRequest();
        request.setSerie("SFJSJDFLS");
        request.setClasseDoc(DocumentClass.INVOICING_DOCUMENTS);
        request.setTipoDoc(DocumentType.INVOICE);
        request.setJustificacao("some justification 1");
        request.setCodValidacaoSerie("AAJFFSPSWJ");
        request.setSeqUltimoDocEmitido(BigInteger.TEN);
        final SingleSeriesResponse response = client.finalizeSeries(request);

        Assertions.assertTrue(response.getResultInfo().isSuccess());
        Assertions.assertEquals("SFJSJDFLS", response.getInfoSerie().getSerie());
        Assertions.assertEquals("AAJFFSPSWJ", response.getInfoSerie().getCodValidacaoSerie());

    }

    @Test
    void revokeSeries() throws Exception {

        webService.setRevokeSerieCallback(serie -> {
            final SeriesResp seriesResp = new SeriesResp();
            final OperationResultInfo infoResultOper = new OperationResultInfo();
            infoResultOper.setCodResultOper(BigInteger.valueOf(2003));
            infoResultOper.setMsgResultOper("success");
            seriesResp.setInfoResultOper(infoResultOper);
            final SeriesInfo info = new SeriesInfo();
            info.setSerie(serie);
            info.setCodValidacaoSerie("AAJFFSPSWJ");
            seriesResp.setInfoSerie(info);
            return seriesResp;
        });

        WebserviceCredentials
            webserviceCredentials = new WebserviceCredentials("599999993/37", "testes1234");
        WebserviceKeys webserviceKeys =
            new WebserviceKeys(Objects.requireNonNull(getClass().getResource("/saPubKey.jks")).toURI(), "saKeyPubPass", "sapubkey.prod");

        SslClientCertificate sslClientCertificate =
            new SslClientCertificate(Objects.requireNonNull(getClass().getResource("/TesteWebservices.pfx")).toURI(), "TESTEwebservice");

        var client = new Client(stubServerUrl, webserviceCredentials, webserviceKeys, sslClientCertificate);

        RevokeSeriesRequest request = new RevokeSeriesRequest();
        request.setSerie("SFJSJDFLS");
        request.setClasseDoc(DocumentClass.INVOICING_DOCUMENTS);
        request.setTipoDoc(DocumentType.INVOICE);
        request.setCodValidacaoSerie("AAJFFSPSWJ");
        request.setMotivo(RevokeJustification.REGISTRATION_ERROR);
        request.setDeclaracaoNaoEmissao(true);
        final SingleSeriesResponse response = client.revokeSeries(request);

        Assertions.assertTrue(response.getResultInfo().isSuccess());
        Assertions.assertEquals("SFJSJDFLS", response.getInfoSerie().getSerie());
        Assertions.assertEquals("AAJFFSPSWJ", response.getInfoSerie().getCodValidacaoSerie());

    }

    @Test
    void consultSeries() throws Exception {

        webService.setConsultarSeriesCallback(serie -> {
            final ConsultSeriesResp consultSeriesResp = new ConsultSeriesResp();
            final OperationResultInfo infoResultOper = new OperationResultInfo();
            infoResultOper.setCodResultOper(BigInteger.valueOf(2002));
            infoResultOper.setMsgResultOper("success");
            consultSeriesResp.setInfoResultOper(infoResultOper);
            final SeriesInfo info = new SeriesInfo();
            info.setSerie("A");
            consultSeriesResp.getInfoSerie().add(info);
            return consultSeriesResp;
        });

        WebserviceCredentials
            webserviceCredentials = new WebserviceCredentials("599999993/37", "testes1234");
        WebserviceKeys webserviceKeys =
            new WebserviceKeys(Objects.requireNonNull(getClass().getResource("/saPubKey.jks")).toURI(), "saKeyPubPass", "sapubkey.prod");

        SslClientCertificate sslClientCertificate =
            new SslClientCertificate(Objects.requireNonNull(getClass().getResource("/TesteWebservices.pfx")).toURI(), "TESTEwebservice");

        var client = new Client(stubServerUrl, webserviceCredentials, webserviceKeys, sslClientCertificate);

        ConsultSeriesRequest consultSeriesRequest = new ConsultSeriesRequest();
        consultSeriesRequest.setSerie("A");
        consultSeriesRequest.setMeioProcessamento(SeriesProcessingMedium.COMPUTER_PROGRAM);
        final MultipleSeriesResponse response = client.consultSeries(consultSeriesRequest);

        Assertions.assertTrue(response.getResultInfo().isSuccess());
        Assertions.assertEquals(1, response.getInfoSerie().size());

    }

    private int getFreePort() throws IOException {
        try (ServerSocket s = new ServerSocket(0)) {
            return s.getLocalPort();
        }
    }
}
