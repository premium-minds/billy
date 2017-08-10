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
package com.premiumminds.billy.core.persistence.dao.jpa;

import com.premiumminds.billy.core.persistence.dao.AbstractDAOGenericInvoice;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAGenericInvoiceEntity;

public abstract class AbstractDAOGenericInvoiceImpl<TInterface extends GenericInvoiceEntity, TEntity extends JPAGenericInvoiceEntity>
        extends AbstractDAO<TInterface, TEntity> implements AbstractDAOGenericInvoice<TInterface> {

    @SuppressWarnings("unchecked")
    @Override
    public TInterface getLatestInvoiceFromSeries(String series, String businessUID) {

        /*QJPAGenericInvoiceEntity genericInvoice = QJPAGenericInvoiceEntity.jPAGenericInvoiceEntity;
        QJPABusinessEntity business = QJPABusinessEntity.jPABusinessEntity;
        
        JPAQuery query = new JPAQuery(this.getEntityManager());
        
        BusinessEntity businessEnity = query.from(business).where(business.uid.eq(businessUID)).uniqueResult(business);
        
        if (businessEnity == null) {
            throw new BillyRuntimeException();
        }
        
        query = new JPAQuery(this.getEntityManager());
        
        query.from(genericInvoice);
        query.where(genericInvoice.series.eq(series));
        query.where(genericInvoice.business.eq(businessEnity));
        query.where(genericInvoice.seriesNumber
                .eq(new JPASubQuery().from(genericInvoice).where(genericInvoice.series.eq(series))
                        .where(genericInvoice.business.eq(businessEnity)).unique(genericInvoice.seriesNumber.max())));
        
        GenericInvoiceEntity invoice = query.uniqueResult(genericInvoice);
        
        return (TInterface) invoice; // FIXME: CAST*/
        return null;
    }
}
