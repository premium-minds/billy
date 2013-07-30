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
		address = new PTAddressTestUtil(injector);
		contact = new PTContactTestUtil(injector);
	}

	public PTSupplierEntity getSupplierEntity() {
		PTSupplier.Builder supplierBuilder = injector
				.getInstance(PTSupplier.Builder.class);
		PTAddress.Builder addressBuilder = address.getAddressBuilder();
		PTContact.Builder contactBuilder = contact.getContactBuilder();

		supplierBuilder.clear();

		supplierBuilder.addAddress(addressBuilder).addContact(contactBuilder)
				.setBillingAddress(addressBuilder)
				.setMainContact(contactBuilder)
				.setSelfBillingAgreement(selfBilling)
				.setTaxRegistrationNumber(number).setName(name)
				.setMainAddress(addressBuilder);

		PTSupplierEntity supplier = (PTSupplierEntity) supplierBuilder.build();
		supplier.setUID(new UID(uid));

		return supplier;
	}
}
