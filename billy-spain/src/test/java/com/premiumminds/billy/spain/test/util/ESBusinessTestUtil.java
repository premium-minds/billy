/*
 * Copyright (C) 2017 Premium Minds.
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

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.entities.ESBusinessEntity;
import com.premiumminds.billy.spain.services.entities.*;
import com.premiumminds.billy.spain.util.Contexts;

import javax.persistence.NoResultException;
import java.net.MalformedURLException;
import java.time.ZoneId;
import java.util.UUID;

public class ESBusinessTestUtil {

    private static final String NAME = "Business";
    private static final String FINANCIAL_ID = "11111111H";
    private static final String WEBSITE = "http://business.com";
    protected static final String ES_COUNTRY_CODE = "ES";

    private Injector injector;
    private ESApplicationTestUtil application;
    private ESContactTestUtil contact;
    private ESAddressTestUtil address;
    private ESRegionContext context;

    public ESBusinessTestUtil(Injector injector) {
        this.injector = injector;
        this.application = new ESApplicationTestUtil(injector);
        this.contact = new ESContactTestUtil(injector);
        this.address = new ESAddressTestUtil(injector);

        this.context = new Contexts(injector).spain().allRegions();
    }

    public ESBusinessEntity getBusinessEntity() {
        return this.getBusinessEntity(StringID.fromValue(UUID.randomUUID().toString()));
    }

    public ESBusinessEntity getBusinessEntity(StringID<Business> businessID) {
        ESBusinessEntity business = null;
        try {
            business = this.injector.getInstance(DAOESBusiness.class).get(businessID);
        } catch (NoResultException e) {
            business = (ESBusinessEntity) this.getBusinessBuilder().build();
            business.setUID(businessID);
            this.injector.getInstance(DAOESBusiness.class).create(business);
        }

        return business;
    }

    public ESBusiness.Builder getBusinessBuilder() {
        ESBusiness.Builder businessBuilder = this.injector.getInstance(ESBusiness.Builder.class);

        ESApplication.Builder applicationBuilder = null;
        try {
            applicationBuilder = this.application.getApplicationBuilder();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ESContact.Builder contactBuilder = this.contact.getContactBuilder();
        ESAddress.Builder addressBuilder = this.address.getAddressBuilder();

        businessBuilder
                .addApplication(applicationBuilder)
                .addContact(contactBuilder, true)
                .setAddress(addressBuilder)
                .setBillingAddress(addressBuilder)
                .setCommercialName(ESBusinessTestUtil.NAME)
                .setFinancialID(ESBusinessTestUtil.FINANCIAL_ID, ESBusinessTestUtil.ES_COUNTRY_CODE)
                .setOperationalContextUID(this.context.getUID())
                .setWebsite(ESBusinessTestUtil.WEBSITE)
                .setName(ESBusinessTestUtil.NAME)
                .setTimezone(ZoneId.of("Europe/Madrid"));

        return businessBuilder;
    }
}
