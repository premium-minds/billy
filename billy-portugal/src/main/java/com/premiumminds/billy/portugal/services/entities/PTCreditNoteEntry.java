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
package com.premiumminds.billy.portugal.services.entities;

import javax.inject.Inject;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNoteEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.services.builders.impl.PTCreditNoteEntryBuilderImpl;
import com.premiumminds.billy.portugal.services.builders.impl.PTManualCreditNoteEntryBuilderImpl;

public interface PTCreditNoteEntry extends PTGenericInvoiceEntry {

	public static class Builder extends
		PTCreditNoteEntryBuilderImpl<Builder, PTCreditNoteEntry> {

		@Inject
		public Builder(DAOPTCreditNoteEntry daoPTCreditNoteEntry,
						DAOPTInvoice daoPTInvoice, DAOPTTax daoPTTax,
						DAOPTProduct daoPTProduct,
						DAOPTRegionContext daoPTRegionContext) {
			super(daoPTCreditNoteEntry, daoPTInvoice, daoPTTax, daoPTProduct,
					daoPTRegionContext);
		}
	}
	
	public static class ManualBuilder extends
	PTManualCreditNoteEntryBuilderImpl<ManualBuilder, PTCreditNoteEntry> {

	@Inject
	public ManualBuilder(DAOPTCreditNoteEntry daoPTCreditNoteEntry,
			DAOPTInvoice daoPTInvoice, DAOPTTax daoPTTax,
			DAOPTProduct daoPTProduct,
			DAOPTRegionContext daoPTRegionContext) {
		super(daoPTCreditNoteEntry, daoPTInvoice, daoPTTax, daoPTProduct,
				daoPTRegionContext);
	}
}

	public PTInvoice getReference();

	public String getReason();
}
