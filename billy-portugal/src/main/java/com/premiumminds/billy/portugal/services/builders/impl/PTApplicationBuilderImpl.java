/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

import java.net.URL;

import javax.inject.Inject;
import javax.validation.ValidationException;

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

	protected static final Localizer	LOCALIZER	= new Localizer(
															"com/premiumminds/billy/portugal/i18n/FieldNames_pt");

	@Inject
	public PTApplicationBuilderImpl(DAOPTApplication daoPTApplication) {
		super(daoPTApplication);
	}

	@Override
	protected PTApplicationEntity getTypeInstance() {
		return (PTApplicationEntity) super.getTypeInstance();
	}

	@Override
	public TBuilder setSoftwareCertificationNumber(Integer number) {
		BillyValidator.mandatory(number, PTApplicationBuilderImpl.LOCALIZER
				.getString("field.certificate_number"));
		this.getTypeInstance().setSoftwareCertificateNum(number);
		return this.getBuilder();
	}

	@Override
	public TBuilder setApplicationKeysPath(URL path) {
		BillyValidator
				.mandatory(path, PTApplicationBuilderImpl.LOCALIZER
						.getString("field.keys_path"));
		this.getTypeInstance().setApplicationKeysPath(path);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance() throws ValidationException {
		super.validateInstance();
		PTApplicationEntity c = this.getTypeInstance();
		BillyValidator.mandatory(c.getSoftwareCertificationNumber(),
				PTApplicationBuilderImpl.LOCALIZER
						.getString("field.certificate_number"));
	}

}
