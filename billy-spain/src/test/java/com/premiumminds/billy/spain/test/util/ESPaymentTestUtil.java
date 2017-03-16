/**
 * Copyright (C) 2013 Premium Minds.
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
package com.premiumminds.billy.spain.test.util;

import java.math.BigDecimal;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.spain.persistence.entities.ESPaymentEntity;
import com.premiumminds.billy.spain.services.entities.ESPayment;
import com.premiumminds.billy.spain.util.PaymentMechanism;


public class ESPaymentTestUtil {
	
	private static final BigDecimal AMOUNT = new BigDecimal(20);
	private static final Date DATE = new Date();
	private static final Enum<PaymentMechanism> METHOD = PaymentMechanism.CASH;

	private Injector injector;
	
	public ESPaymentTestUtil(Injector injector) {
		this.injector = injector;
	}
	
	public ESPayment.Builder getPaymentBuilder(BigDecimal amount, Date date, Enum<?> method) {
		ESPayment.Builder paymentBuilder = this.injector.getInstance(ESPayment.Builder.class);
		paymentBuilder.setPaymentAmount(amount).setPaymentDate(date).setPaymentMethod(method);
		
		return paymentBuilder;
	}
	
	public ESPayment.Builder getPaymentBuilder() {
		return this.getPaymentBuilder(AMOUNT, DATE, METHOD);
	}
	
	public ESPaymentEntity getPaymentEntity(BigDecimal amount, Date date, Enum<?> method) {
		return (ESPaymentEntity) getPaymentBuilder(amount, date, method).build();
	}
	
	public ESPaymentEntity getPaymentEntity() {
		return (ESPaymentEntity) getPaymentBuilder().build();
	}
}
