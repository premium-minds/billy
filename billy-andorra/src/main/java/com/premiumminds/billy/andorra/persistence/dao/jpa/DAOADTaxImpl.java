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

import com.premiumminds.billy.andorra.persistence.entities.ADRegionContextEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADTaxEntity;
import com.premiumminds.billy.andorra.persistence.entities.jpa.QJPAADTaxEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.persistence.EntityManager;

import com.premiumminds.billy.persistence.dao.jpa.DAOTaxImpl;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;
import com.premiumminds.billy.andorra.persistence.entities.jpa.JPAADTaxEntity;

public class DAOADTaxImpl extends DAOTaxImpl implements DAOADTax {

    @Inject
    public DAOADTaxImpl(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    @Override
    public ADTaxEntity getEntityInstance() {
        return new JPAADTaxEntity();
    }

    @Override
    protected Class<JPAADTaxEntity> getEntityClass() {
        return JPAADTaxEntity.class;
    }

    @Override
    public List<JPAADTaxEntity> getTaxes(ADRegionContextEntity context, Date validFrom, Date validTo) {

        QJPAADTaxEntity tax = QJPAADTaxEntity.jPAADTaxEntity;
        JPAQuery<JPAADTaxEntity> query = new JPAQuery<>(this.getEntityManager());

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

        List<JPAADTaxEntity> list = query.select(tax).fetch();
        if (context.getParentContext() != null) {
            list.addAll(this.getTaxes(context.getParentContext(), validFrom, validTo));
        }
        return list;
    }

    @Override
    public List<JPAADTaxEntity> getTaxes(ADRegionContextEntity context, String code, Date validFrom, Date validTo) {
        QJPAADTaxEntity tax = QJPAADTaxEntity.jPAADTaxEntity;
        JPAQuery<JPAADTaxEntity> query = new JPAQuery<>(this.getEntityManager());

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

        List<JPAADTaxEntity> list = query.select(tax).fetch();
        if (context.getParentContext() != null) {
            list.addAll(this.getTaxes(context.getParentContext(), code, validFrom, validTo));
        }
        return list;
    }
}
