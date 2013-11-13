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
package com.premiumminds.billy.spain.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceiptInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESSupplier;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptInvoiceEntity;
import com.premiumminds.billy.spain.services.builders.ESReceiptInvoiceBuilder;
import com.premiumminds.billy.spain.services.entities.ESInvoiceEntry;
import com.premiumminds.billy.spain.services.entities.ESReceiptInvoice;


public class ESReceiptInvoiceBuilderImpl<TBuilder extends ESReceiptInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends ESInvoiceEntry, TDocument extends ESReceiptInvoice> extends
		ESInvoiceBuilderImpl<TBuilder, TEntry, TDocument> implements
		ESReceiptInvoiceBuilder<TBuilder, TEntry, TDocument> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/core/i18n/FieldNames");
	
	@Inject
	public ESReceiptInvoiceBuilderImpl(DAOESReceiptInvoice daoESReceiptInvoice,
			DAOESBusiness daoESBusiness, DAOESCustomer daoESCustomer,
			DAOESSupplier daoESSupplier) {
		super(daoESReceiptInvoice, daoESBusiness, daoESCustomer, daoESSupplier);
	}

	@Override
	protected ESReceiptInvoiceEntity getTypeInstance() {
		return (ESReceiptInvoiceEntity) super.getTypeInstance();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		super.validateInstance();
	}
}
