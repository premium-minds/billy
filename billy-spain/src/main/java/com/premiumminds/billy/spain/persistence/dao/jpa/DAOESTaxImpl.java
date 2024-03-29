/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.persistence.dao.jpa;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.persistence.dao.jpa.DAOTaxImpl;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.persistence.entities.ESRegionContextEntity;
import com.premiumminds.billy.spain.persistence.entities.ESTaxEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESTaxEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESTaxEntity;

public class DAOESTaxImpl extends DAOTaxImpl implements DAOESTax {

    @Inject
    public DAOESTaxImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ESTaxEntity getEntityInstance() {
        return new JPAESTaxEntity();
    }

    @Override
    protected Class<JPAESTaxEntity> getEntityClass() {
        return JPAESTaxEntity.class;
    }

    @Override
    public List<JPAESTaxEntity> getTaxes(ESRegionContextEntity context, Date validFrom, Date validTo) {

        QJPAESTaxEntity tax = QJPAESTaxEntity.jPAESTaxEntity;
        JPAQuery<JPAESTaxEntity> query = new JPAQuery<>(this.getEntityManager());

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

        List<JPAESTaxEntity> list = query.select(tax).fetch();
        if (context.getParentContext() != null) {
            list.addAll(this.getTaxes(context.getParentContext(), validFrom, validTo));
        }
        return list;
    }

    @Override
    public List<JPAESTaxEntity> getTaxes(ESRegionContextEntity context, String code, Date validFrom, Date validTo) {
        QJPAESTaxEntity tax = QJPAESTaxEntity.jPAESTaxEntity;
        JPAQuery<JPAESTaxEntity> query = new JPAQuery<>(this.getEntityManager());

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

        List<JPAESTaxEntity> list = query.select(tax).fetch();
        if (context.getParentContext() != null) {
            list.addAll(this.getTaxes(context.getParentContext(), code, validFrom, validTo));
        }
        return list;
    }
}
