/**
 * Copyright (C) 2013 Premium Minds.
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

import java.util.ArrayList;
import java.util.List;

import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTPayment;

public class MockPTGenericInvoiceEntity extends MockGenericInvoiceEntity
	implements PTGenericInvoiceEntity {

	private static final long	serialVersionUID	= 1L;
	protected boolean			cancelled;
	protected boolean			billed;
	protected String			reason;
	protected String			hash;
	protected String			sourceHash;
	protected String			hashControl;
	protected SourceBilling		sourceBilling;
	protected String			eacCode;
	protected TYPE				type;
	protected List<PTPayment>	payments;

	@Override
	public TYPE getType() {
		return this.type;
	}

	@Override
	public void setType(TYPE type) {
		this.type = type;
	}

	@Override
	public SourceBilling getSourceBilling() {
		return this.sourceBilling;
	}

	@Override
	public void setSourceBilling(SourceBilling soureceBilling) {
		this.sourceBilling = soureceBilling;
	}

	public MockPTGenericInvoiceEntity() {
		this.payments = new ArrayList<PTPayment>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PTGenericInvoiceEntry> getEntries() {
		return (List<PTGenericInvoiceEntry>) (List<?>) super.getEntries();
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
	public String getHashControl() {
		return hashControl;
	}

	@Override
	public String getEACCode() {
		return eacCode;
	}

	@Override
	public String getChangeReason() {
		return reason;
	}

	@Override
	public void setHashControl(String hashControl) {
		this.hashControl = hashControl;
	}

	@Override
	public void setChangeReason(String reason) {
		this.reason = reason;
	}

	@Override
	public void setEACCode(String eacCode) {
		this.eacCode = eacCode;
	}

	@Override
	public List<PTPayment> getPayments() {
		return payments;
	}
}
