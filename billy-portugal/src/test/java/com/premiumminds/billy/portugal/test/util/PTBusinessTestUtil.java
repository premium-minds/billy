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

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.util.Contexts;

public class PTBusinessTestUtil {

	private static final String NAME = "Business";
	private static final String FINANCIAL_ID = "123456789";
	private static final String WEBSITE = "http://business.com";

	private Injector injector;
	private PTApplicationTestUtil application;
	private PTContactTestUtil contact;
	private PTAddressTestUtil address;
	private PTRegionContext context;

	public PTBusinessTestUtil(Injector injector) {
		this.injector = injector;
		this.application = new PTApplicationTestUtil(injector);
		this.contact = new PTContactTestUtil(injector);
		this.address = new PTAddressTestUtil(injector);

		this.context = new Contexts(injector).portugal().portugal();
	}

	public PTBusinessEntity getBusinessEntity() {
		PTBusinessEntity business = (PTBusinessEntity) getBusinessBuilder()
				.build();

		return business;
	}

	public PTBusinessEntity getBusinessEntity(String uid) {
		PTBusinessEntity business = (PTBusinessEntity) getBusinessBuilder()
				.build();
		business.setUID(new UID(uid));

		return business;
	}

	public PTBusiness.Builder getBusinessBuilder() {
		PTBusiness.Builder businessBuilder = this.injector
				.getInstance(PTBusiness.Builder.class);

		PTApplication.Builder applicationBuilder = null;
		try {
			applicationBuilder = this.application.getApplicationBuilder();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		PTContact.Builder contactBuilder = this.contact.getContactBuilder();
		PTAddress.Builder addressBuilder = this.address.getAddressBuilder();

		businessBuilder.addApplication(applicationBuilder)
				.addContact(contactBuilder, true).setAddress(addressBuilder)
				.setBillingAddress(addressBuilder).setCommercialName(NAME)
				.setFinancialID(FINANCIAL_ID)
				.setOperationalContextUID(context.getUID()).setWebsite(WEBSITE)
				.setName(NAME);

		return businessBuilder;
	}
}
