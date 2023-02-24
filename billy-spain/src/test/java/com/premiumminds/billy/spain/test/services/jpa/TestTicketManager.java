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
package com.premiumminds.billy.spain.test.services.jpa;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.TicketManager;
import com.premiumminds.billy.core.services.entities.Ticket;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTicketManager extends ESJPAAbstractTest {

    private static final String OBJECT_UID = "object_uid";
    private static final Date CREATION_DATE = new Date();
    private static final Date PROCESS_DATE = new Date();
    private TicketManager manager = null;
    StringID<Ticket> ticket = null;

    @BeforeEach
    public void setUp() {
        this.manager = this.getInstance(TicketManager.class);

    }

    @Test
    public void generateTicketTest() {
        this.ticket = this.manager.generateTicket(this.getInstance(Ticket.Builder.class));
        Assertions.assertTrue(this.ticket != null);
    }

    @Test
    public void ticketExistsTest() {
        this.ticket = this.manager.generateTicket(this.getInstance(Ticket.Builder.class));
        Assertions.assertTrue(this.manager.ticketExists(this.ticket));
    }

    @Test
    public void updateTicketTest() {
        this.ticket = this.manager.generateTicket(this.getInstance(Ticket.Builder.class));
        this.manager.updateTicket(this.ticket, StringID.fromValue(TestTicketManager.OBJECT_UID),
                                  TestTicketManager.CREATION_DATE, TestTicketManager.PROCESS_DATE);
        Assertions.assertTrue(this.manager.ticketExists(this.ticket));
    }

}
