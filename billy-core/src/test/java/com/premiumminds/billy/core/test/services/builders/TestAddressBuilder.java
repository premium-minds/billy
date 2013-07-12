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

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOAddress;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockAddressEntity;

public class TestAddressBuilder extends AbstractTest {
	
	private static final String STREET_NAME = "street_name";
	private static final String NUMBER = "number";
	private static final String DETAILS = "details";
	private static final String BUILDING = "building";
	private static final String CITY = "city";
	private static final String POSTAL_CODE = "postal_code";
	private static final String REGION = "region";
	private static final String COUNTRY = "country";
	
	@Test
	public void doTest() {
		MockAddressEntity mockAddress = this.loadFixture(AddressEntity.class);
		
		DAOAddress mockDaoAddress = this.getMock(DAOAddress.class);
		
		Mockito.when(mockDaoAddress.getEntityInstance()).thenReturn(new MockAddressEntity());
		
		Address.Builder builder = new Address.Builder(mockDaoAddress);
		
		builder.setBuilding(mockAddress.getBuilding()).setCity(mockAddress.getCity()).setDetails(mockAddress.getDetails()).setISOCountry(mockAddress.getISOCountry()).setNumber(mockAddress.getNumber()).setPostalCode(mockAddress.getPostalCode()).setRegion(mockAddress.getRegion()).setStreetName(mockAddress.getStreetName());
		
		Address address = builder.build();
		
		assert(address != null);
		assertEquals(STREET_NAME, address.getStreetName());
		assertEquals(NUMBER, address.getNumber());
		assertEquals(DETAILS, address.getDetails());
		assertEquals(BUILDING, address.getBuilding());
		assertEquals(CITY, address.getCity());
		assertEquals(POSTAL_CODE, address.getPostalCode());
		assertEquals(REGION, address.getRegion());
		assertEquals(COUNTRY, address.getISOCountry());
	}
	
	public MockAddressEntity loadFixture(Class<AddressEntity> clazz) {
		MockAddressEntity result = new MockAddressEntity();
		
		result.streetName = STREET_NAME;
		result.number = NUMBER;
		result.details = DETAILS;
		result.building = BUILDING;
		result.city = CITY;
		result.postalCode = POSTAL_CODE;
		result.region = REGION;
		result.country = COUNTRY;
		
		return result;
	}
}
