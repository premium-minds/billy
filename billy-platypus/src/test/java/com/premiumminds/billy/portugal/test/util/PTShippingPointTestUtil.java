package com.premiumminds.billy.portugal.test.util;

import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTShippingPoint;

public class PTShippingPointTestUtil {

	private final String deliveryId = "delivery_spot";
	private final String locationId = "location_spot";
	private final String warehouseId = "warehouse1";

	private Injector injector;
	private PTAddressTestUtil address;

	public PTShippingPointTestUtil(Injector injector) {
		this.injector = injector;
		address = new PTAddressTestUtil(injector);
	}

	public PTShippingPoint.Builder getShippingPointBuilder() {
		PTShippingPoint.Builder shippingPointBuilder = injector
				.getInstance(PTShippingPoint.Builder.class);
		PTAddress.Builder addressBuilder = address.getAddressBuilder();

		shippingPointBuilder.clear();

		shippingPointBuilder.setAddress(addressBuilder).setDate(new Date())
				.setDeliveryId(deliveryId).setLocationId(locationId)
				.setWarehouseId(warehouseId);

		return shippingPointBuilder;
	}
}
