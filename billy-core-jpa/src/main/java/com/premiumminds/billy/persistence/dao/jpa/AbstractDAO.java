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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Entity;
import com.premiumminds.billy.persistence.entities.jpa.JPABaseEntity;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;

public abstract class AbstractDAO<TID extends Entity<TID>, TInterface extends TID,
        TEntity extends JPABaseEntity<TID> & Entity<TID>>
        implements DAO<TID, TInterface> {

    private static final Logger log = LoggerFactory.getLogger(AbstractDAO.class);

    protected Provider<EntityManager> emProvider;

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
        if (this.isTransactionActive()) {
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
    protected <T2 extends Entity> T2 checkEntity(Object candidate, Class<T2> entityClass) {
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
    protected <T2 extends Entity> List<T2> checkEntityList(List<?> candidates, Class<T2> entityClass) {
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
    public TInterface get(StringID<TID> uid) throws NoResultException {
        return (TInterface) this.getEntity(uid);
    }

    protected TEntity getEntity(StringID<TID> uid) throws NoResultException {

        TEntity result = null;
        Class<? extends TEntity> entityClass = this.getEntityClass();
        try {
            result = this.getEntityManager()
                    .createQuery("select e from " + entityClass.getCanonicalName() + " e " + "where e.uid=:uid and e" +
                                         ".active=true " + "order by e.entityVersion desc", entityClass)
                    .setParameter("uid", uid.toString())
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw new PersistenceException(e);
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
                    AbstractDAO.this.getEntityManager().merge(newVersion);

                    return AbstractDAO.this.get(entity.getUID());
                }
            }.execute();
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean exists(StringID<TID> uid) {
        Class<? extends TEntity> entityClass = this.getEntityClass();
        TEntity entity = null;
        try {
            entity = this.getEntityManager()
                    .createQuery("select e from " + entityClass.getCanonicalName() + " e " + "where e.uid=:uid",
                                 entityClass)
                    .setParameter("uid", uid.toString())
                    .getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
        return entity != null;
    }

    protected <T> JPAQuery<T> createQuery() {
        return new JPAQuery<>(this.getEntityManager(), JPQLTemplates.DEFAULT);
    }

    protected <D extends Entity<?>, D2 extends EntityPathBase<D>> D2 toDSL(Path<?> path, Class<D2> dslEntityClass) {
        try {
            return dslEntityClass.getDeclaredConstructor(Path.class).newInstance(path);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException | NoSuchMethodException | SecurityException e) {
            AbstractDAO.log.error(e.getMessage(), e);
        }

        return null;
    }

    protected abstract Class<? extends TEntity> getEntityClass();

}
