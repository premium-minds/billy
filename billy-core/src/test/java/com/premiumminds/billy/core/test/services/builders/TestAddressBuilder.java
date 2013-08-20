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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOAddress;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockAddressEntity;

public class TestAddressBuilder extends AbstractTest {

	private static final String	ADDRESS_YML	= AbstractTest.YML_CONFIGS_DIR
													+ "Address.yml";

	@Test
	public void doTest() {
		MockAddressEntity mockAddress = this.createMockEntity(
				MockAddressEntity.class, TestAddressBuilder.ADDRESS_YML);

		Mockito.when(this.getInstance(DAOAddress.class).getEntityInstance())
				.thenReturn(new MockAddressEntity());

		Address.Builder builder = this.getInstance(Address.Builder.class);

		builder.setBuilding(mockAddress.getBuilding())
				.setCity(mockAddress.getCity())
				.setDetails(mockAddress.getDetails())
				.setISOCountry(mockAddress.getISOCountry())
				.setNumber(mockAddress.getNumber())
				.setPostalCode(mockAddress.getPostalCode())
				.setRegion(mockAddress.getRegion())
				.setStreetName(mockAddress.getStreetName());

		Address address = builder.build();

		Assert.assertTrue(address != null);

		Assert.assertEquals(mockAddress.getStreetName(),
				address.getStreetName());
		Assert.assertEquals(mockAddress.getNumber(), address.getNumber());
		Assert.assertEquals(mockAddress.getDetails(), address.getDetails());
		Assert.assertEquals(mockAddress.getBuilding(), address.getBuilding());
		Assert.assertEquals(mockAddress.getCity(), address.getCity());
		Assert.assertEquals(mockAddress.getPostalCode(),
				address.getPostalCode());
		Assert.assertEquals(mockAddress.getRegion(), address.getRegion());
		Assert.assertEquals(mockAddress.getISOCountry(),
				address.getISOCountry());
	}
}
