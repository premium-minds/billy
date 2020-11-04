/*
 * Copyright (C) 2017 Premium Minds.
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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockContactEntity;

public class TestContactBuilder extends AbstractTest {

    private static final String CONTACT_YML = AbstractTest.YML_CONFIGS_DIR + "Contact.yml";

    @Test
    public void doTest() {

        MockContactEntity mockContact = this.createMockEntity(MockContactEntity.class, TestContactBuilder.CONTACT_YML);

        Mockito.when(this.getInstance(DAOContact.class).getEntityInstance()).thenReturn(new MockContactEntity());

        Contact.Builder builder = this.getInstance(Contact.Builder.class);

        builder.setEmail(mockContact.getEmail()).setFax(mockContact.getFax()).setMobile(mockContact.getMobile())
                .setName(mockContact.getName()).setTelephone(mockContact.getTelephone())
                .setWebsite(mockContact.getWebsite());

        Contact contact = builder.build();

        assert (contact != null);
        Assertions.assertEquals(mockContact.getName(), contact.getName());
        Assertions.assertEquals(mockContact.getTelephone(), contact.getTelephone());
        Assertions.assertEquals(mockContact.getMobile(), contact.getMobile());
        Assertions.assertEquals(mockContact.getFax(), contact.getFax());
        Assertions.assertEquals(mockContact.getEmail(), contact.getEmail());
        Assertions.assertEquals(mockContact.getWebsite(), contact.getWebsite());
    }
}
