/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

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
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTSupplierEntity;
import com.premiumminds.billy.portugal.services.builders.PTSupplierBuilder;
import com.premiumminds.billy.portugal.services.entities.PTSupplier;
import com.premiumminds.billy.portugal.util.PTFinancialValidator;

public class PTSupplierBuilderImpl<TBuilder extends PTSupplierBuilderImpl<TBuilder, TSupplier>, TSupplier extends PTSupplier>
        extends SupplierBuilderImpl<TBuilder, TSupplier> implements PTSupplierBuilder<TBuilder, TSupplier> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public PTSupplierBuilderImpl(DAOPTSupplier daoPTSupplier) {
        super(daoPTSupplier);
    }

    @Override
    protected PTSupplierEntity getTypeInstance() {
        return (PTSupplierEntity) super.getTypeInstance();
    }

    @Override
    @NotOnUpdate
    public TBuilder setTaxRegistrationNumber(String number, String countryCode)
            throws InvalidTaxIdentificationNumberException {
        // The <generic> specs below are necessary because type inference fails here for unknown reasons
        // If removed, these lines will fail in runtime with a linkage error (ClassCastException)
        BillyValidator.<String>mandatory(number,
                PTSupplierBuilderImpl.LOCALIZER.getString("field.supplier_tax_number"));

        PTFinancialValidator validator = new PTFinancialValidator(number);

        if (PTFinancialValidator.PT_COUNTRY_CODE.equals(countryCode) && !validator.isValid()) {
            throw new InvalidTaxIdentificationNumberException();
        }
        this.getTypeInstance().setTaxRegistrationNumber(number);
        return this.getBuilder();
    }

    @Override
    public <T extends Address> TBuilder setBillingAddress(Builder<T> addressBuilder) {
        // The <generic> specs below are necessary because type inference fails here for unknown reasons
        // If removed, these lines will fail in runtime with a linkage error (ClassCastException)
        BillyValidator.<Builder<?>>mandatory(addressBuilder,
                PTSupplierBuilderImpl.LOCALIZER.getString("field.supplier_billing_address"));
        this.getTypeInstance().setBillingAddress((AddressEntity) addressBuilder.build());
        return this.getBuilder();
    }

    @Override
    public TBuilder setSelfBillingAgreement(boolean selfBilling) {
        // The <generic> specs below are necessary because type inference fails here for unknown reasons
        // If removed, these lines will fail in runtime with a linkage error (ClassCastException)
        BillyValidator.<Boolean>mandatory(selfBilling,
                PTSupplierBuilderImpl.LOCALIZER.getString("field.supplier_self_billing_agreement"));
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
        PTSupplier s = this.getTypeInstance();
        // The <generic> specs below are necessary because type inference fails here for unknown reasons
        // If removed, these lines will fail in runtime with a linkage error (ClassCastException)
        BillyValidator.<String>mandatory(s.getTaxRegistrationNumber(),
                PTSupplierBuilderImpl.LOCALIZER.getString("field.supplier_tax_number"));
        BillyValidator.<Address>mandatory(s.getBillingAddress(),
                PTSupplierBuilderImpl.LOCALIZER.getString("field.supplier_billing_address"));
        BillyValidator.<Boolean>mandatory(s.hasSelfBillingAgreement(),
                PTSupplierBuilderImpl.LOCALIZER.getString("field.supplier_self_billing_agreement"));
    }
}
