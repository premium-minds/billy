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
import java.util.List;

import com.premiumminds.billy.gin.services.export.InvoiceEntryData;
import com.premiumminds.billy.gin.services.export.ProductData;
import com.premiumminds.billy.gin.services.export.TaxData;

public class ESCreditReceiptEntryData extends InvoiceEntryData {
	
	private final ESReceiptData reference;

	public ESCreditReceiptEntryData(ProductData productCode, String description, BigDecimal quantity, BigDecimal taxAmount,
			BigDecimal unitAmountWithTax, BigDecimal amountWithTax, BigDecimal amountWithoutTax,
			List<TaxData> taxes, ESReceiptData reference) {
		super(productCode, description, quantity, taxAmount, unitAmountWithTax, amountWithTax, amountWithoutTax,
				taxes);
		
		this.reference = reference;
	}

	public ESReceiptData getReference() {
		return reference;
	}
	
}