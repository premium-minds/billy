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

import java.util.Date;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOShippingPoint;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockAddressEntity;
import com.premiumminds.billy.core.test.fixtures.MockCustomerEntity;
import com.premiumminds.billy.core.test.fixtures.MockShippingPointEntity;


public class TestShippingPointBuilder extends AbstractTest {

	private static final String SHIPPINGPOINT_YML = "src/test/resources/ShippingPoint.yml";
	
	@Test
	public void doTest() {
		MockShippingPointEntity mockShippingPoint = loadFixture(MockShippingPointEntity.class);
			
		Mockito.when(getInstance(DAOShippingPoint.class).getEntityInstance()).thenReturn(new MockShippingPointEntity());
		
		ShippingPoint.Builder builder = getInstance(ShippingPoint.Builder.class);
		
		Address.Builder mockAddressBuilder = this.getMock(Address.Builder.class);
		Mockito.when(mockAddressBuilder.build()).thenReturn(Mockito.mock(AddressEntity.class));
		
		builder.setAddress(mockAddressBuilder).setDate(mockShippingPoint.getDate()).setDeliveryId(mockShippingPoint.getDeliveryId()).setLocationId(mockShippingPoint.getLocationId()).setUCR(mockShippingPoint.getUCR()).setWarehouseId(mockShippingPoint.getWarehouseId());
		
		ShippingPoint shippingPoint = builder.build();
		
		assert(shippingPoint != null);
		
		assertEquals(mockShippingPoint.getDeliveryId(), shippingPoint.getDeliveryId());
		assertEquals(mockShippingPoint.getLocationId(), shippingPoint.getLocationId());
		assertEquals(mockShippingPoint.getUCR(), shippingPoint.getUCR());
		assertEquals(mockShippingPoint.getWarehouseId(), shippingPoint.getWarehouseId());
	}

	public MockShippingPointEntity loadFixture(Class<MockShippingPointEntity> clazz) {
		MockShippingPointEntity result = (MockShippingPointEntity) createMockEntityFromYaml(MockShippingPointEntity.class, SHIPPINGPOINT_YML);
		
		result.address = new MockAddressEntity();
		result.date = new Date();
		
		return result;
	}
}
