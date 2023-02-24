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

import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.entities.PTAddressEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTContactEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTBusinessEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestPTBusinessBuilder extends PTAbstractTest {

    private static final String PTBUSINESS_YML = AbstractTest.YML_CONFIGS_DIR + "PTBusiness.yml";

    @Test
    public void doTest() {
        MockPTBusinessEntity mockBusiness =
                this.createMockEntity(MockPTBusinessEntity.class, TestPTBusinessBuilder.PTBUSINESS_YML);

        Mockito.when(this.getInstance(DAOPTBusiness.class).getEntityInstance()).thenReturn(new MockPTBusinessEntity());

        Mockito.when(this.getInstance(DAOPTRegionContext.class).get(Mockito.any()))
                .thenReturn((PTRegionContextEntity) mockBusiness.getOperationalContext());

        PTBusiness.Builder builder = this.getInstance(PTBusiness.Builder.class);

        PTContact.Builder mockMainContactBuilder = this.getMock(PTContact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn((PTContactEntity) mockBusiness.getMainContact());

        PTApplication.Builder mockApplicationBuilder = this.getMock(PTApplication.Builder.class);
        Mockito.when(mockApplicationBuilder.build())
                .thenReturn((PTApplicationEntity) mockBusiness.getApplications().get(0));

        PTAddress.Builder mockAddressBuilder = this.getMock(PTAddress.Builder.class);
        Mockito.when(mockAddressBuilder.build()).thenReturn((PTAddressEntity) mockBusiness.getAddress());

        PTAddress.Builder mockShippingAddressBuilder = this.getMock(PTAddress.Builder.class);
        Mockito.when(mockShippingAddressBuilder.build())
                .thenReturn((PTAddressEntity) mockBusiness.getShippingAddress());

        PTAddress.Builder mockBillingAddressBuilder = this.getMock(PTAddress.Builder.class);
        Mockito.when(mockBillingAddressBuilder.build()).thenReturn((PTAddressEntity) mockBusiness.getBillingAddress());

        builder.setFinancialID(mockBusiness.getFinancialID(), mockBusiness.getFinancialIdISOCountryCode())
                .setName(mockBusiness.getName()).setAddress(mockAddressBuilder)
                .setBillingAddress(mockBillingAddressBuilder).setShippingAddress(mockShippingAddressBuilder)
                .addApplication(mockApplicationBuilder).addContact(mockMainContactBuilder, true)
                .setWebsite(mockBusiness.getWebsiteAddress())
                .setOperationalContextUID(mockBusiness.getOperationalContext().getUID())
                .setCommercialName(mockBusiness.getCommercialName());

        Business business = builder.build();

        Assertions.assertTrue(business != null);

        Assertions.assertEquals(mockBusiness.getFinancialID(), business.getFinancialID());
        Assertions.assertEquals(mockBusiness.getFinancialIdISOCountryCode(), mockBusiness.getFinancialIdISOCountryCode());
        Assertions.assertEquals(mockBusiness.getName(), business.getName());
        Assertions.assertEquals(mockBusiness.getWebsiteAddress(), business.getWebsiteAddress());
        Assertions.assertEquals(mockBusiness.getAddress().getNumber(), business.getAddress().getNumber());

        Assertions.assertTrue(business.getContacts() != null);

        Assertions.assertTrue(business.getApplications() != null);
        Assertions.assertEquals(mockBusiness.getApplications().size(), business.getApplications().size());
    }
}
