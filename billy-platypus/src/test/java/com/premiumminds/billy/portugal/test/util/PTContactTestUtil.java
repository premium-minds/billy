/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.util;

import com.google.inject.Injector;
import com.premiumminds.billy.portugal.services.entities.PTContact;

public class PTContactTestUtil {

	private static final String NAME = "name";
	private static final String TELEPHONE = "998887999";
	private static final String MOBILE = "999999999";
	private static final String EMAIL = "email@email.em";
	private static final String FAX = "9999999122";
	private static final String WEBSITE = "website@website.web";

	private Injector injector;

	public PTContactTestUtil(Injector injector) {
		this.injector = injector;
	}

	public PTContact.Builder getContactBuilder(String name, String telephone,
			String mobile, String fax, String email, String website) {
		PTContact.Builder contactBuilder = injector
				.getInstance(PTContact.Builder.class);

		contactBuilder.setName(name).setEmail(email).setMobile(mobile)
				.setFax(fax).setTelephone(telephone).setWebsite(website);

		return contactBuilder;

	}

	public PTContact.Builder getContactBuilder() {
		return getContactBuilder(NAME, TELEPHONE, MOBILE, FAX, EMAIL, WEBSITE);
	}
}
