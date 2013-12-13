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
package com.premiumminds.billy.spain.services.export.pdf.receipt;

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
import com.premiumminds.billy.gin.services.export.ParamsTree.Node;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractPDFExportHandler;
import com.premiumminds.billy.spain.Config;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntity;

public class ESReceiptPDFExportHandler extends AbstractPDFExportHandler {
	
	private final DAOESReceipt daoReceipt;
	private final Config config;

	@Inject
	public ESReceiptPDFExportHandler(DAOESReceipt daoReceipt) {
		super(daoReceipt);
		this.daoReceipt = daoReceipt;
		this.config = new Config();
	}
	
	public File toFile(URI fileURI, ESReceiptEntity receipt, ESReceiptTemplateBundle bundle)
		throws ExportServiceException {
		return super.toFile(fileURI, bundle.getXSLTFileStream(),
				this.mapDocumentToParamsTree(receipt, bundle), bundle);
	}
	
	@Override
	public <T extends ExportServiceRequest> void export(T request,
			OutputStream targetStream) throws ExportServiceException {

		if (!(request instanceof ESReceiptPDFExportRequest)) {
			throw new ExportServiceException("Cannot handle request of type "
					+ request.getClass().getCanonicalName());
		}
		ESReceiptPDFExportRequest exportRequest = (ESReceiptPDFExportRequest) request;
		UID docUid = exportRequest.getDocumentUID();

		try {
			ESReceiptEntity receipt = (ESReceiptEntity) this.daoReceipt
					.get(docUid);
			this.toStream(receipt, targetStream, exportRequest.getBundle());
		} catch (Exception e) {
			throw new ExportServiceException(e);
		}
	}
	
	@Override
	protected <T extends BillyTemplateBundle, K extends GenericInvoiceEntity> String getCustomerFinancialId(
			K document, T bundle) {
		return "";
	}

	protected void toStream(ESInvoiceEntity invoice, OutputStream targetStream,
			ESReceiptTemplateBundle bundle) throws ExportServiceException {
		super.getStream(bundle.getXSLTFileStream(),
				this.mapDocumentToParamsTree(invoice, bundle), targetStream,
				bundle);
	}

	protected ParamsTree<String, String> mapDocumentToParamsTree(
			ESReceiptEntity receipt, ESReceiptTemplateBundle bundle) {
		ParamKeys.ROOT = "receipt";

		ParamsTree<String, String> params = new ParamsTree<String, String>(
				ParamKeys.ROOT);

		TaxTotals taxTotals = new TaxTotals();

		Node<String, String> entries = params.getRoot().addChild(
				ParamKeys.ENTRIES);

		Node<String, String> taxDetails = params.getRoot().addChild(
				ParamKeys.TAX_DETAILS);

		super.setHeader(params, receipt, bundle);

		super.setBusiness(params, receipt, bundle);

		super.setEntries(taxTotals, entries, receipt);

		super.setTaxDetails(taxTotals, taxDetails);

		super.setTaxValues(params, taxTotals, receipt);
		
		return params;
	}
}
