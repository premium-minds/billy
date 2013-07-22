package com.premiumminds.billy.portugal.persistence.entities.jpa;

import javax.persistence.Column;

import com.premiumminds.billy.core.persistence.entities.jpa.JPAGenericInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;

public class JPAPTInvoiceEntity extends JPAGenericInvoiceEntity implements
		PTInvoiceEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CANCELLED")
	protected Boolean cancelled;

	@Column(name = "BILLED")
	protected Boolean billed;

	@Column(name = "HASH")
	protected String hash;

	@Column(name = "SOURCE_HASH")
	protected String sourceHash;

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public boolean isBilled() {
		return billed;
	}

	@Override
	public String getHash() {
		return hash;
	}

	@Override
	public String getSourceHash() {
		return sourceHash;
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

}