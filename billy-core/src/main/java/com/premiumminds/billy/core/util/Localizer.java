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
package com.premiumminds.billy.core.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Localizer {

	private static Locale	currentLocale	= Locale.getDefault();

	private String			bundleBaseName;
	private ResourceBundle	bundle;

	public Localizer(String bundleBaseName) {
		this.bundleBaseName = bundleBaseName;
	}

	private ResourceBundle getBundle() {
		if (this.bundle == null
				|| !Localizer.currentLocale.equals(this.bundle.getLocale())) {
			this.bundle = ResourceBundle.getBundle(
					this.bundleBaseName, Localizer.currentLocale);
		}
		return this.bundle;
	}

	public String getString(String key) {
		try {
			return this.getBundle().getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public String getString(String key, Object... params) {
		try {
			return MessageFormat.format(this.getBundle().getString(key), params);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

}
