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
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOAddress;
import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.BankAccountEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockAddressEntity;
import com.premiumminds.billy.core.test.fixtures.MockBankAccountEntity;
import com.premiumminds.billy.core.test.fixtures.MockContactEntity;
import com.premiumminds.billy.core.test.fixtures.MockCustomerEntity;


public class TestCustomerBuilder extends AbstractTest {
	
	private static final String CUSTOMER_YML = "src/test/resources/Customer.yml";

	@Test
	public void doTest() {
		MockCustomerEntity mockCustomer = loadFixture(MockCustomerEntity.class);
		
		Mockito.when(getInstance(DAOCustomer.class).getEntityInstance()).thenReturn(new MockCustomerEntity());
		Mockito.when(getInstance(DAOContact.class).get((UID) Matchers.anyObject())).thenReturn(getMock(ContactEntity.class));
		
		Customer.Builder builder = getInstance(Customer.Builder.class);
		
		Address.Builder mockAddressBuilder = this.getMock(Address.Builder.class);
		Mockito.when(mockAddressBuilder.build()).thenReturn(Mockito.mock(AddressEntity.class));
		
		BankAccount.Builder mockBankAccountBuilder = this.getMock(BankAccount.Builder.class);
		Mockito.when(mockBankAccountBuilder.build()).thenReturn(Mockito.mock(BankAccountEntity.class));
		
		Contact.Builder mockContactBuilder = this.getMock(Contact.Builder.class);
		Mockito.when(mockContactBuilder.build()).thenReturn(Mockito.mock(ContactEntity.class));
		
		builder.addBankAccount(mockBankAccountBuilder).addAddress(mockAddressBuilder, true).addContact(mockContactBuilder).setBillingAddress(mockAddressBuilder).setHasSelfBillingAgreement(false).setMainContactUID(mockCustomer.getMainContact().getUID()).setName(mockCustomer.getName()).setShippingAddress(mockAddressBuilder).setTaxRegistrationNumber(mockCustomer.getTaxRegistrationNumber());
		
		Customer customer = builder.build();
		
		assert(customer != null);
		
		assertEquals(mockCustomer.getName(), customer.getName());
		assertEquals(mockCustomer.getTaxRegistrationNumber(), customer.getTaxRegistrationNumber());
	}

	public MockCustomerEntity loadFixture(Class<MockCustomerEntity> clazz) {
		MockCustomerEntity result = (MockCustomerEntity) createMockEntityFromYaml(MockCustomerEntity.class, CUSTOMER_YML);
		
		MockContactEntity contactRef = new MockContactEntity();
		contactRef.uid = new UID("uid_ref");
		Mockito.when(getInstance(DAOContact.class).get(Matchers.any(UID.class))).thenReturn(contactRef);
		
		MockAddressEntity mockAddress = new MockAddressEntity();
		result.addresses = Arrays.asList(new Address[] {mockAddress});
		
		MockBankAccountEntity mockBankAccount = new MockBankAccountEntity();
		result.bankAccounts = Arrays.asList(new BankAccount[] {mockBankAccount});
		
		result.billingAddress = mockAddress;
		result.contacts = Arrays.asList(new Contact[] {contactRef});
		result.hasSelfBillingAgreement = false;
		result.mainAddress = mockAddress;
		result.mainContact = contactRef;
		result.shippingAddress = mockAddress;
		
		return result;
	}

}
