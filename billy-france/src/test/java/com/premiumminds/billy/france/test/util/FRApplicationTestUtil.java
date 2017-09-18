/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.test.util;

import java.net.MalformedURLException;

import com.google.inject.Injector;
import com.premiumminds.billy.france.services.entities.FRApplication;
import com.premiumminds.billy.france.services.entities.FRContact;

public class FRApplicationTestUtil {

    private static final String KEYS_PATH = "http://url";
    private static final String COMPANY_NAME = "company_name";
    private static final String COMPANY_TAX_ID = "12432353426435";
    private static final String APP_NAME = "APP";
    private static final String VERSION = "1";
    private static final String WEBSITE = "http://app.ex";

    private Injector injector;
    private FRContactTestUtil contact;

    public FRApplicationTestUtil(Injector injector) {
        this.injector = injector;
        this.contact = new FRContactTestUtil(injector);
    }

    public FRApplication.Builder getApplicationBuilder(String appName, String version, String companyName,
            String companyTaxId, String website, FRContact.Builder contactBuilder) throws MalformedURLException {

        FRApplication.Builder applicationBuilder = this.injector.getInstance(FRApplication.Builder.class);

        applicationBuilder.addContact(contactBuilder).setDeveloperCompanyName(companyName)
                .setDeveloperCompanyTaxIdentifier(companyTaxId).setName(appName).setVersion(version)
                .setWebsiteAddress(website);

        return applicationBuilder;
    }

    public FRApplication.Builder getApplicationBuilder() throws MalformedURLException {
        FRContact.Builder contactBuilder = this.contact.getContactBuilder();

        return this.getApplicationBuilder(FRApplicationTestUtil.APP_NAME, FRApplicationTestUtil.VERSION,
                FRApplicationTestUtil.COMPANY_NAME, FRApplicationTestUtil.COMPANY_TAX_ID, FRApplicationTestUtil.WEBSITE,
                contactBuilder);
    }
}
