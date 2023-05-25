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

public class FinalizeSeriesRequest {

    private String serie;
    private DocumentClass classeDoc;
    private DocumentType tipoDoc;
    private String codValidacaoSerie;
    private BigInteger seqUltimoDocEmitido;
    private String justificacao;

    public String getSerie() {
        return serie;
    }

    public void setSerie(final String serie) {
        this.serie = serie;
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

    public BigInteger getSeqUltimoDocEmitido() {
        return seqUltimoDocEmitido;
    }

    public void setSeqUltimoDocEmitido(final BigInteger seqUltimoDocEmitido) {
        this.seqUltimoDocEmitido = seqUltimoDocEmitido;
    }

    public String getJustificacao() {
        return justificacao;
    }

    public void setJustificacao(final String justificacao) {
        this.justificacao = justificacao;
    }

    @Override
    public String toString() {
        return "FinalizeSeriesRequest{" + "serie='" + serie + '\'' + ", classeDoc=" + classeDoc + ", tipoDoc=" +
            tipoDoc + ", codValidacaoSerie='" + codValidacaoSerie + '\'' + ", seqUltimoDocEmitido=" +
            seqUltimoDocEmitido + ", justificacao='" + justificacao + '\'' + '}';
    }
}
