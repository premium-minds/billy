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

import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.andorra.services.entities.ADAddress;
import com.premiumminds.billy.andorra.services.entities.ADShippingPoint;

public class ADShippingPointTestUtil {

    private final String deliveryId = "delivery_spot";
    private final String locationId = "location_spot";
    private final String warehouseId = "warehouse1";

    private Injector injector;
    private ADAddressTestUtil address;

    public ADShippingPointTestUtil(Injector injector) {
        this.injector = injector;
        this.address = new ADAddressTestUtil(injector);
    }

    public ADShippingPoint.Builder getShippingPointBuilder() {
        ADShippingPoint.Builder shippingPointBuilder = this.injector.getInstance(ADShippingPoint.Builder.class);
        ADAddress.Builder addressBuilder = this.address.getAddressBuilder();

        shippingPointBuilder.clear();

        shippingPointBuilder.setAddress(addressBuilder).setDate(new Date()).setDeliveryId(this.deliveryId)
                .setLocationId(this.locationId).setWarehouseId(this.warehouseId);

        return shippingPointBuilder;
    }
}
