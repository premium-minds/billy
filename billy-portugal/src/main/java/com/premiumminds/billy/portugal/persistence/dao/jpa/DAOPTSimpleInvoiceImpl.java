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
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTSimpleInvoiceEntity;
import com.querydsl.jpa.impl.JPAQuery;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

public class DAOPTSimpleInvoiceImpl extends
        AbstractDAOPTGenericInvoiceImpl<PTSimpleInvoiceEntity, JPAPTSimpleInvoiceEntity> implements DAOPTSimpleInvoice {

    @Inject
    public DAOPTSimpleInvoiceImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public PTSimpleInvoiceEntity getEntityInstance() {
        return new JPAPTSimpleInvoiceEntity();
    }

    @Override
    protected Class<JPAPTSimpleInvoiceEntity> getEntityClass() {
        return JPAPTSimpleInvoiceEntity.class;
    }

    @Override
    public List<PTSimpleInvoiceEntity> getBusinessSimpleInvoicesForSAFTPT(StringID<Business> uid, LocalDate from, LocalDate to) {
        QJPAPTSimpleInvoiceEntity invoice = QJPAPTSimpleInvoiceEntity.jPAPTSimpleInvoiceEntity;

        JPAQuery<PTSimpleInvoiceEntity> query = this.createQuery();

        query.from(invoice).where(invoice.instanceOf(JPAPTSimpleInvoiceEntity.class).and(invoice.localDate.between(from, to))
                .and(this.toDSL(invoice.business, QJPAPTBusinessEntity.class).uid.eq(uid.toString())));

        return this.checkEntityList(query.select(invoice).fetch(), PTSimpleInvoiceEntity.class);
    }

}
