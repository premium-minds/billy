/**
 * Copyright (C) 2013 Premium Minds.
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
package com.premiumminds.billy.spain.test.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Product.ProductType;
import com.premiumminds.billy.spain.persistence.entities.ESProductEntity;
import com.premiumminds.billy.spain.persistence.entities.ESTaxEntity;
import com.premiumminds.billy.spain.services.entities.ESProduct;
import com.premiumminds.billy.spain.util.Taxes;

public class ESProductTestUtil {

	private static final String NUMBER_CODE = "123";
	private static final String UNIT_OF_MEASURE = "Kg";
	private static final String PRODUCT_CODE = "12345";
	private static final String DESCRIPTION = "DESCRIPTION";
	private static final String GROUP = "FOOD";
	private static final ProductType TYPE = ProductType.GOODS;

	private Injector injector;
	private Taxes taxes;
	private ESTaxEntity tax;

	public ESProductTestUtil(Injector injector) {
		this.injector = injector;
		this.taxes = new Taxes(injector);
		this.tax = (ESTaxEntity) this.taxes.continent().normal();
	}

	public ESProductEntity getProductEntity(String uid) {
		ESProductEntity product = (ESProductEntity) this.getProductBuilder()
				.build();
		product.setUID(new UID(uid));

		return product;
	}

	public ESProductEntity getProductEntity() {
		return (ESProductEntity) this.getProductBuilder().build();
	}

	public ESProduct.Builder getProductBuilder(String productCode,
			String unitMesure, String numberCode, String group,
			String description, ProductType type) {
		ESProduct.Builder productBuilder = this.injector
				.getInstance(ESProduct.Builder.class);

		return productBuilder.addTaxUID(this.tax.getUID())
				.setNumberCode(numberCode).setUnitOfMeasure(unitMesure)
				.setProductCode(productCode).setDescription(description)
				.setType(type).setProductGroup(group);

	}

	public ESProduct.Builder getProductBuilder() {
		return this.getProductBuilder(ESProductTestUtil.PRODUCT_CODE,
				ESProductTestUtil.UNIT_OF_MEASURE,
				ESProductTestUtil.NUMBER_CODE, ESProductTestUtil.GROUP,
				ESProductTestUtil.DESCRIPTION, ESProductTestUtil.TYPE);
	}

	public ESProductEntity getProductEntity(String productCode,
			String unitMesure, String numberCode, String group, ProductType type) {
		return (ESProductEntity) this.getProductBuilder(productCode,
				unitMesure, numberCode, group, ESProductTestUtil.DESCRIPTION,
				type).build();

	}

	public ESProductEntity getOtherRegionProductEntity() {
		ESProduct.Builder productBuilder = this.injector
				.getInstance(ESProduct.Builder.class);

		ESTaxEntity taxRegion = (ESTaxEntity) this.taxes.canaryIslands().normal();

		productBuilder.addTaxUID(taxRegion.getUID()).setNumberCode(ESProductTestUtil.NUMBER_CODE)
				.setUnitOfMeasure(ESProductTestUtil.UNIT_OF_MEASURE)
				.setProductCode(ESProductTestUtil.PRODUCT_CODE)
				.setDescription(ESProductTestUtil.DESCRIPTION)
				.setType(ESProductTestUtil.TYPE)
				.setProductGroup(ESProductTestUtil.GROUP);

		return (ESProductEntity) productBuilder.build();
	}

}
