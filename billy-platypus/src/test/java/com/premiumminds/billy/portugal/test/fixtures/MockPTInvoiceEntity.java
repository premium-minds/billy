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
package com.premiumminds.billy.portugal.test.fixtures;

import java.util.List;

import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.util.PaymentMechanism;

public class MockPTInvoiceEntity extends MockGenericInvoiceEntity implements
		PTInvoiceEntity {

	private static final long serialVersionUID = 1L;

	protected Boolean cancelled;
	protected Boolean billed;
	protected String hash;
	protected String sourceHash;
	protected String sourceBilling;

	@Override
	public String getSourceBilling() {
		return sourceBilling;
	}

	@Override
	public void setSourceBilling(String soureceBilling) {
		this.sourceBilling = soureceBilling;
	}

	public MockPTInvoiceEntity() {

	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public void setBilled(boolean billed) {
		this.billed = billed;
	}

	@Override
	public void setHash(String hash) {
		this.hash = hash;
	}

	@Override
	public void setSourceHash(String source) {
		this.sourceHash = source;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public boolean isBilled() {
		return this.billed;
	}

	@Override
	public String getHash() {
		return this.hash;
	}

	@Override
	public String getSourceHash() {
		return this.sourceHash;
	}

	@Override
	public List<PTInvoiceEntry> getEntries() {
		return (List<PTInvoiceEntry>) (List<?>) super.getEntries();
	}
	
	public PaymentMechanism getPaymentMechanism(){
		return null;
	}
}
