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
import com.premiumminds.billy.core.services.builders.impl.TaxBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.persistence.entities.FRRegionContextEntity;
import com.premiumminds.billy.france.persistence.entities.FRTaxEntity;
import com.premiumminds.billy.france.services.builders.FRTaxBuilder;
import com.premiumminds.billy.france.services.entities.FRTax;

public class FRTaxBuilderImpl<TBuilder extends FRTaxBuilderImpl<TBuilder, TTax>, TTax extends FRTax>
        extends TaxBuilderImpl<TBuilder, TTax> implements FRTaxBuilder<TBuilder, TTax> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public FRTaxBuilderImpl(DAOFRTax daoFRTax, DAOFRRegionContext daoFRContext) {
        super(daoFRTax, daoFRContext);
    }

    @Override
    protected FRTaxEntity getTypeInstance() {
        return (FRTaxEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        FRTaxEntity e = this.getTypeInstance();

        BillyValidator.mandatory(e.getContext(), FRTaxBuilderImpl.LOCALIZER.getString("field.tax_context"));
        BillyValidator.mandatory(e.getTaxRateType(), FRTaxBuilderImpl.LOCALIZER.getString("field.tax_rate_type"));
        BillyValidator.mandatory(e.getCode(), FRTaxBuilderImpl.LOCALIZER.getString("field.tax_code"));
        BillyValidator.mandatory(e.getDescription(), FRTaxBuilderImpl.LOCALIZER.getString("field.tax_description"));

        if (!((DAOFRTax) this.daoTax).getTaxes((FRRegionContextEntity) e.getContext(), e.getValidFrom(), e.getValidTo())
                .isEmpty()) {
            throw new BillyValidationException();
        }
        super.validateInstance();
    }

}
