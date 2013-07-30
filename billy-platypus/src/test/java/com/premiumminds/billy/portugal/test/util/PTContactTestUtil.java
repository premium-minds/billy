package com.premiumminds.billy.portugal.test.util;

import com.google.inject.Injector;
import com.premiumminds.billy.portugal.services.entities.PTContact;

public class PTContactTestUtil {

	private final String name = "name";
	private final String telephone = "998887999";
	private final String mobile = "999999999";
	private final String email = "email@email.em";
	private final String fax = "9999999122";
	private final String website = "website@website.web";

	private Injector injector;

	public PTContactTestUtil(Injector injector) {
		this.injector = injector;
	}

	public PTContact.Builder getContactBuilder() {
		PTContact.Builder contactBuilder = injector
				.getInstance(PTContact.Builder.class);

		contactBuilder.setName(name).setEmail(email).setMobile(mobile)
				.setFax(fax).setTelephone(telephone).setWebsite(website);

		return contactBuilder;
	}
}
