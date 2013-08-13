/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy GIN.
 * 
 * billy GIN is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * billy GIN is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy GIN. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.gin.services.impl.pdf;

import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;
import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.ExportServiceHandler;
import com.premiumminds.billy.gin.services.ExportServiceRequest;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyTemplateBundle;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.export.ParamsTree.Node;
import com.premiumminds.billy.gin.services.export.pdf.AbstractPDFHandler;

public abstract class AbstractPDFExportHandler extends AbstractPDFHandler
	implements ExportServiceHandler {

	protected static class ParamKeys {

		public static String		ROOT									= "invoice";

		public static final String	INVOICE_PAYMETHOD						= "paymentMechanism";
		public static final String	ID										= "id";
		public static final String	EMISSION_DATE							= "emissionDate";
		public static final String	DUE_DATE								= "dueDate";
		public static final String	TOTAL_BEFORE_TAX						= "totalBeforeTax";
		public static final String	TOTAL_TAX								= "totalTax";
		public static final String	TOTAL									= "totalPrice";

		public static final String	BUSINESS								= "business";
		public static final String	BUSINESS_LOGO							= "logoPath";
		public static final String	BUSINESS_NAME							= "name";
		public static final String	BUSINESS_FINANCIAL_ID					= "financialId";
		public static final String	BUSINESS_ADDRESS						= "address";
		public static final String	BUSINESS_ADDRESS_COUNTRY				= "country";
		public static final String	BUSINESS_ADDRESS_DETAILS				= "details";
		public static final String	BUSINESS_ADDRESS_CITY					= "city";
		public static final String	BUSINESS_ADDRESS_REGION					= "region";
		public static final String	BUSINESS_ADDRESS_POSTAL_CODE			= "postalcode";

		public static final String	BUSINESS_CONTACTS						= "contacts";
		public static final String	BUSINESS_PHONE							= "phNo";
		public static final String	BUSINESS_FAX							= "faxNo";
		public static final String	BUSINESS_EMAIL							= "email";

		public static final String	CUSTOMER								= "customer";
		public static final String	CUSTOMER_NAME							= "name";
		public static final String	CUSTOMER_FINANCIAL_ID					= "financialId";

		public static final String	CUSTOMER_BILLING_ADDRESS				= "address";
		public static final String	CUSTOMER_BILLING_ADDRESS_COUNTRY		= "country";
		public static final String	CUSTOMER_BILLING_ADDRESS_DETAILS		= "details";
		public static final String	CUSTOMER_BILLING_ADDRESS_CITY			= "city";
		public static final String	CUSTOMER_BILLING_ADDRESS_REGION			= "region";
		public static final String	CUSTOMER_BILLING_ADDRESS_POSTAL_CODE	= "postalcode";

		public static final String	ENTRIES									= "entries";
		public static final String	ENTRY									= "entry";
		public static final String	ENTRY_ID								= "id";
		public static final String	ENTRY_DESCRIPTION						= "description";
		public static final String	ENTRY_QUANTITY							= "qty";
		// public static final String PRODUCT_DISCOUNT = "entries";
		public static final String	ENTRY_UNIT_PRICE						= "unitPrice";
		public static final String	ENTRY_TOTAL								= "total";
		public static final String	ENTRY_TAX								= "tax";

		public static final String	TAX_DETAILS								= "taxDetails";
		public static final String	TAX_DETAIL								= "detail";
		public static final String	TAX_DETAIL_TAX							= "tax";
		public static final String	TAX_DETAIL_NET_VALUE					= "baseValue";
		public static final String	TAX_DETAIL_VALUE						= "taxValue";
	}

	private DAOGenericInvoice	daoGenericInvoice;
	protected MathContext		mc	= BillyMathContext.get();

	@Inject
	public AbstractPDFExportHandler(DAOGenericInvoice daoGenericInvoice) {
		this.daoGenericInvoice = daoGenericInvoice;
	}

	public File toFile(URI fileURI, GenericInvoiceEntity invoice,
			BillyTemplateBundle bundle) throws ExportServiceException {
		return super.toFile(fileURI, bundle.getXSLTFileStream(),
				this.mapDocumentToParamsTree(invoice, bundle), bundle);
	}

	protected void toStream(GenericInvoiceEntity invoice,
			OutputStream targetStream, BillyTemplateBundle bundle)
		throws ExportServiceException {
		super.getStream(bundle.getXSLTFileStream(),
				this.mapDocumentToParamsTree(invoice, bundle), targetStream,
				bundle);
	}

	protected ParamsTree<String, String> mapDocumentToParamsTree(
			GenericInvoiceEntity document, BillyTemplateBundle bundle) {

		ParamsTree<String, String> params = new ParamsTree<String, String>(
				ParamKeys.ROOT);

		TaxTotals taxTotals = new TaxTotals();

		Node<String, String> entries = params.getRoot().addChild(
				ParamKeys.ENTRIES);

		Node<String, String> taxDetails = params.getRoot().addChild(
				ParamKeys.TAX_DETAILS);

		this.setHeader(params, document, bundle);

		this.setBusiness(params, document, bundle);

		this.setCustomer(params, document, bundle);

		this.setEntries(taxTotals, entries, document);

		this.setTaxDetails(taxTotals, taxDetails);

		this.setTaxValues(params, document);

		return params;
	}

	protected <T extends BillyTemplateBundle, K extends GenericInvoiceEntity> void setHeader(
			ParamsTree<String, String> params, K document, T bundle) {
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

		params.getRoot().addChild(ParamKeys.ID, document.getNumber());

//		Enum paymentMechanism = document.getPaymentMechanism();

		if (document.getPayments() != null) {
			for(Payment p : document.getPayments()) {
			params.getRoot().addChild(
					ParamKeys.INVOICE_PAYMETHOD,
					this.getPaymentMechanismTranslation(p.getPaymentMethod(),
							bundle));
			}
		}
		
		params.getRoot().addChild(ParamKeys.EMISSION_DATE,
				date.format(document.getDate()));

		if (null != document.getSettlementDate()) {
			params.getRoot().addChild(ParamKeys.DUE_DATE,
					date.format(document.getSettlementDate()));
		}
	}

	protected void setTaxDetails(TaxTotals taxTotals,
			Node<String, String> taxDetails) {

		for (TaxTotals.TaxTotalEntry taxDetail : taxTotals.getEntries()) {

			Node<String, String> taxDetailNode = taxDetails
					.addChild(ParamKeys.TAX_DETAIL);

			taxDetailNode.addChild(ParamKeys.TAX_DETAIL_TAX, taxDetail
					.getTaxValue().setScale(2, this.mc.getRoundingMode())
					.toPlainString()
					+ (taxDetail.isPercentage() ? "%" : "&#8364;"));

			taxDetailNode.addChild(ParamKeys.TAX_DETAIL_NET_VALUE, taxDetail
					.getNetValue().setScale(2, this.mc.getRoundingMode())
					.toPlainString());

			taxDetailNode.addChild(ParamKeys.TAX_DETAIL_VALUE, taxDetail
					.getAppliedTaxValue()
					.setScale(2, this.mc.getRoundingMode()).toPlainString());
		}
		return;
	}

	protected <T extends GenericInvoiceEntity> void setEntries(
			TaxTotals taxTotals, Node<String, String> entries, T document) {

		List<GenericInvoiceEntryEntity> genericInvoiceList = document
				.getEntries();

		for (GenericInvoiceEntryEntity entry : genericInvoiceList) {

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

			List<Tax> taxList = entry.getTaxes();
			for (Tax tax : taxList) {
				entryNode
						.addChild(
								ParamKeys.ENTRY_TAX,
								tax.getValue()
										+ (tax.getTaxRateType() == TaxRateType.PERCENTAGE ? "%"
												: "&#8364;"));
				taxTotals
						.add((tax.getTaxRateType() == TaxRateType.PERCENTAGE ? true
								: false), tax.getValue(), entry
								.getAmountWithoutTax(), tax.getUID().toString());
			}
		}
	}

	protected void setBusiness(ParamsTree<String, String> params,
			GenericInvoiceEntity document, BillyTemplateBundle bundle) {
		Node<String, String> businessInfo = params.getRoot().addChild(
				ParamKeys.BUSINESS);

		businessInfo.addChild(ParamKeys.BUSINESS_LOGO,
				bundle.getLogoImagePath());

		businessInfo.addChild(ParamKeys.BUSINESS_NAME, document.getBusiness()
				.getName());

		businessInfo.addChild(ParamKeys.BUSINESS_FINANCIAL_ID, document
				.getBusiness().getFinancialID());

		Node<String, String> businessAddress = businessInfo
				.addChild(ParamKeys.BUSINESS_ADDRESS);

		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_COUNTRY, document
				.getBusiness().getAddress().getISOCountry());

		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_DETAILS, document
				.getBusiness().getAddress().getDetails());

		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_CITY, document
				.getBusiness().getAddress().getCity());

		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_REGION, document
				.getBusiness().getAddress().getRegion());

		businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_POSTAL_CODE,
				document.getBusiness().getAddress().getPostalCode());

		Node<String, String> businessContacts = businessInfo
				.addChild(ParamKeys.BUSINESS_CONTACTS);

		businessContacts.addChild(ParamKeys.BUSINESS_PHONE, document
				.getBusiness().getMainContact().getTelephone());

		businessContacts.addChild(ParamKeys.BUSINESS_FAX, document
				.getBusiness().getMainContact().getFax());

		businessContacts.addChild(ParamKeys.BUSINESS_EMAIL, document
				.getBusiness().getMainContact().getEmail());

		return;
	}

	protected void setCustomer(ParamsTree<String, String> params,
			GenericInvoiceEntity document, BillyTemplateBundle bundle) {

		Node<String, String> customer = params.getRoot().addChild(
				ParamKeys.CUSTOMER);

		customer.addChild(ParamKeys.CUSTOMER_NAME, document.getCustomer()
				.getName());

		customer.addChild(ParamKeys.CUSTOMER_FINANCIAL_ID,
				this.getCustomerFinancialId(document, bundle));

		if (document.getCustomer().getBillingAddress() != null) {
			Node<String, String> customerAddress = customer
					.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS);

			customerAddress.addChild(
					ParamKeys.CUSTOMER_BILLING_ADDRESS_COUNTRY, document
							.getCustomer().getBillingAddress().getISOCountry());

			customerAddress.addChild(
					ParamKeys.CUSTOMER_BILLING_ADDRESS_DETAILS, document
							.getCustomer().getBillingAddress().getDetails());

			customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_CITY,
					document.getCustomer().getBillingAddress().getCity());

			customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_REGION,
					document.getCustomer().getBillingAddress().getRegion());

			customerAddress.addChild(
					ParamKeys.CUSTOMER_BILLING_ADDRESS_POSTAL_CODE, document
							.getCustomer().getBillingAddress().getPostalCode());
		}
		return;
	}

	protected <T extends GenericInvoiceEntity> void setTaxValues(
			ParamsTree<String, String> params, T document) {

		params.getRoot()
				.addChild(
						ParamKeys.TOTAL_BEFORE_TAX,
						document.getAmountWithoutTax()
								.setScale(2, this.mc.getRoundingMode())
								.toPlainString());
		params.getRoot().addChild(
				ParamKeys.TOTAL_TAX,
				document.getTaxAmount().setScale(2, this.mc.getRoundingMode())
						.toPlainString());
		params.getRoot()
				.addChild(
						ParamKeys.TOTAL,
						document.getAmountWithTax()
								.setScale(2, this.mc.getRoundingMode())
								.toPlainString());
		return;
	}

	protected <T extends BillyTemplateBundle> String getPaymentMechanismTranslation(
			Enum pmc, T bundle) {
		return bundle.getPaymentMechanismTranslation(pmc);
	}

	protected abstract <T extends BillyTemplateBundle, K extends GenericInvoiceEntity> String getCustomerFinancialId(
			K document, T bundle);

	protected class TaxTotals {

		Map<String, TaxTotalEntry>	entries;

		private class TaxTotalEntry {

			BigDecimal	baseValue;
			BigDecimal	taxValue;
			Boolean		percentageValued;

			public TaxTotalEntry(boolean perc, BigDecimal taxValue,
									BigDecimal baseValue) {
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

				if (this.percentageValued) {
					BigDecimal tax = this.taxValue
							.divide(new BigDecimal("100"));
					appliedTaxVal = this.baseValue.multiply(tax);
				} else {
					appliedTaxVal = this.taxValue;
				}
				return appliedTaxVal;
			}
		}

		public TaxTotals() {
			this.entries = new HashMap<String, TaxTotalEntry>();
		}

		public void add(boolean isPercentage, BigDecimal taxValue,
				BigDecimal baseValue, String taxUid) {
			TaxTotalEntry currentEntry = new TaxTotalEntry(isPercentage,
					taxValue, baseValue);
			if (this.entries.containsKey(taxUid)) {
				this.entries.get(taxUid).addBaseValue(baseValue);
			} else {
				this.entries.put(taxUid, currentEntry);
			}
		}

		public Collection<TaxTotalEntry> getEntries() {
			return this.entries.values();
		}
	}

	public <T extends ExportServiceRequest> void export(T request,
			OutputStream targetStream) throws ExportServiceException {

		if (!(request instanceof AbstractExportRequest)) {
			throw new ExportServiceException("Cannot handle request of type "
					+ request.getClass().getCanonicalName());
		}
		AbstractExportRequest exportRequest = (AbstractExportRequest) request;
		UID docUid = exportRequest.getDocumentUID();

		try {
			GenericInvoiceEntity invoice = this.daoGenericInvoice.get(docUid);
			this.toStream(invoice, targetStream, exportRequest.getBundle());
		} catch (Exception e) {
			throw new ExportServiceException(e);
		}
	}
}
