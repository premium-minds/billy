/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy platypus (PT Pack).
 * 
 * billy platypus (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy platypus (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.fixtures;

import java.net.URL;

import com.premiumminds.billy.core.test.fixtures.MockApplicationEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;

public class MockPTApplicationEntity extends MockApplicationEntity implements
		PTApplicationEntity {

	private static final long serialVersionUID = 1L;

	public Integer number;
	public URL keysPath;

	public MockPTApplicationEntity() {

	}

	@Override
	public Integer getSoftwareCertificationNumber() {
		return this.number;
	}

	@Override
	public void setSoftwareCertificateNum(Integer number) {
		this.number = number;
	}

	public URL getApplicationKeysPath() {
		return this.keysPath;
	}

	public void setApplicationKeysPath(URL keysPath) {
		this.keysPath = keysPath;
	}

}
