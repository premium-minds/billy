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
package com.premiumminds.billy.portugal.test.services.builders;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTAddress;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTAddressEntity;

public class TestPTAddressBuilder extends PTAbstractTest {

	private static final String PTADDRESS_YML = "src/test/resources/PTAddress.yml";

	@Test
	public void doTest() {

		MockPTAddressEntity mockAddress = (MockPTAddressEntity) createMockEntity(
				generateMockEntityConstructor(MockPTAddressEntity.class),
				PTADDRESS_YML);

		Mockito.when(getInstance(DAOPTAddress.class).getEntityInstance())
				.thenReturn(new MockPTAddressEntity());

		PTAddress.Builder builder = getInstance(PTAddress.Builder.class);

		builder.setCity(mockAddress.getCity())
				.setDetails(mockAddress.getDetails())
				.setISOCountry(mockAddress.getISOCountry())
				.setNumber(mockAddress.getNumber())
				.setPostalCode(mockAddress.getPostalCode())
				.setRegion(mockAddress.getRegion())
				.setStreetName(mockAddress.getStreetName());
		
		PTAddress address = builder.build();
		
		assert(address != null);
		assertEquals(mockAddress.getCity(), address.getCity());
		assertEquals(mockAddress.getDetails(), address.getDetails());
		assertEquals(mockAddress.getISOCountry(), address.getISOCountry());
		assertEquals(mockAddress.getNumber(), address.getNumber());
		assertEquals(mockAddress.getPostalCode(), address.getPostalCode());
		assertEquals(mockAddress.getRegion(), address.getRegion());
		assertEquals(mockAddress.getStreetName(), address.getStreetName());
	}
}
