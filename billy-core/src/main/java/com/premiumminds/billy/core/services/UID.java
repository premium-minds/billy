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
import java.util.UUID;

import com.premiumminds.billy.core.Config;

public class UID implements Serializable, Comparable<UID> {

	private static final long	serialVersionUID	= Config.SERIAL_VERSION;

	private String				value;

	public UID() {
		this.setValue(UUID.randomUUID().toString());
	}

	public UID(String value) {
		this.setValue(value);
	}

	private void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public int compareTo(UID other) {
		return this.getValue().compareTo(other.getValue());
	}

	@Override
	public boolean equals(Object obj) {
		return obj.toString().equals(this.toString());
	}

	@Override
	public String toString() {
		return this.value;
	}

}
