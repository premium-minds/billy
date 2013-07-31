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
package com.premiumminds.billy.portugal.services.documents.util;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.ParameterNotFoundException;

public class PTCreditNoteParamsImpl extends PTIssuingParamsImpl implements
		PTCreditNoteParams {

	@Override
	public void setCreditNoteRef(UID uid) {
		this.setParameter(Keys.INVOICE_SERIES, uid);

	}

	@Override
	public void setCreditNoteReason(String reason) {
		this.setParameter(Keys.CREDIT_NOTE_REASON, reason);

	}

	@Override
	public UID getCreditNoteRef() throws ParameterNotFoundException {
		return (UID) this.getParameter(Keys.INVOICE_REF_UID);
	}

	@Override
	public String getCreditNoteReason() throws ParameterNotFoundException {
		return (String) this.getParameter(Keys.CREDIT_NOTE_REASON);
	}
}
