package com.premiumminds.billy.portugal.test.fixtures;

import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.test.fixtures.MockContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;

public class MockPTRegionContextEntity extends MockContextEntity implements
		PTRegionContextEntity {

	private static final long serialVersionUID = 1L;

	public String regionCode;

	public MockPTRegionContextEntity() {
	}

	@Override
	public String getRegionCode() {
		return regionCode;
	}

	@Override
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	@Override
	public <T extends ContextEntity> void setParentContext(T parent) {
		super.setParentContext(parent);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Context getParentContext() {
		return super.getParentContext();
	}
}
