/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy.
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

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceEntryBuilderImpl;
import com.premiumminds.billy.portugal.services.builders.PTFinancialDocumentEntryBuilder;
import com.premiumminds.billy.portugal.services.entities.PTFinancialDocumentEntry;
import com.premiumminds.billy.portugal.services.entities.impl.PTFinancialDocumentEntryImpl;

public class PTFinancialDocumentEntryBuilderImpl<TBuilder extends PTFinancialDocumentEntryBuilderImpl<TBuilder, TDocumentEntry>, TDocumentEntry extends PTFinancialDocumentEntry> 
	extends GenericInvoiceEntryBuilderImpl<TBuilder, TDocumentEntry>
	implements PTFinancialDocumentEntryBuilder<TBuilder, TDocumentEntry> {

	public PTFinancialDocumentEntryBuilderImpl() {
		entry = new PTFinancialDocumentEntryImpl();
	}
	
	@Override
	protected PTFinancialDocumentEntryImpl getEntryImpl() {
		return (PTFinancialDocumentEntryImpl) entry;
	}

	@Override
	public TBuilder setReferencedDocumentUID(UID referenceDocumentUID) {
		getEntryImpl().setReferencedDocumentUID(referenceDocumentUID);
		return getBuilder();
	}

	@Override
	public TBuilder setExemptionLegalMotiveDescription(String exemptionMotive) {
		getEntryImpl().setExemptionLegalMotiveDescription(exemptionMotive);
		return getBuilder();
	}
	
}
