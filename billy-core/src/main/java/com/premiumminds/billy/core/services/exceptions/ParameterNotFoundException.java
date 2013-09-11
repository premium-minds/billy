/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy core.
 * 
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.services.exceptions;

public class ParameterNotFoundException extends DocumentIssuingException {

	private static final long	serialVersionUID	= 1L;

	public ParameterNotFoundException() {
	}

	public ParameterNotFoundException(String message) {
		super(message);
	}

	public ParameterNotFoundException(Throwable t) {
		super(t);
	}

	public ParameterNotFoundException(String message, Throwable t) {
		super(message, t);
	}
}
