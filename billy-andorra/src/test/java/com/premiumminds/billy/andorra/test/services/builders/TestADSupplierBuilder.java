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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSupplier;
import com.premiumminds.billy.andorra.persistence.entities.ADAddressEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADContactEntity;
import com.premiumminds.billy.andorra.services.entities.ADAddress;
import com.premiumminds.billy.andorra.services.entities.ADContact;
import com.premiumminds.billy.andorra.services.entities.ADSupplier;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.fixtures.MockADSupplierEntity;

public class TestADSupplierBuilder extends ADAbstractTest {

    private static final String ESSUPPLIER_YML = AbstractTest.YML_CONFIGS_DIR + "ESSupplier.yml";

    @Test
    public void doTest() {
        MockADSupplierEntity mockSupplier =
                this.createMockEntity(MockADSupplierEntity.class, TestADSupplierBuilder.ESSUPPLIER_YML);

        Mockito.when(this.getInstance(DAOADSupplier.class).getEntityInstance()).thenReturn(new MockADSupplierEntity());

        ADAddress.Builder mockMainAddressBuilder = this.getMock(ADAddress.Builder.class);
        Mockito.when(mockMainAddressBuilder.build()).thenReturn((ADAddressEntity) mockSupplier.getMainAddress());

        ADAddress.Builder mockBillingAddressBuilder = this.getMock(ADAddress.Builder.class);
        Mockito.when(mockBillingAddressBuilder.build()).thenReturn((ADAddressEntity) mockSupplier.getBillingAddress());

        ADAddress.Builder mockShippingAddressBuilder = this.getMock(ADAddress.Builder.class);
        Mockito.when(mockShippingAddressBuilder.build())
                .thenReturn((ADAddressEntity) mockSupplier.getShippingAddress());

        ADContact.Builder mockMainContactBuilder = this.getMock(ADContact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn((ADContactEntity) mockSupplier.getMainContact());

        ADContact.Builder mockContactBuilder1 = this.getMock(ADContact.Builder.class);
        Mockito.when(mockContactBuilder1.build()).thenReturn((ADContactEntity) mockSupplier.getContacts().get(0));

        ADContact.Builder mockContactBuilder2 = this.getMock(ADContact.Builder.class);
        Mockito.when(mockContactBuilder2.build()).thenReturn((ADContactEntity) mockSupplier.getContacts().get(1));

        ADSupplier.Builder builder = this.getInstance(ADSupplier.Builder.class);

        builder.addAddress(mockMainAddressBuilder).addAddress(mockShippingAddressBuilder)
                .addAddress(mockBillingAddressBuilder).addContact(mockMainContactBuilder)
                .setBillingAddress(mockBillingAddressBuilder).setMainAddress(mockMainAddressBuilder)
                .setMainContact(mockMainContactBuilder).setName(mockSupplier.getName())
                .setSelfBillingAgreement(mockSupplier.hasSelfBillingAgreement())
                .setTaxRegistrationNumber(mockSupplier.getTaxRegistrationNumber(), mockSupplier.getTaxRegistrationNumberISOCountryCode())
                .setShippingAddress(mockShippingAddressBuilder);

        ADSupplier supplier = builder.build();

        Assertions.assertTrue(supplier != null);
        Assertions.assertEquals(mockSupplier.getName(), supplier.getName());
        Assertions.assertEquals(mockSupplier.getTaxRegistrationNumber(), supplier.getTaxRegistrationNumber());
        Assertions.assertEquals(mockSupplier.getTaxRegistrationNumberISOCountryCode(), supplier.getTaxRegistrationNumberISOCountryCode());
        Assertions.assertEquals(mockSupplier.getMainAddress(), supplier.getMainAddress());
        Assertions.assertEquals(mockSupplier.getBankAccounts().size(), mockSupplier.getBankAccounts().size());
    }

}
