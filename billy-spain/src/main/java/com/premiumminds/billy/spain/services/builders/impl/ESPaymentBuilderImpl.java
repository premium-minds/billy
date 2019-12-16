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

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.ValidationException;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.PaymentBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.spain.persistence.dao.DAOESPayment;
import com.premiumminds.billy.spain.persistence.entities.ESPaymentEntity;
import com.premiumminds.billy.spain.services.builders.ESPaymentBuilder;
import com.premiumminds.billy.spain.services.entities.ESPayment;

public class ESPaymentBuilderImpl<TBuilder extends ESPaymentBuilderImpl<TBuilder, TPayment>, TPayment extends ESPayment>
        extends PaymentBuilderImpl<TBuilder, TPayment> implements ESPaymentBuilder<TBuilder, TPayment> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/spain/i18n/FieldNames");

    @Inject
    public ESPaymentBuilderImpl(DAOESPayment daoESPayment) {
        super(daoESPayment);
    }

    @Override
    protected ESPaymentEntity getTypeInstance() {
        return (ESPaymentEntity) super.getTypeInstance();
    }

    @Override
    public TBuilder setPaymentAmount(BigDecimal amount) {
        BillyValidator.notNull(amount, ESPaymentBuilderImpl.LOCALIZER.getString("field.payment_amount"));
        this.getTypeInstance().setPaymentAmount(amount);
        return this.getBuilder();
    }

    @Override
    public TBuilder setPaymentMethod(Enum<?> method) {
        BillyValidator.notNull(method, ESPaymentBuilderImpl.LOCALIZER.getString("field.payment_method"));
        this.getTypeInstance().setPaymentMethod(method);
        return this.getBuilder();
    }

    @Override
    public TBuilder setPaymentDate(Date date) {
        BillyValidator.notNull(date, ESPaymentBuilderImpl.LOCALIZER.getString("field.payment_date"));
        this.getTypeInstance().setPaymentDate(date);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException, ValidationException {
        super.validateInstance();
        ESPaymentEntity p = this.getTypeInstance();
        BillyValidator.mandatory(p.getPaymentAmount(),
                ESPaymentBuilderImpl.LOCALIZER.getString("field.payment_amount"));
        BillyValidator.mandatory(p.getPaymentMethod(),
                ESPaymentBuilderImpl.LOCALIZER.getString("field.payment_method"));
        BillyValidator.mandatory(p.getPaymentDate(), ESPaymentBuilderImpl.LOCALIZER.getString("field.payment_date"));
    }

}
