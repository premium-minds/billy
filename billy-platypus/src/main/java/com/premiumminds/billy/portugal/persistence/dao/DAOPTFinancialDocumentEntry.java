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
package com.premiumminds.billy.portugal.persistence.dao;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.entities.ProductEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTTaxEntity;

public interface DAOPTFinancialDocumentEntry extends DAO<IPTFinancialDocumentEntryEntity> {

	IPTFinancialDocumentEntryEntity getPTFinancialDocumentEntryInstance(
			int sequenceNumber, ProductEntity product, String description,
			Date taxPointDate, BigDecimal productQuantity,
			BigDecimal productUnitPrice, BigDecimal netTotal,
			BigDecimal taxTotal, BigDecimal discountTotal,
			BigDecimal grossTotal, Currency currency, List<IPTTaxEntity> taxes,
			IPTFinancialDocumentEntity referencedDocument,
			String exemptionReason, String orderId, Date orderDate);
	
}
