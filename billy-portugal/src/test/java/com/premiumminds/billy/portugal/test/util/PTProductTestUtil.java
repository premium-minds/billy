/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.util;

import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Product.ProductType;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.services.entities.PTProduct;
import com.premiumminds.billy.portugal.util.Taxes;

public class PTProductTestUtil {

    private static final String NUMBER_CODE = "123";
    private static final String UNIT_OF_MEASURE = "Kg";
    private static final String PRODUCT_CODE = "12345";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String GROUP = "FOOD";
    private static final ProductType TYPE = ProductType.GOODS;

    private Injector injector;
    private Taxes taxes;
    private PTTaxEntity tax;

    public PTProductTestUtil(Injector injector) {
        this.injector = injector;
        this.taxes = new Taxes(injector);
        this.tax = (PTTaxEntity) this.taxes.continent().normal();
        this.setExpireOneMonthAhead(this.tax);
    }

    public PTProductEntity getProductEntity(String uid) {
        PTProductEntity product = (PTProductEntity) this.getProductBuilder().build();
        product.setUID(new UID(uid));

        return product;
    }

    public PTProductEntity getProductEntity() {
        return (PTProductEntity) this.getProductBuilder().build();
    }

    public PTProduct.Builder getProductBuilder(String productCode, String unitMesure, String numberCode, String group,
            String description, ProductType type) {
        PTProduct.Builder productBuilder = this.injector.getInstance(PTProduct.Builder.class);

        return productBuilder.addTaxUID(this.tax.getUID()).setNumberCode(numberCode).setUnitOfMeasure(unitMesure)
                .setProductCode(productCode).setDescription(description).setType(type).setProductGroup(group);

    }

    public PTProduct.Builder getProductBuilder() {
        return this.getProductBuilder(PTProductTestUtil.PRODUCT_CODE, PTProductTestUtil.UNIT_OF_MEASURE,
                PTProductTestUtil.NUMBER_CODE, PTProductTestUtil.GROUP, PTProductTestUtil.DESCRIPTION,
                PTProductTestUtil.TYPE);
    }

    public PTProductEntity getProductEntity(String productCode, String unitMesure, String numberCode, String group,
            ProductType type) {
        return (PTProductEntity) this
                .getProductBuilder(productCode, unitMesure, numberCode, group, PTProductTestUtil.DESCRIPTION, type)
                .build();

    }

    public PTProductEntity getOtherRegionProductEntity(String region) {

        PTTaxEntity taxRegion;

        PTProduct.Builder productBuilder = this.injector.getInstance(PTProduct.Builder.class);

        if (region.equals("PT-20")) {
            taxRegion = (PTTaxEntity) this.taxes.azores().normal();
        } else {
            taxRegion = (PTTaxEntity) this.taxes.madeira().normal();
        }

        this.setExpireOneMonthAhead(taxRegion);

        productBuilder.addTaxUID(taxRegion.getUID()).setNumberCode(PTProductTestUtil.NUMBER_CODE)
                .setUnitOfMeasure(PTProductTestUtil.UNIT_OF_MEASURE).setProductCode(PTProductTestUtil.PRODUCT_CODE)
                .setDescription(PTProductTestUtil.DESCRIPTION).setType(PTProductTestUtil.TYPE)
                .setProductGroup(PTProductTestUtil.GROUP);

        return (PTProductEntity) productBuilder.build();
    }

    private PTTaxEntity setExpireOneMonthAhead(PTTaxEntity tax) {
        Date oneMonthFromNow = new Date();
        oneMonthFromNow.setTime(oneMonthFromNow.getTime() + 30 * 24 * 60 * 60 * 1000L);
        tax.setValidTo(oneMonthFromNow);
        return tax;
    }

}
