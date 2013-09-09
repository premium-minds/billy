/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.export.pdf.creditnote;

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
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;
import com.premiumminds.billy.portugal.services.export.pdf.PTTemplateBundle;

public class PTCreditNotePDFExportHandler extends AbstractPDFExportHandler {

	protected static class PTParamKeys {

		public static final String	CN_HASH						= "hash";
		public static final String	SOFTWARE_CERTIFICATE_NUMBER	= "certificateNumber";
		public static final String	INVOICE						= "invoice";
	}

	private DAOPTCreditNote	daoPTCreditNote;
	private Config			config;

	@Inject
	public PTCreditNotePDFExportHandler(DAOPTCreditNote daoPTCreditNote) {
		super(daoPTCreditNote);
		this.daoPTCreditNote = daoPTCreditNote;
		this.config = new Config();
	}

	public File toFile(URI fileURI, PTCreditNoteEntity creditNote,
			PTCreditNoteTemplateBundle bundle) throws ExportServiceException {
		return super.toFile(fileURI, bundle.getXSLTFileStream(),
				this.mapDocumentToParamsTree(creditNote, bundle), bundle);
	}

	protected void toStream(PTCreditNoteEntity creditNote,
			OutputStream targetStream, PTCreditNoteTemplateBundle bundle)
		throws ExportServiceException {
		super.getStream(bundle.getXSLTFileStream(),
				this.mapDocumentToParamsTree(creditNote, bundle), targetStream,
				bundle);
	}

	protected ParamsTree<String, String> mapDocumentToParamsTree(
			PTCreditNoteEntity creditNote, PTCreditNoteTemplateBundle bundle) {
		ParamKeys.ROOT = "creditnote";

		ParamsTree<String, String> params = super.mapDocumentToParamsTree(
				creditNote, bundle);

		params.getRoot()
				.addChild(
						PTParamKeys.CN_HASH,
						this.getVerificationHashString(creditNote.getHash()
								.getBytes()));

		params.getRoot().addChild(PTParamKeys.SOFTWARE_CERTIFICATE_NUMBER,
				bundle.getSoftwareCertificationId());

		return params;
	}

	@Override
	protected <T extends GenericInvoiceEntity> void setEntries(
			TaxTotals taxTotals, Node<String, String> entries, T document) {
		List<PTCreditNoteEntryEntity> creditNoteList = document.getEntries();
		for (PTCreditNoteEntry entry : creditNoteList) {

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

			Collection<PTTaxEntity> list = entry.getTaxes();
			for (PTTaxEntity tax : list) {
				entryNode
						.addChild(
								ParamKeys.ENTRY_TAX,
								tax.getValue()
										+ (tax.getTaxRateType() == Tax.TaxRateType.PERCENTAGE ? "%"
												: "&#8364;"));
				taxTotals
						.add((tax.getTaxRateType() == Tax.TaxRateType.PERCENTAGE ? true
								: false), tax.getValue(), entry
								.getAmountWithoutTax(), entry.getTaxAmount(), tax.getUID().toString());
			}
			entryNode.addChild(PTParamKeys.INVOICE).addChild(ParamKeys.ID,
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

		if (!(request instanceof PTCreditNotePDFExportRequest)) {
			throw new ExportServiceException("Cannot handle request of type "
					+ request.getClass().getCanonicalName());
		}
		PTCreditNotePDFExportRequest exportRequest = (PTCreditNotePDFExportRequest) request;
		UID docUid = exportRequest.getDocumentUID();

		try {
			PTCreditNoteEntity creditNote = (PTCreditNoteEntity) this.daoPTCreditNote
					.get(docUid);
			this.toStream(creditNote, targetStream, exportRequest.getBundle());
		} catch (Exception e) {
			throw new ExportServiceException(e);
		}
	}

	@Override
	public <T extends BillyTemplateBundle, K extends GenericInvoiceEntity> String getCustomerFinancialId(
			K invoice, T bundle) {
		PTTemplateBundle template = (PTTemplateBundle) bundle;
		return (invoice.getCustomer().getUID()
				.equals(this.config.getUUID(Config.Key.Customer.Generic.UUID)) ? template
				.getGenericCustomer() : invoice.getCustomer()
				.getTaxRegistrationNumber());
	}
}
