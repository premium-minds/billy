/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-platypus.
 * 
 * billy-platypus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-platypus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-platypus.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.portugal.services.export.pdf.invoice;

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

import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.persistence.entities.TaxEntity.Unit;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.gin.services.ExportServiceHandler;
import com.premiumminds.billy.gin.services.ExportServiceRequest;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.export.ParamsTree.Node;
import com.premiumminds.billy.gin.services.export.pdf.AbstractPDFHandler;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.IPTInvoiceEntity;

public class PTInvoicePDFExportHandler extends AbstractPDFHandler implements ExportServiceHandler {

	private @interface ParamKeys {
		public static final String ROOT = "invoice";
		public static final String INVOICE_ID = "id";
		public static final String INVOICE_HASH = "hash";
		public static final String SOFTWARE_CERTIFICATE_NUMBER = "certificateNumber";
		public static final String INVOICE_PAYMETHOD = "paymentMechanism";
		public static final String INVOICE_PAYSETTLEMENT = "paymentSettlement";
		public static final String INVOICE_EMISSION_DATE = "emissionDate";
		public static final String INVOICE_DUE_DATE = "dueDate";

		public static final String INVOICE_TOTAL_BEFORE_TAX = "totalBeforeTax";
		public static final String INVOICE_TOTAL_TAX = "totalTax";
		public static final String INVOICE_TOTAL = "totalPrice";

		public static final String BUSINESS = "business";
		public static final String BUSINESS_LOGO = "logoPath";
		public static final String BUSINESS_NAME = "name";
		public static final String BUSINESS_FINANCIAL_ID = "financialId";
		public static final String BUSINESS_ADDRESS = "address";
		public static final String BUSINESS_ADDRESS_COUNTRY = "country";
		public static final String BUSINESS_ADDRESS_LINE1 = "lineone";
		public static final String BUSINESS_ADDRESS_LINE2 = "linetwo";
		public static final String BUSINESS_ADDRESS_LINE3 = "linethree";
		public static final String BUSINESS_ADDRESS_CITY = "city";
		public static final String BUSINESS_ADDRESS_REGION = "region";
		public static final String BUSINESS_ADDRESS_POSTAL_CODE = "postalcode";

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
		public static final String CUSTOMER_BILLING_ADDRESS_LINE1 = "lineone";
		public static final String CUSTOMER_BILLING_ADDRESS_LINE2 = "linetwo";
		public static final String CUSTOMER_BILLING_ADDRESS_LINE3 = "linethree";
		public static final String CUSTOMER_BILLING_ADDRESS_CITY = "city";
		public static final String CUSTOMER_BILLING_ADDRESS_REGION = "region";
		public static final String CUSTOMER_BILLING_ADDRESS_POSTAL_CODE = "postalcode";

		public static final String ENTRIES = "entries";
		public static final String ENTRY = "entry";
		public static final String ENTRY_ID = "id";
		public static final String ENTRY_DESCRIPTION = "description";
		public static final String ENTRY_QUANTITY = "qty";
		//public static final String PRODUCT_DISCOUNT = "entries";
		public static final String ENTRY_UNIT_PRICE = "unitPrice";
		public static final String ENTRY_TOTAL = "total";
		public static final String ENTRY_TAX = "tax";

		public static final String TAX_DETAILS = "taxDetails";
		public static final String TAX_DETAIL = "detail";
		public static final String TAX_DETAIL_TAX = "tax";
		public static final String TAX_DETAIL_NET_VALUE = "baseValue";
		public static final String TAX_DETAIL_VALUE = "taxValue";
	}

	private DAOPTInvoice daoPTInvoice;
	private Config config;

	public PTInvoicePDFExportHandler() {
		super();
		config = new Config();
	}

	@Inject
	protected void setDAOPTInvoice(DAOPTInvoice daoPTInvoice){
		this.daoPTInvoice = daoPTInvoice;
	}

	public File toFile(IPTInvoiceEntity invoice, PTInvoiceTemplateBundle bundle) throws ExportServiceException {
		return super.toFile(bundle.getXSLTFileStream(), this.mapDocumentToParamsTree(invoice, bundle), bundle);
	}

	protected void toStream(IPTInvoiceEntity invoice, OutputStream targetStream, PTInvoiceTemplateBundle bundle) throws ExportServiceException {
		super.getStream(bundle.getXSLTFileStream(), this.mapDocumentToParamsTree(invoice, bundle), targetStream, bundle);
	}

	protected ParamsTree<String, String> mapDocumentToParamsTree(
			IPTInvoiceEntity entity, PTInvoiceTemplateBundle bundle) {
		ParamsTree<String, String> params = new ParamsTree<String, String>(ParamKeys.ROOT);
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

		params.getRoot().addChild(ParamKeys.INVOICE_ID
				, entity.getDocumentNumber());
		params.getRoot().addChild(ParamKeys.INVOICE_HASH
				, this.getVerificationHashString(entity.getHash()));
		params.getRoot().addChild(ParamKeys.SOFTWARE_CERTIFICATE_NUMBER
				, bundle.getSoftwareCertificationId());

		if(null != entity.getPaymentMechanism()) {
			params.getRoot().addChild(ParamKeys.INVOICE_PAYMETHOD
					, bundle.getPaymentMechanismTranslation(entity.getPaymentMechanism()));
		}
		if(null != entity.getSettlementDescription()) {
			params.getRoot().addChild(ParamKeys.INVOICE_PAYSETTLEMENT
					, entity.getSettlementDescription());
		}
		params.getRoot().addChild(ParamKeys.INVOICE_EMISSION_DATE
				, date.format(entity.getIssueDate()));
		if(null != entity.getSettlementDate()) {
			params.getRoot().addChild(ParamKeys.INVOICE_DUE_DATE
					, date.format(entity.getSettlementDate()));
		}


		Node<String, String> businessInfo = params.getRoot().addChild(ParamKeys.BUSINESS);
		businessInfo.addChild(ParamKeys.BUSINESS_LOGO
				, bundle.getLogoImagePath());
		businessInfo.addChild(ParamKeys.BUSINESS_NAME
				, entity.getBusiness().getName());
		businessInfo.addChild(ParamKeys.BUSINESS_FINANCIAL_ID
				, entity.getBusiness().getFinancialID());

		Node<String, String> businessAddress = businessInfo.addChild(ParamKeys.BUSINESS_ADDRESS);
		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_COUNTRY
				, entity.getBusiness().getAddress().getCountry());
		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_LINE1
				, entity.getBusiness().getAddress().getAddressLine1());
		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_LINE2
				, entity.getBusiness().getAddress().getAddressLine2());
		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_LINE3
				, entity.getBusiness().getAddress().getAddressLine3());
		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_CITY
				, entity.getBusiness().getAddress().getCity());
		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_REGION
				, entity.getBusiness().getAddress().getRegion());
		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_POSTAL_CODE
				, entity.getBusiness().getAddress().getPostalCode());

		Node<String, String> businessContacts = businessInfo.addChild(ParamKeys.BUSINESS_CONTACTS);
		businessContacts.addChild(ParamKeys.BUSINESS_PHONE
				, bundle.getBusinessPhoneContact());
		businessContacts.addChild(ParamKeys.BUSINESS_FAX
				, bundle.getBusinessFaxContact());
		businessContacts.addChild(ParamKeys.BUSINESS_EMAIL
				, bundle.getBusinessEmailContact());

		Node<String, String> customer = params.getRoot().addChild(ParamKeys.CUSTOMER);
//		if(false) {
//			customer.addChild(ParamKeys.CUSTOMER_ID
//					, bundle.getCustomerId());
//		}
		customer.addChild(ParamKeys.CUSTOMER_NAME
				, entity.getCustomer().getName());
		customer.addChild(ParamKeys.CUSTOMER_FINANCIAL_ID
				, (entity.getCustomer().getUUID().equals(config.getUUID(Config.Key.Customer.Generic.UUID)) ? bundle.getGenericCustomer() : entity.getCustomer().getFinancialID()));
		if(entity.getCustomer().getBillingAddress() != null) {
			Node<String, String> customerAddress = customer.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS);
			customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_COUNTRY
					, entity.getCustomer().getBillingAddress().getCountry());
			customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_LINE1
					, entity.getCustomer().getBillingAddress().getAddressLine1());
			customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_LINE2
					, entity.getCustomer().getBillingAddress().getAddressLine2());
			customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_LINE3
					, entity.getCustomer().getBillingAddress().getAddressLine3());
			customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_CITY
					, entity.getCustomer().getBillingAddress().getCity());
			customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_REGION
					, entity.getCustomer().getBillingAddress().getRegion());
			customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_POSTAL_CODE
					, entity.getCustomer().getBillingAddress().getPostalCode());
		}

		TaxTotals taxTotals = new TaxTotals();
		Node<String, String> entries = params.getRoot().addChild(ParamKeys.ENTRIES);
		for(GenericInvoiceEntryEntity entry : entity.getDocumentEntries()) {
			Node<String, String> entryNode = entries.addChild(ParamKeys.ENTRY);
			entryNode.addChild(ParamKeys.ENTRY_ID, entry.getProduct().getProductCode());
			entryNode.addChild(ParamKeys.ENTRY_DESCRIPTION, entry.getProduct().getDescription());
			entryNode.addChild(ParamKeys.ENTRY_QUANTITY, entry.getProductQuantity().setScale(2, RoundingMode.HALF_UP).toPlainString());
			entryNode.addChild(ParamKeys.ENTRY_UNIT_PRICE, entry.getProductUnitPrice().setScale(2, RoundingMode.HALF_UP).toPlainString());
			entryNode.addChild(ParamKeys.ENTRY_TOTAL, entry.getGrossTotal().setScale(2, RoundingMode.HALF_UP).toPlainString());
			for(TaxEntity tax : entry.getTaxes()) {
				entryNode.addChild(ParamKeys.ENTRY_TAX, 
						tax.getValue() + (tax.getUnit() == Unit.PERCENTAGE ? "%" : "&#8364;"));
				taxTotals.add((tax.getUnit() == Unit.PERCENTAGE ? true : false), tax.getValue(), entry.getNetTotal(), tax.getUUID().toString());
			}
		}

		Node<String, String> taxDetails = params.getRoot().addChild(ParamKeys.TAX_DETAILS);
		for(PTInvoicePDFExportHandler.TaxTotals.TaxTotalEntry  taxDetail : taxTotals.getEntries()) {
			Node<String, String> taxDetailNode = taxDetails.addChild(ParamKeys.TAX_DETAIL);
			taxDetailNode.addChild(ParamKeys.TAX_DETAIL_TAX, taxDetail.getTaxValue().setScale(2, RoundingMode.HALF_UP).toPlainString() + (taxDetail.isPercentage() ? "%" : "&#8364;"));
			taxDetailNode.addChild(ParamKeys.TAX_DETAIL_NET_VALUE, taxDetail.getNetValue().setScale(2, RoundingMode.HALF_UP).toPlainString());
			taxDetailNode.addChild(ParamKeys.TAX_DETAIL_VALUE, taxDetail.getAppliedTaxValue().setScale(2, RoundingMode.HALF_UP).toPlainString());
		}

		params.getRoot().addChild(ParamKeys.INVOICE_TOTAL_BEFORE_TAX
				, entity.getNetTotal().setScale(2, RoundingMode.HALF_UP).toPlainString());
		params.getRoot().addChild(ParamKeys.INVOICE_TOTAL_TAX
				, entity.getTaxTotal().setScale(2, RoundingMode.HALF_UP).toPlainString());
		params.getRoot().addChild(ParamKeys.INVOICE_TOTAL
				, entity.getGrossTotal().setScale(2, RoundingMode.HALF_UP).toPlainString());
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

	private class TaxTotals {

		Map<String, TaxTotalEntry> entries;

		private class TaxTotalEntry {
			BigDecimal baseValue;
			BigDecimal taxValue;
			Boolean percentageValued;

			public TaxTotalEntry(boolean perc, BigDecimal taxValue, BigDecimal baseValue) {
				this.baseValue = baseValue;
				this.taxValue = taxValue;
				this.percentageValued = perc;
			}

			public BigDecimal getNetValue() {
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

				if(percentageValued) {
					BigDecimal tax = taxValue.divide(new BigDecimal("100"));
					appliedTaxVal = baseValue.multiply(tax);
				}else {
					appliedTaxVal = taxValue;
				}
				return appliedTaxVal;
			}
		}

		public TaxTotals() {
			entries = new HashMap<String, TaxTotalEntry>();
		}

		public void add(boolean isPercentage, BigDecimal taxValue, BigDecimal baseValue, String taxUid) {
			TaxTotalEntry currentEntry = new TaxTotalEntry(isPercentage, taxValue, baseValue);
			if(entries.containsKey(taxUid)) {
				this.entries.get(taxUid).addBaseValue(baseValue);
			}else {
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

		if(!(request instanceof PDFPTInvoiceExportRequest)) {
			throw new ExportServiceException("Cannot handle request of type " + request.getClass().getCanonicalName());
		}
		PDFPTInvoiceExportRequest exportRequest = (PDFPTInvoiceExportRequest) request;
		UID docUid = exportRequest.getInvoiceUID();

		try {
			IPTInvoiceEntity invoice = daoPTInvoice.get(docUid.getValue());
			this.toStream(invoice, targetStream, exportRequest.getBundle());
		} catch(Exception e) {
			throw new ExportServiceException(e);
		}
	}
}
