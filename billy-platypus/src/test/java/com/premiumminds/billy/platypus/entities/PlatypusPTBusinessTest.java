/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy.
 *
 * billy is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.platypus.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.BeforeClass;

import com.premiumminds.billy.core.persistence.dao.DAOAddress;
import com.premiumminds.billy.core.persistence.dao.DAOApplication;
import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.persistence.entities.ApplicationEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.converters.AbstractEntityConverter;
import com.premiumminds.billy.core.services.exceptions.ConfigurationServiceException;
import com.premiumminds.billy.platypus.entities.mocks.PTBusinessTestEntities;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.entities.IPTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTRegionContextEntity;
import com.premiumminds.billy.portugal.services.configuration.PTBusinessService;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import com.premiumminds.billy.portugal.services.entities.converters.PTBusinessConverter;

public class PlatypusPTBusinessTest extends PlatypusEntityTest<PTBusiness, IPTBusinessEntity, PTBusinessService, DAOPTBusiness> {

	protected static PTBusinessService service;

	@BeforeClass
	public static void setup() {
		PlatypusEntityTest.setup();
		service = getInstance(PTBusinessService.class);
	}

	@Override
	protected PTBusiness createServiceEntity(
			PTBusiness entity) {
		try {
			return service.createBusiness(entity);
		} catch (ConfigurationServiceException e) {
			fail(e.getMessage());
		}
		return entity;
	}

	@Override
	protected PTBusiness updateServiceEntity(
			PTBusiness entity) {
		return service.updateBusiness(entity);
	}

	@Override
	protected PTBusiness readServiceEntity(
			UID entityUID) {
		return service.getBusiness(entityUID);
	}

	@Override
	public AbstractEntityConverter<IPTBusinessEntity, PTBusiness> getConverter() {
		final DAOPTBusiness businessDAO = getInstance(DAOPTBusiness.class);
		final DAOAddress addressDAO = getInstance(DAOAddress.class);
		final DAOPTRegionContext contextDAO = getInstance(DAOPTRegionContext.class);
		final DAOContact contactDAO = getInstance(DAOContact.class);
		final DAOApplication applicationDAO = getInstance(DAOApplication.class);

		return new PTBusinessConverter(businessDAO, addressDAO, contactDAO, applicationDAO) {
			
			@Override
			public IPTRegionContextEntity getPersistenceContext(
					UID contextUID) {
				return contextDAO.get(contextUID.getValue());
			}
			
			@Override
			public List<ContactEntity> getPersistenceContacts(
					List<Contact> contacts) {
				List<ContactEntity> result = new ArrayList<ContactEntity>();
				for(Contact c : contacts) {
					result.add(contactDAO.get(c.getUID().getValue()));
				}
				return result;
			}
			
			@Override
			public List<ApplicationEntity> getPersistenceApplications(
					List<Application> applications) {
				List<ApplicationEntity> result = new ArrayList<ApplicationEntity>();
				for(Application application : applications) {
					result.add(applicationDAO.get(application.getUID().getValue()));
				}
				return new ArrayList<ApplicationEntity>(result);
			}
		};
	}

	@Override
	public Class<PTBusinessService> getServiceClass() {
		return PTBusinessService.class;
	}

	@Override
	public Class<DAOPTBusiness> getDAOClass() {
		return DAOPTBusiness.class;
	}

	
	@Override
	public Collection<IPTBusinessEntity> getTestEntities() {
		return PTBusinessTestEntities.getEntities();
	}
	
}
