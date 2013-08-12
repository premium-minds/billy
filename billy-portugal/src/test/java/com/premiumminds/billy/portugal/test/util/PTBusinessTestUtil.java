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
import com.premiumminds.billy.portugal.persistence.entities.PTContactEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.util.Contexts;

public class PTBusinessTestUtil {

	private final String name = "Business";
	private final String id = "123456789";
	private final String website = "http://business.com";
	private final String UID = "BUSINESS";

	private Injector injector;
	private PTApplicationTestUtil application;
	private PTContactTestUtil contact;
	private PTAddressTestUtil address;
	private Contexts contexts;
	private PTRegionContext context;

	public PTBusinessTestUtil(Injector injector) {
		this.injector = injector;
		this.application = new PTApplicationTestUtil(injector);
		this.contact = new PTContactTestUtil(injector);
		this.address = new PTAddressTestUtil(injector);
		this.contexts = new Contexts(injector);
		this.context = this.contexts.portugal().portugal();
	}

	public PTBusinessEntity getBusinessEntity(String uid, UID contextUID,
			PTContact.Builder contactBuilder, PTAddress.Builder addressBuilder,
			PTApplication.Builder applicationBuilder) {
		PTBusiness.Builder businessBuilder = this.injector
				.getInstance(PTBusiness.Builder.class);

		PTContactEntity contact = (PTContactEntity) contactBuilder.build();

		businessBuilder.clear();

		businessBuilder.addApplication(applicationBuilder)
				.addContact(contactBuilder).setAddress(addressBuilder)
				.setBillingAddress(addressBuilder).setCommercialName(this.name)
				.setFinancialID(this.id).setOperationalContextUID(contextUID)
				.setMainContactUID(contact.getUID()).setWebsite(this.website)
				.setName(this.name);

		PTBusinessEntity business = (PTBusinessEntity) businessBuilder.build();
		business.setUID(new UID(uid));

		return business;
	}

	public PTBusinessEntity getBusinessEntity(String uid) {
		PTApplication.Builder applicationBuilder = null;
		try {
			applicationBuilder = this.application.getApplicationBuilder();
		} catch (MalformedURLException e) {

		}
		PTContact.Builder contactBuilder = this.contact.getContactBuilder();
		PTAddress.Builder addressBuilder = this.address.getAddressBuilder();

		return this.getBusinessEntity(uid, this.context.getUID(),
				contactBuilder, addressBuilder, applicationBuilder);

	}

	public PTBusinessEntity getBusinessEntity() {
		return this.getBusinessEntity(this.UID);
	}

	public PTBusiness.Builder getBusinessBuilder() throws MalformedURLException {
		PTBusiness.Builder businessBuilder = this.injector
				.getInstance(PTBusiness.Builder.class);
		PTApplication.Builder applicationBuilder = this.application
				.getApplicationBuilder();
		PTContact.Builder contactBuilder = this.contact.getContactBuilder();
		PTAddress.Builder addressBuilder = this.address.getAddressBuilder();
		this.context = this.contexts.portugal().portugal();

		businessBuilder.clear();

		businessBuilder.addApplication(applicationBuilder)
				.addContact(contactBuilder).setAddress(addressBuilder)
				.setBillingAddress(addressBuilder).setCommercialName(this.name)
				.setFinancialID(this.id)
				.setOperationalContextUID(this.context.getUID())
				.setWebsite(this.website).setName(this.name);

		return businessBuilder;
	}
}
