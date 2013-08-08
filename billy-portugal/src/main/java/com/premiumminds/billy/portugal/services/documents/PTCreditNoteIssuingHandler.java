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
package com.premiumminds.billy.portugal.services.documents;

import javax.inject.Inject;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.documents.IssuingParams;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;

public class PTCreditNoteIssuingHandler extends PTGenericInvoiceIssuingHandler
		implements DocumentIssuingHandler {

	public final static TYPE INVOICE_TYPE = TYPE.NC;

	@Inject
	public PTCreditNoteIssuingHandler(Injector injector) {
		super(injector);
	}

	@Override
	public <T extends GenericInvoice, P extends IssuingParams> T issue(
			final T document, P parameters) throws DocumentIssuingException {

		final PTIssuingParams parametersPT = (PTIssuingParams) parameters;

		final DAOPTCreditNote daoInvoice = this.injector
				.getInstance(DAOPTCreditNote.class);

		return issue(document, parametersPT, daoInvoice, INVOICE_TYPE);
	}
}
