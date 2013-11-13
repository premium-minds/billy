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
package com.premiumminds.billy.spain.services.export.pdf.invoice;

import java.io.File;
import java.io.OutputStream;
import java.net.URI;

import javax.inject.Inject;

import org.postgresql.util.Base64;

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
import com.premiumminds.billy.spain.services.export.pdf.ESTemplateBundle;

public class ESInvoicePDFExportHandler extends AbstractPDFExportHandler {

	protected static class ESParamKeys {

		public static final String	INVOICE_HASH				= "hash";
		public static final String	SOFTWARE_CERTIFICATE_NUMBER	= "certificateNumber";
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

		params.getRoot().addChild(ESParamKeys.INVOICE_HASH,
				this.getVerificationHashString(invoice.getHash().getBytes()));

		params.getRoot().addChild(ESParamKeys.SOFTWARE_CERTIFICATE_NUMBER,
				bundle.getSoftwareCertificationId());
		return params;
	}

	private String getVerificationHashString(byte[] hash) {
		String hashString = Base64.encodeBytes(hash);
		String rval = hashString.substring(0, 1) + hashString.substring(10, 11)
				+ hashString.substring(20, 21) + hashString.substring(30, 31);

		return rval;
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
		ESTemplateBundle template = (ESTemplateBundle) bundle;
		return (invoice.getCustomer().getUID()
				.equals(this.config.getUUID(Config.Key.Customer.Generic.UUID)) ? template
				.getGenericCustomer() : invoice.getCustomer()
				.getTaxRegistrationNumber());
	}
}
