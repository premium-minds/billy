/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.services.builders.impl;

import java.util.Date;

import javax.validation.ValidationException;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.entities.PaymentEntity;
import com.premiumminds.billy.core.services.builders.PaymentBuilder;
import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.util.EntityFactory;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;

public class PaymentBuilderImpl<TBuilder extends PaymentBuilderImpl<TBuilder, TPayment>, TPayment extends Payment>
		extends AbstractBuilder<TBuilder, TPayment> implements
		PaymentBuilder<TBuilder, TPayment> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/portugal/i18n/FieldNames");

	public PaymentBuilderImpl(EntityFactory<?> entityFactory) {
		super(entityFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected PaymentEntity getTypeInstance() {
		return (PaymentEntity) super.getTypeInstance();
	}

	@Override
	public TBuilder setPaymentMethod(String method) {
		BillyValidator.mandatory(method, PaymentBuilderImpl.LOCALIZER
				.getString("field.payment_method"));
		this.getTypeInstance().setPaymentMethod(method);
		return this.getBuilder();
	}

	@Override
	public TBuilder setPaymentDate(Date date) {
		BillyValidator.mandatory(date, PaymentBuilderImpl.LOCALIZER.getString("field.payment_date"));
		this.getTypeInstance().setPaymentDate(date);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance() throws BillyValidationException,
			ValidationException {
		PaymentEntity p = this.getTypeInstance();
		BillyValidator.mandatory(p.getPaymentDate(), PaymentBuilderImpl.LOCALIZER
				.getString("field.payment_date"));
		BillyValidator.mandatory(p.getPaymentMethod(), PaymentBuilderImpl.LOCALIZER
				.getString("field.payment_method"));
	}

}
