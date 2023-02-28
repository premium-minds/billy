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
package com.premiumminds.billy.andorra.test.util;

import java.math.BigDecimal;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.andorra.persistence.entities.ADPaymentEntity;
import com.premiumminds.billy.andorra.services.entities.ADPayment;

public class ESPaymentTestUtil {

    private static final BigDecimal AMOUNT = new BigDecimal(20);
    private static final Date DATE = new Date();
    private static final Enum<PaymentMechanism> METHOD = PaymentMechanism.CASH;

    private Injector injector;

    public ESPaymentTestUtil(Injector injector) {
        this.injector = injector;
    }

    public ADPayment.Builder getPaymentBuilder(BigDecimal amount, Date date, Enum<?> method) {
        ADPayment.Builder paymentBuilder = this.injector.getInstance(ADPayment.Builder.class);
        paymentBuilder.setPaymentAmount(amount).setPaymentDate(date).setPaymentMethod(method);

        return paymentBuilder;
    }

    public ADPayment.Builder getPaymentBuilder() {
        return this.getPaymentBuilder(ESPaymentTestUtil.AMOUNT, ESPaymentTestUtil.DATE, ESPaymentTestUtil.METHOD);
    }

    public ADPaymentEntity getPaymentEntity(BigDecimal amount, Date date, Enum<?> method) {
        return (ADPaymentEntity) this.getPaymentBuilder(amount, date, method).build();
    }

    public ADPaymentEntity getPaymentEntity() {
        return (ADPaymentEntity) this.getPaymentBuilder().build();
    }
}
