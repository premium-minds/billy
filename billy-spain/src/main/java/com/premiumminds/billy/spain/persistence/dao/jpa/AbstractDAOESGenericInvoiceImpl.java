/*
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

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.persistence.dao.jpa.AbstractDAOGenericInvoiceImpl;
import com.premiumminds.billy.spain.persistence.dao.AbstractDAOESGenericInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESGenericInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESGenericInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESBusinessEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESGenericInvoiceEntity;

public abstract class AbstractDAOESGenericInvoiceImpl<TInterface extends ESGenericInvoiceEntity, TEntity extends JPAESGenericInvoiceEntity>
        extends AbstractDAOGenericInvoiceImpl<TInterface, TEntity> implements AbstractDAOESGenericInvoice<TInterface> {

    @Inject
    public AbstractDAOESGenericInvoiceImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @SuppressWarnings("unchecked")
    @Override
    public TInterface findByNumber(StringID<Business> uidBusiness, String number) {
        QJPAESGenericInvoiceEntity invoice = QJPAESGenericInvoiceEntity.jPAESGenericInvoiceEntity;

        return (TInterface) this.checkEntity(
                this.createQuery()
                        .from(invoice).where(this.toDSL(invoice.business, QJPAESBusinessEntity.class).uid
                                .eq(uidBusiness.toString()).and(invoice.number.eq(number)))
                        .select(invoice).fetchOne(),
                ESGenericInvoiceEntity.class); // FIXME: CAST!!
    }
}
