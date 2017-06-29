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
import com.premiumminds.billy.core.services.builders.impl.TaxBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.persistence.entities.ESRegionContextEntity;
import com.premiumminds.billy.spain.persistence.entities.ESTaxEntity;
import com.premiumminds.billy.spain.services.builders.ESTaxBuilder;
import com.premiumminds.billy.spain.services.entities.ESTax;

public class ESTaxBuilderImpl<TBuilder extends ESTaxBuilderImpl<TBuilder, TTax>, TTax extends ESTax>
        extends TaxBuilderImpl<TBuilder, TTax> implements ESTaxBuilder<TBuilder, TTax> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public ESTaxBuilderImpl(DAOESTax daoESTax, DAOESRegionContext daoESContext) {
        super(daoESTax, daoESContext);
    }

    @Override
    protected ESTaxEntity getTypeInstance() {
        return (ESTaxEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        ESTaxEntity e = this.getTypeInstance();

        BillyValidator.mandatory(e.getContext(), ESTaxBuilderImpl.LOCALIZER.getString("field.tax_context"));
        BillyValidator.mandatory(e.getTaxRateType(), ESTaxBuilderImpl.LOCALIZER.getString("field.tax_rate_type"));
        BillyValidator.mandatory(e.getCode(), ESTaxBuilderImpl.LOCALIZER.getString("field.tax_code"));
        BillyValidator.mandatory(e.getDescription(), ESTaxBuilderImpl.LOCALIZER.getString("field.tax_description"));

        if (!((DAOESTax) this.daoTax).getTaxes((ESRegionContextEntity) e.getContext(), e.getValidFrom(), e.getValidTo())
                .isEmpty()) {
            throw new BillyValidationException();
        }
        super.validateInstance();
    }

}
