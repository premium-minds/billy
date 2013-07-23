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
