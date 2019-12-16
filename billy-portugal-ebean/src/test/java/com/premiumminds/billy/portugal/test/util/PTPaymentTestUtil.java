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
package com.premiumminds.billy.portugal.test.util;

import java.math.BigDecimal;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.portugal.persistence.entities.PTPaymentEntity;
import com.premiumminds.billy.portugal.services.entities.PTPayment;

public class PTPaymentTestUtil {

    private static final BigDecimal AMOUNT = new BigDecimal(20);
    private static final Date DATE = new Date();
    private static final Enum<PaymentMechanism> METHOD = PaymentMechanism.CASH;

    private Injector injector;

    public PTPaymentTestUtil(Injector injector) {
        this.injector = injector;
    }

    public PTPayment.Builder getPaymentBuilder(BigDecimal amount, Date date, Enum<?> method) {
        PTPayment.Builder paymentBuilder = this.injector.getInstance(PTPayment.Builder.class);
        paymentBuilder.setPaymentAmount(amount).setPaymentDate(date).setPaymentMethod(method);

        return paymentBuilder;
    }

    public PTPayment.Builder getPaymentBuilder() {
        return this.getPaymentBuilder(PTPaymentTestUtil.AMOUNT, PTPaymentTestUtil.DATE, PTPaymentTestUtil.METHOD);
    }

    public PTPaymentEntity getPaymentEntity(BigDecimal amount, Date date, Enum<?> method) {
        return (PTPaymentEntity) this.getPaymentBuilder(amount, date, method).build();
    }

    public PTPaymentEntity getPaymentEntity() {
        return (PTPaymentEntity) this.getPaymentBuilder().build();
    }
}
