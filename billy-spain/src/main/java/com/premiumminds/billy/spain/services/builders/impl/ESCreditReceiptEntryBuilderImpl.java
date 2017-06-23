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

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.exceptions.DuplicateCreditNoteException;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditReceiptEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESProduct;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.persistence.entities.ESCreditReceiptEntryEntity;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntity;
import com.premiumminds.billy.spain.services.builders.ESCreditReceiptEntryBuilder;
import com.premiumminds.billy.spain.services.entities.ESCreditReceiptEntry;

public class ESCreditReceiptEntryBuilderImpl<TBuilder extends ESCreditReceiptEntryBuilderImpl<TBuilder, TEntry>, 
TEntry extends ESCreditReceiptEntry>
extends ESGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, DAOESCreditReceiptEntry, DAOESReceipt> implements
ESCreditReceiptEntryBuilder<TBuilder, TEntry> {

	protected static final Localizer	LOCALIZER	= new Localizer(
			"com/premiumminds/billy/core/i18n/FieldNames");

	public ESCreditReceiptEntryBuilderImpl(DAOESCreditReceiptEntry daoESCreditReceiptEntry,
			DAOESReceipt daoESReceipt,
			DAOESTax daoESTax,
			DAOESProduct daoESProduct,
			DAOESRegionContext daoESRegionContext) {
		super(daoESCreditReceiptEntry, daoESReceipt, daoESTax, daoESProduct,
				daoESRegionContext);
	}

	@NotOnUpdate
	public TBuilder setReferenceUID(UID referenceUID) {
		BillyValidator.notNull(referenceUID,
				ESCreditReceiptEntryBuilderImpl.LOCALIZER
				.getString("field.invoice_reference"));
		ESReceiptEntity i = (ESReceiptEntity) this.daoGenericInvoice
				.get(referenceUID);
		BillyValidator.found(i, ESGenericInvoiceBuilderImpl.LOCALIZER
				.getString("field.invoice_reference"));
		this.getTypeInstance().setReference(i);
		return this.getBuilder();
	}

	@NotOnUpdate
	public TBuilder setReason(String reason) {
		BillyValidator.notBlank(reason, ESCreditReceiptEntryBuilderImpl.LOCALIZER
				.getString("field.reason"));
		this.getTypeInstance().setReason(reason);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		getTypeInstance().setCreditOrDebit(CreditOrDebit.DEBIT);

		super.validateInstance();
		ESCreditReceiptEntryEntity cn = this.getTypeInstance();
		BillyValidator.mandatory(cn.getReference(),
				ESCreditReceiptEntryBuilderImpl.LOCALIZER
				.getString("field.invoice_reference"));

		BillyValidator.mandatory(cn.getReason(),
				ESCreditReceiptEntryBuilderImpl.LOCALIZER
				.getString("field.reason"));

		this.ValidateESCreditReceiptEntry(cn);
	}

	private void ValidateESCreditReceiptEntry(ESCreditReceiptEntryEntity cn) {
		if (daoEntry.checkCreditReceipt(cn.getReference()) != null) {
			throw new DuplicateCreditNoteException();
		}
	}

	@Override
	protected ESCreditReceiptEntryEntity getTypeInstance() {
		return (ESCreditReceiptEntryEntity) super.getTypeInstance();
	}

}
