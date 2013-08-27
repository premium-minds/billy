/**
 * Copyright (C) 2013 Premium Minds.
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
package com.premiumminds.billy.core.persistence.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.entities.BaseEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPABaseEntity;
import com.premiumminds.billy.core.services.UID;

public abstract class AbstractDAO<TInterface extends BaseEntity, TEntity extends JPABaseEntity & BaseEntity>
	implements DAO<TInterface> {

	protected Provider<EntityManager>	emProvider;

	@Inject
	public AbstractDAO(Provider<EntityManager> emProvider) {
		this.emProvider = emProvider;
		this.getEntityManager().setFlushMode(FlushModeType.AUTO);
	}

	@Override
	public void beginTransaction() {
		this.getEntityManager().getTransaction().begin();
	}

	@Override
	public void rollback() {
		this.getEntityManager().getTransaction().rollback();
	}

	@Override
	public void setForRollback() {
		this.getEntityManager().getTransaction().setRollbackOnly();
	}

	@Override
	public boolean isSetForRollback() {
		return this.getEntityManager().getTransaction().getRollbackOnly();
	}

	@Override
	public void commit() {
		this.getEntityManager().getTransaction().commit();
	}

	@Override
	public void lock(TInterface entity, LockModeType type) {
		if (isTransactionActive()) {
			this.getEntityManager().lock(entity, type);
		}
	}

	@Override
	public boolean isTransactionActive() {
		return this.getEntityManager().getTransaction().isActive();
	}

	public EntityManager getEntityManager() {
		return this.emProvider.get();
	}

	@SuppressWarnings("unchecked")
	protected <T2 extends BaseEntity> T2 checkEntity(Object candidate,
			Class<T2> entityClass) {
		if (candidate == null) {
			return null;
		}
		if (entityClass.isInstance(candidate)) {
			return (T2) candidate;
		}
		throw new RuntimeException("The entity is not a JPA implementation : "
				+ candidate.getClass().getCanonicalName());
	}

	@SuppressWarnings("unchecked")
	protected <T2 extends BaseEntity> List<T2> checkEntityList(
			List<?> candidates, Class<T2> entityClass) {
		if (candidates == null) {
			return null;
		}
		List<T2> result = new ArrayList<T2>();
		for (Object candidate : candidates) {
			if (!entityClass.isInstance(candidate)) {
				throw new RuntimeException(
						"The entity is not a JPA implementation : "
								+ candidate.getClass().getCanonicalName());
			}
			result.add((T2) candidate);
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public TInterface get(UID uid) throws NoResultException {
		return (TInterface) this.getEntity(uid);
	}

	protected TEntity getEntity(UID uid) throws NoResultException {

		TEntity result = null;
		Class<? extends TEntity> entityClass = this.getEntityClass();
		try {
			result = this
					.getEntityManager()
					.createQuery(
							"select e from " + entityClass.getCanonicalName()
									+ " e "
									+ "where e.uid=:uid and e.active=true "
									+ "order by e.entityVersion desc",
							entityClass).setParameter("uid", uid.toString())
					.setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public TInterface create(final TInterface entity)
		throws PersistenceException {
		if (!entity.isNew()) {
			throw new PersistenceException(
					"Cannot create. The entity is marked as not new.");
		}
		try {
			return new TransactionWrapper<TInterface>(this) {

				@Override
				public TInterface runTransaction() throws Exception {
					try {
						AbstractDAO.this.getEntity(entity.getUID());
						throw new RuntimeException(
								"Cannot create the new entity since its uid already exists in the table : "
										+ entity.getUID());
					} catch (NoResultException e) {
						// Ok so do nothing
					} catch (Exception e) {
						throw e;
					}
					TEntity newEntity = (TEntity) entity;
					AbstractDAO.this.getEntityManager().persist(newEntity);
					return AbstractDAO.this.get(entity.getUID());
				}
			}.execute();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public final synchronized TInterface update(final TInterface entity)
		throws PersistenceException {
		if (entity.isNew()) {
			throw new PersistenceException(
					"Cannot update. The entity is marked as new.");
		}
		try {
			return new TransactionWrapper<TInterface>(this) {

				@Override
				public TInterface runTransaction() throws Exception {
					TEntity oldVersion = AbstractDAO.this.getEntity(entity
							.getUID());
					if (oldVersion == null) {
						throw new RuntimeException(
								"Cannot update a non existing entity : "
										+ entity.getUID());
					}

					TEntity newVersion = (TEntity) entity;
					AbstractDAO.this.getEntityManager().merge(newVersion);
					
					return AbstractDAO.this.get(entity.getUID());
				}
			}.execute();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public boolean exists(UID uid) {
		Class<? extends TEntity> entityClass = this.getEntityClass();
		TEntity entity = null;
		try {
			entity = this
					.getEntityManager()
					.createQuery(
							"select e from " + entityClass.getCanonicalName()
									+ " e "
									+ "where e.uid=:uid and e.active=true",
							entityClass).setParameter("uid", uid.toString())
					.getSingleResult();
		} catch (NoResultException e) {
			return false;
		}
		return entity != null;
	}

	protected abstract Class<? extends TEntity> getEntityClass();

}
