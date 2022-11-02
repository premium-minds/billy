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
import com.premiumminds.billy.core.services.builders.impl.SupplierBuilderImpl;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.spain.persistence.dao.DAOESSupplier;
import com.premiumminds.billy.spain.persistence.entities.ESSupplierEntity;
import com.premiumminds.billy.spain.services.builders.ESSupplierBuilder;
import com.premiumminds.billy.spain.services.entities.ESSupplier;
import com.premiumminds.billy.spain.util.ESFinancialValidator;

public class ESSupplierBuilderImpl<TBuilder extends ESSupplierBuilderImpl<TBuilder, TSupplier>, TSupplier extends ESSupplier>
        extends SupplierBuilderImpl<TBuilder, TSupplier> implements ESSupplierBuilder<TBuilder, TSupplier> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public ESSupplierBuilderImpl(DAOESSupplier daoESSupplier) {
        super(daoESSupplier);
    }

    @Override
    protected ESSupplierEntity getTypeInstance() {
        return (ESSupplierEntity) super.getTypeInstance();
    }

    @Override
    @NotOnUpdate
    public TBuilder setTaxRegistrationNumber(String number, String countryCode)
            throws InvalidTaxIdentificationNumberException {
        BillyValidator.mandatory(number, ESSupplierBuilderImpl.LOCALIZER.getString("field.supplier_tax_number"));
        BillyValidator.mandatory(number, ESSupplierBuilderImpl.LOCALIZER.getString("field.supplier_tax_number_iso_country_code"));

        ESFinancialValidator validator = new ESFinancialValidator(number);

        if (ESFinancialValidator.ES_COUNTRY_CODE.equals(countryCode) && !validator.isValid()) {
            throw new InvalidTaxIdentificationNumberException();
        }
        this.getTypeInstance().setTaxRegistrationNumber(number);
        this.getTypeInstance().setTaxRegistrationNumberISOCountryCode(countryCode);
        return this.getBuilder();
    }

    @Override
    public <T extends Address> TBuilder setBillingAddress(Builder<T> addressBuilder) {
        BillyValidator.mandatory(addressBuilder,
                ESSupplierBuilderImpl.LOCALIZER.getString("field.supplier_billing_address"));
        this.getTypeInstance().setBillingAddress((AddressEntity) addressBuilder.build());
        return this.getBuilder();
    }

    @Override
    public TBuilder setSelfBillingAgreement(boolean selfBilling) {
        BillyValidator.mandatory(selfBilling,
                ESSupplierBuilderImpl.LOCALIZER.getString("field.supplier_self_billing_agreement"));
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
        ESSupplier s = this.getTypeInstance();
        BillyValidator.mandatory(s.getTaxRegistrationNumber(),
                ESSupplierBuilderImpl.LOCALIZER.getString("field.supplier_tax_number"));
        BillyValidator.mandatory(s.getBillingAddress(),
                ESSupplierBuilderImpl.LOCALIZER.getString("field.supplier_billing_address"));
        BillyValidator.mandatory(s.hasSelfBillingAgreement(),
                ESSupplierBuilderImpl.LOCALIZER.getString("field.supplier_self_billing_agreement"));
    }
}
