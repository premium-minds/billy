/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.persistence.dao.jpa;

import com.premiumminds.billy.andorra.persistence.entities.ADGenericInvoiceEntryEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.JPAADGenericInvoiceEntryEntity;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.andorra.persistence.dao.DAOADGenericInvoiceEntry;

public class DAOADGenericInvoiceEntryImpl
        extends AbstractDAOADGenericInvoiceEntryImpl<ADGenericInvoiceEntryEntity, JPAADGenericInvoiceEntryEntity>
        implements DAOADGenericInvoiceEntry
{

    @Inject
    public DAOADGenericInvoiceEntryImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ADGenericInvoiceEntryEntity getEntityInstance() {
        return new JPAADGenericInvoiceEntryEntity();
    }

    @Override
    protected Class<? extends JPAADGenericInvoiceEntryEntity> getEntityClass() {
        return JPAADGenericInvoiceEntryEntity.class;
    }

}
