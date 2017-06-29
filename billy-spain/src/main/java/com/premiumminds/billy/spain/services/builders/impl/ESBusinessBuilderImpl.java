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
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.entities.ESBusinessEntity;
import com.premiumminds.billy.spain.services.builders.ESBusinessBuilder;
import com.premiumminds.billy.spain.services.entities.ESBusiness;
import com.premiumminds.billy.spain.util.ESFinancialValidator;

public class ESBusinessBuilderImpl<TBuilder extends ESBusinessBuilderImpl<TBuilder, TBusiness>, TBusiness extends ESBusiness>
        extends BusinessBuilderImpl<TBuilder, TBusiness> implements ESBusinessBuilder<TBuilder, TBusiness> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public ESBusinessBuilderImpl(DAOESBusiness daoBusiness, DAOESRegionContext daoContext) {
        super(daoBusiness, daoContext);
    }

    @Override
    @NotOnUpdate
    public TBuilder setFinancialID(String id, String countryCode) throws InvalidTaxIdentificationNumberException {
        BillyValidator.notBlank(id, BusinessBuilderImpl.LOCALIZER.getString("field.financial_id"));
        ESFinancialValidator validator = new ESFinancialValidator(id);

        if (ESFinancialValidator.ES_COUNTRY_CODE.equals(countryCode) && !validator.isValid()) {
            throw new InvalidTaxIdentificationNumberException();
        }
        this.getTypeInstance().setFinancialID(id);
        return this.getBuilder();
    }

    @Override
    protected ESBusinessEntity getTypeInstance() {
        return (ESBusinessEntity) super.getTypeInstance();
    }

    @Override
    public TBuilder setCommercialName(String name) {
        BillyValidator.notBlank(name, ESBusinessBuilderImpl.LOCALIZER.getString("field.commercial_name"));
        this.getTypeInstance().setCommercialName(name);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        BusinessEntity b = this.getTypeInstance();

        BillyValidator.mandatory(b.getFinancialID(), ESBusinessBuilderImpl.LOCALIZER.getString("field.financial_id"));
        BillyValidator.mandatory(b.getName(), ESBusinessBuilderImpl.LOCALIZER.getString("field.business_name"));
        BillyValidator.mandatory(b.getCommercialName(),
                ESBusinessBuilderImpl.LOCALIZER.getString("field.commercial_name"));
        BillyValidator.mandatory(b.getAddress(), ESBusinessBuilderImpl.LOCALIZER.getString("field.business_address"));

        Pattern pattern;
        pattern = Pattern.compile("[0-9]{5}");

        Matcher matcher = pattern.matcher(b.getAddress().getPostalCode());
        if (!matcher.find()) {
            throw new BillyValidationException();
        }
    }
}
