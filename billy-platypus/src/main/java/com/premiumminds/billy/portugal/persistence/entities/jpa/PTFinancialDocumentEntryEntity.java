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
package com.premiumminds.billy.portugal.persistence.entities.jpa;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntryEntity;

@Entity
@Table(name = Config.TABLE_PREFIX + "FINANCIAL_DOCUMENT_ENTRY")
public class PTFinancialDocumentEntryEntity extends FinancialDocumentEntryEntity
implements IPTFinancialDocumentEntryEntity {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "ID_REFERENCED_DOCUMENT", referencedColumnName = "ID")
	private PTFinancialDocumentEntity referencedDocument;

	@Column(name = "EXEMPTION_MOTIVE", nullable = true, insertable = true, updatable = false)
	private String exemptionMotive;


	public PTFinancialDocumentEntryEntity(){}

	public PTFinancialDocumentEntryEntity(
			int sequenceNumber,
			ProductEntity product,
			String description,
			Date taxPointDate,
			BigDecimal productQuantity,
			BigDecimal productUnitPrice,
			BigDecimal netTotal,
			BigDecimal taxTotal,
			BigDecimal discountTotal,
			BigDecimal grossTotal,
			Currency currency,
			List<PTTaxEntity> taxes,
			PTFinancialDocumentEntity referencedDocument,			
			String exemptionMotive,
			String orderId,
			Date orderDate){

		super(sequenceNumber, product, description, taxPointDate, productQuantity, productUnitPrice, netTotal, taxTotal, discountTotal, grossTotal, currency, taxes, orderId, orderDate);
		
		setReferencedDocument(referencedDocument);
		setExemptionMotive(exemptionMotive);
	}


	//GETTERS

	@Override
	public IPTFinancialDocumentEntity getReferencedDocument() {
		return referencedDocument;
	}

	@Override
	public String getExemptionLegalMotiveDescription() {
		return exemptionMotive;
	}


	//SETTERS

	private void setReferencedDocument(PTFinancialDocumentEntity referencedDocument) {
		this.referencedDocument = referencedDocument;
	}
	
	private void setExemptionMotive(String motive) {
		this.exemptionMotive = motive;
	}

}
