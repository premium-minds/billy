/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.fixtures;

import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;

public class MockPTCreditNoteEntryEntity extends MockGenericInvoiceEntryEntity
	implements PTCreditNoteEntryEntity {

	private static final long	serialVersionUID	= 1L;
	private PTInvoice			reference;
	private String				reason;

	public MockPTCreditNoteEntryEntity() {

	}

	@Override
	public PTInvoice getReference() {
		return this.reference;
	}

	@Override
	public String getReason() {
		return this.reason;
	}

	@Override
	public void setReference(PTInvoice reference) {
		this.reference = reference;
	}

	@Override
	public void setReason(String reason) {
		this.reason = reason;
	}

}
