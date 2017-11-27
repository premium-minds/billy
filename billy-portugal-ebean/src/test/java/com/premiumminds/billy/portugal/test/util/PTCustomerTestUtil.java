/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTCustomer;

public class PTCustomerTestUtil {

    private static final String NAME = "Name";
    private static final String TAX_NUMBER = "123456789";
    private static final Boolean SELF_BILLING_AGREE = false;
    protected static final String PT_COUNTRY_CODE = "PT";

    private Injector injector;
    private PTAddressTestUtil address;
    private PTContactTestUtil contact;

    public PTCustomerTestUtil(Injector injector) {
        this.injector = injector;
        this.address = new PTAddressTestUtil(injector);
        this.contact = new PTContactTestUtil(injector);
    }

    public PTCustomerEntity getCustomerEntity(String uid) {
        PTCustomerEntity customer = (PTCustomerEntity) this.getCustomerBuilder().build();
        customer.setUID(new UID(uid));
        return customer;
    }

    public PTCustomerEntity getCustomerEntity() {
        return (PTCustomerEntity) this.getCustomerBuilder().build();
    }

    public PTCustomer.Builder getCustomerBuilder() {
        PTAddress.Builder addressBuilder = this.address.getAddressBuilder();
        PTContact.Builder contactBuilder = this.contact.getContactBuilder();

        return this.getCustomerBuilder(PTCustomerTestUtil.NAME, PTCustomerTestUtil.TAX_NUMBER,
                PTCustomerTestUtil.SELF_BILLING_AGREE, addressBuilder, contactBuilder);
    }

    public PTCustomer.Builder getCustomerBuilder(String name, String taxNumber, Boolean selfBilling,
            PTAddress.Builder addressBuilder, PTContact.Builder contactBuilder) {

        PTCustomer.Builder customerBuilder = this.injector.getInstance(PTCustomer.Builder.class);

        return customerBuilder.addAddress(addressBuilder, true).addContact(contactBuilder)
                .setBillingAddress(addressBuilder).setName(name).setHasSelfBillingAgreement(selfBilling)
                .setTaxRegistrationNumber(taxNumber, PTCustomerTestUtil.PT_COUNTRY_CODE);
    }

    public PTCustomerEntity getCustomerEntity(String name, String taxNumber, boolean selfBilling,
            PTAddress.Builder addressBuilder, PTContact.Builder contactBuilder) {

        PTCustomerEntity customer = (PTCustomerEntity) this.getCustomerBuilder().build();

        return customer;
    }
}
