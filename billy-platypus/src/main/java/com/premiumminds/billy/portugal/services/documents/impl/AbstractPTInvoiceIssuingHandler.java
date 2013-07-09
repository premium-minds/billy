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
package com.premiumminds.billy.portugal.services.documents.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOAddress;
import com.premiumminds.billy.core.persistence.dao.DAOBusinessOffice;
import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.entities.BusinessOfficeEntity;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.persistence.entities.ProductEntity;
import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.persistence.entities.TaxEntity.Unit;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.FinancialDocument;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTFinancialDocument;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTFinancialDocumentEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.IPTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTTaxEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity.DocumentState;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.converters.PTInvoiceConverter;

public abstract class AbstractPTInvoiceIssuingHandler extends
AbstractPTFinancialDocumentIssuingHandler {

	protected DAOPTInvoice daoPTInvoice;
	protected DAOContext daoContext;

	@Inject
	public AbstractPTInvoiceIssuingHandler(DAOPTInvoice daoPTInvoice,
			DAOPTFinancialDocument daoPTFinancialDocument,
			DAOPTFinancialDocumentEntry daoPTFinancialDocumentEntry,
			DAOPTBusiness daoPTBusiness, DAOBusinessOffice daoBusinessOffice,
			DAOCustomer daoCustomer, DAOPTTax daoPTTax, DAOProduct daoProduct,
			DAOAddress daoAddress, DAOPTRegionContext daoPTRegionContext, DAOContext daoContext) {
		super(daoPTFinancialDocument, daoPTFinancialDocumentEntry,
				daoPTBusiness, daoBusinessOffice, daoCustomer, daoPTTax, daoProduct,
				daoAddress, daoPTRegionContext);

		this.daoPTInvoice = daoPTInvoice;
		this.daoContext = daoContext;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends FinancialDocument> T issue(final T document) throws DocumentIssuingException {
		try {
			return new TransactionWrapper<T>(daoPTInvoice) {

				@Override
				public T runTransaction() throws Exception {
					PTInvoice invoice = (PTInvoice) document;
					BillyValidator.validate(invoice);

					PTInvoiceConverter invoiceConverter = new PTInvoiceConverter(daoPTInvoice, daoPTFinancialDocumentEntry, daoAddress) {

						@Override
						protected ProductEntity getPersistenceProduct(UID productUID) {
							if(productUID != null) {
								return daoProduct.get(productUID.getValue());
							} else {
								return null;
							}
						}

						@Override
						protected List<IPTTaxEntity> getPersistencePTTaxes(List<UID> taxUIDs) {
							if(taxUIDs != null) {
								List<IPTTaxEntity> result = new ArrayList<IPTTaxEntity>();
								for(UID uid : taxUIDs) {
									IPTTaxEntity tax = daoPTTax.get(uid.getValue());
									result.add(tax);
								}
								return result;
							} else {
								return null;
							}
						}

						@Override
						protected IPTFinancialDocumentEntity getPersistencePTFinancialDocument(
								UID documentUID) {
							if(documentUID != null) {
								return daoPTFinancialDocument.get(documentUID.getValue());
							} else {
								return null;
							}
						}

						@Override
						protected CustomerEntity getPersistenceCustomer(UID customerUID) {
							if(customerUID != null) {
								return daoCustomer.get(customerUID.getValue());
							} else {
								return null;
							}
						}

						@Override
						protected BusinessOfficeEntity getPersistenceBusinessOffice(
								UID businessOfficeUID) {
							if(businessOfficeUID != null) {
								return daoBusinessOffice.get(businessOfficeUID.getValue());
							}
							return null;
						}

						@Override
						protected IPTBusinessEntity getPersistenceBusiness(UID businessUID) {
							return daoPTBusiness.get(businessUID.getValue());
						}

						@Override
						protected IPTRegionContextEntity getPersistenceRegionContext(
								UID contextUID) {
							if(contextUID == null) {
								return null;
							}
							return daoPTRegionContext.get(contextUID.getValue());
						}
					};

					//Build persistence invoice 
					IPTInvoiceEntity invoiceEntity;
					try {
						invoiceEntity = invoiceConverter.toPersistence(invoice);
					} catch (Exception e) {
						daoPTFinancialDocument.rollback();
						throw new DocumentIssuingException(e);
					}

					fillInDefaults(invoiceEntity);
					invoiceEntity.setPTRegionContext(getInvoiceContext(invoiceEntity));
					computeInvoiceValues(invoiceEntity);

					IPTInvoiceEntity previousInvoice = 
							daoPTInvoice.getLastIssuedInvoice(
									invoiceEntity.getBusiness().getUUID(),
									invoiceEntity.getSequenceID());

					long sequencialNumber = previousInvoice == null ? 1 : previousInvoice.getSequencialNumber() + 1;
					invoiceEntity.setSequencialNumber(sequencialNumber);
					invoiceEntity.setDocumentNumber(generateDocumentNumber(invoiceEntity));
					invoiceEntity.setDocumentState(DocumentState.NORMAL);

					byte[] hash = null;
					hash = generateHash(
							getPrivateKey(),
							getPublicKey(),
							invoiceEntity.getIssueDate(),
							invoiceEntity.getCreateTimestamp(),
							invoiceEntity.getDocumentNumber(),
							invoiceEntity.getGrossTotal(),
							previousInvoice == null ? null : previousInvoice.getHash());
					invoiceEntity.setHash(hash);

					BillyValidator.validate(invoiceEntity);

					for(IPTFinancialDocumentEntryEntity entry : invoiceEntity.getDocumentEntries()) {
						entry = daoPTFinancialDocumentEntry.create(entry);
					}
					daoPTInvoice.create(invoiceEntity);

					T result = null;
					result = (T) invoiceConverter.toService(invoiceEntity); 
					return result;
				}
			}.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DocumentIssuingException(e);
		}
	}

	protected void fillInDefaults(IPTInvoiceEntity invoiceEntity) throws DocumentIssuingException {
		if(invoiceEntity.getIssueDate() == null) {
			invoiceEntity.setIssueDate(new Date());
		} else {
			IPTInvoiceEntity previousInvoice = daoPTInvoice.getLastIssuedInvoice(invoiceEntity.getBusiness().getUUID(), invoiceEntity.getSequenceID());
			if(previousInvoice != null && previousInvoice.getIssueDate().after(invoiceEntity.getIssueDate())) {
				throw new DocumentIssuingException("There is already a document issued for a date after " + invoiceEntity.getIssueDate().toString());
			}
		}

		//Entries
		for(IPTFinancialDocumentEntryEntity entry : invoiceEntity.getDocumentEntries()) {
			if(entry.getTaxPointDate() == null) {
				entry.setTaxPointDate(invoiceEntity.getIssueDate());
			}
		}
	}

	protected void computeInvoiceValues(IPTInvoiceEntity invoiceEntity) {
		BigDecimal netTotal= new BigDecimal(0);
		BigDecimal taxTotal = new BigDecimal(0);
		BigDecimal settlementTotal = new BigDecimal(0); //TODO Settlements and discounts currently not supported
		BigDecimal grossTotal = new BigDecimal(0);

		for(IPTFinancialDocumentEntryEntity entry : invoiceEntity.getDocumentEntries()) {
			computeEntryTaxes(entry, invoiceEntity.getPTRegionContext());
			computeEntryValues(entry);
			netTotal = netTotal.add(entry.getNetTotal());
			taxTotal = taxTotal.add(entry.getTaxTotal());
			grossTotal = grossTotal.add(entry.getGrossTotal());
		}

		invoiceEntity.setNetTotal(netTotal);
		invoiceEntity.setTaxTotal(taxTotal);
		invoiceEntity.setGrossTotal(grossTotal);
	}

	private IPTRegionContextEntity getInvoiceContext(IPTInvoiceEntity invoiceEntity) {
		IPTRegionContextEntity context = null;
		
		if(invoiceEntity.getPTRegionContext() != null) { //Invoice context
			context = invoiceEntity.getPTRegionContext();
		} else if(invoiceEntity.getBusinessOffice() != null) { //Office context
			context = (IPTRegionContextEntity) invoiceEntity.getBusinessOffice().getOperationalContext();
		} else { //Business context
			context = (IPTRegionContextEntity) invoiceEntity.getBusiness().getOperationalContext();
		}
		return context;
	}

	protected void computeEntryTaxes(IPTFinancialDocumentEntryEntity entry, ContextEntity taxContext) {
		List<? extends TaxEntity> productTaxes = entry.getProduct().getTaxes();
		List<TaxEntity> entryTaxes = new ArrayList<TaxEntity>();

		for(TaxEntity tax : productTaxes) {
			if(tax.getContext().getUUID().equals(taxContext.getUUID())
					|| daoContext.isSubContext(taxContext, tax.getContext())) {
				entryTaxes.add(tax);
			}
		}

		if(entryTaxes.size() > 1) {
			throw new RuntimeException("Billy does not currently support invoice lines with more than 1 tax.");
		}
		entry.setTaxes(entryTaxes);
	}

	protected void computeEntryValues(IPTFinancialDocumentEntryEntity entry) {
		BigDecimal netTotal = new BigDecimal(0);
		BigDecimal taxTotal = new BigDecimal(0);
		BigDecimal settlementTotal = new BigDecimal(0); //TODO Settlements and discounts currently not supported
		BigDecimal grossTotal = new BigDecimal(0);

		if(entry.getNetTotal() != null) { //With nettotal
			netTotal = entry.getNetTotal();

			//Fixed value
			for(TaxEntity tax : entry.getTaxes()) {

				if(tax.getUnit() == Unit.VALUE) {
					taxTotal = taxTotal.add(tax.getValue());

				}else if(tax.getUnit() == Unit.PERCENTAGE) {
					taxTotal = taxTotal.add(tax.getValue().divide(new BigDecimal(100)).multiply(netTotal));
				}
				break; //Only one tax instance
			}
			grossTotal = netTotal.add(taxTotal);

		} else if(entry.getGrossTotal() != null) { //With grosstotal

			grossTotal = entry.getGrossTotal();

			//Fixed value
			for(TaxEntity tax : entry.getTaxes()) {

				if(tax.getUnit() == Unit.VALUE) {
					taxTotal = taxTotal.add(tax.getValue());
					netTotal = grossTotal.subtract(taxTotal);
				}else if(tax.getUnit() == Unit.PERCENTAGE) {
					netTotal = grossTotal.
							divide(
									BigDecimal.ONE.
									add(tax.getValue().divide(new BigDecimal("100"))), 2, RoundingMode.HALF_UP);
					taxTotal = taxTotal.add(tax.getValue().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP).
							multiply(netTotal));
				}
				break; //Only one tax instance
			}

		}

		entry.setNetTotal(netTotal);
		entry.setTaxTotal(taxTotal);
		entry.setGrossTotal(grossTotal);
	}

	@Override
	protected abstract String generateDocumentNumber(IPTFinancialDocumentEntity document);

	@Override
	protected abstract PrivateKey getPrivateKey();

	@Override
	protected abstract PublicKey getPublicKey();

}
