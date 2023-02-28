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

import com.premiumminds.billy.andorra.persistence.entities.ADGenericInvoiceEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.JPAADGenericInvoiceEntity;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.andorra.persistence.dao.DAOADGenericInvoice;

public class DAOADGenericInvoiceImpl
        extends AbstractDAOADGenericInvoiceImpl<ADGenericInvoiceEntity, JPAADGenericInvoiceEntity>
        implements DAOADGenericInvoice
{

    @Inject
    public DAOADGenericInvoiceImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ADGenericInvoiceEntity getEntityInstance() {
        return new JPAADGenericInvoiceEntity();
    }

    @Override
    protected Class<? extends JPAADGenericInvoiceEntity> getEntityClass() {
        return JPAADGenericInvoiceEntity.class;
    }

}
