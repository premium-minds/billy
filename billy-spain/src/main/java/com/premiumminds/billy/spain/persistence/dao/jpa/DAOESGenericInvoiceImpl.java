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
package com.premiumminds.billy.spain.persistence.dao.jpa;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.mysema.query.jpa.impl.JPAQuery;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOGenericInvoiceImpl;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.spain.persistence.dao.DAOESGenericInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESBusinessEntity;
import com.premiumminds.billy.spain.persistence.entities.ESGenericInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESGenericInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESBusinessEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESGenericInvoiceEntity;

public class DAOESGenericInvoiceImpl extends DAOGenericInvoiceImpl implements DAOESGenericInvoice {

    @Inject
    public DAOESGenericInvoiceImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ESGenericInvoiceEntity getEntityInstance() {
        return new JPAESGenericInvoiceEntity();
    }

    @Override
    protected Class<? extends JPAESGenericInvoiceEntity> getEntityClass() {
        return JPAESGenericInvoiceEntity.class;
    }

    protected ESBusinessEntity getBusinessEntity(UID uid) {

        QJPAESBusinessEntity business = QJPAESBusinessEntity.jPAESBusinessEntity;
        JPAQuery query = new JPAQuery(this.getEntityManager());

        query.from(business).where(business.uid.eq(uid.getValue()));

        return this.checkEntity(query.singleResult(business), ESBusinessEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ESGenericInvoiceEntity> T findByNumber(UID uidBusiness, String number) {
        QJPAESGenericInvoiceEntity invoice = QJPAESGenericInvoiceEntity.jPAESGenericInvoiceEntity;

        return (T) this.checkEntity(
                this.createQuery()
                        .from(invoice).where(this.toDSL(invoice.business, QJPAESBusinessEntity.class).uid
                                .eq(uidBusiness.toString()).and(invoice.number.eq(number)))
                        .singleResult(invoice),
                ESGenericInvoiceEntity.class);
    }
}
