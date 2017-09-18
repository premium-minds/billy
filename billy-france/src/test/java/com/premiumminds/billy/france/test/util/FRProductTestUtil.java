/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.test.util;

import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Product.ProductType;
import com.premiumminds.billy.france.persistence.entities.FRProductEntity;
import com.premiumminds.billy.france.persistence.entities.FRTaxEntity;
import com.premiumminds.billy.france.services.entities.FRProduct;
import com.premiumminds.billy.france.util.Taxes;

public class FRProductTestUtil {

    private static final String NUMBER_CODE = "123";
    private static final String UNIT_OF_MEASURE = "Kg";
    private static final String PRODUCT_CODE = "12345";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String GROUP = "FOOD";
    private static final ProductType TYPE = ProductType.GOODS;

    private Injector injector;
    private Taxes taxes;
    private FRTaxEntity tax;

    public FRProductTestUtil(Injector injector) {
        this.injector = injector;
        this.taxes = new Taxes(injector);
        this.tax = (FRTaxEntity) this.taxes.continent().normal();
        this.setExpireOneMonthAhead(this.tax);
    }

    public FRProductEntity getProductEntity(String uid) {
        FRProductEntity product = (FRProductEntity) this.getProductBuilder().build();
        product.setUID(new UID(uid));

        return product;
    }

    public FRProductEntity getProductEntity() {
        return (FRProductEntity) this.getProductBuilder().build();
    }

    public FRProduct.Builder getProductBuilder(String productCode, String unitMesure, String numberCode, String group,
            String description, ProductType type) {
        FRProduct.Builder productBuilder = this.injector.getInstance(FRProduct.Builder.class);

        return productBuilder.addTaxUID(this.tax.getUID()).setNumberCode(numberCode).setUnitOfMeasure(unitMesure)
                .setProductCode(productCode).setDescription(description).setType(type).setProductGroup(group);

    }

    public FRProduct.Builder getProductBuilder() {
        return this.getProductBuilder(FRProductTestUtil.PRODUCT_CODE, FRProductTestUtil.UNIT_OF_MEASURE,
                FRProductTestUtil.NUMBER_CODE, FRProductTestUtil.GROUP, FRProductTestUtil.DESCRIPTION,
                FRProductTestUtil.TYPE);
    }

    public FRProductEntity getProductEntity(String productCode, String unitMesure, String numberCode, String group,
            ProductType type) {
        return (FRProductEntity) this
                .getProductBuilder(productCode, unitMesure, numberCode, group, FRProductTestUtil.DESCRIPTION, type)
                .build();

    }

    private FRTaxEntity setExpireOneMonthAhead(FRTaxEntity tax) {
        Date oneMonthFromNow = new Date();
        oneMonthFromNow.setTime(oneMonthFromNow.getTime() + 30 * 24 * 60 * 60 * 1000L);
        tax.setValidTo(oneMonthFromNow);
        return tax;
    }
}
