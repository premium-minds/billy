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
package com.premiumminds.billy.spain.services.export.pdf.creditreceipt;

import java.io.File;
import java.io.OutputStream;
import java.net.URI;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.gin.services.ExportServiceRequest;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyTemplateBundle;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.export.ParamsTree.Node;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractPDFExportHandler;
import com.premiumminds.billy.spain.Config;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditReceipt;
import com.premiumminds.billy.spain.persistence.entities.ESCreditReceiptEntity;
import com.premiumminds.billy.spain.persistence.entities.ESCreditReceiptEntryEntity;
import com.premiumminds.billy.spain.persistence.entities.ESTaxEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditReceiptEntry;

public class ESCreditReceiptPDFExportHandler extends AbstractPDFExportHandler {

	protected static class ESParamKeys {
		public static final String	RECEIPT						= "receipt";
	}

	private DAOESCreditReceipt	daoESCreditReceipt;
	private Config			config;

	@Inject
	public ESCreditReceiptPDFExportHandler(DAOESCreditReceipt daoESCreditReceipt) {
		super(daoESCreditReceipt);
		this.daoESCreditReceipt = daoESCreditReceipt;
		this.config = new Config();
	}

	public File toFile(URI fileURI, ESCreditReceiptEntity creditReceipt,
			ESCreditReceiptTemplateBundle bundle) throws ExportServiceException {
		return super.toFile(fileURI, bundle.getXSLTFileStream(),
				this.mapDocumentToParamsTree(creditReceipt, bundle), bundle);
	}

	protected void toStream(ESCreditReceiptEntity creditReceipt,
			OutputStream targetStream, ESCreditReceiptTemplateBundle bundle)
		throws ExportServiceException {
		super.getStream(bundle.getXSLTFileStream(),
				this.mapDocumentToParamsTree(creditReceipt, bundle), targetStream,
				bundle);
	}

	protected ParamsTree<String, String> mapDocumentToParamsTree(
			ESCreditReceiptEntity creditReceipt, ESCreditReceiptTemplateBundle bundle) {
		ParamKeys.ROOT = "creditreceipt";

		ParamsTree<String, String> params = new ParamsTree<String, String>(
				ParamKeys.ROOT);

		TaxTotals taxTotals = new TaxTotals();

		Node<String, String> entries = params.getRoot().addChild(
				ParamKeys.ENTRIES);

		Node<String, String> taxDetails = params.getRoot().addChild(
				ParamKeys.TAX_DETAILS);

		setHeader(params, creditReceipt, bundle);

		setBusiness(params, creditReceipt, bundle);

		setEntries(taxTotals, entries, creditReceipt);

		setTaxDetails(taxTotals, taxDetails);

		setTaxValues(params, taxTotals, creditReceipt);
		
		return params;
	}

	@Override
	protected <T extends GenericInvoiceEntity> void setEntries(
			TaxTotals taxTotals, Node<String, String> entries, T document) {
		List<ESCreditReceiptEntryEntity> creditReceiptList = document.getEntries();
		for (ESCreditReceiptEntry entry : creditReceiptList) {

			Node<String, String> entryNode = entries.addChild(ParamKeys.ENTRY);
			entryNode.addChild(ParamKeys.ENTRY_ID, entry.getProduct()
					.getProductCode());
			entryNode.addChild(ParamKeys.ENTRY_DESCRIPTION, entry.getProduct()
					.getDescription());
			entryNode.addChild(ParamKeys.ENTRY_QUANTITY, entry.getQuantity()
					.setScale(2, this.mc.getRoundingMode()).toPlainString());
			entryNode.addChild(
					ParamKeys.ENTRY_UNIT_PRICE,
					entry.getUnitAmountWithTax()
							.setScale(2, this.mc.getRoundingMode())
							.toPlainString());
			entryNode.addChild(ParamKeys.ENTRY_TOTAL, entry.getAmountWithTax()
					.setScale(2, this.mc.getRoundingMode()).toPlainString());

			Collection<ESTaxEntity> list = entry.getTaxes();
			for (ESTaxEntity tax : list) {
				entryNode
						.addChild(
								ParamKeys.ENTRY_TAX,
								tax.getValue().setScale(2, mc.getRoundingMode())
										+ (tax.getTaxRateType() == Tax.TaxRateType.PERCENTAGE ? "%"
												: "&#8364;"));
				taxTotals
						.add((tax.getTaxRateType() == Tax.TaxRateType.PERCENTAGE ? true
								: false), tax.getValue(), entry
								.getAmountWithoutTax(), entry.getTaxAmount(), tax.getUID().toString()
								, tax.getDesignation()
								, tax.getDescription());
			}
			entryNode.addChild(ESParamKeys.RECEIPT).addChild(ParamKeys.ID,
					entry.getReference().getNumber());
		}
	}

	@Override
	public <T extends ExportServiceRequest> void export(T request,
			OutputStream targetStream) throws ExportServiceException {

		if (!(request instanceof ESCreditReceiptPDFExportRequest)) {
			throw new ExportServiceException("Cannot handle request of type "
					+ request.getClass().getCanonicalName());
		}
		ESCreditReceiptPDFExportRequest exportRequest = (ESCreditReceiptPDFExportRequest) request;
		UID docUid = exportRequest.getDocumentUID();

		try {
			ESCreditReceiptEntity creditReceipt = (ESCreditReceiptEntity) this.daoESCreditReceipt
					.get(docUid);
			this.toStream(creditReceipt, targetStream, exportRequest.getBundle());
		} catch (Exception e) {
			throw new ExportServiceException(e);
		}
	}

	@Override
	protected <T extends BillyTemplateBundle, K extends GenericInvoiceEntity> String getCustomerFinancialId(
			K document, T bundle) {
		return "";
	}
}
