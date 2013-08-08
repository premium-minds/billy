/**
 * Copyright (C) 2013 Premium Minds.
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
package com.premiumminds.billy.portugal.test.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTCustomer;

public class PTCustomerTestUtil {

	private static final String NAME = "Name";
	private static final String TAX_NUMBER = "123456789";
	private static final Boolean SELF_BILLING_AGREE = false;
	private static final String uid = "CUSTOMER";

	private Injector injector;
	private PTAddressTestUtil address;
	private PTContactTestUtil contact;

	public PTCustomerTestUtil(Injector injector) {
		this.injector = injector;
		address = new PTAddressTestUtil(injector);
		contact = new PTContactTestUtil(injector);
	}

	public PTCustomerEntity getCustomerEntity(String customerUID, String name,
			String taxNumber, boolean selfBillingAgree,
			PTAddress.Builder addressBuilder, PTContact.Builder contactBuilder) {
		PTCustomer.Builder customerBuilder = injector
				.getInstance(PTCustomer.Builder.class);
		customerBuilder.clear();
		customerBuilder.addAddress(addressBuilder, true)
				.addContact(contactBuilder).setBillingAddress(addressBuilder)
				.setName(name).setHasSelfBillingAgreement(selfBillingAgree)
				.setTaxRegistrationNumber(taxNumber);

		// TODO Check this
		PTCustomerEntity customer = (PTCustomerEntity) customerBuilder.build();
		customer.setUID(new UID(customerUID));

		return customer;
	}

	public PTCustomerEntity getCustomerEntity(String customerUID) {
		PTAddress.Builder addressBuilder = address.getAddressBuilder();
		PTContact.Builder contactBuilder = contact.getContactBuilder();

		return getCustomerEntity(customerUID, NAME, TAX_NUMBER,
				SELF_BILLING_AGREE, addressBuilder, contactBuilder);
	}

	public PTCustomerEntity getCustomerEntity() {
		return getCustomerEntity(uid);
	}
}
