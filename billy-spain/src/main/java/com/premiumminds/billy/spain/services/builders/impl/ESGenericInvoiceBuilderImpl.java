/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyUpdateException;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.dao.DAOESGenericInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESSupplier;
import com.premiumminds.billy.spain.persistence.entities.ESGenericInvoiceEntity;
import com.premiumminds.billy.spain.services.builders.ESGenericInvoiceBuilder;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoice;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoiceEntry;

public class ESGenericInvoiceBuilderImpl<TBuilder extends ESGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends ESGenericInvoiceEntry, TDocument extends ESGenericInvoice>
		extends GenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
		implements ESGenericInvoiceBuilder<TBuilder, TEntry, TDocument> {

	protected static final Localizer	LOCALIZER	= new Localizer(
			"com/premiumminds/billy/core/i18n/FieldNames");

	@Inject
	public ESGenericInvoiceBuilderImpl(DAOESGenericInvoice daoESGenericInvoice,
			DAOESBusiness daoESBusiness, DAOESCustomer daoESCustomer,
			DAOESSupplier daoESSupplier) {
		super(daoESGenericInvoice, daoESBusiness, daoESCustomer, daoESSupplier);
	}

	@Override
	@NotOnUpdate
	public TBuilder setSelfBilled(boolean selfBilled) {
		BillyValidator.notNull(selfBilled,
				ESGenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.self_billed"));
		this.getTypeInstance().setSelfBilled(selfBilled);
		return this.getBuilder();
	}

	@Override
	protected ESGenericInvoiceEntity getTypeInstance() {
		return (ESGenericInvoiceEntity) super.getTypeInstance();
	}

	@Override
	public TBuilder setCancelled(boolean cancelled) {
		if (this.getTypeInstance().isCancelled()) {
			throw new BillyUpdateException("Invoice is allready cancelled!");
		}
		BillyValidator.notNull(cancelled, ESGenericInvoiceBuilderImpl.LOCALIZER
				.getString("field.cancelled"));
		this.getTypeInstance().setCancelled(cancelled);
		return this.getBuilder();
	}

	@Override
	public TBuilder setBilled(boolean billed) {
		if (this.getTypeInstance().isCancelled()) {
			throw new BillyUpdateException("Invoice is allready cancelled!");
		}
		if (this.getTypeInstance().isBilled()) {
			throw new BillyUpdateException("Invoice is allready billed!");
		}
		BillyValidator
				.notNull(billed, ESGenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.billed"));
		this.getTypeInstance().setBilled(billed);
		return this.getBuilder();
	}

	@Override
	@NotOnUpdate
	public TBuilder setSourceId(String source) {
		BillyValidator
				.notBlank(source, ESGenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.source"));
		this.getTypeInstance().setSourceId(source);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		ESGenericInvoiceEntity i = this.getTypeInstance();
		super.validateValues();
		this.validateESInstance(i);
	}

	protected void validateESInstance(ESGenericInvoiceEntity i) {
		super.validateDate();
		BillyValidator
				.mandatory(i.getCustomer(), GenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.customer"));

		BillyValidator
				.mandatory(i.getSourceId(),
						ESGenericInvoiceBuilderImpl.LOCALIZER
								.getString("field.source"));
		BillyValidator.mandatory(i.getDate(),
				ESGenericInvoiceBuilderImpl.LOCALIZER.getString("field.date"));
		if(i.isSelfBilled() != null) {
		BillyValidator.mandatory(i.isSelfBilled(),
				ESGenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.self_billed"));
		} else  {
			i.setSelfBilled(false);
		}
		BillyValidator.mandatory(i.isCancelled(),
				ESGenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.cancelled"));
		BillyValidator
				.mandatory(i.isBilled(), ESGenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.billed"));
		BillyValidator.notEmpty(i.getPayments(),
				ESGenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.payment_mechanism"));
	}

}
