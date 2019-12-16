/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.builders;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTContact;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.entities.PTAddressEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTContactEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTCustomer;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTCustomerEntity;

public class TestPTCustomerBuilder extends PTAbstractTest {

    /*private static final String PTCUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "PTCustomer.yml";

    @Test
    public void doTest() {
        MockPTCustomerEntity mockCustomer =
                this.createMockEntity(MockPTCustomerEntity.class, TestPTCustomerBuilder.PTCUSTOMER_YML);

        Mockito.when(this.getInstance(DAOPTCustomer.class).getEntityInstance()).thenReturn(new MockPTCustomerEntity());

        Mockito.when(this.getInstance(DAOPTContact.class).get(Matchers.any(UID.class)))
                .thenReturn((PTContactEntity) mockCustomer.getMainContact());

        PTCustomer.Builder builder = this.getInstance(PTCustomer.Builder.class);

        PTAddress.Builder mockMainAddressBuilder = this.getMock(PTAddress.Builder.class);
        Mockito.when(mockMainAddressBuilder.build()).thenReturn((PTAddressEntity) mockCustomer.getMainAddress());

        PTAddress.Builder mockBillingAddressBuilder = this.getMock(PTAddress.Builder.class);
        Mockito.when(mockBillingAddressBuilder.build()).thenReturn((PTAddressEntity) mockCustomer.getBillingAddress());

        PTAddress.Builder mockShippingAddressBuilder = this.getMock(PTAddress.Builder.class);
        Mockito.when(mockShippingAddressBuilder.build())
                .thenReturn((PTAddressEntity) mockCustomer.getShippingAddress());

        PTContact.Builder mockMainContactBuilder = this.getMock(PTContact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn((PTContactEntity) mockCustomer.getMainContact());

        PTContact.Builder mockContactBuilder1 = this.getMock(PTContact.Builder.class);
        Mockito.when(mockContactBuilder1.build()).thenReturn((PTContactEntity) mockCustomer.getContacts().get(0));

        PTContact.Builder mockContactBuilder2 = this.getMock(PTContact.Builder.class);
        Mockito.when(mockContactBuilder2.build()).thenReturn((PTContactEntity) mockCustomer.getContacts().get(1));

        builder.addAddress(mockMainAddressBuilder, true).addContact(mockMainContactBuilder)
                .addContact(mockContactBuilder1).addContact(mockContactBuilder2)
                .setBillingAddress(mockBillingAddressBuilder)
                .setHasSelfBillingAgreement(mockCustomer.hasSelfBillingAgreement()).setName(mockCustomer.getName())
                .setShippingAddress(mockShippingAddressBuilder)
                .setTaxRegistrationNumber(mockCustomer.getTaxRegistrationNumber(), PTAbstractTest.PT_COUNTRY_CODE)
                .setMainContactUID(mockCustomer.getMainContact().getUID());

        Customer customer = builder.build();

        Assert.assertTrue(customer != null);

        Assert.assertEquals(mockCustomer.getName(), customer.getName());
        Assert.assertEquals(mockCustomer.getTaxRegistrationNumber(), customer.getTaxRegistrationNumber());
        Assert.assertEquals(mockCustomer.getMainAddress(), customer.getMainAddress());
        Assert.assertEquals(mockCustomer.getShippingAddress(), customer.getShippingAddress());
        Assert.assertEquals(mockCustomer.hasSelfBillingAgreement(), customer.hasSelfBillingAgreement());
    }*/
}
