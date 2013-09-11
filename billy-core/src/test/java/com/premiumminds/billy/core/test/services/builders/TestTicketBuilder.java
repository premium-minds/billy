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
package com.premiumminds.billy.core.test.services.builders;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOTicket;
import com.premiumminds.billy.core.services.entities.Ticket;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockTicketEntity;

public class TestTicketBuilder extends AbstractTest{

	private static final String TICKET_YML = AbstractTest.YML_CONFIGS_DIR
			+ "Ticket.yml";
	
	@Test
	public void doTest(){
		MockTicketEntity mockTicket = this.createMockEntity(MockTicketEntity.class, TICKET_YML);
		
		Mockito.when(this.getInstance(DAOTicket.class).getEntityInstance()).thenReturn(new MockTicketEntity());
		
		Ticket.Builder builder = this.getInstance(Ticket.Builder.class);
		
		builder.setCreationDate(mockTicket.getCreationDate())
			.setProcessDate(mockTicket.getProcessDate())
			.setObjectUID(mockTicket.getObjectUID());
		
		Ticket ticket = builder.build();
		ticket.setUID(mockTicket.getUID());

		Assert.assertTrue(ticket != null);
		Assert.assertEquals(mockTicket.getCreationDate(),
				ticket.getCreationDate());
		Assert.assertEquals(mockTicket.getProcessDate(),
				ticket.getProcessDate());
		Assert.assertEquals(mockTicket.getObjectUID(),
				ticket.getObjectUID());
		Assert.assertEquals(mockTicket.getUID(),
				ticket.getUID());
		
	}
	
}
