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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.persistence.entities.ebean.JPATicketEntity;
import com.premiumminds.billy.core.services.UID;

import io.ebean.Ebean;

public class DAOTicketImplTest extends BaseH2Test {

    private static final UID rightTicketUid = new UID("f01970a9-c004-4f29-a3e1-bf2183248d76");

    private static final UID wrongTicketUid = new UID("a413c9e9-f2de-4f4b-a937-a63d88504796");

    private static final UID referencedObjUid = new UID("1796dc4d-462c-468c-9f0f-170b65944341");

    private static DAOTicketImpl daoTicketImpl;

    @BeforeEach
    public void prepare() {
        daoTicketImpl = new DAOTicketImpl();
    }

    @Test
    public void getObjectEntityUID_noTickets() {
        Assertions.assertThrows(NoResultException.class, () -> daoTicketImpl.getObjectEntityUID(rightTicketUid.toString()));
    }

    @Test
    public void getObjectEntityUID_noSuchUID() {
        Ebean.beginTransaction();
        JPATicketEntity ticket = new JPATicketEntity();
        ticket.setUID(rightTicketUid);
        ticket.setObjectUID(referencedObjUid);
        daoTicketImpl.create(ticket);
        Ebean.commitTransaction();
        
        Assertions.assertThrows(NoResultException.class, () -> daoTicketImpl.getObjectEntityUID(wrongTicketUid.toString()));
    }

    @Test
    public void getObjectEntityUID() {
        Ebean.beginTransaction();
        JPATicketEntity ticket = new JPATicketEntity();
        ticket.setUID(rightTicketUid);
        ticket.setObjectUID(referencedObjUid);
        daoTicketImpl.create(ticket);
        Ebean.commitTransaction();

        UID objUid = daoTicketImpl.getObjectEntityUID(rightTicketUid.toString());

        Assertions.assertEquals(objUid, referencedObjUid);
    }
}
