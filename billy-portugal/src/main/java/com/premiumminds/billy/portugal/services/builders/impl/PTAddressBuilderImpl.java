/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

import java.util.Arrays;
import java.util.Locale;
import jakarta.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.AddressBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.portugal.PortugalBootstrap;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTAddress;
import com.premiumminds.billy.portugal.persistence.entities.PTAddressEntity;
import com.premiumminds.billy.portugal.services.builders.PTAddressBuilder;
import com.premiumminds.billy.portugal.services.entities.PTAddress;

public class PTAddressBuilderImpl<TBuilder extends PTAddressBuilderImpl<TBuilder, TAddress>, TAddress extends PTAddress>
        extends AddressBuilderImpl<TBuilder, TAddress> implements PTAddressBuilder<TBuilder, TAddress> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    protected PTAddressBuilderImpl(DAOPTAddress daoPTAddress) {
        super(daoPTAddress);
    }

    @Override
    protected PTAddressEntity getTypeInstance() {
        return (PTAddressEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        super.validateInstance();
        PTAddressEntity address = this.getTypeInstance();
        BillyValidator.mandatory(address.getDetails(), PTAddressBuilderImpl.LOCALIZER.getString("field.details"));
        BillyValidator.mandatory(address.getISOCountry(), PTAddressBuilderImpl.LOCALIZER.getString("field.country"));
        BillyValidator.isTrue(
                Arrays.stream(Locale.getISOCountries()).anyMatch(x -> x.equals(address.getISOCountry()))
                        || address.getISOCountry().equals(PortugalBootstrap.ISO_CONTRY_UNKNOW),
                PTAddressBuilderImpl.LOCALIZER.getString("field.country_iso_code", address.getISOCountry())
        );
    }
}
