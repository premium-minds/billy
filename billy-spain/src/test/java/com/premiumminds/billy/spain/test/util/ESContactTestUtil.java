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

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.spain.persistence.entities.ESContactEntity;
import com.premiumminds.billy.spain.services.entities.ESContact;

public class ESContactTestUtil {

	private static final String	NAME		= "name";
	private static final String	TELEPHONE	= "998887999";
	private static final String	MOBILE		= "999999999";
	private static final String	EMAIL		= "email@email.em";
	private static final String	FAX			= "9999999122";
	private static final String	WEBSITE		= "website@website.web";

	private Injector			injector;

	public ESContactTestUtil(Injector injector) {
		this.injector = injector;
	}

	public ESContact.Builder getContactBuilder(String name, String telephone,
			String mobile, String fax, String email, String website) {
		ESContact.Builder contactBuilder = this.injector
				.getInstance(ESContact.Builder.class);

		contactBuilder.setName(name).setEmail(email).setMobile(mobile)
				.setFax(fax).setTelephone(telephone).setWebsite(website);

		return contactBuilder;

	}

	public ESContactEntity getContactEntity(String uid) {
		ESContactEntity entity = (ESContactEntity) this.getContactBuilder()
				.build();
		entity.setUID(new UID(uid));
		return entity;
	}

	public ESContactEntity getContactEntity() {
		return (ESContactEntity) this.getContactBuilder().build();
	}

	public ESContact.Builder getContactBuilder() {
		return this.getContactBuilder(ESContactTestUtil.NAME,
				ESContactTestUtil.TELEPHONE, ESContactTestUtil.MOBILE,
				ESContactTestUtil.FAX, ESContactTestUtil.EMAIL,
				ESContactTestUtil.WEBSITE);
	}
}
