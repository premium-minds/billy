/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.test.services.builders;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.france.persistence.dao.DAOFRShippingPoint;
import com.premiumminds.billy.france.persistence.entities.FRAddressEntity;
import com.premiumminds.billy.france.services.entities.FRAddress;
import com.premiumminds.billy.france.services.entities.FRShippingPoint;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRShippingPointEntity;

public class TestFRShippingPointBuilder extends FRAbstractTest {

    private static final String FRSHIPPINGPOINT_YML = AbstractTest.YML_CONFIGS_DIR + "FRShippingPoint.yml";

    @Test
    public void doTest() {
        MockFRShippingPointEntity mockShippingPoint =
                this.createMockEntity(MockFRShippingPointEntity.class, TestFRShippingPointBuilder.FRSHIPPINGPOINT_YML);

        Mockito.when(this.getInstance(DAOFRShippingPoint.class).getEntityInstance())
                .thenReturn(new MockFRShippingPointEntity());

        FRShippingPoint.Builder builder = this.getInstance(FRShippingPoint.Builder.class);

        FRAddress.Builder mockAddressBuilder = this.getMock(FRAddress.Builder.class);
        Mockito.when(mockAddressBuilder.build()).thenReturn((FRAddressEntity) mockShippingPoint.getAddress());

        builder.setAddress(mockAddressBuilder).setDate(mockShippingPoint.getDate())
                .setDeliveryId(mockShippingPoint.getDeliveryId()).setLocationId(mockShippingPoint.getLocationId())
                .setWarehouseId(mockShippingPoint.getWarehouseId());

        FRShippingPoint shippingPoint = builder.build();

        Assert.assertTrue(shippingPoint != null);

        Assert.assertEquals(mockShippingPoint.getDeliveryId(), shippingPoint.getDeliveryId());
        Assert.assertEquals(mockShippingPoint.getLocationId(), shippingPoint.getLocationId());
        Assert.assertEquals(mockShippingPoint.getWarehouseId(), shippingPoint.getWarehouseId());
        Assert.assertEquals(mockShippingPoint.getDate(), shippingPoint.getDate());
        Assert.assertEquals(mockShippingPoint.getAddress(), shippingPoint.getAddress());
    }

}
