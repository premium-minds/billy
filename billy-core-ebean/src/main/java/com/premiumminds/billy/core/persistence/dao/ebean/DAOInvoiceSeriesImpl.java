/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core Ebean.
 *
 * billy core Ebean is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core Ebean is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core Ebean. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.persistence.dao.ebean;

import javax.persistence.LockModeType;

import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.JPAInvoiceSeriesEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.query.QJPAInvoiceSeriesEntity;

public class DAOInvoiceSeriesImpl extends AbstractDAO<InvoiceSeriesEntity, JPAInvoiceSeriesEntity>
        implements DAOInvoiceSeries {

    @Override
    public InvoiceSeriesEntity getEntityInstance() {
        return new JPAInvoiceSeriesEntity();
    }

    @Override
    protected Class<? extends JPAInvoiceSeriesEntity> getEntityClass() {
        return JPAInvoiceSeriesEntity.class;
    }

    @Override
    public InvoiceSeriesEntity getSeries(String series, String businessUID, LockModeType lockMode) {
        QJPAInvoiceSeriesEntity querySeries = this.queryInvoiceSeries(series, businessUID);
        if (lockMode == LockModeType.WRITE || lockMode == LockModeType.PESSIMISTIC_WRITE) {
            querySeries = querySeries.forUpdate();
        }
        return querySeries.findOne();
    }

    private QJPAInvoiceSeriesEntity queryInvoiceSeries(String series, String businessUID) {
        return new QJPAInvoiceSeriesEntity().series.eq(series).business.uid.eq(businessUID);
    }
}
