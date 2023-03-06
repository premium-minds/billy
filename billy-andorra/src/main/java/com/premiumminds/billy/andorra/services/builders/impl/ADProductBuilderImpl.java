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

import com.premiumminds.billy.andorra.persistence.entities.ADProductEntity;
import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.ProductBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADProduct;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;
import com.premiumminds.billy.andorra.services.builders.ADProductBuilder;
import com.premiumminds.billy.andorra.services.entities.ADProduct;

public class ADProductBuilderImpl<TBuilder extends ADProductBuilderImpl<TBuilder, TProduct>, TProduct extends ADProduct>
        extends ProductBuilderImpl<TBuilder, TProduct> implements ADProductBuilder<TBuilder, TProduct>
{

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public ADProductBuilderImpl(DAOADProduct daoADProduct, DAOADTax daoADTax) {
        super(daoADProduct, daoADTax);
    }

    @Override
    protected ADProductEntity getTypeInstance() {
        return (ADProductEntity) super.getTypeInstance();
    }

    @Override
    public TBuilder setNumberCode(String code) {
        BillyValidator.mandatory(code, ADProductBuilderImpl.LOCALIZER.getString("field.product_number_code"));
        this.getTypeInstance().setNumberCode(code);
        return this.getBuilder();
    }

    @Override
    public TBuilder setUnitOfMeasure(String unit) {
        BillyValidator.mandatory(unit, ADProductBuilderImpl.LOCALIZER.getString("field.unit_of_measure"));
        this.getTypeInstance().setUnitOfMeasure(unit);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        super.validateInstance();
        ADProduct p = this.getTypeInstance();
        BillyValidator.mandatory(p.getNumberCode(),
								 ADProductBuilderImpl.LOCALIZER.getString("field.product_number_code"));
    }
}
