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
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.france.persistence.dao.DAOFRContact;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.entities.FRAddressEntity;
import com.premiumminds.billy.france.persistence.entities.FRContactEntity;
import com.premiumminds.billy.france.services.entities.FRAddress;
import com.premiumminds.billy.france.services.entities.FRContact;
import com.premiumminds.billy.france.services.entities.FRCustomer;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRCustomerEntity;

public class TestFRCustomerBuilder extends FRAbstractTest {

    private static final String FRCUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "FRCustomer.yml";

    @Test
    public void doTest() {
        MockFRCustomerEntity mockCustomer =
                this.createMockEntity(MockFRCustomerEntity.class, TestFRCustomerBuilder.FRCUSTOMER_YML);

        Mockito.when(this.getInstance(DAOFRCustomer.class).getEntityInstance()).thenReturn(new MockFRCustomerEntity());

        Mockito.when(this.getInstance(DAOFRContact.class).get(Matchers.any(UID.class)))
                .thenReturn((FRContactEntity) mockCustomer.getMainContact());

        FRCustomer.Builder builder = this.getInstance(FRCustomer.Builder.class);

        FRAddress.Builder mockMainAddressBuilder = this.getMock(FRAddress.Builder.class);
        Mockito.when(mockMainAddressBuilder.build()).thenReturn((FRAddressEntity) mockCustomer.getMainAddress());

        FRAddress.Builder mockBillingAddressBuilder = this.getMock(FRAddress.Builder.class);
        Mockito.when(mockBillingAddressBuilder.build()).thenReturn((FRAddressEntity) mockCustomer.getBillingAddress());

        FRAddress.Builder mockShippingAddressBuilder = this.getMock(FRAddress.Builder.class);
        Mockito.when(mockShippingAddressBuilder.build())
                .thenReturn((FRAddressEntity) mockCustomer.getShippingAddress());

        FRContact.Builder mockMainContactBuilder = this.getMock(FRContact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn((FRContactEntity) mockCustomer.getMainContact());

        FRContact.Builder mockContactBuilder1 = this.getMock(FRContact.Builder.class);
        Mockito.when(mockContactBuilder1.build()).thenReturn((FRContactEntity) mockCustomer.getContacts().get(0));

        FRContact.Builder mockContactBuilder2 = this.getMock(FRContact.Builder.class);
        Mockito.when(mockContactBuilder2.build()).thenReturn((FRContactEntity) mockCustomer.getContacts().get(1));

        builder.addAddress(mockMainAddressBuilder, true).addContact(mockMainContactBuilder)
                .addContact(mockContactBuilder1).addContact(mockContactBuilder2)
                .setBillingAddress(mockBillingAddressBuilder)
                .setHasSelfBillingAgreement(mockCustomer.hasSelfBillingAgreement()).setName(mockCustomer.getName())
                .setShippingAddress(mockShippingAddressBuilder)
                .setTaxRegistrationNumber(mockCustomer.getTaxRegistrationNumber(), FRAbstractTest.FR_COUNTRY_CODE)
                .setMainContactUID(mockCustomer.getMainContact().getUID());

        Customer customer = builder.build();

        Assert.assertTrue(customer != null);

        Assert.assertEquals(mockCustomer.getName(), customer.getName());
        Assert.assertEquals(mockCustomer.getTaxRegistrationNumber(), customer.getTaxRegistrationNumber());
        Assert.assertEquals(mockCustomer.getMainAddress(), customer.getMainAddress());
        Assert.assertEquals(mockCustomer.getShippingAddress(), customer.getShippingAddress());
        Assert.assertEquals(mockCustomer.hasSelfBillingAgreement(), customer.hasSelfBillingAgreement());
    }
}
