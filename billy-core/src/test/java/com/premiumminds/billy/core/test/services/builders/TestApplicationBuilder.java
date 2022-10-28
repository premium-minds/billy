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

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOApplication;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockApplicationEntity;

public class TestApplicationBuilder extends AbstractTest {

    private static final String APPLICATION_YML = AbstractTest.YML_CONFIGS_DIR + "Application.yml";

    @Test
    public void doTest() {
        MockApplicationEntity mockApplication =
                this.createMockEntity(MockApplicationEntity.class, TestApplicationBuilder.APPLICATION_YML);

        Mockito.when(this.getInstance(DAOApplication.class).getEntityInstance())
                .thenReturn(new MockApplicationEntity());

        Application.Builder builder = this.getInstance(Application.Builder.class);

        ArrayList<ContactEntity> contacts = (ArrayList<ContactEntity>) mockApplication.getContacts();

        Contact.Builder mockContactBuilder1 = this.getMock(Contact.Builder.class);
        Mockito.when(mockContactBuilder1.build()).thenReturn(contacts.get(0));

        Contact.Builder mockContactBuilder2 = this.getMock(Contact.Builder.class);
        Mockito.when(mockContactBuilder2.build()).thenReturn(contacts.get(1));

        Contact.Builder mockMainContactBuilder = this.getMock(Contact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn(mockApplication.getMainContact());

        builder.addContact(mockContactBuilder1).addContact(mockContactBuilder2).addContact(mockMainContactBuilder)
                .setDeveloperCompanyName(mockApplication.getDeveloperCompanyName())
                .setDeveloperCompanyTaxIdentifier(mockApplication.getDeveloperCompanyTaxIdentifier())
                .setDeveloperCompanyTaxIdentifierISOCountryCode(mockApplication.getDeveloperCompanyTaxIdentifierISOCountryCode())
                .setMainContact(mockMainContactBuilder).setName(mockApplication.getName())
                .setVersion(mockApplication.getVersion()).setWebsiteAddress(mockApplication.getWebsiteAddress());

        Application application = builder.build();

        Assertions.assertTrue(application != null);
        Assertions.assertEquals(mockApplication.getName(), application.getName());
        Assertions.assertEquals(mockApplication.getVersion(), application.getVersion());
        Assertions.assertEquals(mockApplication.getDeveloperCompanyName(), application.getDeveloperCompanyName());
        Assertions.assertEquals(mockApplication.getDeveloperCompanyTaxIdentifier(),
                application.getDeveloperCompanyTaxIdentifier());
        Assertions.assertEquals(mockApplication.getWebsiteAddress(), application.getWebsiteAddress());

        Assertions.assertTrue(application.getMainContact() != null);
        Assertions.assertEquals(application.getMainContact().getName(), mockApplication.getMainContact().getName());

        Assertions.assertTrue(application.getContacts() != null);
        Assertions.assertEquals(application.getContacts().size(), mockApplication.getContacts().size() + 1);

        for (int i = 0; i < application.getContacts().size() - 1; i++) {
            List<Contact> appContacts = (List<Contact>) application.getContacts();
            Assertions.assertEquals(appContacts.get(i).getName(), mockApplication.getContacts().get(i).getName());
        }

    }

}
