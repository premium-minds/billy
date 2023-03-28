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

import com.premiumminds.billy.andorra.persistence.dao.DAOADApplication;
import com.premiumminds.billy.andorra.persistence.entities.ADContactEntity;
import com.premiumminds.billy.andorra.services.entities.ADApplication;
import com.premiumminds.billy.andorra.services.entities.ADApplication.Builder;
import com.premiumminds.billy.andorra.services.entities.ADContact;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.fixtures.MockADApplicationEntity;
import java.net.MalformedURLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;

public class TestADApplicationBuilder extends ADAbstractTest {

    private static final String AD_APPLICATION_YML = AbstractTest.YML_CONFIGS_DIR + "ADApplication.yml";

    @Test
    public void doTest() throws MalformedURLException {

        MockADApplicationEntity mockApplication =
                this.createMockEntity(MockADApplicationEntity.class, TestADApplicationBuilder.AD_APPLICATION_YML);

        Mockito.when(this.getInstance(DAOADApplication.class).getEntityInstance())
                .thenReturn(new MockADApplicationEntity());

        Builder builder = this.getInstance(Builder.class);

        ADContact.Builder mockContactBuilder = this.getMock(ADContact.Builder.class);
        Mockito.when(mockContactBuilder.build()).thenReturn(Mockito.mock(ADContactEntity.class));

        ADContact.Builder mockMainContactBuilder = this.getMock(ADContact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn(Mockito.mock(ADContactEntity.class));

        builder.addContact(mockContactBuilder).addContact(mockMainContactBuilder)
                .setDeveloperCompanyName(mockApplication.getDeveloperCompanyName())
                .setDeveloperCompanyTaxIdentifier(
                    mockApplication.getDeveloperCompanyTaxIdentifier(),
                    mockApplication.getDeveloperCompanyTaxIdentifierISOCountryCode())
                .setMainContact(mockMainContactBuilder).setName(mockApplication.getName())
                .setVersion(mockApplication.getVersion()).setWebsiteAddress(mockApplication.getWebsiteAddress());

        ADApplication application = builder.build();

        Assertions.assertNotNull(application);
        Assertions.assertEquals(mockApplication.getName(), application.getName());
        Assertions.assertEquals(mockApplication.getVersion(), application.getVersion());
        Assertions.assertEquals(mockApplication.getDeveloperCompanyName(), application.getDeveloperCompanyName());
        Assertions.assertEquals(mockApplication.getDeveloperCompanyTaxIdentifier(),
                application.getDeveloperCompanyTaxIdentifier());
        Assertions.assertEquals(mockApplication.getDeveloperCompanyTaxIdentifierISOCountryCode(),
                application.getDeveloperCompanyTaxIdentifierISOCountryCode());
        Assertions.assertEquals(mockApplication.getWebsiteAddress(), application.getWebsiteAddress());
        Assertions.assertNotNull(application.getContacts());
        Assertions.assertNotNull(application.getMainContact());

    }
}
