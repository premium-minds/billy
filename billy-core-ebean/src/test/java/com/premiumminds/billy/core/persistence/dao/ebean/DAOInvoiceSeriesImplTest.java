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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.JPABusinessEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.JPAInvoiceSeriesEntity;
import com.premiumminds.billy.core.services.UID;

public class DAOInvoiceSeriesImplTest extends BaseH2Test {

    private static String rightSeries = "Right Series";

    private static String wrongSeries = "Wrong Series";

    private static UID rightBusinessUid = new UID("f01970a9-c004-4f29-a3e1-bf2183248d76");

    private static UID wrongBusinessUid = new UID("cde197fd-a866-4959-a2bc-6750360947d4");

    private static UID invoiceSeriesUid = new UID("1796dc4d-462c-468c-9f0f-170b65944341");

    private static DAOInvoiceSeriesImpl daoInvoiceSeriesImpl;

    @Before
    public void prepare() {
        DAOBusinessImpl businessDAO = new DAOBusinessImpl();
        businessDAO.beginTransaction();
        JPABusinessEntity business = new JPABusinessEntity();
        business.setUID(DAOInvoiceSeriesImplTest.rightBusinessUid);
        business.setName("Test Business");
        businessDAO.create(business);
        businessDAO.commit();

        DAOInvoiceSeriesImplTest.daoInvoiceSeriesImpl = new DAOInvoiceSeriesImpl();
        DAOInvoiceSeriesImplTest.daoInvoiceSeriesImpl.beginTransaction();
        JPAInvoiceSeriesEntity series = new JPAInvoiceSeriesEntity();
        series.setUID(DAOInvoiceSeriesImplTest.invoiceSeriesUid);
        series.setBusiness(business);
        series.setSeries(DAOInvoiceSeriesImplTest.rightSeries);
        DAOInvoiceSeriesImplTest.daoInvoiceSeriesImpl.create(series);
        DAOInvoiceSeriesImplTest.daoInvoiceSeriesImpl.commit();
    }

    @Test
    public void getSeries() {
        InvoiceSeriesEntity series = DAOInvoiceSeriesImplTest.daoInvoiceSeriesImpl.getSeries(
                DAOInvoiceSeriesImplTest.rightSeries, DAOInvoiceSeriesImplTest.rightBusinessUid.toString(), null);

        Assert.assertEquals(series.getUID(), DAOInvoiceSeriesImplTest.invoiceSeriesUid);
    }

    @Test
    public void getSeries_wrongSeriesId() {
        InvoiceSeriesEntity series = DAOInvoiceSeriesImplTest.daoInvoiceSeriesImpl.getSeries(
                DAOInvoiceSeriesImplTest.wrongSeries, DAOInvoiceSeriesImplTest.rightBusinessUid.toString(), null);

        Assert.assertEquals(series, null);
    }

    @Test
    public void getSeries_wrongBusinessId() {
        InvoiceSeriesEntity series = DAOInvoiceSeriesImplTest.daoInvoiceSeriesImpl.getSeries(
                DAOInvoiceSeriesImplTest.rightSeries, DAOInvoiceSeriesImplTest.wrongBusinessUid.toString(), null);

        Assert.assertEquals(series, null);
    }

    @Test
    public void getSeries_concurrent_deadlock() {
        DAOInvoiceSeriesImplTest.daoInvoiceSeriesImpl.beginTransaction();
        InvoiceSeriesEntity series =
                DAOInvoiceSeriesImplTest.daoInvoiceSeriesImpl.getSeries(DAOInvoiceSeriesImplTest.rightSeries,
                        DAOInvoiceSeriesImplTest.rightBusinessUid.toString(), LockModeType.PESSIMISTIC_WRITE);

        long threadTimeout = 1000;
        long millis = System.currentTimeMillis();
        Thread concurrentThread = new Thread() {

            @Override
            public void run() {
                DAOInvoiceSeriesImplTest.daoInvoiceSeriesImpl.beginTransaction();
                InvoiceSeriesEntity series =
                        DAOInvoiceSeriesImplTest.daoInvoiceSeriesImpl.getSeries(DAOInvoiceSeriesImplTest.rightSeries,
                                DAOInvoiceSeriesImplTest.rightBusinessUid.toString(), LockModeType.PESSIMISTIC_WRITE);
                DAOInvoiceSeriesImplTest.daoInvoiceSeriesImpl.commit();
            }
        };
        concurrentThread.start();
        try {
            concurrentThread.join(threadTimeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long diff = System.currentTimeMillis() - millis;

        DAOInvoiceSeriesImplTest.daoInvoiceSeriesImpl.commit();

        Assert.assertEquals(diff >= threadTimeout, true);
    }
}
