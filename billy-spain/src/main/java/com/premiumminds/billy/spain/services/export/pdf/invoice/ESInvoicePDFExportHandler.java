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
package com.premiumminds.billy.spain.services.export.pdf.invoice;

import java.io.File;
import java.io.OutputStream;
import java.net.URI;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.gin.services.ExportServiceRequest;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyTemplateBundle;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractPDFExportHandler;
import com.premiumminds.billy.spain.Config;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;

public class ESInvoicePDFExportHandler extends AbstractPDFExportHandler {

	protected static class ESParamKeys {
		public static final String	INVOICE_PAYSETTLEMENT		= "paymentSettlement";
	}

	private DAOESInvoice	daoESInvoice;
	private Config			config;

	@Inject
	public ESInvoicePDFExportHandler(DAOESInvoice daoESInvoice) {
		super(daoESInvoice);
		this.daoESInvoice = daoESInvoice;
		this.config = new Config();
	}

	public File toFile(URI fileURI, ESInvoiceEntity invoice,
			ESInvoiceTemplateBundle bundle) throws ExportServiceException {
		return super.toFile(fileURI, bundle.getXSLTFileStream(),
				this.mapDocumentToParamsTree(invoice, bundle), bundle);
	}

	protected void toStream(ESInvoiceEntity invoice, OutputStream targetStream,
			ESInvoiceTemplateBundle bundle) throws ExportServiceException {
		super.getStream(bundle.getXSLTFileStream(),
				this.mapDocumentToParamsTree(invoice, bundle), targetStream,
				bundle);
	}

	protected ParamsTree<String, String> mapDocumentToParamsTree(
			ESInvoiceEntity invoice, ESInvoiceTemplateBundle bundle) {

		ParamKeys.ROOT = "invoice";

		ParamsTree<String, String> params = super.mapDocumentToParamsTree(
				invoice, bundle);
		return params;
	}

	@Override
	public <T extends ExportServiceRequest> void export(T request,
			OutputStream targetStream) throws ExportServiceException {

		if (!(request instanceof ESInvoicePDFExportRequest)) {
			throw new ExportServiceException("Cannot handle request of type "
					+ request.getClass().getCanonicalName());
		}
		ESInvoicePDFExportRequest exportRequest = (ESInvoicePDFExportRequest) request;
		UID docUid = exportRequest.getDocumentUID();

		try {
			ESInvoiceEntity invoice = (ESInvoiceEntity) this.daoESInvoice
					.get(docUid);
			this.toStream(invoice, targetStream, exportRequest.getBundle());
		} catch (Exception e) {
			throw new ExportServiceException(e);
		}
	}

	@Override
	protected <T extends BillyTemplateBundle, K extends GenericInvoiceEntity> void setHeader(
			ParamsTree<String, String> params, K document, T bundle) {

		if (null != document.getSettlementDescription()) {
			params.getRoot().addChild(ESParamKeys.INVOICE_PAYSETTLEMENT,
					document.getSettlementDescription());

		}
		super.setHeader(params, document, bundle);

		return;
	}

	@Override
	protected <T extends BillyTemplateBundle, K extends GenericInvoiceEntity> String getCustomerFinancialId(
			K invoice, T bundle) {
		return (invoice.getCustomer().getTaxRegistrationNumber());
	}
}
