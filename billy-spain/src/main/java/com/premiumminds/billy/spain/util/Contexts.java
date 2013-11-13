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
package com.premiumminds.billy.spain.util;

import com.google.inject.Injector;
import com.premiumminds.billy.spain.Config;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.services.entities.ESRegionContext;
import com.premiumminds.billy.spain.services.persistence.ESRegionContextPersistenceService;

/**
 * Encapsulates all Context information for Spain.
 */
public class Contexts {

	Config					configuration	= new Config();

	private final Spain	spain;
	private final Continent	continent;
	private final Injector	injector;
	private final ESRegionContextPersistenceService persistenceService;
	

	public class Spain {

		public ESRegionContext allRegions() {
			DAOESRegionContext dao = Contexts.this
					.getInstance(DAOESRegionContext.class);
			return (ESRegionContext) dao.get(Contexts.this.configuration
					.getUID(Config.Key.Context.Spain.UUID));
		}
	}

	public class Continent {

		public ESRegionContext allContinentRegions() {
			DAOESRegionContext dao = Contexts.this
					.getInstance(DAOESRegionContext.class);
			return (ESRegionContext) dao.get(Contexts.this.configuration
					.getUID(Config.Key.Context.Spain.Continental.UUID));
		}

		public ESRegionContext madrid() {
			DAOESRegionContext dao = Contexts.this
					.getInstance(DAOESRegionContext.class);
			return (ESRegionContext) dao
					.get(Contexts.this.configuration
							.getUID(Config.Key.Context.Spain.Continental.Madrid.UUID));
		}
	}

	public Contexts(Injector injector) {
		this.spain = new Spain();
		this.continent = new Continent();
		this.injector = injector;
		this.persistenceService = getInstance(ESRegionContextPersistenceService.class);
	}

	public Spain spain() {
		return this.spain;
	}

	public Continent continent() {
		return this.continent;
	}
	
	public ESRegionContextPersistenceService persistence() {
		return this.persistenceService;
	}

	private <T> T getInstance(Class<T> clazz) {
		return this.injector.getInstance(clazz);
	}
}
