package com.premiumminds.billy.portugal.test.util;

import java.net.MalformedURLException;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.util.Contexts;

public class PTBusinessTestUtil {

	private final String name = "Business";
	private final String id = "123456789";
	private final String website = "http://business.com";
	private final String uid = "BUSINESS";

	private Injector injector;
	private PTApplicationTestUtil application;
	private PTContactTestUtil contact;
	private PTAddressTestUtil address;
	private Contexts contexts;
	private PTRegionContext context;

	public PTBusinessTestUtil(Injector injector) {
		this.injector = injector;
		application = new PTApplicationTestUtil(injector);
		contact = new PTContactTestUtil(injector);
		address = new PTAddressTestUtil(injector);
		contexts = new Contexts(injector);
	}

	public PTBusinessEntity getBusinessEntity() throws MalformedURLException {
		PTBusiness.Builder businessBuilder = injector
				.getInstance(PTBusiness.Builder.class);
		PTApplication.Builder applicationBuilder = application
				.getApplicationBuilder();
		PTContact.Builder contactBuilder = contact.getContactBuilder();
		PTAddress.Builder addressBuilder = address.getAddressBuilder();
		context = contexts.portugal().portugal();

		businessBuilder.clear();

		businessBuilder.addApplication(applicationBuilder)
				.addContact(contactBuilder).setAddress(addressBuilder)
				.setBillingAddress(addressBuilder).setCommercialName(name)
				.setFinancialID(id).setOperationalContextUID(context.getUID())
				.setWebsite(website).setName(name);

		PTBusinessEntity business = (PTBusinessEntity) businessBuilder.build();
		business.setUID(new UID(uid));

		return business;
	}
}
