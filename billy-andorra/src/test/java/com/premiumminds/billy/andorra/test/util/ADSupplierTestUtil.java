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
import com.premiumminds.billy.andorra.persistence.entities.ADSupplierEntity;
import com.premiumminds.billy.andorra.services.entities.ADAddress;
import com.premiumminds.billy.andorra.services.entities.ADContact;
import com.premiumminds.billy.andorra.services.entities.ADSupplier;

public class ADSupplierTestUtil {

    private static final Boolean SELF_BILLING = false;
    private static final String NUMBER = "A798765Z";
    private static final String NAME = "Supplier";
    protected static final String AD_COUNTRY_CODE = "AD";

    private Injector injector;
    private ADAddressTestUtil address;
    private ADContactTestUtil contact;

    public ADSupplierTestUtil(Injector injector) {
        this.injector = injector;
        this.address = new ADAddressTestUtil(injector);
        this.contact = new ADContactTestUtil(injector);
    }

    public ADSupplierEntity getSupplierEntity() {

        ADAddress.Builder addressBuilder = this.address.getAddressBuilder();
        ADContact.Builder contactBuilder = this.contact.getContactBuilder();

        return this.getSupplierEntity(ADSupplierTestUtil.NAME, ADSupplierTestUtil.NUMBER,
                                      ADSupplierTestUtil.SELF_BILLING, addressBuilder, contactBuilder);
    }

    public ADSupplierEntity getSupplierEntity(String name, String taxNumber, boolean selfBillingAgree,
                                              ADAddress.Builder addressBuilder, ADContact.Builder contactBuilder) {

        return (ADSupplierEntity) this
                .getSupplierBuilder(name, taxNumber, selfBillingAgree, addressBuilder, contactBuilder).build();
    }

    public ADSupplier.Builder getSupplierBuilder(String name, String taxNumber, boolean selfBillingAgree,
                                                 ADAddress.Builder addressBuilder, ADContact.Builder contactBuilder) {
        ADSupplier.Builder supplierBuilder = this.injector.getInstance(ADSupplier.Builder.class);

        supplierBuilder.addAddress(addressBuilder).addContact(contactBuilder).setBillingAddress(addressBuilder)
                       .setMainContact(contactBuilder).setSelfBillingAgreement(selfBillingAgree)
                       .setTaxRegistrationNumber(taxNumber, ADSupplierTestUtil.AD_COUNTRY_CODE).setName(name)
                       .setMainAddress(addressBuilder);
        return supplierBuilder;
    }
}
