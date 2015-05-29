/**
 * Copyright (C) 2013 Premium Minds.
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
package com.premiumminds.billy.spain.test.fixtures;

import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntryEntity;
import com.premiumminds.billy.spain.persistence.entities.ESCreditReceiptEntryEntity;
import com.premiumminds.billy.spain.services.entities.ESReceipt;

public class MockESCreditReceiptEntryEntity extends MockGenericInvoiceEntryEntity
	implements ESCreditReceiptEntryEntity {

	private static final long	serialVersionUID	= 1L;
	private ESReceipt			reference;
	private String				reason;

	public MockESCreditReceiptEntryEntity() {

	}

	@Override
	public ESReceipt getReference() {
		return this.reference;
	}

	@Override
	public String getReason() {
		return this.reason;
	}

	@Override
	public void setReference(ESReceipt reference) {
		this.reference = reference;
	}

	@Override
	public void setReason(String reason) {
		this.reason = reason;
	}

}
