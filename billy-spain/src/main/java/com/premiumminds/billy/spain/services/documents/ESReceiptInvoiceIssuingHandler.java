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
package com.premiumminds.billy.spain.services.documents;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceiptInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;

//FIXME: Is this even used??
public class ESReceiptInvoiceIssuingHandler extends ESGenericInvoiceIssuingHandler<ESInvoiceEntity, ESIssuingParams> {

	private final DAOESReceiptInvoice	daoReceiptInvoice;

	@Inject
	public ESReceiptInvoiceIssuingHandler(DAOInvoiceSeries daoInvoiceSeries,
											DAOESReceiptInvoice daoRecepit) {
		super(daoInvoiceSeries);
		this.daoReceiptInvoice = daoRecepit;
	}

	@Override
	public ESInvoiceEntity issue(ESInvoiceEntity document, ESIssuingParams parameters) throws DocumentIssuingException {
		return this.issue(document, parameters, daoReceiptInvoice);
	}

}
