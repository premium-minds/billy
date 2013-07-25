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

import com.premiumminds.billy.core.persistence.dao.DAOShippingPoint;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockShippingPointEntity;

public class TestShippingPointBuilder extends AbstractTest {

	private static final String SHIPPINGPOINT_YML = "src/test/resources/ShippingPoint.yml";

	@Test
	public void doTest() {
		MockShippingPointEntity mockShippingPoint = this.createMockEntity(
				MockShippingPointEntity.class,
				TestShippingPointBuilder.SHIPPINGPOINT_YML);

		Mockito.when(
				this.getInstance(DAOShippingPoint.class).getEntityInstance())
				.thenReturn(new MockShippingPointEntity());

		ShippingPoint.Builder builder = this
				.getInstance(ShippingPoint.Builder.class);

		Address.Builder mockAddressBuilder = this
				.getMock(Address.Builder.class);
		Mockito.when(mockAddressBuilder.build()).thenReturn(
				mockShippingPoint.getAddress());

		builder.setAddress(mockAddressBuilder)
				.setDate(mockShippingPoint.getDate())
				.setDeliveryId(mockShippingPoint.getDeliveryId())
				.setLocationId(mockShippingPoint.getLocationId())
				.setUCR(mockShippingPoint.getUCR())
				.setWarehouseId(mockShippingPoint.getWarehouseId());

		ShippingPoint shippingPoint = builder.build();

		Assert.assertTrue(shippingPoint != null);

		Assert.assertEquals(mockShippingPoint.getDeliveryId(),
				shippingPoint.getDeliveryId());
		Assert.assertEquals(mockShippingPoint.getLocationId(),
				shippingPoint.getLocationId());
		Assert.assertEquals(mockShippingPoint.getUCR(), shippingPoint.getUCR());
		Assert.assertEquals(mockShippingPoint.getWarehouseId(),
				shippingPoint.getWarehouseId());
		Assert.assertEquals(mockShippingPoint.getDate(),
				shippingPoint.getDate());
		Assert.assertEquals(mockShippingPoint.getAddress(),
				shippingPoint.getAddress());
	}

}
