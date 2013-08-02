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

	private static final String NUMBER = "1";
	private static final String STREET = "street";
	private static final String BUILDING = "building";
	private static final String CITY = "city";
	private static final String REGION = "region";
	private static final String ISOCODE = "PT";
	private static final String DETAILS = "details";
	private static final String POSTAL_CODE = "1000-000";

	private Injector injector;

	public PTAddressTestUtil(Injector injector) {
		this.injector = injector;
	}

	public PTAddress.Builder getAddressBuilder(String streetName,
			String number, String details, String building, String city,
			String postalCode, String region, String isoCountry) {

		PTAddress.Builder addressBuilder = injector
				.getInstance(PTAddress.Builder.class);

		addressBuilder.clear();

		addressBuilder.setBuilding(building).setCity(city).setDetails(details)
				.setISOCountry(isoCountry).setNumber(number).setRegion(region)
				.setStreetName(streetName).setPostalCode(postalCode);

		return addressBuilder;
	}

	public PTAddress.Builder getAddressBuilder() {
		return getAddressBuilder(STREET, NUMBER, DETAILS, BUILDING, CITY,
				POSTAL_CODE, REGION, ISOCODE);
	}
}
