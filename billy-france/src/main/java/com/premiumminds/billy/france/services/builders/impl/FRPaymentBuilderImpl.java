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

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.ValidationException;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.PaymentBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.france.persistence.dao.DAOFRPayment;
import com.premiumminds.billy.france.persistence.entities.FRPaymentEntity;
import com.premiumminds.billy.france.services.builders.FRPaymentBuilder;
import com.premiumminds.billy.france.services.entities.FRPayment;

public class FRPaymentBuilderImpl<TBuilder extends FRPaymentBuilderImpl<TBuilder, TPayment>, TPayment extends FRPayment>
        extends PaymentBuilderImpl<TBuilder, TPayment> implements FRPaymentBuilder<TBuilder, TPayment> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/france/i18n/FieldNames");

    @Inject
    public FRPaymentBuilderImpl(DAOFRPayment daoFRPayment) {
        super(daoFRPayment);
    }

    @Override
    protected FRPaymentEntity getTypeInstance() {
        return (FRPaymentEntity) super.getTypeInstance();
    }

    @Override
    public TBuilder setPaymentAmount(BigDecimal amount) {
        BillyValidator.notNull(amount, FRPaymentBuilderImpl.LOCALIZER.getString("field.payment_amount"));
        this.getTypeInstance().setPaymentAmount(amount);
        return this.getBuilder();
    }

    @Override
    public TBuilder setPaymentMethod(Enum<?> method) {
        BillyValidator.notNull(method, FRPaymentBuilderImpl.LOCALIZER.getString("field.payment_method"));
        this.getTypeInstance().setPaymentMethod(method);
        return this.getBuilder();
    }

    @Override
    public TBuilder setPaymentDate(Date date) {
        BillyValidator.notNull(date, FRPaymentBuilderImpl.LOCALIZER.getString("field.payment_date"));
        this.getTypeInstance().setPaymentDate(date);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException, ValidationException {
        super.validateInstance();
        FRPaymentEntity p = this.getTypeInstance();
        BillyValidator.mandatory(p.getPaymentAmount(),
                FRPaymentBuilderImpl.LOCALIZER.getString("field.payment_amount"));
        BillyValidator.mandatory(p.getPaymentMethod(),
                FRPaymentBuilderImpl.LOCALIZER.getString("field.payment_method"));
        BillyValidator.mandatory(p.getPaymentDate(), FRPaymentBuilderImpl.LOCALIZER.getString("field.payment_date"));
    }

}
