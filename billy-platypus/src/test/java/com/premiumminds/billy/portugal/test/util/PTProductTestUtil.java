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
package com.premiumminds.billy.portugal.test.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Product.ProductType;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.services.entities.PTProduct;
import com.premiumminds.billy.portugal.util.Taxes;

public class PTProductTestUtil {

	private final String numberCode = "123";
	private final String unitOfMeasure = "Kg";
	private final String productCode = "12345";
	private final String description = "DESCRIPTION";
	private final String uid = "POTATOES";

	private Injector injector;
	private Taxes taxes;
	private PTTaxEntity tax;

	public PTProductTestUtil(Injector injector) {
		this.injector = injector;
		taxes = new Taxes(injector);
	}

	public PTProductEntity getProductEntity() {
		PTProduct.Builder productBuilder = injector
				.getInstance(PTProduct.Builder.class);
		tax = (PTTaxEntity) taxes.continent().normal();

		productBuilder.clear();
		productBuilder.addTaxUID(tax.getUID()).setNumberCode(numberCode)
				.setUnitOfMeasure(unitOfMeasure).setProductCode(productCode)
				.setDescription(description).setType(ProductType.GOODS);

		PTProductEntity product = (PTProductEntity) productBuilder.build();

		product.setUID(new UID(uid));

		return product;
	}
}
