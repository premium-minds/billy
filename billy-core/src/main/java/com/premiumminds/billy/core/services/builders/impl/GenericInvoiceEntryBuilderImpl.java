/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy-core.
 * 
 * billy-core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * billy-core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.core.services.builders.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Currency;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.ValidationException;

import org.apache.commons.lang3.Validate;

import com.premiumminds.billy.core.exceptions.NotImplementedException;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.persistence.entities.ProductEntity;
import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.services.entities.util.EntityFactory;
import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.DiscountType;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotImplemented;

@NotImplemented
@Deprecated
public class GenericInvoiceEntryBuilderImpl<TBuilder extends GenericInvoiceEntryBuilderImpl<TBuilder, TEntry>, TEntry extends GenericInvoiceEntry>
		extends AbstractBuilder<TBuilder, TEntry> implements
		GenericInvoiceEntryBuilder<TBuilder, TEntry> {

	protected static final Localizer LOCALIZER = new Localizer(
			"com/premiumminds/billy/core/i18n/FieldNames");

	protected DAOGenericInvoiceEntry daoEntry;
	protected DAOGenericInvoice daoGenericInvoice;
	protected DAOTax daoTax;
	protected DAOProduct daoProduct;

	@SuppressWarnings("unchecked")
	@Inject
	public GenericInvoiceEntryBuilderImpl(DAOGenericInvoiceEntry daoEntry,
			DAOGenericInvoice daoGenericInvoice, DAOTax daoTax,
			DAOProduct daoProduct) {
		super((EntityFactory<? extends TEntry>) daoEntry);
		this.daoEntry = daoEntry;
		this.daoGenericInvoice = daoGenericInvoice;
		this.daoTax = daoTax;
		this.daoProduct = daoProduct;
	}

	@Override
	public <T extends ShippingPoint> TBuilder setShippingOrigin(
			Builder<T> originBuilder) {
		BillyValidator.notNull(originBuilder,
				GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.shipping_origin"));
		this.getTypeInstance().setShippingOrigin(originBuilder.build());
		return this.getBuilder();
	}

	@Override
	public <T extends ShippingPoint> TBuilder setShippingDestination(
			Builder<T> destinationBuilder) {
		BillyValidator.notNull(destinationBuilder,
				GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.shipping_destination"));
		this.getTypeInstance().setShippingDestination(
				destinationBuilder.build());
		return this.getBuilder();
	}

	@Override
	public TBuilder setProductUID(UID productUID) {
		BillyValidator.mandatory(productUID, "field.product");
		ProductEntity p = this.daoProduct.get(productUID);
		BillyValidator.found(p, "field.product");
		this.getTypeInstance().setProduct(p);

		// Adds the product taxes to the invoice line
		for (Tax t : p.getTaxes()) {
			this.getTypeInstance().getTaxes().add(t);
		}
		return this.getBuilder();
	}

	@Override
	public TBuilder setQuantity(BigDecimal quantity) {
		Validate.isTrue(quantity.compareTo(BigDecimal.ZERO) > 0,
				"The quantity must be positive"); // TODO message
		this.getTypeInstance().setQuantity(quantity);
		return this.getBuilder();
	}

	@Override
	public TBuilder setUnitOfMeasure(String unit) {
		BillyValidator.mandatory(unit, "field.unit");
		this.getTypeInstance().setUnitOfMeasure(unit);
		return this.getBuilder();
	}

	@Override
	public TBuilder setTaxPointDate(Date date) {
		BillyValidator.notNull(date, GenericInvoiceEntryBuilderImpl.LOCALIZER
				.getString("field.tax_point_date"));
		this.getTypeInstance().setTaxPointDate(date);
		return this.getBuilder();
	}

	@Override
	public TBuilder addDocumentReferenceUID(UID referenceUID) {
		BillyValidator.notNull(referenceUID,
				GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.reference"));
		GenericInvoiceEntity d = this.daoGenericInvoice.get(referenceUID);
		BillyValidator.found(d, GenericInvoiceEntryBuilderImpl.LOCALIZER
				.getString("field.reference"));
		this.getTypeInstance().getDocumentReferences().add(d);
		return this.getBuilder();
	}

	@Override
	public TBuilder setDescription(String description) {
		BillyValidator.mandatory(description,
				GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.description"));
		this.getTypeInstance().setDescription(description);
		return this.getBuilder();
	}

	@Override
	public TBuilder setCreditOrDebit(CreditOrDebit creditOrDebit) {
		BillyValidator.notNull(creditOrDebit,
				GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.description"));
		this.getTypeInstance().setCreditOrDebit(creditOrDebit);
		return this.getBuilder();
	}

	@Override
	public TBuilder setShippingCostsAmount(BigDecimal amount) {
		BillyValidator.notNull(amount, GenericInvoiceEntryBuilderImpl.LOCALIZER
				.getString("field.shipping_costs_amount"));
		Validate.isTrue(amount.compareTo(BigDecimal.ZERO) >= 0);
		this.getTypeInstance().setShippingCostsAmount(amount);
		return this.getBuilder();
	}

	@Override
	public TBuilder setUnitAmount(AmountType type, BigDecimal amount,
			Currency currency) {
		BillyValidator.mandatory(type, GenericInvoiceEntryBuilderImpl.LOCALIZER
				.getString("field.unit_amount_type"));
		BillyValidator.mandatory(amount,
				GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.unit_gross_amount"));
		BillyValidator.mandatory(currency,
				GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.currency"));
		switch (type) {
			case WITH_TAX:
				this.getTypeInstance().setUnitAmountWithTax(amount);
				this.getTypeInstance().setUnitAmountWithoutTax(null);
				break;
			case WITHOUT_TAX:
				this.getTypeInstance().setUnitAmountWithoutTax(amount);
				this.getTypeInstance().setUnitAmountWithTax(null);
				break;
		}
		this.getTypeInstance().setCurrency(currency);
		return this.getBuilder();
	}

	@NotImplemented
	@Override
	public TBuilder addTaxUID(UID taxUID) {
		BillyValidator
				.notNull(taxUID, GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.tax"));
		TaxEntity t = this.daoTax.get(taxUID);
		BillyValidator
				.found(t, GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.tax"));
		this.getTypeInstance().getTaxes().add(t);
		return this.getBuilder();
	}

	@Override
	public TBuilder setTaxExemptionReason(String exemptionReason) {
		BillyValidator.notBlank(exemptionReason,
				GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.tax_exemption_reason"));
		this.getTypeInstance().setTaxExemptionReason(exemptionReason);
		return this.getBuilder();
	}

	@NotImplemented
	@Deprecated
	@Override
	public TBuilder setDiscounts(DiscountType type, BigDecimal... discounts) {
		BillyValidator.notNull(type, GenericInvoiceEntryBuilderImpl.LOCALIZER
				.getString("field.discount_type"));
		BillyValidator.notNull(discounts,
				GenericInvoiceEntryBuilderImpl.LOCALIZER
						.getString("field.discount"));
		Validate.notEmpty(discounts, GenericInvoiceEntryBuilderImpl.LOCALIZER
				.getString("field.discount_type"));

		// TODO

		return this.getBuilder();
	}

	@Override
	protected void validateInstance() throws ValidationException {
		// TODO Auto-generated method stub
		this.validateValues();
	}

	@Deprecated
	protected void validateValues() throws ValidationException {
		// TODO check mandatories

		MathContext mc = BillyMathContext.get();

		GenericInvoiceEntryEntity e = this.getTypeInstance();
		e.setUnitDiscountAmount(BigDecimal.ZERO); // TODO

		if (e.getUnitAmountWithTax() != null) {
			BigDecimal unitAmountWithoutTax = e.getUnitAmountWithTax();
			BigDecimal unitTaxAmount = BigDecimal.ZERO;

			for (Tax t : this.getTypeInstance().getTaxes()) {
				switch (t.getTaxRateType()) {
					case FLAT:
						unitAmountWithoutTax = unitAmountWithoutTax.subtract(
								t.getValue(), mc);
						unitTaxAmount.add(t.getValue(), mc);
						break;
					case PERCENTAGE:
						unitAmountWithoutTax = e
								.getUnitAmountWithTax()
								.divide(BigDecimal.ONE.add(
										t.getValue().divide(
												new BigDecimal("100"), mc), mc),
										mc);
						unitTaxAmount.add(
								e.getUnitAmountWithTax().subtract(
										unitAmountWithoutTax, mc), mc);
						break;
				}
			}
			e.setUnitAmountWithoutTax(unitAmountWithoutTax);
			e.setUnitTaxAmount(unitTaxAmount);

			// Minus discounts
			e.setUnitAmountWithoutTax(unitAmountWithoutTax.subtract(
					e.getUnitDiscountAmount(), mc));
		} else {
			throw new NotImplementedException(
					"Cannot calculate from value without tax... yet");
		}

		e.setAmountWithTax(this.getTypeInstance().getUnitAmountWithTax()
				.multiply(e.getQuantity(), mc));
		e.setAmountWithoutTax(this.getTypeInstance().getUnitAmountWithoutTax()
				.multiply(e.getQuantity(), mc));
		e.setTaxAmount(this.getTypeInstance().getUnitTaxAmount()
				.multiply(e.getQuantity(), mc));
		e.setDiscountAmount(this.getTypeInstance().getUnitDiscountAmount()
				.multiply(e.getQuantity(), mc));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected GenericInvoiceEntryEntity getTypeInstance() {
		return (GenericInvoiceEntryEntity) super.getTypeInstance();
	}

}
