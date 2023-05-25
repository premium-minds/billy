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
import java.time.LocalDateTime;

public class SeriesInfo {

    private String serie;
    private String tipoSerie;
    private String classeDoc;
    private String tipoDoc;
    private BigInteger numInicialSeq;
    private BigInteger numFinalSeq;
    private LocalDate dataInicioPrevUtiliz;
    private BigInteger seqUltimoDocEmitido;
    private String meioProcessamento;
    private BigInteger numCertSWFatur;
    private String codValidacaoSerie;
    private LocalDate dataRegisto;
    private String estado;
    private String motivoEstado;
    private String justificacao;
    private LocalDateTime dataEstado;
    private String nifComunicou;

    public String getSerie() {
        return serie;
    }

    public void setSerie(final String serie) {
        this.serie = serie;
    }

    public String getTipoSerie() {
        return tipoSerie;
    }

    public void setTipoSerie(final String tipoSerie) {
        this.tipoSerie = tipoSerie;
    }

    public String getClasseDoc() {
        return classeDoc;
    }

    public void setClasseDoc(final String classeDoc) {
        this.classeDoc = classeDoc;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(final String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public BigInteger getNumInicialSeq() {
        return numInicialSeq;
    }

    public void setNumInicialSeq(final BigInteger numInicialSeq) {
        this.numInicialSeq = numInicialSeq;
    }

    public BigInteger getNumFinalSeq() {
        return numFinalSeq;
    }

    public void setNumFinalSeq(final BigInteger numFinalSeq) {
        this.numFinalSeq = numFinalSeq;
    }

    public BigInteger getSeqUltimoDocEmitido() {
        return seqUltimoDocEmitido;
    }

    public void setSeqUltimoDocEmitido(final BigInteger seqUltimoDocEmitido) {
        this.seqUltimoDocEmitido = seqUltimoDocEmitido;
    }

    public String getMeioProcessamento() {
        return meioProcessamento;
    }

    public void setMeioProcessamento(final String meioProcessamento) {
        this.meioProcessamento = meioProcessamento;
    }

    public BigInteger getNumCertSWFatur() {
        return numCertSWFatur;
    }

    public void setNumCertSWFatur(final BigInteger numCertSWFatur) {
        this.numCertSWFatur = numCertSWFatur;
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

    public String getMotivoEstado() {
        return motivoEstado;
    }

    public void setMotivoEstado(final String motivoEstado) {
        this.motivoEstado = motivoEstado;
    }

    public String getJustificacao() {
        return justificacao;
    }

    public void setJustificacao(final String justificacao) {
        this.justificacao = justificacao;
    }

    public String getNifComunicou() {
        return nifComunicou;
    }

    public void setNifComunicou(final String nifComunicou) {
        this.nifComunicou = nifComunicou;
    }

    public LocalDate getDataInicioPrevUtiliz() {
        return dataInicioPrevUtiliz;
    }

    public void setDataInicioPrevUtiliz(final LocalDate dataInicioPrevUtiliz) {
        this.dataInicioPrevUtiliz = dataInicioPrevUtiliz;
    }

    public LocalDate getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(final LocalDate dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public LocalDateTime getDataEstado() {
        return dataEstado;
    }

    public void setDataEstado(final LocalDateTime dataEstado) {
        this.dataEstado = dataEstado;
    }

    @Override
    public String toString() {
        return "SeriesInfo{" + "serie='" + serie + '\'' + ", tipoSerie='" + tipoSerie + '\'' + ", classeDoc='" +
            classeDoc + '\'' + ", tipoDoc='" + tipoDoc + '\'' + ", numInicialSeq=" + numInicialSeq + ", numFinalSeq=" +
            numFinalSeq + ", dataInicioPrevUtiliz=" + dataInicioPrevUtiliz + ", seqUltimoDocEmitido=" +
            seqUltimoDocEmitido + ", meioProcessamento='" + meioProcessamento + '\'' + ", numCertSWFatur=" +
            numCertSWFatur + ", codValidacaoSerie='" + codValidacaoSerie + '\'' + ", dataRegisto=" + dataRegisto +
            ", estado='" + estado + '\'' + ", motivoEstado='" + motivoEstado + '\'' + ", justificacao='" +
            justificacao + '\'' + ", dataEstado=" + dataEstado + ", nifComunicou='" + nifComunicou + '\'' + '}';
    }
}
