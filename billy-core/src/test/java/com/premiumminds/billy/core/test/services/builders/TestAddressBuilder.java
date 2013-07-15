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
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockAddressEntity;

public class TestAddressBuilder extends AbstractTest {

	private static final String ADDRESS_YML = "src/test/resources/Address.yml";

	@Test
	public void doTest() {
		MockAddressEntity mockAddress = (MockAddressEntity) createMockEntityFromYaml(
				MockAddressEntity.class, ADDRESS_YML);

		Mockito.when(getInstance(DAOAddress.class).getEntityInstance())
				.thenReturn(new MockAddressEntity());

		Address.Builder builder = getInstance(Address.Builder.class);

		builder.setBuilding(mockAddress.getBuilding())
				.setCity(mockAddress.getCity())
				.setDetails(mockAddress.getDetails())
				.setISOCountry(mockAddress.getISOCountry())
				.setNumber(mockAddress.getNumber())
				.setPostalCode(mockAddress.getPostalCode())
				.setRegion(mockAddress.getRegion())
				.setStreetName(mockAddress.getStreetName());

		Address address = builder.build();

		assert (address != null);
		assertEquals(mockAddress.getStreetName(), address.getStreetName());
		assertEquals(mockAddress.getNumber(), address.getNumber());
		assertEquals(mockAddress.getDetails(), address.getDetails());
		assertEquals(mockAddress.getBuilding(), address.getBuilding());
		assertEquals(mockAddress.getCity(), address.getCity());
		assertEquals(mockAddress.getPostalCode(), address.getPostalCode());
		assertEquals(mockAddress.getRegion(), address.getRegion());
		assertEquals(mockAddress.getISOCountry(), address.getISOCountry());
	}
}
