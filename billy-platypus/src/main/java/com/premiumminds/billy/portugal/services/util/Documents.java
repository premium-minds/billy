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
package com.premiumminds.billy.portugal.services.util;

import com.premiumminds.billy.portugal.persistence.entities.IPTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTFinancialDocumentEntity;

public class Documents {
	
	public static String getDocumentNo(
			PTFinancialDocumentEntity doc) {
		if(doc instanceof IPTInvoiceEntity) {
			return getDocumentNo((IPTInvoiceEntity) doc);
		} else if(doc instanceof IPTSimpleInvoiceEntity) {
			return getDocumentNo((IPTSimpleInvoiceEntity) doc);
		} else if(doc instanceof IPTCreditNoteEntity) {
			return getDocumentNo((IPTCreditNoteEntity) doc);
		}
		throw new RuntimeException("Cannot get document number for doc : " + doc.getUUID().toString());
	}
	
	public static String getDocumentNo(IPTInvoiceEntity invoice)
	{
		return "FT " + getCommonInfo(invoice);
	}
	
	public static String getDocumentNo(IPTSimpleInvoiceEntity simpleInvoice)
	{
		return "FS " + getCommonInfo(simpleInvoice);
	}
	
	public static String getDocumentNo(IPTCreditNoteEntity creditNote)
	{
		return "NC " + getCommonInfo(creditNote);
	}
	
	/**
	 * @param documentNo The document number
	 * @return the document number in a specific format
	 */
	public static String getDocumentTypeAcronym(String documentNo)
	{
		return documentNo.substring(0, 2);
	}

	
	private static String getCommonInfo(IPTFinancialDocumentEntity document)
	{
		return document.getSequenceID() + "/" + document.getSequencialNumber();
	}

}
