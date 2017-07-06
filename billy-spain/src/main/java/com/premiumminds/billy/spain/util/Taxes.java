/**
 * Copyright (C) 2017 Premium Minds.
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
package com.premiumminds.billy.spain.util;

import com.google.inject.Injector;
import com.premiumminds.billy.spain.Config;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.services.entities.ESTax;
import com.premiumminds.billy.spain.services.persistence.ESTaxPersistenceService;

/**
 * Encapsulates all tax information for Billy ES-Module.
 */
public class Taxes {

	Config	configuration	= new Config();

	/**
	 * Provides Continent tax information
	 */
	public class Continent {

		/**
		 * @return Normal VAT value for Continent.
		 */
		public ESTax normal() {
			DAOESTax dao = Taxes.this.getInstance(DAOESTax.class);
			return (ESTax) dao
					.get(Taxes.this.configuration
							.getUID(Config.Key.Context.Spain.Continental.VAT.NORMAL_UUID));
		}

		/**
		 * @return Intermediate VAT value for Continent.
		 */
		public ESTax intermediate() {
			DAOESTax dao = Taxes.this.getInstance(DAOESTax.class);
			return (ESTax) dao
					.get(Taxes.this.configuration
							.getUID(Config.Key.Context.Spain.Continental.VAT.INTERMEDIATE_UUID));
		}

		/**
		 * @return Reduced VAT value for Continent.
		 */
		public ESTax reduced() {
			DAOESTax dao = Taxes.this.getInstance(DAOESTax.class);
			return (ESTax) dao
					.get(Taxes.this.configuration
							.getUID(Config.Key.Context.Spain.Continental.VAT.REDUCED_UUID));
		}

	}
	
	public class CanaryIslands{
		/**
		 * @return Normal IGIC value for the Canary Islands.
		 */
		public ESTax normal() {
			DAOESTax dao = Taxes.this.getInstance(DAOESTax.class);
			return (ESTax) dao
					.get(Taxes.this.configuration
							.getUID(Config.Key.Context.Spain.CanaryIslands.IGIC.NORMAL_UUID));
		}

		/**
		 * @return Intermediate IGIC value for the Canary Islands.
		 */
		public ESTax intermediate() {
			DAOESTax dao = Taxes.this.getInstance(DAOESTax.class);
			return (ESTax) dao
					.get(Taxes.this.configuration
							.getUID(Config.Key.Context.Spain.CanaryIslands.IGIC.INTERMEDIATE_UUID));
		}

		/**
		 * @return Reduced IGIC value for the Canary Islands.
		 */
		public ESTax reduced() {
			DAOESTax dao = Taxes.this.getInstance(DAOESTax.class);
			return (ESTax) dao
					.get(Taxes.this.configuration
							.getUID(Config.Key.Context.Spain.CanaryIslands.IGIC.REDUCED_UUID));
		}
	}


	/**
	 * @return Exemption tax value.
	 */
	public ESTax exempt() {
		DAOESTax dao = this.getInstance(DAOESTax.class);
		return (ESTax) dao.get(this.configuration
				.getUID(Config.Key.Context.Spain.TAX_EXEMPT_UUID));
	}

	private final Continent	continent;
	private final CanaryIslands canaryIslands;
	private final Injector	injector;
	private final ESTaxPersistenceService persistenceService;

	public Taxes(Injector injector) {
		this.continent = new Continent();
		this.canaryIslands = new CanaryIslands();
		this.injector = injector;
		this.persistenceService = getInstance(ESTaxPersistenceService.class);
	}

	/**
	 * @return Spanish tax information from {@link Continent} region.
	 */
	public Continent continent() {
		return this.continent;
	}
	
	/**
	 * @return Spanish tax information from {@link CanaryIslands} region.
	 */
	public CanaryIslands canaryIslands() {
		return this.canaryIslands;
	}

	public ESTaxPersistenceService persistence() {
		return this.persistenceService;
	}

	private <T> T getInstance(Class<T> clazz) {
		return this.injector.getInstance(clazz);
	}
}