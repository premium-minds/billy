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
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.france.persistence.entities.FRCustomerEntity;
import com.premiumminds.billy.france.services.entities.FRAddress;
import com.premiumminds.billy.france.services.entities.FRContact;
import com.premiumminds.billy.france.services.entities.FRCustomer;

public class FRCustomerTestUtil {

    private static final String NAME = "Name";
    private static final String TAX_NUMBER = "11111111H";
    private static final Boolean SELF_BILLING_AGREE = false;
    protected static final String FR_COUNTRY_CODE = "FR";

    private Injector injector;
    private FRAddressTestUtil address;
    private FRContactTestUtil contact;

    public FRCustomerTestUtil(Injector injector) {
        this.injector = injector;
        this.address = new FRAddressTestUtil(injector);
        this.contact = new FRContactTestUtil(injector);
    }

    public FRCustomerEntity getCustomerEntity(String uid) {
        FRCustomerEntity customer = (FRCustomerEntity) this.getCustomerBuilder().build();
        customer.setUID(new UID(uid));
        return customer;
    }

    public FRCustomerEntity getCustomerEntity() {
        return (FRCustomerEntity) this.getCustomerBuilder().build();
    }

    public FRCustomer.Builder getCustomerBuilder() {
        FRAddress.Builder addressBuilder = this.address.getAddressBuilder();
        FRContact.Builder contactBuilder = this.contact.getContactBuilder();

        return this.getCustomerBuilder(FRCustomerTestUtil.NAME, FRCustomerTestUtil.TAX_NUMBER,
                FRCustomerTestUtil.SELF_BILLING_AGREE, addressBuilder, contactBuilder);
    }

    public FRCustomer.Builder getCustomerBuilder(String name, String taxNumber, Boolean selfBilling,
            FRAddress.Builder addressBuilder, FRContact.Builder contactBuilder) {

        FRCustomer.Builder customerBuilder = this.injector.getInstance(FRCustomer.Builder.class);

        return customerBuilder.addAddress(addressBuilder, true).addContact(contactBuilder)
                .setBillingAddress(addressBuilder).setName(name).setHasSelfBillingAgreement(selfBilling)
                .setTaxRegistrationNumber(taxNumber, FRCustomerTestUtil.FR_COUNTRY_CODE);
    }

    public FRCustomerEntity getCustomerEntity(String name, String taxNumber, boolean selfBilling,
            FRAddress.Builder addressBuilder, FRContact.Builder contactBuilder) {

        FRCustomerEntity customer = (FRCustomerEntity) this.getCustomerBuilder().build();

        return customer;
    }
}
