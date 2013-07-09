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
package com.premiumminds.billy.portugal.persistence.dao.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.persistence.dao.jpa.AbstractDAO;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ApplicationEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.AddressEntityImpl;
import com.premiumminds.billy.core.persistence.entities.jpa.ApplicationEntityImpl;
import com.premiumminds.billy.core.persistence.entities.jpa.ContactEntity;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.entities.IPTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTRegionContextEntity;

public class DAOPTBusinessImpl extends AbstractDAO<IPTBusinessEntity, PTBusinessEntity> implements
		DAOPTBusiness {

	@Inject
	public DAOPTBusinessImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}
	
	@Override
	public IPTBusinessEntity getPTBusinessInstance(
			IPTRegionContextEntity operationalContext, 
			String financialId, 
			String name,
			String commercialName,
			AddressEntity address, 
			AddressEntity billingAddress, 
			AddressEntity shippingAddress, 
			List<ContactEntity> contacts, 
			String website, 
			List<ApplicationEntity> applications, 
			String centralCommercialRegistryName, 
			String centralCommercialRegistryNumber) {

		return new PTBusinessEntity(
				checkEntity(operationalContext, PTRegionContextEntity.class), 
				financialId, 
				name, 
				commercialName,
				checkEntity(address, AddressEntity.class), 
				checkEntity(billingAddress, AddressEntity.class), 
				checkEntity(shippingAddress, AddressEntity.class),
				checkEntityList(contacts, ContactEntity.class), 
				website, 
				checkEntityList(applications, ApplicationEntity.class),
				centralCommercialRegistryName, 
				centralCommercialRegistryNumber);
	}

	@Override
	protected Class<PTBusinessEntity> getEntityClass() {
		return PTBusinessEntity.class;
	}

}
