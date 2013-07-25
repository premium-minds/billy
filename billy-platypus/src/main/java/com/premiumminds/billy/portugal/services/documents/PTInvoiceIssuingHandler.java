/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy platypus (PT Pack).
 * 
 * billy platypus (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy platypus (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.documents;

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;

import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.documents.IssuingParams;
import com.premiumminds.billy.core.services.documents.impl.DocumentIssuingHandlerImpl;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.util.GenerateHash;

public class PTInvoiceIssuingHandler extends DocumentIssuingHandlerImpl
		implements DocumentIssuingHandler {

	public final static String INVOICE_TYPE = "FT";
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
					Date invoiceDate = document.getDate();
					Date systemDate = document.getCreateTimestamp();
					String series = parametersPT.getInvoiceSeries();

					// get from DAO this info!
					String seriesNumber = "";
					byte[] previousHash = new byte[] {};

					BigDecimal grossTotal = document.getAmountWithTax();

					String formatedNumber = PTInvoiceIssuingHandler.INVOICE_TYPE
							+ " "
							+ parametersPT.getInvoiceSeries()
							+ "/"
							+ seriesNumber;

					byte[] newHash = GenerateHash.generateHash(
							parametersPT.getPrivateKey(),
							parametersPT.getPublicKey(), invoiceDate,
							systemDate, formatedNumber, grossTotal,
							previousHash);

					PTInvoiceEntity documentEntity = (PTInvoiceEntity) document;

					documentEntity.setNumber(formatedNumber);
					documentEntity.setSeries(series);
					documentEntity.setSeriesNumber(seriesNumber);
					documentEntity.setHash(newHash.toString());
					documentEntity.setBilled(true);
					documentEntity.setSourceHash(GenerateHash
							.generateSourceHash(invoiceDate, systemDate,
									formatedNumber, grossTotal, previousHash));

					// TODO: documentEntity.setSourceBilling(SOURCE_BILLING);

					daoInvoice.create(documentEntity);

					return document;
				}

			}.execute();
		} catch (Exception e) {
			throw new DocumentIssuingException(e);
		}
	}
}
