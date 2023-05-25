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

import java.time.LocalDate;

public class ConsultSeriesRequest {

    private String serie;
    private SeriesType tipoSerie;
    private DocumentClass classeDoc;
    private DocumentType tipoDoc;
    private String codValidacaoSerie;
    private LocalDate dataRegistoDe;
    private LocalDate dataRegistoAte;
    private String estado;
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

    public String getCodValidacaoSerie() {
        return codValidacaoSerie;
    }

    public void setCodValidacaoSerie(final String codValidacaoSerie) {
        this.codValidacaoSerie = codValidacaoSerie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(final String estado) {
        this.estado = estado;
    }

    public SeriesProcessingMedium getMeioProcessamento() {
        return meioProcessamento;
    }

    public void setMeioProcessamento(final SeriesProcessingMedium meioProcessamento) {
        this.meioProcessamento = meioProcessamento;
    }

    public LocalDate getDataRegistoDe() {
        return dataRegistoDe;
    }

    public void setDataRegistoDe(final LocalDate dataRegistoDe) {
        this.dataRegistoDe = dataRegistoDe;
    }

    public LocalDate getDataRegistoAte() {
        return dataRegistoAte;
    }

    public void setDataRegistoAte(final LocalDate dataRegistoAte) {
        this.dataRegistoAte = dataRegistoAte;
    }

    @Override
    public String toString() {
        return "ConsultSeriesRequest{" + "serie='" + serie + '\'' + ", tipoSerie='" + tipoSerie + '\'' +
            ", classeDoc='" + classeDoc + '\'' + ", tipoDoc='" + tipoDoc + '\'' + ", codValidacaoSerie='" +
            codValidacaoSerie + '\'' + ", dataRegistoDe=" + dataRegistoDe + ", dataRegistoAte=" + dataRegistoAte +
            ", estado='" + estado + '\'' + ", meioProcessamento='" + meioProcessamento + '\'' + '}';
    }


}
