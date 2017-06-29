/**
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
import com.premiumminds.billy.spain.services.entities.ESAddress;

public class ESAddressTestUtil {

    private static final String NUMBER = "1";
    private static final String STREET = "street";
    private static final String BUILDING = "building";
    private static final String CITY = "city";
    private static final String REGION = "region";
    private static final String ISOCODE = "ES";
    private static final String DETAILS = "details";
    private static final String POSTAL_CODE = "10000";

    private Injector injector;

    public ESAddressTestUtil(Injector injector) {
        this.injector = injector;
    }

    public ESAddress.Builder getAddressBuilder(String streetName, String number, String details, String building,
            String city, String postalCode, String region, String isoCountry) {

        ESAddress.Builder addressBuilder = this.injector.getInstance(ESAddress.Builder.class);

        addressBuilder.clear();

        addressBuilder.setBuilding(building).setCity(city).setDetails(details).setISOCountry(isoCountry)
                .setNumber(number).setRegion(region).setStreetName(streetName).setPostalCode(postalCode);

        return addressBuilder;
    }

    public ESAddress.Builder getAddressBuilder() {
        return this.getAddressBuilder(ESAddressTestUtil.STREET, ESAddressTestUtil.NUMBER, ESAddressTestUtil.DETAILS,
                ESAddressTestUtil.BUILDING, ESAddressTestUtil.CITY, ESAddressTestUtil.POSTAL_CODE,
                ESAddressTestUtil.REGION, ESAddressTestUtil.ISOCODE);
    }
}
