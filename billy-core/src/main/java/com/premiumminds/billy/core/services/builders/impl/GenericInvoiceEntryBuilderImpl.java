/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-core.
 * 
 * billy-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.core.services.builders.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.ValidationException;

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
	extends AbstractBuilder<TBuilder, TEntry>
	implements GenericInvoiceEntryBuilder<TBuilder, TEntry> {

	protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");
	
	protected DAOGenericInvoiceEntry daoEntry;
	protected DAOGenericInvoice daoGenericInvoice;
	protected DAOTax daoTax;
	protected DAOProduct daoProduct;
	
	@SuppressWarnings("unchecked")
	@Inject
	public GenericInvoiceEntryBuilderImpl(
			DAOGenericInvoiceEntry daoEntry,
			DAOGenericInvoice daoGenericInvoice,
			DAOTax daoTax,
			DAOProduct daoProduct) {
		super((EntityFactory<? extends TEntry>) daoEntry);
		this.daoEntry = daoEntry;
		this.daoGenericInvoice = daoGenericInvoice;
		this.daoTax = daoTax;
		this.daoProduct = daoProduct;
	}
	
	@Override
	public <T extends ShippingPoint> TBuilder setShippingOrigin(Builder<T> originBuilder) {
		BillyValidator.notNull(originBuilder, LOCALIZER.getString("field.shipping_origin"));
		getTypeInstance().setShippingOrigin(originBuilder.build());
		return getBuilder();
	}

	@Override
	public <T extends ShippingPoint> TBuilder setShippingDestination(
			Builder<T> destinationBuilder) {
		BillyValidator.notNull(destinationBuilder, LOCALIZER.getString("field.shipping_destination"));
		getTypeInstance().setShippingDestination(destinationBuilder.build());
		return getBuilder();
	}

	@Override
	public TBuilder setProductUID(UID productUID) {
		BillyValidator.mandatory(productUID, "field.product");
		ProductEntity p = daoProduct.get(productUID);
		BillyValidator.found(p, "field.product");
		getTypeInstance().setProduct(p);
		return getBuilder();
	}

	@Override
	public TBuilder setQuantity(BigDecimal quantity) {
		BillyValidator.isTrue(quantity.compareTo(BigDecimal.ZERO) > 0, "The quantity must be positive"); //TODO message
		getTypeInstance().setQuantity(quantity);
		return getBuilder();
	}

	@Override
	public TBuilder setUnitOfMeasure(String unit) {
		BillyValidator.mandatory(unit, "field.unit");
		getTypeInstance().setUnitOfMeasure(unit);
		return getBuilder();
	}

	@Override
	public TBuilder setTaxPointDate(Date date) {
		BillyValidator.notNull(date, LOCALIZER.getString("field.tax_point_date"));
		getTypeInstance().setTaxPointDate(date);
		return getBuilder();
	}

	@Override
	public TBuilder addDocumentReferenceUID(UID referenceUID) {
		BillyValidator.notNull(referenceUID, LOCALIZER.getString("field.reference"));
		GenericInvoiceEntity d = daoGenericInvoice.get(referenceUID);
		BillyValidator.found(d, LOCALIZER.getString("field.reference"));
		getTypeInstance().getDocumentReferences().add(d);
		return getBuilder();
	}

	@Override
	public TBuilder setDescription(String description) {
		BillyValidator.mandatory(description, LOCALIZER.getString("field.description"));
		getTypeInstance().setDescription(description);
		return getBuilder();
	}

	@Override
	public TBuilder setCreditOrDebit(CreditOrDebit creditOrDebit) {
		BillyValidator.notNull(creditOrDebit, LOCALIZER.getString("field.description"));
		getTypeInstance().setCreditOrDebit(creditOrDebit);
		return getBuilder();
	}

	@Override
	public TBuilder setShippingCostsAmount(BigDecimal amount) {
		BillyValidator.notNull(amount, LOCALIZER.getString("field.shipping_costs_amount"));
		BillyValidator.isTrue(amount.compareTo(BigDecimal.ZERO) >= 0);
		getTypeInstance().setShippingCostsAmount(amount);
		return getBuilder();
	}
	
	@Override
	public TBuilder setUnitGrossAmount(BigDecimal amount, Currency currency) {
		BillyValidator.mandatory(amount, LOCALIZER.getString("field.unit_gross_amount"));
		BillyValidator.mandatory(currency, LOCALIZER.getString("field.currency"));
		getTypeInstance().setUnitGrossAmount(amount);
		getTypeInstance().setCurrency(currency);
		return getBuilder();
	}

	@NotImplemented
	@Override
	public TBuilder addTaxUID(UID taxUID) {
		BillyValidator.notNull(taxUID, LOCALIZER.getString("field.tax"));
		TaxEntity t = daoTax.get(taxUID);
		BillyValidator.found(t, LOCALIZER.getString("field.tax"));
		getTypeInstance().getTaxes().add(t);
		return getBuilder();
	}

	@Override
	public TBuilder setTaxExemptionReason(String exemptionReason) {
		BillyValidator.notBlank(exemptionReason, LOCALIZER.getString("field.tax_exemption_reason"));
		getTypeInstance().setTaxExemptionReason(exemptionReason);
		return getBuilder();
	}
	
	@NotImplemented
	@Deprecated
	@Override
	public TBuilder setDiscounts(DiscountType type, BigDecimal... discounts) {
		BillyValidator.notNull(type, LOCALIZER.getString("field.discount_type"));
		BillyValidator.notNull(discounts, LOCALIZER.getString("field.discount"));
		BillyValidator.notEmpty(discounts, LOCALIZER.getString("field.discount_type"));

		//TODO
		
		return getBuilder();
	}

	@Override
	protected void validateInstance() throws ValidationException {
		// TODO Auto-generated method stub
		validateValues();
	}
	
	@Deprecated
	protected void validateValues() throws ValidationException {
		//TODO check mandatories
		
		MathContext mc = BillyMathContext.get();
		
		GenericInvoiceEntryEntity e = getTypeInstance();
		BigDecimal unitGross = e.getGrossAmount();
		
//		e.setUnitGrossAmount(unitGross.multiply(e.getQuantity(), mc));
		
		Collection<Tax> taxes = e.getProduct().getTaxes();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected GenericInvoiceEntryEntity getTypeInstance() {
		return (GenericInvoiceEntryEntity) super.getTypeInstance();
	}

}
