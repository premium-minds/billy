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
import com.premiumminds.billy.core.services.builders.impl.BuilderManager;
import com.premiumminds.billy.spain.services.entities.ESBusiness;
import com.premiumminds.billy.spain.services.persistence.ESBusinessPersistenceService;

public class Businesses {

	private final Injector	injector;
	private final ESBusinessPersistenceService persistenceService;


	public Businesses(Injector injector) {
		this.injector = injector;
		this.persistenceService = getInstance(ESBusinessPersistenceService.class);
	}

	public ESBusiness.Builder builder() {
		return getInstance(ESBusiness.Builder.class);
	}
	
	public ESBusiness.Builder builder(ESBusiness customer) {
		ESBusiness.Builder builder = getInstance(ESBusiness.Builder.class);
		BuilderManager.setTypeInstance(builder, customer);
		return builder;
	}
	
	public ESBusinessPersistenceService persistence() {
		return this.persistenceService;
	}

	private <T> T getInstance(Class<T> clazz) {
		return this.injector.getInstance(clazz);
	}
	
}
