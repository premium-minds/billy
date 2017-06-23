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
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.spain.persistence.dao.DAOESProduct;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceiptEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntryEntity;
import com.premiumminds.billy.spain.services.builders.ESReceiptEntryBuilder;
import com.premiumminds.billy.spain.services.entities.ESReceiptEntry;

public class ESReceiptEntryBuilderImpl<TBuilder extends ESReceiptEntryBuilderImpl<TBuilder, TEntry>, TEntry extends ESReceiptEntry>
	extends ESGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, DAOESReceiptEntry, DAOESReceipt>
	implements ESReceiptEntryBuilder<TBuilder, TEntry>{

	public ESReceiptEntryBuilderImpl(DAOESReceiptEntry daoESReceiptEntry,
			DAOESReceipt daoESReceipt, 
			DAOESTax daoESTax,
			DAOESProduct daoESProduct, DAOESRegionContext daoESRegionContext) {
		super(daoESReceiptEntry, daoESReceipt, daoESTax, daoESProduct,
				daoESRegionContext);
	}
	
	@Override
	protected ESReceiptEntryEntity getTypeInstance() {
		return (ESReceiptEntryEntity) super.getTypeInstance();
	}
	
	@Override
	protected void validateInstance() throws BillyValidationException {
		getTypeInstance().setCreditOrDebit(CreditOrDebit.CREDIT);
		super.validateInstance();
	}

}
