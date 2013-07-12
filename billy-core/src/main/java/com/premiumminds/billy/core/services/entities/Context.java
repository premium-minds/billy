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
package com.premiumminds.billy.core.services.entities;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.services.builders.impl.ContextBuilderImpl;

/**
 * @author Francisco Vargas
 * 
 *         The Billy services entity for a context. A context is an aggregation
 *         concept which might represent a region context, political context,
 *         financial context and so on.
 * 
 *         The purpose of this entity is to create relationships between
 *         different concepts. For instance, a {@link Tax} entity needs a
 *         defined context over which it is valid. An example would be a tax for
 *         Portugal which is only applied in a Portuguese context.
 */
public interface Context extends Entity {

	public static class Builder extends ContextBuilderImpl<Builder, Context> {

		@Inject
		public Builder(DAOContext daoContext) {
			super(daoContext);
		}
	}

	public String getName();

	public String getDescription();

	public <T extends Context> T getParentContext();

}
