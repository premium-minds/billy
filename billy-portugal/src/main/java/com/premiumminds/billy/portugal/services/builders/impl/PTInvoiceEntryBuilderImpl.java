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
package com.premiumminds.billy.portugal.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntryEntity;
import com.premiumminds.billy.portugal.services.builders.PTInvoiceEntryBuilder;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;

public class PTInvoiceEntryBuilderImpl<TBuilder extends PTInvoiceEntryBuilderImpl<TBuilder, TEntry>, TEntry extends PTInvoiceEntry>
	extends PTGenericInvoiceEntryBuilderImpl<TBuilder, TEntry> implements
	PTInvoiceEntryBuilder<TBuilder, TEntry> {

	protected static final Localizer	LOCALIZER	= new Localizer(
																	"com/premiumminds/billy/portugal/i18n/FieldNames_pt");

	@Inject
	public PTInvoiceEntryBuilderImpl(DAOPTInvoiceEntry daoPTEntry,
										DAOPTInvoice daoPTInvoice,
										DAOPTTax daoPTTax,
										DAOPTProduct daoPTProduct,
										DAOPTRegionContext daoPTRegionContext) {
		super(daoPTEntry, daoPTInvoice, daoPTTax, daoPTProduct,
				daoPTRegionContext);
	}

	@Override
	protected PTInvoiceEntryEntity getTypeInstance() {
		return (PTInvoiceEntryEntity) super.getTypeInstance();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		super.validateInstance();
	}

}
