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
import com.premiumminds.billy.andorra.services.entities.ADAddress;

public class ADAddressTestUtil {

    private static final String NUMBER = "1";
    private static final String STREET = "street";
    private static final String BUILDING = "building";
    private static final String CITY = "city";
    private static final String REGION = "region";
    private static final String ISOCODE = "AD";
    private static final String DETAILS = "details";
    private static final String POSTAL_CODE = "10000";

    private Injector injector;

    public ADAddressTestUtil(Injector injector) {
        this.injector = injector;
    }

    public ADAddress.Builder getAddressBuilder(String streetName, String number, String details, String building,
											   String city, String postalCode, String region, String isoCountry) {

        ADAddress.Builder addressBuilder = this.injector.getInstance(ADAddress.Builder.class);

        addressBuilder.clear();

        addressBuilder.setBuilding(building).setCity(city).setDetails(details).setISOCountry(isoCountry)
                .setNumber(number).setRegion(region).setStreetName(streetName).setPostalCode(postalCode);

        return addressBuilder;
    }

    public ADAddress.Builder getAddressBuilder() {
        return this.getAddressBuilder(ADAddressTestUtil.STREET, ADAddressTestUtil.NUMBER, ADAddressTestUtil.DETAILS,
									  ADAddressTestUtil.BUILDING, ADAddressTestUtil.CITY, ADAddressTestUtil.POSTAL_CODE,
									  ADAddressTestUtil.REGION, ADAddressTestUtil.ISOCODE);
    }
}
