/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
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
package com.premiumminds.billy.portugal.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.services.builders.impl.ContactBuilderImpl;
import com.premiumminds.billy.portugal.persistence.entities.PTContactEntity;
import com.premiumminds.billy.portugal.services.builders.PTContactBuilder;
import com.premiumminds.billy.portugal.services.entities.PTContact;


public class PTContactBuilderImpl<TBuilder extends PTContactBuilderImpl<TBuilder, TContact>, TContact extends PTContact>
extends ContactBuilderImpl<TBuilder, TContact> implements
PTContactBuilder<TBuilder, TContact> {
	
	@Inject
	public PTContactBuilderImpl(DAOContact daoContact) {
		super(daoContact);
	}

	@Override
	protected PTContactEntity getTypeInstance() {
		return (PTContactEntity) super.getTypeInstance();
	}
}
