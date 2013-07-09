/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-core.
 * 
 * billy-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.core.services.builders.impl;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.ContextBuilder;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.util.EntityFactory;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;

public class ContextBuilderImpl<TBuilder extends ContextBuilderImpl<TBuilder, TContext>, TContext extends Context>
extends AbstractBuilder<TBuilder, TContext>
implements ContextBuilder<TBuilder, TContext> {

	protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

	protected DAOContext daoContext;
	
	@SuppressWarnings("unchecked")
	public ContextBuilderImpl(
			DAOContext daoContext) {
		super((EntityFactory<? extends TContext>) daoContext);
		this.daoContext = daoContext;
	}

	@Override
	public TBuilder setName(String name) {
		BillyValidator.mandatory(name, LOCALIZER.getString("field.name"));
		getTypeInstance().setName(name);
		return getBuilder();
	}

	@Override
	public TBuilder setDescription(String description) {
		BillyValidator.mandatory(description, LOCALIZER.getString("field.description"));
		getTypeInstance().setDescription(description);
		return getBuilder();
	}

	@Override
	public TBuilder setParentContextUID(UID parentUID) {
		if(parentUID == null) {
			getTypeInstance().setParentContext(null);
		}
		else {
			ContextEntity c = daoContext.get(parentUID);
			BillyValidator.found(c, LOCALIZER.getString("field.parent_context"));
			if(!getTypeInstance().isNew() && daoContext.isSubContext(c, getTypeInstance())) {
				throw new BillyRuntimeException();
			}
			getTypeInstance().setParentContext(c);
		}
		return getBuilder();
	}

	@Override
	protected void validateInstance()
			throws javax.validation.ValidationException {
		ContextEntity c = getTypeInstance();
		BillyValidator.mandatory(c.getName(), LOCALIZER.getString("field.name"));
		BillyValidator.mandatory(c.getDescription(), LOCALIZER.getString("field.description"));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ContextEntity getTypeInstance() {
		return (ContextEntity) super.getTypeInstance();
	}
	
}
