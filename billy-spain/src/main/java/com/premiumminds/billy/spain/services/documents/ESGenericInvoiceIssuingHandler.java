/**
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

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.LockModeType;

import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.persistence.entities.BaseEntity;
import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAInvoiceSeriesEntity;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.documents.IssuingParams;
import com.premiumminds.billy.core.services.documents.impl.DocumentIssuingHandlerImpl;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.spain.persistence.entities.ESGenericInvoiceEntity;
import com.premiumminds.billy.spain.services.documents.exceptions.InvalidInvoiceDateException;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;

public abstract class ESGenericInvoiceIssuingHandler extends DocumentIssuingHandlerImpl
    implements DocumentIssuingHandler {

  protected DAOInvoiceSeries daoInvoiceSeries;

  @Inject
  public ESGenericInvoiceIssuingHandler(DAOInvoiceSeries daoInvoiceSeries) {
    this.daoInvoiceSeries = daoInvoiceSeries;
  }

  @Override
  public abstract <T extends GenericInvoice, P extends IssuingParams> T issue(T document,
      P parameters) throws DocumentIssuingException;

  protected <T extends GenericInvoice, D extends DAOGenericInvoice> T issue(final T document,
      final ESIssuingParams parametersES, final D daoInvoice) throws DocumentIssuingException {

    String series = parametersES.getInvoiceSeries();

    InvoiceSeriesEntity invoiceSeriesEntity = getInvoiceSeries(document, series,
        LockModeType.PESSIMISTIC_WRITE);

    ESGenericInvoiceEntity documentEntity = (ESGenericInvoiceEntity) document;

    ((BaseEntity) document).initializeEntityDates();

    // If the date is null then the invoice date is the current date
    Date invoiceDate = document.getDate() == null ? new Date() : document.getDate();

    // if (systemDate..after(invoiceDate)) {
    // throw new InvalidInvoiceDateException();
    // }

    Integer seriesNumber = 1;

    ESGenericInvoiceEntity latestInvoice = daoInvoice.getLatestInvoiceFromSeries(
        invoiceSeriesEntity.getSeries(), document.getBusiness().getUID().toString());

    if (null != latestInvoice) {
      seriesNumber = latestInvoice.getSeriesNumber() + 1;
      Date latestInvoiceDate = latestInvoice.getDate();

      if (latestInvoiceDate.compareTo(invoiceDate) > 0) {
        throw new InvalidInvoiceDateException();
      }
    }

    String formatedNumber = parametersES.getInvoiceSeries() + "/" + seriesNumber;

    documentEntity.setDate(invoiceDate);
    documentEntity.setNumber(formatedNumber);
    documentEntity.setSeries(invoiceSeriesEntity.getSeries());
    documentEntity.setSeriesNumber(seriesNumber);
    documentEntity.setBilled(false);
    documentEntity.setCancelled(false);
    documentEntity.setEACCode(parametersES.getEACCode());
    documentEntity.setCurrency(document.getCurrency());

    daoInvoice.create(documentEntity);

    return (T) documentEntity;
  }

  private <T extends GenericInvoice> InvoiceSeriesEntity getInvoiceSeries(final T document,
      String series, LockModeType lockMode) {
    InvoiceSeriesEntity invoiceSeriesEntity = daoInvoiceSeries.getSeries(series,
        document.getBusiness().getUID().toString(), lockMode);

    if (null == invoiceSeriesEntity) {
      InvoiceSeriesEntity entity = new JPAInvoiceSeriesEntity();
      entity.setBusiness(document.getBusiness());
      entity.setSeries(series);

      invoiceSeriesEntity = daoInvoiceSeries.create(entity);
    }
    return invoiceSeriesEntity;
  }
}
