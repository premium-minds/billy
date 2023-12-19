/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.exceptions.InvalidTaxIdentificationNumberException;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.services.builders.impl.BusinessBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.services.builders.PTBusinessBuilder;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import com.premiumminds.billy.portugal.util.PTFinancialValidator;

import jakarta.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PTBusinessBuilderImpl<TBuilder extends PTBusinessBuilderImpl<TBuilder, TBusiness>, TBusiness extends PTBusiness>
        extends BusinessBuilderImpl<TBuilder, TBusiness> implements PTBusinessBuilder<TBuilder, TBusiness> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public PTBusinessBuilderImpl(DAOPTBusiness daoBusiness, DAOPTRegionContext daoContext) {
        super(daoBusiness, daoContext);
    }

    @Override
    @NotOnUpdate
    public TBuilder setFinancialID(String id, String isoCountryCode) throws InvalidTaxIdentificationNumberException {
        BillyValidator.notBlank(id, BusinessBuilderImpl.LOCALIZER.getString("field.financial_id"));
        PTFinancialValidator validator = new PTFinancialValidator(id);

        BillyValidator.notBlank(isoCountryCode, BusinessBuilderImpl.LOCALIZER.getString("field.financial_id_iso_country_code"));

        if (PTFinancialValidator.PT_COUNTRY_CODE.equals(isoCountryCode) && !validator.isValid()) {
            throw new InvalidTaxIdentificationNumberException();
        }
        this.getTypeInstance().setFinancialID(id);
        this.getTypeInstance().setFinancialIdISOCountryCode(isoCountryCode);
        return this.getBuilder();
    }

    @Override
    protected PTBusinessEntity getTypeInstance() {
        return (PTBusinessEntity) super.getTypeInstance();
    }

    @Override
    public TBuilder setCommercialName(String name) {
        BillyValidator.notBlank(name, PTBusinessBuilderImpl.LOCALIZER.getString("field.commercial_name"));
        this.getTypeInstance().setCommercialName(name);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        BusinessEntity b = this.getTypeInstance();

        BillyValidator.mandatory(b.getFinancialID(), PTBusinessBuilderImpl.LOCALIZER.getString("field.financial_id"));
        BillyValidator.mandatory(b.getName(), PTBusinessBuilderImpl.LOCALIZER.getString("field.business_name"));
        BillyValidator.mandatory(b.getCommercialName(),
                PTBusinessBuilderImpl.LOCALIZER.getString("field.commercial_name"));
        BillyValidator.<Object>mandatory(b.getAddress(), PTBusinessBuilderImpl.LOCALIZER.getString("field.business_address"));
        BillyValidator.mandatory(b.getTimezone(), BusinessBuilderImpl.LOCALIZER.getString("field.timezone"));

        Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{3}");

        Matcher matcher = pattern.matcher(b.getAddress().getPostalCode());
        if (!matcher.find()) {
            throw new BillyValidationException();
        }
    }
}
