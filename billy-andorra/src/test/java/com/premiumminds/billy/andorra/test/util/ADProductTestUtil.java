/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.test.util;

import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Product.ProductType;
import com.premiumminds.billy.andorra.persistence.entities.ADProductEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADTaxEntity;
import com.premiumminds.billy.andorra.services.entities.ADProduct;
import com.premiumminds.billy.andorra.util.Taxes;

public class ADProductTestUtil {

    private static final String NUMBER_CODE = "123";
    private static final String UNIT_OF_MEASURE = "Kg";
    private static final String PRODUCT_CODE = "12345";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String GROUP = "FOOD";
    private static final ProductType TYPE = ProductType.GOODS;

    private Injector injector;
    private Taxes taxes;
    private ADTaxEntity tax;

    public ADProductTestUtil(Injector injector) {
        this.injector = injector;
        this.taxes = new Taxes(injector);
        this.tax = (ADTaxEntity) this.taxes.normal();
        this.setExpireOneMonthAhead(this.tax);
    }

    public ADProductEntity getProductEntity(String uid) {
        ADProductEntity product = (ADProductEntity) this.getProductBuilder().build();
        product.setUID(StringID.fromValue(uid));

        return product;
    }

    public ADProductEntity getProductEntity() {
        return (ADProductEntity) this.getProductBuilder().build();
    }

    public ADProduct.Builder getProductBuilder(String productCode, String unitMesure, String numberCode, String group,
                                               String description, ProductType type) {
        ADProduct.Builder productBuilder = this.injector.getInstance(ADProduct.Builder.class);

        return productBuilder.addTaxUID(this.tax.getUID()).setNumberCode(numberCode).setUnitOfMeasure(unitMesure)
                .setProductCode(productCode).setDescription(description).setType(type).setProductGroup(group);

    }

    public ADProduct.Builder getProductBuilder() {
        return this.getProductBuilder(ADProductTestUtil.PRODUCT_CODE, ADProductTestUtil.UNIT_OF_MEASURE,
                                      ADProductTestUtil.NUMBER_CODE, ADProductTestUtil.GROUP, ADProductTestUtil.DESCRIPTION,
                                      ADProductTestUtil.TYPE);
    }

    public ADProductEntity getProductEntity(String productCode, String unitMesure, String numberCode, String group,
                                            ProductType type) {
        return (ADProductEntity) this
                .getProductBuilder(productCode, unitMesure, numberCode, group, ADProductTestUtil.DESCRIPTION, type)
                .build();

    }

    public ADProductEntity getOtherRegionProductEntity() {
        ADProduct.Builder productBuilder = this.injector.getInstance(ADProduct.Builder.class);

        ADTaxEntity taxRegion = (ADTaxEntity) this.taxes.normal();
        this.setExpireOneMonthAhead(taxRegion);

        productBuilder.addTaxUID(taxRegion.getUID()).setNumberCode(ADProductTestUtil.NUMBER_CODE)
                      .setUnitOfMeasure(ADProductTestUtil.UNIT_OF_MEASURE).setProductCode(ADProductTestUtil.PRODUCT_CODE)
                      .setDescription(ADProductTestUtil.DESCRIPTION).setType(ADProductTestUtil.TYPE)
                      .setProductGroup(ADProductTestUtil.GROUP);

        return (ADProductEntity) productBuilder.build();
    }

    private ADTaxEntity setExpireOneMonthAhead(ADTaxEntity tax) {
        Date oneMonthFromNow = new Date();
        oneMonthFromNow.setTime(oneMonthFromNow.getTime() + 30 * 24 * 60 * 60 * 1000L);
        tax.setValidTo(oneMonthFromNow);
        return tax;
    }
}
