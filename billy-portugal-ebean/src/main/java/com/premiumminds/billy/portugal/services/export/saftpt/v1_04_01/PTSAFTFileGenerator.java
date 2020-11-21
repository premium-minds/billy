/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01;

import com.google.common.io.ByteStreams;
import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Product.ProductType;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.Config.Key;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTAddressEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTContactEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTSupplierEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.services.documents.exceptions.InvalidInvoiceTypeException;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTPayment;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.export.exceptions.InvalidContactTypeException;
import com.premiumminds.billy.portugal.services.export.exceptions.InvalidDocumentStateException;
import com.premiumminds.billy.portugal.services.export.exceptions.InvalidDocumentTypeException;
import com.premiumminds.billy.portugal.services.export.exceptions.InvalidPaymentMechanismException;
import com.premiumminds.billy.portugal.services.export.exceptions.InvalidProductTypeException;
import com.premiumminds.billy.portugal.services.export.exceptions.InvalidTaxCodeException;
import com.premiumminds.billy.portugal.services.export.exceptions.InvalidTaxTypeException;
import com.premiumminds.billy.portugal.services.export.exceptions.RequiredFieldNotFoundException;
import com.premiumminds.billy.portugal.services.export.exceptions.SAFTPTExportException;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.AddressStructure;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.AddressStructurePT;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.AuditFile;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.AuditFile.MasterFiles;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.Currency;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.Customer;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.Header;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.PaymentMethod;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.Product;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.References;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.SAFTPTSourceBilling;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.Settlement;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.ShippingPointStructure;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.SourceDocuments;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.SourceDocuments.SalesInvoices;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.SourceDocuments.SalesInvoices.Invoice;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.SourceDocuments.SalesInvoices.Invoice.DocumentStatus;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.SourceDocuments.SalesInvoices.Invoice.DocumentTotals;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.SourceDocuments.SalesInvoices.Invoice.Line;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.SpecialRegimes;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.Supplier;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.SupplierAddressStructure;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.Tax;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.TaxTable;
import com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema.TaxTableEntry;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PTSAFTFileGenerator {

	private static final Logger log = LoggerFactory.getLogger(PTSAFTFileGenerator.class);

	private Config						config					= null;
	private JAXBContext					jaxbContext;
	private Marshaller					marshaller;
	private MathContext					mc						= BillyMathContext
																		.get();

	private String						context					= null;
	private String						optionalParam;

	private static final String			FILE_ENCODING			= "UTF-8";

	private final int					MAX_LENGTH_1			= 1;
	private final int					MAX_LENGTH_2			= 2;
	private final int					MAX_LENGTH_3			= 3;
	private final int					MAX_LENGTH_5			= 5;
	private final int					MAX_LENGTH_8			= 8;
	private final int					MAX_LENGTH_9			= 9;
	private final int					MAX_LENGTH_10			= 10;
	private final int					MAX_LENGTH_12			= 12;
	private final int					MAX_LENGTH_20			= 20;
	private final int					MAX_LENGTH_30			= 30;
	private final int					MAX_LENGTH_40			= 40;
	private final int					MAX_LENGTH_50			= 50;
	private final int					MAX_LENGTH_60			= 60;
	private final int					MAX_LENGTH_90			= 90;
	private final int					MAX_LENGTH_100			= 100;
	private final int					MAX_LENGTH_172			= 172;
	private final int					MAX_LENGTH_200			= 200;
	private final int					MAX_LENGTH_255			= 255;

	private final String				XML_SCHEMA_VERSION		= "1.04_01";
	private final String				TAX_ACCOUNTING_BASIS	= "F";
	private final String				COUNTRY_CODE			= "PT";
	private final String				CURRENCY_CODE			= "EUR";
	private final String				TAX_ENTITY				= "Global";

	private final String				ACCOUNT_ID				= "Desconhecido";
	private final String				SELF_BILLING_INDICATOR	= "0";
	private final String				UNIT_OF_MEASURE			= "Unidade";

	private final DAOPTCustomer			daoCustomer;
	private final DAOPTSupplier			daoSupplier;
	private final DAOPTProduct			daoProduct;
	private final DAOPTTax				daoPTTax;
	private final DAOPTRegionContext	daoPTRegionContext;
	private final DAOPTInvoice			daoPTInvoice;
	private final DAOPTSimpleInvoice	daoPTSimpleInvoice;
	private final DAOPTReceiptInvoice	daoPTReceiptInvoice;
	private final DAOPTCreditNote		daoPTCreditNote;
	private final DAOInvoiceSeries      daoInvoiceSeries;

	@Inject
	public PTSAFTFileGenerator(
		DAOPTCustomer daoCustomer,
		DAOPTSupplier daoSupplier,
		DAOPTProduct daoProduct,
		DAOPTTax daoPTTax,
		DAOPTRegionContext daoPTRegionContext,
		DAOPTInvoice daoPTInvoice,
		DAOPTSimpleInvoice daoPTSimpleInvoice,
		DAOPTReceiptInvoice daoPTReceiptInvoice,
		DAOPTCreditNote daoPTCreditNote,
		final DAOInvoiceSeries daoInvoiceSeries) {

		this.daoCustomer = daoCustomer;
		this.daoSupplier = daoSupplier;
		this.daoProduct = daoProduct;
		this.daoPTTax = daoPTTax;
		this.daoPTRegionContext = daoPTRegionContext;
		this.daoPTInvoice = daoPTInvoice;
		this.daoPTSimpleInvoice = daoPTSimpleInvoice;
		this.daoPTReceiptInvoice = daoPTReceiptInvoice;
		this.daoPTCreditNote = daoPTCreditNote;
		this.daoInvoiceSeries = daoInvoiceSeries;

		this.config = new Config();

		try {
			this.jaxbContext = JAXBContext
					.newInstance("com.premiumminds.billy.portugal.services.export.saftpt.v1_04_01.schema");
			this.marshaller = this.jaxbContext.createMarshaller();
		} catch (Exception e) {
			log.error("Problems with JAXBContext", e);
		}
	}

	/**
	 * Constructs a new SAFT a.k.a. AuditFile
	 *
	 * @deprecated Use the overloaded method instead
	 *
	 * @param targetStream
	 *
	 * @param businessEntity - the company
	 * @param application - the software
	 * @param certificateNumber - ignored. It uses the softwareCertificationNumber in application
	 * @param fromDate - the period for the SAFT file
	 * @param toDate - the period for the SAFT file
	 * @return the SAFT for that business entity, given lists of customers,
	 *         products, taxes and financial documents; depends on a period of
	 *         time
	 * @throws SAFTPTExportException
	 */
	@Deprecated
	public AuditFile generateSAFTFile(final OutputStream targetStream,
									  final PTBusinessEntity businessEntity,
									  final PTApplicationEntity application,
									  final String certificateNumber,
									  final Date fromDate,
									  final Date toDate) throws SAFTPTExportException {
		return this.generateSAFTFile(targetStream, businessEntity, application, fromDate, toDate, false);
	}

	/**
	 * Constructs a new SAFT a.k.a. AuditFile
	 *
	 * @param targetStream
	 *
	 * @param businessEntity - the company
	 * @param application - the software
	 * @param fromDate - the period for the SAFT file
	 * @param toDate - the period for the SAFT file
	 * @return the SAFT for that business entity, given lists of customers,
	 *         products, taxes and financial documents; depends on a period of
	 *         time
	 * @throws SAFTPTExportException
	 */
	public AuditFile generateSAFTFile(final OutputStream targetStream,
									  final PTBusinessEntity businessEntity,
									  final PTApplicationEntity application,
									  final Date fromDate,
									  final Date toDate,
									  final boolean validate) throws SAFTPTExportException {

		try {
			return new TransactionWrapper<AuditFile>(daoPTInvoice) {

				@Override
				public AuditFile runTransaction() throws Exception {
					AuditFile SAFTFile = new AuditFile();

					/* HEADER */
					Header hdr = PTSAFTFileGenerator.this.generateHeader(
							businessEntity, application,
							fromDate, toDate);
					SAFTFile.setHeader(hdr);

					/* MASTER FILES */
					MasterFiles mf = new MasterFiles();

					// Customers
					@SuppressWarnings("unchecked")
					List<PTCustomerEntity> customers = (List<PTCustomerEntity>) (List<?>) daoCustomer
							.getAllActiveCustomers();
					for (PTCustomerEntity customer : customers) {
						Customer SAFTCustomer = PTSAFTFileGenerator.this
								.generateCustomer(customer);
						mf.getCustomer().add(SAFTCustomer);
					}

					// Suppliers
					@SuppressWarnings("unchecked")
					List<PTSupplierEntity> suppliers = (List<PTSupplierEntity>) (List<?>) daoSupplier
							.getAllActiveSuppliers();
					for (PTSupplierEntity sup : suppliers) {
						Supplier SAFTSupplier = PTSAFTFileGenerator.this
								.generateSupplier(sup);
						mf.getSupplier().add(SAFTSupplier);
					}

					// Products
					@SuppressWarnings("unchecked")
					List<PTProductEntity> products = (List<PTProductEntity>) (List<?>) daoProduct
							.getAllActiveProducts();
					for (PTProductEntity prod : products) {
						Product SAFTProduct = PTSAFTFileGenerator.this
								.generateProduct(prod);
						mf.getProduct().add(SAFTProduct);
					}

					PTRegionContextEntity context = (PTRegionContextEntity) daoPTRegionContext
							.get(PTSAFTFileGenerator.this.config
									.getUID(Key.Context.Portugal.UUID));
					// Taxes
					@SuppressWarnings("unchecked")
					List<PTTaxEntity> taxes = (List<PTTaxEntity>) (List<?>) daoPTTax
							.getTaxesForSAFTPT(context, null, null);
					TaxTable SAFTTaxTable = PTSAFTFileGenerator.this
							.generateTaxTable(taxes);
					mf.setTaxTable(SAFTTaxTable);
					SAFTFile.setMasterFiles(mf);

					/* SOURCE DOCUMENTS */
					List<PTInvoiceEntity> invoices = daoPTInvoice
							.getBusinessInvoicesForSAFTPT(
									businessEntity.getUID(), fromDate, toDate);

					List<PTSimpleInvoiceEntity> simpleInvoices = daoPTSimpleInvoice
							.getBusinessSimpleInvoicesForSAFTPT(
									businessEntity.getUID(), fromDate, toDate);

					List<PTReceiptInvoiceEntity> receiptInvoices = daoPTReceiptInvoice
							.getBusinessReceiptInvoicesForSAFTPT(
									businessEntity.getUID(), fromDate, toDate);

					List<PTCreditNoteEntity> creditNotes = daoPTCreditNote
							.getBusinessCreditNotesForSAFTPT(
									businessEntity.getUID(), fromDate, toDate);

					SourceDocuments sd = PTSAFTFileGenerator.this
							.generateSourceDocuments(
									invoices == null ? new ArrayList<PTInvoiceEntity>()
											: invoices,
									simpleInvoices == null ? new ArrayList<PTSimpleInvoiceEntity>()
											: simpleInvoices,
									receiptInvoices == null ? new ArrayList<PTReceiptInvoiceEntity>()
											: receiptInvoices,
									creditNotes == null ? new ArrayList<PTCreditNoteEntity>()
											: creditNotes);
					SAFTFile.setSourceDocuments(sd);

					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					exportSAFTFile(SAFTFile, outputStream);

					ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

					if (validate){
						SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
						URL url = getClass().getClassLoader().getResource("documents/SAFTPT1.04_01.xsd");
						Schema schema = sf.newSchema(url);
						Validator validator = schema.newValidator();
						validator.validate(new StreamSource(inputStream));
						inputStream.reset();
					}

					ByteStreams.copy(inputStream, targetStream);

					return SAFTFile;
				}
			}.execute();

		} catch (Exception e) {
			throw new SAFTPTExportException(e);
		}
	}

	/**
	 * Generates the SAFT XML File
	 *
	 * @param auditFile
	 *            that will be exported
	 * @param targetStream
	 */
	private void exportSAFTFile(AuditFile auditFile, OutputStream targetStream) {
		try {
			this.marshaller.setProperty(Marshaller.JAXB_ENCODING, FILE_ENCODING);
			this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			this.marshaller.marshal(auditFile, targetStream);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Generates the Header of SAFT file (Table 1)
	 *
	 * @param businessEntity - the company
	 * @param application - the software
	 * @param startDate - the period for the SAFT file
	 * @param endDate - the period for the SAFT file
	 * @return the Header
	 * @throws DatatypeConfigurationException
	 *             - problems with dates
	 * @throws RequiredFieldNotFoundException
	 *             - if a required field in the SAFT file could not be assigned
	 * @throws InvalidContactTypeException
	 */
	private Header generateHeader(PTBusinessEntity businessEntity,
			PTApplicationEntity application,
			Date startDate, Date endDate)
		throws DatatypeConfigurationException, RequiredFieldNotFoundException,
		InvalidContactTypeException {
		this.context = "Header.";

		Header hdr = new Header();
		hdr.setAuditFileVersion(this.validateString("AuditFileVersion",
				this.XML_SCHEMA_VERSION, this.MAX_LENGTH_10, true));
		hdr.setCompanyID(this.validateString("CompanyID",
				businessEntity.getFinancialID(), this.MAX_LENGTH_50, true));
		hdr.setTaxRegistrationNumber(this.validateInteger(
				"TaxRegistrationNumber", businessEntity.getFinancialID(),
				this.MAX_LENGTH_9, true));
		hdr.setTaxAccountingBasis(this.validateString("TaxAccountBasis",
				this.TAX_ACCOUNTING_BASIS, this.MAX_LENGTH_1, true));
		hdr.setCompanyName(this.validateString("CompanyName",
				businessEntity.getName(), this.MAX_LENGTH_100, true));

		if ((this.optionalParam = this.validateString("BusinessName",
				businessEntity.getCommercialName(), this.MAX_LENGTH_60, false))
				.length() > 0) {
			hdr.setBusinessName(this.optionalParam);
		}

		hdr.setCompanyAddress(this
				.generateAddressStructurePT((AddressEntity) businessEntity
						.getAddress()));

		hdr.setFiscalYear(this.getDateField(startDate, Calendar.YEAR));
		hdr.setStartDate(this.formatDate(startDate));
		hdr.setEndDate(this.formatDate(endDate));
		hdr.setCurrencyCode(this.validateString("CurrencyCode",
				this.CURRENCY_CODE, this.MAX_LENGTH_3, true));
		hdr.setDateCreated(this.getXMLGregorianCalendarNow());

		hdr.setTaxEntity(this.validateString("TaxEntity", this.TAX_ENTITY,
				this.MAX_LENGTH_20, true));
		hdr.setProductCompanyTaxID(this.validateString("ProductCompanyTaxID",
				application.getDeveloperCompanyTaxIdentifier(),
				this.MAX_LENGTH_20, true));
		hdr.setSoftwareCertificateNumber(this.validateBigInteger(
				"SoftwareCertificateNumber", application.getSoftwareCertificationNumber().toString(),
				this.MAX_LENGTH_255, true));
		hdr.setProductID(this.validateString("ProductID", application.getName()
				+ "/" + application.getDeveloperCompanyName(),
				this.MAX_LENGTH_255, true));
		hdr.setProductVersion(this.validateString("ProductVersion",
				application.getVersion(), this.MAX_LENGTH_30, true));

		List<PTContactEntity> list = businessEntity.getContacts();
		this.setContacts(hdr, list);

		return hdr;
	}

	/**
	 * Generates an instance of Customer to be inserted in the table 2.2
	 * (Customer)
	 *
	 * @param customerEntity
	 *            - the customer
	 * @return an instance of Customer
	 * @throws RequiredFieldNotFoundException
	 * @throws InvalidContactTypeException
	 */
	private Customer generateCustomer(PTCustomerEntity customerEntity)
		throws RequiredFieldNotFoundException, InvalidContactTypeException {
		this.context = "Customer.";
		Customer customer = new Customer();
		final String customerId;

		if (this.config.getUID(Key.Customer.Generic.UUID).equals(
				customerEntity.getUID())) {
			customerEntity.setTaxRegistrationNumber("999999990");
			customerId = "Consumidor final";
		} else {
			if ((this.optionalParam = this
					.validateString("Contact",
							customerEntity.getReferralName(),
							this.MAX_LENGTH_50, false)).length() > 0) {
				customer.setContact(this.optionalParam);
			}

			if (customerEntity.getShippingAddress() != null) {
				customer.getShipToAddress()
						.add(this
								.generateAddressStructure((PTAddressEntity) customerEntity
										.getShippingAddress()));
			}
			List<PTContactEntity> contacts = customerEntity.getContacts();
			this.setContacts(customer, contacts);
			customerId = customerEntity.getID().toString();
		}
		this.updateCustomerGeneralInfo(customer, customerId, customerEntity.getTaxRegistrationNumber(),
				customerEntity.getName(), (PTAddressEntity) customerEntity
						.getBillingAddress());

		customer.setAccountID(this.validateString("AccountID", this.ACCOUNT_ID,
				this.MAX_LENGTH_30, true));
		customer.setSelfBillingIndicator(this.validateInteger(
				"SelfBillingIndicator", this.SELF_BILLING_INDICATOR,
				this.MAX_LENGTH_1, true));

		return customer;
	}

	/**
	 * Generates an instance of Supplier to be inserted in the table 2.3
	 * (Supplier)
	 *
	 * @param supplierEntity
	 *            - the supplier
	 * @return an instance of Supplier
	 * @throws RequiredFieldNotFoundException
	 * @throws InvalidContactTypeException
	 */
	private Supplier generateSupplier(PTSupplierEntity supplierEntity)
		throws RequiredFieldNotFoundException, InvalidContactTypeException {
		this.context = "Supplier.";
		Supplier supplier = new Supplier();

		supplier.setSupplierID(this.validateString("SupplierID", supplierEntity
				.getID().toString(), this.MAX_LENGTH_30, true));
		// No accounting support
		supplier.setAccountID(this.validateString("AccountID", this.ACCOUNT_ID,
				this.MAX_LENGTH_30, true));

		supplier.setSupplierTaxID(this.validateString("SupplierTaxID",
				supplierEntity.getTaxRegistrationNumber(), this.MAX_LENGTH_20,
				true));

		supplier.setCompanyName(this.validateString("CompanyName",
				supplierEntity.getName(), this.MAX_LENGTH_100, true));

		if ((this.optionalParam = this.validateString("Contact",
				supplierEntity.getReferralName(), this.MAX_LENGTH_50, false))
				.length() > 0) {
			supplier.setContact(this.optionalParam);
		}

		supplier.setBillingAddress(this
				.generateSupplierAddressStructure((PTAddressEntity) supplierEntity
						.getBillingAddress()));

		if (supplierEntity.getShippingAddress() != null) {
			supplier.getShipFromAddress()
					.add(this
							.generateSupplierAddressStructure((PTAddressEntity) supplierEntity
									.getShippingAddress()));
		}

		supplier.setSelfBillingIndicator(this.validateInteger(
				"SelfBillingIndicator", this.SELF_BILLING_INDICATOR,
				this.MAX_LENGTH_1, true));

		List<PTContactEntity> contacts = supplierEntity.getContacts();

		this.setContacts(supplier, contacts);

		return supplier;
	}

	/**
	 * Generates an instance of Product to be inserted in the table 2.4
	 * (Product)
	 *
	 * @param productEntity
	 *            - the product/service
	 * @return an instance of Product
	 * @throws RequiredFieldNotFoundException
	 * @throws InvalidProductTypeException
	 */
	private Product generateProduct(PTProductEntity productEntity)
		throws RequiredFieldNotFoundException, InvalidProductTypeException {
		this.context = "Product.";

		Product product = new Product();
		product.setProductType(this.validateString("ProductType",
				this.getProductType(productEntity.getType()),
				this.MAX_LENGTH_1, true));

		product.setProductCode(this.validateString("ProductCode", productEntity
				.getID().toString(), this.MAX_LENGTH_30, true));

		product.setProductGroup(this.validateString("ProductGroup",
				productEntity.getProductGroup(), this.MAX_LENGTH_50, false));

		product.setProductDescription(this.validateString("ProductDescription",
				productEntity.getDescription(), this.MAX_LENGTH_200, true));

		if (productEntity.getNumberCode() == null
				|| productEntity.getNumberCode().length() == 0) {
			product.setProductNumberCode(this.validateString("ProductCode",
					productEntity.getID().toString(), this.MAX_LENGTH_50, true));
		} else {
			product.setProductNumberCode(this.validateString("ProductCode",
					productEntity.getNumberCode(), this.MAX_LENGTH_50, true));
		}

		return product;
	}

	/**
	 * Generates the TaxTable of SAFT file (Table 2.5)
	 *
	 * @param taxEntities
	 *            - the taxes to be inserted in this table
	 * @return
	 * @throws DatatypeConfigurationException
	 * @throws RequiredFieldNotFoundException
	 * @throws InvalidTaxTypeException
	 * @throws InvalidTaxCodeException
	 */
	private TaxTable generateTaxTable(List<PTTaxEntity> taxEntities)
		throws DatatypeConfigurationException, RequiredFieldNotFoundException,
		InvalidTaxTypeException, InvalidTaxCodeException {
		this.context = "TaxTable.";

		TaxTable taxTable = new TaxTable();
		for (PTTaxEntity taxEntity : taxEntities) {
			TaxTableEntry tte = new TaxTableEntry();
			tte.setTaxType(this.validateString("TaxType",
					this.getTaxType(taxEntity), this.MAX_LENGTH_3, true));
			tte.setTaxCode(this.validateString("TaxCode", taxEntity.getCode(),
					this.MAX_LENGTH_10, true));
			tte.setTaxCountryRegion(this.validateString("TaxCountryRegion",
					this.getRegionCodeFromISOCode(((PTRegionContext) taxEntity
							.getContext()).getRegionCode()), this.MAX_LENGTH_5,
					true));

			tte.setDescription(this.validateString("Description",
					taxEntity.getDescription(), this.MAX_LENGTH_255, true));

			if (taxEntity.getValidTo() != null) {
				tte.setTaxExpirationDate(this.formatDate(taxEntity.getValidTo()));
			}

			if (taxEntity.getTaxRateType().equals(TaxRateType.FLAT)) {
				tte.setTaxAmount(taxEntity.getValue());
			} else if (taxEntity.getTaxRateType()
					.equals(TaxRateType.PERCENTAGE)) {
				tte.setTaxPercentage(taxEntity.getValue());
			} else if (taxEntity.getTaxRateType().equals(TaxRateType.NONE)) {
				tte.setTaxPercentage(BigDecimal.ZERO);
			}

			taxTable.getTaxTableEntry().add(tte);
		}

		return taxTable;
	}

	/**
	 * Generates the SourceDocuments of SAFT file (Table 4)
	 *
	 * @param invoices
	 *            - list of invoices
	 * @param simpleInvoices
	 *            - list of simple invoices
	 * @param creditNotes
	 *            - list of credit notes
	 * @return
	 * @throws DatatypeConfigurationException
	 * @throws RequiredFieldNotFoundException
	 * @throws InvalidDocumentTypeException
	 * @throws InvalidDocumentStateException
	 * @throws InvalidTaxTypeException
	 * @throws InvalidTaxCodeException
	 * @throws InvalidPaymentMechanismException
	 * @throws InvalidInvoiceTypeException
	 */
	private SourceDocuments generateSourceDocuments(
			List<PTInvoiceEntity> invoices,
			List<PTSimpleInvoiceEntity> simpleInvoices,
			List<PTReceiptInvoiceEntity> receiptInvoices,
			List<PTCreditNoteEntity> creditNotes)
		throws DatatypeConfigurationException, RequiredFieldNotFoundException,
		InvalidDocumentTypeException, InvalidDocumentStateException,
		InvalidTaxTypeException, InvalidTaxCodeException,
		InvalidPaymentMechanismException, InvalidInvoiceTypeException {
		this.context = "SourceDocuments.";

		SourceDocuments srcDocs = new SourceDocuments();
		SalesInvoices salesInvoices = new SalesInvoices();
		salesInvoices.setNumberOfEntries(new BigInteger(Integer
				.toString(invoices.size() + simpleInvoices.size()
						+ receiptInvoices.size() + creditNotes.size())));

		BigDecimal totalDebit = BigDecimal.ZERO;
		BigDecimal totalCredit = BigDecimal.ZERO;

		totalCredit = processInvoices(invoices, salesInvoices, totalCredit);
		totalCredit = processInvoices(simpleInvoices, salesInvoices,
				totalCredit);
		totalCredit = processInvoices(receiptInvoices, salesInvoices,
				totalCredit);

		totalDebit = processInvoices(creditNotes, salesInvoices, totalDebit);

		salesInvoices.setTotalDebit(this.validateBigDecimal(totalDebit));
		salesInvoices.setTotalCredit(this.validateBigDecimal(totalCredit));
		srcDocs.setSalesInvoices(salesInvoices);

		return srcDocs;
	}

	private <T extends PTGenericInvoiceEntity> BigDecimal processInvoices(
			List<T> invoices, SalesInvoices salesInvoices,
			BigDecimal totalCredit) throws DatatypeConfigurationException,
		RequiredFieldNotFoundException, InvalidDocumentTypeException,
		InvalidDocumentStateException, InvalidInvoiceTypeException,
		InvalidTaxTypeException, InvalidTaxCodeException,
		InvalidPaymentMechanismException {

		for (T invoice : invoices) {
			Invoice saftInvoice = this.generateSAFTInvoice(invoice);

			this.processDocument(saftInvoice, invoice);
			salesInvoices.getInvoice().add(saftInvoice);

			if (!invoice.isBilled() && !invoice.isCancelled()) {
				totalCredit = totalCredit.add(invoice.getAmountWithoutTax());
			}
		}
		return totalCredit;
	}

	/**
	 * Generates a financial document that will be inserted in the
	 * SourceDocuments table
	 *
	 * @param document
	 *            - can be an invoice, simple invoice or credit note
	 * @return an instance of Invoice (represents a financial document in the
	 *         SAFT XML context)
	 *
	 * @throws DatatypeConfigurationException
	 * @throws RequiredFieldNotFoundException
	 * @throws InvalidDocumentTypeException
	 * @throws InvalidDocumentStateException
	 * @throws InvalidInvoiceTypeException
	 */
	private Invoice generateSAFTInvoice(PTGenericInvoiceEntity document)
		throws DatatypeConfigurationException, RequiredFieldNotFoundException,
		InvalidDocumentTypeException, InvalidDocumentStateException,
		InvalidInvoiceTypeException {
		Invoice saftInv = new Invoice();

		InvoiceSeriesEntity invoiceSeries =
			this.daoInvoiceSeries.getSeries(
				document.getSeries(),
				document.getBusiness().getUID().toString(),
				LockModeType.NONE);

		StringBuilder atcudBuilder =
			invoiceSeries.getSeriesUniqueCode()
						 .map(s -> new StringBuilder().append(s)
													  .append("-")
													  .append(document.getSeriesNumber()))
						 .orElse(new StringBuilder().append("0"));

		saftInv.setDocumentStatus(this.getDocumentStatus(document));

		saftInv.setInvoiceNo(validateString("InvoiceNo", document.getNumber(),
				MAX_LENGTH_60, true));
		saftInv.setATCUD(atcudBuilder.toString());
		saftInv.setInvoiceType(validateString("InvoiceType",
				getDocumentType(document), MAX_LENGTH_2, true));
		saftInv.setHash(validateString("Hash", document.getHash(),
				MAX_LENGTH_172, true));
		if (document.getHashControl() != null) {
			saftInv.setHashControl(validateString("HashControl",
					document.getHashControl(), MAX_LENGTH_40, false));
		}
		saftInv.setPeriod(validateInteger("Period", Integer
				.toString(getDateField(document.getDate(), Calendar.MONTH)+1),
				MAX_LENGTH_2, true));
		saftInv.setInvoiceDate(formatDate(document.getDate()));
		saftInv.setSpecialRegimes(
				getSpecialRegimes(document.isSelfBilled(),
						document.isCashVATEndorser(),
						document.isThirdPartyBilled()));
		saftInv.setSourceID(validateString("InvoiceSourceID",
				document.getSourceId(), MAX_LENGTH_30, true));
		if (document.getEACCode() != null) {
			saftInv.setEACCode(validateString("EACCode", document.getEACCode(),
					MAX_LENGTH_5, false));
		}
		saftInv.setSystemEntryDate(formatDateTime(document.getCreateTimestamp()));
		UID customerUID = document.getCustomer().getUID();
		String customerID = customerUID.equals(this.config
				.getUID(Key.Customer.Generic.UUID)) ? "Consumidor final"
				: document.getCustomer().getID().toString();

		saftInv.setCustomerID(this.validateString("CustomerID", customerID,
				this.MAX_LENGTH_30, true));

		/* NOT REQUIRED */
		if (document.getShippingDestination() != null) {
			saftInv.setShipTo(this.getShippingPointStructure(document
					.getShippingDestination().getDeliveryId(), document
					.getShippingDestination().getDate(),
					(PTAddressEntity) document.getShippingDestination()
							.getAddress()));
		}
		if (document.getShippingOrigin() != null) {
			saftInv.setShipFrom(this
					.getShippingPointStructure(document
							.getShippingDestination().getDeliveryId(), document
							.getShippingDestination().getDate(),
							(PTAddressEntity) document.getShippingOrigin()
									.getAddress()));
		}

		return saftInv;
	}

	private SpecialRegimes getSpecialRegimes(Boolean selfBilled,
			Boolean cashVATEndorser, Boolean thirdPartyBilled) throws RequiredFieldNotFoundException {
		SpecialRegimes rvalue = new SpecialRegimes();
		if(null != selfBilled){
			rvalue.setSelfBillingIndicator(validateInteger(
					"SelfBillingIndicator",
					selfBilled ? "1" : "0", MAX_LENGTH_1, true));
		} else {
			rvalue.setSelfBillingIndicator(0);
		}
		if(null != cashVATEndorser){
			rvalue.setCashVATSchemeIndicator(validateInteger(
					"CashVATSchemeIndicator",
					cashVATEndorser ? "1" : "0", MAX_LENGTH_1, true));
		} else {
			rvalue.setCashVATSchemeIndicator(0);
		}
		if(null != thirdPartyBilled){
			rvalue.setThirdPartiesBillingIndicator(validateInteger(
					"ThirdPartiesBillingIndicator",
					thirdPartyBilled ? "1" : "0", MAX_LENGTH_1, true));
		} else {
			rvalue.setThirdPartiesBillingIndicator(0);
		}
		return rvalue;
	}

	/**
	 * Process a financial document to generate its DocumentTotals and the
	 * document's entries to generate the table Line in the Invoice instance of
	 * SAFT File
	 *
	 * @param saftInvoice
	 *            - instance of Invoice (representation of financial document in
	 *            SAFT XML context)
	 * @param document
	 *            - the document that is represented in the saftInvoice
	 *
	 * @throws RequiredFieldNotFoundException
	 * @throws DatatypeConfigurationException
	 * @throws InvalidDocumentTypeException
	 * @throws InvalidTaxTypeException
	 * @throws InvalidTaxCodeException
	 * @throws InvalidPaymentMechanismException
	 */
	private void processDocument(Invoice saftInvoice,
			PTGenericInvoiceEntity document)
		throws RequiredFieldNotFoundException, DatatypeConfigurationException,
		InvalidDocumentTypeException, InvalidTaxTypeException,
		InvalidTaxCodeException, InvalidPaymentMechanismException {
		List<PTGenericInvoiceEntryEntity> entries = document.getEntries();
		if (entries == null || entries.size() < 1) {
			throw new RequiredFieldNotFoundException(this.context + " Line");
		}

		entries.sort(Comparator.comparing(GenericInvoiceEntry::getEntryNumber));
		
		for (PTGenericInvoiceEntryEntity entry : entries) {
			/* REQUIRED - One Invoice.Line per IPTFinancialDocumentEntryEntity */
			Line line = new Line();
			line.setLineNumber(new BigInteger(Integer.toString(entry
					.getEntryNumber())));

			/* REQUIRED */
			line.setProductCode(this.validateString("ProductCode", entry
					.getProduct().getID().toString(), this.MAX_LENGTH_30, true));
			line.setProductDescription(this.validateString(
					"ProductDescription", entry.getProduct().getDescription(),
					this.MAX_LENGTH_200, true));
			line.setQuantity(this.validateBigDecimal(entry.getQuantity()));
			line.setUnitOfMeasure(this.validateString("UnitOfMeasure",
					this.UNIT_OF_MEASURE, this.MAX_LENGTH_20, true));
			line.setUnitPrice(entry.getAmountWithoutTax().divide(entry.getQuantity(),
							this.mc.getRoundingMode()));
			line.setTaxPointDate(this.formatDate(entry.getTaxPointDate()));

			/* NOT REQUIRED - Invoice.Line.References */
			References ref = this
					.getReferencesForDocumentEntry(entry, document);
			if (ref != null) {
				line.getReferences().add(ref);
			}

			/* REQUIRED */
			line.setDescription(this.validateString("Description",
					entry.getDescription(), this.MAX_LENGTH_200, true));
			switch (document.getCreditOrDebit()){
				case DEBIT:
					line.setDebitAmount(entry.getAmountWithoutTax());
					break;
				case CREDIT:
					line.setCreditAmount(entry.getAmountWithoutTax());
					break;
			}

			/* NOT REQUIRED Invoice.Line.Tax */
			if (entry.getTaxes().size() > 0) {
				PTTaxEntity taxEntity = (PTTaxEntity) entry.getTaxes().get(0);
				Tax tax = new Tax();
				tax.setTaxType(this.validateString("TaxType",
						this.getTaxType(taxEntity), this.MAX_LENGTH_3, true));
				tax.setTaxCode(this.validateString("TaxCode",
						taxEntity.getCode(), this.MAX_LENGTH_10, true));
				tax.setTaxCountryRegion(this.validateString(
						"TaxCountryRegion",
						this.getRegionCodeFromISOCode(((PTRegionContext) taxEntity
								.getContext()).getRegionCode()),
						this.MAX_LENGTH_5, true));

				switch (taxEntity.getTaxRateType()) {
					case FLAT:
						tax.setTaxAmount(this.validateBigDecimal(taxEntity.getValue()));
						break;
					case PERCENTAGE:
						tax.setTaxPercentage(taxEntity.getValue());
						break;
					case NONE:
						tax.setTaxPercentage(BigDecimal.ZERO);
						break;
					default:
						throw new InvalidTaxTypeException(taxEntity.getTaxRateType().toString());
				}
				line.setTax(tax);

				if ((tax.getTaxPercentage() != null && tax.getTaxPercentage()
						.compareTo(BigDecimal.ZERO) == 0)
						|| (tax.getTaxAmount() != null && tax.getTaxAmount()
								.compareTo(BigDecimal.ZERO) == 0)) {
					line.setTaxExemptionReason(this.validateString(
							"TaxExemptionReason",
							entry.getTaxExemptionReason(), this.MAX_LENGTH_60,
							true));
                    line.setTaxExemptionCode(this.validateString(
                            "TaxExemptionCode",
                            entry.getTaxExemptionCode(), this.MAX_LENGTH_60,
                            true));
				}
			}

			/* NOT REQUIRED */
			if (entry.getDiscountAmount() != null
					&& !entry.getDiscountAmount().equals(BigDecimal.ZERO)) {
				line.setSettlementAmount(entry.getDiscountAmount());
			}

			saftInvoice.getLine().add(line);
		}

		DocumentTotals dt = this.getDocumentTotals(document);
		if (dt != null) {
			saftInvoice.setDocumentTotals(dt);
		}
	}

	/**
	 * Generates the field OrderReferences (4.1.4.18.2) of document entry
	 *
	 * @param entry
	 * @return an instance of OrderReferences
	 *
	 * @throws RequiredFieldNotFoundException
	 * @throws DatatypeConfigurationException
	 */
	// private OrderReferences getOrderReferencesForDocumentEntry(
	// PTGenericInvoiceEntryEntity entry)
	// throws RequiredFieldNotFoundException,
	// DatatypeConfigurationException {
	// OrderReferences or = new OrderReferences();
	// if ((optionalParam = validateString("OriginatingON", entry.get,
	// MAX_LENGTH_30, false)).length() > 0) {
	// or.setOriginatingON(optionalParam);
	// }
	// if (entry.getOrderDate() != null) {
	// or.setOrderDate(formatDate(entry.getOrderDate()));
	// }
	// if (entry.getOrderId() != null || entry.getOrderDate() != null) {
	// return or;
	// } else {
	// return null;
	// }
	// }

	/**
	 * Generates the field References (4.1.4.18.9) of document entry
	 *
	 * @param entry
	 * @param document
	 * @return an instance of References
	 * @throws RequiredFieldNotFoundException
	 * @throws DatatypeConfigurationException
	 * @throws InvalidDocumentTypeException
	 */
	private References getReferencesForDocumentEntry(
			PTGenericInvoiceEntryEntity entry, PTGenericInvoiceEntity document)
		throws RequiredFieldNotFoundException, DatatypeConfigurationException,
		InvalidDocumentTypeException {
		References ref = null;
		PTInvoice referencedDocument = null;
		if (PTCreditNoteEntryEntity.class.isInstance(entry)) {
			referencedDocument = ((PTCreditNoteEntryEntity) entry)
					.getReference();
		}

		if (referencedDocument != null) {
			ref = new References();

			String reasonForCredit = "";

			if (document.getType().equals(TYPE.NC)) {
				/* then referencedDocument is an invoice/simple invoice */
				reasonForCredit = ((PTCreditNoteEntryEntity) entry).getReason();
			}

			if ((this.optionalParam = this.validateString(
					"References.CreditNote.Reference",
					referencedDocument.getNumber(), this.MAX_LENGTH_60, false))
					.length() > 0) {
				ref.setReference(this.optionalParam);
			}

			if ((this.optionalParam = this.validateString(
					"References.CreditNote.Reason", reasonForCredit,
					this.MAX_LENGTH_50, false)).length() > 0) {
				ref.setReason(this.optionalParam);
			}
		}
		return ref;
	}

	/**
	 * Constructs an instance of ShippingPointStructure needed in the
	 * construction of a SAFT Invoice
	 *
	 * @param deliveryID
	 *            - the id of delivery/origin
	 * @param deliveryDate
	 *            - the date of delivery/origin
	 * @param address
	 *            - the address of delivery/origin
	 * @return an instance of ShippingPointStructure
	 * @throws RequiredFieldNotFoundException
	 * @throws DatatypeConfigurationException
	 */
	private ShippingPointStructure getShippingPointStructure(String deliveryID,
			Date deliveryDate, PTAddressEntity address)
		throws RequiredFieldNotFoundException, DatatypeConfigurationException {
		ShippingPointStructure sps = new ShippingPointStructure();

		if ((this.optionalParam = this.validateString("DeliveryID", deliveryID,
				this.MAX_LENGTH_30, false)).length() > 0) {
			sps.getDeliveryID().add(this.optionalParam);
		}
		if (deliveryDate != null) {
			sps.setDeliveryDate(this.formatDate(deliveryDate));
		}
		sps.setAddress(this.generateAddressStructure(address));

		return sps;
	}

	/**
	 * Generates the field Currency (4.1.4.19.4) of DocumentTotals of a SAFT
	 * Invoice
	 *
	 * @param document
	 * @return
	 */
	private Currency getCurrency(PTGenericInvoiceEntity document) {
		Currency cur = null;

		if (!document.getCurrency().getCurrencyCode()
				.equals(this.CURRENCY_CODE)) {
			cur = new Currency();
			cur.setCurrencyCode(document.getCurrency().getCurrencyCode());
			cur.setCurrencyAmount(this.validateBigDecimal(document
					.getAmountWithoutTax()));
		}

		return cur;
	}

	/**
	 * Generates the field Settlement (4.1.4.19.5) of DocumentTotals of a SAFT
	 * Invoice
	 *
	 * @param document
	 * @return
	 * @throws RequiredFieldNotFoundException
	 * @throws DatatypeConfigurationException
	 * @throws InvalidPaymentMechanismException
	 */
	private Settlement getSettlement(PTGenericInvoiceEntity document)
		throws RequiredFieldNotFoundException, DatatypeConfigurationException,
		InvalidPaymentMechanismException {
		if (document.getSettlementDiscount() != null) {
			Settlement settlement = new Settlement();
			settlement.setSettlementAmount(this.validateBigDecimal(document
					.getSettlementDiscount()));
			settlement.setSettlementDate(this.formatDate(document
					.getSettlementDate()));
			if ((this.optionalParam = this.validateString("SettlementDiscount",
					document.getSettlementDescription(), this.MAX_LENGTH_30,
					false)).length() > 0) {
				settlement.setSettlementDiscount(this.optionalParam);
			}

			if (settlement.getSettlementAmount() != null
					&& !settlement.getSettlementAmount().equals(
							this.validateBigDecimal(BigDecimal.ZERO))) {
				return settlement;
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * Generates the DocumentTotals (4.1.4.19) of a SAFT Invoice
	 *
	 * @param document
	 * @return
	 * @throws RequiredFieldNotFoundException
	 * @throws DatatypeConfigurationException
	 * @throws InvalidPaymentMechanismException
	 */
	private DocumentTotals getDocumentTotals(PTGenericInvoiceEntity document) throws RequiredFieldNotFoundException,
		DatatypeConfigurationException, InvalidPaymentMechanismException {
		DocumentTotals dt = null;

		if (!this.validateBigDecimal(document.getAmountWithoutTax()).equals(
				this.validateBigDecimal(BigDecimal.ZERO))) {
			dt = new DocumentTotals(); // 4.1.4.19
			/* REQUIRED */
			// 4.1.4.19.1
			dt.setTaxPayable(this.validateBigDecimal(document.getTaxAmount()));
			// 4.1.4.19.2
			dt.setNetTotal(this.validateBigDecimal(document
					.getAmountWithoutTax()));
			// 4.1.4.19.3
			dt.setGrossTotal(this.validateBigDecimal(document
					.getAmountWithTax()));

			/*
			 * NOT REQUIRED - I guess it's only required if it has another
			 * currency
			 */
			// 4.1.4.19.4
			Currency cur = this.getCurrency(document);
			if (cur != null) {
				dt.setCurrency(cur);
			}

			/* NOT REQUIRED - Invoice.DocumentTotals.Settlements */
			Settlement stlment = this.getSettlement(document); // 4.1.4.19.5
			if (stlment != null) {
				dt.getSettlement().add(stlment);
			}

			List<PaymentMethod> payments = this.getPaymentsList(document);
			if (payments != null) {
				for (PaymentMethod p : payments) {
					dt.getPayment().add(p);
				}
			}
		}

		return dt;
	}

	/**
	 * Generates the InvoiceType (4.1.4.7) of a SAFT Invoice
	 *
	 * @param document
	 * @return
	 * @throws InvalidInvoiceTypeException
	 */
	private String getDocumentType(PTGenericInvoiceEntity document)
		throws InvalidInvoiceTypeException {
		switch (document.getType()) {
			case FS:
				return "FS";
			case FT:
				return "FT";
			case FR:
				return "FR";
			case NC:
				return "NC";
			case ND:
				return "ND";
			default:
				throw new InvalidInvoiceTypeException(document.getType()
						.toString(), document.getSeries());

		}
	}

	/*************
	 * CUSTOMERS *
	 *************/
	/**
	 * Sets the main information about the Customer
	 *
	 * @param customer
	 * @param customerID
	 * @param customerFinancialID
	 *            - NIF
	 * @param companyName
	 *            - the company where the customer works
	 * @param address
	 * @throws RequiredFieldNotFoundException
	 */
	private void updateCustomerGeneralInfo(Customer customer,
			String customerID, String customerFinancialID, String companyName,
			PTAddressEntity address) throws RequiredFieldNotFoundException {
		customer.setCustomerTaxID(this.validateString("CustomerTaxID",
				customerFinancialID, this.MAX_LENGTH_20, true));

		customer.setCompanyName(this.validateString("CompanyName", companyName,
				this.MAX_LENGTH_100, true));

		customer.setCustomerID(this.validateString("CustomerId", customerID,
				this.MAX_LENGTH_30, true));

		customer.setBillingAddress(this.generateAddressStructure(address));
	}

	/*************
	 * DOCUMENTS *
	 *************/
	/**
	 * Converts the document state to an acronym according to the SAFT file
	 * rules
	 *
	 * @param document
	 * @return
	 * @throws InvalidDocumentStateException
	 * @throws DatatypeConfigurationException
	 * @throws RequiredFieldNotFoundException
	 */
	private DocumentStatus getDocumentStatus(PTGenericInvoiceEntity document)
		throws InvalidDocumentStateException, DatatypeConfigurationException,
		RequiredFieldNotFoundException {
		DocumentStatus status = new DocumentStatus();

		if (document.isCancelled()) {
			status.setInvoiceStatus("A");
		} else if (document.isBilled()) {
			status.setInvoiceStatus("F");
		} else if (document.isSelfBilled()) {
			status.setInvoiceStatus("S");
		} else {
			status.setInvoiceStatus("N");
		}

		status.setInvoiceStatusDate(formatDateTime(document.getDate()));
		if (document.getChangeReason() != null) {
			status.setReason(validateString("Reason",
					document.getChangeReason(), MAX_LENGTH_50, false));
		}
		status.setSourceID(document.getSourceId());
		status.setSourceBilling(SAFTPTSourceBilling.valueOf(
				document.getSourceBilling().toString()));
		return status;
	}

	@SuppressWarnings("unchecked")
	private List<PaymentMethod> getPaymentsList(PTGenericInvoiceEntity document)
		throws RequiredFieldNotFoundException,
		InvalidPaymentMechanismException, DatatypeConfigurationException {

		if (document.getPayments() != null) {
			List<PaymentMethod> payments = new ArrayList<PaymentMethod>();
			for (com.premiumminds.billy.core.services.entities.Payment p : document
					.getPayments()) {
				PaymentMethod payment = new PaymentMethod();

				payment.setPaymentMechanism(this.validateString(
						"PaymentMechanism", this
								.getPaymentMechanism((Enum<PaymentMechanism>) p
										.getPaymentMethod()),
						this.MAX_LENGTH_2, true));
				payment.setPaymentAmount(this
						.validateBigDecimal(((PTPayment) p).getPaymentAmount()));
				payment.setPaymentDate(this.formatDate(p.getPaymentDate()));
				payments.add(payment);
			}

			return payments;
		}
		return null;
	}

	/**
	 * Converts the payment mechanism to an acronym according to the SAFT file
	 * rules
	 *
	 * @param pm
	 * @return
	 * @throws InvalidPaymentMechanismException
	 */
	private String getPaymentMechanism(Enum<PaymentMechanism> pm)
		throws InvalidPaymentMechanismException {
		switch ((PaymentMechanism) pm) {
			case CASH:
				return "NU";
			case CHECK:
				return "CH";
			case DEBIT_CARD:
				return "CD";
			case CREDIT_CARD:
				return "CC";
			case BANK_TRANSFER:
				return "TB";
			case RESTAURANT_TICKET:
				return "TR";
			case COMPENSATION:
				return "CS";
			case COMMERCIAL_LETTER:
				return "LC";
			case ATM:
				return "MB";
			case EXCHANGE:
				return "PR";
			case ELECTRONIC_MONEY:
				return "DE";
			default:
				throw new InvalidPaymentMechanismException(pm.toString());
		}
	}

	/***********************
	 * ADDRESSES & REGIONS *
	 ***********************/
	/**
	 * Constructs an AddressStructures that is used in almost every fields of
	 * SAFT file that represent addresses
	 *
	 * @param addressEntity
	 * @return
	 * @throws RequiredFieldNotFoundException
	 */
	private AddressStructure generateAddressStructure(
			PTAddressEntity addressEntity)
		throws RequiredFieldNotFoundException {
		AddressStructure address = new AddressStructure();

		if ((this.optionalParam = this.validateString("BuildingNumber",
				addressEntity.getNumber(), this.MAX_LENGTH_10, false)).length() > 0) {
			address.setBuildingNumber(this.optionalParam);
		}

		if ((this.optionalParam = this.validateString("StreetName",
				addressEntity.getStreetName(), this.MAX_LENGTH_90, false))
				.length() > 0) {
			address.setStreetName(this.optionalParam);
		}

		address.setAddressDetail(this.validateString("AddressDetail",
				addressEntity.getDetails(), this.MAX_LENGTH_100, true));

		address.setCity(this.validateString("City", addressEntity.getCity(),
				this.MAX_LENGTH_50, true));

		address.setPostalCode(this.validateString("PostalCode",
				addressEntity.getPostalCode(), this.MAX_LENGTH_20, true));

		if ((this.optionalParam = this.validateString("Region",
				addressEntity.getRegion(), this.MAX_LENGTH_50, false)).length() > 0) {
			address.setRegion(this.optionalParam);
		}

		address.setCountry(this.validateString("Country",
				addressEntity.getISOCountry(), this.MAX_LENGTH_12, true));

		return address;
	}

	/**
	 * Constructs a specific AddressStructurePT that is only used in the Header
	 * of the SAFT File
	 *
	 * @param addressEntity
	 * @return
	 * @throws RequiredFieldNotFoundException
	 */
	private AddressStructurePT generateAddressStructurePT(
			AddressEntity addressEntity) throws RequiredFieldNotFoundException {
		AddressStructurePT address = new AddressStructurePT();

		if ((this.optionalParam = this.validateString("BuildingNumber",
				addressEntity.getNumber(), this.MAX_LENGTH_10, false)).length() > 0) {
			address.setBuildingNumber(this.optionalParam);
		}
		if ((this.optionalParam = this.validateString("StreetName",
				addressEntity.getStreetName(), this.MAX_LENGTH_90, false))
				.length() > 0) {
			address.setStreetName(this.optionalParam);
		}

		address.setAddressDetail(this.validateString("AddressDetail",
				addressEntity.getDetails(), this.MAX_LENGTH_100, true));

		address.setCity(this.validateString("City", addressEntity.getCity(),
				this.MAX_LENGTH_50, true));

		address.setPostalCode(this.validateString("PostalCode",
				addressEntity.getPostalCode(), this.MAX_LENGTH_8, true));

		if ((this.optionalParam = this.validateString("Region",
				addressEntity.getRegion(), this.MAX_LENGTH_50, false)).length() > 0) {
			address.setRegion(this.optionalParam);
		}

		address.setCountry(this.validateString("Country", this.COUNTRY_CODE,
				this.MAX_LENGTH_2, true));
		return address;
	}

	/**
	 * Constructs an SupplierAddressStructures that is used in the Supplier of
	 * the SAFT file
	 *
	 * @param addressEntity
	 * @return
	 * @throws RequiredFieldNotFoundException
	 */
	private SupplierAddressStructure generateSupplierAddressStructure(
			PTAddressEntity addressEntity)
		throws RequiredFieldNotFoundException {
		SupplierAddressStructure address = new SupplierAddressStructure();

		if ((this.optionalParam = this.validateString("StreetName",
				addressEntity.getStreetName(), this.MAX_LENGTH_90, false))
				.length() > 0) {
			address.setStreetName(this.optionalParam);
		}
		if ((this.optionalParam = this.validateString("BuildingNumber",
				addressEntity.getNumber(), this.MAX_LENGTH_10, false)).length() > 0) {
			address.setBuildingNumber(this.optionalParam);
		}

		address.setAddressDetail(this.validateString("AddressDetail",
				addressEntity.getDetails(), this.MAX_LENGTH_100, true));

		address.setCity(this.validateString("City", addressEntity.getCity(),
				this.MAX_LENGTH_50, true));

		address.setPostalCode(this.validateString("PostalCode",
				addressEntity.getPostalCode(), this.MAX_LENGTH_20, true));

		address.setCountry(this.validateString("Country",
				addressEntity.getISOCountry(), this.MAX_LENGTH_2, true));

		if ((this.optionalParam = this.validateString("Region",
				addressEntity.getRegion(), this.MAX_LENGTH_50, false)).length() > 0) {
			address.setRegion(this.optionalParam);
		}
		return address;
	}

	/**
	 * Converts the ISO code of a region to the code defined in the SAFT file
	 * rules
	 *
	 * @param regionCode
	 * @return
	 */
	private String getRegionCodeFromISOCode(String regionCode) {
		if (regionCode.equals("PT-20")) {
			return "PT-AC";
		}
		if (regionCode.equals("PT-30")) {
			return "PT-MA";
		}
		return "PT";
	}

	/************
	 * CONTACTS *
	 ************/
	/**
	 * Sets the contacts of a company
	 *
	 * @param hdr
	 * @param contacts
	 *            - the company's list of contacts
	 * @throws RequiredFieldNotFoundException
	 * @throws InvalidContactTypeException
	 */
	private void setContacts(Header hdr, List<PTContactEntity> contacts)
		throws RequiredFieldNotFoundException, InvalidContactTypeException {

		for (ContactEntity ce : contacts) {

			if ((this.optionalParam = this.validateString("Telephone",
					ce.getTelephone(), this.MAX_LENGTH_20, false)).length() > 0) {
				hdr.setTelephone(this.optionalParam);
			} else {
				if ((this.optionalParam = this.validateString("Telephone",
						ce.getMobile(), this.MAX_LENGTH_20, false)).length() > 0) {
					hdr.setTelephone(this.optionalParam);
				}
			}

			if ((this.optionalParam = this.validateString("Fax", ce.getFax(),
					this.MAX_LENGTH_20, false)).length() > 0) {
				hdr.setFax(this.optionalParam);
			}

			if ((this.optionalParam = this.validateString("Email",
					ce.getEmail(), this.MAX_LENGTH_60, false)).length() > 0) {
				hdr.setEmail(this.optionalParam);
			}

			if ((this.optionalParam = this.validateString("Website",
					ce.getWebsite(), this.MAX_LENGTH_60, false)).length() > 0) {
				hdr.setWebsite(this.optionalParam);
			}
		}
	}

	/**
	 * Sets the contacts of a customer
	 *
	 * @param customer
	 * @param contacts
	 *            - the customer's list of contacts
	 * @throws RequiredFieldNotFoundException
	 * @throws InvalidContactTypeException
	 */
	private void setContacts(Customer customer, List<PTContactEntity> contacts)
		throws RequiredFieldNotFoundException, InvalidContactTypeException {
		for (PTContactEntity ce : contacts) {

			if ((this.optionalParam = this.validateString("Telephone",
					ce.getTelephone(), this.MAX_LENGTH_20, false)).length() > 0) {
				customer.setTelephone(this.optionalParam);
			} else {
				if ((this.optionalParam = this.validateString("Telephone",
						ce.getMobile(), this.MAX_LENGTH_20, false)).length() > 0) {
					customer.setTelephone(this.optionalParam);
				}
			}
			if ((this.optionalParam = this.validateString("Fax", ce.getFax(),
					this.MAX_LENGTH_20, false)).length() > 0) {
				customer.setFax(this.optionalParam);
			}

			if ((this.optionalParam = this.validateString("Email",
					ce.getEmail(), this.MAX_LENGTH_60, false)).length() > 0) {
				customer.setEmail(this.optionalParam);
			}

			if ((this.optionalParam = this.validateString("Website",
					ce.getWebsite(), this.MAX_LENGTH_60, false)).length() > 0) {
				customer.setWebsite(this.optionalParam);
			}
		}
	}

	/**
	 * Sets the contacts of a supplier
	 *
	 * @param supplier
	 * @param contacts
	 *            - the supplier's list of contacts
	 * @throws RequiredFieldNotFoundException
	 * @throws InvalidContactTypeException
	 */
	private void setContacts(Supplier supplier, List<PTContactEntity> contacts)
		throws RequiredFieldNotFoundException, InvalidContactTypeException {
		for (PTContactEntity ce : contacts) {
			if ((this.optionalParam = this.validateString("Email",
					ce.getEmail(), this.MAX_LENGTH_60, false)).length() > 0) {
				supplier.setEmail(this.optionalParam);
			}

			if ((this.optionalParam = this.validateString("Fax", ce.getFax(),
					this.MAX_LENGTH_20, false)).length() > 0) {
				supplier.setFax(this.optionalParam);
			}

			if ((this.optionalParam = this.validateString("Telephone",
					ce.getTelephone(), this.MAX_LENGTH_20, false)).length() > 0) {
				supplier.setTelephone(this.optionalParam);
			} else {
				if ((this.optionalParam = this.validateString("Telephone",
						ce.getMobile(), this.MAX_LENGTH_20, false)).length() > 0) {
					supplier.setTelephone(this.optionalParam);
				}
			}

			if ((this.optionalParam = this.validateString("Website",
					ce.getWebsite(), this.MAX_LENGTH_60, false)).length() > 0) {
				supplier.setWebsite(this.optionalParam);
			}
		}
	}

	/************
	 * PRODUCTS *
	 ************/
	/**
	 * Converts the product type to an acronym according to the SAFT file rules
	 *
	 * @param type
	 * @return
	 * @throws InvalidProductTypeException
	 */
	private String getProductType(ProductType type)
		throws InvalidProductTypeException {
		switch (type) {
			case GOODS:
				return "P";
			case SERVICE:
				return "S";
			case OTHER:
				return "O";
			case CHARGE:
				return "I";
			default:
				throw new InvalidProductTypeException(type.toString());
		}
	}

	/*********
	 * TAXES *
	 *********/
	/**
	 * Converts the tax type to an acronym according to the SAFT file rules
	 *
	 * @param entity
	 * @return
	 * @throws InvalidTaxTypeException
	 */
	private String getTaxType(PTTaxEntity entity)
		throws InvalidTaxTypeException {
		switch (entity.getTaxRateType()) {
			case PERCENTAGE:
				return "IVA";
			case FLAT:
				return "IS";
			case NONE:
				return "NS";
			default:
				throw new InvalidTaxTypeException(entity.getTaxRateType()
						.toString());
		}
	}

	/********
	 * MISC *
	 ********/
	/**
	 * Validates a string, considering the max length of that field that will be
	 * assigned and if that field is required
	 *
	 * @param field
	 * @param str
	 * @param maxLength
	 * @param isRequired
	 * @return
	 * @throws RequiredFieldNotFoundException
	 */
	private String validateString(String field, String str, int maxLength,
			boolean isRequired) throws RequiredFieldNotFoundException {
		if (isRequired && (str == null || str.length() == 0)) {
			throw new RequiredFieldNotFoundException(this.context + field);
		} else {
			if (str != null && str.length() > maxLength) {
				String original = str;
				str = str.substring(0, maxLength);
				log.debug("the field {} has been truncated from '{}' to '{}'.", this.context + field, original, str);
			}
			return (str == null) ? new String() : str.trim();
		}
	}

	/**
	 * Validates an integer, considering the max length of that field that will
	 * be assigned and if that field is required
	 *
	 * @param field
	 * @param str
	 *            - a string that represents an integer
	 * @param maxLength
	 * @param isRequired
	 * @return
	 * @throws RequiredFieldNotFoundException
	 */
	private int validateInteger(String field, String str, int maxLength,
			boolean isRequired) throws RequiredFieldNotFoundException {
		return Integer.parseInt(this.validateString(field, str, maxLength,
				isRequired));
	}

	/**
	 * Validates a BigInteger, considering the max length of that field that
	 * will be assigned and if that field is required
	 *
	 * @param field
	 * @param str
	 *            - a string that represents a BigInteger
	 * @param maxLength
	 * @param isRequired
	 * @return
	 * @throws RequiredFieldNotFoundException
	 */
	private BigInteger validateBigInteger(String field, String str,
			int maxLength, boolean isRequired)
		throws RequiredFieldNotFoundException {
		return new BigInteger(this.validateString(field, str, maxLength,
				isRequired));
	}

	/**
	 * Validates a decimal number, limiting it to 2 decimal places and rounding
	 * int
	 *
	 * @param bd
	 * @return
	 */
	private BigDecimal validateBigDecimal(BigDecimal bd) {
		return bd.setScale(BillyMathContext.SCALE, this.mc.getRoundingMode());
	}

	/**
	 * Gets the today's date
	 *
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	private XMLGregorianCalendar getXMLGregorianCalendarNow()
		throws DatatypeConfigurationException {
		return this.formatDate(new Date());
	}

	/**
	 * Returns a date in the format YYYY-MM-dd
	 *
	 * @param date
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	private XMLGregorianCalendar formatDate(Date date)
		throws DatatypeConfigurationException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
				cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH) + 1),
				cal.get(Calendar.DAY_OF_MONTH),
				DatatypeConstants.FIELD_UNDEFINED);
	}

	/**
	 * Returns a datetime in the format YYYY-MM-ddThh-mm-ss
	 *
	 * @param date
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	private XMLGregorianCalendar formatDateTime(Date date)
		throws DatatypeConfigurationException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(
				cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH) + 1),
				cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY),
				cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND),
				DatatypeConstants.FIELD_UNDEFINED,
				DatatypeConstants.FIELD_UNDEFINED);
	}

	/**
	 * Returns a field (the month or the year) of a date
	 *
	 * @param date
	 * @param field
	 * @return
	 */
	private int getDateField(Date date, int field) {
		Calendar issueDate = Calendar.getInstance();
		issueDate.setTime(date);
		return issueDate.get(field);
	}

}
