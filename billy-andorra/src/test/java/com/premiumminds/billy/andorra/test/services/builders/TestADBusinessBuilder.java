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

import com.premiumminds.billy.andorra.persistence.dao.DAOADBusiness;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.persistence.entities.ADAddressEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADApplicationEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADContactEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADRegionContextEntity;
import com.premiumminds.billy.andorra.services.entities.ADAddress;
import com.premiumminds.billy.andorra.services.entities.ADApplication;
import com.premiumminds.billy.andorra.services.entities.ADBusiness;
import com.premiumminds.billy.andorra.services.entities.ADContact;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.fixtures.MockADBusinessEntity;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.test.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestADBusinessBuilder extends ADAbstractTest {

    private static final String AD_BUSINESS_YML = AbstractTest.YML_CONFIGS_DIR + "ADBusiness.yml";

    @Test
    public void doTest() {
        MockADBusinessEntity mockBusiness =
                this.createMockEntity(MockADBusinessEntity.class, TestADBusinessBuilder.AD_BUSINESS_YML);

        Mockito.when(this.getInstance(DAOADBusiness.class).getEntityInstance()).thenReturn(new MockADBusinessEntity());

        Mockito.when(this.getInstance(DAOADRegionContext.class).get(Mockito.any()))
                .thenReturn((ADRegionContextEntity) mockBusiness.getOperationalContext());

        ADBusiness.Builder builder = this.getInstance(ADBusiness.Builder.class);

        ADContact.Builder mockMainContactBuilder = this.getMock(ADContact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn((ADContactEntity) mockBusiness.getMainContact());

        ADApplication.Builder mockApplicationBuilder = this.getMock(ADApplication.Builder.class);
        Mockito.when(mockApplicationBuilder.build())
                .thenReturn((ADApplicationEntity) mockBusiness.getApplications().get(0));

        ADAddress.Builder mockAddressBuilder = this.getMock(ADAddress.Builder.class);
        Mockito.when(mockAddressBuilder.build()).thenReturn((ADAddressEntity) mockBusiness.getAddress());

        ADAddress.Builder mockShippingAddressBuilder = this.getMock(ADAddress.Builder.class);
        Mockito.when(mockShippingAddressBuilder.build())
                .thenReturn((ADAddressEntity) mockBusiness.getShippingAddress());

        ADAddress.Builder mockBillingAddressBuilder = this.getMock(ADAddress.Builder.class);
        Mockito.when(mockBillingAddressBuilder.build()).thenReturn((ADAddressEntity) mockBusiness.getBillingAddress());

        builder.setFinancialID(mockBusiness.getFinancialID(), mockBusiness.getFinancialIdISOCountryCode())
                .setName(mockBusiness.getName()).setAddress(mockAddressBuilder)
                .setBillingAddress(mockBillingAddressBuilder).setShippingAddress(mockShippingAddressBuilder)
                .addApplication(mockApplicationBuilder).addContact(mockMainContactBuilder, true)
                .setWebsite(mockBusiness.getWebsiteAddress())
                .setOperationalContextUID(mockBusiness.getOperationalContext().getUID())
                .setCommercialName(mockBusiness.getCommercialName())
                .setTimezone(mockBusiness.getTimezone());

        Business business = builder.build();

        Assertions.assertNotNull(business);

        Assertions.assertEquals(mockBusiness.getFinancialID(), business.getFinancialID());
        Assertions.assertEquals(mockBusiness.getFinancialIdISOCountryCode(), business.getFinancialIdISOCountryCode());
        Assertions.assertEquals(mockBusiness.getName(), business.getName());
        Assertions.assertEquals(mockBusiness.getWebsiteAddress(), business.getWebsiteAddress());
        Assertions.assertEquals(mockBusiness.getAddress().getNumber(), business.getAddress().getNumber());

        Assertions.assertNotNull(business.getContacts());

        Assertions.assertNotNull(business.getApplications());
        Assertions.assertEquals(mockBusiness.getApplications().size(), business.getApplications().size());
    }
}
