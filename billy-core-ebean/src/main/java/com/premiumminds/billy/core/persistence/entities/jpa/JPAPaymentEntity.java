/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core Ebean.
 *
 * billy core Ebean is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core Ebean is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core Ebean. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.persistence.entities.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.PaymentEntity;

@Entity
@Table(name = Config.TABLE_PREFIX + "PAYMENT")
public class JPAPaymentEntity extends JPABaseEntity implements PaymentEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "PAYMENT_METHOD")
    protected String paymentMethod;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAYMENT_DATE")
    protected Date paymentDate;

    public <T extends Enum<T>> T getPaymentMethod(Class<T> enumType) {
        return Enum.valueOf(enumType, this.paymentMethod);
    }

    @Override
    public Date getPaymentDate() {
        return this.paymentDate;
    }

    @Override
    public void setPaymentMethod(Enum<?> method) {
        this.paymentMethod = method.toString();
    }

    @Override
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public <T extends Enum<?>> T getPaymentMethod() {
        // TODO Auto-generated method stub
        return null;
    }

}
