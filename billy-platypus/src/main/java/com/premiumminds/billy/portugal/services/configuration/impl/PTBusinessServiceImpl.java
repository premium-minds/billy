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
package com.premiumminds.billy.portugal.services.configuration.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.ValidationException;

import com.premiumminds.billy.core.persistence.dao.DAOAddress;
import com.premiumminds.billy.core.persistence.dao.DAOApplication;
import com.premiumminds.billy.core.persistence.dao.DAOBusinessOffice;
import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.ApplicationEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.BusinessOffice;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.converters.ApplicationConverter;
import com.premiumminds.billy.core.services.entities.converters.ContactConverter;
import com.premiumminds.billy.core.services.exceptions.ConfigurationServiceException;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.entities.IPTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTRegionContextEntity;
import com.premiumminds.billy.portugal.services.configuration.PTBusinessService;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import com.premiumminds.billy.portugal.services.entities.converters.PTBusinessConverter;

/**
 * @author Francisco Vargas
 * The Core implementation of {@link PTBusinessService}
 */
public class PTBusinessServiceImpl implements PTBusinessService {

	protected DAOPTBusiness daoPTBusiness;
	protected DAOBusinessOffice daoBusinessOffice;
	protected DAOApplication daoApplication;
	protected DAOContact daoContact;
	protected DAOPTRegionContext daoPTRegionContext;
	protected DAOAddress daoAddress;

	@Inject
	protected PTBusinessServiceImpl(
			DAOPTBusiness daoPTBusiness,
			DAOBusinessOffice daoBusinessOffice,
			DAOApplication daoApplication,
			DAOContact daoContact,
			DAOPTRegionContext daoPTRegionContext,
			DAOAddress daoAddress) {

		this.daoPTBusiness = daoPTBusiness;
		this.daoBusinessOffice = daoBusinessOffice;
		this.daoApplication = daoApplication;
		this.daoContact = daoContact;
		this.daoPTRegionContext = daoPTRegionContext;
		this.daoAddress = daoAddress;
	}

	@Override
	public <T extends PTBusiness> T getBusiness(UID uid) {
		return convertToService(daoPTBusiness.get(uid.getValue()));
	}

	@Override
	public <T extends PTBusiness> T createBusiness(final T business) throws ConfigurationServiceException {
		try {
			return new TransactionWrapper<T>(daoPTBusiness) {
				@Override
				public T runTransaction() throws Exception {
					BillyValidator.validate(business);

					final IPTBusinessEntity ptBusinessEntity = convertToPersistence(business);
					BillyValidator.validate(ptBusinessEntity);

					AddressEntity address = ptBusinessEntity.getAddress();
					if(address != null) {
						daoAddress.create(address);
					}

					AddressEntity billingAddress = ptBusinessEntity.getBillingAddress();
					if(billingAddress != null) {
						daoAddress.create(billingAddress);
					}

					AddressEntity shippingAddress = ptBusinessEntity.getShippingAddress();
					if(shippingAddress != null) {
						daoAddress.create(shippingAddress);
					}

					for(ContactEntity contact : ptBusinessEntity.getContacts()) {
						daoContact.create(contact);
					}

					for(ApplicationEntity application : ptBusinessEntity.getApplications()) {
						for(ContactEntity contact : application.getContacts()) {
							daoContact.create(contact);
						}
						daoApplication.create(application);
					}

					daoPTBusiness.create(ptBusinessEntity);
					return convertToService(ptBusinessEntity);
				}
			}.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigurationServiceException(e); 
		}
	}

	@Override
	public <T extends PTBusiness> T updateBusiness(T business) {
		return convertToService(
				daoPTBusiness.update(
						convertToPersistence(business)));
	}

	protected IPTBusinessEntity convertToPersistence(PTBusiness business) {
		try {
			return getPTBusinessConverter().toPersistence(business);
		} catch (ValidationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T extends BusinessOffice> T addOffice(UID businessUID, T office) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends BusinessOffice> T updateOffice(T office) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	protected <T extends PTBusiness> T convertToService(IPTBusinessEntity business) {
		try {
			return (T) getPTBusinessConverter().toService(business);
		} catch (ValidationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	protected PTBusinessConverter getPTBusinessConverter() {
		return new PTBusinessConverter(daoPTBusiness, daoAddress, daoContact, daoApplication) {

			@Override
			public IPTRegionContextEntity getPersistenceContext(
					UID contextUID) {
				return daoPTRegionContext.get(contextUID.getValue());
			}

			@Override
			public List<ContactEntity> getPersistenceContacts(List<Contact> contacts) {
				List<ContactEntity> result = new ArrayList<ContactEntity>();
				ContactConverter contactConverter = new ContactConverter(daoContact);
				for(Contact contact : contacts) {
					if(contact.isNew()) {
						result.add(contactConverter.toPersistence(contact));
					} else {
						result.add(daoContact.get(contact.getUID().getValue()));
					}
				}
				return result;
			}

			@Override
			public List<ApplicationEntity> getPersistenceApplications(
					List<Application> applications) {
				List<ApplicationEntity> result = new ArrayList<ApplicationEntity>();
				final PTBusinessConverter myConverter = this;
				ApplicationConverter applicationConverter = new ApplicationConverter(daoApplication, daoContact) {

					@Override
					public List<ContactEntity> getPersistenceContacts(List<Contact> contacts) {
						return myConverter.getPersistenceContacts(contacts);
					}
				};
				for(Application application : applications) {
					if(application.isNew()) {
						result.add(applicationConverter.toPersistence(application));
					} else {
						result.add(daoApplication.get(application.getUID().getValue()));
					}
				}
				return result;
			}
		};
	}

}
