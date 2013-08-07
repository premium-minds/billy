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
package com.premiumminds.billy.portugal.services.export.pdf.invoice;

import java.io.File;
import java.io.OutputStream;
import java.net.URI;

import javax.inject.Inject;

import org.postgresql.util.Base64;

import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.gin.services.ExportServiceRequest;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.IBillyTemplateBundle;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractPDFExportHandler;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.export.pdf.IBillyPTTemplateBundle;
import com.premiumminds.billy.portugal.util.PaymentMechanism;

public class PTInvoicePDFExportHandler extends AbstractPDFExportHandler {

	protected static class PTParamKeys {
		public static final String INVOICE_HASH = "hash";
		public static final String SOFTWARE_CERTIFICATE_NUMBER = "certificateNumber";
		public static final String BANK_TRANSFER_TEXT = "Transferência bancária";
		public static final String CASH_TEXT = "Numerário";
		public static final String CREDIT_CARD_TEXT = "Cartão crédito";
		public static final String CHECK_TEXT = "Cheque";
		public static final String DEBIT_CARD_TEXT = "Cartão débito";
		public static final String COMPENSATION_TEXT = "Compensação de saldos em conta corrente";
		public static final String COMMERCIAL_LETTER_TEXT = " Letra comercial";
		public static final String RESTAURANT_TICKET_TEXT = "Ticket restaurante";
		public static final String ATM_TEXT = "Multibanco";
		public static final String EXCHANGE_TEXT = "Permuta";
	}

	private DAOPTInvoice daoPTInvoice;
	private Config config;

	@Inject
	public PTInvoicePDFExportHandler(DAOPTInvoice daoPTInvoice) {
		super(daoPTInvoice);
		this.daoPTInvoice = daoPTInvoice;
		config = new Config();
	}

	public File toFile(URI fileURI, PTInvoiceEntity invoice, PTInvoiceTemplateBundle bundle)
			throws ExportServiceException {
		return super.toFile(fileURI, bundle.getXSLTFileStream(),
				this.mapDocumentToParamsTree(invoice, bundle), bundle);
	}

	protected void toStream(PTInvoiceEntity invoice, OutputStream targetStream,
			PTInvoiceTemplateBundle bundle) throws ExportServiceException {
		super.getStream(bundle.getXSLTFileStream(),
				this.mapDocumentToParamsTree(invoice, bundle), targetStream,
				bundle);
	}
	
	protected ParamsTree<String, String> mapDocumentToParamsTree(
			PTInvoiceEntity invoice, PTInvoiceTemplateBundle bundle) {
		
		ParamsTree<String, String> params = super.mapDocumentToParamsTree(invoice, bundle);
		params.getRoot().addChild(PTParamKeys.INVOICE_HASH,
				this.getVerificationHashString(invoice.getHash().getBytes()));
		params.getRoot().addChild(PTParamKeys.SOFTWARE_CERTIFICATE_NUMBER,
				bundle.getSoftwareCertificationId());
		return params;
	}

	private String getVerificationHashString(byte[] hash) {
		String hashString = Base64.encodeBytes(hash);
		String rval = hashString.substring(0, 1)
		 + hashString.substring(10, 11)
		 + hashString.substring(20, 21)
		 + hashString.substring(30, 31);

		return rval;
	}
	
	@Override
	public <T extends ExportServiceRequest> void export(T request,
			OutputStream targetStream) throws ExportServiceException {

		if (!(request instanceof PDFPTInvoiceExportRequest)) {
			throw new ExportServiceException("Cannot handle request of type "
					+ request.getClass().getCanonicalName());
		}
		PDFPTInvoiceExportRequest exportRequest = (PDFPTInvoiceExportRequest) request;
		UID docUid = exportRequest.getDocumentUID();

		try {
			PTInvoiceEntity invoice = (PTInvoiceEntity) daoPTInvoice
					.get(docUid);
			this.toStream(invoice, targetStream, exportRequest.getBundle());
		} catch (Exception e) {
			throw new ExportServiceException(e);
		}
	}
	
	@Override
	public <T extends IBillyTemplateBundle, K extends GenericInvoiceEntity> void setFields(
			ParamsTree<String, String> params, K document, T bundle){
		
		if (null != document.getSettlementDescription()) {
		params.getRoot().addChild(ParamKeys.INVOICE_PAYSETTLEMENT,
				document.getSettlementDescription());
		}	
		
	}
			
	@Override
	public String getPaymentMechanismTranslation(Enum<?> pmc){
			if (null == pmc) {
				return null;
			}
			PaymentMechanism payment = (PaymentMechanism) pmc;
			switch (payment) {
				case BANK_TRANSFER:
					return PTParamKeys.BANK_TRANSFER_TEXT;
				case CASH:
					return PTParamKeys.CASH_TEXT;
				case CREDIT_CARD:
					return PTParamKeys.CREDIT_CARD_TEXT;
				case CHECK:
					return PTParamKeys.CHECK_TEXT;
				case DEBIT_CARD:
					return PTParamKeys.DEBIT_CARD_TEXT;
				case COMPENSATION:
					return PTParamKeys.COMPENSATION_TEXT;
				case COMMERCIAL_LETTER:
					return PTParamKeys.COMMERCIAL_LETTER_TEXT;
				case ATM:
					return PTParamKeys.ATM_TEXT;
				case RESTAURANT_TICKET:
					return PTParamKeys.RESTAURANT_TICKET_TEXT;
				case EXCHANGE:
					return PTParamKeys.EXCHANGE_TEXT;
				default:
					return null;
			}
	}
	
	@Override
	public	<T extends IBillyTemplateBundle, K extends GenericInvoiceEntity> String getCustomerFinancialId(K invoice, T bundle){
		IBillyPTTemplateBundle template = (IBillyPTTemplateBundle) bundle;
		return (invoice.getCustomer().getUID().equals(config.getUUID(Config.Key.Customer.Generic.UUID)) ? 
				template.getGenericCustomer() : invoice.getCustomer().getTaxRegistrationNumber());
	}
}
