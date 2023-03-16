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

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.andorra.persistence.dao.DAOADProduct;
import com.premiumminds.billy.andorra.persistence.entities.ADProductEntity;
import com.premiumminds.billy.andorra.services.entities.ADInvoiceEntry;
import com.premiumminds.billy.andorra.services.entities.ADInvoiceEntry.ManualBuilder;
import com.premiumminds.billy.andorra.services.entities.ADRegionContext;
import com.premiumminds.billy.andorra.services.entities.ADShippingPoint;
import com.premiumminds.billy.andorra.util.Contexts;

public class ADInvoiceEntryTestUtil {

    private static final BigDecimal AMOUNT = new BigDecimal(20);
    private static final Currency CURRENCY = Currency.getInstance("EUR");
    private static final BigDecimal QUANTITY = new BigDecimal("1");

    private Injector injector;
    private ADProductTestUtil product;
    private Contexts contexts;
    private ADRegionContext context;
    private ADShippingPointTestUtil shippingPoint;

    public ADInvoiceEntryTestUtil(Injector injector) {
        this.injector = injector;
        this.product = new ADProductTestUtil(injector);
        this.contexts = new Contexts(injector);
        this.shippingPoint = new ADShippingPointTestUtil(injector);
    }

    public ADInvoiceEntry.Builder getInvoiceEntryBuilder(ADProductEntity product) {
        ADInvoiceEntry.Builder invoiceEntryBuilder = this.injector.getInstance(ADInvoiceEntry.Builder.class);
        ADShippingPoint.Builder originBuilder = this.shippingPoint.getShippingPointBuilder();
        this.context = this.contexts.andorra().encamp();

        invoiceEntryBuilder.clear();

        invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, ADInvoiceEntryTestUtil.AMOUNT)
                           .setTaxPointDate(new Date()).setDescription(product.getDescription())
                           .setQuantity(ADInvoiceEntryTestUtil.QUANTITY).setUnitOfMeasure(product.getUnitOfMeasure())
                           .setProductUID(product.getUID()).setContextUID(this.context.getUID()).setShippingOrigin(originBuilder)
                           .setCurrency(ADInvoiceEntryTestUtil.CURRENCY);

        return invoiceEntryBuilder;

    }

    private ManualBuilder getManualInvoiceEntryBuilder(ADProductEntity product) {
        ADInvoiceEntry.ManualBuilder invoiceEntryBuilder =
                this.injector.getInstance(ADInvoiceEntry.ManualBuilder.class);
        ADShippingPoint.Builder originBuilder = this.shippingPoint.getShippingPointBuilder();
        this.context = this.contexts.andorra().laMassana();

        invoiceEntryBuilder.clear();

        invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, ADInvoiceEntryTestUtil.AMOUNT)
                           .setTaxPointDate(new Date()).setDescription(product.getDescription())
                           .setQuantity(ADInvoiceEntryTestUtil.QUANTITY).setUnitOfMeasure(product.getUnitOfMeasure())
                           .setProductUID(product.getUID()).setContextUID(this.context.getUID()).setShippingOrigin(originBuilder)
                           .setCurrency(ADInvoiceEntryTestUtil.CURRENCY);

        return invoiceEntryBuilder;
    }

    public ADInvoiceEntry.Builder getInvoiceEntryBuilder() {
        ADProductEntity newProduct = this.product.getProductEntity();
        return this.getInvoiceEntryBuilder(
                (ADProductEntity) this.injector.getInstance(DAOADProduct.class).create(newProduct));
    }

    public ManualBuilder getManualInvoiceEntryBuilder() {
        ADProductEntity newProduct = this.product.getProductEntity();
        return this.getManualInvoiceEntryBuilder(
                (ADProductEntity) this.injector.getInstance(DAOADProduct.class).create(newProduct));
    }

    public ADInvoiceEntry.Builder getInvoiceOtherRegionsEntryBuilder() {
        ADProductEntity product = (ADProductEntity) this.injector.getInstance(DAOADProduct.class)
                                                                 .create(this.product.getOtherRegionProductEntity());
        this.context = this.contexts.andorra().allRegions();

        ADInvoiceEntry.Builder invoiceEntryBuilder = this.injector.getInstance(ADInvoiceEntry.Builder.class);
        ADShippingPoint.Builder originBuilder = this.shippingPoint.getShippingPointBuilder();

        invoiceEntryBuilder.clear();

        invoiceEntryBuilder.setUnitAmount(AmountType.WITHOUT_TAX, ADInvoiceEntryTestUtil.AMOUNT)
                           .setTaxPointDate(new Date()).setDescription(product.getDescription())
                           .setQuantity(ADInvoiceEntryTestUtil.QUANTITY).setUnitOfMeasure(product.getUnitOfMeasure())
                           .setProductUID(product.getUID()).setContextUID(this.context.getUID()).setShippingOrigin(originBuilder)
                           .setCurrency(Currency.getInstance("EUR"));

        return invoiceEntryBuilder;
    }
}
