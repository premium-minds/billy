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

import com.premiumminds.billy.core.persistence.dao.DAOApplication;
import com.premiumminds.billy.core.services.builders.impl.ApplicationBuilderImpl;

/**
 * @author Francisco Vargas
 *
 *         The Billy services entity for an application which is a client of
 *         Billy.
 */
public interface Application extends Entity<Application> {

    /**
     * The builder for the {@link Application} entity.
     */
    public static class Builder extends ApplicationBuilderImpl<Builder, Application> {

        @Inject
        public Builder(DAOApplication daoApplication) {
            super(daoApplication);
        }
    }

    /**
     * Gets the name of the application.
     *
     * @return The application name.
     */
    public String getName();

    /**
     * Gets the application version.
     *
     * @return The application version.
     */
    public String getVersion();

    /**
     * Gets the name of the application developer company.
     *
     * @return The name of the developer company.
     */
    public String getDeveloperCompanyName();

    /**
     * Gets the tax identifier for the application developer company.
     *
     * @return The company tax identifier.
     */
    public String getDeveloperCompanyTaxIdentifier();

    /**
     * Gets the tax identifier iso country code for the application developer company.
     *
     * @return The company tax identifier iso country code.
     */
    public String getDeveloperCompanyTaxIdentifierISOCountryCode();

    /**
     * Gets the application developer company website address.
     *
     * @return The website address.
     */
    public String getWebsiteAddress();

    /**
     * Gets the application developer company collection of {@link Contact}
     * contacts.
     *
     * @param <T> contact type class
     * @return The list of contacts.
     */
    public <T extends Contact> Collection<T> getContacts();

    public <T extends Contact> Contact getMainContact();

}
