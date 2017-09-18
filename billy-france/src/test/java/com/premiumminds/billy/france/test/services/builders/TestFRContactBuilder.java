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
import com.premiumminds.billy.france.persistence.dao.DAOFRContact;
import com.premiumminds.billy.france.services.entities.FRContact;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRContactEntity;

public class TestFRContactBuilder extends FRAbstractTest {

    private static final String FRCONTACT_YML = AbstractTest.YML_CONFIGS_DIR + "FRContact.yml";

    @Test
    public void doTest() {
        MockFRContactEntity mockContact =
                this.createMockEntity(MockFRContactEntity.class, TestFRContactBuilder.FRCONTACT_YML);

        Mockito.when(this.getInstance(DAOFRContact.class).getEntityInstance()).thenReturn(new MockFRContactEntity());

        FRContact.Builder builder = this.getInstance(FRContact.Builder.class);

        builder.setEmail(mockContact.getEmail()).setFax(mockContact.getFax()).setMobile(mockContact.getMobile())
                .setName(mockContact.getName()).setTelephone(mockContact.getTelephone())
                .setWebsite(mockContact.getWebsite());

        FRContact contact = builder.build();

        assert (contact != null);
        Assert.assertEquals(mockContact.getEmail(), contact.getEmail());
        Assert.assertEquals(mockContact.getFax(), contact.getFax());
        Assert.assertEquals(mockContact.getMobile(), contact.getMobile());
        Assert.assertEquals(mockContact.getName(), contact.getName());
        Assert.assertEquals(mockContact.getTelephone(), contact.getTelephone());
        Assert.assertEquals(mockContact.getWebsite(), contact.getWebsite());

    }
}
