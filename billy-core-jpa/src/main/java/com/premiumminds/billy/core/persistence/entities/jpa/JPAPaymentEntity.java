/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy core JPA.
 *
 * billy core JPA is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core JPA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core JPA. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.persistence.entities.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.PaymentEntity;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "PAYMENT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JPAPaymentEntity extends JPABaseEntity implements PaymentEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "PAYMENT_METHOD")
	protected String paymentMethod;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PAYMENT_DATE")
	protected Date paymentDate;
	

	@Override
	public String getPaymentMethod() {
		return paymentMethod;
	}

	@Override
	public Date getPaymentDate() {
		return paymentDate;
	}

	@Override
	public void setPaymentMethod(String method) {
		this.paymentMethod = method;
	}

	@Override
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

}
