/*
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
import com.premiumminds.billy.core.exceptions.InvalidTaxIdentificationNumberException;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.builders.impl.CustomerBuilderImpl;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.spain.persistence.dao.DAOESContact;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.entities.ESCustomerEntity;
import com.premiumminds.billy.spain.services.builders.ESCustomerBuilder;
import com.premiumminds.billy.spain.services.entities.ESCustomer;
import com.premiumminds.billy.spain.util.ESFinancialValidator;

public class ESCustomerBuilderImpl<TBuilder extends ESCustomerBuilderImpl<TBuilder, TCustomer>, TCustomer extends ESCustomer>
        extends CustomerBuilderImpl<TBuilder, TCustomer> implements ESCustomerBuilder<TBuilder, TCustomer> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    protected ESCustomerBuilderImpl(DAOESCustomer daoESCustomer, DAOESContact daoESContact) {
        super(daoESCustomer, daoESContact);
    }

    @Override
    @NotOnUpdate
    public TBuilder setTaxRegistrationNumber(String number, String countryCode)
            throws InvalidTaxIdentificationNumberException {
        BillyValidator.notBlank(number, CustomerBuilderImpl.LOCALIZER.getString("field.customer_tax_number"));

        ESFinancialValidator validator = new ESFinancialValidator(number);

        if (ESFinancialValidator.ES_COUNTRY_CODE.equals(countryCode) && !validator.isValid()) {
            throw new InvalidTaxIdentificationNumberException();
        }
        this.getTypeInstance().setTaxRegistrationNumber(number);
		this.getTypeInstance().setTaxRegistrationNumberCountry(countryCode);
        return this.getBuilder();
    }

    @Override
    protected ESCustomerEntity getTypeInstance() {
        return (ESCustomerEntity) super.getTypeInstance();
    }

    @Override
    public <T extends Address> TBuilder setBillingAddress(Builder<T> addressBuilder) {
        BillyValidator.notNull(addressBuilder,
                ESCustomerBuilderImpl.LOCALIZER.getString("field.customer_billing_address"));
        this.getTypeInstance().setBillingAddress((AddressEntity) addressBuilder.build());
        return this.getBuilder();
    }

    @Override
    public TBuilder setHasSelfBillingAgreement(boolean selfBiling) {
        BillyValidator.notNull(selfBiling,
                ESCustomerBuilderImpl.LOCALIZER.getString("field.customer_self_billing_agreement"));
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
        ESCustomerEntity c = this.getTypeInstance();
        BillyValidator.mandatory(c.getName(), ESCustomerBuilderImpl.LOCALIZER.getString("field.customer_name"));
        BillyValidator.mandatory(c.getTaxRegistrationNumber(),
                ESCustomerBuilderImpl.LOCALIZER.getString("field.customer_tax_number"));
        BillyValidator.<Object>mandatory(c.getMainAddress(),
                ESCustomerBuilderImpl.LOCALIZER.getString("field.customer_main_address"));
        BillyValidator.<Object>mandatory(c.getBillingAddress(),
                ESCustomerBuilderImpl.LOCALIZER.getString("field.customer_billing_address"));
        BillyValidator.mandatory(c.hasSelfBillingAgreement(),
                ESCustomerBuilderImpl.LOCALIZER.getString("field.customer_self_billing_agreement"));
        BillyValidator.notEmpty(c.getAddresses(), ESCustomerBuilderImpl.LOCALIZER.getString("field.customer_address"));
    }
}
