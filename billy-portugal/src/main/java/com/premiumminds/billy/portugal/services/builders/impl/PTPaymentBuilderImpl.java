/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.ValidationException;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.PaymentBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTPayment;
import com.premiumminds.billy.portugal.persistence.entities.PTPaymentEntity;
import com.premiumminds.billy.portugal.services.builders.PTPaymentBuilder;
import com.premiumminds.billy.portugal.services.entities.PTPayment;

public class PTPaymentBuilderImpl<TBuilder extends PTPaymentBuilderImpl<TBuilder, TPayment>, TPayment extends PTPayment>
        extends PaymentBuilderImpl<TBuilder, TPayment> implements PTPaymentBuilder<TBuilder, TPayment> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/portugal/i18n/FieldNames");

    @Inject
    public PTPaymentBuilderImpl(DAOPTPayment daoPTPayment) {
        super(daoPTPayment);
    }

    @Override
    protected PTPaymentEntity getTypeInstance() {
        return (PTPaymentEntity) super.getTypeInstance();
    }

    @Override
    public TBuilder setPaymentAmount(BigDecimal amount) {
        BillyValidator.notNull(amount, PTPaymentBuilderImpl.LOCALIZER.getString("field.payment_amount"));
        this.getTypeInstance().setPaymentAmount(amount);
        return this.getBuilder();
    }

    @Override
    public TBuilder setPaymentMethod(Enum<?> method) {
        BillyValidator.notNull(method, PTPaymentBuilderImpl.LOCALIZER.getString("field.payment_method"));
        this.getTypeInstance().setPaymentMethod(method);
        return this.getBuilder();
    }

    @Override
    public TBuilder setPaymentDate(Date date) {
        BillyValidator.notNull(date, PTPaymentBuilderImpl.LOCALIZER.getString("field.payment_date"));
        this.getTypeInstance().setPaymentDate(date);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException, ValidationException {
        super.validateInstance();
        PTPaymentEntity p = this.getTypeInstance();
        BillyValidator.mandatory(p.getPaymentAmount(),
                PTPaymentBuilderImpl.LOCALIZER.getString("field.payment_amount"));
        BillyValidator.mandatory(p.getPaymentMethod(),
                PTPaymentBuilderImpl.LOCALIZER.getString("field.payment_method"));
        BillyValidator.mandatory(p.getPaymentDate(), PTPaymentBuilderImpl.LOCALIZER.getString("field.payment_date"));
    }

}
