package com.premiumminds.billy.portugal.test.util;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.inject.Injector;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTContact;

public class PTApplicationTestUtil {

	private final String path = "http://url";
	private final String name = "company_name";
	private final String id = "12432353426435";
	private final String appName = "APP";
	private final Integer number = 1;
	private final String version = "1";
	private final String website = "http://app.ex";
	private final String uid = "APPLICATION";

	private Injector injector;
	private PTContactTestUtil contact;

	public PTApplicationTestUtil(Injector injector) {
		this.injector = injector;
		contact = new PTContactTestUtil(injector);
	}

	public PTApplication.Builder getApplicationBuilder()
			throws MalformedURLException {
		PTApplication.Builder applicationBuilder = injector
				.getInstance(PTApplication.Builder.class);
		PTContact.Builder contactBuilder = contact.getContactBuilder();

		applicationBuilder.clear();

		applicationBuilder.addContact(contactBuilder)
				.setApplicationKeysPath(new URL(path))
				.setDeveloperCompanyName(name)
				.setDeveloperCompanyTaxIdentifier(id).setName(appName)
				.setSoftwareCertificationNumber(number).setVersion(version)
				.setWebsiteAddress(website);

		return applicationBuilder;
	}
}
