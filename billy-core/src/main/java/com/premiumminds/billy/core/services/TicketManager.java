/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.services;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.inject.Inject;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.exceptions.InvalidTicketException;
import com.premiumminds.billy.core.persistence.dao.DAOTicket;
import com.premiumminds.billy.core.persistence.entities.TicketEntity;
import com.premiumminds.billy.core.services.entities.Ticket;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;

@Deprecated
public class TicketManager implements Serializable {

    private static final long serialVersionUID = Config.SERIAL_VERSION;
    private DAOTicket daoTicket = null;

    @Inject
    public TicketManager(DAOTicket daoTicket) {
        this.daoTicket = daoTicket;
    }

    public StringID<Ticket> generateTicket(Builder<?> ticketBuilder) throws InvalidTicketException {

        TicketEntity newTicket = (TicketEntity) ticketBuilder.build();

        final StringID<Ticket> uid = StringID.fromValue(UUID.randomUUID().toString());
        newTicket.setUID(uid);

        if (this.ticketExists(newTicket.getUID())) {
            throw new InvalidTicketException();
        }

        this.daoTicket.create(newTicket);

        return newTicket.getUID();

    }

    public boolean ticketExists(StringID<Ticket> ticketUID) {
        return this.daoTicket.exists(ticketUID);
    }

    public boolean ticketIssued(StringID<Ticket> ticketUID) throws InvalidTicketException {
        if (!this.ticketExists(ticketUID)) {
            throw new InvalidTicketException();
        }
        TicketEntity ticket = this.daoTicket.get(ticketUID);
        return ticket.getObjectUID() != null;
    }

    public void updateTicket(
            StringID<Ticket> ticketUID, StringID<GenericInvoice> objectUID, Date creationDate, Date processDate)
            throws InvalidTicketException {

        if (!this.ticketExists(ticketUID)) {
            throw new InvalidTicketException();
        }

        TicketEntity ticket = this.daoTicket.get(ticketUID);
        ticket.setCreationDate(creationDate);
        ticket.setProcessDate(processDate);
        ticket.setObjectUID(objectUID);

        this.daoTicket.update(ticket);
    }
}
