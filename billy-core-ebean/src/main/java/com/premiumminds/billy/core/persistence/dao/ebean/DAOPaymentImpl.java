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
package com.premiumminds.billy.core.persistence.dao.ebean;

import com.premiumminds.billy.core.persistence.dao.DAOPayment;
import com.premiumminds.billy.core.persistence.entities.PaymentEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.JPAPaymentEntity;

public class DAOPaymentImpl extends AbstractDAO<PaymentEntity, JPAPaymentEntity> implements DAOPayment {

    @Override
    public PaymentEntity getEntityInstance() {
        return new JPAPaymentEntity();
    }

    @Override
    protected Class<? extends JPAPaymentEntity> getEntityClass() {
        return JPAPaymentEntity.class;
    }

}
