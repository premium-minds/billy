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
import com.premiumminds.billy.portugal.persistence.entities.PTSupplierEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTSupplier;

public class PTSupplierTestUtil {

	private static final Boolean	SELF_BILLING	= false;
	private static final String		NUMBER			= "123456789";
	private static final String		NAME			= "Supplier";
	protected static final String	PT_COUNTRY_CODE	= "PT";

	private Injector				injector;
	private PTAddressTestUtil		address;
	private PTContactTestUtil		contact;

	public PTSupplierTestUtil(Injector injector) {
		this.injector = injector;
		this.address = new PTAddressTestUtil(injector);
		this.contact = new PTContactTestUtil(injector);
	}

	public PTSupplierEntity getSupplierEntity() {

		PTAddress.Builder addressBuilder = this.address.getAddressBuilder();
		PTContact.Builder contactBuilder = this.contact.getContactBuilder();

		return this.getSupplierEntity(PTSupplierTestUtil.NAME,
				PTSupplierTestUtil.NUMBER, PTSupplierTestUtil.SELF_BILLING,
				addressBuilder, contactBuilder);
	}

	public PTSupplierEntity getSupplierEntity(String name, String taxNumber,
			boolean selfBillingAgree, PTAddress.Builder addressBuilder,
			PTContact.Builder contactBuilder) {

		return (PTSupplierEntity) this.getSupplierBuilder(name, taxNumber,
				selfBillingAgree, addressBuilder, contactBuilder).build();
	}

	public PTSupplier.Builder getSupplierBuilder(String name, String taxNumber,
			boolean selfBillingAgree, PTAddress.Builder addressBuilder,
			PTContact.Builder contactBuilder) {
		PTSupplier.Builder supplierBuilder = this.injector
				.getInstance(PTSupplier.Builder.class);

		supplierBuilder.addAddress(addressBuilder).addContact(contactBuilder)
				.setBillingAddress(addressBuilder)
				.setMainContact(contactBuilder)
				.setSelfBillingAgreement(selfBillingAgree)
				.setTaxRegistrationNumber(taxNumber, PT_COUNTRY_CODE)
				.setName(name).setMainAddress(addressBuilder);
		return supplierBuilder;
	}
}
