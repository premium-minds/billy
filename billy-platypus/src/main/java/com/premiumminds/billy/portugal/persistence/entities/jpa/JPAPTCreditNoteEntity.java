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

import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;

public class JPAPTCreditNoteEntity extends JPAPTInvoiceEntity implements
		PTCreditNoteEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "REFERENCE")
	protected String reference;

	@Column(name = "REASON")
	protected String reason;

	@Override
	public String getReason() {
		return reason;
	}

	@Override
	public String getReference() {
		return reference;
	}

	@Override
	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public void setReason(String reason) {
		this.reason = reason;
	}

}
