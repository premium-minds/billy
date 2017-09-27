/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.dao.ebean;

import com.premiumminds.billy.core.persistence.dao.ebean.AbstractDAOGenericInvoiceImpl;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.AbstractDAOPTGenericInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.ebean.JPAPTGenericInvoiceEntity;

public abstract class AbstractDAOPTGenericInvoiceImpl<TInterface extends PTGenericInvoiceEntity, TEntity extends JPAPTGenericInvoiceEntity>
        extends AbstractDAOGenericInvoiceImpl<TInterface, TEntity> implements AbstractDAOPTGenericInvoice<TInterface> {

    @SuppressWarnings("unchecked")
    @Override
    public TInterface findByNumber(UID uidBusiness, String number) {
        /*QJPAPTGenericInvoiceEntity invoice = QJPAPTGenericInvoiceEntity.jPAPTGenericInvoiceEntity;
        
        return (TInterface) this.checkEntity(
                this.createQuery()
                        .from(invoice).where(this.toDSL(invoice.business, QJPAPTBusinessEntity.class).uid
                                .eq(uidBusiness.toString()).and(invoice.number.eq(number)))
                        .singleResult(invoice),
                PTGenericInvoiceEntity.class);*/
        return null;
    }
}
