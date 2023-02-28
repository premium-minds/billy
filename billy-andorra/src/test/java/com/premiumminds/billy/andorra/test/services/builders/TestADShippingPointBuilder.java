/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.test.services.builders;

import com.premiumminds.billy.andorra.persistence.dao.DAOADShippingPoint;
import com.premiumminds.billy.andorra.persistence.entities.ADAddressEntity;
import com.premiumminds.billy.andorra.services.entities.ADAddress;
import com.premiumminds.billy.andorra.services.entities.ADShippingPoint;
import com.premiumminds.billy.andorra.services.entities.ADShippingPoint.Builder;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.fixtures.MockADShippingPointEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;

public class TestADShippingPointBuilder extends ADAbstractTest {

    private static final String ESSHIPPINGPOINT_YML = AbstractTest.YML_CONFIGS_DIR + "ESShippingPoint.yml";

    @Test
    public void doTest() {
        MockADShippingPointEntity mockShippingPoint =
                this.createMockEntity(MockADShippingPointEntity.class, TestADShippingPointBuilder.ESSHIPPINGPOINT_YML);

        Mockito.when(this.getInstance(DAOADShippingPoint.class).getEntityInstance())
                .thenReturn(new MockADShippingPointEntity());

        Builder builder = this.getInstance(Builder.class);

        ADAddress.Builder mockAddressBuilder = this.getMock(ADAddress.Builder.class);
        Mockito.when(mockAddressBuilder.build()).thenReturn((ADAddressEntity) mockShippingPoint.getAddress());

        builder.setAddress(mockAddressBuilder).setDate(mockShippingPoint.getDate())
                .setDeliveryId(mockShippingPoint.getDeliveryId()).setLocationId(mockShippingPoint.getLocationId())
                .setWarehouseId(mockShippingPoint.getWarehouseId());

        ADShippingPoint shippingPoint = builder.build();

        Assertions.assertTrue(shippingPoint != null);

        Assertions.assertEquals(mockShippingPoint.getDeliveryId(), shippingPoint.getDeliveryId());
        Assertions.assertEquals(mockShippingPoint.getLocationId(), shippingPoint.getLocationId());
        Assertions.assertEquals(mockShippingPoint.getWarehouseId(), shippingPoint.getWarehouseId());
        Assertions.assertEquals(mockShippingPoint.getDate(), shippingPoint.getDate());
        Assertions.assertEquals(mockShippingPoint.getAddress(), shippingPoint.getAddress());
    }

}
