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

import com.premiumminds.billy.andorra.persistence.entities.ADTaxEntity;
import com.premiumminds.billy.andorra.services.entities.ADTax;
import java.util.List;
import jakarta.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.TaxBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;
import com.premiumminds.billy.andorra.persistence.entities.jpa.JPAADTaxEntity;
import com.premiumminds.billy.andorra.services.builders.ADTaxBuilder;

public class ADTaxBuilderImpl<TBuilder extends ADTaxBuilderImpl<TBuilder, TTax>, TTax extends ADTax>
        extends TaxBuilderImpl<TBuilder, TTax> implements ADTaxBuilder<TBuilder, TTax>
{

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public ADTaxBuilderImpl(DAOADTax daoADTax, DAOADRegionContext daoADContext) {
        super(daoADTax, daoADContext);
    }

    @Override
    protected ADTaxEntity getTypeInstance() {
        return (ADTaxEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        ADTaxEntity e = this.getTypeInstance();

        BillyValidator.<Object>mandatory(e.getContext(), ADTaxBuilderImpl.LOCALIZER.getString("field.tax_context"));
        BillyValidator.mandatory(e.getTaxRateType(), ADTaxBuilderImpl.LOCALIZER.getString("field.tax_rate_type"));
        BillyValidator.mandatory(e.getCode(), ADTaxBuilderImpl.LOCALIZER.getString("field.tax_code"));
        BillyValidator.mandatory(e.getDescription(), ADTaxBuilderImpl.LOCALIZER.getString("field.tax_description"));

        final List<JPAADTaxEntity> taxes = ((DAOADTax) this.daoTax).getTaxes(e.getContext(), e.getCode(), e.getValidFrom(), e.getValidTo());
        if (!taxes.isEmpty()) {
            throw new BillyValidationException();
        }
        super.validateInstance();
    }
}
