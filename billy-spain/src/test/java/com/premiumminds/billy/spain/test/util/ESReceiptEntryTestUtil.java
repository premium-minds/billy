/**
 * Copyright (C) 2017 Premium Minds.
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

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.spain.persistence.dao.DAOESProduct;
import com.premiumminds.billy.spain.persistence.entities.ESProductEntity;
import com.premiumminds.billy.spain.services.entities.ESReceiptEntry;
import com.premiumminds.billy.spain.services.entities.ESRegionContext;
import com.premiumminds.billy.spain.util.Contexts;

public class ESReceiptEntryTestUtil {

    private static final BigDecimal AMOUNT = new BigDecimal(20);
    private static final Currency CURRENCY = Currency.getInstance("EUR");
    private static final BigDecimal QUANTITY = new BigDecimal("1");

    private Injector injector;
    private ESProductTestUtil product;
    private Contexts contexts;
    private ESRegionContext context;

    public ESReceiptEntryTestUtil(Injector injector) {
        this.injector = injector;
        this.product = new ESProductTestUtil(injector);
        this.contexts = new Contexts(injector);
    }

    public ESReceiptEntry.Builder getReceiptEntryBuilder(ESProductEntity product) {
        ESReceiptEntry.Builder receiptEntryBuilder = this.injector.getInstance(ESReceiptEntry.Builder.class);
        this.context = this.contexts.spain().allRegions();

        receiptEntryBuilder.clear();
        receiptEntryBuilder.setUnitAmount(AmountType.WITH_TAX, ESReceiptEntryTestUtil.AMOUNT)
                .setTaxPointDate(new Date()).setDescription(product.getDescription())
                .setQuantity(ESReceiptEntryTestUtil.QUANTITY).setUnitOfMeasure(product.getUnitOfMeasure())
                .setProductUID(product.getUID()).setContextUID(this.context.getUID())
                .setCurrency(ESReceiptEntryTestUtil.CURRENCY);

        return receiptEntryBuilder;
    }

    public ESReceiptEntry.Builder getReceiptEntryBuilder() {
        ESProductEntity newProduct = this.product.getProductEntity();
        return this.getReceiptEntryBuilder(
                (ESProductEntity) this.injector.getInstance(DAOESProduct.class).create(newProduct));
    }
}
