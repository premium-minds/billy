/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy.
 *
 * billy is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.platypus.entities;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.entities.BaseEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Entity;
import com.premiumminds.billy.core.services.entities.converters.AbstractEntityConverter;
import com.premiumminds.billy.core.util.Comparer;
import com.premiumminds.billy.platypus.PlatypusBaseTest;

public abstract class PlatypusEntityTest
<TServiceEntity extends Entity, TPersistenceEntity extends Entity,
TService, TDAO extends DAO<TPersistenceEntity>>
extends
PlatypusBaseTest {

	@Test
	public void crudTest() throws Exception {
		for(final TPersistenceEntity entity : getTestEntities()) {
			doTest(entity);
		}
		assertTrue(true);
	}

	public void doTest(final TPersistenceEntity persistenceEntity) throws Exception {
		DAOGenericInvoice dao = getInstance(DAOGenericInvoice.class);

		//Persistence
		new TransactionWrapper<Void>(dao) {
			@Override
			public Void runTransaction() throws Exception {
				persistenceCreateTest(persistenceEntity);
				return null;
			}
		}.execute();
		new TransactionWrapper<Void>(dao) {
			@Override
			public Void runTransaction() throws Exception {
				persistenceReadTest(persistenceEntity);
				return null;
			}
		}.execute();
		new TransactionWrapper<Void>(dao) {
			@Override
			public Void runTransaction() throws Exception {
				persistenceUpdateTest(persistenceEntity);
				return null;
			}
		}.execute();

		//Service
		final TServiceEntity serviceEntity = getConverter().toService(persistenceEntity, false);
		new TransactionWrapper<Void>(dao) {
			@Override
			public Void runTransaction() throws Exception {
				serviceCreateTest(serviceEntity);
				return null;
			}
		}.execute();
		new TransactionWrapper<Void>(dao) {
			@Override
			public Void runTransaction() throws Exception {
				serviceUpdateTest(serviceEntity);
				return null;
			}
		}.execute();
		new TransactionWrapper<Void>(dao) {
			@Override
			public Void runTransaction() throws Exception {
				serviceReadTest(serviceEntity);
				return null;
			}
		}.execute();
	}

	//Services
	private TServiceEntity serviceCreateTest(
			TServiceEntity serviceEntity) {
		TServiceEntity persistedEntity = createServiceEntity(serviceEntity);
		try {
			assertTrue(Comparer.areEqual(serviceEntity, persistedEntity));
		} catch (IOException e) {
			fail(e.getMessage());
		}
		return persistedEntity;
	}

	protected abstract TServiceEntity createServiceEntity(TServiceEntity entity);

	private TServiceEntity serviceUpdateTest(
			TServiceEntity serviceEntity) {
		TServiceEntity persistedEntity = createServiceEntity(serviceEntity);
		try {
			assertTrue(Comparer.areEqual(serviceEntity, persistedEntity));
		} catch (IOException e) {
			fail(e.getMessage());
		}
		return persistedEntity;
	}

	protected abstract TServiceEntity updateServiceEntity(TServiceEntity entity);

	private TServiceEntity serviceReadTest(
			TServiceEntity serviceEntity) {
		TServiceEntity persistedEntity = updateServiceEntity(serviceEntity);
		try {
			assertTrue(Comparer.areEqual(serviceEntity, persistedEntity));
		} catch (IOException e) {
			fail(e.getMessage());
		}
		return persistedEntity;
	}

	protected abstract TServiceEntity readServiceEntity(UID entityUID);

	//Persistence
	private TPersistenceEntity persistenceReadTest(
			TPersistenceEntity persistenceEntity) {
		TDAO dao = getInstance(getDAOClass());
		TPersistenceEntity persistedEntity = dao.get(persistenceEntity.getUUID());
		try {
			assertTrue(Comparer.areEqual(persistenceEntity, persistedEntity));
		} catch (IOException e) {
			fail(e.getMessage());
		}
		return persistedEntity;
	}

	private TPersistenceEntity persistenceCreateTest(
			TPersistenceEntity persistenceEntity) {
		TDAO dao = getInstance(getDAOClass());
		TPersistenceEntity persistedEntity = dao.create(persistenceEntity);
		try {
			assertTrue(Comparer.areEqual(persistenceEntity, persistedEntity));
		} catch (IOException e) {
			fail(e.getMessage());
		}
		return persistedEntity;
	}

	private TPersistenceEntity persistenceUpdateTest(
			TPersistenceEntity persistenceEntity) {
		TDAO dao = getInstance(getDAOClass());
		TPersistenceEntity persistedEntity = dao.update(persistenceEntity);
		try {
			assertTrue(Comparer.areEqual(persistenceEntity, persistedEntity));
		} catch (IOException e) {
			fail(e.getMessage());
		}
		return persistedEntity;
	}

	public abstract Collection<TPersistenceEntity> getTestEntities();

	public abstract AbstractEntityConverter<TPersistenceEntity, TServiceEntity> getConverter();

	public abstract Class<TService> getServiceClass();

	public abstract Class<TDAO> getDAOClass();

}
