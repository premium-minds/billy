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

import java.math.BigDecimal;
import java.util.Date;

import jakarta.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.PaymentBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADPayment;
import com.premiumminds.billy.andorra.persistence.entities.ADPaymentEntity;
import com.premiumminds.billy.andorra.services.builders.ADPaymentBuilder;
import com.premiumminds.billy.andorra.services.entities.ADPayment;

public class ADPaymentBuilderImpl<TBuilder extends ADPaymentBuilderImpl<TBuilder, TPayment>, TPayment extends ADPayment>
        extends PaymentBuilderImpl<TBuilder, TPayment> implements ADPaymentBuilder<TBuilder, TPayment>
{

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/andorra/i18n/FieldNames");

    @Inject
    public ADPaymentBuilderImpl(DAOADPayment daoADPayment) {
        super(daoADPayment);
    }

    @Override
    protected ADPaymentEntity getTypeInstance() {
        return (ADPaymentEntity) super.getTypeInstance();
    }

    @Override
    public TBuilder setPaymentAmount(BigDecimal amount) {
        BillyValidator.notNull(amount, ADPaymentBuilderImpl.LOCALIZER.getString("field.payment_amount"));
        this.getTypeInstance().setPaymentAmount(amount);
        return this.getBuilder();
    }

    @Override
    public TBuilder setPaymentMethod(Enum<?> method) {
        BillyValidator.notNull(method, ADPaymentBuilderImpl.LOCALIZER.getString("field.payment_method"));
        this.getTypeInstance().setPaymentMethod(method);
        return this.getBuilder();
    }

    @Override
    public TBuilder setPaymentDate(Date date) {
        BillyValidator.notNull(date, ADPaymentBuilderImpl.LOCALIZER.getString("field.payment_date"));
        this.getTypeInstance().setPaymentDate(date);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        super.validateInstance();
        ADPaymentEntity p = this.getTypeInstance();
        BillyValidator.mandatory(p.getPaymentAmount(),
                                 ADPaymentBuilderImpl.LOCALIZER.getString("field.payment_amount"));
        BillyValidator.mandatory(p.getPaymentMethod(),
                                 ADPaymentBuilderImpl.LOCALIZER.getString("field.payment_method"));
        BillyValidator.mandatory(p.getPaymentDate(), ADPaymentBuilderImpl.LOCALIZER.getString("field.payment_date"));
    }

}
