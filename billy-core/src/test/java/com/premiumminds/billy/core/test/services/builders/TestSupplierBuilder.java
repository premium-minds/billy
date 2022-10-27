/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.test.services.builders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOSupplier;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Supplier;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockSupplierEntity;

public class TestSupplierBuilder extends AbstractTest {

    private static final String SUPPLIER_YML = AbstractTest.YML_CONFIGS_DIR + "Supplier.yml";

    @Test
    public void doTest() {
        MockSupplierEntity mockSupplier =
                this.createMockEntity(MockSupplierEntity.class, TestSupplierBuilder.SUPPLIER_YML);

        Mockito.when(this.getInstance(DAOSupplier.class).getEntityInstance()).thenReturn(new MockSupplierEntity());

        Address.Builder mockMainAddressBuilder = this.getMock(Address.Builder.class);
        Mockito.when(mockMainAddressBuilder.build()).thenReturn(mockSupplier.getMainAddress());

        Address.Builder mockBillingAddressBuilder = this.getMock(Address.Builder.class);
        Mockito.when(mockBillingAddressBuilder.build()).thenReturn(mockSupplier.getBillingAddress());

        Address.Builder mockShippingAddressBuilder = this.getMock(Address.Builder.class);
        Mockito.when(mockShippingAddressBuilder.build()).thenReturn(mockSupplier.getShippingAddress());

        BankAccount.Builder mockBankAccountBuilder1 = this.getMock(BankAccount.Builder.class);
        Mockito.when(mockBankAccountBuilder1.build()).thenReturn(mockSupplier.getBankAccounts().get(0));

        BankAccount.Builder mockBankAccountBuilder2 = this.getMock(BankAccount.Builder.class);
        Mockito.when(mockBankAccountBuilder2.build()).thenReturn(mockSupplier.getBankAccounts().get(1));

        Contact.Builder mockMainContactBuilder = this.getMock(Contact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn(mockSupplier.getMainContact());

        Contact.Builder mockContactBuilder1 = this.getMock(Contact.Builder.class);
        Mockito.when(mockContactBuilder1.build()).thenReturn(mockSupplier.getContacts().get(0));

        Contact.Builder mockContactBuilder2 = this.getMock(Contact.Builder.class);
        Mockito.when(mockContactBuilder2.build()).thenReturn(mockSupplier.getContacts().get(1));

        Supplier.Builder builder = this.getInstance(Supplier.Builder.class);

        builder.addAddress(mockMainAddressBuilder).addAddress(mockShippingAddressBuilder)
                .addAddress(mockBillingAddressBuilder).addContact(mockMainContactBuilder)
                .addBankAccount(mockBankAccountBuilder1).setBillingAddress(mockBillingAddressBuilder)
                .setMainAddress(mockMainAddressBuilder).setMainContact(mockMainContactBuilder)
                .setName(mockSupplier.getName()).setSelfBillingAgreement(mockSupplier.hasSelfBillingAgreement())
                .setTaxRegistrationNumber(mockSupplier.getTaxRegistrationNumber(), mockSupplier.getTaxRegistrationNumberCountry())
                .setShippingAddress(mockShippingAddressBuilder);

        Supplier supplier = builder.build();

        Assertions.assertTrue(supplier != null);
        Assertions.assertEquals(mockSupplier.getName(), supplier.getName());
        Assertions.assertEquals(mockSupplier.getTaxRegistrationNumber(), supplier.getTaxRegistrationNumber());
		Assertions.assertEquals(mockSupplier.getTaxRegistrationNumberCountry(), supplier.getTaxRegistrationNumberCountry());
        Assertions.assertEquals(mockSupplier.getMainAddress(), supplier.getMainAddress());
        Assertions.assertEquals(mockSupplier.getBankAccounts().size(), mockSupplier.getBankAccounts().size());
    }

}
