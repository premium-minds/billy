/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.services.documents;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.LockModeType;

import com.premiumminds.billy.core.persistence.dao.AbstractDAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.andorra.persistence.entities.ADGenericInvoiceEntity;
import com.premiumminds.billy.andorra.services.documents.exceptions.InvalidInvoiceDateException;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParams;

public abstract class ADGenericInvoiceIssuingHandler<T extends ADGenericInvoiceEntity, P extends ADIssuingParams>
        implements DocumentIssuingHandler<T, P> {

    protected DAOInvoiceSeries daoInvoiceSeries;

    @Inject
    public ADGenericInvoiceIssuingHandler(DAOInvoiceSeries daoInvoiceSeries) {
        this.daoInvoiceSeries = daoInvoiceSeries;
    }

    protected <D extends AbstractDAOGenericInvoice<T>> T issue(final T document, final ADIssuingParams parametersES,
            final D daoInvoice) throws DocumentIssuingException, DocumentSeriesDoesNotExistException
    {

        String series = parametersES.getInvoiceSeries();

        InvoiceSeriesEntity invoiceSeriesEntity =
                this.getInvoiceSeries(document, series, LockModeType.PESSIMISTIC_WRITE);

        document.initializeEntityDates();

        // If the date is null then the invoice date is the current date
        Date invoiceDate = document.getDate() == null ? new Date() : document.getDate();

        Integer seriesNumber = 1;

        T latestInvoice = daoInvoice.getLatestInvoiceFromSeries(invoiceSeriesEntity.getSeries(),
                document.getBusiness().getUID());

        if (null != latestInvoice) {
            seriesNumber = latestInvoice.getSeriesNumber() + 1;
            Date latestInvoiceDate = latestInvoice.getDate();

            if (latestInvoiceDate.compareTo(invoiceDate) > 0) {
                throw new InvalidInvoiceDateException();
            }
        }

        String formatedNumber = parametersES.getInvoiceSeries() + "/" + seriesNumber;

        ZoneId timezone = document.getBusiness().getTimezone();
        LocalDate issueLocalDate = document.getLocalDate().orElse(LocalDate.ofInstant(invoiceDate.toInstant(), timezone));

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
