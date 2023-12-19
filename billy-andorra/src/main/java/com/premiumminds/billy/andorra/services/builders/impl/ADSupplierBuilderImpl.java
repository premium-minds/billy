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

import com.premiumminds.billy.andorra.persistence.entities.ADSupplierEntity;
import com.premiumminds.billy.andorra.services.entities.ADSupplier;
import com.premiumminds.billy.andorra.util.ADFinancialValidator;
import jakarta.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.exceptions.InvalidTaxIdentificationNumberException;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.builders.impl.SupplierBuilderImpl;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSupplier;
import com.premiumminds.billy.andorra.services.builders.ADSupplierBuilder;

public class ADSupplierBuilderImpl<TBuilder extends ADSupplierBuilderImpl<TBuilder, TSupplier>, TSupplier extends ADSupplier>
        extends SupplierBuilderImpl<TBuilder, TSupplier> implements ADSupplierBuilder<TBuilder, TSupplier>
{

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public ADSupplierBuilderImpl(DAOADSupplier daoADSupplier) {
        super(daoADSupplier);
    }

    @Override
    protected ADSupplierEntity getTypeInstance() {
        return (ADSupplierEntity) super.getTypeInstance();
    }

    @Override
    @NotOnUpdate
    public TBuilder setTaxRegistrationNumber(String number, String countryCode)
            throws InvalidTaxIdentificationNumberException {
        BillyValidator.mandatory(number, ADSupplierBuilderImpl.LOCALIZER.getString("field.supplier_tax_number"));
        BillyValidator.mandatory(number, ADSupplierBuilderImpl.LOCALIZER.getString("field.supplier_tax_number_iso_country_code"));

        ADFinancialValidator validator = new ADFinancialValidator(number);

        if (ADFinancialValidator.AD_COUNTRY_CODE.equals(countryCode) && !validator.isValid()) {
            throw new InvalidTaxIdentificationNumberException();
        }
        this.getTypeInstance().setTaxRegistrationNumber(number);
        this.getTypeInstance().setTaxRegistrationNumberISOCountryCode(countryCode);
        return this.getBuilder();
    }

    @Override
    public <T extends Address> TBuilder setBillingAddress(Builder<T> addressBuilder) {
        BillyValidator.mandatory(addressBuilder,
                                 ADSupplierBuilderImpl.LOCALIZER.getString("field.supplier_billing_address"));
        this.getTypeInstance().setBillingAddress((AddressEntity) addressBuilder.build());
        return this.getBuilder();
    }

    @Override
    public TBuilder setSelfBillingAgreement(boolean selfBilling) {
        BillyValidator.mandatory(selfBilling,
                                 ADSupplierBuilderImpl.LOCALIZER.getString("field.supplier_self_billing_agreement"));
        this.getTypeInstance().setSelfBillingAgreement(selfBilling);
        return this.getBuilder();
    }

    @Override
    public TBuilder setReferralName(String referralName) {
        this.getTypeInstance().setReferralName(referralName);
        return this.getBuilder();
    }

    @Deprecated
    @NotImplemented
    public TBuilder setAccountID(String accountID) {
        return null;
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        super.validateInstance();
        ADSupplier s = this.getTypeInstance();
        BillyValidator.mandatory(s.getTaxRegistrationNumber(),
                                 ADSupplierBuilderImpl.LOCALIZER.getString("field.supplier_tax_number"));
        BillyValidator.mandatory(s.getBillingAddress(),
                                 ADSupplierBuilderImpl.LOCALIZER.getString("field.supplier_billing_address"));
        BillyValidator.mandatory(s.hasSelfBillingAgreement(),
                                 ADSupplierBuilderImpl.LOCALIZER.getString("field.supplier_self_billing_agreement"));
    }
}
