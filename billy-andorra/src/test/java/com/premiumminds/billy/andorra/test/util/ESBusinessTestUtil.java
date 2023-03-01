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

import com.google.inject.Injector;
import java.net.MalformedURLException;
import java.util.UUID;

import javax.persistence.NoResultException;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.andorra.persistence.dao.DAOADBusiness;
import com.premiumminds.billy.andorra.persistence.entities.ADBusinessEntity;
import com.premiumminds.billy.andorra.services.entities.ADAddress;
import com.premiumminds.billy.andorra.services.entities.ADApplication;
import com.premiumminds.billy.andorra.services.entities.ADBusiness;
import com.premiumminds.billy.andorra.services.entities.ADContact;
import com.premiumminds.billy.andorra.services.entities.ADRegionContext;
import com.premiumminds.billy.andorra.util.Contexts;

public class ESBusinessTestUtil {

    private static final String NAME = "Business";
    private static final String FINANCIAL_ID = "11111111H";
    private static final String WEBSITE = "http://business.com";
    protected static final String ES_COUNTRY_CODE = "ES";

    private Injector injector;
    private ESApplicationTestUtil application;
    private ESContactTestUtil contact;
    private ESAddressTestUtil address;
    private ADRegionContext context;

    public ESBusinessTestUtil(Injector injector) {
        this.injector = injector;
        this.application = new ESApplicationTestUtil(injector);
        this.contact = new ESContactTestUtil(injector);
        this.address = new ESAddressTestUtil(injector);

        this.context = new Contexts(injector).andorra().allRegions();
    }

    public ADBusinessEntity getBusinessEntity() {
        return this.getBusinessEntity(StringID.fromValue(UUID.randomUUID().toString()));
    }

    public ADBusinessEntity getBusinessEntity(StringID<Business> businessID) {
        ADBusinessEntity business = null;
        try {
            business = this.injector.getInstance(DAOADBusiness.class).get(businessID);
        } catch (NoResultException e) {
            business = (ADBusinessEntity) this.getBusinessBuilder().build();
            business.setUID(businessID);
            this.injector.getInstance(DAOADBusiness.class).create(business);
        }

        return business;
    }

    public ADBusiness.Builder getBusinessBuilder() {
        ADBusiness.Builder businessBuilder = this.injector.getInstance(ADBusiness.Builder.class);

        ADApplication.Builder applicationBuilder = null;
        try {
            applicationBuilder = this.application.getApplicationBuilder();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ADContact.Builder contactBuilder = this.contact.getContactBuilder();
        ADAddress.Builder addressBuilder = this.address.getAddressBuilder();

        businessBuilder.addApplication(applicationBuilder).addContact(contactBuilder, true).setAddress(addressBuilder)
                .setBillingAddress(addressBuilder).setCommercialName(ESBusinessTestUtil.NAME)
                .setFinancialID(ESBusinessTestUtil.FINANCIAL_ID, ESBusinessTestUtil.ES_COUNTRY_CODE)
                .setOperationalContextUID(this.context.getUID()).setWebsite(ESBusinessTestUtil.WEBSITE)
                .setName(ESBusinessTestUtil.NAME);

        return businessBuilder;
    }
}
