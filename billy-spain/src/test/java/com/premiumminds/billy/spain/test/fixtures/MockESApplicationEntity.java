/**
 * Copyright (C) 2013 Premium Minds.
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
package com.premiumminds.billy.spain.test.fixtures;

import java.net.URL;

import com.premiumminds.billy.core.test.fixtures.MockApplicationEntity;
import com.premiumminds.billy.spain.persistence.entities.ESApplicationEntity;

public class MockESApplicationEntity extends MockApplicationEntity implements
	ESApplicationEntity {

	private static final long	serialVersionUID	= 1L;

	public Integer				number;
	public URL					keysPath;

	public MockESApplicationEntity() {

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
