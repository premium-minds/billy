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
package com.premiumminds.billy.portugal.services.documents;

import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.LockModeType;

import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.dao.AbstractDAOPTGenericInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.exceptions.InvalidInvoiceDateException;
import com.premiumminds.billy.portugal.services.documents.exceptions.InvalidInvoiceTypeException;
import com.premiumminds.billy.portugal.services.documents.exceptions.InvalidSourceBillingException;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.util.GenerateHash;

public abstract class PTGenericInvoiceIssuingHandler<T extends PTGenericInvoiceEntity, P extends PTIssuingParams>
        implements DocumentIssuingHandler<T, P> {

    protected DAOInvoiceSeries daoInvoiceSeries;

    @Inject
    public PTGenericInvoiceIssuingHandler(DAOInvoiceSeries daoInvoiceSeries) {
        this.daoInvoiceSeries = daoInvoiceSeries;
    }

    protected void validateDocumentType(TYPE documentType, TYPE expectedType, String series)
            throws InvalidInvoiceTypeException {
        if (documentType != expectedType) {
            throw new InvalidInvoiceTypeException(series, documentType.toString(), expectedType.toString());
        }
    }

    protected <D extends AbstractDAOPTGenericInvoice<T>> T issue(final T document, final PTIssuingParams parametersPT,
            final D daoInvoice, final TYPE invoiceType) throws DocumentIssuingException, DocumentSeriesDoesNotExistException, SeriesUniqueCodeNotFilled {

        String series = parametersPT.getInvoiceSeries();

        validateSeriesHasNoWhiteSpaces(series);

        InvoiceSeriesEntity invoiceSeriesEntity =
                this.getInvoiceSeries(document, series, LockModeType.PESSIMISTIC_WRITE);

        SourceBilling sourceBilling = ((PTGenericInvoice) document).getSourceBilling();

        document.initializeEntityDates();

        // If the date is null then the invoice date is the current date
        Date invoiceDate = document.getDate() == null ? new Date() : document.getDate();
        Date systemDate = document.getCreateTimestamp();

        final Integer seriesNumber;
        String previousHash = null;

        T latestInvoice = daoInvoice.getLatestInvoiceFromSeries(invoiceSeriesEntity.getSeries(),
                document.getBusiness().getUID().toString());

        if (null != latestInvoice) {
            seriesNumber = latestInvoice.getSeriesNumber() + 1;
            previousHash = latestInvoice.getHash();
            Date latestInvoiceDate = latestInvoice.getDate();

            this.validateDocumentType(invoiceType, latestInvoice.getType(), invoiceSeriesEntity.getSeries());

            if (!latestInvoice.getSourceBilling().equals(sourceBilling)) {
                throw new InvalidSourceBillingException(invoiceSeriesEntity.getSeries(), sourceBilling.toString(),
                        latestInvoice.getSourceBilling().toString());
            }

            if (latestInvoiceDate.compareTo(invoiceDate) > 0) {
                throw new InvalidInvoiceDateException();
            }
        } else {
            seriesNumber = 1;
        }

        String formattedNumber = invoiceType.toString() + " " + parametersPT.getInvoiceSeries() + "/" + seriesNumber;

        validatePTInvoiceNumber(formattedNumber);

        String newHash =
                GenerateHash.generateHash(parametersPT.getPrivateKey(), parametersPT.getPublicKey(), invoiceDate,
                        systemDate, formattedNumber, document.getAmountWithTax(), previousHash);

        String sourceHash =
                GenerateHash.generateSourceHash(invoiceDate, systemDate, formattedNumber, document.getAmountWithTax(),
                        previousHash);

        if (invoiceSeriesEntity.getSeriesUniqueCode().isPresent()) {
            validateSeriesUniqueCode(invoiceSeriesEntity.getSeriesUniqueCode().get());
        }

        final String atcud = invoiceSeriesEntity
            .getSeriesUniqueCode()
            .map(s -> new StringBuilder()
                .append(s)
                .append("-")
                .append(seriesNumber))
            .orElseThrow(() -> new SeriesUniqueCodeNotFilled("The series " + invoiceSeriesEntity.getSeries()
                                                                + " does not have a series unique code specified"))
            .toString();

        document.setDate(invoiceDate);
        document.setNumber(formattedNumber);
        document.setSeries(invoiceSeriesEntity.getSeries());
        document.setSeriesNumber(seriesNumber);
        document.setHash(newHash);
        document.setBilled(false);
        document.setCancelled(false);
        document.setType(invoiceType);
        document.setSourceHash(sourceHash);
        document.setHashControl(parametersPT.getPrivateKeyVersion());
        document.setEACCode(parametersPT.getEACCode());
        document.setCurrency(document.getCurrency());
        document.setATCUD(atcud);

        daoInvoice.create(document);

        return document;

    }

    private void validatePTInvoiceNumber(final String formattedNumber) throws DocumentIssuingException {
        try {
            BillyValidator.matchesPattern(formattedNumber, "([a-zA-Z0-9./_-])+ ([a-zA-Z0-9]*/[0-9]+)",
                    "field.documentNumber");
        } catch (IllegalArgumentException e) {
            throw new DocumentIssuingException(e);
        }
    }

    private void validateSeriesHasNoWhiteSpaces(final String series) throws DocumentIssuingException {
        try {
            BillyValidator.matchesPattern(series, "[a-zA-Z0-9]*", "field.documentSeries");
        } catch (IllegalArgumentException e) {
            throw new DocumentIssuingException(e);
        }
    }

    private void validateSeriesUniqueCode(final String seriesUniqueCode) throws DocumentIssuingException {
        try {
            BillyValidator.matchesPattern(seriesUniqueCode, "[A-Za-z0-9]{8,}", "field.seriesUniqueCode");
        } catch (IllegalArgumentException e) {
            throw new DocumentIssuingException(e);
        }
    }

    private InvoiceSeriesEntity getInvoiceSeries(final T document, String series, LockModeType lockMode)
            throws DocumentSeriesDoesNotExistException {
        InvoiceSeriesEntity invoiceSeriesEntity =
                this.daoInvoiceSeries.getSeries(series, document.getBusiness().getUID().toString(), lockMode);

        if (null == invoiceSeriesEntity) {
            throw new DocumentSeriesDoesNotExistException(
                    "Requested to issue an invoice with series " + series + " but series does not exist");
        }
        return invoiceSeriesEntity;
    }
}
