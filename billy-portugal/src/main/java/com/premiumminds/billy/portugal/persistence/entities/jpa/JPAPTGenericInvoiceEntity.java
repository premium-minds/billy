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
package com.premiumminds.billy.portugal.persistence.entities.jpa;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.persistence.entities.jpa.JPAGenericInvoiceEntity;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoiceEntry;
import com.premiumminds.billy.portugal.util.PaymentMechanism;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "GENERIC_INVOICE")
public class JPAPTGenericInvoiceEntity extends JPAGenericInvoiceEntity
		implements PTGenericInvoiceEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "CANCELLED")
	protected Boolean cancelled;

	@Column(name = "EAC_CODE")
	protected String eacCode;

	@Column(name = "BILLED")
	protected Boolean billed;

	@Column(name = "CHANGE_REASON")
	protected String reason;

	@Column(name = "HASH")
	protected String hash;

	@Column(name = "SOURCE_HASH")
	protected String sourceHash;

	@Column(name = "HASH_CONTROL")
	protected String hashControl;

	@Column(name = "SOURCE_BILLING")
	protected SourceBilling sourceBilling;

	@Column(name = "INVOICE_TYPE")
	protected TYPE type;

	@Override
	public TYPE getType() {
		return this.type;
	}

	@Override
	public void setType(TYPE type) {
		this.type = type;
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
	public String getChangeReason() {
		return reason;
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
	public SourceBilling getSourceBilling() {
		return this.sourceBilling;
	}

	@Override
	public String getEACCode() {
		return eacCode;
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
	public void setChangeReason(String reason) {
		this.reason = reason;
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
	public void setHashControl(String hashControl) {
		this.hashControl = hashControl;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<? extends PTGenericInvoiceEntry> getEntries() {
		return (List<PTGenericInvoiceEntry>) super.getEntries();
	}

	@Override
	public void setSourceBilling(SourceBilling sourceBilling) {
		this.sourceBilling = sourceBilling;
	}

	@Override
	public void setEACCode(String eacCode) {
		this.eacCode = eacCode;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public PaymentMechanism getPaymentMechanism() {
		return (PaymentMechanism) super.getPaymentMechanism();
	}

}
