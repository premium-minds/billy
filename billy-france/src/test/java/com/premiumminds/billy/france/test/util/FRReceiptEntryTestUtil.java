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

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.france.persistence.dao.DAOFRProduct;
import com.premiumminds.billy.france.persistence.entities.FRProductEntity;
import com.premiumminds.billy.france.services.entities.FRReceiptEntry;
import com.premiumminds.billy.france.services.entities.FRRegionContext;
import com.premiumminds.billy.france.util.Contexts;

public class FRReceiptEntryTestUtil {

    private static final BigDecimal AMOUNT = new BigDecimal(20);
    private static final Currency CURRENCY = Currency.getInstance("EUR");
    private static final BigDecimal QUANTITY = new BigDecimal("1");

    private Injector injector;
    private FRProductTestUtil product;
    private Contexts contexts;
    private FRRegionContext context;

    public FRReceiptEntryTestUtil(Injector injector) {
        this.injector = injector;
        this.product = new FRProductTestUtil(injector);
        this.contexts = new Contexts(injector);
    }

    public FRReceiptEntry.Builder getReceiptEntryBuilder(FRProductEntity product) {
        FRReceiptEntry.Builder receiptEntryBuilder = this.injector.getInstance(FRReceiptEntry.Builder.class);
        this.context = this.contexts.france().allRegions();

        receiptEntryBuilder.clear();
        receiptEntryBuilder.setUnitAmount(AmountType.WITH_TAX, FRReceiptEntryTestUtil.AMOUNT)
                .setTaxPointDate(new Date()).setDescription(product.getDescription())
                .setQuantity(FRReceiptEntryTestUtil.QUANTITY).setUnitOfMeasure(product.getUnitOfMeasure())
                .setProductUID(product.getUID()).setContextUID(this.context.getUID())
                .setCurrency(FRReceiptEntryTestUtil.CURRENCY);

        return receiptEntryBuilder;
    }

    public FRReceiptEntry.Builder getReceiptEntryBuilder() {
        FRProductEntity newProduct = this.product.getProductEntity();
        return this.getReceiptEntryBuilder(
                (FRProductEntity) this.injector.getInstance(DAOFRProduct.class).create(newProduct));
    }
}
