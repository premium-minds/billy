/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy.
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
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTFinancialDocument;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTFinancialDocumentEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.IPTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTTaxEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity.DocumentState;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.converters.PTCreditNoteConverter;

public abstract class AbstractPTCreditNoteIssuingHandler extends
AbstractPTFinancialDocumentIssuingHandler {

	protected DAOPTCreditNote daoPTCreditNote;
	protected DAOContext daoContext;

	@Inject
	public AbstractPTCreditNoteIssuingHandler(
			DAOPTCreditNote daoPTCreditNote,
			DAOPTFinancialDocument daoPTFinancialDocument,
			DAOPTFinancialDocumentEntry daoPTFinancialDocumentEntry,
			DAOPTBusiness daoPTBusiness, DAOBusinessOffice daoBusinessOffice,
			DAOCustomer daoCustomer, DAOPTTax daoPTTax, DAOProduct daoProduct,
			DAOAddress daoAddress, DAOPTRegionContext daoPTRegionContext, DAOContext daoContext) {
		super(daoPTFinancialDocument, daoPTFinancialDocumentEntry,
				daoPTBusiness, daoBusinessOffice, daoCustomer, daoPTTax, daoProduct,
				daoAddress, daoPTRegionContext);

		this.daoPTCreditNote = daoPTCreditNote;
		this.daoContext = daoContext;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends FinancialDocument> T issue(final T document) throws DocumentIssuingException {
		try {
			return new TransactionWrapper<T>(daoPTCreditNote) {

				@Override
				public T runTransaction() throws Exception {
					PTCreditNote creditNote = (PTCreditNote) document;
					BillyValidator.validate(creditNote);

					PTCreditNoteConverter creditNoteConverter = new PTCreditNoteConverter(daoPTCreditNote, daoPTFinancialDocumentEntry, daoAddress) {

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

					//Build persistence entity 
					IPTCreditNoteEntity creditNoteEntity;
					try {
						creditNoteEntity = creditNoteConverter.toPersistence(creditNote);
					} catch (Exception e) {
						daoPTFinancialDocument.rollback();
						throw new DocumentIssuingException(e);
					}

					fillInDefaults(creditNoteEntity);
					creditNoteEntity.setPTRegionContext(getCreditNoteContext(creditNoteEntity));
					computeCreditNoteValues(creditNoteEntity);

					IPTCreditNoteEntity previousCreditNote = 
							daoPTCreditNote.getLastIssuedCreditNote(
									creditNoteEntity.getBusiness().getUUID(),
									creditNoteEntity.getSequenceID());

					long sequencialNumber = previousCreditNote == null ? 1 : previousCreditNote.getSequencialNumber() + 1;
					creditNoteEntity.setSequencialNumber(sequencialNumber);
					creditNoteEntity.setDocumentNumber(generateDocumentNumber(creditNoteEntity));
					creditNoteEntity.setDocumentState(DocumentState.NORMAL);

					byte[] hash = null;
					hash = generateHash(
							getPrivateKey(),
							getPublicKey(),
							creditNoteEntity.getIssueDate(),
							creditNoteEntity.getCreateTimestamp(),
							creditNoteEntity.getDocumentNumber(),
							creditNoteEntity.getGrossTotal(),
							previousCreditNote == null ? null : previousCreditNote.getHash());
					creditNoteEntity.setHash(hash);

					BillyValidator.validate(creditNoteEntity);
					
					for(IPTFinancialDocumentEntryEntity entry : creditNoteEntity.getDocumentEntries()) {
						entry = daoPTFinancialDocumentEntry.create(entry);
					}
					daoPTCreditNote.create(creditNoteEntity);

					T result = null;
					result = (T) creditNoteConverter.toService(creditNoteEntity); 
					return result;
				}
			}.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DocumentIssuingException(e);
		}
	}

	protected void fillInDefaults(IPTCreditNoteEntity creditNoteEntity) throws DocumentIssuingException {
		if(creditNoteEntity.getIssueDate() == null) {
			creditNoteEntity.setIssueDate(new Date());
		} else {
			IPTCreditNoteEntity previousCreditNote = daoPTCreditNote.getLastIssuedCreditNote(creditNoteEntity.getBusiness().getUUID(), creditNoteEntity.getSequenceID());
			if(previousCreditNote != null && previousCreditNote.getIssueDate().after(creditNoteEntity.getIssueDate())) {
				throw new DocumentIssuingException("There is already a document issued for a date after " + creditNoteEntity.getIssueDate().toString());
			}
		}

		//Entries
		for(IPTFinancialDocumentEntryEntity entry : creditNoteEntity.getDocumentEntries()) {
			if(entry.getTaxPointDate() == null) {
				entry.setTaxPointDate(creditNoteEntity.getIssueDate());
			}
		}
	}

	protected void computeCreditNoteValues(IPTCreditNoteEntity creditNoteEntity) {
		BigDecimal netTotal= new BigDecimal(0);
		BigDecimal taxTotal = new BigDecimal(0);
		BigDecimal settlementTotal = new BigDecimal(0); //TODO Settlements and discounts currently not supported
		BigDecimal grossTotal = new BigDecimal(0);

		for(IPTFinancialDocumentEntryEntity entry : creditNoteEntity.getDocumentEntries()) {
			computeEntryTaxes(entry, creditNoteEntity.getPTRegionContext());
			computeEntryValues(entry);
			netTotal = netTotal.add(entry.getNetTotal());
			taxTotal = taxTotal.add(entry.getTaxTotal());
			grossTotal = grossTotal.add(entry.getGrossTotal());
		}

		creditNoteEntity.setNetTotal(netTotal);
		creditNoteEntity.setTaxTotal(taxTotal);
		creditNoteEntity.setGrossTotal(grossTotal);
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

	private IPTRegionContextEntity getCreditNoteContext(IPTCreditNoteEntity creditNoteEntity) {
		IPTRegionContextEntity context = null;

		if(creditNoteEntity.getPTRegionContext() != null) { //Credit Note context
			context = creditNoteEntity.getPTRegionContext();
		} else if(creditNoteEntity.getBusinessOffice() != null) { //Office context
			context = (IPTRegionContextEntity) creditNoteEntity.getBusinessOffice().getOperationalContext();
		} else { //Business context
			context = (IPTRegionContextEntity) creditNoteEntity.getBusiness().getOperationalContext();
		}
		return context;
	}

	protected void computeEntryValues(IPTFinancialDocumentEntryEntity entry) {
		BigDecimal taxTotal = entry.getGrossTotal().subtract(entry.getNetTotal());
		entry.setTaxTotal(taxTotal);
	}


	@Override
	protected abstract String generateDocumentNumber(IPTFinancialDocumentEntity document);

	@Override
	protected abstract PrivateKey getPrivateKey();

	@Override
	protected abstract PublicKey getPublicKey();

}
