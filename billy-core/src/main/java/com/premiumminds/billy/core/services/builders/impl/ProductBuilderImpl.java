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

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.persistence.entities.ProductEntity;
import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.ProductBuilder;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.core.services.entities.Product.ProductType;
import com.premiumminds.billy.core.services.entities.util.EntityFactory;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;

public class ProductBuilderImpl<TBuilder extends ProductBuilderImpl<TBuilder, TProduct>, TProduct extends Product>
extends AbstractBuilder<TBuilder, TProduct>
implements ProductBuilder<TBuilder, TProduct> {

	protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");
	
	protected DAOProduct daoProduct;
	protected DAOTax daoTax;

	
	@SuppressWarnings("unchecked")
	@Inject
	public ProductBuilderImpl(
			DAOProduct daoProduct) {
		super((EntityFactory<? extends TProduct>) daoProduct);
		this.daoProduct = daoProduct;
	}

	@Override
	public TBuilder setProductCode(String code) {
		BillyValidator.mandatory(code, LOCALIZER.getString("field.product_code"));
		getTypeInstance().setProductCode(code);
		return getBuilder();
	}

	@Override
	public TBuilder setProductGroup(String group) {
		getTypeInstance().setProductGroup(group);
		return getBuilder();
	}

	@Override
	public TBuilder setDescription(String description) {
		BillyValidator.mandatory(description, LOCALIZER.getString("field.description"));
		getTypeInstance().setDescription(description);
		return getBuilder();
	}

	@Override
	public TBuilder setType(ProductType type) {
		BillyValidator.mandatory(type, LOCALIZER.getString("field.type"));
		getTypeInstance().setType(type);
		return getBuilder();
	}

	@Override
	public TBuilder setCommodityCode(String code) {
		getTypeInstance().setCommodityCode(code);
		return getBuilder();
	}

	@Override
	public TBuilder setNumberCode(String code) {
		getTypeInstance().setNumberCode(code);
		return getBuilder();
	}

	@Override
	public TBuilder setValuationMethod(String method) {
		getTypeInstance().setValuationMethod(method);
		return getBuilder();
	}

	@Override
	public TBuilder setUnitOfMeasure(String unit) {
		getTypeInstance().setUnitOfMeasure(unit);
		return getBuilder();
	}

	@Override
	public TBuilder addTaxUID(UID taxUID) {
		BillyValidator.notNull(taxUID, LOCALIZER.getString("field.tax"));
		TaxEntity t = daoTax.get(taxUID);
		BillyValidator.found(t, LOCALIZER.getString("field.tax"));
		getTypeInstance().getTaxes().add(t);
		return getBuilder();
	}

	@Override
	protected void validateInstance()
			throws javax.validation.ValidationException {
		Product p = getTypeInstance();
		BillyValidator.mandatory(p.getProductCode(), LOCALIZER.getString("field.product_code"));
		BillyValidator.mandatory(p.getDescription(), LOCALIZER.getString("field.description"));
		BillyValidator.mandatory(p.getType(), LOCALIZER.getString("field.type"));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ProductEntity getTypeInstance() {
		return (ProductEntity) super.getTypeInstance();
	}

}
