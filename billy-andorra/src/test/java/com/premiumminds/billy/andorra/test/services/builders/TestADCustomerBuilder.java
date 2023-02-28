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

import com.premiumminds.billy.andorra.persistence.dao.DAOADContact;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.entities.ADAddressEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADContactEntity;
import com.premiumminds.billy.andorra.services.entities.ADAddress;
import com.premiumminds.billy.andorra.services.entities.ADContact;
import com.premiumminds.billy.andorra.services.entities.ADCustomer.Builder;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.fixtures.MockADCustomerEntity;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.test.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestADCustomerBuilder extends ADAbstractTest {

    private static final String ESCUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "ESCustomer.yml";

    @Test
    public void doTest() {
        MockADCustomerEntity mockCustomer =
                this.createMockEntity(MockADCustomerEntity.class, TestADCustomerBuilder.ESCUSTOMER_YML);

        Mockito.when(this.getInstance(DAOADCustomer.class).getEntityInstance()).thenReturn(new MockADCustomerEntity());

        Mockito.when(this.getInstance(DAOADContact.class).get(Mockito.any()))
                .thenReturn((ADContactEntity) mockCustomer.getMainContact());

        Builder builder = this.getInstance(Builder.class);

        ADAddress.Builder mockMainAddressBuilder = this.getMock(ADAddress.Builder.class);
        Mockito.when(mockMainAddressBuilder.build()).thenReturn((ADAddressEntity) mockCustomer.getMainAddress());

        ADAddress.Builder mockBillingAddressBuilder = this.getMock(ADAddress.Builder.class);
        Mockito.when(mockBillingAddressBuilder.build()).thenReturn((ADAddressEntity) mockCustomer.getBillingAddress());

        ADAddress.Builder mockShippingAddressBuilder = this.getMock(ADAddress.Builder.class);
        Mockito.when(mockShippingAddressBuilder.build())
                .thenReturn((ADAddressEntity) mockCustomer.getShippingAddress());

        ADContact.Builder mockMainContactBuilder = this.getMock(ADContact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn((ADContactEntity) mockCustomer.getMainContact());

        ADContact.Builder mockContactBuilder1 = this.getMock(ADContact.Builder.class);
        Mockito.when(mockContactBuilder1.build()).thenReturn((ADContactEntity) mockCustomer.getContacts().get(0));

        ADContact.Builder mockContactBuilder2 = this.getMock(ADContact.Builder.class);
        Mockito.when(mockContactBuilder2.build()).thenReturn((ADContactEntity) mockCustomer.getContacts().get(1));

        builder.addAddress(mockMainAddressBuilder, true).addContact(mockMainContactBuilder)
                .addContact(mockContactBuilder1).addContact(mockContactBuilder2)
                .setBillingAddress(mockBillingAddressBuilder)
                .setHasSelfBillingAgreement(mockCustomer.hasSelfBillingAgreement()).setName(mockCustomer.getName())
                .setShippingAddress(mockShippingAddressBuilder)
                .setTaxRegistrationNumber(mockCustomer.getTaxRegistrationNumber(), mockCustomer.getTaxRegistrationNumberISOCountryCode())
                .setMainContactUID(mockCustomer.getMainContact().getUID());

        Customer customer = builder.build();

        Assertions.assertTrue(customer != null);

        Assertions.assertEquals(mockCustomer.getName(), customer.getName());
        Assertions.assertEquals(mockCustomer.getTaxRegistrationNumber(), customer.getTaxRegistrationNumber());
        Assertions.assertEquals(mockCustomer.getTaxRegistrationNumberISOCountryCode(), mockCustomer.getTaxRegistrationNumberISOCountryCode());
        Assertions.assertEquals(mockCustomer.getMainAddress(), customer.getMainAddress());
        Assertions.assertEquals(mockCustomer.getShippingAddress(), customer.getShippingAddress());
        Assertions.assertEquals(mockCustomer.hasSelfBillingAgreement(), customer.hasSelfBillingAgreement());
    }
}
