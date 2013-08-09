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
import com.premiumminds.billy.portugal.persistence.entities.PTSupplierEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTSupplier;

public class PTSupplierTestUtil {

	private final Boolean selfBilling = false;
	private final String number = "123456789";
	private final String name = "Supplier";
	private final String uid = "SUPPLIER";

	private Injector injector;
	private PTAddressTestUtil address;
	private PTContactTestUtil contact;

	public PTSupplierTestUtil(Injector injector) {
		this.injector = injector;
		this.address = new PTAddressTestUtil(injector);
		this.contact = new PTContactTestUtil(injector);
	}

	public PTSupplierEntity getSupplierEntity() {

		PTAddress.Builder addressBuilder = this.address.getAddressBuilder();
		PTContact.Builder contactBuilder = this.contact.getContactBuilder();

		return this.getSupplierEntity(this.uid, this.name, this.number,
				this.selfBilling, addressBuilder, contactBuilder);
	}

	public PTSupplierEntity getSupplierEntity(String supplierUID, String name,
			String taxNumber, boolean selfBillingAgree,
			PTAddress.Builder addressBuilder, PTContact.Builder contactBuilder) {
		PTSupplier.Builder supplierBuilder = this.injector
				.getInstance(PTSupplier.Builder.class);

		supplierBuilder.clear();

		supplierBuilder.addAddress(addressBuilder).addContact(contactBuilder)
				.setBillingAddress(addressBuilder)
				.setMainContact(contactBuilder)
				.setSelfBillingAgreement(selfBillingAgree)
				.setTaxRegistrationNumber(taxNumber).setName(name)
				.setMainAddress(addressBuilder);

		PTSupplierEntity supplier = (PTSupplierEntity) supplierBuilder.build();
		supplier.setUID(new UID(supplierUID));

		return supplier;
	}
}
