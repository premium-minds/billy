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

import jakarta.inject.Provider;
import jakarta.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.AbstractDAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.persistence.entities.jpa.JPAGenericInvoiceEntryEntity;

public abstract class AbstractDAOGenericInvoiceEntryImpl<TInterface extends GenericInvoiceEntryEntity,
        TEntity extends JPAGenericInvoiceEntryEntity>
        extends AbstractDAO<GenericInvoiceEntry, TInterface, TEntity>
        implements AbstractDAOGenericInvoiceEntry<TInterface> {

    public AbstractDAOGenericInvoiceEntryImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

}
