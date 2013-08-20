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
package com.premiumminds.billy.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.inject.Singleton;

@Singleton
public class Config {

	public static final long	SERIAL_VERSION				= -4303676531780572465L;
	public static final String	TABLE_PREFIX				= "BILLY_CORE_";

	private static final String	CONFIGURATIONS_FILE_NAME	= "";

	private Properties			properties;

	public Config() {
		this.properties = this.load();
		this.validateProperties(this.properties);
	}

	private Properties load() {
		InputStream stream = Thread
									.currentThread()
									.getContextClassLoader()
									.getResourceAsStream(
											Config.CONFIGURATIONS_FILE_NAME);
		Properties properties = new Properties();
		try {
			properties.load(stream);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return properties;
	};

	private void validateProperties(Properties properties) {
		// TODO
	}

}
