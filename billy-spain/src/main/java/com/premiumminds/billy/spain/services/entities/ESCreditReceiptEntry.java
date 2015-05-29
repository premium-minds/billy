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
package com.premiumminds.billy.spain.services.entities;

import javax.inject.Inject;

import com.premiumminds.billy.spain.persistence.dao.DAOESCreditReceiptEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESProduct;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.services.builders.impl.ESCreditReceiptEntryBuilderImpl;
import com.premiumminds.billy.spain.services.builders.impl.ESManualCreditReceiptEntryBuilderImpl;

public interface ESCreditReceiptEntry extends ESGenericInvoiceEntry {

	public static class Builder extends
		ESCreditReceiptEntryBuilderImpl<Builder, ESCreditReceiptEntry> {

		@Inject
		public Builder(DAOESCreditReceiptEntry daoESCreditReceiptEntry,
						DAOESReceipt daoESReceipt, DAOESTax daoESTax,
						DAOESProduct daoESProduct,
						DAOESRegionContext daoESRegionContext) {
			super(daoESCreditReceiptEntry, daoESReceipt, daoESTax, daoESProduct,
					daoESRegionContext);
		}
	}
	
	public static class ManualBuilder extends
		ESManualCreditReceiptEntryBuilderImpl<ManualBuilder, ESCreditReceiptEntry> {
	
		@Inject
		public ManualBuilder(DAOESCreditReceiptEntry daoESCreditReceiptEntry,
				DAOESReceipt daoESReceipt, DAOESTax daoESTax,
				DAOESProduct daoESProduct,
				DAOESRegionContext daoESRegionContext) {
			super(daoESCreditReceiptEntry, daoESReceipt, daoESTax, daoESProduct,
					daoESRegionContext);
		}
	}
	

	public ESReceipt getReference();

	public String getReason();
}
