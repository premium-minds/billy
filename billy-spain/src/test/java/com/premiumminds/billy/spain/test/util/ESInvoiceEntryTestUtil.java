/*
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
import com.premiumminds.billy.spain.services.entities.ESInvoiceEntry;
import com.premiumminds.billy.spain.services.entities.ESInvoiceEntry.ManualBuilder;
import com.premiumminds.billy.spain.services.entities.ESRegionContext;
import com.premiumminds.billy.spain.services.entities.ESShippingPoint;
import com.premiumminds.billy.spain.util.Contexts;

public class ESInvoiceEntryTestUtil {

    private static final BigDecimal AMOUNT = new BigDecimal(20);
    private static final Currency CURRENCY = Currency.getInstance("EUR");
    private static final BigDecimal QUANTITY = new BigDecimal("1");

    private Injector injector;
    private ESProductTestUtil product;
    private Contexts contexts;
    private ESRegionContext context;
    private ESShippingPointTestUtil shippingPoint;

    public ESInvoiceEntryTestUtil(Injector injector) {
        this.injector = injector;
        this.product = new ESProductTestUtil(injector);
        this.contexts = new Contexts(injector);
        this.shippingPoint = new ESShippingPointTestUtil(injector);
    }

    public ESInvoiceEntry.Builder getInvoiceEntryBuilder(ESProductEntity product) {
        ESInvoiceEntry.Builder invoiceEntryBuilder = this.injector.getInstance(ESInvoiceEntry.Builder.class);
        ESShippingPoint.Builder originBuilder = this.shippingPoint.getShippingPointBuilder();
        this.context = this.contexts.spain().allRegions();

        invoiceEntryBuilder.clear();

        invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, ESInvoiceEntryTestUtil.AMOUNT)
                .setTaxPointDate(new Date()).setDescription(product.getDescription())
                .setQuantity(ESInvoiceEntryTestUtil.QUANTITY).setUnitOfMeasure(product.getUnitOfMeasure())
                .setProductUID(product.getUID()).setContextUID(this.context.getUID()).setShippingOrigin(originBuilder)
                .setCurrency(ESInvoiceEntryTestUtil.CURRENCY);

        return invoiceEntryBuilder;

    }

    private ManualBuilder getManualInvoiceEntryBuilder(ESProductEntity product) {
        ESInvoiceEntry.ManualBuilder invoiceEntryBuilder =
                this.injector.getInstance(ESInvoiceEntry.ManualBuilder.class);
        ESShippingPoint.Builder originBuilder = this.shippingPoint.getShippingPointBuilder();
        this.context = this.contexts.spain().allRegions();

        invoiceEntryBuilder.clear();

        invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, ESInvoiceEntryTestUtil.AMOUNT)
                .setTaxPointDate(new Date()).setDescription(product.getDescription())
                .setQuantity(ESInvoiceEntryTestUtil.QUANTITY).setUnitOfMeasure(product.getUnitOfMeasure())
                .setProductUID(product.getUID()).setContextUID(this.context.getUID()).setShippingOrigin(originBuilder)
                .setCurrency(ESInvoiceEntryTestUtil.CURRENCY);

        return invoiceEntryBuilder;
    }

    public ESInvoiceEntry.Builder getInvoiceEntryBuilder() {
        ESProductEntity newProduct = this.product.getProductEntity();
        return this.getInvoiceEntryBuilder(
                (ESProductEntity) this.injector.getInstance(DAOESProduct.class).create(newProduct));
    }

    public ManualBuilder getManualInvoiceEntryBuilder() {
        ESProductEntity newProduct = this.product.getProductEntity();
        return this.getManualInvoiceEntryBuilder(
                (ESProductEntity) this.injector.getInstance(DAOESProduct.class).create(newProduct));
    }

    public ESInvoiceEntry.Builder getInvoiceOtherRegionsEntryBuilder() {
        ESProductEntity product = (ESProductEntity) this.injector.getInstance(DAOESProduct.class)
                .create(this.product.getOtherRegionProductEntity());
        this.context = this.contexts.canaryIslands().staCruzDeTenerife().getParentContext();

        ESInvoiceEntry.Builder invoiceEntryBuilder = this.injector.getInstance(ESInvoiceEntry.Builder.class);
        ESShippingPoint.Builder originBuilder = this.shippingPoint.getShippingPointBuilder();

        invoiceEntryBuilder.clear();

        invoiceEntryBuilder.setUnitAmount(AmountType.WITHOUT_TAX, ESInvoiceEntryTestUtil.AMOUNT)
                .setTaxPointDate(new Date()).setDescription(product.getDescription())
                .setQuantity(ESInvoiceEntryTestUtil.QUANTITY).setUnitOfMeasure(product.getUnitOfMeasure())
                .setProductUID(product.getUID()).setContextUID(this.context.getUID()).setShippingOrigin(originBuilder)
                .setCurrency(Currency.getInstance("EUR"));

        return invoiceEntryBuilder;
    }
}
