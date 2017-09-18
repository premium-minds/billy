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
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.france.persistence.dao.DAOFRBusiness;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.entities.FRAddressEntity;
import com.premiumminds.billy.france.persistence.entities.FRApplicationEntity;
import com.premiumminds.billy.france.persistence.entities.FRContactEntity;
import com.premiumminds.billy.france.persistence.entities.FRRegionContextEntity;
import com.premiumminds.billy.france.services.entities.FRAddress;
import com.premiumminds.billy.france.services.entities.FRApplication;
import com.premiumminds.billy.france.services.entities.FRBusiness;
import com.premiumminds.billy.france.services.entities.FRContact;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRBusinessEntity;

public class TestFRBusinessBuilder extends FRAbstractTest {

    private static final String FRBUSINESS_YML = AbstractTest.YML_CONFIGS_DIR + "FRBusiness.yml";

    @Test
    public void doTest() {
        MockFRBusinessEntity mockBusiness =
                this.createMockEntity(MockFRBusinessEntity.class, TestFRBusinessBuilder.FRBUSINESS_YML);

        Mockito.when(this.getInstance(DAOFRBusiness.class).getEntityInstance()).thenReturn(new MockFRBusinessEntity());

        Mockito.when(this.getInstance(DAOFRRegionContext.class).get(Matchers.any(UID.class)))
                .thenReturn((FRRegionContextEntity) mockBusiness.getOperationalContext());

        FRBusiness.Builder builder = this.getInstance(FRBusiness.Builder.class);

        FRContact.Builder mockMainContactBuilder = this.getMock(FRContact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn((FRContactEntity) mockBusiness.getMainContact());

        FRApplication.Builder mockApplicationBuilder = this.getMock(FRApplication.Builder.class);
        Mockito.when(mockApplicationBuilder.build())
                .thenReturn((FRApplicationEntity) mockBusiness.getApplications().get(0));

        FRAddress.Builder mockAddressBuilder = this.getMock(FRAddress.Builder.class);
        Mockito.when(mockAddressBuilder.build()).thenReturn((FRAddressEntity) mockBusiness.getAddress());

        FRAddress.Builder mockShippingAddressBuilder = this.getMock(FRAddress.Builder.class);
        Mockito.when(mockShippingAddressBuilder.build())
                .thenReturn((FRAddressEntity) mockBusiness.getShippingAddress());

        FRAddress.Builder mockBillingAddressBuilder = this.getMock(FRAddress.Builder.class);
        Mockito.when(mockBillingAddressBuilder.build()).thenReturn((FRAddressEntity) mockBusiness.getBillingAddress());

        builder.setFinancialID(mockBusiness.getFinancialID(), FRAbstractTest.FR_COUNTRY_CODE)
                .setName(mockBusiness.getName()).setAddress(mockAddressBuilder)
                .setBillingAddress(mockBillingAddressBuilder).setShippingAddress(mockShippingAddressBuilder)
                .addApplication(mockApplicationBuilder).addContact(mockMainContactBuilder, true)
                .setWebsite(mockBusiness.getWebsiteAddress())
                .setOperationalContextUID(mockBusiness.getOperationalContext().getUID())
                .setCommercialName(mockBusiness.getCommercialName());

        Business business = builder.build();

        Assert.assertTrue(business != null);

        Assert.assertEquals(mockBusiness.getFinancialID(), business.getFinancialID());
        Assert.assertEquals(mockBusiness.getName(), business.getName());
        Assert.assertEquals(mockBusiness.getWebsiteAddress(), business.getWebsiteAddress());
        Assert.assertEquals(mockBusiness.getAddress().getNumber(), business.getAddress().getNumber());

        Assert.assertTrue(business.getContacts() != null);

        Assert.assertTrue(business.getApplications() != null);
        Assert.assertEquals(mockBusiness.getApplications().size(), business.getApplications().size());
    }
}
