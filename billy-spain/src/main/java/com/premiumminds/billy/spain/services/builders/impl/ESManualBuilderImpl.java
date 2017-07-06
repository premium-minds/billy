/**
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
package com.premiumminds.billy.spain.services.builders.impl;

import java.math.BigDecimal;
import java.util.Currency;

import javax.validation.ValidationException;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.dao.DAOESGenericInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESSupplier;
import com.premiumminds.billy.spain.persistence.entities.ESGenericInvoiceEntity;
import com.premiumminds.billy.spain.services.builders.ESManualInvoiceBuilder;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoice;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoiceEntry;

public abstract class ESManualBuilderImpl<TBuilder extends ESManualBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends ESGenericInvoiceEntry, TDocument extends ESGenericInvoice>
extends ESGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
implements ESManualInvoiceBuilder<TBuilder, TEntry, TDocument> {

	public ESManualBuilderImpl(DAOESGenericInvoice daoESGenericInvoice,
			DAOESBusiness daoESBusiness, DAOESCustomer daoESCustomer,
			DAOESSupplier daoESSupplier) {
		super(daoESGenericInvoice, daoESBusiness, daoESCustomer, daoESSupplier);
	}

	@Override
	@NotOnUpdate
	public TBuilder setTaxAmount(BigDecimal taxAmount){
		this.getTypeInstance().setTaxAmount(taxAmount);
		return this.getBuilder();
	}

	@Override
	@NotOnUpdate
	public TBuilder setAmount(AmountType type, BigDecimal amount) {
		BillyValidator.notNull(type, ESGenericInvoiceBuilderImpl.LOCALIZER
				.getString("field.unit_amount_type"));
		BillyValidator.notNull(amount,
				ESGenericInvoiceBuilderImpl.LOCALIZER
				.getString("field.unit_gross_amount"));

		switch (type) {
		case WITH_TAX:
			this.getTypeInstance().setAmountWithTax(amount);
			break;
		case WITHOUT_TAX:
			this.getTypeInstance().setAmountWithoutTax(amount);
			break;
		}
		return this.getBuilder();
	}

	@Override
	protected void validateValues() throws ValidationException {
		GenericInvoiceEntity i = (GenericInvoiceEntity) this.getTypeInstance();
		i.setCurrency(Currency.getInstance("EUR"));

		for (GenericInvoiceEntry e : i.getEntries()) {
			if (e.getCurrency() == null) {
				GenericInvoiceEntryEntity entry = (GenericInvoiceEntryEntity) e;
				entry.setCurrency(i.getCurrency());
				e = entry;
			} else {
				BillyValidator.isTrue(i.getCurrency().getCurrencyCode()
						.equals(e.getCurrency().getCurrencyCode()));
			}
		}	
	}
	
	@Override
	protected void validateInstance() throws BillyValidationException {
		ESGenericInvoiceEntity i = (ESGenericInvoiceEntity) this.getTypeInstance();
		validateESInstance(i);
	}

}
