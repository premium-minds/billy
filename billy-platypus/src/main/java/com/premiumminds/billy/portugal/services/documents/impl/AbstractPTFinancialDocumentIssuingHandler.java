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
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.apache.commons.codec.binary.Base64;

import com.premiumminds.billy.core.persistence.dao.DAOAddress;
import com.premiumminds.billy.core.persistence.dao.DAOBusinessOffice;
import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.entities.FinancialDocument;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTFinancialDocument;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTFinancialDocumentEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity;
import com.premiumminds.billy.portugal.services.certification.CertificationManager;
import com.premiumminds.billy.portugal.services.certification.InvalidHashException;

public abstract class AbstractPTFinancialDocumentIssuingHandler implements DocumentIssuingHandler {

	protected DAOPTFinancialDocument daoPTFinancialDocument;
	protected DAOPTFinancialDocumentEntry daoPTFinancialDocumentEntry;
	protected DAOPTBusiness daoPTBusiness;
	protected DAOBusinessOffice daoBusinessOffice;
	protected DAOCustomer daoCustomer;
	protected DAOPTTax daoPTTax;
	protected DAOProduct daoProduct;
	protected DAOAddress daoAddress;
	protected DAOPTRegionContext daoPTRegionContext;


	@Inject
	public AbstractPTFinancialDocumentIssuingHandler(
			DAOPTFinancialDocument daoPTFinancialDocument,
			DAOPTFinancialDocumentEntry daoPTFinancialDocumentEntry,
			DAOPTBusiness daoPTBusiness,
			DAOBusinessOffice daoBusinessOffice,
			DAOCustomer daoCustomer,
			DAOPTTax daoPTTax,
			DAOProduct daoProduct,
			DAOAddress daoAddress,
			DAOPTRegionContext daoPTRegionContext) {

		this.daoPTFinancialDocument = daoPTFinancialDocument;
		this.daoPTFinancialDocumentEntry = daoPTFinancialDocumentEntry;
		this.daoPTBusiness = daoPTBusiness;
		this.daoBusinessOffice = daoBusinessOffice;
		this.daoCustomer = daoCustomer;
		this.daoPTTax = daoPTTax;
		this.daoProduct = daoProduct;
		this.daoAddress = daoAddress;
		this.daoPTRegionContext = daoPTRegionContext;
	}

	@Override
	public abstract <T extends FinancialDocument> T issue(T document) throws DocumentIssuingException;

	protected static byte[] generateHash(
			@NotNull PrivateKey privateKey,
			@NotNull PublicKey publicKey,
			@NotNull Date invoiceDate,
			@NotNull Date systemEntryDate, 
			@NotNull String invoiceNumber, 
			@NotNull BigDecimal grossTotal, 
			byte[] previousInvoiceHash) throws InvalidKeySpecException, InvalidKeyException, InvalidHashException {
		BillyValidator.validate(privateKey, publicKey, invoiceDate, systemEntryDate, invoiceNumber, grossTotal);
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		StringBuilder builder = new StringBuilder();
		builder
		.append(date.format(invoiceDate))
		.append(';')
		.append(dateTime.format(systemEntryDate))
		.append(';')
		.append(invoiceNumber)
		.append(';')
		.append(grossTotal.setScale(2))
		.append(';')
		.append(previousInvoiceHash == null ? "" : Base64.encodeBase64String(previousInvoiceHash));

		String sourceString = builder.toString();

		CertificationManager certificationManager = new CertificationManager();
		certificationManager.setAutoVerifyHash(true);
		certificationManager.setPrivateKey(privateKey);
		certificationManager.setPublicKey(publicKey);

		return certificationManager.getHashBinary(sourceString);
	}
	
	protected abstract String generateDocumentNumber(IPTFinancialDocumentEntity document);
	
	protected abstract PrivateKey getPrivateKey();
	
	protected abstract PublicKey getPublicKey();

}
