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

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public boolean isBilled() {
		return billed;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public void setBilled(boolean billed) {
		this.billed = billed;
	}

}