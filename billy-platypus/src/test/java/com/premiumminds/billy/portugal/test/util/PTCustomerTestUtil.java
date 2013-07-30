/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTCustomer;

public class PTCustomerTestUtil {

	private final String name = "Name";
	private final String taxNumber = "123456789";
	private final Boolean selfBillingAgree = false;
	private final String addressUID = "CUSTOMER_ADDRESS";
	private final String contactUID = "CUSTOMER_CONTACT";
	private final String uid = "CUSTOMER";

	private Injector injector;
	private PTAddressTestUtil address;
	private PTContactTestUtil contact;

	public PTCustomerTestUtil(Injector injector) {
		this.injector = injector;
		address = new PTAddressTestUtil(injector);
		contact = new PTContactTestUtil(injector);
	}

	public PTCustomerEntity getCustomerEntity() {
		PTCustomer.Builder customerBuilder = injector
				.getInstance(PTCustomer.Builder.class);
		PTAddress.Builder addressBuilder = address.getAddressBuilder();
		PTContact.Builder contactBuilder = contact.getContactBuilder();

		customerBuilder.clear();
		customerBuilder.addAddress(addressBuilder, true)
				.addContact(contactBuilder).setBillingAddress(addressBuilder)
				.setName(name).setHasSelfBillingAgreement(selfBillingAgree)
				.setTaxRegistrationNumber(taxNumber);

		PTCustomerEntity customer = (PTCustomerEntity) customerBuilder.build();
		customer.getAddresses().get(0).setUID(new UID(addressUID));
		customer.getContacts().get(0).setUID(new UID(contactUID));
		customer.setUID(new UID(uid));

		return customer;
	}
}
