/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.test.util;

import java.net.MalformedURLException;

import com.google.inject.Injector;
import com.premiumminds.billy.andorra.services.entities.ADApplication;
import com.premiumminds.billy.andorra.services.entities.ADContact;

public class ADApplicationTestUtil {

    private static final String KEYS_PATH = "http://url";
    private static final String COMPANY_NAME = "company_name";
    private static final String COMPANY_TAX_ID = "12432353426435";
    private static final String COMPANY_TAX_ID_ISO_COUNTRY_CODE = "AD";
    private static final String APP_NAME = "APP";
    private static final String VERSION = "1";
    private static final String WEBSITE = "http://app.ex";

    private Injector injector;
    private ADContactTestUtil contact;

    public ADApplicationTestUtil(Injector injector) {
        this.injector = injector;
        this.contact = new ADContactTestUtil(injector);
    }

    public ADApplication.Builder getApplicationBuilder(String appName, String version, String companyName,
                                                       String companyTaxId, String companyTaxIdIsoCountryCode, String website, ADContact.Builder contactBuilder)
        throws MalformedURLException {

        ADApplication.Builder applicationBuilder = this.injector.getInstance(ADApplication.Builder.class);

        applicationBuilder
            .addContact(contactBuilder)
            .setDeveloperCompanyName(companyName)
            .setDeveloperCompanyTaxIdentifier(companyTaxId, companyTaxIdIsoCountryCode)
            .setName(appName)
            .setVersion(version)
            .setWebsiteAddress(website);

        return applicationBuilder;
    }

    public ADApplication.Builder getApplicationBuilder() throws MalformedURLException {
        ADContact.Builder contactBuilder = this.contact.getContactBuilder();

        return this.getApplicationBuilder(ADApplicationTestUtil.APP_NAME, ADApplicationTestUtil.VERSION,
                                          ADApplicationTestUtil.COMPANY_NAME, ADApplicationTestUtil.COMPANY_TAX_ID,
                                          ADApplicationTestUtil.COMPANY_TAX_ID_ISO_COUNTRY_CODE, ADApplicationTestUtil.WEBSITE, contactBuilder);
    }
}
