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
package com.premiumminds.billy.core.test.fixtures;

import java.util.Date;

import com.premiumminds.billy.core.persistence.entities.TicketEntity;
import com.premiumminds.billy.core.services.UID;

public class MockTicketEntity extends MockBaseEntity implements TicketEntity{

	private static final long serialVersionUID = 1L;
	
	private UID objectUID;
	private Date creationDate;
	private Date processDate;
	
	public MockTicketEntity(){
		
	}

	@Override
	public UID getObjectUID() {
		return this.objectUID;
	}

	@Override
	public Date getCreationDate() {
		return this.creationDate;
	}

	@Override
	public Date getProcessDate() {
		return this.processDate;
	}

	@Override
	public void setObjectUID(UID objectUID) {
		this.objectUID = objectUID;
	}

	@Override
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	
	
}
