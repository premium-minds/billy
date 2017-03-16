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

import com.premiumminds.billy.core.persistence.entities.BaseEntity;
import com.premiumminds.billy.core.services.UID;

public class MockBaseEntity implements BaseEntity {

	private static final long serialVersionUID = 1L;

	public Integer id;
	public UID uid;
	public Date createTimestamp;
	public Date updateTimestamp;
	public boolean isNew;

	public MockBaseEntity() {
		this.isNew = true;
	}

	@Override
	public boolean isNew() {
		return this.isNew;
	}
	
	@Override
	public Integer getID() {
		return id;
	}

	@Override
	public UID getUID() {
		return this.uid;
	}

	@Override
	public void setUID(UID uid) {
		this.uid = uid;
	}

	@Override
	public Date getCreateTimestamp() {
		return this.createTimestamp;
	}

	@Override
	public Date getUpdateTimestamp() {
		return this.updateTimestamp;
	}

	@Override
	public void initializeEntityDates() {
		this.createTimestamp = updateTimestamp = new Date();
	}

}
