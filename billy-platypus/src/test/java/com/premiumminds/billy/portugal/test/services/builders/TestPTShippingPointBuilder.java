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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTShippingPoint;
import com.premiumminds.billy.portugal.persistence.entities.PTAddressEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTShippingPoint;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTShippingPointEntity;

public class TestPTShippingPointBuilder extends PTAbstractTest {

	private static final String PTSHIPPINGPOINT_YML = "src/test/resources/PTShippingPoint.yml";

	@Test
	public void doTest() {
		MockPTShippingPointEntity mockShippingPoint = createMockEntity(
				MockPTShippingPointEntity.class, PTSHIPPINGPOINT_YML);

		Mockito.when(getInstance(DAOPTShippingPoint.class).getEntityInstance())
				.thenReturn(new MockPTShippingPointEntity());

		PTShippingPoint.Builder builder = getInstance(PTShippingPoint.Builder.class);

		PTAddress.Builder mockAddressBuilder = this
				.getMock(PTAddress.Builder.class);
		Mockito.when(mockAddressBuilder.build()).thenReturn(
				(PTAddressEntity) mockShippingPoint.getAddress());

		builder.setAddress(mockAddressBuilder)
				.setDate(mockShippingPoint.getDate())
				.setDeliveryId(mockShippingPoint.getDeliveryId())
				.setLocationId(mockShippingPoint.getLocationId())
				.setWarehouseId(mockShippingPoint.getWarehouseId());

		PTShippingPoint shippingPoint = builder.build();

		assertTrue(shippingPoint != null);

		assertEquals(mockShippingPoint.getDeliveryId(),
				shippingPoint.getDeliveryId());
		assertEquals(mockShippingPoint.getLocationId(),
				shippingPoint.getLocationId());
		assertEquals(mockShippingPoint.getWarehouseId(),
				shippingPoint.getWarehouseId());
		assertEquals(mockShippingPoint.getDate(), shippingPoint.getDate());
		assertEquals(mockShippingPoint.getAddress(), shippingPoint.getAddress());
	}

}
