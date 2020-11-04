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
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOBusiness;
import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockBusinessEntity;

public class TestBusinessBuilder extends AbstractTest {

    private static final String BUSINESS_YML = AbstractTest.YML_CONFIGS_DIR + "Business.yml";

    @Test
    public void doTest() {
        MockBusinessEntity mockBusiness =
                this.createMockEntity(MockBusinessEntity.class, TestBusinessBuilder.BUSINESS_YML);

        Mockito.when(this.getInstance(DAOBusiness.class).getEntityInstance()).thenReturn(new MockBusinessEntity());

        Mockito.when(this.getInstance(DAOContext.class).get(Matchers.any(UID.class)))
                .thenReturn((ContextEntity) mockBusiness.getOperationalContext());

        Business.Builder builder = this.getInstance(Business.Builder.class);

        Contact.Builder mockMainContactBuilder = this.getMock(Contact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn(mockBusiness.getMainContact());

        Application.Builder mockApplicationBuilder = this.getMock(Application.Builder.class);
        Mockito.when(mockApplicationBuilder.build()).thenReturn(mockBusiness.getApplications().get(0));

        Address.Builder mockAddressBuilder = this.getMock(Address.Builder.class);
        Mockito.when(mockAddressBuilder.build()).thenReturn(mockBusiness.getAddress());

        Address.Builder mockShippingAddressBuilder = this.getMock(Address.Builder.class);
        Mockito.when(mockShippingAddressBuilder.build()).thenReturn(mockBusiness.getShippingAddress());

        Address.Builder mockBillingAddressBuilder = this.getMock(Address.Builder.class);
        Mockito.when(mockBillingAddressBuilder.build()).thenReturn(mockBusiness.getBillingAddress());

        builder.setFinancialID(mockBusiness.getFinancialID(), null).setName(mockBusiness.getName())
                .setCommercialName(mockBusiness.getCommercialName()).setAddress(mockAddressBuilder)
                .setBillingAddress(mockBillingAddressBuilder).setShippingAddress(mockShippingAddressBuilder)
                .addApplication(mockApplicationBuilder).addContact(mockMainContactBuilder, true)
                .setWebsite(mockBusiness.getWebsiteAddress())
                .setOperationalContextUID(mockBusiness.getOperationalContext().getUID());

        Business business = builder.build();

        Assertions.assertTrue(business != null);

        Assertions.assertEquals(mockBusiness.getFinancialID(), business.getFinancialID());
        Assertions.assertEquals(mockBusiness.getName(), business.getName());
        Assertions.assertEquals(mockBusiness.getWebsiteAddress(), business.getWebsiteAddress());
        Assertions.assertEquals(mockBusiness.getCommercialName(), business.getCommercialName());
        Assertions.assertEquals(mockBusiness.getAddress().getNumber(), business.getAddress().getNumber());

        Assertions.assertTrue(business.getContacts() != null);

        Assertions.assertTrue(business.getApplications() != null);
        Assertions.assertEquals(mockBusiness.getApplications().size(), business.getApplications().size());
    }
}
