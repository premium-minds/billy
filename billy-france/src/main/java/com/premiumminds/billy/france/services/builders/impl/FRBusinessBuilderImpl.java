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
import com.premiumminds.billy.france.persistence.dao.DAOFRBusiness;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.entities.FRBusinessEntity;
import com.premiumminds.billy.france.services.builders.FRBusinessBuilder;
import com.premiumminds.billy.france.services.entities.FRBusiness;
import com.premiumminds.billy.france.util.FRFinancialValidator;

public class FRBusinessBuilderImpl<TBuilder extends FRBusinessBuilderImpl<TBuilder, TBusiness>, TBusiness extends FRBusiness>
        extends BusinessBuilderImpl<TBuilder, TBusiness> implements FRBusinessBuilder<TBuilder, TBusiness> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public FRBusinessBuilderImpl(DAOFRBusiness daoBusiness, DAOFRRegionContext daoContext) {
        super(daoBusiness, daoContext);
    }

    @Override
    @NotOnUpdate
    public TBuilder setFinancialID(String id, String countryCode) throws InvalidTaxIdentificationNumberException {
        BillyValidator.notBlank(id, BusinessBuilderImpl.LOCALIZER.getString("field.financial_id"));
        FRFinancialValidator validator = new FRFinancialValidator(id);

        if (FRFinancialValidator.FR_COUNTRY_CODE.equals(countryCode) && !validator.isValid()) {
            throw new InvalidTaxIdentificationNumberException();
        }
        this.getTypeInstance().setFinancialID(id);
        return this.getBuilder();
    }

    @Override
    protected FRBusinessEntity getTypeInstance() {
        return (FRBusinessEntity) super.getTypeInstance();
    }

    @Override
    public TBuilder setCommercialName(String name) {
        BillyValidator.notBlank(name, FRBusinessBuilderImpl.LOCALIZER.getString("field.commercial_name"));
        this.getTypeInstance().setCommercialName(name);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        BusinessEntity b = this.getTypeInstance();

        BillyValidator.mandatory(b.getFinancialID(), FRBusinessBuilderImpl.LOCALIZER.getString("field.financial_id"));
        BillyValidator.mandatory(b.getName(), FRBusinessBuilderImpl.LOCALIZER.getString("field.business_name"));
        BillyValidator.mandatory(b.getCommercialName(),
                FRBusinessBuilderImpl.LOCALIZER.getString("field.commercial_name"));
        BillyValidator.mandatory(b.getAddress(), FRBusinessBuilderImpl.LOCALIZER.getString("field.business_address"));

        Pattern pattern;
        pattern = Pattern.compile("[0-9]{5}");

        Matcher matcher = pattern.matcher(b.getAddress().getPostalCode());
        if (!matcher.find()) {
            throw new BillyValidationException();
        }
    }
}
