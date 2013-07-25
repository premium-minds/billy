/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy platypus (PT Pack).
 * 
 * billy platypus (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy platypus (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.certification;

public class InvalidHashException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidHashException() {
	}

	public InvalidHashException(String arg0) {
		super(arg0);
	}

	public InvalidHashException(Throwable arg0) {
		super(arg0);
	}

	public InvalidHashException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
