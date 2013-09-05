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

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.services.builders.PTCreditNoteBuilder;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;

public class PTCreditNoteBuilderImpl<TBuilder extends PTCreditNoteBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends PTCreditNoteEntry, TDocument extends PTCreditNote>
	extends PTGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument> implements
	PTCreditNoteBuilder<TBuilder, TEntry, TDocument> {
	
	protected static final Localizer	LOCALIZER	= new Localizer(
			"com/premiumminds/billy/core/i18n/FieldNames");

	public PTCreditNoteBuilderImpl(DAOPTCreditNote daoPTCreditNote,
									DAOPTBusiness daoPTBusiness,
									DAOPTCustomer daoPTCustomer,
									DAOPTSupplier daoPTSupplier) {
		super(daoPTCreditNote, daoPTBusiness, daoPTCustomer, daoPTSupplier);
	}

	@Override
	protected PTCreditNoteEntity getTypeInstance() {
		return (PTCreditNoteEntity) super.getTypeInstance();
	}
	
	@Override
	protected void validateInstance() throws BillyValidationException {
		PTCreditNoteEntity i = getTypeInstance();
		i.setCreditOrDebit(CreditOrDebit.DEBIT);
		super.validateInstance();
	}
}
