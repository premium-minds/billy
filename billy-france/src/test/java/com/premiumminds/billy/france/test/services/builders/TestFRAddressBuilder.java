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
import com.premiumminds.billy.france.persistence.dao.DAOFRAddress;
import com.premiumminds.billy.france.services.entities.FRAddress;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRAddressEntity;

public class TestFRAddressBuilder extends FRAbstractTest {

    private static final String FRADDRESS_YML = AbstractTest.YML_CONFIGS_DIR + "FRAddress.yml";

    @Test
    public void doTest() {

        MockFRAddressEntity mockAddress =
                this.createMockEntity(MockFRAddressEntity.class, TestFRAddressBuilder.FRADDRESS_YML);

        Mockito.when(this.getInstance(DAOFRAddress.class).getEntityInstance()).thenReturn(new MockFRAddressEntity());

        FRAddress.Builder builder = this.getInstance(FRAddress.Builder.class);

        builder.setCity(mockAddress.getCity()).setDetails(mockAddress.getDetails())
                .setISOCountry(mockAddress.getISOCountry()).setNumber(mockAddress.getNumber())
                .setPostalCode(mockAddress.getPostalCode()).setRegion(mockAddress.getRegion())
                .setStreetName(mockAddress.getStreetName());

        FRAddress address = builder.build();

        assert (address != null);
        Assert.assertEquals(mockAddress.getCity(), address.getCity());
        Assert.assertEquals(mockAddress.getDetails(), address.getDetails());
        Assert.assertEquals(mockAddress.getISOCountry(), address.getISOCountry());
        Assert.assertEquals(mockAddress.getNumber(), address.getNumber());
        Assert.assertEquals(mockAddress.getPostalCode(), address.getPostalCode());
        Assert.assertEquals(mockAddress.getRegion(), address.getRegion());
        Assert.assertEquals(mockAddress.getStreetName(), address.getStreetName());
    }
}
