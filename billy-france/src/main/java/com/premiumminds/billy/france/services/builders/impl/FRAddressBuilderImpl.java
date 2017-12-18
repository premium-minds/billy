/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.AddressBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.france.persistence.dao.DAOFRAddress;
import com.premiumminds.billy.france.persistence.entities.FRAddressEntity;
import com.premiumminds.billy.france.services.builders.FRAddressBuilder;
import com.premiumminds.billy.france.services.entities.FRAddress;

public class FRAddressBuilderImpl<TBuilder extends FRAddressBuilderImpl<TBuilder, TAddress>, TAddress extends FRAddress>
        extends AddressBuilderImpl<TBuilder, TAddress> implements FRAddressBuilder<TBuilder, TAddress> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    protected FRAddressBuilderImpl(DAOFRAddress daoFRAddress) {
        super(daoFRAddress);
    }

    @Override
    protected FRAddressEntity getTypeInstance() {
        return (FRAddressEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        super.validateInstance();
        FRAddressEntity address = this.getTypeInstance();
        BillyValidator.mandatory(address.getDetails(), FRAddressBuilderImpl.LOCALIZER.getString("field.details"));
        BillyValidator.mandatory(address.getISOCountry(), FRAddressBuilderImpl.LOCALIZER.getString("field.country"));
    }
}
