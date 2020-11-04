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
import com.premiumminds.billy.portugal.persistence.dao.DAOPTContact;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTContactEntity;

public class TestPTContactBuilder extends PTAbstractTest {

    private static final String PTCONTACT_YML = AbstractTest.YML_CONFIGS_DIR + "PTContact.yml";

    @Test
    public void doTest() {
        MockPTContactEntity mockContact =
                this.createMockEntity(MockPTContactEntity.class, TestPTContactBuilder.PTCONTACT_YML);

        Mockito.when(this.getInstance(DAOPTContact.class).getEntityInstance()).thenReturn(new MockPTContactEntity());

        PTContact.Builder builder = this.getInstance(PTContact.Builder.class);

        builder.setEmail(mockContact.getEmail()).setFax(mockContact.getFax()).setMobile(mockContact.getMobile())
                .setName(mockContact.getName()).setTelephone(mockContact.getTelephone())
                .setWebsite(mockContact.getWebsite());

        PTContact contact = builder.build();

        assert (contact != null);
        Assertions.assertEquals(mockContact.getEmail(), contact.getEmail());
        Assertions.assertEquals(mockContact.getFax(), contact.getFax());
        Assertions.assertEquals(mockContact.getMobile(), contact.getMobile());
        Assertions.assertEquals(mockContact.getName(), contact.getName());
        Assertions.assertEquals(mockContact.getTelephone(), contact.getTelephone());
        Assertions.assertEquals(mockContact.getWebsite(), contact.getWebsite());

    }
}
