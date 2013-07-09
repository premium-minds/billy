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
package com.premiumminds.billy.portugal.services.configuration.impl;

import javax.inject.Inject;
import javax.xml.bind.ValidationException;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.IPTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTTaxEntity;
import com.premiumminds.billy.portugal.services.configuration.PTTaxService;
import com.premiumminds.billy.portugal.services.entities.PTTax;
import com.premiumminds.billy.portugal.services.entities.converters.PTTaxConverter;

public class PTTaxServiceImpl implements PTTaxService {

	protected DAOPTTax daoPTTax;
	protected DAOPTRegionContext daoPTRegionContext;

	@Inject
	public PTTaxServiceImpl(
			DAOPTTax daoPTTax,
			DAOPTRegionContext daoPTRegionContext){
		this.daoPTTax = daoPTTax;
		this.daoPTRegionContext = daoPTRegionContext;
	}

	@Override
	public <T extends PTTax> T getTax(UID uid) {
		return convertToService(
				daoPTTax.get(uid.getValue()));
	}

	@Override
	public <T extends PTTax> T createTax(T tax) {
		return convertToService(
				daoPTTax.create(
						convertToPersistence(tax)));
	}

	@Override
	public <T extends PTTax> T updateTax(T tax) {
		return convertToService(
				daoPTTax.update(
						convertToPersistence(tax)));
	}

	@SuppressWarnings("unchecked")
	protected <T extends PTTax> T convertToService(IPTTaxEntity tax) {
		try {
			return (T) getPTTaxConverter().toService(tax);
		} catch (ValidationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	protected IPTTaxEntity convertToPersistence(PTTax tax) {
		return getPTTaxConverter().toPersistence(tax);
	}

	protected PTTaxConverter getPTTaxConverter() {
		return new PTTaxConverter(daoPTTax) {

			@Override
			protected IPTRegionContextEntity getPersistenceContext(UID contextUID) {
				return daoPTRegionContext.get(contextUID.getValue());
			}
		};
	}

}
