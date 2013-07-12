/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy.
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.dao.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.jpa.AbstractDAO;
import com.premiumminds.billy.core.persistence.entities.TaxEntity.Unit;
import com.premiumminds.billy.core.util.TaxType;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.IPTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTTaxEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTTaxEntity.PTVatCode;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTTaxEntity;

public class DAOPTTaxImpl extends AbstractDAO<IPTTaxEntity, PTTaxEntity> implements
		DAOPTTax {

	@Inject
	public DAOPTTaxImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}
	
	@Override
	public IPTTaxEntity getPTTaxInstance(
			IPTRegionContextEntity context,
			String designation,
			TaxType type,
			BigDecimal value,
			Unit unit,
			Currency currency,
			Date validFrom,
			Date validTo,
			PTVatCode vatCode) {

		return new PTTaxEntity(
				checkEntity(context, PTRegionContextEntity.class), 
				designation, 
				type, 
				value, 
				unit, 
				currency, 
				validFrom, 
				validTo, 
				vatCode);
	}

	@Override
	public List<IPTTaxEntity> getAllTaxes() {
		List<PTTaxEntity> result = getEntityManager().createQuery(
				"select t from PTTaxEntity t " +
						"order by t.createTimestamp desc",
						PTTaxEntity.class)
						.getResultList();
		return new ArrayList<IPTTaxEntity>(result);
	}
	
	@Override
	protected Class<PTTaxEntity> getEntityClass() {
		return PTTaxEntity.class;
	}

}
