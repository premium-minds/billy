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
package com.premiumminds.billy.portugal.services.export.saftpt;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.services.entities.Product.ProductType;
import com.premiumminds.billy.platypus.services.export.saftpt.schema.AddressStructure;
import com.premiumminds.billy.platypus.services.export.saftpt.schema.AddressStructurePT;
import com.premiumminds.billy.platypus.services.export.saftpt.schema.AuditFile;
import com.premiumminds.billy.platypus.services.export.saftpt.schema.AuditFile.MasterFiles;
import com.premiumminds.billy.platypus.services.export.saftpt.schema.Customer;
import com.premiumminds.billy.platypus.services.export.saftpt.schema.Header;
import com.premiumminds.billy.platypus.services.export.saftpt.schema.Product;
import com.premiumminds.billy.platypus.services.export.saftpt.schema.TaxTable;
import com.premiumminds.billy.platypus.services.export.saftpt.schema.TaxTableEntry;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.Config.Key;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTGenericInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTAddressEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTContactEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.export.exceptions.InvalidContactTypeException;
import com.premiumminds.billy.portugal.services.export.exceptions.InvalidProductTypeException;
import com.premiumminds.billy.portugal.services.export.exceptions.InvalidTaxCodeException;
import com.premiumminds.billy.portugal.services.export.exceptions.InvalidTaxTypeException;
import com.premiumminds.billy.portugal.services.export.exceptions.RequiredFieldNotFoundException;
import com.premiumminds.billy.portugal.services.export.exceptions.SAFTPTExportException;

public class SAFTFileGenerator {

	private Config config = null;
	private JAXBContext jaxbContext;
	private Marshaller marshaller;

	private String context = null;
	private String optionalParam;

	private static final String FILE_ENCODING = "WINDOWS-1252";

	private final int MAX_LENGTH_1 = 1;
	private final int MAX_LENGTH_2 = 2;
	private final int MAX_LENGTH_3 = 3;
	private final int MAX_LENGTH_5 = 5;
	private final int MAX_LENGTH_8 = 8;
	private final int MAX_LENGTH_9 = 9;
	private final int MAX_LENGTH_10 = 10;
	private final int MAX_LENGTH_12 = 12;
	private final int MAX_LENGTH_20 = 20;
	private final int MAX_LENGTH_30 = 30;
	private final int MAX_LENGTH_50 = 50;
	private final int MAX_LENGTH_60 = 60;
	private final int MAX_LENGTH_90 = 90;
	private final int MAX_LENGTH_200 = 200;
	private final int MAX_LENGTH_255 = 255;

	private final String XML_SCHEMA_VERSION = "1.01_01";
	private final String TAX_ACCOUNTING_BASIS = "F";
	private final String COUNTRY_CODE = "PT";
	private final String CURRENCY_CODE = "EUR";
	private final String TAX_ENTITY = "Global";

	private final String ACCOUNT_ID = "Desconhecido";
	private final String SELF_BILLING_INDICATOR = "0";
	private final String UNIT_OF_MEASURE = "Unidade";

	public SAFTFileGenerator() {
		this.config = new Config();

		try {
			this.jaxbContext = JAXBContext
					.newInstance("com.premiumminds.billy.platypus.services.export.saftpt.schema");
			this.marshaller = this.jaxbContext.createMarshaller();
		} catch (Exception e) {
			System.err.println("Problems with JAXBContext");
		}
	}

	/**
	 * Constructs a new SAFT a.k.a. AuditFile
	 * 
	 * @param targetStream
	 * 
	 * @param businessEntity
	 *            - the company
	 * @param application
	 * @param certificateNumber
	 * @param fromDate
	 * @param toDate
	 * @param daoCustomer
	 * @param daoProduct
	 * @param daoPTTax
	 * @param daoPTGenericInvoice
	 * @param daoPTInvoice
	 * @param daoPTSimpleInvoice
	 * @param daoPTCreditNote
	 * @return the SAFT for that business entity, given lists of customers,
	 *         products, taxes and financial documents; depends on a period of
	 *         time
	 * @throws SAFTPTExportException
	 */
	public AuditFile generateSAFTFile(final OutputStream targetStream,
			final PTBusinessEntity businessEntity,
			final PTApplicationEntity application,
			final String certificateNumber, final Date fromDate,
			final Date toDate, final DAOPTCustomer daoCustomer,
			final DAOPTProduct daoProduct, final DAOPTTax daoPTTax,
			final DAOPTGenericInvoice daoPTGenericInvoice,
			final DAOPTInvoice daoPTInvoice,
			final DAOPTSimpleInvoice daoPTSimpleInvoice,
			final DAOPTCreditNote daoPTCreditNote) throws SAFTPTExportException {

		try {
			return new TransactionWrapper<AuditFile>(daoPTGenericInvoice) {

				@Override
				public AuditFile runTransaction() throws Exception {
					AuditFile SAFTFile = new AuditFile();

					/* HEADER */
					Header hdr = generateHeader(businessEntity, application,
							certificateNumber, fromDate, toDate);
					SAFTFile.setHeader(hdr);

					/* MASTER FILES */
					MasterFiles mf = new MasterFiles();

					// Customers
					@SuppressWarnings("unchecked")
					List<PTCustomerEntity> customers = (List<PTCustomerEntity>) (List<?>) daoCustomer
							.getAllActiveCustomers();
					for (PTCustomerEntity customer : customers) {
						Customer SAFTCustomer = generateCustomer(customer);
						mf.getGeneralLedgerOrCustomerOrSupplier().add(
								SAFTCustomer);
					}

					// Products
					@SuppressWarnings("unchecked")
					List<PTProductEntity> products = (List<PTProductEntity>) (List<?>) daoProduct
							.getAllActiveProducts();
					for (PTProductEntity prod : products) {
						Product SAFTProduct = generateProduct(prod);
						mf.getGeneralLedgerOrCustomerOrSupplier().add(
								SAFTProduct);
					}

					// Taxes
					@SuppressWarnings("unchecked")
					List<PTTaxEntity> taxes = (List<PTTaxEntity>) (List<?>) daoPTTax
							.getAllTaxes();
					TaxTable SAFTTaxTable = generateTaxTable(taxes);
					mf.getGeneralLedgerOrCustomerOrSupplier().add(SAFTTaxTable);
					SAFTFile.setMasterFiles(mf);

					/* SOURCE DOCUMENTS */
					// List<PTInvoiceEntity> invoices = daoPTInvoice
					// .getBusinessInvoicesForSAFTPT(
					// businessEntity.getUUID(), fromDate, toDate);
					// List<PTSimpleInvoiceEntity> simpleInvoices =
					// daoPTSimpleInvoice
					// .getBusinessSimpleInvoicesForSAFTPT(
					// businessEntity.getUUID(), fromDate, toDate);
					// List<PTCreditNoteEntity> creditNotes = daoPTCreditNote
					// .getBusinessCreditNotesForSAFTPT(
					// businessEntity.getUUID(), fromDate, toDate);
					//
					// SourceDocuments sd = generateSourceDocuments(
					// invoices == null ? new ArrayList<PTInvoiceEntity>()
					// : invoices,
					// simpleInvoices == null ? new
					// ArrayList<PTSimpleInvoiceEntity>()
					// : simpleInvoices,
					// creditNotes == null ? new ArrayList<PTCreditNoteEntity>()
					// : creditNotes);
					// SAFTFile.setSourceDocuments(sd);

					exportSAFTFile(SAFTFile, targetStream);

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
	 */
	private void exportSAFTFile(AuditFile auditFile, OutputStream targetStream) {
		try {
			this.marshaller.setProperty("jaxb.encoding", FILE_ENCODING);
			this.marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
			this.marshaller.marshal(auditFile, targetStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates the Header of SAFT file (Table 1)
	 * 
	 * @param businessEntity
	 *            - the company
	 * @param startDate
	 *            , endDate - the period for the SAFT file
	 * @return the Header
	 * @throws DatatypeConfigurationException
	 *             - problems with dates
	 * @throws RequiredFieldNotFoundException
	 *             - if a required field in the SAFT file could not be assigned
	 * @throws InvalidContactTypeException
	 */
	private Header generateHeader(PTBusinessEntity businessEntity,
			PTApplicationEntity application, String certificateNumber,
			Date startDate, Date endDate)
			throws DatatypeConfigurationException,
			RequiredFieldNotFoundException, InvalidContactTypeException {
		this.context = "Header.";

		Header hdr = new Header();
		hdr.setAuditFileVersion(validateString("AuditFileVersion",
				XML_SCHEMA_VERSION, MAX_LENGTH_10, true));
		hdr.setCompanyID(validateString("CompanyID",
				businessEntity.getFinancialID(), MAX_LENGTH_50, true));
		hdr.setTaxRegistrationNumber(validateInteger("TaxRegistrationNumber",
				businessEntity.getFinancialID(), MAX_LENGTH_9, true));
		hdr.setTaxAccountingBasis(validateString("TaxAccountBasis",
				TAX_ACCOUNTING_BASIS, MAX_LENGTH_1, true));
		hdr.setCompanyName(validateString("CompanyName",
				businessEntity.getName(), MAX_LENGTH_60, true));

		if ((optionalParam = validateString("BusinessName",
				businessEntity.getCommercialName(), MAX_LENGTH_60, false))
				.length() > 0) {
			hdr.setBusinessName(optionalParam);
		}

		hdr.setCompanyAddress(generateAddressStructurePT((AddressEntity) businessEntity
				.getAddress()));

		hdr.setFiscalYear(getDateField(startDate, Calendar.YEAR));
		hdr.setStartDate(formatDate(startDate));
		hdr.setEndDate(formatDate(endDate));
		hdr.setCurrencyCode(validateString("CurrencyCode", CURRENCY_CODE,
				MAX_LENGTH_3, true));
		hdr.setDateCreated(getXMLGregorianCalendarNow());

		hdr.setTaxEntity(validateString("TaxEntity", TAX_ENTITY, MAX_LENGTH_20,
				true));
		hdr.setProductCompanyTaxID(validateString("ProductCompanyTaxID",
				application.getDeveloperCompanyTaxIdentifier(), MAX_LENGTH_20,
				true));
		hdr.setSoftwareCertificateNumber(validateString(
				"SoftwareCertificateNumber", certificateNumber, MAX_LENGTH_20,
				true));
		hdr.setProductID(validateString("ProductID", application.getName()
				+ "/" + application.getDeveloperCompanyName(), MAX_LENGTH_255,
				true));
		hdr.setProductVersion(validateString("ProductVersion",
				application.getVersion(), MAX_LENGTH_30, true));

		List<PTContactEntity> list = businessEntity.getContacts();
		setContacts(hdr, list);

		return hdr;
	}

	/**
	 * Generates an instance of Customer to be inserted in the table 2.2
	 * (Customer )
	 * 
	 * @param customerEntity
	 *            - the customer
	 * @return an instance of Customer
	 * @throws RequiredFieldNotFoundException
	 * @throws InvalidContactTypeException
	 */
	@SuppressWarnings("unchecked")
	private Customer generateCustomer(PTCustomerEntity customerEntity)
			throws RequiredFieldNotFoundException, InvalidContactTypeException {
		this.context = "Customer.";
		Customer customer = new Customer();
		if (config.getUID(Key.Customer.Generic.UUID).equals(
				customerEntity.getUID())) {
			// updateCustomerGeneralInfo(customer, "Consumidor final",
			// "999999990", "Consumidor final", new PTAddressEntity(
			// "Desconhecido", "Desconhecido", "", "",
			// "Desconhecido", "", "Desconhecido"));
		} else {
			updateCustomerGeneralInfo(customer, customerEntity.getUID()
					.getValue(), customerEntity.getTaxRegistrationNumber(),
					customerEntity.getName(),
					(PTAddressEntity) customerEntity.getBillingAddress());

			if ((optionalParam = validateString("Contact",
					customerEntity.getReferralName(), MAX_LENGTH_50, false))
					.length() > 0) {
				customer.setContact(optionalParam);
			}

			if (customerEntity.getShippingAddress() != null) {
				customer.setShipToAddress(generateAddressStructure((PTAddressEntity) customerEntity
						.getShippingAddress()));
			}
			setContacts(customer,
					(List<PTContactEntity>) (List<?>) customerEntity
							.getContacts());
		}

		customer.setAccountID(validateString("AccountID", ACCOUNT_ID,
				MAX_LENGTH_30, true));
		customer.setSelfBillingIndicator(validateInteger(
				"SelfBillingIndicator", SELF_BILLING_INDICATOR, MAX_LENGTH_1,
				true));

		return customer;
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
		product.setProductType(validateString("ProductType",
				getProductType(productEntity.getType()), MAX_LENGTH_1, true));

		product.setProductCode(validateString("ProductCode", productEntity
				.getUID().getValue(), MAX_LENGTH_30, true));

		product.setProductGroup(validateString("ProductGroup",
				productEntity.getProductGroup(), MAX_LENGTH_50, false));

		product.setProductDescription(validateString("ProductDescription",
				productEntity.getDescription(), MAX_LENGTH_200, true));

		if (productEntity.getNumberCode() == null
				|| productEntity.getNumberCode().length() == 0) {
			product.setProductNumberCode(validateString("ProductCode",
					productEntity.getUID().getValue(), MAX_LENGTH_50, true));
		} else {
			product.setProductNumberCode(validateString("ProductCode",
					productEntity.getNumberCode(), MAX_LENGTH_50, true));
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
			throws DatatypeConfigurationException,
			RequiredFieldNotFoundException, InvalidTaxTypeException,
			InvalidTaxCodeException {
		this.context = "TaxTable.";

		TaxTable taxTable = new TaxTable();
		for (PTTaxEntity taxEntity : taxEntities) {
			TaxTableEntry tte = new TaxTableEntry();
			tte.setTaxType(validateString("TaxType", getTaxType(taxEntity),
					MAX_LENGTH_3, true));
			tte.setTaxCode(validateString("TaxCode", getTaxCode(taxEntity),
					MAX_LENGTH_10, true));
			tte.setTaxCountryRegion(validateString("TaxCountryRegion",
					getRegionCodeFromISOCode(((PTRegionContext) taxEntity
							.getContext()).getRegionCode()), MAX_LENGTH_5, true));

			/* TODO STAMP DUTY NOT SUPPORTED IN THIS VERSION */
			tte.setDescription(validateString("Description",
					taxEntity.getDesignation(), MAX_LENGTH_255, true));

			if (taxEntity.getValidTo() != null) {
				tte.setTaxExpirationDate(formatDate(taxEntity.getValidTo()));
			}

			// if (taxEntity.getTaxRateType().equals(TaxType.STAMP_DUTY)
			// && taxEntity.getUnit().equals(Unit.VALUE)) {
			// tte.setTaxAmount(taxEntity.getValue());
			// } else if (taxEntity.getType().equals(TaxType.VAT)
			// && taxEntity.getUnit().equals(Unit.PERCENTAGE)) {
			// tte.setTaxPercentage(taxEntity.getValue());
			// }

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
	 */
	// private SourceDocuments generateSourceDocuments(List<IPTInvoiceEntity>
	// invoices, List<IPTSimpleInvoiceEntity> simpleInvoices,
	// List<IPTCreditNoteEntity> creditNotes)
	// throws DatatypeConfigurationException, RequiredFieldNotFoundException,
	// InvalidDocumentTypeException, InvalidDocumentStateException,
	// InvalidTaxTypeException,
	// InvalidTaxCodeException, InvalidPaymentMechanismException
	// {
	// this.context = "SourceDocuments.";
	//
	// SourceDocuments srcDocs = new SourceDocuments();
	// SalesInvoices salesInvoices = new SalesInvoices();
	// salesInvoices.setNumberOfEntries(new
	// BigInteger(Integer.toString(invoices.size() + simpleInvoices.size() +
	// creditNotes.size())));
	//
	// BigDecimal totalDebit = BigDecimal.ZERO;
	// BigDecimal totalCredit = BigDecimal.ZERO;
	//
	// Invoice saftInvoice;
	// for(IPTInvoiceEntity invoice : invoices) {
	// saftInvoice = generateSAFTInvoice(invoice);
	// processDocument(saftInvoice, invoice, true);
	// salesInvoices.getInvoice().add(saftInvoice);
	//
	// if (!invoice.getDocumentState().equals(DocumentState.BILLED) &&
	// !invoice.getDocumentState().equals(DocumentState.VOID)) {
	// totalCredit = totalCredit.add(invoice.getNetTotal());
	// }
	// }
	//
	// for(IPTSimpleInvoiceEntity simpleInvoice : simpleInvoices) {
	// saftInvoice = generateSAFTInvoice(simpleInvoice);
	// processDocument(saftInvoice, simpleInvoice, true);
	// salesInvoices.getInvoice().add(saftInvoice);
	//
	// if (!simpleInvoice.getDocumentState().equals(DocumentState.BILLED) &&
	// !simpleInvoice.getDocumentState().equals(DocumentState.VOID)) {
	// totalCredit = totalCredit.add(simpleInvoice.getNetTotal());
	// }
	// }
	//
	// for(IPTCreditNoteEntity creditNote : creditNotes) {
	// saftInvoice = generateSAFTInvoice(creditNote);
	// processDocument(saftInvoice, creditNote, false);
	// salesInvoices.getInvoice().add(saftInvoice);
	//
	// if (!creditNote.getDocumentState().equals(DocumentState.BILLED) &&
	// !creditNote.getDocumentState().equals(DocumentState.VOID)) {
	// totalDebit = totalDebit.add(creditNote.getNetTotal());
	// }
	// }
	//
	// salesInvoices.setTotalDebit(validateBigDecimal(totalDebit));
	// salesInvoices.setTotalCredit(validateBigDecimal(totalCredit));
	// srcDocs.setSalesInvoices(salesInvoices);
	//
	// return srcDocs;
	// }

	/**
	 * Generates a financial document that will be inserted in the
	 * SourceDocuments table
	 * 
	 * @param document
	 *            - can be an invoice, simple invoice or credit note
	 * @param documentType
	 * @return an instance of Invoice (represents a financial document in the
	 *         SAFT XML context)
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws RequiredFieldNotFoundException
	 * @throws InvalidDocumentTypeException
	 * @throws InvalidDocumentStateException
	 */
	// private Invoice generateSAFTInvoice(IPTFinancialDocumentEntity document)
	// throws DatatypeConfigurationException, RequiredFieldNotFoundException,
	// InvalidDocumentTypeException, InvalidDocumentStateException
	// {
	// Invoice saftInv = new Invoice();
	//
	// saftInv.setInvoiceNo(validateString("InvoiceNo",
	// document.getDocumentNumberSAFTPT(), MAX_LENGTH_60, true));
	// saftInv.setInvoiceStatus(validateString("InvoiceStatus",
	// getDocumentStatus(document), MAX_LENGTH_1, true));
	// saftInv.setInvoiceType(validateString("InvoiceType",
	// Documents.getDocumentTypeAcronym(document.getDocumentNumberSAFTPT()),
	// MAX_LENGTH_2, true));
	// saftInv.setHash(validateString("Hash",
	// Base64.encodeBase64String(document.getHash()), MAX_LENGTH_200, true));
	//
	// saftInv.setPeriod(validateInteger("Period",
	// Integer.toString(getDateField(document.getIssueDate(), Calendar.MONTH)),
	// MAX_LENGTH_2, true));
	// saftInv.setInvoiceDate(formatDate(document.getIssueDate()));
	// saftInv.setSelfBillingIndicator(validateInteger("SelfBillingIndicator",
	// document.isSelfBilling() ? "1" : "0", MAX_LENGTH_1, true));
	// saftInv.setSystemEntryDate(formatDateTime(document.getCreateTimestamp()));
	// UUID customerUUID = document.getCustomer().getUUID();
	// String customerID =
	// customerUUID.equals(config.getUUID(Config.Key.Customer.Generic.UUID)) ?
	// "Consumidor final" : customerUUID.toString();
	//
	// saftInv.setCustomerID(validateString("CustomerID", customerID,
	// MAX_LENGTH_30, true));
	//
	// /* NOT REQUIRED */
	// if (document.getDeliveryDestinationAddress() != null) {
	// saftInv.setShipTo(getShippingPointStructure(document.getDeliveryId(),
	// document.getDeliveryDate(), document.getDeliveryDestinationAddress()));
	// }
	// if (document.getDeliveryOriginAddress() != null) {
	// saftInv.setShipFrom(getShippingPointStructure(document.getDeliveryOriginId(),
	// document.getDeliveryShippingDate(),
	// document.getDeliveryOriginAddress()));
	// }
	//
	// return saftInv;
	// }

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
	 * @param isCredit
	 *            - to distinguish between invoices/simple invoices (credit
	 *            transactions) and credit notes (debit transaction)
	 * 
	 * @throws RequiredFieldNotFoundException
	 * @throws DatatypeConfigurationException
	 * @throws InvalidDocumentTypeException
	 * @throws InvalidTaxTypeException
	 * @throws InvalidTaxCodeException
	 * @throws InvalidPaymentMechanismException
	 */
	// private void processDocument(Invoice saftInvoice,
	// IPTFinancialDocumentEntity document, boolean isCredit)
	// throws RequiredFieldNotFoundException, DatatypeConfigurationException,
	// InvalidDocumentTypeException, InvalidTaxTypeException,
	// InvalidTaxCodeException, InvalidPaymentMechanismException
	// {
	// List<? extends IPTFinancialDocumentEntryEntity> entries =
	// document.getDocumentEntries();
	// if(entries == null || entries.size() < 1) {
	// throw new RequiredFieldNotFoundException(this.context + "Line");
	// }
	//
	// for (IPTFinancialDocumentEntryEntity entry : entries)
	// {
	// /* REQUIRED - One Invoice.Line per IPTFinancialDocumentEntryEntity */
	// Line line = new Line();
	// line.setLineNumber(new
	// BigInteger(Integer.toString(entry.getSequenceNumber() + 1)));
	//
	// /* NOT REQUIRED - Invoice.Line.OrderReferences */
	// OrderReferences or = getOrderReferencesForDocumentEntry(entry);
	// if (or != null) {
	// line.getOrderReferences().add(or);
	// }
	//
	// /* REQUIRED */
	// line.setProductCode(validateString("ProductCode",
	// validateUUID(entry.getProduct().getUUID()), MAX_LENGTH_30, true));
	// line.setProductDescription(validateString("ProductDescription",
	// entry.getProduct().getDescription(), MAX_LENGTH_60, true));
	// line.setQuantity(validateBigDecimal(entry.getProductQuantity()));
	// line.setUnitOfMeasure(validateString("UnitOfMeasure", UNIT_OF_MEASURE,
	// MAX_LENGTH_20, true));
	// line.setUnitPrice(validateBigDecimal(entry.getNetTotal().divide(entry.getProductQuantity(),
	// RoundingMode.HALF_UP)));
	// line.setTaxPointDate(formatDate(entry.getTaxPointDate()));
	//
	// /* NOT REQUIRED - Invoice.Line.References */
	// References ref = getReferencesForDocumentEntry(entry, document);
	// if (ref != null) {
	// line.setReferences(ref);
	// }
	//
	// /* REQUIRED */
	// line.setDescription(validateString("Description", entry.getDescription(),
	// MAX_LENGTH_60, true));
	// if (isCredit) {
	// line.setCreditAmount(validateBigDecimal(entry.getNetTotal()));
	// } else {
	// line.setDebitAmount(validateBigDecimal(entry.getNetTotal()));
	// }
	//
	// /* NOT REQUIRED Invoice.Line.Tax */
	// if (entry.getTaxes().size() > 0)
	// {
	// IPTTaxEntity taxEntity = (IPTTaxEntity) entry.getTaxes().get(0);
	// Tax tax = new Tax();
	// tax.setTaxType(validateString("TaxType", getTaxType(taxEntity),
	// MAX_LENGTH_3, true));
	// tax.setTaxCode(validateString("TaxCode", getTaxCode(taxEntity),
	// MAX_LENGTH_10, true));
	// tax.setTaxCountryRegion(validateString("TaxCountryRegion",
	// getRegionCodeFromISOCode(taxEntity.getRegionContext().getRegionCode()),
	// MAX_LENGTH_5, true));
	//
	// if (taxEntity.getType().equals(TaxType.STAMP_DUTY) &&
	// taxEntity.getUnit().equals(Unit.VALUE)) {
	// tax.setTaxAmount(validateBigDecimal(taxEntity.getValue()));
	// } else if (taxEntity.getType().equals(TaxType.VAT) &&
	// taxEntity.getUnit().equals(Unit.PERCENTAGE)) {
	// tax.setTaxPercentage(taxEntity.getValue());
	// }
	// line.setTax(tax);
	//
	// if ((tax.getTaxPercentage() != null &&
	// tax.getTaxPercentage().equals(BigDecimal.ZERO)) || (tax.getTaxAmount() !=
	// null && tax.getTaxAmount().equals(BigDecimal.ZERO))) {
	// line.setTaxExemptionReason(validateString("TaxExemptionReason",
	// entry.getExemptionLegalMotiveDescription(), MAX_LENGTH_60, true));
	// }
	// }
	//
	// /* NOT REQUIRED */
	// if (entry.getDiscountTotal() != null &&
	// !entry.getDiscountTotal().equals(BigDecimal.ZERO)) {
	// line.setSettlementAmount(entry.getDiscountTotal());
	// }
	//
	// saftInvoice.getLine().add(line);
	// }
	//
	// /* NOT REQUIRED */
	// DocumentTotals dt = getDocumentTotals(document, isCredit);
	// if (dt != null) {
	// saftInvoice.setDocumentTotals(dt);
	// }
	// }

	/**
	 * Generates the field OrderReferences (4.1.4.14.2) of document entry
	 * 
	 * @param entry
	 * @return an instance of OrderReferences
	 * 
	 * @throws RequiredFieldNotFoundException
	 * @throws DatatypeConfigurationException
	 */
	// private OrderReferences
	// getOrderReferencesForDocumentEntry(IPTFinancialDocumentEntryEntity entry)
	// throws RequiredFieldNotFoundException, DatatypeConfigurationException
	// {
	// OrderReferences or = new OrderReferences();
	// if ((optionalParam = validateString("OriginatingON", entry.getOrderId(),
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
	 * Generates the field References (4.1.4.14.9) of document entry
	 * 
	 * @param entry
	 * @param document
	 * @return an instance of References
	 * @throws RequiredFieldNotFoundException
	 * @throws DatatypeConfigurationException
	 * @throws InvalidDocumentTypeException
	 */
	// private References
	// getReferencesForDocumentEntry(IPTFinancialDocumentEntryEntity entry,
	// IPTFinancialDocumentEntity document) throws
	// RequiredFieldNotFoundException, DatatypeConfigurationException,
	// InvalidDocumentTypeException
	// {
	// References ref = null;
	// IPTFinancialDocumentEntity referencedDocument =
	// entry.getReferencedDocument();
	//
	// /* FIXME ONLY SUPPORTS ONE REFERENCED DOCUMENT */
	// if (referencedDocument != null)
	// {
	// ref = new References();
	// CreditNote cn = new CreditNote();
	// String reasonForCredit;
	//
	// if (document.getType().equals(DocumentType.CREDIT_NOTE)) {
	// /* then referencedDocument is an invoice/simple invoice */
	// reasonForCredit = ((IPTCreditNoteEntity) document).getReasonForCredit();
	// } else {
	// /* then referencedDocument is a credit note */
	// reasonForCredit = ((IPTCreditNoteEntity)
	// referencedDocument).getReasonForCredit();
	// }
	//
	// if ((optionalParam = validateString("References.CreditNote.Reference",
	// referencedDocument.getDocumentNumberSAFTPT(), MAX_LENGTH_60,
	// false)).length() > 0) {
	// cn.setReference(optionalParam);
	// }
	//
	// if ((optionalParam = validateString("References.CreditNote.Reason",
	// reasonForCredit, MAX_LENGTH_50, false)).length() > 0) {
	// cn.setReason(optionalParam);
	// }
	//
	// ref.setCreditNote(cn);
	// }
	//
	// return ref;
	// }

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
	// private ShippingPointStructure getShippingPointStructure(String
	// deliveryID, Date deliveryDate, AddressEntity address) throws
	// RequiredFieldNotFoundException, DatatypeConfigurationException
	// {
	// ShippingPointStructure sps = new ShippingPointStructure();
	//
	// if((optionalParam = validateString("DeliveryID", deliveryID,
	// MAX_LENGTH_30, false)).length() > 0) {
	// sps.setDeliveryID(optionalParam);
	// }
	// if(deliveryDate != null) {
	// sps.setDeliveryDate(formatDate(deliveryDate));
	// }
	// sps.setAddress(generateAddressStructure(address));
	//
	// return sps;
	// }

	/**
	 * Generates the field Currency (4.1.4.15.4) of DocumentTotals of a SAFT
	 * Invoice
	 * 
	 * @param document
	 * @param isCredit
	 * @return
	 */
	// private Currency getCurrency(IPTFinancialDocumentEntity document, boolean
	// isCredit)
	// {
	// Currency cur = null;
	//
	// if (!document.getCurrency().getCurrencyCode().equals(CURRENCY_CODE))
	// {
	// cur = new Currency();
	// cur.setCurrencyCode(document.getCurrency().getCurrencyCode());
	// if (isCredit) {
	// cur.setCurrencyCreditAmount(validateBigDecimal(document.getNetTotal()));
	// } else {
	// cur.setCurrencyDebitAmount(validateBigDecimal(document.getNetTotal()));
	// }
	// }
	//
	// return cur;
	// }

	/**
	 * Generates the field Settlement (4.1.4.15.5) of DocumentTotals of a SAFT
	 * Invoice
	 * 
	 * @param document
	 * @return
	 * @throws RequiredFieldNotFoundException
	 * @throws DatatypeConfigurationException
	 * @throws InvalidPaymentMechanismException
	 */
	// private Settlement getSettlement(IPTFinancialDocumentEntity document)
	// throws RequiredFieldNotFoundException, DatatypeConfigurationException,
	// InvalidPaymentMechanismException
	// {
	// if(document.getSettlementTotal() != null) {
	// Settlement settlement = new Settlement();
	// settlement.setSettlementAmount(validateBigDecimal(document.getSettlementTotal()));
	// settlement.setSettlementDate(formatDate(document.getSettlementDate()));
	// if ((optionalParam = validateString("SettlementDiscount",
	// document.getSettlementDescription(), MAX_LENGTH_30, false)).length() > 0)
	// {
	// settlement.setSettlementDiscount(optionalParam);
	// }
	// if ((optionalParam = validateString("PaymentMechanism",
	// getPaymentMechanism(document.getPaymentMechanism()), MAX_LENGTH_2,
	// false)).length() > 0) {
	// settlement.setPaymentMechanism(optionalParam);
	// }
	//
	// if (settlement.getSettlementAmount() != null &&
	// !settlement.getSettlementAmount().equals(validateBigDecimal(BigDecimal.ZERO)))
	// {
	// return settlement;
	// } else {
	// return null;
	// }
	// }
	// return null;
	// }

	/**
	 * Generates the DocumentTotals (4.1.4.15) of a SAFT Invoice
	 * 
	 * @param document
	 * @param isCredit
	 * @return
	 * @throws RequiredFieldNotFoundException
	 * @throws DatatypeConfigurationException
	 * @throws InvalidPaymentMechanismException
	 */
	// private DocumentTotals getDocumentTotals(IPTFinancialDocumentEntity
	// document, boolean isCredit) throws RequiredFieldNotFoundException,
	// DatatypeConfigurationException, InvalidPaymentMechanismException
	// {
	// DocumentTotals dt = null;
	//
	// if
	// (!validateBigDecimal(document.getNetTotal()).equals(validateBigDecimal(BigDecimal.ZERO)))
	// {
	// dt = new DocumentTotals(); //4.1.4.15
	// /* REQUIRED */
	// dt.setTaxPayable(validateBigDecimal(document.getTaxTotal()));
	// //4.1.4.15.1
	// dt.setNetTotal(validateBigDecimal(document.getNetTotal())); //4.1.4.15.2
	// dt.setGrossTotal(validateBigDecimal(document.getGrossTotal()));
	// //4.1.4.15.3
	//
	// /* NOT REQUIRED - I guess it's only required if it has another currency*/
	// Currency cur = getCurrency(document, isCredit); //4.1.4.15.4
	// if (cur != null) {
	// dt.setCurrency(cur);
	// }
	// /* NOT REQUIRED - Invoice.DocumentTotals.Settlements */
	// Settlement stlment = getSettlement(document); //4.1.4.15.5
	// if (stlment != null) {
	// dt.setSettlement(stlment);
	// }
	// }
	//
	// return dt;
	// }

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
		customer.setCustomerTaxID(validateString("CustomerTaxID",
				customerFinancialID, MAX_LENGTH_20, true));

		customer.setCompanyName(validateString("CompanyName", companyName,
				MAX_LENGTH_60, true));

		customer.setCustomerID(validateString("CustomerId", customerID,
				MAX_LENGTH_30, true));

		customer.setBillingAddress(generateAddressStructure(address));
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
	 */
	// private String getDocumentStatus(IPTFinancialDocumentEntity document)
	// throws InvalidDocumentStateException
	// {
	// switch(document.getDocumentState())
	// {
	// case BILLED: return "F";
	// case NORMAL: return "N";
	// case SELF_BILLING: return "S";
	// case VOID: return "A";
	// default: throw new
	// InvalidDocumentStateException(document.getDocumentState().toString());
	// }
	// }

	/**
	 * Converts the payment mechanism to an acronym according to the SAFT file
	 * rules
	 * 
	 * @param pm
	 * @return
	 * @throws InvalidPaymentMechanismException
	 */
	// private String getPaymentMechanism(PaymentMechanism pm) throws
	// InvalidPaymentMechanismException
	// {
	// switch(pm)
	// {
	// case BANK_TRANSFER: return "TB";
	// case CASH: return "NU";
	// case CHECK: return "CH";
	// case CREDIT_CARD: return "CC";
	// case DEBIT_CARD: return "CD";
	// case RESTAURANT_TICKET: return "TR";
	// default: throw new InvalidPaymentMechanismException(pm.toString());
	// }
	// }

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

		if ((optionalParam = validateString("StreetName",
				addressEntity.getStreetName(), MAX_LENGTH_90, false)).length() > 0) {
			address.setStreetName(optionalParam);
		}
		if ((optionalParam = validateString("BuildingNumber",
				addressEntity.getNumber(), MAX_LENGTH_10, false)).length() > 0) {
			address.setBuildingNumber(optionalParam);
		}

		address.setAddressDetail(validateString("AddressDetail",
				addressEntity.getDetails(), MAX_LENGTH_60, true));

		address.setCity(validateString("City", addressEntity.getCity(),
				MAX_LENGTH_50, true));

		address.setPostalCode(validateString("PostalCode",
				addressEntity.getPostalCode(), MAX_LENGTH_20, true));

		address.setCountry(validateString("Country",
				addressEntity.getISOCountry(), MAX_LENGTH_12, true));

		if ((optionalParam = validateString("Region",
				addressEntity.getRegion(), MAX_LENGTH_50, false)).length() > 0) {
			address.setRegion(optionalParam);
		}
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

		if ((optionalParam = validateString("StreetName",
				addressEntity.getStreetName(), MAX_LENGTH_90, false)).length() > 0) {
			address.setStreetName(optionalParam);
		}
		if ((optionalParam = validateString("BuildingNumber",
				addressEntity.getNumber(), MAX_LENGTH_10, false)).length() > 0) {
			address.setBuildingNumber(optionalParam);
		}

		address.setAddressDetail(validateString("AddressDetail",
				addressEntity.getDetails(), MAX_LENGTH_60, true));

		address.setCity(validateString("City", addressEntity.getCity(),
				MAX_LENGTH_50, true));

		address.setPostalCode(validateString("PostalCode",
				addressEntity.getPostalCode(), MAX_LENGTH_8, true));

		address.setCountry(validateString("Country", COUNTRY_CODE,
				MAX_LENGTH_2, true));

		if ((optionalParam = validateString("Region",
				addressEntity.getRegion(), MAX_LENGTH_50, false)).length() > 0) {
			address.setRegion(optionalParam);
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
			if ((optionalParam = validateString("Email", ce.getEmail(),
					MAX_LENGTH_60, false)).length() > 0)
				hdr.setEmail(optionalParam);

			if ((optionalParam = validateString("Fax", ce.getFax(),
					MAX_LENGTH_20, false)).length() > 0)
				hdr.setFax(optionalParam);

			if ((optionalParam = validateString("Telephone", ce.getTelephone(),
					MAX_LENGTH_20, false)).length() > 0)
				hdr.setTelephone(optionalParam);

			else {
				if ((optionalParam = validateString("Telephone",
						ce.getMobile(), MAX_LENGTH_20, false)).length() > 0)
					hdr.setTelephone(optionalParam);
			}

			if ((optionalParam = validateString("Website", ce.getWebsite(),
					MAX_LENGTH_60, false)).length() > 0)
				hdr.setWebsite(optionalParam);
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
			if ((optionalParam = validateString("Email", ce.getEmail(),
					MAX_LENGTH_60, false)).length() > 0)
				customer.setEmail(optionalParam);

			if ((optionalParam = validateString("Fax", ce.getFax(),
					MAX_LENGTH_20, false)).length() > 0)
				customer.setFax(optionalParam);

			if ((optionalParam = validateString("Telephone", ce.getTelephone(),
					MAX_LENGTH_20, false)).length() > 0)
				customer.setTelephone(optionalParam);

			else {
				if ((optionalParam = validateString("Telephone",
						ce.getMobile(), MAX_LENGTH_20, false)).length() > 0)
					customer.setTelephone(optionalParam);
			}

			if ((optionalParam = validateString("Website", ce.getWebsite(),
					MAX_LENGTH_60, false)).length() > 0)
				customer.setWebsite(optionalParam);
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
			default:
				throw new InvalidTaxTypeException(entity.getTaxRateType()
						.toString());
		}
	}

	/**
	 * Converts the tax code to an acronym according to the SAFT file rules
	 * 
	 * @param entity
	 * @return
	 * @throws InvalidTaxCodeException
	 */
	private String getTaxCode(PTTaxEntity entity)
			throws InvalidTaxCodeException {

		/* TODO Check this with Francisco */
		switch (entity.getVATCode()) {
			case EXEMPT:
				return "ISE";
			case INTERMEDIATE:
				return "INT";
			case NORMAL:
				return "NOR";
			case OTHER:
				return "OUT";
			case REDUCED:
				return "RED";
			default:
				throw new InvalidTaxCodeException(entity.getVATCode()
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
				str = str.substring(0, maxLength);
				System.err.println("WARNING: the field " + this.context + field
						+ " has been truncated.");
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
		return Integer.parseInt(validateString(field, str, maxLength,
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
		return bd.setScale(MAX_LENGTH_2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * Validates an UUID, XOR-ing the most and the least significant bits and
	 * transforming it in a String
	 * 
	 * @param uuid
	 * @return
	 */
	private String validateUUID(UUID uuid) {
		return Long.toString(Math.abs(uuid.getMostSignificantBits()
				^ uuid.getLeastSignificantBits()));
	}

	/**
	 * Gets the today's date
	 * 
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	private XMLGregorianCalendar getXMLGregorianCalendarNow()
			throws DatatypeConfigurationException {
		return formatDate(new Date());
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
