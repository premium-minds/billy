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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.spain.persistence.dao.DAOESSupplier;
import com.premiumminds.billy.spain.persistence.entities.ESAddressEntity;
import com.premiumminds.billy.spain.persistence.entities.ESContactEntity;
import com.premiumminds.billy.spain.services.entities.ESAddress;
import com.premiumminds.billy.spain.services.entities.ESContact;
import com.premiumminds.billy.spain.services.entities.ESSupplier;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESSupplierEntity;

public class TestESSupplierBuilder extends ESAbstractTest {

    private static final String ESSUPPLIER_YML = AbstractTest.YML_CONFIGS_DIR + "ESSupplier.yml";

    @Test
    public void doTest() {
        MockESSupplierEntity mockSupplier =
                this.createMockEntity(MockESSupplierEntity.class, TestESSupplierBuilder.ESSUPPLIER_YML);

        Mockito.when(this.getInstance(DAOESSupplier.class).getEntityInstance()).thenReturn(new MockESSupplierEntity());

        ESAddress.Builder mockMainAddressBuilder = this.getMock(ESAddress.Builder.class);
        Mockito.when(mockMainAddressBuilder.build()).thenReturn((ESAddressEntity) mockSupplier.getMainAddress());

        ESAddress.Builder mockBillingAddressBuilder = this.getMock(ESAddress.Builder.class);
        Mockito.when(mockBillingAddressBuilder.build()).thenReturn((ESAddressEntity) mockSupplier.getBillingAddress());

        ESAddress.Builder mockShippingAddressBuilder = this.getMock(ESAddress.Builder.class);
        Mockito.when(mockShippingAddressBuilder.build())
                .thenReturn((ESAddressEntity) mockSupplier.getShippingAddress());

        ESContact.Builder mockMainContactBuilder = this.getMock(ESContact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn((ESContactEntity) mockSupplier.getMainContact());

        ESContact.Builder mockContactBuilder1 = this.getMock(ESContact.Builder.class);
        Mockito.when(mockContactBuilder1.build()).thenReturn((ESContactEntity) mockSupplier.getContacts().get(0));

        ESContact.Builder mockContactBuilder2 = this.getMock(ESContact.Builder.class);
        Mockito.when(mockContactBuilder2.build()).thenReturn((ESContactEntity) mockSupplier.getContacts().get(1));

        ESSupplier.Builder builder = this.getInstance(ESSupplier.Builder.class);

        builder.addAddress(mockMainAddressBuilder).addAddress(mockShippingAddressBuilder)
                .addAddress(mockBillingAddressBuilder).addContact(mockMainContactBuilder)
                .setBillingAddress(mockBillingAddressBuilder).setMainAddress(mockMainAddressBuilder)
                .setMainContact(mockMainContactBuilder).setName(mockSupplier.getName())
                .setSelfBillingAgreement(mockSupplier.hasSelfBillingAgreement())
                .setTaxRegistrationNumber(mockSupplier.getTaxRegistrationNumber(), ESAbstractTest.ES_COUNTRY_CODE)
                .setShippingAddress(mockShippingAddressBuilder);

        ESSupplier supplier = builder.build();

        Assertions.assertTrue(supplier != null);
        Assertions.assertEquals(mockSupplier.getName(), supplier.getName());
        Assertions.assertEquals(mockSupplier.getTaxRegistrationNumber(), supplier.getTaxRegistrationNumber());
        Assertions.assertEquals(mockSupplier.getMainAddress(), supplier.getMainAddress());
        Assertions.assertEquals(mockSupplier.getBankAccounts().size(), mockSupplier.getBankAccounts().size());
    }

}
