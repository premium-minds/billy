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

import com.premiumminds.billy.andorra.persistence.dao.DAOADAddress;
import com.premiumminds.billy.andorra.services.entities.ADAddress;
import com.premiumminds.billy.andorra.services.entities.ADAddress.Builder;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.fixtures.MockADAddressEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;

public class TestADAddressBuilder extends ADAbstractTest {

    private static final String AD_ADDRESS_YML = AbstractTest.YML_CONFIGS_DIR + "ADAddress.yml";

    @Test
    public void doTest() {

        MockADAddressEntity mockAddress =
                this.createMockEntity(MockADAddressEntity.class, TestADAddressBuilder.AD_ADDRESS_YML);

        Mockito.when(this.getInstance(DAOADAddress.class).getEntityInstance()).thenReturn(new MockADAddressEntity());

        Builder builder = this.getInstance(Builder.class);

        builder.setCity(mockAddress.getCity()).setDetails(mockAddress.getDetails())
                .setISOCountry(mockAddress.getISOCountry()).setNumber(mockAddress.getNumber())
                .setPostalCode(mockAddress.getPostalCode()).setRegion(mockAddress.getRegion())
                .setStreetName(mockAddress.getStreetName());

        ADAddress address = builder.build();

        Assertions.assertNotNull(address);
        Assertions.assertEquals(mockAddress.getCity(), address.getCity());
        Assertions.assertEquals(mockAddress.getDetails(), address.getDetails());
        Assertions.assertEquals(mockAddress.getISOCountry(), address.getISOCountry());
        Assertions.assertEquals(mockAddress.getNumber(), address.getNumber());
        Assertions.assertEquals(mockAddress.getPostalCode(), address.getPostalCode());
        Assertions.assertEquals(mockAddress.getRegion(), address.getRegion());
        Assertions.assertEquals(mockAddress.getStreetName(), address.getStreetName());
    }
}
