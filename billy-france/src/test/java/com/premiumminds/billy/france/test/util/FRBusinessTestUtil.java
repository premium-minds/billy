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

import javax.persistence.NoResultException;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.france.persistence.dao.DAOFRBusiness;
import com.premiumminds.billy.france.persistence.entities.FRBusinessEntity;
import com.premiumminds.billy.france.services.entities.FRAddress;
import com.premiumminds.billy.france.services.entities.FRApplication;
import com.premiumminds.billy.france.services.entities.FRBusiness;
import com.premiumminds.billy.france.services.entities.FRContact;
import com.premiumminds.billy.france.services.entities.FRRegionContext;
import com.premiumminds.billy.france.util.Contexts;

public class FRBusinessTestUtil {

    private static final String NAME = "Business";
    private static final String FINANCIAL_ID = "11111111H";
    private static final String WEBSITE = "http://business.com";
    protected static final String FR_COUNTRY_CODE = "FR";

    private Injector injector;
    private FRApplicationTestUtil application;
    private FRContactTestUtil contact;
    private FRAddressTestUtil address;
    private FRRegionContext context;

    public FRBusinessTestUtil(Injector injector) {
        this.injector = injector;
        this.application = new FRApplicationTestUtil(injector);
        this.contact = new FRContactTestUtil(injector);
        this.address = new FRAddressTestUtil(injector);

        this.context = new Contexts(injector).france().allRegions();
    }

    public FRBusinessEntity getBusinessEntity() {
        return this.getBusinessEntity(new UID().toString());
    }

    public FRBusinessEntity getBusinessEntity(String uid) {
        FRBusinessEntity business = null;
        try {
            business = this.injector.getInstance(DAOFRBusiness.class).get(new UID(uid));
        } catch (NoResultException e) {
            business = (FRBusinessEntity) this.getBusinessBuilder().build();
            business.setUID(new UID(uid));
            this.injector.getInstance(DAOFRBusiness.class).create(business);
        }

        return business;
    }

    public FRBusiness.Builder getBusinessBuilder() {
        FRBusiness.Builder businessBuilder = this.injector.getInstance(FRBusiness.Builder.class);

        FRApplication.Builder applicationBuilder = null;
        try {
            applicationBuilder = this.application.getApplicationBuilder();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        FRContact.Builder contactBuilder = this.contact.getContactBuilder();
        FRAddress.Builder addressBuilder = this.address.getAddressBuilder();

        businessBuilder.addApplication(applicationBuilder).addContact(contactBuilder, true).setAddress(addressBuilder)
                .setBillingAddress(addressBuilder).setCommercialName(FRBusinessTestUtil.NAME)
                .setFinancialID(FRBusinessTestUtil.FINANCIAL_ID, FRBusinessTestUtil.FR_COUNTRY_CODE)
                .setOperationalContextUID(this.context.getUID()).setWebsite(FRBusinessTestUtil.WEBSITE)
                .setName(FRBusinessTestUtil.NAME);

        return businessBuilder;
    }
}
