/**
 * Copyright (C) 2013 Premium Minds.
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

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.TaxBuilder;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;
import com.premiumminds.billy.core.services.entities.util.EntityFactory;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;

public class TaxBuilderImpl<TBuilder extends TaxBuilderImpl<TBuilder, TTax>, TTax extends Tax>
		extends AbstractBuilder<TBuilder, TTax> implements
		TaxBuilder<TBuilder, TTax> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/core/i18n/FieldNames");

	protected DAOTax daoTax;
	protected DAOContext daoContext;

	@SuppressWarnings("unchecked")
	@Inject
	public TaxBuilderImpl(DAOTax daoTax, DAOContext daoContext) {
		super((EntityFactory<? extends TTax>) daoTax);
		this.daoContext = daoContext;
	}

	@Override
	public TBuilder setContextUID(UID contextUID) {
		BillyValidator.notNull(contextUID,
				TaxBuilderImpl.LOCALIZER.getString("field.context"));
		ContextEntity c = BillyValidator.found(this.daoContext.get(contextUID),
				TaxBuilderImpl.LOCALIZER.getString("field.context"));
		this.getTypeInstance().setContext(c);
		return this.getBuilder();
	}

	@Override
	public TBuilder setDesignation(String designation) {
		this.getTypeInstance().setDesignation(designation);
		return this.getBuilder();
	}

	@Override
	public TBuilder setDescription(String description) {
		BillyValidator.mandatory(description,
				TaxBuilderImpl.LOCALIZER.getString("field.description"));
		this.getTypeInstance().setDescription(description);
		return this.getBuilder();
	}

	@Override
	public TBuilder setCode(String code) {
		BillyValidator.mandatory(code,
				TaxBuilderImpl.LOCALIZER.getString("field.tax_code"));
		this.getTypeInstance().setCode(code);
		return this.getBuilder();
	}

	@Override
	public TBuilder setValidFrom(Date from) {
		this.getTypeInstance().setValidFrom(from);
		return this.getBuilder();
	}

	@Override
	public TBuilder setValidTo(Date to) {
		this.getTypeInstance().setValidTo(to);
		return this.getBuilder();
	}

	@Override
	public TBuilder setValue(BigDecimal value) {
		this.getTypeInstance().setValue(value);
		return this.getBuilder();
	}

	@Override
	public TBuilder setTaxRate(TaxRateType rateType, BigDecimal amount) {
		BillyValidator.mandatory(rateType,
				TaxBuilderImpl.LOCALIZER.getString("field.tax_rate_type"));
		BillyValidator.mandatory(amount,
				TaxBuilderImpl.LOCALIZER.getString("field.tax_rate_amount"));
		this.getTypeInstance().setTaxRateType(rateType);
		switch (rateType) {
			case FLAT:
				this.getTypeInstance().setPercentageRateValue(BigDecimal.ZERO);
				this.getTypeInstance().setFlatRateAmount(amount);
				break;
			case PERCENTAGE:
				Validate.exclusiveBetween(BigDecimal.ZERO,
						new BigDecimal("100"), amount);
				this.getTypeInstance().setPercentageRateValue(amount);
				this.getTypeInstance().setFlatRateAmount(BigDecimal.ZERO);
				break;
			default:
				throw new RuntimeException("The tax rate type is unknown");
		}
		return this.getBuilder();
	}

	@Override
	public TBuilder setCurrency(Currency currency) {
		this.getTypeInstance().setCurrency(currency);
		return this.getBuilder();
	}

	@Override
	protected void validateInstance()
			throws javax.validation.ValidationException {
		Tax t = this.getTypeInstance();
		BillyValidator.notNull(t.getContext(),
				TaxBuilderImpl.LOCALIZER.getString("field.context"));
		BillyValidator.mandatory(t.getDescription(),
				TaxBuilderImpl.LOCALIZER.getString("field.description"));
		BillyValidator.mandatory(t.getCode(),
				TaxBuilderImpl.LOCALIZER.getString("field.tax_code"));
		BillyValidator.mandatory(t.getTaxRateType(),
				TaxBuilderImpl.LOCALIZER.getString("field.tax_rate_type"));

		switch (t.getTaxRateType()) {
			case FLAT:
				BillyValidator.mandatory(t.getFlatRateAmount(),
						TaxBuilderImpl.LOCALIZER
								.getString("field.tax_rate_flat_amount"));
				BillyValidator.mandatory(t.getCurrency(),
						TaxBuilderImpl.LOCALIZER.getString("field.currency"));
				break;
			case PERCENTAGE:
				BillyValidator.inclusiveBetween(BigDecimal.ZERO,
						new BigDecimal("100"), t.getPercentageRateValue());
				break;
			default:
				throw new RuntimeException("The tax rate type is unknown");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected TaxEntity getTypeInstance() {
		return (TaxEntity) super.getTypeInstance();
	}

}
