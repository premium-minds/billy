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
import com.premiumminds.billy.portugal.persistence.dao.DAOPTGenericInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.exceptions.InvalidInvoiceDateException;
import com.premiumminds.billy.portugal.services.documents.exceptions.InvalidInvoiceTypeException;
import com.premiumminds.billy.portugal.services.documents.exceptions.InvalidSourceBillingException;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.util.GenerateHash;

public abstract class PTGenericInvoiceIssuingHandler extends
		DocumentIssuingHandlerImpl implements DocumentIssuingHandler {

	@Inject
	public PTGenericInvoiceIssuingHandler(Injector injector) {
		super(injector);
	}

	protected void validateDocumentType(TYPE documentType, TYPE expectedType,
			String series) throws InvalidInvoiceTypeException {
		if (documentType != expectedType) {
			throw new InvalidInvoiceTypeException(series,
					documentType.toString(), expectedType.toString());
		}
	}

	@Override
	public abstract <T extends GenericInvoice, P extends IssuingParams> T issue(
			T document, P parameters) throws DocumentIssuingException;

	protected <T extends GenericInvoice, D extends DAOPTGenericInvoice> T issue(
			final T document, final PTIssuingParams parametersPT,
			final D daoInvoice, final TYPE invoiceType,
			final String sourceBilling) throws DocumentIssuingException {
		try {
			return new TransactionWrapper<T>(daoInvoice) {

				@Override
				public T runTransaction() throws Exception {
					PTGenericInvoiceEntity documentEntity = (PTGenericInvoiceEntity) document;
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
						PTGenericInvoiceEntity latestInvoice = daoInvoice
								.getLatestInvoiceFromSeries(series);
						Date latestInvoiceDate = latestInvoice.getDate();

						validateDocumentType(documentType, invoiceType, series);
						validateDocumentType(documentType,
								latestInvoice.getType(), series);

						if (!latestInvoice.getSourceBilling().equals(
								sourceBilling)) {
							throw new InvalidSourceBillingException(series,
									sourceBilling,
									latestInvoice.getSourceBilling());
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

					String formatedNumber = invoiceType.toString() + " "
							+ parametersPT.getInvoiceSeries() + "/"
							+ seriesNumber;

					String newHash = GenerateHash.generateHash(
							parametersPT.getPrivateKey(),
							parametersPT.getPublicKey(), invoiceDate,
							systemDate, formatedNumber,
							document.getAmountWithTax(), previousHash);

					documentEntity.setNumber(formatedNumber);
					documentEntity.setSeries(series);
					documentEntity.setSeriesNumber(seriesNumber);
					documentEntity.setHash(newHash);
					documentEntity.setBilled(true);
					documentEntity.setType(invoiceType);
					documentEntity.setSourceHash(GenerateHash
							.generateSourceHash(invoiceDate, systemDate,
									formatedNumber,
									document.getAmountWithTax(), previousHash));
					documentEntity.setSourceBilling(sourceBilling);

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
