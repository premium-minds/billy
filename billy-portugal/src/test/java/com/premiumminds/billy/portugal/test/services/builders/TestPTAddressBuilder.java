/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.builders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTAddress;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTAddressEntity;

public class TestPTAddressBuilder extends PTAbstractTest {

    private static final String PTADDRESS_YML = AbstractTest.YML_CONFIGS_DIR + "PTAddress.yml";

    @Test
    public void doTest() {

        MockPTAddressEntity mockAddress =
                this.createMockEntity(MockPTAddressEntity.class, TestPTAddressBuilder.PTADDRESS_YML);

        Mockito.when(this.getInstance(DAOPTAddress.class).getEntityInstance()).thenReturn(new MockPTAddressEntity());

        PTAddress.Builder builder = this.getInstance(PTAddress.Builder.class);

        builder.setCity(mockAddress.getCity()).setDetails(mockAddress.getDetails())
                .setISOCountry(mockAddress.getISOCountry()).setNumber(mockAddress.getNumber())
                .setPostalCode(mockAddress.getPostalCode()).setRegion(mockAddress.getRegion())
                .setStreetName(mockAddress.getStreetName());

        PTAddress address = builder.build();

        assert (address != null);
        Assertions.assertEquals(mockAddress.getCity(), address.getCity());
        Assertions.assertEquals(mockAddress.getDetails(), address.getDetails());
        Assertions.assertEquals(mockAddress.getISOCountry(), address.getISOCountry());
        Assertions.assertEquals(mockAddress.getNumber(), address.getNumber());
        Assertions.assertEquals(mockAddress.getPostalCode(), address.getPostalCode());
        Assertions.assertEquals(mockAddress.getRegion(), address.getRegion());
        Assertions.assertEquals(mockAddress.getStreetName(), address.getStreetName());
    }
}
