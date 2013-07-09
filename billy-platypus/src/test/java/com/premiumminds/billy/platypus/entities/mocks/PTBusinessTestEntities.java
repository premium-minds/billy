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
package com.premiumminds.billy.platypus.entities.mocks;

import java.util.ArrayList;
import java.util.List;

import com.premiumminds.billy.core.persistence.dao.DAOAddress;
import com.premiumminds.billy.core.persistence.dao.DAOApplication;
import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.persistence.entities.ApplicationEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.platypus.PlatypusBaseTest;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.entities.IPTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTRegionContextEntity;

public class PTBusinessTestEntities extends PlatypusBaseTest {

	public static List<IPTBusinessEntity> getEntities() {
		List<IPTBusinessEntity> result = new ArrayList<IPTBusinessEntity>();
		result.add(getInstance1());
		return result;
	}

	public static IPTBusinessEntity getInstance1() {
		DAOPTBusiness daoBusiness = getInstance(DAOPTBusiness.class);
		DAOAddress daoAddress = getInstance(DAOAddress.class);
		DAOPTRegionContext daoContext = getInstance(DAOPTRegionContext.class);
		DAOApplication daoApplication = getInstance(DAOApplication.class);
		DAOContact daoContact = getInstance(DAOContact.class);

		IPTBusinessEntity result = daoBusiness.getPTBusinessInstance(
				PTRegionContextTestEntities.getInstance1(),
				"123456789", 
				"Nome de teste", 
				"Nome comercial",
				AddressTestEntities.getInstance1(), 
				AddressTestEntities.getInstance2(), 
				AddressTestEntities.getInstance3(), 
				ContactTestEntities.getEntities(), 
				"www.somososmaiores.com/pt/ws?123=4&q=2", 
				ApplicationTestEntities.getEntities(), 
				"conservatória tal e tal", 
				"numero");

		daoContext.create((IPTRegionContextEntity) result.getOperationalContext());
		daoAddress.create(result.getAddress());
		daoAddress.create(result.getBillingAddress());
		daoAddress.create(result.getShippingAddress());
		for(ContactEntity c : result.getContacts()) {
			daoContact.create(c);
		}
		for(ApplicationEntity a : result.getApplications()) {
			daoApplication.create(a);
		}

		return result;
	}

}
