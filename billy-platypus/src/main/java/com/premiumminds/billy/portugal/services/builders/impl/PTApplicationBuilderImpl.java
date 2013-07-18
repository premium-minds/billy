/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy platypus (PT Pack).
 * 
 * billy platypus (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy platypus (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.ApplicationBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTApplication;
import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;
import com.premiumminds.billy.portugal.services.builders.PTApplicationBuilder;
import com.premiumminds.billy.portugal.services.entities.PTApplication;

public class PTApplicationBuilderImpl<TBuilder extends PTApplicationBuilderImpl<TBuilder, TApplication>, TApplication extends PTApplication>
		extends ApplicationBuilderImpl<TBuilder, TApplication> implements
		PTApplicationBuilder<TBuilder, TApplication> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/portugal/i18n/FieldNames");

	@Inject
	public PTApplicationBuilderImpl(DAOPTApplication daoPTApplication) {
		super(daoPTApplication);
	}

	@Override
	protected PTApplicationEntity getTypeInstance() {
		return (PTApplicationEntity) super.getTypeInstance();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		super.validateInstance();
	}

	@Override
	public TBuilder setSoftwareCertificationNumber(Integer number) {
		BillyValidator.mandatory(number, LOCALIZER.getString("field.number"));
		getTypeInstance().setSoftwareCertificateNum(number);
		return getBuilder();
	}

	public TBuilder setApplicationKeysPath(String path) {
		BillyValidator.found(path, LOCALIZER.getString("field.keys_path"));
		getTypeInstance().setApplicationKeysPath(path);
		return getBuilder();
	}
}
