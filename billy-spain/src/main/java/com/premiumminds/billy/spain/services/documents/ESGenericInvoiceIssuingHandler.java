/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.services.documents;

import com.premiumminds.billy.core.persistence.dao.AbstractDAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.spain.persistence.entities.ESGenericInvoiceEntity;
import com.premiumminds.billy.spain.services.documents.exceptions.InvalidInvoiceDateException;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import jakarta.inject.Inject;
import jakarta.persistence.LockModeType;

public abstract class ESGenericInvoiceIssuingHandler<T extends ESGenericInvoiceEntity, P extends ESIssuingParams>
        implements DocumentIssuingHandler<T, P> {

    protected DAOInvoiceSeries daoInvoiceSeries;

    @Inject
    public ESGenericInvoiceIssuingHandler(DAOInvoiceSeries daoInvoiceSeries) {
        this.daoInvoiceSeries = daoInvoiceSeries;
    }

    protected <D extends AbstractDAOGenericInvoice<T>> T issue(final T document, final ESIssuingParams parametersES,
            final D daoInvoice) throws DocumentIssuingException, DocumentSeriesDoesNotExistException
    {

        String series = parametersES.getInvoiceSeries();

        InvoiceSeriesEntity invoiceSeriesEntity =
                this.getInvoiceSeries(document, series, LockModeType.PESSIMISTIC_WRITE);

        document.initializeEntityDates();

        // If the date is null then the invoice date is the current date
        Date invoiceDate = document.getDate() == null ? new Date() : document.getDate();
        ZoneId timezone = document.getBusiness().getTimezone();
        LocalDate issueLocalDate = document.getLocalDate() == null ?
            LocalDate.ofInstant(invoiceDate.toInstant(), timezone) :
            document.getLocalDate();

        Integer seriesNumber = 1;

        T latestInvoice = daoInvoice.getLatestInvoiceFromSeries(invoiceSeriesEntity.getSeries(),
                document.getBusiness().getUID());

        if (null != latestInvoice) {
            seriesNumber = latestInvoice.getSeriesNumber() + 1;

            final LocalDate latestLocalDate = Optional
                .ofNullable(latestInvoice.getLocalDate())
                .orElseGet(() -> latestInvoice
                    .getDate()
                    .toInstant()
                    .atZone(latestInvoice.getBusiness().getTimezone())
                    .toLocalDate());
            if (latestLocalDate.isAfter(issueLocalDate)) {
                throw new InvalidInvoiceDateException();
            }
        }

        String formatedNumber = parametersES.getInvoiceSeries() + "/" + seriesNumber;

        document.setDate(invoiceDate);
        document.setNumber(formatedNumber);
        document.setSeries(invoiceSeriesEntity.getSeries());
        document.setSeriesNumber(seriesNumber);
        document.setBilled(false);
        document.setCancelled(false);
        document.setEACCode(parametersES.getEACCode());
        document.setCurrency(document.getCurrency());
        document.setLocalDate(issueLocalDate);

        daoInvoice.create(document);

        return document;
    }

    private InvoiceSeriesEntity getInvoiceSeries(final T document, String series, LockModeType lockMode)
        throws DocumentSeriesDoesNotExistException
    {
        InvoiceSeriesEntity invoiceSeriesEntity =
                this.daoInvoiceSeries.getSeries(series, document.getBusiness().getUID().toString(), lockMode);

        if (null == invoiceSeriesEntity) {
            throw new DocumentSeriesDoesNotExistException(series);
        }
        return invoiceSeriesEntity;
    }
}
