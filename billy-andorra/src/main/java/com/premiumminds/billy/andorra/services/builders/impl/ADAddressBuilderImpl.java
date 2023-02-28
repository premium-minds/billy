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
package com.premiumminds.billy.andorra.services.builders.impl;

import com.premiumminds.billy.andorra.persistence.entities.ADAddressEntity;
import com.premiumminds.billy.andorra.services.entities.ADAddress;
import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.AddressBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADAddress;
import com.premiumminds.billy.andorra.services.builders.ADAddressBuilder;

public class ADAddressBuilderImpl<TBuilder extends ADAddressBuilderImpl<TBuilder, TAddress>, TAddress extends ADAddress>
        extends AddressBuilderImpl<TBuilder, TAddress> implements ADAddressBuilder<TBuilder, TAddress>
{

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    protected ADAddressBuilderImpl(DAOADAddress daoESAddress) {
        super(daoESAddress);
    }

    @Override
    protected ADAddressEntity getTypeInstance() {
        return (ADAddressEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        super.validateInstance();
        ADAddressEntity address = this.getTypeInstance();
        BillyValidator.mandatory(address.getDetails(), ADAddressBuilderImpl.LOCALIZER.getString("field.details"));
        BillyValidator.mandatory(address.getISOCountry(), ADAddressBuilderImpl.LOCALIZER.getString("field.country"));
    }
}
