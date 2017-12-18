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
import com.premiumminds.billy.france.services.entities.FRInvoiceEntry;
import com.premiumminds.billy.france.services.entities.FRRegionContext;
import com.premiumminds.billy.france.services.entities.FRShippingPoint;
import com.premiumminds.billy.france.services.entities.FRInvoiceEntry.ManualBuilder;
import com.premiumminds.billy.france.util.Contexts;

public class FRInvoiceEntryTestUtil {

    private static final BigDecimal AMOUNT = new BigDecimal(20);
    private static final Currency CURRENCY = Currency.getInstance("EUR");
    private static final BigDecimal QUANTITY = new BigDecimal("1");

    private Injector injector;
    private FRProductTestUtil product;
    private Contexts contexts;
    private FRRegionContext context;
    private FRShippingPointTestUtil shippingPoint;

    public FRInvoiceEntryTestUtil(Injector injector) {
        this.injector = injector;
        this.product = new FRProductTestUtil(injector);
        this.contexts = new Contexts(injector);
        this.shippingPoint = new FRShippingPointTestUtil(injector);
    }

    public FRInvoiceEntry.Builder getInvoiceEntryBuilder(FRProductEntity product) {
        FRInvoiceEntry.Builder invoiceEntryBuilder = this.injector.getInstance(FRInvoiceEntry.Builder.class);
        FRShippingPoint.Builder originBuilder = this.shippingPoint.getShippingPointBuilder();
        this.context = this.contexts.france().allRegions();

        invoiceEntryBuilder.clear();

        invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, FRInvoiceEntryTestUtil.AMOUNT)
                .setTaxPointDate(new Date()).setDescription(product.getDescription())
                .setQuantity(FRInvoiceEntryTestUtil.QUANTITY).setUnitOfMeasure(product.getUnitOfMeasure())
                .setProductUID(product.getUID()).setContextUID(this.context.getUID()).setShippingOrigin(originBuilder)
                .setCurrency(FRInvoiceEntryTestUtil.CURRENCY);

        return invoiceEntryBuilder;

    }

    private ManualBuilder getManualInvoiceEntryBuilder(FRProductEntity product) {
        FRInvoiceEntry.ManualBuilder invoiceEntryBuilder =
                this.injector.getInstance(FRInvoiceEntry.ManualBuilder.class);
        FRShippingPoint.Builder originBuilder = this.shippingPoint.getShippingPointBuilder();
        this.context = this.contexts.france().allRegions();

        invoiceEntryBuilder.clear();

        invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, FRInvoiceEntryTestUtil.AMOUNT)
                .setTaxPointDate(new Date()).setDescription(product.getDescription())
                .setQuantity(FRInvoiceEntryTestUtil.QUANTITY).setUnitOfMeasure(product.getUnitOfMeasure())
                .setProductUID(product.getUID()).setContextUID(this.context.getUID()).setShippingOrigin(originBuilder)
                .setCurrency(FRInvoiceEntryTestUtil.CURRENCY);

        return invoiceEntryBuilder;
    }

    public FRInvoiceEntry.Builder getInvoiceEntryBuilder() {
        FRProductEntity newProduct = this.product.getProductEntity();
        return this.getInvoiceEntryBuilder(
                (FRProductEntity) this.injector.getInstance(DAOFRProduct.class).create(newProduct));
    }

    public ManualBuilder getManualInvoiceEntryBuilder() {
        FRProductEntity newProduct = this.product.getProductEntity();
        return this.getManualInvoiceEntryBuilder(
                (FRProductEntity) this.injector.getInstance(DAOFRProduct.class).create(newProduct));
    }
}
