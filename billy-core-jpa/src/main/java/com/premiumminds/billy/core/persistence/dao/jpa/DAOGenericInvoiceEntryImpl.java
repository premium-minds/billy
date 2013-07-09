/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-core-jpa.
 * 
 * billy-core-jpa is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-core-jpa is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core-jpa.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.core.persistence.dao.jpa;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAGenericInvoiceEntryEntity;

public class DAOGenericInvoiceEntryImpl extends AbstractDAO<GenericInvoiceEntryEntity, JPAGenericInvoiceEntryEntity> implements DAOGenericInvoiceEntry {

	@Inject
	public DAOGenericInvoiceEntryImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}
	
	@Override
	protected Class<JPAGenericInvoiceEntryEntity> getEntityClass() {
		return JPAGenericInvoiceEntryEntity.class;
	}

	@Override
	public GenericInvoiceEntryEntity getEntityInstance() {
		return new JPAGenericInvoiceEntryEntity();
	}

}
