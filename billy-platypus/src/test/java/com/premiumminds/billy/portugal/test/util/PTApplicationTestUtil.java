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
