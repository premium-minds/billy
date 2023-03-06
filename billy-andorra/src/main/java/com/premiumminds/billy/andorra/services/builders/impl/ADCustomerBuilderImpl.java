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

import com.premiumminds.billy.andorra.persistence.entities.ADCustomerEntity;
import com.premiumminds.billy.andorra.util.ADFinancialValidator;
import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.exceptions.InvalidTaxIdentificationNumberException;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.builders.impl.CustomerBuilderImpl;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.andorra.persistence.dao.DAOADContact;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.services.builders.ADCustomerBuilder;
import com.premiumminds.billy.andorra.services.entities.ADCustomer;

public class ADCustomerBuilderImpl<TBuilder extends ADCustomerBuilderImpl<TBuilder, TCustomer>, TCustomer extends ADCustomer>
        extends CustomerBuilderImpl<TBuilder, TCustomer> implements ADCustomerBuilder<TBuilder, TCustomer>
{

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    protected ADCustomerBuilderImpl(DAOADCustomer daoADCustomer, DAOADContact daoADContact) {
        super(daoADCustomer, daoADContact);
    }

    @Override
    @NotOnUpdate
    public TBuilder setTaxRegistrationNumber(String number, String countryCode)
            throws InvalidTaxIdentificationNumberException {
        BillyValidator.notBlank(number, CustomerBuilderImpl.LOCALIZER.getString("field.customer_tax_number"));
        BillyValidator.notBlank(number, CustomerBuilderImpl.LOCALIZER.getString("field.customer_tax_number_iso_country_code"));

        ADFinancialValidator validator = new ADFinancialValidator(number);

        if (ADFinancialValidator.AD_COUNTRY_CODE.equals(countryCode) && !validator.isValid()) {
            throw new InvalidTaxIdentificationNumberException();
        }
        this.getTypeInstance().setTaxRegistrationNumber(number);
        this.getTypeInstance().setTaxRegistrationNumberISOCountryCode(countryCode);
        return this.getBuilder();
    }

    @Override
    protected ADCustomerEntity getTypeInstance() {
        return (ADCustomerEntity) super.getTypeInstance();
    }

    @Override
    public <T extends Address> TBuilder setBillingAddress(Builder<T> addressBuilder) {
        BillyValidator.notNull(addressBuilder,
							   ADCustomerBuilderImpl.LOCALIZER.getString("field.customer_billing_address"));
        this.getTypeInstance().setBillingAddress((AddressEntity) addressBuilder.build());
        return this.getBuilder();
    }

    @Override
    public TBuilder setHasSelfBillingAgreement(boolean selfBiling) {
        BillyValidator.notNull(selfBiling,
							   ADCustomerBuilderImpl.LOCALIZER.getString("field.customer_self_billing_agreement"));
        this.getTypeInstance().setHasSelfBillingAgreement(selfBiling);
        return this.getBuilder();
    }

    @Override
    public TBuilder setReferralName(String referralName) {
        this.getTypeInstance().setReferralName(referralName);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        ADCustomerEntity c = this.getTypeInstance();
        BillyValidator.mandatory(c.getName(), ADCustomerBuilderImpl.LOCALIZER.getString("field.customer_name"));
        BillyValidator.mandatory(c.getTaxRegistrationNumber(),
								 ADCustomerBuilderImpl.LOCALIZER.getString("field.customer_tax_number"));
        BillyValidator.<Object>mandatory(c.getMainAddress(),
										 ADCustomerBuilderImpl.LOCALIZER.getString("field.customer_main_address"));
        BillyValidator.<Object>mandatory(c.getBillingAddress(),
										 ADCustomerBuilderImpl.LOCALIZER.getString("field.customer_billing_address"));
        BillyValidator.mandatory(c.hasSelfBillingAgreement(),
								 ADCustomerBuilderImpl.LOCALIZER.getString("field.customer_self_billing_agreement"));
        BillyValidator.notEmpty(c.getAddresses(), ADCustomerBuilderImpl.LOCALIZER.getString("field.customer_address"));
    }
}
