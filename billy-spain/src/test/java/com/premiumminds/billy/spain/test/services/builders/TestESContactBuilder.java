/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.test.services.builders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.spain.persistence.dao.DAOESContact;
import com.premiumminds.billy.spain.services.entities.ESContact;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESContactEntity;

public class TestESContactBuilder extends ESAbstractTest {

    private static final String ESCONTACT_YML = AbstractTest.YML_CONFIGS_DIR + "ESContact.yml";

    @Test
    public void doTest() {
        MockESContactEntity mockContact =
                this.createMockEntity(MockESContactEntity.class, TestESContactBuilder.ESCONTACT_YML);

        Mockito.when(this.getInstance(DAOESContact.class).getEntityInstance()).thenReturn(new MockESContactEntity());

        ESContact.Builder builder = this.getInstance(ESContact.Builder.class);

        builder.setEmail(mockContact.getEmail()).setFax(mockContact.getFax()).setMobile(mockContact.getMobile())
                .setName(mockContact.getName()).setTelephone(mockContact.getTelephone())
                .setWebsite(mockContact.getWebsite());

        ESContact contact = builder.build();

        Assertions.assertNotNull(contact);
        Assertions.assertEquals(mockContact.getEmail(), contact.getEmail());
        Assertions.assertEquals(mockContact.getFax(), contact.getFax());
        Assertions.assertEquals(mockContact.getMobile(), contact.getMobile());
        Assertions.assertEquals(mockContact.getName(), contact.getName());
        Assertions.assertEquals(mockContact.getTelephone(), contact.getTelephone());
        Assertions.assertEquals(mockContact.getWebsite(), contact.getWebsite());

    }
}
