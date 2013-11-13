/**
 * Copyright (C) 2013 Premium Minds.
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.premiumminds.billy.core.persistence.dao.jpa.DAOTaxImpl;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.persistence.entities.ESRegionContextEntity;
import com.premiumminds.billy.spain.persistence.entities.ESTaxEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESRegionContextEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.JPAESTaxEntity;
import com.premiumminds.billy.spain.persistence.entities.jpa.QJPAESRegionContextEntity;
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
	public List<JPAESTaxEntity> getTaxesForSAFTES(
			ESRegionContextEntity context, Date validFrom, Date validTo) {
		QJPAESTaxEntity tax = QJPAESTaxEntity.jPAESTaxEntity;
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
		List<JPAESTaxEntity> list = query.list(tax);
		List<JPAESRegionContextEntity> childContexts = null;
		List<JPAESTaxEntity> taxResult = null;
		List<JPAESTaxEntity> taxContextResult = new ArrayList<JPAESTaxEntity>();

		for (JPAESTaxEntity t : list) {
			childContexts = this.getChildContexts((ESRegionContextEntity) t
					.getContext());
			for (JPAESRegionContextEntity c : childContexts) {
				taxResult = this.getTaxesForSAFTES(c, validFrom, validTo);
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

	private List<JPAESRegionContextEntity> getChildContexts(
			ESRegionContextEntity parentContext) {
		QJPAESRegionContextEntity contexts = QJPAESRegionContextEntity.jPAESRegionContextEntity;
		JPAQuery query = new JPAQuery(this.getEntityManager());

		query.from(contexts).where(contexts.parent.eq(parentContext));
		return query.list(contexts);
	}

	@Override
	public List<JPAESTaxEntity> getTaxes(ESRegionContextEntity context,
			Date validFrom, Date validTo) {

		QJPAESTaxEntity tax = QJPAESTaxEntity.jPAESTaxEntity;
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

		List<JPAESTaxEntity> list = null;
		list = query.list(tax);
		if (context.getParentContext() != null) {
			list.addAll(this.getTaxes(
					(ESRegionContextEntity) context.getParentContext(),
					validFrom, validTo));
		}
		return list;
	}
}
