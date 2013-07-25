/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
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
package com.premiumminds.billy.portugal.services.builders.impl;

import java.util.Date;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceEntryBuilderImpl;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTGenericInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTGenericInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntryEntity;
import com.premiumminds.billy.portugal.services.builders.PTGenericInvoiceEntryBuilder;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoiceEntry;

public class PTGenericInvoiceEntryBuilderImpl<TBuilder extends PTGenericInvoiceEntryBuilderImpl<TBuilder, TEntry>, TEntry extends PTGenericInvoiceEntry>
		extends GenericInvoiceEntryBuilderImpl<TBuilder, TEntry> implements
		PTGenericInvoiceEntryBuilder<TBuilder, TEntry> {

	public PTGenericInvoiceEntryBuilderImpl(
			DAOPTGenericInvoiceEntry daoPTGenericInvoiceEntry,
			DAOPTGenericInvoice daoPTGenericInvoice, DAOPTTax daoPTTax,
			DAOPTProduct daoPTProduct, DAOPTRegionContext daoPTRegionContext) {
		super(daoPTGenericInvoiceEntry, daoPTGenericInvoice, daoPTTax,
				daoPTProduct, daoPTRegionContext);
	}

	@Override
	protected PTGenericInvoiceEntryEntity getTypeInstance() {
		return (PTGenericInvoiceEntryEntity) super.getTypeInstance();
	}

	@Override
	public TBuilder setTaxPointDate(Date date) {
		BillyValidator.mandatory(date, GenericInvoiceEntryBuilderImpl.LOCALIZER
				.getString("field.tax_point_date"));
		this.getTypeInstance().setTaxPointDate(date);
		return this.getBuilder();
	}

	@Override
	public TBuilder setCreditOrDebit(CreditOrDebit creditOrDebit) {
		BillyValidator.mandatory(creditOrDebit,
				GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.credit_or_debit"));
		this.getTypeInstance().setCreditOrDebit(creditOrDebit);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		super.validateInstance();
		PTGenericInvoiceEntryEntity i = this.getTypeInstance();
		BillyValidator.mandatory(i.getTaxPointDate(),
				GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.tax_point_date"));

		BillyValidator.mandatory(i.getCreditOrDebit(),
				GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.credit_or_debit"));
	}
}
