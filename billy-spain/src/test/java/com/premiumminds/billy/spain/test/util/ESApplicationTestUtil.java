/**
 * Copyright (C) 2013 Premium Minds.
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

import java.net.MalformedURLException;
import java.net.URL;

import com.google.inject.Injector;
import com.premiumminds.billy.spain.services.entities.ESApplication;
import com.premiumminds.billy.spain.services.entities.ESContact;

public class ESApplicationTestUtil {

	private static final String		KEYS_PATH				= "http://url";
	private static final String		COMPANY_NAME			= "company_name";
	private static final String		COMPANY_TAX_ID			= "12432353426435";
	private static final String		APP_NAME				= "APP";
	private static final Integer	SW_CERTIFICATE_NUMBER	= 1;
	private static final String		VERSION					= "1";
	private static final String		WEBSITE					= "http://app.ex";

	private Injector				injector;
	private ESContactTestUtil		contact;

	public ESApplicationTestUtil(Injector injector) {
		this.injector = injector;
		this.contact = new ESContactTestUtil(injector);
	}

	public ESApplication.Builder getApplicationBuilder(String appName,
			String version, String companyName, String companyTaxId,
			String website, Integer swCertificateNumber, String keysPath,
			ESContact.Builder contactBuilder) throws MalformedURLException {

		ESApplication.Builder applicationBuilder = this.injector
				.getInstance(ESApplication.Builder.class);

		applicationBuilder.addContact(contactBuilder)
				.setApplicationKeysPath(new URL(keysPath))
				.setDeveloperCompanyName(companyName)
				.setDeveloperCompanyTaxIdentifier(companyTaxId)
				.setName(appName)
				.setSoftwareCertificationNumber(swCertificateNumber)
				.setVersion(version).setWebsiteAddress(website);

		return applicationBuilder;
	}

	public ESApplication.Builder getApplicationBuilder()
		throws MalformedURLException {
		ESContact.Builder contactBuilder = this.contact.getContactBuilder();

		return this.getApplicationBuilder(ESApplicationTestUtil.APP_NAME,
				ESApplicationTestUtil.VERSION,
				ESApplicationTestUtil.COMPANY_NAME,
				ESApplicationTestUtil.COMPANY_TAX_ID,
				ESApplicationTestUtil.WEBSITE,
				ESApplicationTestUtil.SW_CERTIFICATE_NUMBER,
				ESApplicationTestUtil.KEYS_PATH, contactBuilder);
	}
}
