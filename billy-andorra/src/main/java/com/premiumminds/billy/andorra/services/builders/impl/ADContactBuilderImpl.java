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

import com.premiumminds.billy.andorra.persistence.entities.ADContactEntity;
import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.ContactBuilderImpl;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADContact;
import com.premiumminds.billy.andorra.services.builders.ADContactBuilder;
import com.premiumminds.billy.andorra.services.entities.ADContact;

public class ADContactBuilderImpl<TBuilder extends ADContactBuilderImpl<TBuilder, TContact>, TContact extends ADContact>
        extends ContactBuilderImpl<TBuilder, TContact> implements ADContactBuilder<TBuilder, TContact>
{

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public ADContactBuilderImpl(DAOADContact daoADContact) {
        super(daoADContact);
    }

    @Override
    protected ADContactEntity getTypeInstance() {
        return (ADContactEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
    }
}
