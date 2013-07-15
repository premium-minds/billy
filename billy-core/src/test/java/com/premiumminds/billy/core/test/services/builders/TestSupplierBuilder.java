/**
 * Copyright (C) 2013 Premium Minds.
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

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOSupplier;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.BankAccountEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Supplier;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockAddressEntity;
import com.premiumminds.billy.core.test.fixtures.MockBankAccountEntity;
import com.premiumminds.billy.core.test.fixtures.MockContactEntity;
import com.premiumminds.billy.core.test.fixtures.MockSupplierEntity;

public class TestSupplierBuilder extends AbstractTest {

	private static final String SUPPLIER_YML = "src/test/resources/Supplier.yml";

	@Test
	public void doTest() {
		MockSupplierEntity mockSupplier = loadFixture(MockSupplierEntity.class);
		Mockito.when(getInstance(DAOSupplier.class).getEntityInstance())
				.thenReturn(new MockSupplierEntity());

		Supplier.Builder builder = getInstance(Supplier.Builder.class);

		Address.Builder mockAddressBuilder = this
				.getMock(Address.Builder.class);
		Mockito.when(mockAddressBuilder.build()).thenReturn(
				Mockito.mock(AddressEntity.class));

		BankAccount.Builder mockBankAccountBuilder = this
				.getMock(BankAccount.Builder.class);
		Mockito.when(mockBankAccountBuilder.build()).thenReturn(
				Mockito.mock(BankAccountEntity.class));

		Contact.Builder mockContactBuilder = this
				.getMock(Contact.Builder.class);
		Mockito.when(mockContactBuilder.build()).thenReturn(
				Mockito.mock(ContactEntity.class));

		builder.addAddress(mockAddressBuilder)
				.addContact(mockContactBuilder)
				.setBankAccount(mockBankAccountBuilder)
				.setBillingAddress(mockAddressBuilder)
				.setMainAddress(mockAddressBuilder)
				.setMainContact(mockContactBuilder)
				.setName(mockSupplier.getName())
				.setSelfBillingAgreement(false)
				.setShippingAddress(mockAddressBuilder)
				.setTaxRegistrationNumber(
						mockSupplier.getTaxRegistrationNumber());

		Supplier supplier = builder.build();

		assert (supplier != null);
		assertEquals(mockSupplier.getName(), supplier.getName());
		assertEquals(mockSupplier.getTaxRegistrationNumber(),
				supplier.getTaxRegistrationNumber());

	}

	public MockSupplierEntity loadFixture(Class<MockSupplierEntity> clazz) {
		MockSupplierEntity result = (MockSupplierEntity) createMockEntityFromYaml(
				MockSupplierEntity.class, SUPPLIER_YML);

		MockAddressEntity mockAddress = new MockAddressEntity();
		result.mainAddress = mockAddress;
		result.billingAddress = mockAddress;
		result.shippingAddress = mockAddress;
		result.addresses = Arrays.asList(new Address[] { mockAddress });

		MockContactEntity mockContact = new MockContactEntity();
		result.mainContact = mockContact;
		result.contacts = Arrays.asList(new Contact[] { mockContact });

		MockBankAccountEntity mockBankAccount = new MockBankAccountEntity();
		result.bankAccounts = Arrays
				.asList(new BankAccount[] { mockBankAccount });

		return result;
	}
}
