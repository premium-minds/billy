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
import com.premiumminds.billy.core.services.builders.impl.ProductBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.spain.persistence.dao.DAOESProduct;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.persistence.entities.ESProductEntity;
import com.premiumminds.billy.spain.services.builders.ESProductBuilder;
import com.premiumminds.billy.spain.services.entities.ESProduct;

public class ESProductBuilderImpl<TBuilder extends ESProductBuilderImpl<TBuilder, TProduct>, TProduct extends ESProduct>
        extends ProductBuilderImpl<TBuilder, TProduct> implements ESProductBuilder<TBuilder, TProduct> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public ESProductBuilderImpl(DAOESProduct daoESProduct, DAOESTax daoESTax) {
        super(daoESProduct, daoESTax);
    }

    @Override
    protected ESProductEntity getTypeInstance() {
        return (ESProductEntity) super.getTypeInstance();
    }

    @Override
    public TBuilder setNumberCode(String code) {
        BillyValidator.mandatory(code, ESProductBuilderImpl.LOCALIZER.getString("field.product_number_code"));
        this.getTypeInstance().setNumberCode(code);
        return this.getBuilder();
    }

    @Override
    public TBuilder setUnitOfMeasure(String unit) {
        BillyValidator.mandatory(unit, ESProductBuilderImpl.LOCALIZER.getString("field.unit_of_measure"));
        this.getTypeInstance().setUnitOfMeasure(unit);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        super.validateInstance();
        ESProduct p = this.getTypeInstance();
        BillyValidator.mandatory(p.getNumberCode(),
                ESProductBuilderImpl.LOCALIZER.getString("field.product_number_code"));
    }
}
