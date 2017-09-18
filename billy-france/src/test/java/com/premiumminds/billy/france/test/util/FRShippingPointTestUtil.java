/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.test.util;

import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.france.services.entities.FRAddress;
import com.premiumminds.billy.france.services.entities.FRShippingPoint;

public class FRShippingPointTestUtil {

    private final String deliveryId = "delivery_spot";
    private final String locationId = "location_spot";
    private final String warehouseId = "warehouse1";

    private Injector injector;
    private FRAddressTestUtil address;

    public FRShippingPointTestUtil(Injector injector) {
        this.injector = injector;
        this.address = new FRAddressTestUtil(injector);
    }

    public FRShippingPoint.Builder getShippingPointBuilder() {
        FRShippingPoint.Builder shippingPointBuilder = this.injector.getInstance(FRShippingPoint.Builder.class);
        FRAddress.Builder addressBuilder = this.address.getAddressBuilder();

        shippingPointBuilder.clear();

        shippingPointBuilder.setAddress(addressBuilder).setDate(new Date()).setDeliveryId(this.deliveryId)
                .setLocationId(this.locationId).setWarehouseId(this.warehouseId);

        return shippingPointBuilder;
    }
}
