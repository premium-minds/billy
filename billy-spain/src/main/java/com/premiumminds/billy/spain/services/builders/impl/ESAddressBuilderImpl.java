/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.AddressBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.spain.persistence.dao.DAOESAddress;
import com.premiumminds.billy.spain.persistence.entities.ESAddressEntity;
import com.premiumminds.billy.spain.services.builders.ESAddressBuilder;
import com.premiumminds.billy.spain.services.entities.ESAddress;

public class ESAddressBuilderImpl<TBuilder extends ESAddressBuilderImpl<TBuilder, TAddress>, TAddress extends ESAddress>
        extends AddressBuilderImpl<TBuilder, TAddress> implements ESAddressBuilder<TBuilder, TAddress> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    protected ESAddressBuilderImpl(DAOESAddress daoESAddress) {
        super(daoESAddress);
    }

    @Override
    protected ESAddressEntity getTypeInstance() {
        return (ESAddressEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        super.validateInstance();
        ESAddressEntity address = this.getTypeInstance();
        BillyValidator.mandatory(address.getDetails(), ESAddressBuilderImpl.LOCALIZER.getString("field.details"));
        BillyValidator.mandatory(address.getISOCountry(), ESAddressBuilderImpl.LOCALIZER.getString("field.country"));
    }
}
