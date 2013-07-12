/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy.
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
package com.premiumminds.billy.portugal.services.builders;

import javax.validation.constraints.NotNull;

import com.premiumminds.billy.core.services.builders.ContextBuilder;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;

public interface PTRegionContextBuilder<TBuilder extends PTRegionContextBuilder<TBuilder, TContext>, TContext extends PTRegionContext> extends ContextBuilder<TBuilder, TContext> {

	public TBuilder setRegionCode(@NotNull String regionCode);
	
}
