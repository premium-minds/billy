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
import com.premiumminds.billy.andorra.services.entities.ADReceiptEntry;
import com.premiumminds.billy.andorra.services.entities.ADRegionContext;
import com.premiumminds.billy.andorra.util.Contexts;

public class ADReceiptEntryTestUtil {

    private static final BigDecimal AMOUNT = new BigDecimal(20);
    private static final Currency CURRENCY = Currency.getInstance("EUR");
    private static final BigDecimal QUANTITY = new BigDecimal("1");

    private Injector injector;
    private ADProductTestUtil product;
    private Contexts contexts;
    private ADRegionContext context;

    public ADReceiptEntryTestUtil(Injector injector) {
        this.injector = injector;
        this.product = new ADProductTestUtil(injector);
        this.contexts = new Contexts(injector);
    }

    public ADReceiptEntry.Builder getReceiptEntryBuilder(ADProductEntity product) {
        ADReceiptEntry.Builder receiptEntryBuilder = this.injector.getInstance(ADReceiptEntry.Builder.class);
        this.context = this.contexts.andorra().lasEscaldasEngordany();

        receiptEntryBuilder.clear();
        receiptEntryBuilder.setUnitAmount(AmountType.WITH_TAX, ADReceiptEntryTestUtil.AMOUNT)
						   .setTaxPointDate(new Date()).setDescription(product.getDescription())
						   .setQuantity(ADReceiptEntryTestUtil.QUANTITY).setUnitOfMeasure(product.getUnitOfMeasure())
						   .setProductUID(product.getUID()).setContextUID(this.context.getUID())
						   .setCurrency(ADReceiptEntryTestUtil.CURRENCY);

        return receiptEntryBuilder;
    }

    public ADReceiptEntry.Builder getReceiptEntryBuilder() {
        ADProductEntity newProduct = this.product.getProductEntity();
        return this.getReceiptEntryBuilder(
                (ADProductEntity) this.injector.getInstance(DAOADProduct.class).create(newProduct));
    }
}
