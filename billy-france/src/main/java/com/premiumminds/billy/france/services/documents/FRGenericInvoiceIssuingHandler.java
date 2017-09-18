/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.services.documents;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.LockModeType;

import com.premiumminds.billy.core.persistence.dao.AbstractDAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAInvoiceSeriesEntity;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.france.persistence.entities.FRGenericInvoiceEntity;
import com.premiumminds.billy.france.services.documents.exceptions.InvalidInvoiceDateException;
import com.premiumminds.billy.france.services.documents.util.FRIssuingParams;

public abstract class FRGenericInvoiceIssuingHandler<T extends FRGenericInvoiceEntity, P extends FRIssuingParams>
        implements DocumentIssuingHandler<T, P> {

    protected DAOInvoiceSeries daoInvoiceSeries;

    @Inject
    public FRGenericInvoiceIssuingHandler(DAOInvoiceSeries daoInvoiceSeries) {
        this.daoInvoiceSeries = daoInvoiceSeries;
    }

    protected <D extends AbstractDAOGenericInvoice<T>> T issue(final T document, final FRIssuingParams parametersFR,
            final D daoInvoice) throws DocumentIssuingException {

        String series = parametersFR.getInvoiceSeries();

        InvoiceSeriesEntity invoiceSeriesEntity =
                this.getInvoiceSeries(document, series, LockModeType.PESSIMISTIC_WRITE);

        document.initializeEntityDates();

        // If the date is null then the invoice date is the current date
        Date invoiceDate = document.getDate() == null ? new Date() : document.getDate();

        Integer seriesNumber = 1;

        T latestInvoice = daoInvoice.getLatestInvoiceFromSeries(invoiceSeriesEntity.getSeries(),
                document.getBusiness().getUID().toString());

        if (null != latestInvoice) {
            seriesNumber = latestInvoice.getSeriesNumber() + 1;
            Date latestInvoiceDate = latestInvoice.getDate();

            if (latestInvoiceDate.compareTo(invoiceDate) > 0) {
                throw new InvalidInvoiceDateException();
            }
        }

        String formatedNumber = parametersFR.getInvoiceSeries() + "/" + seriesNumber;

        document.setDate(invoiceDate);
        document.setNumber(formatedNumber);
        document.setSeries(invoiceSeriesEntity.getSeries());
        document.setSeriesNumber(seriesNumber);
        document.setBilled(false);
        document.setCancelled(false);
        document.setEACCode(parametersFR.getEACCode());
        document.setCurrency(document.getCurrency());

        daoInvoice.create(document);

        return document;
    }

    private InvoiceSeriesEntity getInvoiceSeries(final T document, String series, LockModeType lockMode) {
        InvoiceSeriesEntity invoiceSeriesEntity =
                this.daoInvoiceSeries.getSeries(series, document.getBusiness().getUID().toString(), lockMode);

        if (null == invoiceSeriesEntity) {
            InvoiceSeriesEntity entity = new JPAInvoiceSeriesEntity();
            entity.setBusiness(document.getBusiness());
            entity.setSeries(series);

            invoiceSeriesEntity = this.daoInvoiceSeries.create(entity);
        }
        return invoiceSeriesEntity;
    }
}
