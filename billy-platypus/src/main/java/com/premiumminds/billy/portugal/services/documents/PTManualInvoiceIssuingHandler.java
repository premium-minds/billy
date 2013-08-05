/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
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
package com.premiumminds.billy.portugal.services.documents;

import javax.inject.Inject;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.documents.IssuingParams;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.util.NotImplemented;

@Deprecated
public class PTManualInvoiceIssuingHandler extends
		PTGenericInvoiceIssuingHandler implements DocumentIssuingHandler {

	public final static String SOURCE_BILLING = "M";

	@Inject
	public PTManualInvoiceIssuingHandler(Injector injector) {
		super(injector);
	}

	@Override
	@Deprecated
	@NotImplemented
	public <T extends GenericInvoice, P extends IssuingParams> T issue(
			final T document, P parameters) throws DocumentIssuingException {

		// final PTIssuingParams parametersPT = (PTIssuingParams) parameters;
		//
		// final DAOPTManualInvoice daoInvoice = this.injector
		// .getInstance(DAOPTManualInvoice.class);
		//
		// return issue(document, parametersPT, daoInvoice,
		// parametersPT.getManualInvoiceType(), SOURCE_BILLING);
		return null;
	}
}
