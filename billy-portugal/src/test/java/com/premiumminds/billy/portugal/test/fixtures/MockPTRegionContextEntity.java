/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
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
		return this.regionCode;
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
