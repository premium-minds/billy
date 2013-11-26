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
package com.premiumminds.billy.spain.services.export.pdf.creditnote;

import java.io.File;
import java.io.OutputStream;
import java.net.URI;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.postgresql.util.Base64;

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
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNote;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntity;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntryEntity;
import com.premiumminds.billy.spain.persistence.entities.ESTaxEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditNoteEntry;

public class ESCreditNotePDFExportHandler extends AbstractPDFExportHandler {

	protected static class ESParamKeys {

		public static final String	CN_HASH						= "hash";
		public static final String	SOFTWARE_CERTIFICATE_NUMBER	= "certificateNumber";
		public static final String	INVOICE						= "invoice";
	}

	private DAOESCreditNote	daoESCreditNote;
	private Config			config;

	@Inject
	public ESCreditNotePDFExportHandler(DAOESCreditNote daoESCreditNote) {
		super(daoESCreditNote);
		this.daoESCreditNote = daoESCreditNote;
		this.config = new Config();
	}

	public File toFile(URI fileURI, ESCreditNoteEntity creditNote,
			ESCreditNoteTemplateBundle bundle) throws ExportServiceException {
		return super.toFile(fileURI, bundle.getXSLTFileStream(),
				this.mapDocumentToParamsTree(creditNote, bundle), bundle);
	}

	protected void toStream(ESCreditNoteEntity creditNote,
			OutputStream targetStream, ESCreditNoteTemplateBundle bundle)
		throws ExportServiceException {
		super.getStream(bundle.getXSLTFileStream(),
				this.mapDocumentToParamsTree(creditNote, bundle), targetStream,
				bundle);
	}

	protected ParamsTree<String, String> mapDocumentToParamsTree(
			ESCreditNoteEntity creditNote, ESCreditNoteTemplateBundle bundle) {
		ParamKeys.ROOT = "creditnote";

		ParamsTree<String, String> params = super.mapDocumentToParamsTree(
				creditNote, bundle);

		params.getRoot()
				.addChild(
						ESParamKeys.CN_HASH,
						this.getVerificationHashString(creditNote.getHash()
								.getBytes()));

		params.getRoot().addChild(ESParamKeys.SOFTWARE_CERTIFICATE_NUMBER,
				bundle.getSoftwareCertificationId());

		return params;
	}

	@Override
	protected <T extends GenericInvoiceEntity> void setEntries(
			TaxTotals taxTotals, Node<String, String> entries, T document) {
		List<ESCreditNoteEntryEntity> creditNoteList = document.getEntries();
		for (ESCreditNoteEntry entry : creditNoteList) {

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
								.getAmountWithoutTax(), entry.getTaxAmount(), tax.getUID().toString());
			}
			entryNode.addChild(ESParamKeys.INVOICE).addChild(ParamKeys.ID,
					entry.getReference().getNumber());
		}
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

		if (!(request instanceof ESCreditNotePDFExportRequest)) {
			throw new ExportServiceException("Cannot handle request of type "
					+ request.getClass().getCanonicalName());
		}
		ESCreditNotePDFExportRequest exportRequest = (ESCreditNotePDFExportRequest) request;
		UID docUid = exportRequest.getDocumentUID();

		try {
			ESCreditNoteEntity creditNote = (ESCreditNoteEntity) this.daoESCreditNote
					.get(docUid);
			this.toStream(creditNote, targetStream, exportRequest.getBundle());
		} catch (Exception e) {
			throw new ExportServiceException(e);
		}
	}

	@Override
	public <T extends BillyTemplateBundle, K extends GenericInvoiceEntity> String getCustomerFinancialId(
			K invoice, T bundle) {
		return (invoice.getCustomer().getTaxRegistrationNumber());
	}
}
