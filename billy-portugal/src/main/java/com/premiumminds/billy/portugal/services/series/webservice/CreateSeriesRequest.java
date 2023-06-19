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

import java.math.BigInteger;
import java.time.LocalDate;

public class CreateSeriesRequest {

    private String serie;
    private SeriesType tipoSerie;
    private DocumentClass classeDoc;
    private DocumentType tipoDoc;
    private BigInteger numInicialSeq;
    private LocalDate dataInicioPrevUtiliz;
    private String numCertSWFatur;
    private SeriesProcessingMedium meioProcessamento;

    public String getSerie() {
        return serie;
    }

    public void setSerie(final String serie) {
        this.serie = serie;
    }

    public SeriesType getTipoSerie() {
        return tipoSerie;
    }

    public void setTipoSerie(final SeriesType tipoSerie) {
        this.tipoSerie = tipoSerie;
    }

    public DocumentClass getClasseDoc() {
        return classeDoc;
    }

    public void setClasseDoc(final DocumentClass classeDoc) {
        this.classeDoc = classeDoc;
    }

    public DocumentType getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(final DocumentType tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public BigInteger getNumInicialSeq() {
        return numInicialSeq;
    }

    public void setNumInicialSeq(final BigInteger numInicialSeq) {
        this.numInicialSeq = numInicialSeq;
    }

    public LocalDate getDataInicioPrevUtiliz() {
        return dataInicioPrevUtiliz;
    }

    public void setDataInicioPrevUtiliz(final LocalDate dataInicioPrevUtiliz) {
        this.dataInicioPrevUtiliz = dataInicioPrevUtiliz;
    }

    public String getNumCertSWFatur() {
        return numCertSWFatur;
    }

    public void setNumCertSWFatur(final String numCertSWFatur) {
        this.numCertSWFatur = numCertSWFatur;
    }

    public SeriesProcessingMedium getMeioProcessamento() {
        return meioProcessamento;
    }

    public void setMeioProcessamento(final SeriesProcessingMedium meioProcessamento) {
        this.meioProcessamento = meioProcessamento;
    }

    @Override
    public String toString() {
        return "CreateSeriesRequest{" + "serie='" + serie + '\'' + ", tipoSerie='" + tipoSerie + '\'' +
            ", classeDoc='" + classeDoc + '\'' + ", tipoDoc='" + tipoDoc + '\'' + ", numInicialSeq=" + numInicialSeq +
            ", dataInicioPrevUtiliz=" + dataInicioPrevUtiliz + ", numCertSWFatur='" + numCertSWFatur + '\'' +
            ", meioProcessamento='" + meioProcessamento + '\'' + '}';
    }
}
