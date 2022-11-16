/*
 * Copyright (C) 2017 Premium Minds.
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
package com.premiumminds.billy.persistence.dao.jpa;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.AbstractDAOGenericInvoice;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.persistence.entities.jpa.JPABusinessEntity;
import com.premiumminds.billy.persistence.entities.jpa.JPAGenericInvoiceEntity;
import com.premiumminds.billy.persistence.entities.jpa.QJPABusinessEntity;
import com.premiumminds.billy.persistence.entities.jpa.QJPAGenericInvoiceEntity;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

public abstract class AbstractDAOGenericInvoiceImpl<TInterface extends GenericInvoiceEntity, TEntity extends JPAGenericInvoiceEntity>
        extends AbstractDAO<GenericInvoice, TInterface, TEntity> implements AbstractDAOGenericInvoice<TInterface> {

    @Inject
    public AbstractDAOGenericInvoiceImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @SuppressWarnings("unchecked")
    @Override
    public TInterface getLatestInvoiceFromSeries(String series, String businessUID) {

        QJPAGenericInvoiceEntity genericInvoice = QJPAGenericInvoiceEntity.jPAGenericInvoiceEntity;
        QJPABusinessEntity business = QJPABusinessEntity.jPABusinessEntity;

        JPABusinessEntity businessEntity = new JPAQuery<>(this.getEntityManager())
            .from(business)
            .where(business.uid.eq(businessUID))
            .select(business)
            .fetchOne();

        if (businessEntity == null) {
            throw new BillyRuntimeException();
        }

        GenericInvoiceEntity invoice = new JPAQuery<>(this.getEntityManager())
            .from(genericInvoice)
            .where(genericInvoice.series.eq(series))
            .where(genericInvoice.business.eq(businessEntity))
            .where(genericInvoice.seriesNumber.eq(JPAExpressions
                                                      .select(genericInvoice.seriesNumber.max())
                                                      .from(genericInvoice)
                                                      .where(genericInvoice.series.eq(series))
                                                      .where(genericInvoice.business.eq(businessEntity))))
            .select(genericInvoice)
            .fetchOne();

        return (TInterface) invoice; // FIXME: CAST
    }
}
