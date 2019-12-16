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

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.mysema.query.jpa.impl.JPAQuery;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTInvoiceEntity;

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
    public List<PTInvoiceEntity> getBusinessInvoicesForSAFTPT(UID uid, Date from, Date to) {

        QJPAPTInvoiceEntity invoice = QJPAPTInvoiceEntity.jPAPTInvoiceEntity;

        JPAQuery query = this.createQuery();

        query.from(invoice).where(invoice.instanceOf(JPAPTInvoiceEntity.class).and(invoice.date.between(from, to))
                .and(this.toDSL(invoice.business, QJPAPTBusinessEntity.class).uid.eq(uid.toString())));

        List<PTInvoiceEntity> result = this.checkEntityList(query.list(invoice), PTInvoiceEntity.class);
        return result;
    }

}
