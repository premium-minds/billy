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
package com.premiumminds.billy.spain;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import com.premiumminds.billy.core.services.UID;

public class Config {

	public static final String	TABLE_PREFIX				= "BILLY_ES_";

	private static final String	CONFIGURATIONS_FILE_NAME	= "spain.properties";

	private Properties			properties;

	public Config() {
		this.properties = this.load();
		this.validateProperties(this.properties);
	}

	private Properties load() {
		InputStream stream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(Config.CONFIGURATIONS_FILE_NAME);
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
		return; // TODO
	}

	public String get(String key) {
		return this.properties.getProperty(key);
	}

	public UID getUID(String key) {
		return new UID(this.getUUID(key).toString());
	}

	public UUID getUUID(String key) {
		return UUID.fromString(this.get(key));
	}

	/**
	 * Property access keys
	 */
	public static class Key {

		public static class Customer {

			public static class Generic {

				public static final String	UUID	= "customer.generic.uuid";
			}
		}

		public static class Contact {

			public static class Generic {

				public static final String	UUID	= "contact.generic.uuid";
			}
		}

		public static class Address {

			public static class Generic {

				public static final String	UUID	= "address.generic.uuid";
			}
		}

		public static class Context {

			public static class Spain {

				public static final String	UUID				= "context.spain.uuid";
				public static final String	TAX_EXEMPT_UUID		= "context.spain.tax.exempt.uuid";
				public static final String	TAX_EXEMPT_VALUE	= "context.spain.tax.exempt.value";

				public static class Continental {

					public static final String	UUID	= "context.spain.continental.uuid";

					public static class VAT {

						public static final String	NORMAL_UUID				= "context.spain.continental.tax.vat.normal.uuid";
						public static final String	NORMAL_PERCENT			= "context.spain.continental.tax.vat.normal.percent";

						public static final String	INTERMEDIATE_UUID		= "context.spain.continental.tax.vat.intermediate.uuid";
						public static final String	INTERMEDIATE_PERCENT	= "context.spain.continental.tax.vat.intermediate.percent";

						public static final String	REDUCED_UUID			= "context.spain.continental.tax.vat.reduced.uuid";
						public static final String	REDUCED_PERCENT			= "context.spain.continental.tax.vat.reduced.percent";
					}

					public static class Madrid {

						public static final String	UUID	= "context.spain.madrid.uuid";
					}
					
					// TODO Billy: Configure Spanish Context, including Taxes
					
				}
			}
		}
	}

}
