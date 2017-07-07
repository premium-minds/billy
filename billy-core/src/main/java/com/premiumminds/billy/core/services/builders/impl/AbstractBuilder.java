/**
 * Copyright (C) 2017 Premium Minds.
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
package com.premiumminds.billy.core.services.builders.impl;

import javax.validation.ValidationException;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.entities.BaseEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.util.EntityFactory;

public abstract class AbstractBuilder<TBuilder extends AbstractBuilder<TBuilder, TType>, TType> {

	private EntityFactory<?>	factory;
	protected TType				typeInstance;

	public AbstractBuilder(EntityFactory<?> entityFactory) {
		this.factory = entityFactory;
		this.setTypeInstance((TType) entityFactory.getEntityInstance());
	}

	@SuppressWarnings("unchecked")
	protected TBuilder getBuilder() {
		return (TBuilder) this;
	}

	protected abstract void validateInstance() throws BillyValidationException,
		ValidationException;

	protected <T extends TType> void setTypeInstance(T instance) {
		this.typeInstance = instance;
	}

	public void clear() {
		this.typeInstance = (TType) this.factory.getEntityInstance();
	}

	@SuppressWarnings("unchecked")
	protected <T extends TType> T getTypeInstance() {
		return (T) this.typeInstance;
	}
	
	public TBuilder setUID(UID uid){ 
		((BaseEntity) this.typeInstance).setUID(uid);
		return getBuilder();
	}

	public TType build() throws BillyValidationException, ValidationException {
		this.validateInstance();
		return this.getTypeInstance();
	}

}
