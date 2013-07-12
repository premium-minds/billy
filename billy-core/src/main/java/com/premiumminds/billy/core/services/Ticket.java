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
package com.premiumminds.billy.core.services;

import java.io.Serializable;

import org.apache.commons.lang3.Validate;

import com.premiumminds.billy.core.Config;

public class Ticket implements Serializable {

	private static final long serialVersionUID = Config.SERIAL_VERSION;

	private Object value;

	public Ticket(Object value) {
		this.setValue(value);
	}

	public Object getValue() {
		return this.value;
	}

	private void setValue(Object value) {
		Validate.notNull(value);
		this.value = value;
	}

}
