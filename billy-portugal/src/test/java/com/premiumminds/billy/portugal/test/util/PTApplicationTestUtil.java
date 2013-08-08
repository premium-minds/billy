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

import java.net.MalformedURLException;
import java.net.URL;

import com.google.inject.Injector;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTContact;

public class PTApplicationTestUtil {

	private static final String KEYS_PATH = "http://url";
	private static final String COMPANY_NAME = "company_name";
	private static final String COMPANY_TAX_ID = "12432353426435";
	private static final String APP_NAME = "APP";
	private static final Integer SW_CERTIFICATE_NUMBER = 1;
	private static final String VERSION = "1";
	private static final String WEBSITE = "http://app.ex";

	private Injector injector;
	private PTContactTestUtil contact;

	public PTApplicationTestUtil(Injector injector) {
		this.injector = injector;
		contact = new PTContactTestUtil(injector);
	}

	public PTApplication.Builder getApplicationBuilder(String appName,
			String version, String companyName, String companyTaxId,
			String website, Integer swCertificateNumber, String keysPath,
			PTContact.Builder contactBuilder) throws MalformedURLException {
		PTApplication.Builder applicationBuilder = injector
				.getInstance(PTApplication.Builder.class);

		applicationBuilder.clear();

		applicationBuilder.addContact(contactBuilder)
				.setApplicationKeysPath(new URL(keysPath))
				.setDeveloperCompanyName(companyName)
				.setDeveloperCompanyTaxIdentifier(companyTaxId)
				.setName(appName)
				.setSoftwareCertificationNumber(swCertificateNumber)
				.setVersion(version).setWebsiteAddress(website);

		return applicationBuilder;
	}

	public PTApplication.Builder getApplicationBuilder()
			throws MalformedURLException {
		PTContact.Builder contactBuilder = contact.getContactBuilder();

		return getApplicationBuilder(APP_NAME, VERSION, COMPANY_NAME,
				COMPANY_TAX_ID, WEBSITE, SW_CERTIFICATE_NUMBER, KEYS_PATH,
				contactBuilder);
	}
}
