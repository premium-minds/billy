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
package com.premiumminds.billy.spain.services.documents.exceptions;

import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;

public class ESDocumentIssuingException extends DocumentIssuingException {

	private static final long	serialVersionUID	= 1L;

	public ESDocumentIssuingException() {
	}

	public ESDocumentIssuingException(String message) {
		super(message);
	}

	public ESDocumentIssuingException(Throwable t) {
		super(t);
	}

	public ESDocumentIssuingException(String message, Throwable t) {
		super(message, t);
	}
}
