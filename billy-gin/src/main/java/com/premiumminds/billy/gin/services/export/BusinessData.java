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

public class BusinessData {

    private final String name;
    private final String financialId;
    private final AddressData address;
    private final ContactData mainContact;

    public BusinessData(String name, String financialId, AddressData address, ContactData mainContact) {
        this.name = name;
        this.financialId = financialId;
        this.address = address;
        this.mainContact = mainContact;
    }

    public String getName() {
        return this.name;
    }

    public String getFinancialID() {
        return this.financialId;
    }

    public AddressData getAddress() {
        return this.address;
    }

    public ContactData getMainContact() {
        return this.mainContact;
    }

}
