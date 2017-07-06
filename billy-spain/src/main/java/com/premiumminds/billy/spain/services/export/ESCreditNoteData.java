/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.services.export;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.premiumminds.billy.gin.services.export.BusinessData;
import com.premiumminds.billy.gin.services.export.CostumerData;
import com.premiumminds.billy.gin.services.export.GenericInvoiceData;
import com.premiumminds.billy.gin.services.export.InvoiceEntryData;
import com.premiumminds.billy.gin.services.export.PaymentData;

public class ESCreditNoteData extends GenericInvoiceData {
	
	private final List<ESCreditNoteEntryData> entries;
	
	public ESCreditNoteData(String number, Date date, Date settlementDate, List<PaymentData> payments,
			CostumerData customer, BusinessData business, List<ESCreditNoteEntryData> entries, BigDecimal taxAmount,
			BigDecimal amountWithTax, BigDecimal amountWithoutTax, String settlementDescription) {
		
		super(number, date, settlementDate, payments, customer, business, new ArrayList<InvoiceEntryData>(0), taxAmount, amountWithTax,
				amountWithoutTax, settlementDescription);
		
		this.entries = entries;
	}
	
	@Override
	public List<ESCreditNoteEntryData> getEntries() {
		return entries;
	}
	
}
