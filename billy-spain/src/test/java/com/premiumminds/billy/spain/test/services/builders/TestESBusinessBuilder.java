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

import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.entities.ESAddressEntity;
import com.premiumminds.billy.spain.persistence.entities.ESApplicationEntity;
import com.premiumminds.billy.spain.persistence.entities.ESContactEntity;
import com.premiumminds.billy.spain.persistence.entities.ESRegionContextEntity;
import com.premiumminds.billy.spain.services.entities.ESAddress;
import com.premiumminds.billy.spain.services.entities.ESApplication;
import com.premiumminds.billy.spain.services.entities.ESBusiness;
import com.premiumminds.billy.spain.services.entities.ESContact;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESBusinessEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestESBusinessBuilder extends ESAbstractTest {

    private static final String ESBUSINESS_YML = AbstractTest.YML_CONFIGS_DIR + "ESBusiness.yml";

    @Test
    public void doTest() {
        MockESBusinessEntity mockBusiness =
                this.createMockEntity(MockESBusinessEntity.class, TestESBusinessBuilder.ESBUSINESS_YML);

        Mockito.when(this.getInstance(DAOESBusiness.class).getEntityInstance()).thenReturn(new MockESBusinessEntity());

        Mockito.when(this.getInstance(DAOESRegionContext.class).get(Mockito.any()))
                .thenReturn((ESRegionContextEntity) mockBusiness.getOperationalContext());

        ESBusiness.Builder builder = this.getInstance(ESBusiness.Builder.class);

        ESContact.Builder mockMainContactBuilder = this.getMock(ESContact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn((ESContactEntity) mockBusiness.getMainContact());

        ESApplication.Builder mockApplicationBuilder = this.getMock(ESApplication.Builder.class);
        Mockito.when(mockApplicationBuilder.build())
                .thenReturn((ESApplicationEntity) mockBusiness.getApplications().get(0));

        ESAddress.Builder mockAddressBuilder = this.getMock(ESAddress.Builder.class);
        Mockito.when(mockAddressBuilder.build()).thenReturn((ESAddressEntity) mockBusiness.getAddress());

        ESAddress.Builder mockShippingAddressBuilder = this.getMock(ESAddress.Builder.class);
        Mockito.when(mockShippingAddressBuilder.build())
                .thenReturn((ESAddressEntity) mockBusiness.getShippingAddress());

        ESAddress.Builder mockBillingAddressBuilder = this.getMock(ESAddress.Builder.class);
        Mockito.when(mockBillingAddressBuilder.build()).thenReturn((ESAddressEntity) mockBusiness.getBillingAddress());

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
