/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy platypus (PT Pack).
 * 
 * billy platypus (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy platypus (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNoteEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.services.builders.PTCreditNoteEntryBuilder;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;

public class PTCreditNoteEntryBuilderImpl<TBuilder extends PTCreditNoteEntryBuilderImpl<TBuilder, TEntry>, TEntry extends PTCreditNoteEntry>
		extends PTInvoiceEntryBuilderImpl<TBuilder, TEntry> implements
		PTCreditNoteEntryBuilder<TBuilder, TEntry> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/portugal/i18n/FieldNames");

	public PTCreditNoteEntryBuilderImpl(
			DAOPTCreditNoteEntry daoPTCreditNoteEntry,
			DAOPTInvoice daoPTInvoice, DAOPTTax daoPTTax,
			DAOPTProduct daoPTProduct, DAOPTRegionContext daoPTRegionContext) {
		super(daoPTCreditNoteEntry, daoPTInvoice, daoPTTax, daoPTProduct,
				daoPTRegionContext);
	}

	public TBuilder setReference(String reference) {
		BillyValidator.mandatory(reference,
				LOCALIZER.getString("field.reference"));
		this.getTypeInstance().setReference(reference);
		return this.getBuilder();
	}

	public TBuilder setReason(String reason) {
		BillyValidator
				.mandatory(reason, LOCALIZER.getString("field.reference"));
		this.getTypeInstance().setReason(reason);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		super.validateInstance();
		PTCreditNoteEntryEntity cn = this.getTypeInstance();
		BillyValidator.mandatory(cn.getReference(),
				LOCALIZER.getString("field.reference"));

		BillyValidator.mandatory(cn.getReason(),
				LOCALIZER.getString("field.reason"));
	}

	@Override
	protected PTCreditNoteEntryEntity getTypeInstance() {
		return (PTCreditNoteEntryEntity) super.getTypeInstance();
	}

}
