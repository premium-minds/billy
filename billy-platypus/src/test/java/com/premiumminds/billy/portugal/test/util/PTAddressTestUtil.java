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
import com.premiumminds.billy.portugal.services.entities.PTAddress;

public class PTAddressTestUtil {

	private final String number = "1";
	private final String street = "street";
	private final String building = "building";
	private final String city = "city";
	private final String region = "region";
	private final String isoCode = "PT";
	private final String details = "details";
	private final String postalCode = "1000-000";

	private Injector injector;

	public PTAddressTestUtil(Injector injector) {
		this.injector = injector;
	}

	public PTAddress.Builder getAddressBuilder() {
		PTAddress.Builder addressBuilder = injector
				.getInstance(PTAddress.Builder.class);

		addressBuilder.clear();

		addressBuilder.setBuilding(building).setCity(city).setDetails(details)
				.setISOCountry(isoCode).setNumber(number).setRegion(region)
				.setStreetName(street).setPostalCode(postalCode);

		return addressBuilder;
	}
}
