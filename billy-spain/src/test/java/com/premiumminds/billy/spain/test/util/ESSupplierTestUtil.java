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
import com.premiumminds.billy.spain.persistence.entities.ESSupplierEntity;
import com.premiumminds.billy.spain.services.entities.ESAddress;
import com.premiumminds.billy.spain.services.entities.ESContact;
import com.premiumminds.billy.spain.services.entities.ESSupplier;

public class ESSupplierTestUtil {

    private static final Boolean SELF_BILLING = false;
    private static final String NUMBER = "11111111H";
    private static final String NAME = "Supplier";
    protected static final String ES_COUNTRY_CODE = "ES";

    private Injector injector;
    private ESAddressTestUtil address;
    private ESContactTestUtil contact;

    public ESSupplierTestUtil(Injector injector) {
        this.injector = injector;
        this.address = new ESAddressTestUtil(injector);
        this.contact = new ESContactTestUtil(injector);
    }

    public ESSupplierEntity getSupplierEntity() {

        ESAddress.Builder addressBuilder = this.address.getAddressBuilder();
        ESContact.Builder contactBuilder = this.contact.getContactBuilder();

        return this.getSupplierEntity(ESSupplierTestUtil.NAME, ESSupplierTestUtil.NUMBER,
                ESSupplierTestUtil.SELF_BILLING, addressBuilder, contactBuilder);
    }

    public ESSupplierEntity getSupplierEntity(String name, String taxNumber, boolean selfBillingAgree,
            ESAddress.Builder addressBuilder, ESContact.Builder contactBuilder) {

        return (ESSupplierEntity) this
                .getSupplierBuilder(name, taxNumber, selfBillingAgree, addressBuilder, contactBuilder).build();
    }

    public ESSupplier.Builder getSupplierBuilder(String name, String taxNumber, boolean selfBillingAgree,
            ESAddress.Builder addressBuilder, ESContact.Builder contactBuilder) {
        ESSupplier.Builder supplierBuilder = this.injector.getInstance(ESSupplier.Builder.class);

        supplierBuilder.addAddress(addressBuilder).addContact(contactBuilder).setBillingAddress(addressBuilder)
                .setMainContact(contactBuilder).setSelfBillingAgreement(selfBillingAgree)
                .setTaxRegistrationNumber(taxNumber, ESSupplierTestUtil.ES_COUNTRY_CODE).setName(name)
                .setMainAddress(addressBuilder);
        return supplierBuilder;
    }
}
