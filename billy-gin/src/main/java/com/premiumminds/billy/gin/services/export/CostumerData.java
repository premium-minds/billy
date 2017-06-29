/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy GIN.
 *
 * billy GIN is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy GIN is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy GIN. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.gin.services.export;

import com.premiumminds.billy.core.services.UID;

public class CostumerData {

    private final UID uid;
    private final String taxRegistrationNumber;
    private final String name;
    private final AddressData billingAddress;

    public CostumerData(UID uid, String taxRegistrationNumber, String name, AddressData billingAddress) {
        this.uid = uid;
        this.taxRegistrationNumber = taxRegistrationNumber;
        this.name = name;
        this.billingAddress = billingAddress;
    }

    public UID getUID() {
        return this.uid;
    }

    public String getTaxRegistrationNumber() {
        return this.taxRegistrationNumber;
    }

    public String getName() {
        return this.name;
    }

    public AddressData getBillingAddress() {
        return this.billingAddress;
    }

}
