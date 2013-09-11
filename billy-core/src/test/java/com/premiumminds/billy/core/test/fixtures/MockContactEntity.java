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

import com.premiumminds.billy.core.persistence.entities.ContactEntity;

public class MockContactEntity extends MockBaseEntity implements ContactEntity {

	private static final long	serialVersionUID	= 1L;

	public String				name;
	public String				telephone;
	public String				mobile;
	public String				fax;
	public String				email;
	public String				website;

	public MockContactEntity() {

	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getTelephone() {
		return this.telephone;
	}

	@Override
	public String getMobile() {
		return this.mobile;
	}

	@Override
	public String getFax() {
		return this.fax;
	}

	@Override
	public String getEmail() {
		return this.email;
	}

	@Override
	public String getWebsite() {
		return this.website;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public void setFax(String fax) {
		this.fax = fax;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public void setWebsite(String website) {
		this.website = website;
	}

}
