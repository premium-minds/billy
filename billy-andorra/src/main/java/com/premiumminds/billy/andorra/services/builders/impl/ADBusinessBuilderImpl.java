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

import com.premiumminds.billy.andorra.persistence.entities.ADBusinessEntity;
import com.premiumminds.billy.andorra.services.entities.ADBusiness;
import com.premiumminds.billy.andorra.util.ADFinancialValidator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.exceptions.InvalidTaxIdentificationNumberException;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.services.builders.impl.BusinessBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.andorra.persistence.dao.DAOADBusiness;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.services.builders.ADBusinessBuilder;

public class ADBusinessBuilderImpl<TBuilder extends ADBusinessBuilderImpl<TBuilder, TBusiness>, TBusiness extends ADBusiness>
        extends BusinessBuilderImpl<TBuilder, TBusiness> implements ADBusinessBuilder<TBuilder, TBusiness>
{

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public ADBusinessBuilderImpl(DAOADBusiness daoBusiness, DAOADRegionContext daoContext) {
        super(daoBusiness, daoContext);
    }

    @Override
    @NotOnUpdate
    public TBuilder setFinancialID(String id, String isoCountryCode) throws InvalidTaxIdentificationNumberException {
        BillyValidator.notBlank(id, BusinessBuilderImpl.LOCALIZER.getString("field.financial_id"));
        ADFinancialValidator validator = new ADFinancialValidator(id);

        BillyValidator.notBlank(isoCountryCode, BusinessBuilderImpl.LOCALIZER.getString("field.financial_id_iso_country_code"));

        if (ADFinancialValidator.AD_COUNTRY_CODE.equals(isoCountryCode) && !validator.isValid()) {
            throw new InvalidTaxIdentificationNumberException();
        }
        this.getTypeInstance().setFinancialID(id);
        this.getTypeInstance().setFinancialIdISOCountryCode(isoCountryCode);
        return this.getBuilder();
    }

    @Override
    protected ADBusinessEntity getTypeInstance() {
        return (ADBusinessEntity) super.getTypeInstance();
    }

    @Override
    public TBuilder setCommercialName(String name) {
        BillyValidator.notBlank(name, ADBusinessBuilderImpl.LOCALIZER.getString("field.commercial_name"));
        this.getTypeInstance().setCommercialName(name);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        BusinessEntity b = this.getTypeInstance();

        BillyValidator.mandatory(b.getFinancialID(), ADBusinessBuilderImpl.LOCALIZER.getString("field.financial_id"));
        BillyValidator.mandatory(b.getName(), ADBusinessBuilderImpl.LOCALIZER.getString("field.business_name"));
        BillyValidator.mandatory(b.getCommercialName(),
                                 ADBusinessBuilderImpl.LOCALIZER.getString("field.commercial_name"));
        BillyValidator.<Object>mandatory(b.getAddress(), ADBusinessBuilderImpl.LOCALIZER.getString("field.business_address"));

        Pattern pattern;
        pattern = Pattern.compile("[0-9]{5}");

        Matcher matcher = pattern.matcher(b.getAddress().getPostalCode());
        if (!matcher.find()) {
            throw new BillyValidationException();
        }
    }
}
