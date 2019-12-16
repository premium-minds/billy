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

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOAddress;
import com.premiumminds.billy.core.services.builders.impl.AddressBuilderImpl;

/**
 * @author Francisco Vargas
 *
 *         The Billy services entity for an address
 */
public interface Address extends Entity {

    /**
     * @author Francisco Vargas
     *
     *         The builder for and {@link Address} instance.
     */
    public static class Builder extends AddressBuilderImpl<Builder, Address> {

        @Inject
        public Builder(DAOAddress daoAddress) {
            super(daoAddress);
        }
    }

    public String getStreetName();

    public String getNumber();

    public String getDetails();

    public String getBuilding();

    public String getCity();

    public String getPostalCode();

    /**
     * Gets the ISO 3166-2 code for the country region
     *
     * @return The region ISO code
     */
    public String getRegion();

    /**
     * Gets the address country ISO 3166-1 code.
     *
     * @return The country iso code.
     */
    public String getISOCountry();

}
