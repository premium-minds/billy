/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.builders;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTAddressEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTContactEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTSupplier;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTSupplierEntity;

public class TestPTSupplierBuilder extends PTAbstractTest {

    private static final String PTSUPPLIER_YML = AbstractTest.YML_CONFIGS_DIR + "PTSupplier.yml";

    @Test
    public void doTest() {
        MockPTSupplierEntity mockSupplier =
                this.createMockEntity(MockPTSupplierEntity.class, TestPTSupplierBuilder.PTSUPPLIER_YML);

        Mockito.when(this.getInstance(DAOPTSupplier.class).getEntityInstance()).thenReturn(new MockPTSupplierEntity());

        PTAddress.Builder mockMainAddressBuilder = this.getMock(PTAddress.Builder.class);
        Mockito.when(mockMainAddressBuilder.build()).thenReturn((PTAddressEntity) mockSupplier.getMainAddress());

        PTAddress.Builder mockBillingAddressBuilder = this.getMock(PTAddress.Builder.class);
        Mockito.when(mockBillingAddressBuilder.build()).thenReturn((PTAddressEntity) mockSupplier.getBillingAddress());

        PTAddress.Builder mockShippingAddressBuilder = this.getMock(PTAddress.Builder.class);
        Mockito.when(mockShippingAddressBuilder.build())
                .thenReturn((PTAddressEntity) mockSupplier.getShippingAddress());

        PTContact.Builder mockMainContactBuilder = this.getMock(PTContact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn((PTContactEntity) mockSupplier.getMainContact());

        PTContact.Builder mockContactBuilder1 = this.getMock(PTContact.Builder.class);
        Mockito.when(mockContactBuilder1.build()).thenReturn((PTContactEntity) mockSupplier.getContacts().get(0));

        PTContact.Builder mockContactBuilder2 = this.getMock(PTContact.Builder.class);
        Mockito.when(mockContactBuilder2.build()).thenReturn((PTContactEntity) mockSupplier.getContacts().get(1));

        PTSupplier.Builder builder = this.getInstance(PTSupplier.Builder.class);

        builder.addAddress(mockMainAddressBuilder).addAddress(mockShippingAddressBuilder)
                .addAddress(mockBillingAddressBuilder).addContact(mockMainContactBuilder)
                .setBillingAddress(mockBillingAddressBuilder).setMainAddress(mockMainAddressBuilder)
                .setMainContact(mockMainContactBuilder).setName(mockSupplier.getName())
                .setSelfBillingAgreement(mockSupplier.hasSelfBillingAgreement())
                .setTaxRegistrationNumber(mockSupplier.getTaxRegistrationNumber(), PTAbstractTest.PT_COUNTRY_CODE)
                .setShippingAddress(mockShippingAddressBuilder);

        PTSupplier supplier = builder.build();

        Assert.assertTrue(supplier != null);
        Assert.assertEquals(mockSupplier.getName(), supplier.getName());
        Assert.assertEquals(mockSupplier.getTaxRegistrationNumber(), supplier.getTaxRegistrationNumber());
        Assert.assertEquals(mockSupplier.getMainAddress(), supplier.getMainAddress());
        Assert.assertEquals(mockSupplier.getBankAccounts().size(), mockSupplier.getBankAccounts().size());
    }

}
