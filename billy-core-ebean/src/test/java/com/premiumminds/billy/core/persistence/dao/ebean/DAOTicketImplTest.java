/*
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

import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.premiumminds.billy.core.persistence.entities.ebean.JPATicketEntity;
import com.premiumminds.billy.core.services.UID;

import io.ebean.Ebean;

public class DAOTicketImplTest extends BaseH2Test {

    private static UID rightTicketUid = new UID("f01970a9-c004-4f29-a3e1-bf2183248d76");

    private static UID wrongTicketUid = new UID("a413c9e9-f2de-4f4b-a937-a63d88504796");

    private static UID referencedObjUid = new UID("1796dc4d-462c-468c-9f0f-170b65944341");

    private static DAOTicketImpl daoTicketImpl;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void prepare() {
        DAOTicketImplTest.daoTicketImpl = new DAOTicketImpl();
    }

    @Test
    public void getObjectEntityUID_noTickets() {
        this.expectedException.expect(NoResultException.class);
        DAOTicketImplTest.daoTicketImpl.getObjectEntityUID(DAOTicketImplTest.rightTicketUid.toString());
    }

    @Test
    public void getObjectEntityUID_noSuchUID() {
        Ebean.beginTransaction();
        JPATicketEntity ticket = new JPATicketEntity();
        ticket.setUID(DAOTicketImplTest.rightTicketUid);
        ticket.setObjectUID(DAOTicketImplTest.referencedObjUid);
        DAOTicketImplTest.daoTicketImpl.create(ticket);
        Ebean.commitTransaction();

        this.expectedException.expect(NoResultException.class);
        DAOTicketImplTest.daoTicketImpl.getObjectEntityUID(DAOTicketImplTest.wrongTicketUid.toString());
    }

    @Test
    public void getObjectEntityUID() {
        Ebean.beginTransaction();
        JPATicketEntity ticket = new JPATicketEntity();
        ticket.setUID(DAOTicketImplTest.rightTicketUid);
        ticket.setObjectUID(DAOTicketImplTest.referencedObjUid);
        DAOTicketImplTest.daoTicketImpl.create(ticket);
        Ebean.commitTransaction();

        UID objUid = DAOTicketImplTest.daoTicketImpl.getObjectEntityUID(DAOTicketImplTest.rightTicketUid.toString());

        Assert.assertEquals(objUid, DAOTicketImplTest.referencedObjUid);
    }
}
