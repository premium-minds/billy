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

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.services.builders.impl.CustomerBuilderImpl;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;

/**
 * @author Francisco Vargas
 *
 *         The Billy services entity for a customer. A customer is someone for
 *         which a {@link GenericInvoice} is issued.
 */
public interface Customer extends Entity<Customer> {

    public static class Builder extends CustomerBuilderImpl<Builder, Customer> {

        @Inject
        public Builder(DAOCustomer daoCustomer, DAOContact daoContact) {
            super(daoCustomer, daoContact);
        }
    }

    public String getName();

    public String getTaxRegistrationNumber();

    public String getTaxRegistrationNumberISOCountryCode();

    public <T extends Address> Collection<T> getAddresses();

    public <T extends Address> T getMainAddress();

    public <T extends Address> T getBillingAddress();

    public <T extends Address> T getShippingAddress();

    public <T extends Contact> Collection<T> getContacts();

    public <T extends Contact> T getMainContact();

    public <T extends BankAccount> Collection<T> getBankAccounts();

    public boolean hasSelfBillingAgreement();

}
