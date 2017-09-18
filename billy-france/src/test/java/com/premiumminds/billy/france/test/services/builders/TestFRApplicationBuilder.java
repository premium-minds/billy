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

import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.france.persistence.dao.DAOFRApplication;
import com.premiumminds.billy.france.persistence.entities.FRContactEntity;
import com.premiumminds.billy.france.services.entities.FRApplication;
import com.premiumminds.billy.france.services.entities.FRContact;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRApplicationEntity;

public class TestFRApplicationBuilder extends FRAbstractTest {

    private static final String FRAPPLICATION_YML = AbstractTest.YML_CONFIGS_DIR + "FRApplication.yml";

    @Test
    public void doTest() throws MalformedURLException {

        MockFRApplicationEntity mockApplication =
                this.createMockEntity(MockFRApplicationEntity.class, TestFRApplicationBuilder.FRAPPLICATION_YML);

        Mockito.when(this.getInstance(DAOFRApplication.class).getEntityInstance())
                .thenReturn(new MockFRApplicationEntity());

        FRApplication.Builder builder = this.getInstance(FRApplication.Builder.class);

        FRContact.Builder mockContactBuilder = this.getMock(FRContact.Builder.class);
        Mockito.when(mockContactBuilder.build()).thenReturn(Mockito.mock(FRContactEntity.class));

        FRContact.Builder mockMainContactBuilder = this.getMock(FRContact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn(Mockito.mock(FRContactEntity.class));

        builder.addContact(mockContactBuilder).addContact(mockMainContactBuilder)
                .setDeveloperCompanyName(mockApplication.getDeveloperCompanyName())
                .setDeveloperCompanyTaxIdentifier(mockApplication.getDeveloperCompanyTaxIdentifier())
                .setMainContact(mockMainContactBuilder).setName(mockApplication.getName())
                .setVersion(mockApplication.getVersion()).setWebsiteAddress(mockApplication.getWebsiteAddress());

        FRApplication application = builder.build();

        assert (application != null);
        Assert.assertEquals(mockApplication.getName(), application.getName());
        Assert.assertEquals(mockApplication.getVersion(), application.getVersion());
        Assert.assertEquals(mockApplication.getDeveloperCompanyName(), application.getDeveloperCompanyName());
        Assert.assertEquals(mockApplication.getDeveloperCompanyTaxIdentifier(),
                application.getDeveloperCompanyTaxIdentifier());
        Assert.assertEquals(mockApplication.getWebsiteAddress(), application.getWebsiteAddress());
        assert (application.getContacts() != null);
        assert (application.getMainContact() != null);

    }
}
