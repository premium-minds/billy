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
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.andorra.persistence.entities.ADCustomerEntity;
import com.premiumminds.billy.andorra.services.entities.ADAddress;
import com.premiumminds.billy.andorra.services.entities.ADContact;
import com.premiumminds.billy.andorra.services.entities.ADCustomer;

public class ESCustomerTestUtil {

    private static final String NAME = "Name";
    private static final String TAX_NUMBER = "11111111H";
    private static final Boolean SELF_BILLING_AGREE = false;
    protected static final String ES_COUNTRY_CODE = "ES";

    private Injector injector;
    private ESAddressTestUtil address;
    private ESContactTestUtil contact;

    public ESCustomerTestUtil(Injector injector) {
        this.injector = injector;
        this.address = new ESAddressTestUtil(injector);
        this.contact = new ESContactTestUtil(injector);
    }

    public ADCustomerEntity getCustomerEntity(String uid) {
        ADCustomerEntity customer = (ADCustomerEntity) this.getCustomerBuilder().build();
        customer.setUID(StringID.fromValue(uid));
        return customer;
    }

    public ADCustomerEntity getCustomerEntity() {
        return (ADCustomerEntity) this.getCustomerBuilder().build();
    }

    public ADCustomer.Builder getCustomerBuilder() {
        ADAddress.Builder addressBuilder = this.address.getAddressBuilder();
        ADContact.Builder contactBuilder = this.contact.getContactBuilder();

        return this.getCustomerBuilder(ESCustomerTestUtil.NAME, ESCustomerTestUtil.TAX_NUMBER,
                ESCustomerTestUtil.SELF_BILLING_AGREE, addressBuilder, contactBuilder);
    }

    public ADCustomer.Builder getCustomerBuilder(String name, String taxNumber, Boolean selfBilling,
												 ADAddress.Builder addressBuilder, ADContact.Builder contactBuilder) {

        ADCustomer.Builder customerBuilder = this.injector.getInstance(ADCustomer.Builder.class);

        return customerBuilder.addAddress(addressBuilder, true).addContact(contactBuilder)
                .setBillingAddress(addressBuilder).setName(name).setHasSelfBillingAgreement(selfBilling)
                .setTaxRegistrationNumber(taxNumber, ESCustomerTestUtil.ES_COUNTRY_CODE);
    }

    public ADCustomerEntity getCustomerEntity(String name, String taxNumber, boolean selfBilling,
											  ADAddress.Builder addressBuilder, ADContact.Builder contactBuilder) {

        ADCustomerEntity customer = (ADCustomerEntity) this.getCustomerBuilder().build();

        return customer;
    }
}
