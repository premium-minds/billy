/**
 * Copyright (C) 2013 Premium Minds.
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOTaxImpl;
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

	public List<JPAPTTaxEntity> getTaxesForSAFTPT(
			PTRegionContextEntity context, Date validFrom, Date validTo) {
		QJPAPTTaxEntity tax = QJPAPTTaxEntity.jPAPTTaxEntity;
		JPAQuery query = new JPAQuery(this.getEntityManager());

		List<BooleanExpression> predicates = new ArrayList<BooleanExpression>();
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
		List<JPAPTTaxEntity> list = query.list(tax);
		List<JPAPTRegionContextEntity> childContexts = null;
		List<JPAPTTaxEntity> taxResult = null;
		List<JPAPTTaxEntity> taxContextResult = new ArrayList<JPAPTTaxEntity>();

		for (JPAPTTaxEntity t : list) {
			childContexts = this.getChildContexts((PTRegionContextEntity) t
					.getContext());
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

	private List<JPAPTRegionContextEntity> getChildContexts(
			PTRegionContextEntity parentContext) {
		QJPAPTRegionContextEntity contexts = QJPAPTRegionContextEntity.jPAPTRegionContextEntity;
		JPAQuery query = new JPAQuery(this.getEntityManager());

		query.from(contexts).where(contexts.parent.eq(parentContext));
		return query.list(contexts);
	}

	public List<JPAPTTaxEntity> getTaxes(PTRegionContextEntity context,
			Date validFrom, Date validTo) {

		QJPAPTTaxEntity tax = QJPAPTTaxEntity.jPAPTTaxEntity;
		JPAQuery query = new JPAQuery(this.getEntityManager());

		query.from(tax);
		List<BooleanExpression> predicates = new ArrayList<BooleanExpression>();
		BooleanExpression validFromPredicate = tax.validFrom.eq(validFrom);
		predicates.add(validFromPredicate);
		BooleanExpression validToPredicate = tax.validTo.eq(validTo);
		predicates.add(validToPredicate);
		BooleanExpression lessOrEqual = tax.validTo.loe(validFrom);
		predicates.add(lessOrEqual);
		BooleanExpression active = tax.active.eq(true);
		predicates.add(active);
		BooleanExpression contextPredicate = tax.context.eq(context);
		predicates.add(contextPredicate);

		for (BooleanExpression e : predicates) {
			query.where(e);
		}

		List<JPAPTTaxEntity> list = null;
		list = query.list(tax);
		if (context.getParentContext() != null) {
			list.addAll(this.getTaxes(
					(PTRegionContextEntity) context.getParentContext(),
					validFrom, validTo));
		}
		return list;
	}
}
