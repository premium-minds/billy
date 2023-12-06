/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.dao.jpa;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.persistence.EntityManager;

import com.premiumminds.billy.persistence.dao.jpa.DAOTaxImpl;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTTaxEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTTaxEntity;

public class DAOPTTaxImpl extends DAOTaxImpl implements DAOPTTax {

    @Inject
    public DAOPTTaxImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public PTTaxEntity getEntityInstance() {
        return new JPAPTTaxEntity();
    }

    @Override
    protected Class<JPAPTTaxEntity> getEntityClass() {
        return JPAPTTaxEntity.class;
    }

    @Override
    public List<JPAPTTaxEntity> getTaxesForSAFTPT(PTRegionContextEntity context, Date validFrom, Date validTo) {
        QJPAPTTaxEntity tax = QJPAPTTaxEntity.jPAPTTaxEntity;
        JPAQuery<JPAPTTaxEntity> query = new JPAQuery<>(this.getEntityManager());

        List<BooleanExpression> predicates = new ArrayList<>();
        BooleanExpression active = tax.active.eq(true);
        predicates.add(active);
        BooleanExpression regionContext = tax.context.eq(context);
        predicates.add(regionContext);
        if (validFrom != null) {
            BooleanExpression dateFrom = tax.validFrom.eq(validFrom);
            predicates.add(dateFrom);
        }

        if (validTo != null) {
            BooleanExpression dateTo = tax.validTo.eq(validTo);
            predicates.add(dateTo);
        }

        query.from(tax);
        for (BooleanExpression e : predicates) {
            query.where(e);
        }
        List<JPAPTTaxEntity> list = query.select(tax).fetch();
        List<JPAPTRegionContextEntity> childContexts = null;
        List<JPAPTTaxEntity> taxResult = null;
        List<JPAPTTaxEntity> taxContextResult = new ArrayList<>();

        for (JPAPTTaxEntity t : list) {
            childContexts = this.getChildContexts((JPAPTRegionContextEntity) t.getContext());
            for (JPAPTRegionContextEntity c : childContexts) {
                taxResult = this.getTaxesForSAFTPT(c, validFrom, validTo);
                if (taxResult != null) {
                    taxContextResult.addAll(taxResult);
                }
            }
        }
        if (taxContextResult != null) {
            list.addAll(taxContextResult);
        }
        return list;
    }

    private List<JPAPTRegionContextEntity> getChildContexts(JPAPTRegionContextEntity parentContext) {
        QJPAPTRegionContextEntity contexts = QJPAPTRegionContextEntity.jPAPTRegionContextEntity;
        JPAQuery<JPAPTRegionContextEntity> query = new JPAQuery<>(this.getEntityManager());

        query.from(contexts).where(contexts.parent.eq(parentContext));
        return query.select(contexts).fetch();
    }

    @Override
    public List<JPAPTTaxEntity> getTaxes(PTRegionContextEntity context, Date validFrom, Date validTo) {

        QJPAPTTaxEntity tax = QJPAPTTaxEntity.jPAPTTaxEntity;
        JPAQuery<JPAPTTaxEntity> query = new JPAQuery<>(this.getEntityManager());

        query.from(tax);
        List<BooleanExpression> predicates = new ArrayList<>();

        predicates.add(tax.context.eq(context));
        predicates.add(tax.active.eq(true));
        if (validFrom != null) {
            predicates.add(tax.validTo.after(validFrom).or(tax.validTo.isNull()));
        }
        if (validTo != null) {
            predicates.add(tax.validFrom.before(validTo).or(tax.validFrom.isNull()));
        }

        for (BooleanExpression e : predicates) {
            query.where(e);
        }

        List<JPAPTTaxEntity> list = query.select(tax).fetch();
        if (context.getParentContext() != null) {
            list.addAll(this.getTaxes(context.getParentContext(), validFrom, validTo));
        }
        return list;
    }

    @Override
    public List<JPAPTTaxEntity> getTaxes(PTRegionContextEntity context, String code, Date validFrom, Date validTo) {
        QJPAPTTaxEntity tax = QJPAPTTaxEntity.jPAPTTaxEntity;
        JPAQuery<JPAPTTaxEntity> query = new JPAQuery<>(this.getEntityManager());

        query.from(tax);
        List<BooleanExpression> predicates = new ArrayList<>();

        predicates.add(tax.context.eq(context));
        predicates.add(tax.active.eq(true));
        predicates.add(tax.code.eq(code));
        if (validFrom != null) {
            predicates.add(tax.validTo.after(validFrom).or(tax.validTo.isNull()));
        }
        if (validTo != null) {
            predicates.add(tax.validFrom.before(validTo).or(tax.validFrom.isNull()));
        }

        for (BooleanExpression e : predicates) {
            query.where(e);
        }

        List<JPAPTTaxEntity> list = query.select(tax).fetch();
        if (context.getParentContext() != null) {
            list.addAll(this.getTaxes(context.getParentContext(), code, validFrom, validTo));
        }
        return list;
    }
}
