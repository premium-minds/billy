/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.persistence.dao.jpa;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.jpa.AbstractDAOGenericInvoiceImpl;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.france.persistence.dao.AbstractDAOFRGenericInvoice;
import com.premiumminds.billy.france.persistence.entities.FRGenericInvoiceEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.JPAFRGenericInvoiceEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.QJPAFRBusinessEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.QJPAFRGenericInvoiceEntity;

public abstract class AbstractDAOFRGenericInvoiceImpl<TInterface extends FRGenericInvoiceEntity, TEntity extends JPAFRGenericInvoiceEntity>
        extends AbstractDAOGenericInvoiceImpl<TInterface, TEntity> implements AbstractDAOFRGenericInvoice<TInterface> {

    @Inject
    public AbstractDAOFRGenericInvoiceImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @SuppressWarnings("unchecked")
    @Override
    public TInterface findByNumber(UID uidBusiness, String number) {
        QJPAFRGenericInvoiceEntity invoice = QJPAFRGenericInvoiceEntity.jPAFRGenericInvoiceEntity;

        return (TInterface) this.checkEntity(
                this.createQuery()
                        .from(invoice).where(this.toDSL(invoice.business, QJPAFRBusinessEntity.class).uid
                                .eq(uidBusiness.toString()).and(invoice.number.eq(number)))
                        .singleResult(invoice),
                FRGenericInvoiceEntity.class); // FIXME: CAST!!
    }
}
