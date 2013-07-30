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

	private final String name = "name";
	private final String telephone = "998887999";
	private final String mobile = "999999999";
	private final String email = "email@email.em";
	private final String fax = "9999999122";
	private final String website = "website@website.web";

	private Injector injector;

	public PTContactTestUtil(Injector injector) {
		this.injector = injector;
	}

	public PTContact.Builder getContactBuilder() {
		PTContact.Builder contactBuilder = injector
				.getInstance(PTContact.Builder.class);

		contactBuilder.setName(name).setEmail(email).setMobile(mobile)
				.setFax(fax).setTelephone(telephone).setWebsite(website);

		return contactBuilder;
	}
}
