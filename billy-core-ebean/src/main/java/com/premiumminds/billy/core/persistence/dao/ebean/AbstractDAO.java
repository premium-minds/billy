/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core Ebean.
 *
 * billy core Ebean is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core Ebean is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core Ebean. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.persistence.dao.ebean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.entities.BaseEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.JPABaseEntity;
import com.premiumminds.billy.core.services.UID;

import io.ebean.Ebean;

public abstract class AbstractDAO<TInterface extends BaseEntity, TEntity extends JPABaseEntity & BaseEntity>
        implements DAO<TInterface> {

    private boolean isForRollback = false;

    @Override
    public void beginTransaction() {
        Ebean.beginTransaction();
        this.isForRollback = false;
    }

    @Override
    public void rollback() {
        Ebean.rollbackTransaction();
    }

    @Override
    public void setForRollback() {
        Ebean.setRollbackOnly();
        this.isForRollback = true;
    }

    @Override
    public boolean isSetForRollback() {
        return this.isForRollback;
    }

    @Override
    public void commit() {
        Ebean.commitTransaction();
    }

    @Override
    public void lock(TInterface entity, LockModeType type) {
        // Not used. Needed to override DAO.java interface from billy-core
        throw new RuntimeException("Unsupported operation: lock(...)");
    }

    @Override
    public boolean isTransactionActive() {
        return Ebean.currentTransaction() != null;
    }

    @SuppressWarnings("unchecked")
    protected <T2 extends BaseEntity> T2 checkEntity(Object candidate, Class<T2> entityClass) {
        if (candidate == null) {
            return null;
        }
        if (entityClass.isInstance(candidate)) {
            return (T2) candidate;
        }
        throw new RuntimeException(
                "The entity is not a JPA implementation : " + candidate.getClass().getCanonicalName());
    }

    @SuppressWarnings("unchecked")
    protected <T2 extends BaseEntity> List<T2> checkEntityList(List<?> candidates, Class<T2> entityClass) {
        if (candidates == null) {
            return null;
        }
        List<T2> result = new ArrayList<>();
        for (Object candidate : candidates) {
            if (!entityClass.isInstance(candidate)) {
                throw new RuntimeException(
                        "The entity is not a JPA implementation : " + candidate.getClass().getCanonicalName());
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
            result = Ebean.find(entityClass).where().eq("uid", uid.toString()).and().eq("active", true).findOne();
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
        if (result == null) {
            throw new NoResultException("Could not find " + entityClass.getSimpleName() + " with uid: " + uid);
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TInterface create(final TInterface entity) throws PersistenceException {
        if (!entity.isNew()) {
            throw new PersistenceException("Cannot create. The entity is marked as not new.");
        }
        try {
            return new TransactionWrapper<TInterface>(this) {

                @Override
                public TInterface runTransaction() throws Exception {
                    try {
                        AbstractDAO.this.getEntity(entity.getUID());
                        throw new RuntimeException(
                                "Cannot create the new entity since its uid already exists in the table : " +
                                        entity.getUID());
                    } catch (NoResultException e) {
                        // Ok so do nothing
                    } catch (Exception e) {
                        throw e;
                    }
                    TEntity newEntity = (TEntity) entity;
                    Ebean.save(newEntity);
                    return AbstractDAO.this.get(entity.getUID());
                }
            }.execute();
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public final synchronized TInterface update(final TInterface entity) throws PersistenceException {
        if (entity.isNew()) {
            throw new PersistenceException("Cannot update. The entity is marked as new.");
        }
        try {
            return new TransactionWrapper<TInterface>(this) {

                @Override
                public TInterface runTransaction() throws Exception {
                    TEntity oldVersion = AbstractDAO.this.getEntity(entity.getUID());
                    if (oldVersion == null) {
                        throw new RuntimeException("Cannot update a non existing entity : " + entity.getUID());
                    }

                    TEntity newVersion = (TEntity) entity;
                    Ebean.save(newVersion);

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
        TEntity entity = Ebean.find(entityClass).where().eq("uid", uid.toString()).findOne();
        return entity != null;
    }

    protected abstract Class<? extends TEntity> getEntityClass();

}
