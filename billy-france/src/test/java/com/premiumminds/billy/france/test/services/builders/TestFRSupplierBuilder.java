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
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.france.persistence.dao.DAOFRSupplier;
import com.premiumminds.billy.france.persistence.entities.FRAddressEntity;
import com.premiumminds.billy.france.persistence.entities.FRContactEntity;
import com.premiumminds.billy.france.services.entities.FRAddress;
import com.premiumminds.billy.france.services.entities.FRContact;
import com.premiumminds.billy.france.services.entities.FRSupplier;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRSupplierEntity;

public class TestFRSupplierBuilder extends FRAbstractTest {

    private static final String FRSUPPLIER_YML = AbstractTest.YML_CONFIGS_DIR + "FRSupplier.yml";

    @Test
    public void doTest() {
        MockFRSupplierEntity mockSupplier =
                this.createMockEntity(MockFRSupplierEntity.class, TestFRSupplierBuilder.FRSUPPLIER_YML);

        Mockito.when(this.getInstance(DAOFRSupplier.class).getEntityInstance()).thenReturn(new MockFRSupplierEntity());

        FRAddress.Builder mockMainAddressBuilder = this.getMock(FRAddress.Builder.class);
        Mockito.when(mockMainAddressBuilder.build()).thenReturn((FRAddressEntity) mockSupplier.getMainAddress());

        FRAddress.Builder mockBillingAddressBuilder = this.getMock(FRAddress.Builder.class);
        Mockito.when(mockBillingAddressBuilder.build()).thenReturn((FRAddressEntity) mockSupplier.getBillingAddress());

        FRAddress.Builder mockShippingAddressBuilder = this.getMock(FRAddress.Builder.class);
        Mockito.when(mockShippingAddressBuilder.build())
                .thenReturn((FRAddressEntity) mockSupplier.getShippingAddress());

        FRContact.Builder mockMainContactBuilder = this.getMock(FRContact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn((FRContactEntity) mockSupplier.getMainContact());

        FRContact.Builder mockContactBuilder1 = this.getMock(FRContact.Builder.class);
        Mockito.when(mockContactBuilder1.build()).thenReturn((FRContactEntity) mockSupplier.getContacts().get(0));

        FRContact.Builder mockContactBuilder2 = this.getMock(FRContact.Builder.class);
        Mockito.when(mockContactBuilder2.build()).thenReturn((FRContactEntity) mockSupplier.getContacts().get(1));

        FRSupplier.Builder builder = this.getInstance(FRSupplier.Builder.class);

        builder.addAddress(mockMainAddressBuilder).addAddress(mockShippingAddressBuilder)
                .addAddress(mockBillingAddressBuilder).addContact(mockMainContactBuilder)
                .setBillingAddress(mockBillingAddressBuilder).setMainAddress(mockMainAddressBuilder)
                .setMainContact(mockMainContactBuilder).setName(mockSupplier.getName())
                .setSelfBillingAgreement(mockSupplier.hasSelfBillingAgreement())
                .setTaxRegistrationNumber(mockSupplier.getTaxRegistrationNumber(), FRAbstractTest.FR_COUNTRY_CODE)
                .setShippingAddress(mockShippingAddressBuilder);

        FRSupplier supplier = builder.build();

        Assert.assertTrue(supplier != null);
        Assert.assertEquals(mockSupplier.getName(), supplier.getName());
        Assert.assertEquals(mockSupplier.getTaxRegistrationNumber(), supplier.getTaxRegistrationNumber());
        Assert.assertEquals(mockSupplier.getMainAddress(), supplier.getMainAddress());
        Assert.assertEquals(mockSupplier.getBankAccounts().size(), mockSupplier.getBankAccounts().size());
    }

}
