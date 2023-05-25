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
import com.premiumminds.billy.portugal.webservices.series.ObjectFactory;
import com.premiumminds.billy.portugal.webservices.series.SeriesResp;
import java.math.BigInteger;
import java.util.function.Function;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "SeriesWS", targetNamespace = "http://at.gov.pt/")
@XmlSeeAlso({
    ObjectFactory.class
})
public class StubSeriesWS {

    private Function<String,SeriesResp> registarSerieCallback;
    private Function<String,SeriesResp> finalizeSerieCallback;
    private Function<String,SeriesResp> revokeSerieCallback;
    private Function<String,ConsultSeriesResp> consultarSeriesCallback;

    @WebMethod
    @WebResult(name = "registarSerieResp", targetNamespace = "")
    @RequestWrapper(localName = "registarSerie", targetNamespace = "http://at.gov.pt/", className = "com.premiumminds.billy.portugal.webservices.series.RegistarSerie")
    @ResponseWrapper(localName = "registarSerieResponse", targetNamespace = "http://at.gov.pt/", className = "com.premiumminds.billy.portugal.webservices.series.RegistarSerieResponse")
    @Action(input = "http://at.gov.pt/SeriesWS/registarSerieRequest", output = "http://at.gov.pt/SeriesWS/registarSerieResponse")
    public SeriesResp registarSerie(
        @WebParam(name = "serie", targetNamespace = "")
        String serie,
        @WebParam(name = "tipoSerie", targetNamespace = "")
        String tipoSerie,
        @WebParam(name = "classeDoc", targetNamespace = "")
        String classeDoc,
        @WebParam(name = "tipoDoc", targetNamespace = "")
        String tipoDoc,
        @WebParam(name = "numInicialSeq", targetNamespace = "") BigInteger numInicialSeq,
        @WebParam(name = "dataInicioPrevUtiliz", targetNamespace = "")
        XMLGregorianCalendar dataInicioPrevUtiliz,
        @WebParam(name = "numCertSWFatur", targetNamespace = "")
        BigInteger numCertSWFatur,
        @WebParam(name = "meioProcessamento", targetNamespace = "")
        String meioProcessamento){

        return registarSerieCallback.apply(serie);
    }

    @WebMethod
    @WebResult(name = "finalizarSerieResp", targetNamespace = "")
    @RequestWrapper(localName = "finalizarSerie", targetNamespace = "http://at.gov.pt/", className = "com.premiumminds.billy.portugal.webservices.series.FinalizarSerie")
    @ResponseWrapper(localName = "finalizarSerieResponse", targetNamespace = "http://at.gov.pt/", className = "com.premiumminds.billy.portugal.webservices.series.FinalizarSerieResponse")
    @Action(input = "http://at.gov.pt/SeriesWS/finalizarSerieRequest", output = "http://at.gov.pt/SeriesWS/finalizarSerieResponse")
    public SeriesResp finalizarSerie(
        @WebParam(name = "serie", targetNamespace = "")
        String serie,
        @WebParam(name = "classeDoc", targetNamespace = "")
        String classeDoc,
        @WebParam(name = "tipoDoc", targetNamespace = "")
        String tipoDoc,
        @WebParam(name = "codValidacaoSerie", targetNamespace = "")
        String codValidacaoSerie,
        @WebParam(name = "seqUltimoDocEmitido", targetNamespace = "")
        BigInteger seqUltimoDocEmitido,
        @WebParam(name = "justificacao", targetNamespace = "")
        String justificacao){

        return finalizeSerieCallback.apply(serie);
    }

    @WebMethod
    @WebResult(name = "anularSerieResp", targetNamespace = "")
    @RequestWrapper(localName = "anularSerie", targetNamespace = "http://at.gov.pt/", className = "com.premiumminds.billy.portugal.webservices.series.AnularSerie")
    @ResponseWrapper(localName = "anularSerieResponse", targetNamespace = "http://at.gov.pt/", className = "com.premiumminds.billy.portugal.webservices.series.AnularSerieResponse")
    @Action(input = "http://at.gov.pt/SeriesWS/anularSerieRequest", output = "http://at.gov.pt/SeriesWS/anularSerieResponse")
    public SeriesResp anularSerie(
        @WebParam(name = "serie", targetNamespace = "")
        String serie,
        @WebParam(name = "classeDoc", targetNamespace = "")
        String classeDoc,
        @WebParam(name = "tipoDoc", targetNamespace = "")
        String tipoDoc,
        @WebParam(name = "codValidacaoSerie", targetNamespace = "")
        String codValidacaoSerie,
        @WebParam(name = "motivo", targetNamespace = "")
        String motivo,
        @WebParam(name = "declaracaoNaoEmissao", targetNamespace = "")
        boolean declaracaoNaoEmissao){

        return revokeSerieCallback.apply(serie);
    }
    @WebMethod
    @WebResult(name = "consultarSeriesResp", targetNamespace = "")
    @RequestWrapper(localName = "consultarSeries", targetNamespace = "http://at.gov.pt/", className = "com.premiumminds.billy.portugal.webservices.series.ConsultarSeries")
    @ResponseWrapper(localName = "consultarSeriesResponse", targetNamespace = "http://at.gov.pt/", className = "com.premiumminds.billy.portugal.webservices.series.ConsultarSeriesResponse")
    @Action(input = "http://at.gov.pt/SeriesWS/consultarSeriesRequest", output = "http://at.gov.pt/SeriesWS/consultarSeriesResponse")
    public ConsultSeriesResp consultarSeries(
        @WebParam(name = "serie", targetNamespace = "")
        String serie,
        @WebParam(name = "tipoSerie", targetNamespace = "")
        String tipoSerie,
        @WebParam(name = "classeDoc", targetNamespace = "")
        String classeDoc,
        @WebParam(name = "tipoDoc", targetNamespace = "")
        String tipoDoc,
        @WebParam(name = "codValidacaoSerie", targetNamespace = "")
        String codValidacaoSerie,
        @WebParam(name = "dataRegistoDe", targetNamespace = "") XMLGregorianCalendar dataRegistoDe,
        @WebParam(name = "dataRegistoAte", targetNamespace = "")
        XMLGregorianCalendar dataRegistoAte,
        @WebParam(name = "estado", targetNamespace = "")
        String estado,
        @WebParam(name = "meioProcessamento", targetNamespace = "")
        String meioProcessamento){

        return consultarSeriesCallback.apply(serie);
    }

    void setRegistarSerieCallback(final Function<String, SeriesResp> registarSerieCallback) {
        this.registarSerieCallback = registarSerieCallback;
    }
    void setFinalizeSerieCallback(final Function<String, SeriesResp> finalizeSerieCallback) {
        this.finalizeSerieCallback = finalizeSerieCallback;
    }

    void setRevokeSerieCallback(final Function<String, SeriesResp> revokeSerieCallback) {
        this.revokeSerieCallback = revokeSerieCallback;
    }

    void setConsultarSeriesCallback(final Function<String, ConsultSeriesResp> consultarSeriesCallback) {
        this.consultarSeriesCallback = consultarSeriesCallback;
    }
}
