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
import com.premiumminds.billy.core.services.builders.impl.ProductBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.france.persistence.dao.DAOFRProduct;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.persistence.entities.FRProductEntity;
import com.premiumminds.billy.france.services.builders.FRProductBuilder;
import com.premiumminds.billy.france.services.entities.FRProduct;

public class FRProductBuilderImpl<TBuilder extends FRProductBuilderImpl<TBuilder, TProduct>, TProduct extends FRProduct>
        extends ProductBuilderImpl<TBuilder, TProduct> implements FRProductBuilder<TBuilder, TProduct> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public FRProductBuilderImpl(DAOFRProduct daoFRProduct, DAOFRTax daoFRTax) {
        super(daoFRProduct, daoFRTax);
    }

    @Override
    protected FRProductEntity getTypeInstance() {
        return (FRProductEntity) super.getTypeInstance();
    }

    @Override
    public TBuilder setNumberCode(String code) {
        BillyValidator.mandatory(code, FRProductBuilderImpl.LOCALIZER.getString("field.product_number_code"));
        this.getTypeInstance().setNumberCode(code);
        return this.getBuilder();
    }

    @Override
    public TBuilder setUnitOfMeasure(String unit) {
        BillyValidator.mandatory(unit, FRProductBuilderImpl.LOCALIZER.getString("field.unit_of_measure"));
        this.getTypeInstance().setUnitOfMeasure(unit);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        super.validateInstance();
        FRProduct p = this.getTypeInstance();
        BillyValidator.mandatory(p.getNumberCode(),
                FRProductBuilderImpl.LOCALIZER.getString("field.product_number_code"));
    }
}
