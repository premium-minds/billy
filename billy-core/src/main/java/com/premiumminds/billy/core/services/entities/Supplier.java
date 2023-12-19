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
package com.premiumminds.billy.core.services.entities;

import java.util.Collection;

import jakarta.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOSupplier;
import com.premiumminds.billy.core.services.builders.impl.SupplierBuilderImpl;

public interface Supplier extends Entity<Supplier> {

    public static class Builder extends SupplierBuilderImpl<Builder, Supplier> {

        @Inject
        public Builder(DAOSupplier daoSupplier) {
            super(daoSupplier);
        }
    }

    public String getName();

    public String getTaxRegistrationNumber();

    public String getTaxRegistrationNumberISOCountryCode();

    public <T extends Address> Collection<T> getAddresses();

    public Address getMainAddress();

    public Address getBillingAddress();

    public Address getShippingAddress();

    public <T extends Contact> Collection<T> getContacts();

    public Contact getMainContact();

    public <T extends BankAccount> Collection<T> getBankAccounts();

    public boolean hasSelfBillingAgreement();

}
