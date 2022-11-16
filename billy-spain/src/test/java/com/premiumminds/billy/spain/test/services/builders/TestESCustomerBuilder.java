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

import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.spain.persistence.dao.DAOESContact;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.entities.ESAddressEntity;
import com.premiumminds.billy.spain.persistence.entities.ESContactEntity;
import com.premiumminds.billy.spain.services.entities.ESAddress;
import com.premiumminds.billy.spain.services.entities.ESContact;
import com.premiumminds.billy.spain.services.entities.ESCustomer;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESCustomerEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestESCustomerBuilder extends ESAbstractTest {

    private static final String ESCUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "ESCustomer.yml";

    @Test
    public void doTest() {
        MockESCustomerEntity mockCustomer =
                this.createMockEntity(MockESCustomerEntity.class, TestESCustomerBuilder.ESCUSTOMER_YML);

        Mockito.when(this.getInstance(DAOESCustomer.class).getEntityInstance()).thenReturn(new MockESCustomerEntity());

        Mockito.when(this.getInstance(DAOESContact.class).get(Mockito.any()))
                .thenReturn((ESContactEntity) mockCustomer.getMainContact());

        ESCustomer.Builder builder = this.getInstance(ESCustomer.Builder.class);

        ESAddress.Builder mockMainAddressBuilder = this.getMock(ESAddress.Builder.class);
        Mockito.when(mockMainAddressBuilder.build()).thenReturn((ESAddressEntity) mockCustomer.getMainAddress());

        ESAddress.Builder mockBillingAddressBuilder = this.getMock(ESAddress.Builder.class);
        Mockito.when(mockBillingAddressBuilder.build()).thenReturn((ESAddressEntity) mockCustomer.getBillingAddress());

        ESAddress.Builder mockShippingAddressBuilder = this.getMock(ESAddress.Builder.class);
        Mockito.when(mockShippingAddressBuilder.build())
                .thenReturn((ESAddressEntity) mockCustomer.getShippingAddress());

        ESContact.Builder mockMainContactBuilder = this.getMock(ESContact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn((ESContactEntity) mockCustomer.getMainContact());

        ESContact.Builder mockContactBuilder1 = this.getMock(ESContact.Builder.class);
        Mockito.when(mockContactBuilder1.build()).thenReturn((ESContactEntity) mockCustomer.getContacts().get(0));

        ESContact.Builder mockContactBuilder2 = this.getMock(ESContact.Builder.class);
        Mockito.when(mockContactBuilder2.build()).thenReturn((ESContactEntity) mockCustomer.getContacts().get(1));

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
