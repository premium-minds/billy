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
package com.premiumminds.billy.portugal.services.export.pdf.creditnote;

import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.postgresql.util.Base64;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.gin.services.ExportServiceHandler;
import com.premiumminds.billy.gin.services.ExportServiceRequest;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.export.ParamsTree.Node;
import com.premiumminds.billy.gin.services.export.pdf.AbstractPDFHandler;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;

public class PTCreditNotePDFExportHandler extends AbstractPDFHandler implements
		ExportServiceHandler {

	private interface ParamKeys {

		public static final String ROOT = "creditnote";
		public static final String CN_ID = "id";
		public static final String CN_HASH = "hash";
		public static final String CN_EMISSION_DATE = "emissionDate";
		public static final String CN_DUE_DATE = "dueDate";
		public static final String CN_TOTAL_BEFORE_TAX = "totalBeforeTax";
		public static final String CN_TOTAL_TAX = "totalTax";
		public static final String CN_TOTAL = "totalPrice";
		public static final String SOFTWARE_CERTIFICATE_NUMBER = "certificateNumber";

		public static final String INVOICE = "invoice";
		public static final String INVOICE_ID = "id";

		// public static final String INVOICE_TOTAL_BEFORE_TAX =
		// "totalBeforeTax";
		// public static final String INVOICE_TOTAL_TAX = "totalTax";
		// public static final String INVOICE_TOTAL = "totalPrice";

		public static final String BUSINESS = "business";
		public static final String BUSINESS_LOGO = "logoPath";
		public static final String BUSINESS_NAME = "name";
		public static final String BUSINESS_FINANCIAL_ID = "financialId";
		public static final String BUSINESS_ADDRESS = "address";
		public static final String BUSINESS_ADDRESS_COUNTRY = "country";
		public static final String BUSINESS_ADDRESS_DETAILS = "details";
		public static final String BUSINESS_ADDRESS_REGION = "region";
		public static final String BUSINESS_ADDRESS_POSTAL_CODE = "postalcode";
		public static final String BUSINESS_ADDRESS_CITY = "city";

		public static final String BUSINESS_CONTACTS = "contacts";
		public static final String BUSINESS_PHONE = "phNo";
		public static final String BUSINESS_FAX = "faxNo";
		public static final String BUSINESS_EMAIL = "email";

		public static final String CUSTOMER = "customer";
		public static final String CUSTOMER_ID = "id";
		public static final String CUSTOMER_NAME = "name";
		public static final String CUSTOMER_FINANCIAL_ID = "financialId";

		public static final String CUSTOMER_BILLING_ADDRESS = "address";
		public static final String CUSTOMER_BILLING_ADDRESS_COUNTRY = "country";
		public static final String CUSTOMER_BILLING_ADDRESS_DETAILS = "details";
		public static final String CUSTOMER_BILLING_ADDRESS_CITY = "city";
		public static final String CUSTOMER_BILLING_ADDRESS_REGION = "region";
		public static final String CUSTOMER_BILLING_ADDRESS_POSTAL_CODE = "postalcode";

		public static final String ENTRIES = "entries";
		public static final String ENTRY = "entry";
		public static final String ENTRY_ID = "id";
		public static final String ENTRY_DESCRIPTION = "description";
		public static final String ENTRY_QUANTITY = "qty";
		// public static final String PRODUCT_DISCOUNT = "entries";
		public static final String ENTRY_UNIT_PRICE = "unitPrice";
		public static final String ENTRY_TOTAL = "total";
		public static final String ENTRY_TAX = "tax";

		public static final String TAX_DETAILS = "taxDetails";
		public static final String TAX_DETAIL = "detail";
		public static final String TAX_DETAIL_TAX = "tax";
		public static final String TAX_DETAIL_BASE_VALUE = "baseValue";
		public static final String TAX_DETAIL_VALUE = "taxValue";
	}

	private DAOPTCreditNote daoPTCreditNote;
	private Config config;

	public PTCreditNotePDFExportHandler() {
		super();
		this.config = new Config();
	}

	@Inject
	protected void setDAOPTInvoice(DAOPTCreditNote daoPTCreditNote) {
		this.daoPTCreditNote = daoPTCreditNote;
	}

	public File toFile(PTCreditNoteEntity creditNote,
			PTCreditNoteTemplateBundle bundle) throws ExportServiceException {
		return super.toFile(bundle.getXSLTFileStream(),
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
		ParamsTree<String, String> params = new ParamsTree<String, String>(
				ParamKeys.ROOT);
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

		params.getRoot().addChild(ParamKeys.CN_ID, creditNote.getNumber());
		params.getRoot()
				.addChild(
						ParamKeys.CN_HASH,
						this.getVerificationHashString(creditNote.getHash()
								.getBytes()));
		params.getRoot().addChild(ParamKeys.SOFTWARE_CERTIFICATE_NUMBER,
				bundle.getSoftwareCertificationId());
		params.getRoot().addChild(ParamKeys.CN_EMISSION_DATE,
				date.format(creditNote.getDate()));
		if (null != creditNote.getSettlementDate()) {
			params.getRoot().addChild(ParamKeys.CN_DUE_DATE,
					date.format(creditNote.getSettlementDate()));
		}

		Node<String, String> businessInfo = params.getRoot().addChild(
				ParamKeys.BUSINESS);
		businessInfo.addChild(ParamKeys.BUSINESS_LOGO,
				bundle.getLogoImagePath());
		businessInfo.addChild(ParamKeys.BUSINESS_NAME, creditNote.getBusiness()
				.getName());
		businessInfo.addChild(ParamKeys.BUSINESS_FINANCIAL_ID, creditNote
				.getBusiness().getFinancialID());

		Node<String, String> businessAddress = businessInfo
				.addChild(ParamKeys.BUSINESS_ADDRESS);
		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_COUNTRY, creditNote
				.getBusiness().getAddress().getISOCountry());
		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_DETAILS, creditNote
				.getBusiness().getAddress().getDetails());
		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_CITY, creditNote
				.getBusiness().getAddress().getCity());
		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_REGION, creditNote
				.getBusiness().getAddress().getRegion());
		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_POSTAL_CODE,
				creditNote.getBusiness().getAddress().getPostalCode());

		Node<String, String> businessContacts = businessInfo
				.addChild(ParamKeys.BUSINESS_CONTACTS);
		businessContacts.addChild(ParamKeys.BUSINESS_PHONE,
				bundle.getBusinessPhoneContact());
		businessContacts.addChild(ParamKeys.BUSINESS_FAX,
				bundle.getBusinessFaxContact());
		businessContacts.addChild(ParamKeys.BUSINESS_EMAIL,
				bundle.getBusinessEmailContact());

		Node<String, String> customer = params.getRoot().addChild(
				ParamKeys.CUSTOMER);
		customer.addChild(ParamKeys.CUSTOMER_ID, "");
		customer.addChild(ParamKeys.CUSTOMER_NAME, creditNote.getCustomer()
				.getName());
		customer.addChild(
				ParamKeys.CUSTOMER_FINANCIAL_ID,
				(creditNote
						.getCustomer()
						.getUID()
						.equals(config
								.getUUID(Config.Key.Customer.Generic.UUID)) ? bundle
						.getGenericCustomer() : creditNote.getCustomer()
						.getTaxRegistrationNumber()));
		if (creditNote.getCustomer().getBillingAddress() != null) {
			Node<String, String> customerAddress = customer
					.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS);
			customerAddress.addChild(
					ParamKeys.CUSTOMER_BILLING_ADDRESS_COUNTRY, creditNote
							.getCustomer().getBillingAddress().getISOCountry());
			customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_DETAILS,
					creditNote.getCustomer().getBillingAddress().getDetails());
			customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_CITY,
					creditNote.getCustomer().getBillingAddress().getCity());
			customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_REGION,
					creditNote.getCustomer().getBillingAddress().getRegion());
			customerAddress.addChild(
					ParamKeys.CUSTOMER_BILLING_ADDRESS_POSTAL_CODE, creditNote
							.getCustomer().getBillingAddress().getPostalCode());
		}

		TaxTotals taxTotals = new TaxTotals();
		Node<String, String> entries = params.getRoot().addChild(
				ParamKeys.ENTRIES);
		for (PTCreditNoteEntry entry : creditNote.getEntries()) {
			Node<String, String> entryNode = entries.addChild(ParamKeys.ENTRY);
			entryNode.addChild(ParamKeys.ENTRY_ID, entry.getProduct()
					.getProductCode());
			entryNode.addChild(ParamKeys.ENTRY_DESCRIPTION, entry.getProduct()
					.getDescription());
			entryNode.addChild(ParamKeys.ENTRY_QUANTITY, entry.getQuantity()
					.setScale(2, RoundingMode.HALF_UP).toPlainString());
			entryNode.addChild(ParamKeys.ENTRY_UNIT_PRICE, entry
					.getUnitAmountWithTax().setScale(2, RoundingMode.HALF_UP)
					.toPlainString());
			entryNode.addChild(ParamKeys.ENTRY_TOTAL, entry.getAmountWithTax()
					.setScale(2, RoundingMode.HALF_UP).toPlainString());

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
								.getAmountWithoutTax(), tax.getUID().toString());
			}
			entryNode.addChild(ParamKeys.INVOICE).addChild(
					ParamKeys.INVOICE_ID, entry.getReference().getNumber());
		}

		Node<String, String> taxDetails = params.getRoot().addChild(
				ParamKeys.TAX_DETAILS);
		for (PTCreditNotePDFExportHandler.TaxTotals.TaxTotalEntry taxDetail : taxTotals
				.getEntries()) {
			Node<String, String> taxDetailNode = taxDetails
					.addChild(ParamKeys.TAX_DETAIL);
			taxDetailNode.addChild(ParamKeys.TAX_DETAIL_TAX, taxDetail
					.getTaxValue().setScale(2, RoundingMode.HALF_UP)
					.toPlainString()
					+ (taxDetail.isPercentage() ? "%" : "&#8364;"));
			taxDetailNode.addChild(ParamKeys.TAX_DETAIL_BASE_VALUE, taxDetail
					.getBaseValue().setScale(2, RoundingMode.HALF_UP)
					.toPlainString());
			taxDetailNode.addChild(ParamKeys.TAX_DETAIL_VALUE, taxDetail
					.getAppliedTaxValue().setScale(2, RoundingMode.HALF_UP)
					.toPlainString());
		}

		params.getRoot().addChild(
				ParamKeys.CN_TOTAL_BEFORE_TAX,
				creditNote.getAmountWithoutTax()
						.setScale(2, RoundingMode.HALF_UP).toPlainString());
		params.getRoot().addChild(
				ParamKeys.CN_TOTAL_TAX,
				creditNote.getTaxAmount().setScale(2, RoundingMode.HALF_UP)
						.toPlainString());
		params.getRoot().addChild(
				ParamKeys.CN_TOTAL,
				creditNote.getAmountWithTax().setScale(2, RoundingMode.HALF_UP)
						.toPlainString());
		return params;
	}

	private String getVerificationHashString(byte[] hash) {
		String hashString = Base64.encodeBytes(hash);
		String rval = hashString.substring(0, 1) + hashString.substring(10, 11)
				+ hashString.substring(20, 21) + hashString.substring(30, 31);

		return rval;
	}

	private class TaxTotals {

		Map<String, TaxTotalEntry> entries;

		private class TaxTotalEntry {

			BigDecimal baseValue;
			BigDecimal taxValue;
			Boolean percentageValued;

			public TaxTotalEntry(boolean perc, BigDecimal taxValue,
					BigDecimal baseValue) {
				this.baseValue = baseValue;
				this.taxValue = taxValue;
				this.percentageValued = perc;
			}

			public BigDecimal getBaseValue() {
				return this.baseValue;
			}

			public BigDecimal getTaxValue() {
				return this.taxValue;
			}

			public boolean isPercentage() {
				return this.percentageValued;
			}

			public void addBaseValue(BigDecimal val) {
				this.baseValue = this.baseValue.add(val);
			}

			public BigDecimal getAppliedTaxValue() {
				BigDecimal appliedTaxVal;

				if (percentageValued) {
					BigDecimal tax = taxValue.divide(new BigDecimal("100"));
					appliedTaxVal = baseValue.multiply(tax);
				} else {
					appliedTaxVal = taxValue;
				}
				return appliedTaxVal;
			}
		}

		public TaxTotals() {
			entries = new HashMap<String, TaxTotalEntry>();
		}

		public void add(boolean isPercentage, BigDecimal taxValue,
				BigDecimal baseValue, String taxUid) {
			TaxTotalEntry currentEntry = new TaxTotalEntry(isPercentage,
					taxValue, baseValue);
			if (entries.containsKey(taxUid)) {
				this.entries.get(taxUid).addBaseValue(baseValue);
			} else {
				entries.put(taxUid, currentEntry);
			}
		}

		public Collection<TaxTotalEntry> getEntries() {
			return entries.values();
		}
	}

	@Override
	public <T extends ExportServiceRequest> void export(T request,
			OutputStream targetStream) throws ExportServiceException {

		if (!(request instanceof PDFPTCreditNoteExportRequest)) {
			throw new ExportServiceException("Cannot handle request of type "
					+ request.getClass().getCanonicalName());
		}
		PDFPTCreditNoteExportRequest exportRequest = (PDFPTCreditNoteExportRequest) request;
		UID docUid = exportRequest.getCreditNoteUID();

		try {
			PTCreditNoteEntity creditNote = (PTCreditNoteEntity) daoPTCreditNote
					.get(docUid);
			this.toStream(creditNote, targetStream, exportRequest.getBundle());
		} catch (Exception e) {
			throw new ExportServiceException(e);
		}
	}
}
