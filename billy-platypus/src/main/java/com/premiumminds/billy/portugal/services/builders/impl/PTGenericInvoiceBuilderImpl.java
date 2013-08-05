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

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
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
		extends GenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
		implements PTGenericInvoiceBuilder<TBuilder, TEntry, TDocument> {

	@Inject
	public PTGenericInvoiceBuilderImpl(DAOPTGenericInvoice daoPTGenericInvoice,
			DAOPTBusiness daoPTBusiness, DAOPTCustomer daoPTCustomer,
			DAOPTSupplier daoPTSupplier) {
		super(daoPTGenericInvoice, daoPTBusiness, daoPTCustomer, daoPTSupplier);
	}

	@Override
	public TBuilder setSelfBilled(boolean selfBilled) {
		BillyValidator.mandatory(selfBilled,
				PTInvoiceBuilderImpl.LOCALIZER.getString("field.self_billed"));
		this.getTypeInstance().setSelfBilled(selfBilled);
		return this.getBuilder();
	}

	@Override
	protected PTGenericInvoiceEntity getTypeInstance() {
		return (PTGenericInvoiceEntity) super.getTypeInstance();
	}

	@Override
	public TBuilder setCancelled(boolean cancelled) {
		BillyValidator.mandatory(cancelled,
				PTInvoiceBuilderImpl.LOCALIZER.getString("field.cancelled"));
		this.getTypeInstance().setCancelled(cancelled);
		return this.getBuilder();
	}

	@Override
	public TBuilder setBilled(boolean billed) {
		BillyValidator.mandatory(billed,
				PTInvoiceBuilderImpl.LOCALIZER.getString("field.billed"));
		this.getTypeInstance().setBilled(billed);
		return this.getBuilder();
	}

	@Override
	public TBuilder setHash(String hash) {
		BillyValidator.mandatory(hash,
				PTInvoiceBuilderImpl.LOCALIZER.getString("field.hash"));
		this.getTypeInstance().setHash(hash);
		return this.getBuilder();
	}

	@Override
	public TBuilder setSourceHash(String source) {
		BillyValidator.notBlank(source,
				PTInvoiceBuilderImpl.LOCALIZER.getString("field.source_hash"));
		this.getTypeInstance().setSourceHash(source);
		return this.getBuilder();
	}

	@Override
	public TBuilder setSourceBilling(SourceBilling sourceBilling) {
		BillyValidator
				.mandatory(sourceBilling, PTInvoiceBuilderImpl.LOCALIZER
						.getString("field.sourceBilling"));
		this.getTypeInstance().setSourceBilling(sourceBilling);
		return this.getBuilder();
	}

	@Override
	public TBuilder setSourceId(String source) {
		BillyValidator.mandatory(source,
				GenericInvoiceBuilderImpl.LOCALIZER.getString("field.source"));
		this.getTypeInstance().setSourceId(source);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		PTGenericInvoiceEntity i = this.getTypeInstance();

		switch (i.getSourceBilling()) {
			case M:
				this.validatePTInstance(i);
				break;
			case P:
				super.validateInstance();
				break;
			default:
				break;
		}
	}

	private void validatePTInstance(PTGenericInvoiceEntity i) {
		super.validateDate();
		BillyValidator.mandatory(i.getSourceId(),
				PTInvoiceBuilderImpl.LOCALIZER.getString("field.source_id"));
		BillyValidator.mandatory(i.getDate(),
				PTInvoiceBuilderImpl.LOCALIZER.getString("field.date_id"));
		BillyValidator.mandatory(i.isSelfBilled(),
				PTInvoiceBuilderImpl.LOCALIZER.getString("field.self_billed"));
		BillyValidator.mandatory(i.isCancelled(),
				PTInvoiceBuilderImpl.LOCALIZER.getString("field.cancelled"));
		BillyValidator.mandatory(i.isBilled(),
				PTInvoiceBuilderImpl.LOCALIZER.getString("field.billed"));
	}

}
