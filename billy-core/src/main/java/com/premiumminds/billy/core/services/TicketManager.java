/**
 * Copyright (C) 2013 Premium Minds.
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

public class TicketManager implements Serializable {

	private static final long	serialVersionUID	= Config.SERIAL_VERSION;
	private DAOTicket			daoTicket			= null;

	@Inject
	public TicketManager(DAOTicket daoTicket) {
		this.daoTicket = daoTicket;
	}

	public String generateTicket(Builder<?> ticketBuilder)
		throws InvalidTicketException {

		TicketEntity newTicket = (TicketEntity) ticketBuilder.build();

		UID uid = new UID(UUID.randomUUID().toString());
		newTicket.setUID(uid);

		if (ticketExists(newTicket.getUID().getValue())) {
			throw new InvalidTicketException();
		}

		daoTicket.create(newTicket);

		return newTicket.getUID().getValue();

	}

	public boolean ticketExists(String ticketUID) {
		return daoTicket.exists(new UID(ticketUID));
	}

	public boolean ticketIssued(String ticketUID) throws InvalidTicketException {
		if (!ticketExists(ticketUID)) {
			throw new InvalidTicketException();
		}
		TicketEntity ticket = daoTicket.get(new UID(ticketUID));
		return ticket.getObjectUID() != null;
	}

	public void updateTicket(UID ticketUID, UID objectUID, Date creationDate,
			Date processDate) throws InvalidTicketException {

		if (!ticketExists(ticketUID.getValue())) {
			throw new InvalidTicketException();
		}

		TicketEntity ticket = daoTicket.get(ticketUID);
		ticket.setCreationDate(creationDate);
		ticket.setProcessDate(processDate);
		ticket.setObjectUID(objectUID);

		daoTicket.update(ticket);
	}
}
