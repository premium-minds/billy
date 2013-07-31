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

import java.util.Date;

import javax.inject.Inject;

import com.google.inject.Injector;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.documents.IssuingParams;
import com.premiumminds.billy.core.services.documents.impl.DocumentIssuingHandlerImpl;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.exceptions.InvalidInvoiceDateException;
import com.premiumminds.billy.portugal.services.documents.exceptions.InvalidInvoiceTypeException;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.util.GenerateHash;

public class PTInvoiceIssuingHandler extends DocumentIssuingHandlerImpl
		implements DocumentIssuingHandler {

	public final static TYPE INVOICE_TYPE = TYPE.FT;
	public final static String SOURCE_BILLING = "P";

	@Inject
	public PTInvoiceIssuingHandler(Injector injector) {
		super(injector);
	}

	@Override
	public <T extends GenericInvoice> T issue(final T document,
			IssuingParams parameters) throws DocumentIssuingException {

		final PTIssuingParams parametersPT = (PTIssuingParams) parameters;

		final DAOPTInvoice daoInvoice = this.injector
				.getInstance(DAOPTInvoice.class);

		try {
			return new TransactionWrapper<T>(daoInvoice) {

				@Override
				public T runTransaction() throws Exception {
					TYPE documentType = ((PTGenericInvoice) document).getType();
					Date invoiceDate = document.getDate();
					Date systemDate = document.getCreateTimestamp();
					String series = parametersPT.getInvoiceSeries();
					Integer seriesNumber;
					String previousHash;

					if (systemDate.after(invoiceDate)) {
						throw new InvalidInvoiceDateException();
					}

					try {
						PTInvoiceEntity latestInvoice = daoInvoice
								.getLatestInvoiceFromSeries(series);
						Date latestInvoiceDate = latestInvoice.getDate();

						if (documentType != TYPE.FT) {
							throw new InvalidInvoiceTypeException(series,
									documentType.toString(),
									INVOICE_TYPE.toString());
						}

						if (latestInvoiceDate.after(invoiceDate)) {
							invoiceDate
									.setTime(latestInvoiceDate.getTime() + 100);
						}

						seriesNumber = latestInvoice.getSeriesNumber() + 1;
						previousHash = latestInvoice.getHash();
					} catch (DocumentIssuingException e) {
						throw e;
					} catch (BillyRuntimeException bre) {
						previousHash = null;
						seriesNumber = 1;
					}

					String formatedNumber = INVOICE_TYPE.toString() + " "
							+ parametersPT.getInvoiceSeries() + "/"
							+ seriesNumber;

					String newHash = GenerateHash.generateHash(
							parametersPT.getPrivateKey(),
							parametersPT.getPublicKey(), invoiceDate,
							systemDate, formatedNumber,
							document.getAmountWithTax(), previousHash);

					PTInvoiceEntity documentEntity = (PTInvoiceEntity) document;

					documentEntity.setNumber(formatedNumber);
					documentEntity.setSeries(series);
					documentEntity.setSeriesNumber(seriesNumber);
					documentEntity.setHash(newHash);
					documentEntity.setBilled(true);
					documentEntity.setType(TYPE.FT);
					documentEntity.setSourceHash(GenerateHash
							.generateSourceHash(invoiceDate, systemDate,
									formatedNumber,
									document.getAmountWithTax(), previousHash));
					documentEntity.setSourceBilling(SOURCE_BILLING);

					daoInvoice.create(documentEntity);

					return (T) documentEntity;
				}

			}.execute();
		} catch (DocumentIssuingException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DocumentIssuingException(e);
		}
	}
}
