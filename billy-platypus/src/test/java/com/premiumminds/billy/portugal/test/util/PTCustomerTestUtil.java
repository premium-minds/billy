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
