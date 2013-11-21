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

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNote;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.dao.DAOESSupplier;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntity;
import com.premiumminds.billy.spain.services.builders.ESCreditNoteBuilder;
import com.premiumminds.billy.spain.services.entities.ESCreditNote;
import com.premiumminds.billy.spain.services.entities.ESCreditNoteEntry;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoice.SourceBilling;

public class ESCreditNoteBuilderImpl<TBuilder extends ESCreditNoteBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends ESCreditNoteEntry, TDocument extends ESCreditNote>
	extends ESGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument> implements
	ESCreditNoteBuilder<TBuilder, TEntry, TDocument> {
	
	protected static final Localizer	LOCALIZER	= new Localizer(
			"com/premiumminds/billy/core/i18n/FieldNames");

	public ESCreditNoteBuilderImpl(DAOESCreditNote daoESCreditNote,
									DAOESBusiness daoESBusiness,
									DAOESCustomer daoESCustomer,
									DAOESSupplier daoESSupplier) {
		super(daoESCreditNote, daoESBusiness, daoESCustomer, daoESSupplier);
		this.setSourceBilling(SourceBilling.P);
	}

	@Override
	protected ESCreditNoteEntity getTypeInstance() {
		return (ESCreditNoteEntity) super.getTypeInstance();
	}
	
	@Override
	protected void validateInstance() throws BillyValidationException {
		ESCreditNoteEntity i = getTypeInstance();
		i.setSourceBilling(SourceBilling.P);
		i.setCreditOrDebit(CreditOrDebit.DEBIT);
		super.validateInstance();
	}
	
	@Override
	@NotOnUpdate
	public TBuilder setSourceBilling(SourceBilling sourceBilling) {
		switch (sourceBilling) {
		case P:
			return super.setSourceBilling(sourceBilling);
		case M:
		default:
			throw new BillyValidationException();
		}
	}
}
