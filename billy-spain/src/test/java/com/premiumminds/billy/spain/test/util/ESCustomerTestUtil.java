/**
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
package com.premiumminds.billy.spain.test.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.spain.persistence.entities.ESCustomerEntity;
import com.premiumminds.billy.spain.services.entities.ESAddress;
import com.premiumminds.billy.spain.services.entities.ESContact;
import com.premiumminds.billy.spain.services.entities.ESCustomer;

public class ESCustomerTestUtil {

	private static final String		NAME				= "Name";
	private static final String		TAX_NUMBER			= "11111111H";
	private static final Boolean	SELF_BILLING_AGREE	= false;
	protected static final String	ES_COUNTRY_CODE		= "ES";

	private Injector				injector;
	private ESAddressTestUtil		address;
	private ESContactTestUtil		contact;

	public ESCustomerTestUtil(Injector injector) {
		this.injector = injector;
		this.address = new ESAddressTestUtil(injector);
		this.contact = new ESContactTestUtil(injector);
	}

	public ESCustomerEntity getCustomerEntity(String uid) {
		ESCustomerEntity customer = (ESCustomerEntity) this
				.getCustomerBuilder().build();
		customer.setUID(new UID(uid));
		return customer;
	}

	public ESCustomerEntity getCustomerEntity() {
		return (ESCustomerEntity) this.getCustomerBuilder().build();
	}

	public ESCustomer.Builder getCustomerBuilder() {
		ESAddress.Builder addressBuilder = this.address.getAddressBuilder();
		ESContact.Builder contactBuilder = this.contact.getContactBuilder();

		return this.getCustomerBuilder(ESCustomerTestUtil.NAME,
				ESCustomerTestUtil.TAX_NUMBER,
				ESCustomerTestUtil.SELF_BILLING_AGREE, addressBuilder,
				contactBuilder);
	}

	public ESCustomer.Builder getCustomerBuilder(String name, String taxNumber,
			Boolean selfBilling, ESAddress.Builder addressBuilder,
			ESContact.Builder contactBuilder) {

		ESCustomer.Builder customerBuilder = this.injector
				.getInstance(ESCustomer.Builder.class);

		return customerBuilder.addAddress(addressBuilder, true)
				.addContact(contactBuilder).setBillingAddress(addressBuilder)
				.setName(name).setHasSelfBillingAgreement(selfBilling)
				.setTaxRegistrationNumber(taxNumber, ES_COUNTRY_CODE);
	}

	public ESCustomerEntity getCustomerEntity(String name, String taxNumber,
			boolean selfBilling, ESAddress.Builder addressBuilder,
			ESContact.Builder contactBuilder) {

		ESCustomerEntity customer = (ESCustomerEntity) this
				.getCustomerBuilder().build();

		return customer;
	}
}
