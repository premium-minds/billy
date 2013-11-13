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

import java.math.BigDecimal;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.spain.exceptions.BillySimpleInvoiceException;
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.dao.DAOESSimpleInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESSupplier;
import com.premiumminds.billy.spain.persistence.entities.ESSimpleInvoiceEntity;
import com.premiumminds.billy.spain.services.builders.ESSimpleInvoiceBuilder;
import com.premiumminds.billy.spain.services.entities.ESInvoiceEntry;
import com.premiumminds.billy.spain.services.entities.ESSimpleInvoice;
import com.premiumminds.billy.spain.services.entities.ESSimpleInvoice.CLIENTTYPE;

public class ESSimpleInvoiceBuilderImpl<TBuilder extends ESSimpleInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends ESInvoiceEntry, TDocument extends ESSimpleInvoice>
		extends ESInvoiceBuilderImpl<TBuilder, TEntry, TDocument> implements
		ESSimpleInvoiceBuilder<TBuilder, TEntry, TDocument> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/core/i18n/FieldNames");

	@Inject
	public ESSimpleInvoiceBuilderImpl(DAOESSimpleInvoice daoESSimpleInvoice,
			DAOESBusiness daoESBusiness, DAOESCustomer daoESCustomer,
			DAOESSupplier daoESSupplier) {
		super(daoESSimpleInvoice, daoESBusiness, daoESCustomer, daoESSupplier);
	}

	@Override
	public TBuilder setClientType(CLIENTTYPE type) {
		BillyValidator.mandatory(type, ESGenericInvoiceBuilderImpl.LOCALIZER
				.getString("field.clientType"));
		this.getTypeInstance().setClientType(type);
		return this.getBuilder();
	}

	@Override
	protected ESSimpleInvoiceEntity getTypeInstance() {
		return (ESSimpleInvoiceEntity) super.getTypeInstance();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		ESSimpleInvoiceEntity i = this.getTypeInstance();
		BillyValidator.mandatory(i.getClientType(),
				ESGenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.clientType"));
		super.validateInstance();
		
		if (i.getClientType() == CLIENTTYPE.CUSTOMER
				&& i.getAmountWithTax().compareTo(new BigDecimal(1000)) >= 0) {
			throw new BillySimpleInvoiceException(
					"Amount > 1000 for customer simple invoice. Issue invoice");
		} else if (i.getClientType() == CLIENTTYPE.BUSINESS
				&& i.getAmountWithTax().compareTo(new BigDecimal(100)) >= 0) {
			throw new BillySimpleInvoiceException(
					"Amount > 100 for business simple invoice. Issue invoice");
		}
	}

}
