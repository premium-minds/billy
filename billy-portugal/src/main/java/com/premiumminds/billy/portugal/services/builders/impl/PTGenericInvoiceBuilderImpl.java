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

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyUpdateException;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTGenericInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.services.builders.PTGenericInvoiceBuilder;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoiceEntry;

public class PTGenericInvoiceBuilderImpl<TBuilder extends PTGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends PTGenericInvoiceEntry, TDocument extends PTGenericInvoice>
	extends GenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument> implements
	PTGenericInvoiceBuilder<TBuilder, TEntry, TDocument> {

	protected static final Localizer	LOCALIZER	= new Localizer(
															"com/premiumminds/billy/portugal/i18n/FieldNames_pt");

	@Inject
	public PTGenericInvoiceBuilderImpl(DAOPTGenericInvoice daoPTGenericInvoice,
										DAOPTBusiness daoPTBusiness,
										DAOPTCustomer daoPTCustomer,
										DAOPTSupplier daoPTSupplier) {
		super(daoPTGenericInvoice, daoPTBusiness, daoPTCustomer, daoPTSupplier);
	}

	@Override
	@NotOnUpdate
	public TBuilder setSelfBilled(boolean selfBilled) {
		BillyValidator.mandatory(selfBilled,
				PTGenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.self_billed"));
		this.getTypeInstance().setSelfBilled(selfBilled);
		return this.getBuilder();
	}

	@Override
	protected PTGenericInvoiceEntity getTypeInstance() {
		return (PTGenericInvoiceEntity) super.getTypeInstance();
	}

	@Override
	public TBuilder setCancelled(boolean cancelled) {
		if (this.getTypeInstance().isCancelled()) {
			throw new BillyUpdateException("Invoice is allready cancelled!");
		}
		BillyValidator.mandatory(cancelled,
				PTGenericInvoiceBuilderImpl.LOCALIZER
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
				.mandatory(billed, PTGenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.billed"));
		this.getTypeInstance().setBilled(billed);
		return this.getBuilder();
	}

	@Override
	@NotOnUpdate
	public TBuilder setChangeReason(String reason) {
		this.getTypeInstance().setChangeReason(reason);
		return this.getBuilder();
	}

	@Override
	@NotOnUpdate
	public TBuilder setSourceBilling(SourceBilling sourceBilling) {
		BillyValidator.mandatory(sourceBilling,
				PTGenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.source_billing"));
		this.getTypeInstance().setSourceBilling(sourceBilling);
		return this.getBuilder();
	}

	@Override
	@NotOnUpdate
	public TBuilder setSourceId(String source) {
		BillyValidator
				.mandatory(source, PTGenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.source"));
		this.getTypeInstance().setSourceId(source);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		PTGenericInvoiceEntity i = this.getTypeInstance();
		BillyValidator.mandatory(i.getSourceBilling(),
				PTGenericInvoiceBuilderImpl.LOCALIZER
						.getString("field.source_billing"));

		switch (i.getSourceBilling()) {
			case M:
				this.validatePTInstance(i);
				break;
			case P:
				super.validateInstance();
				this.validatePTInstance(i);
				break;
			default:
				break;
		}
	}

	private void validatePTInstance(PTGenericInvoiceEntity i) {
		super.validateDate();
		BillyValidator
				.mandatory(i.getSourceId(),
						PTGenericInvoiceBuilderImpl.LOCALIZER
								.getString("field.source"));
		BillyValidator.mandatory(i.getDate(),
				PTGenericInvoiceBuilderImpl.LOCALIZER.getString("field.date"));
	}

}
