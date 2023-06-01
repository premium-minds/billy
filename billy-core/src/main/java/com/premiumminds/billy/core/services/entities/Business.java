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

import com.google.inject.Inject;
import com.premiumminds.billy.core.persistence.dao.DAOBusiness;
import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.services.builders.impl.BusinessBuilderImpl;

import java.time.ZoneId;
import java.util.Collection;

/**
 * @author Francisco Vargas
 *
 *         The Billy services entity for a Business. The business is the entity
 *         for which the financial data is being managed.
 */
public interface Business extends Entity<Business> {

    /**
     * @author Francisco Vargas
     *
     *         A builder class for {@link Business} entities.
     */
    public static class Builder extends BusinessBuilderImpl<Builder, Business> {

        @Inject
        public Builder(DAOBusiness daoBusiness, DAOContext daoContext) {
            super(daoBusiness, daoContext);
        }
    }

    public <T extends Context> T getOperationalContext();

    public String getFinancialID();

    public String getFinancialIdISOCountryCode();

    public String getName();

    public String getCommercialName();

    public <T extends Address> T getAddress();

    public <T extends Address> T getBillingAddress();

    public <T extends Address> T getShippingAddress();

    public <T extends Contact> Collection<T> getContacts();

    public <T extends Contact> T getMainContact();

    public String getWebsiteAddress();

    public <T extends Application> Collection<T> getApplications();

    public ZoneId getTimezone();

}
