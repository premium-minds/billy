/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.fixtures;

import com.premiumminds.billy.core.test.fixtures.MockBaseEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTAddressEntity;


public class MockPTAddressEntity extends MockBaseEntity implements
		PTAddressEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String streetName;
	public String number;
	public String details;
	public String city;
	public String postalCode;
	public String region;
	public String country;

	public MockPTAddressEntity() {

	}

	@Override
	public String getStreetName() {
		return streetName;
	}

	@Override
	public String getNumber() {
		return number;
	}

	@Override
	public String getDetails() {
		return details;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public String getPostalCode() {
		return postalCode;
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public String getISOCountry() {
		return country;
	}

	@Override
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	@Override
	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public void setISOCountry(String country) {
		this.country = country;
	}

	// Not used in Portugal
	@Override
	public void setBuilding(String building) {
	}

	@Override
	public String getBuilding() {
		return null;
	}

}
