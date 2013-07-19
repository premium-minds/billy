package com.premiumminds.billy.portugal.services.builders.impl;

import java.util.Date;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceEntryBuilderImpl;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntryEntity;
import com.premiumminds.billy.portugal.services.builders.PTInvoiceEntryBuilder;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;

public class PTInvoiceEntryBuilderImpl<TBuilder extends PTInvoiceEntryBuilderImpl<TBuilder, TEntry>, TEntry extends PTInvoiceEntry>
		extends GenericInvoiceEntryBuilderImpl<TBuilder, TEntry> implements
		PTInvoiceEntryBuilder<TBuilder, TEntry> {

	@Inject
	public PTInvoiceEntryBuilderImpl(DAOPTInvoiceEntry daoPTEntry,
			DAOPTInvoice daoPTInvoice, DAOPTTax daoPTTax,
			DAOPTProduct daoPTProduct, DAOPTRegionContext daoPTRegionContext) {
		super(daoPTEntry, daoPTInvoice, daoPTTax, daoPTProduct,
				daoPTRegionContext);
	}

	@Override
	public TBuilder setTaxPointDate(Date date) {
		BillyValidator.mandatory(date, GenericInvoiceEntryBuilderImpl.LOCALIZER
				.getString("field.tax_point_date"));
		this.getTypeInstance().setTaxPointDate(date);
		return this.getBuilder();
	}

	@Override
	public TBuilder setCreditOrDebit(CreditOrDebit creditOrDebit) {
		BillyValidator.mandatory(creditOrDebit,
				GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.credit_or_debit"));
		this.getTypeInstance().setCreditOrDebit(creditOrDebit);
		return this.getBuilder();
	}

	@Override
	protected PTInvoiceEntryEntity getTypeInstance() {
		return (PTInvoiceEntryEntity) super.getTypeInstance();
	}

	@Override
	protected void validateInstance() throws BillyValidationException {
		super.validateInstance();
		PTInvoiceEntryEntity i = this.getTypeInstance();
		BillyValidator.mandatory(i.getTaxPointDate(),
				GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.tax_point_date"));

		BillyValidator.mandatory(i.getCreditOrDebit(),
				GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.credit_or_debit"));
	}

}
