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
