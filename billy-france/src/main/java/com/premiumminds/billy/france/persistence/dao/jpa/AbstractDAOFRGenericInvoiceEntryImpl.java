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

import com.premiumminds.billy.core.persistence.dao.jpa.AbstractDAOGenericInvoiceEntryImpl;
import com.premiumminds.billy.france.persistence.dao.AbstractDAOFRGenericInvoiceEntry;
import com.premiumminds.billy.france.persistence.entities.FRGenericInvoiceEntryEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.JPAFRGenericInvoiceEntryEntity;

public abstract class AbstractDAOFRGenericInvoiceEntryImpl<TInterface extends FRGenericInvoiceEntryEntity, TEntity extends JPAFRGenericInvoiceEntryEntity>
        extends AbstractDAOGenericInvoiceEntryImpl<TInterface, TEntity>
        implements AbstractDAOFRGenericInvoiceEntry<TInterface> {

    @Inject
    public AbstractDAOFRGenericInvoiceEntryImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

}
