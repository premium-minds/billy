/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.services.builders.impl;

import java.util.Date;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOTicket;
import com.premiumminds.billy.core.persistence.entities.TicketEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.TicketBuilder;
import com.premiumminds.billy.core.services.entities.Ticket;
import com.premiumminds.billy.core.services.entities.util.EntityFactory;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;

public class TicketBuilderImpl <TBuilder extends TicketBuilderImpl<TBuilder, TTicket>, TTicket extends Ticket> 
	extends AbstractBuilder<TBuilder, TTicket> implements TicketBuilder<TBuilder, TTicket>{

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/core/i18n/FieldNames");

	protected DAOTicket daoTicket;

	@Inject
	public TicketBuilderImpl(DAOTicket daoTicket) {
		super((EntityFactory<?>) daoTicket);
		this.daoTicket = daoTicket;
	}

	@Override
	public TBuilder setObjectUID(UID objectUID) {
		BillyValidator.mandatory(objectUID,
				ContactBuilderImpl.LOCALIZER.getString("field.uid"));
		this.getTypeInstance().setObjectUID(objectUID);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance()
			throws javax.validation.ValidationException {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected TicketEntity getTypeInstance() {
		return (TicketEntity) super.getTypeInstance();
	}

	@Override
	public TBuilder setCreationDate(Date creationDate) {
		BillyValidator.mandatory(creationDate,
				ContactBuilderImpl.LOCALIZER.getString("field.date"));
		this.getTypeInstance().setCreationDate(creationDate);
		return this.getBuilder();
	}
	
	@Override
	public TBuilder setProcessDate(Date processDate) {
		BillyValidator.mandatory(processDate,
				ContactBuilderImpl.LOCALIZER.getString("field.date"));
		this.getTypeInstance().setProcessDate(processDate);
		return this.getBuilder();
	}
	
}
