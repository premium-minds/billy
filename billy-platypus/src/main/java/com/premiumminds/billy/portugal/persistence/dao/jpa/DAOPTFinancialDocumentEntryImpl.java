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
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.jpa.AbstractDAO;
import com.premiumminds.billy.core.persistence.entities.jpa.ProductEntity;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTFinancialDocumentEntry;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTTaxEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTFinancialDocumentEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTFinancialDocumentEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTTaxEntity;

public class DAOPTFinancialDocumentEntryImpl extends AbstractDAO<IPTFinancialDocumentEntryEntity, PTFinancialDocumentEntryEntity> implements
		DAOPTFinancialDocumentEntry {

	@Inject
	public DAOPTFinancialDocumentEntryImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	public IPTFinancialDocumentEntryEntity getPTFinancialDocumentEntryInstance(
			int sequenceNumber,
			ProductEntity product, 
			String description,
			Date taxPointDate, 
			BigDecimal productQuantity,
			BigDecimal productUnitPrice, 
			BigDecimal netTotal,
			BigDecimal taxTotal, 
			BigDecimal discountTotal,
			BigDecimal grossTotal,
			Currency currency, 
			List<IPTTaxEntity> taxes,
			IPTFinancialDocumentEntity referencedDocument,
			String exemptionReason,
			String orderId,
			Date orderDate) {

		return new PTFinancialDocumentEntryEntity(
				sequenceNumber, 
				checkEntity(product, ProductEntity.class), 
				description, 
				taxPointDate, 
				productQuantity, 
				productUnitPrice, 
				netTotal,
				taxTotal,
				discountTotal,
				grossTotal, 
				currency, 
				checkEntityList(taxes, PTTaxEntity.class),
				checkEntity(referencedDocument, PTFinancialDocumentEntity.class),
				exemptionReason,
				orderId,
				orderDate
				);	
	}

	@Override
	protected Class<PTFinancialDocumentEntryEntity> getEntityClass() {
		return PTFinancialDocumentEntryEntity.class;
	}
	

}
