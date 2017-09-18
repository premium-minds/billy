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
import com.premiumminds.billy.core.exceptions.InvalidTaxIdentificationNumberException;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.builders.impl.CustomerBuilderImpl;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.france.persistence.dao.DAOFRContact;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.entities.FRCustomerEntity;
import com.premiumminds.billy.france.services.builders.FRCustomerBuilder;
import com.premiumminds.billy.france.services.entities.FRCustomer;
import com.premiumminds.billy.france.util.FRFinancialValidator;

public class FRCustomerBuilderImpl<TBuilder extends FRCustomerBuilderImpl<TBuilder, TCustomer>, TCustomer extends FRCustomer>
        extends CustomerBuilderImpl<TBuilder, TCustomer> implements FRCustomerBuilder<TBuilder, TCustomer> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    protected FRCustomerBuilderImpl(DAOFRCustomer daoFRCustomer, DAOFRContact daoFRContact) {
        super(daoFRCustomer, daoFRContact);
    }

    @Override
    @NotOnUpdate
    public TBuilder setTaxRegistrationNumber(String number, String countryCode)
            throws InvalidTaxIdentificationNumberException {
        BillyValidator.notBlank(number, CustomerBuilderImpl.LOCALIZER.getString("field.customer_tax_number"));

        FRFinancialValidator validator = new FRFinancialValidator(number);

        if (FRFinancialValidator.FR_COUNTRY_CODE.equals(countryCode) && !validator.isValid()) {
            throw new InvalidTaxIdentificationNumberException();
        }
        this.getTypeInstance().setTaxRegistrationNumber(number);
        return this.getBuilder();
    }

    @Override
    protected FRCustomerEntity getTypeInstance() {
        return (FRCustomerEntity) super.getTypeInstance();
    }

    @Override
    public <T extends Address> TBuilder setBillingAddress(Builder<T> addressBuilder) {
        BillyValidator.notNull(addressBuilder,
                FRCustomerBuilderImpl.LOCALIZER.getString("field.customer_billing_address"));
        this.getTypeInstance().setBillingAddress((AddressEntity) addressBuilder.build());
        return this.getBuilder();
    }

    @Override
    public TBuilder setHasSelfBillingAgreement(boolean selfBiling) {
        BillyValidator.notNull(selfBiling,
                FRCustomerBuilderImpl.LOCALIZER.getString("field.customer_self_billing_agreement"));
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
        FRCustomerEntity c = this.getTypeInstance();
        BillyValidator.mandatory(c.getName(), FRCustomerBuilderImpl.LOCALIZER.getString("field.customer_name"));
        BillyValidator.mandatory(c.getTaxRegistrationNumber(),
                FRCustomerBuilderImpl.LOCALIZER.getString("field.customer_tax_number"));
        BillyValidator.mandatory(c.getMainAddress(),
                FRCustomerBuilderImpl.LOCALIZER.getString("field.customer_main_address"));
        BillyValidator.mandatory(c.getBillingAddress(),
                FRCustomerBuilderImpl.LOCALIZER.getString("field.customer_billing_address"));
        BillyValidator.mandatory(c.hasSelfBillingAgreement(),
                FRCustomerBuilderImpl.LOCALIZER.getString("field.customer_self_billing_agreement"));
        BillyValidator.notEmpty(c.getAddresses(), FRCustomerBuilderImpl.LOCALIZER.getString("field.customer_address"));
    }
}
