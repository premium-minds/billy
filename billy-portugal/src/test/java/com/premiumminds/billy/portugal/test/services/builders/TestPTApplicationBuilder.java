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

import java.net.MalformedURLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTApplication;
import com.premiumminds.billy.portugal.persistence.entities.PTContactEntity;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTApplicationEntity;

public class TestPTApplicationBuilder extends PTAbstractTest {

    private static final String PTAPPLICATION_YML = AbstractTest.YML_CONFIGS_DIR + "PTApplication.yml";

    @Test
    public void doTest() throws MalformedURLException {

        MockPTApplicationEntity mockApplication =
                this.createMockEntity(MockPTApplicationEntity.class, TestPTApplicationBuilder.PTAPPLICATION_YML);

        Mockito.when(this.getInstance(DAOPTApplication.class).getEntityInstance())
                .thenReturn(new MockPTApplicationEntity());

        PTApplication.Builder builder = this.getInstance(PTApplication.Builder.class);

        PTContact.Builder mockContactBuilder = this.getMock(PTContact.Builder.class);
        Mockito.when(mockContactBuilder.build()).thenReturn(Mockito.mock(PTContactEntity.class));

        PTContact.Builder mockMainContactBuilder = this.getMock(PTContact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn(Mockito.mock(PTContactEntity.class));

        builder.addContact(mockContactBuilder).addContact(mockMainContactBuilder)
                .setDeveloperCompanyName(mockApplication.getDeveloperCompanyName())
                .setDeveloperCompanyTaxIdentifier(mockApplication.getDeveloperCompanyTaxIdentifier())
                .setDeveloperCompanyTaxIdentifierISOCountryCode(mockApplication.getDeveloperCompanyTaxIdentifierISOCountryCode())
                .setMainContact(mockMainContactBuilder).setName(mockApplication.getName())
                .setVersion(mockApplication.getVersion()).setWebsiteAddress(mockApplication.getWebsiteAddress())
                .setSoftwareCertificationNumber(mockApplication.getSoftwareCertificationNumber())
                .setApplicationKeysPath(mockApplication.getApplicationKeysPath());

        PTApplication application = builder.build();

        Assertions.assertNotNull(application);
        Assertions.assertEquals(mockApplication.getName(), application.getName());
        Assertions.assertEquals(mockApplication.getVersion(), application.getVersion());
        Assertions.assertEquals(mockApplication.getDeveloperCompanyName(), application.getDeveloperCompanyName());
        Assertions.assertEquals(mockApplication.getDeveloperCompanyTaxIdentifier(),
                application.getDeveloperCompanyTaxIdentifier());
        Assertions.assertEquals(mockApplication.getDeveloperCompanyTaxIdentifierISOCountryCode(),
                application.getDeveloperCompanyTaxIdentifierISOCountryCode());
        Assertions.assertEquals(mockApplication.getWebsiteAddress(), application.getWebsiteAddress());
        Assertions.assertEquals(mockApplication.getSoftwareCertificationNumber(),
                application.getSoftwareCertificationNumber());
        Assertions.assertEquals(mockApplication.getApplicationKeysPath(), application.getApplicationKeysPath());
        Assertions.assertNotNull(application.getContacts());
        Assertions.assertNotNull(application.getMainContact());

    }
}
