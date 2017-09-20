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
package com.premiumminds.billy.portugal.persistence.dao.jpa;

import com.premiumminds.billy.core.persistence.dao.ebean.AbstractDAOGenericInvoiceEntryImpl;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTGenericInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTGenericInvoiceEntryEntity;

public class DAOPTGenericInvoiceEntryImpl
        extends AbstractDAOGenericInvoiceEntryImpl<PTGenericInvoiceEntryEntity, JPAPTGenericInvoiceEntryEntity>
        implements DAOPTGenericInvoiceEntry {

    @Override
    public PTGenericInvoiceEntryEntity getEntityInstance() {
        return new JPAPTGenericInvoiceEntryEntity();
    }

    @Override
    protected Class<? extends JPAPTGenericInvoiceEntryEntity> getEntityClass() {
        return JPAPTGenericInvoiceEntryEntity.class;
    }

}
