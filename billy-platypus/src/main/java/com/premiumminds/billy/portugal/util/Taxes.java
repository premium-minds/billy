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
package com.premiumminds.billy.portugal.util;

import com.google.inject.Injector;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.Config.Key;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.services.entities.PTTax;

public class Taxes { // TODO

	public class Continent {

		public PTTax normal() {
			Config configuration = new Config();
			DAOPTTax dao = getInstance(DAOPTTax.class);
			return (PTTax) dao.get(configuration
					.getUID(Key.Context.Portugal.Continental.VAT.NORMAL_UUID));
		}

		public PTTax intermediate() {
			// TODO
			return null;
		}

		public PTTax reduced() {
			// TODO
			return null;
		}

	}

	public class Madeira {

		public PTTax normal() {
			// TODO
			return null;
		}

		public PTTax intermediate() {
			// TODO
			return null;
		}

		public PTTax reduced() {
			// TODO
			return null;
		}

	}

	public class Azores {

		public PTTax normal() {
			// TODO
			return null;
		}

		public PTTax intermediate() {
			// TODO
			return null;
		}

		public PTTax reduced() {
			// TODO
			return null;
		}

	}

	public PTTax exempt() {
		// TODO
		return null;
	}

	private final Continent continent;
	private final Madeira madeira;
	private final Azores azores;
	private final Injector injector;

	public Taxes(Injector injector) {
		continent = new Continent();
		madeira = new Madeira();
		azores = new Azores();
		this.injector = injector;
	}

	public Continent continent() {
		return continent;
	}

	public Madeira madeira() {
		return madeira;
	}

	public Azores azores() {
		return azores;
	}

	private <T> T getInstance(Class<T> clazz) {
		return injector.getInstance(clazz);
	}

}