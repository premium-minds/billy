/**
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

import com.mysema.query.jpa.impl.JPAQuery;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTReceiptInvoiceEntity;

public class DAOPTReceiptInvoiceImpl
        extends AbstractDAOPTGenericInvoiceImpl<PTReceiptInvoiceEntity, JPAPTReceiptInvoiceEntity>
        implements DAOPTReceiptInvoice {

    @Override
    public PTReceiptInvoiceEntity getEntityInstance() {
        return new JPAPTReceiptInvoiceEntity();
    }

    @Override
    protected Class<JPAPTReceiptInvoiceEntity> getEntityClass() {
        return JPAPTReceiptInvoiceEntity.class;
    }

    @Override
    public List<PTReceiptInvoiceEntity> getBusinessReceiptInvoicesForSAFTPT(UID uid, Date from, Date to) {
        QJPAPTReceiptInvoiceEntity invoice = QJPAPTReceiptInvoiceEntity.jPAPTReceiptInvoiceEntity;

        JPAQuery query = this.createQuery();

        query.from(invoice)
                .where(invoice.instanceOf(JPAPTReceiptInvoiceEntity.class).and(invoice.date.between(from, to))
                        .and(this.toDSL(invoice.business, QJPAPTBusinessEntity.class).uid.eq(uid.toString())));

        List<PTReceiptInvoiceEntity> result = this.checkEntityList(query.list(invoice), PTReceiptInvoiceEntity.class);
        return result;
    }

}
