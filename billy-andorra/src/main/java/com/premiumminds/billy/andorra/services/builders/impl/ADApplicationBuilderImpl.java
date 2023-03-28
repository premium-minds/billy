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

import com.premiumminds.billy.andorra.persistence.entities.ADApplicationEntity;
import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.ApplicationBuilderImpl;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADApplication;
import com.premiumminds.billy.andorra.services.builders.ADApplicationBuilder;
import com.premiumminds.billy.andorra.services.entities.ADApplication;

public class ADApplicationBuilderImpl<TBuilder extends ADApplicationBuilderImpl<TBuilder, TApplication>, TApplication extends ADApplication>
        extends ApplicationBuilderImpl<TBuilder, TApplication> implements ADApplicationBuilder<TBuilder, TApplication>
{

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public ADApplicationBuilderImpl(DAOADApplication daoADApplication) {
        super(daoADApplication);
    }

    @Override
    protected ADApplicationEntity getTypeInstance() {
        return (ADApplicationEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        super.validateInstance();
    }

}
