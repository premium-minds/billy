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

import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockCustomerEntity;

public class TestCustomerBuilder extends AbstractTest {

    private static final String CUSTOMER_YML = AbstractTest.YML_CONFIGS_DIR + "Customer.yml";

    @Test
    public void doTest() {
        MockCustomerEntity mockCustomer =
                this.createMockEntity(MockCustomerEntity.class, TestCustomerBuilder.CUSTOMER_YML);

        Mockito.when(this.getInstance(DAOCustomer.class).getEntityInstance()).thenReturn(new MockCustomerEntity());

        // Mockito.when(
        // this.getInstance(DAOCustomer.class).getEntityInstance().isNew())
        // .thenReturn(true);

        Mockito.when(this.getInstance(DAOContact.class).get(Mockito.any(UID.class)))
                .thenReturn((ContactEntity) mockCustomer.getMainContact());

        Customer.Builder builder = this.getInstance(Customer.Builder.class);

        Address.Builder mockMainAddressBuilder = this.getMock(Address.Builder.class);
        Mockito.when(mockMainAddressBuilder.build()).thenReturn(mockCustomer.getMainAddress());

        Address.Builder mockBillingAddressBuilder = this.getMock(Address.Builder.class);
        Mockito.when(mockBillingAddressBuilder.build()).thenReturn(mockCustomer.getBillingAddress());

        Address.Builder mockShippingAddressBuilder = this.getMock(Address.Builder.class);
        Mockito.when(mockShippingAddressBuilder.build()).thenReturn(mockCustomer.getShippingAddress());

        BankAccount.Builder mockBankAccountBuilder1 = this.getMock(BankAccount.Builder.class);
        Mockito.when(mockBankAccountBuilder1.build()).thenReturn(mockCustomer.getBankAccounts().get(0));

        BankAccount.Builder mockBankAccountBuilder2 = this.getMock(BankAccount.Builder.class);
        Mockito.when(mockBankAccountBuilder2.build()).thenReturn(mockCustomer.getBankAccounts().get(1));

        Contact.Builder mockMainContactBuilder = this.getMock(Contact.Builder.class);
        Mockito.when(mockMainContactBuilder.build()).thenReturn(mockCustomer.getMainContact());

        Contact.Builder mockContactBuilder1 = this.getMock(Contact.Builder.class);
        Mockito.when(mockContactBuilder1.build()).thenReturn(mockCustomer.getContacts().get(0));

        Contact.Builder mockContactBuilder2 = this.getMock(Contact.Builder.class);
        Mockito.when(mockContactBuilder2.build()).thenReturn(mockCustomer.getContacts().get(1));

        builder.addBankAccount(mockBankAccountBuilder1).addBankAccount(mockBankAccountBuilder2)
                .addAddress(mockMainAddressBuilder, true).addContact(mockMainContactBuilder)
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
        Assertions.assertEquals(mockCustomer.getTaxRegistrationNumberISOCountryCode(), customer.getTaxRegistrationNumberISOCountryCode());
        Assertions.assertEquals(mockCustomer.getMainAddress(), customer.getMainAddress());
        Assertions.assertEquals(mockCustomer.getShippingAddress(), customer.getShippingAddress());
        Assertions.assertEquals(mockCustomer.hasSelfBillingAgreement(), customer.hasSelfBillingAgreement());
    }
}
