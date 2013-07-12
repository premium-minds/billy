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

import javax.inject.Inject;
import javax.xml.bind.ValidationException;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.entities.IPTRegionContextEntity;
import com.premiumminds.billy.portugal.services.configuration.PTRegionContextService;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.entities.converters.PTRegionContextConverter;

public class PTRegionContextServiceImpl implements PTRegionContextService {

	protected DAOPTRegionContext daoPTRegionContext;

	@Inject
	public PTRegionContextServiceImpl(
			DAOPTRegionContext daoPTRegionContext) {
		this.daoPTRegionContext = daoPTRegionContext;
	}

	@Override
	public <T extends PTRegionContext> T getContext(UID uid) {
		IPTRegionContextEntity context = daoPTRegionContext.get(uid.getValue());
		return convertToService(context);
	}

	@Override
	public <T extends PTRegionContext> T createContext(T context) {
		return convertToService(
				daoPTRegionContext.create(
						convertToPersistence(context)));
	}

	@Override
	public <T extends PTRegionContext> T updateContext(T context) {
		return convertToService(
				daoPTRegionContext.update(
						convertToPersistence(context)));
	}

	@SuppressWarnings("unchecked")
	protected  <T extends PTRegionContext> T convertToService(IPTRegionContextEntity context) {
		PTRegionContextConverter converter = getPTRegionContextConverter();
		try {
			return (T) converter.toService(context);
		} catch (ValidationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	protected IPTRegionContextEntity convertToPersistence(PTRegionContext context) {
		return getPTRegionContextConverter().toPersistence(context);
	}

	protected PTRegionContextConverter getPTRegionContextConverter() {
		return new PTRegionContextConverter(daoPTRegionContext) {

			@Override
			protected IPTRegionContextEntity getPersistenceContext(UID uid) {
				return daoPTRegionContext.get(uid.getValue());
			}
		};
	}

}
