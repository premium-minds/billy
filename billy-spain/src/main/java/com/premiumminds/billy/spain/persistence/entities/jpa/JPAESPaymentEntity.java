/**
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
package com.premiumminds.billy.spain.persistence.entities.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.persistence.entities.jpa.JPAPaymentEntity;
import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.spain.Config;
import com.premiumminds.billy.spain.persistence.entities.ESPaymentEntity;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "PAYMENT")
public class JPAESPaymentEntity extends JPAPaymentEntity implements ESPaymentEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "PAYMENT_AMOUNT")
	protected BigDecimal paymentAmount;


	@Override
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	@Override
	public void setPaymentAmount(BigDecimal amount) {
		this.paymentAmount = amount;
	}

	@Override
	public PaymentMechanism getPaymentMethod() {
		return PaymentMechanism.valueOf(paymentMethod);
	}

}
