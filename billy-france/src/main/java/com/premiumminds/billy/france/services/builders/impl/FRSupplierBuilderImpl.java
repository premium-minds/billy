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
import com.premiumminds.billy.core.services.builders.impl.SupplierBuilderImpl;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.france.persistence.dao.DAOFRSupplier;
import com.premiumminds.billy.france.persistence.entities.FRSupplierEntity;
import com.premiumminds.billy.france.services.builders.FRSupplierBuilder;
import com.premiumminds.billy.france.services.entities.FRSupplier;
import com.premiumminds.billy.france.util.FRFinancialValidator;

public class FRSupplierBuilderImpl<TBuilder extends FRSupplierBuilderImpl<TBuilder, TSupplier>, TSupplier extends FRSupplier>
        extends SupplierBuilderImpl<TBuilder, TSupplier> implements FRSupplierBuilder<TBuilder, TSupplier> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public FRSupplierBuilderImpl(DAOFRSupplier daoFRSupplier) {
        super(daoFRSupplier);
    }

    @Override
    protected FRSupplierEntity getTypeInstance() {
        return (FRSupplierEntity) super.getTypeInstance();
    }

    @Override
    @NotOnUpdate
    public TBuilder setTaxRegistrationNumber(String number, String countryCode)
            throws InvalidTaxIdentificationNumberException {
        BillyValidator.mandatory(number, FRSupplierBuilderImpl.LOCALIZER.getString("field.supplier_tax_number"));

        FRFinancialValidator validator = new FRFinancialValidator(number);

        if (FRFinancialValidator.FR_COUNTRY_CODE.equals(countryCode) && !validator.isValid()) {
            throw new InvalidTaxIdentificationNumberException();
        }
        this.getTypeInstance().setTaxRegistrationNumber(number);
        return this.getBuilder();
    }

    @Override
    public <T extends Address> TBuilder setBillingAddress(Builder<T> addressBuilder) {
        BillyValidator.mandatory(addressBuilder,
                FRSupplierBuilderImpl.LOCALIZER.getString("field.supplier_billing_address"));
        this.getTypeInstance().setBillingAddress((AddressEntity) addressBuilder.build());
        return this.getBuilder();
    }

    @Override
    public TBuilder setSelfBillingAgreement(boolean selfBilling) {
        BillyValidator.mandatory(selfBilling,
                FRSupplierBuilderImpl.LOCALIZER.getString("field.supplier_self_billing_agreement"));
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
        FRSupplier s = this.getTypeInstance();
        BillyValidator.mandatory(s.getTaxRegistrationNumber(),
                FRSupplierBuilderImpl.LOCALIZER.getString("field.supplier_tax_number"));
        BillyValidator.mandatory(s.getBillingAddress(),
                FRSupplierBuilderImpl.LOCALIZER.getString("field.supplier_billing_address"));
        BillyValidator.mandatory(s.hasSelfBillingAgreement(),
                FRSupplierBuilderImpl.LOCALIZER.getString("field.supplier_self_billing_agreement"));
    }
}
