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
package com.premiumminds.billy.france.test.util;

import java.math.BigDecimal;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.france.persistence.entities.FRPaymentEntity;
import com.premiumminds.billy.france.services.entities.FRPayment;

public class FRPaymentTestUtil {

    private static final BigDecimal AMOUNT = new BigDecimal(20);
    private static final Date DATE = new Date();
    private static final Enum<PaymentMechanism> METHOD = PaymentMechanism.CASH;

    private Injector injector;

    public FRPaymentTestUtil(Injector injector) {
        this.injector = injector;
    }

    public FRPayment.Builder getPaymentBuilder(BigDecimal amount, Date date, Enum<?> method) {
        FRPayment.Builder paymentBuilder = this.injector.getInstance(FRPayment.Builder.class);
        paymentBuilder.setPaymentAmount(amount).setPaymentDate(date).setPaymentMethod(method);

        return paymentBuilder;
    }

    public FRPayment.Builder getPaymentBuilder() {
        return this.getPaymentBuilder(FRPaymentTestUtil.AMOUNT, FRPaymentTestUtil.DATE, FRPaymentTestUtil.METHOD);
    }

    public FRPaymentEntity getPaymentEntity(BigDecimal amount, Date date, Enum<?> method) {
        return (FRPaymentEntity) this.getPaymentBuilder(amount, date, method).build();
    }

    public FRPaymentEntity getPaymentEntity() {
        return (FRPaymentEntity) this.getPaymentBuilder().build();
    }
}
