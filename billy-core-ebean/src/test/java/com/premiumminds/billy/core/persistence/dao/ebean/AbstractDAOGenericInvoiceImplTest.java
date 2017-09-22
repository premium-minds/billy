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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.JPABusinessEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.JPAGenericInvoiceEntity;
import com.premiumminds.billy.core.services.UID;

import io.ebean.Ebean;

public class AbstractDAOGenericInvoiceImplTest extends BaseH2Test {

    private static UID olderInvoiceUid = new UID("1796dc4d-462c-468c-9f0f-170b65944341");

    private static UID latestInvoiceUid = new UID("a413c9e9-f2de-4f4b-a937-a63d88504796");

    private static UID rightBusinessUid = new UID("f01970a9-c004-4f29-a3e1-bf2183248d76");

    private static UID wrongBusinessUid = new UID("cde197fd-a866-4959-a2bc-6750360947d4");

    private static String rightSeries = "Series";

    private static String wrongSeries = "WrongSeries";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static AbstractDAOGenericInvoiceImpl<GenericInvoiceEntity, JPAGenericInvoiceEntity> genericInvoiceDAO;

    @Before
    public void prepare() {
        Ebean.beginTransaction();
        DAOBusinessImpl businessDAO = new DAOBusinessImpl();
        JPABusinessEntity business = new JPABusinessEntity();
        business.setUID(AbstractDAOGenericInvoiceImplTest.rightBusinessUid);
        business.setName("Test Business");
        businessDAO.create(business);
        Ebean.commitTransaction();

        Ebean.beginTransaction();
        AbstractDAOGenericInvoiceImplTest.genericInvoiceDAO = new DAOGenericInvoiceImpl();
        JPAGenericInvoiceEntity invoice1 = new JPAGenericInvoiceEntity();
        invoice1.setUID(AbstractDAOGenericInvoiceImplTest.olderInvoiceUid);
        invoice1.setSeries(AbstractDAOGenericInvoiceImplTest.rightSeries);
        invoice1.setSeriesNumber(1);
        invoice1.setBusiness(business);
        AbstractDAOGenericInvoiceImplTest.genericInvoiceDAO.create(invoice1);
        JPAGenericInvoiceEntity invoice2 = new JPAGenericInvoiceEntity();
        invoice2.setUID(AbstractDAOGenericInvoiceImplTest.latestInvoiceUid);
        invoice2.setSeries(AbstractDAOGenericInvoiceImplTest.rightSeries);
        invoice2.setSeriesNumber(2);
        invoice2.setBusiness(business);
        AbstractDAOGenericInvoiceImplTest.genericInvoiceDAO.create(invoice2);
        Ebean.commitTransaction();
    }

    @Test
    public void getLatestInvoiceFromSeries() {
        GenericInvoiceEntity invoice = AbstractDAOGenericInvoiceImplTest.genericInvoiceDAO.getLatestInvoiceFromSeries(
                AbstractDAOGenericInvoiceImplTest.rightSeries,
                AbstractDAOGenericInvoiceImplTest.rightBusinessUid.toString());

        Assert.assertEquals(invoice.getUID(), AbstractDAOGenericInvoiceImplTest.latestInvoiceUid);
    }

    @Test
    public void getLatestInvoiceFromSeries_noBusiness() {
        this.expectedException.expect(BillyRuntimeException.class);
        GenericInvoiceEntity invoice = AbstractDAOGenericInvoiceImplTest.genericInvoiceDAO.getLatestInvoiceFromSeries(
                AbstractDAOGenericInvoiceImplTest.rightSeries,
                AbstractDAOGenericInvoiceImplTest.wrongBusinessUid.toString());
    }

    @Test
    public void getLatestInvoiceFromSeries_wrongSeries() {
        GenericInvoiceEntity invoice = AbstractDAOGenericInvoiceImplTest.genericInvoiceDAO.getLatestInvoiceFromSeries(
                AbstractDAOGenericInvoiceImplTest.wrongSeries,
                AbstractDAOGenericInvoiceImplTest.rightBusinessUid.toString());

        Assert.assertEquals(invoice, null);
    }

    @Test
    public void getLatestInvoiceFromSeries_wrongBusiness() {
        this.expectedException.expect(BillyRuntimeException.class);
        GenericInvoiceEntity invoice = AbstractDAOGenericInvoiceImplTest.genericInvoiceDAO.getLatestInvoiceFromSeries(
                AbstractDAOGenericInvoiceImplTest.rightSeries,
                AbstractDAOGenericInvoiceImplTest.wrongBusinessUid.toString());
    }
}
