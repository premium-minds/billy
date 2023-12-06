/*
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

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.persistence.EntityManager;

import com.premiumminds.billy.persistence.dao.jpa.DAOTaxImpl;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.persistence.entities.FRRegionContextEntity;
import com.premiumminds.billy.france.persistence.entities.FRTaxEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.JPAFRTaxEntity;
import com.premiumminds.billy.france.persistence.entities.jpa.QJPAFRTaxEntity;

public class DAOFRTaxImpl extends DAOTaxImpl implements DAOFRTax {

    @Inject
    public DAOFRTaxImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public FRTaxEntity getEntityInstance() {
        return new JPAFRTaxEntity();
    }

    @Override
    protected Class<JPAFRTaxEntity> getEntityClass() {
        return JPAFRTaxEntity.class;
    }

    @Override
    public List<JPAFRTaxEntity> getTaxes(FRRegionContextEntity context, Date validFrom, Date validTo) {

        QJPAFRTaxEntity tax = QJPAFRTaxEntity.jPAFRTaxEntity;
        JPAQuery<JPAFRTaxEntity> query = new JPAQuery<>(this.getEntityManager());

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

        List<JPAFRTaxEntity> list = query.select(tax).fetch();
        if (context.getParentContext() != null) {
            list.addAll(this.getTaxes(context.getParentContext(), validFrom, validTo));
        }
        return list;
    }

    @Override
    public List<JPAFRTaxEntity> getTaxes(FRRegionContextEntity context, String code, Date validFrom, Date validTo) {
        QJPAFRTaxEntity tax = QJPAFRTaxEntity.jPAFRTaxEntity;
        JPAQuery<JPAFRTaxEntity> query = new JPAQuery<>(this.getEntityManager());

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

        List<JPAFRTaxEntity> list = query.select(tax).fetch();
        if (context.getParentContext() != null) {
            list.addAll(this.getTaxes(context.getParentContext(), code, validFrom, validTo));
        }
        return list;
    }
}
