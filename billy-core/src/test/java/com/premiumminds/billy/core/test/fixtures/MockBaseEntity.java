package com.premiumminds.billy.core.test.fixtures;

import java.util.Date;

import com.premiumminds.billy.core.persistence.entities.BaseEntity;
import com.premiumminds.billy.core.services.UID;

public class MockBaseEntity implements BaseEntity {
	private static final long serialVersionUID = 1L;

	public UID uid;
	public Date createTimestamp;
	public Date updateTimestamp;
	public Boolean isNew;
	
	@Override
	public boolean isNew() {
		return isNew;
	}
	
	@Override
	public UID getUID() {
		return uid;
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
	
}
