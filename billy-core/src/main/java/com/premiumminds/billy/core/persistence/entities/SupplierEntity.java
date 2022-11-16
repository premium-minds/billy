/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.persistence.entities;

import java.util.List;

import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Supplier;

public interface SupplierEntity extends Supplier {

    void setName(String name);

    void setTaxRegistrationNumber(String number);

    void setTaxRegistrationNumberISOCountryCode(String isoCountryCode);

    @Override <T extends Address> List<T> getAddresses();

    <T extends AddressEntity> void setMainAddress(T address);

    <T extends AddressEntity> void setBillingAddress(T address);

    <T extends AddressEntity> void setShippingAddress(T address);

    @Override <T extends Contact> List<T> getContacts();

    <T extends ContactEntity> void setMainContact(T contact);

    @Override <T extends BankAccount> List<T> getBankAccounts();

    void setSelfBillingAgreement(boolean selfBilling);

}
