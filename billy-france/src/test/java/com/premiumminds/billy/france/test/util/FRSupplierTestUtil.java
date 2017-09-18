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

import com.google.inject.Injector;
import com.premiumminds.billy.france.persistence.entities.FRSupplierEntity;
import com.premiumminds.billy.france.services.entities.FRAddress;
import com.premiumminds.billy.france.services.entities.FRContact;
import com.premiumminds.billy.france.services.entities.FRSupplier;

public class FRSupplierTestUtil {

    private static final Boolean SELF_BILLING = false;
    private static final String NUMBER = "11111111H";
    private static final String NAME = "Supplier";
    protected static final String FR_COUNTRY_CODE = "FR";

    private Injector injector;
    private FRAddressTestUtil address;
    private FRContactTestUtil contact;

    public FRSupplierTestUtil(Injector injector) {
        this.injector = injector;
        this.address = new FRAddressTestUtil(injector);
        this.contact = new FRContactTestUtil(injector);
    }

    public FRSupplierEntity getSupplierEntity() {

        FRAddress.Builder addressBuilder = this.address.getAddressBuilder();
        FRContact.Builder contactBuilder = this.contact.getContactBuilder();

        return this.getSupplierEntity(FRSupplierTestUtil.NAME, FRSupplierTestUtil.NUMBER,
                FRSupplierTestUtil.SELF_BILLING, addressBuilder, contactBuilder);
    }

    public FRSupplierEntity getSupplierEntity(String name, String taxNumber, boolean selfBillingAgree,
            FRAddress.Builder addressBuilder, FRContact.Builder contactBuilder) {

        return (FRSupplierEntity) this
                .getSupplierBuilder(name, taxNumber, selfBillingAgree, addressBuilder, contactBuilder).build();
    }

    public FRSupplier.Builder getSupplierBuilder(String name, String taxNumber, boolean selfBillingAgree,
            FRAddress.Builder addressBuilder, FRContact.Builder contactBuilder) {
        FRSupplier.Builder supplierBuilder = this.injector.getInstance(FRSupplier.Builder.class);

        supplierBuilder.addAddress(addressBuilder).addContact(contactBuilder).setBillingAddress(addressBuilder)
                .setMainContact(contactBuilder).setSelfBillingAgreement(selfBillingAgree)
                .setTaxRegistrationNumber(taxNumber, FRSupplierTestUtil.FR_COUNTRY_CODE).setName(name)
                .setMainAddress(addressBuilder);
        return supplierBuilder;
    }
}
