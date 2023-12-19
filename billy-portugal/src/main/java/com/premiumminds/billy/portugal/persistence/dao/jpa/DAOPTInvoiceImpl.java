/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.dao.jpa;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTInvoiceEntity;
import com.querydsl.jpa.impl.JPAQuery;
import java.time.LocalDate;
import java.util.List;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.persistence.EntityManager;

public class DAOPTInvoiceImpl extends AbstractDAOPTGenericInvoiceImpl<PTInvoiceEntity, JPAPTInvoiceEntity>
        implements DAOPTInvoice {

    @Inject
    public DAOPTInvoiceImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public PTInvoiceEntity getEntityInstance() {
        return new JPAPTInvoiceEntity();
    }

    @Override
    protected Class<? extends JPAPTInvoiceEntity> getEntityClass() {
        return JPAPTInvoiceEntity.class;
    }

    @Override
    public List<PTInvoiceEntity> getBusinessInvoicesForSAFTPT(StringID<Business> uid, LocalDate from, LocalDate to) {

        QJPAPTInvoiceEntity invoice = QJPAPTInvoiceEntity.jPAPTInvoiceEntity;

        JPAQuery<PTInvoiceEntity> query = this.createQuery();

        query.from(invoice).where(invoice.instanceOf(JPAPTInvoiceEntity.class).and(invoice.localDate.between(from, to))
                .and(this.toDSL(invoice.business, QJPAPTBusinessEntity.class).uid.eq(uid.toString())));

        return this.checkEntityList(query.select(invoice).fetch(), PTInvoiceEntity.class);
    }

}
