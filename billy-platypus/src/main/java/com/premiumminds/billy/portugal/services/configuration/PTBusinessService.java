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
import com.premiumminds.billy.core.services.entities.BusinessOffice;
import com.premiumminds.billy.core.services.exceptions.ConfigurationServiceException;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;


/**
 * @author Francisco Vargas
 * The service to be used in configuring a business for Portugal
 */
public interface PTBusinessService {

	/**
	 * @param uid The unique identifier for the business
	 * @return The PTBusiness instance for the provided uid
	 */
	public <T extends PTBusiness> T getBusiness(UID uid);

	/**
	 * @param business The business to be created
	 * @return The business after its creation
	 * @throws ConfigurationServiceException
	 */
	public <T extends PTBusiness> T createBusiness(T business) throws ConfigurationServiceException;

	/**
	 * @param business The business to be created
	 * @return The business after its update
	 */
	public <T extends PTBusiness> T updateBusiness(T business);

	/**
	 * @param ptBusinessUID The unique identifier for the {@link PTBusiness} to which the {@link BusinessOffice} belongs
	 * @param office The {@link BusinessOffice} to be created and attached to a {@link PTBusiness}
	 * @return The {@link BusinessOffice} after its creation
	 */
	public <T extends BusinessOffice> T addOffice(UID ptBusinessUID, T office);
	
	/**
	 * @param office The {@link BusinessOffice} to be updated
	 * @return The {@link BusinessOffice} after its been updated
	 */
	public <T extends BusinessOffice> T updateOffice(T office);

}
