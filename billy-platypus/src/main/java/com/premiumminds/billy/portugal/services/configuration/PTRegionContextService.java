/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-platypus.
 * 
 * billy-platypus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-platypus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-platypus.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.portugal.services.configuration;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;

/**
 * @author Francisco Vargas
 * The service to be used in configuring a {@link PTRegionContext}
 */
public interface PTRegionContextService {

	/**
	 * Gets a persisted {@link PTRegionContext} instance
	 * @param uid The unique identifier for the requested region context
	 * @return The persisted {@link PTRegionContext} instance
	 */
	public <T extends PTRegionContext> T getContext(UID uid);
	
	/**
	 * Persists a supplied {@link PTRegionContext} instance
	 * @param context The instance to be persisted
	 * @return The newly persisted instance
	 */
	public <T extends PTRegionContext> T createContext(T context);
	
	/**
	 * Persists an updated {@link PTRegionContext} instance
	 * @param context The instance to be persisted
	 * @return The updated instance
	 */
	public <T extends PTRegionContext> T updateContext(T context);

}
