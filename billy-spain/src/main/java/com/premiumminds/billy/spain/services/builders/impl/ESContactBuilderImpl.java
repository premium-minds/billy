/**
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
import com.premiumminds.billy.core.services.builders.impl.ContactBuilderImpl;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.spain.persistence.dao.DAOESContact;
import com.premiumminds.billy.spain.persistence.entities.ESContactEntity;
import com.premiumminds.billy.spain.services.builders.ESContactBuilder;
import com.premiumminds.billy.spain.services.entities.ESContact;

public class ESContactBuilderImpl<TBuilder extends ESContactBuilderImpl<TBuilder, TContact>, TContact extends ESContact>
        extends ContactBuilderImpl<TBuilder, TContact> implements ESContactBuilder<TBuilder, TContact> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public ESContactBuilderImpl(DAOESContact daoESContact) {
        super(daoESContact);
    }

    @Override
    protected ESContactEntity getTypeInstance() {
        return (ESContactEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
    }
}
