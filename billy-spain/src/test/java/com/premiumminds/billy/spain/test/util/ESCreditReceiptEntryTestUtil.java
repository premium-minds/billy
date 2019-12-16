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
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditReceiptEntry;
import com.premiumminds.billy.spain.services.entities.ESRegionContext;
import com.premiumminds.billy.spain.util.Contexts;

public class ESCreditReceiptEntryTestUtil {

    private static final BigDecimal AMOUNT = new BigDecimal(20);
    private static final Currency CURRENCY = Currency.getInstance("EUR");
    private static final BigDecimal QUANTITY = new BigDecimal(1);
    private static final String REASON = "Rotten potatoes";

    private Injector injector;
    private Contexts contexts;
    private ESRegionContext context;

    public ESCreditReceiptEntryTestUtil(Injector injector) {
        this.injector = injector;
        this.contexts = new Contexts(injector);
    }

    public ESCreditReceiptEntry.Builder getCreditReceiptEntryBuilder(ESReceiptEntity reference) {
        ESCreditReceiptEntry.Builder creditReceiptEntryBuilder =
                this.injector.getInstance(ESCreditReceiptEntry.Builder.class);

        ESProductEntity newProduct = (ESProductEntity) this.injector.getInstance(DAOESProduct.class)
                .create(new ESProductTestUtil(this.injector).getProductEntity());

        this.context = this.contexts.spain().allRegions();

        creditReceiptEntryBuilder.setUnitAmount(AmountType.WITH_TAX, ESCreditReceiptEntryTestUtil.AMOUNT)
                .setTaxPointDate(new Date()).setDescription(newProduct.getDescription())
                .setQuantity(ESCreditReceiptEntryTestUtil.QUANTITY).setUnitOfMeasure(newProduct.getUnitOfMeasure())
                .setProductUID(newProduct.getUID()).setContextUID(this.context.getUID())
                .setReason(ESCreditReceiptEntryTestUtil.REASON).setReferenceUID(reference.getUID())
                .setCurrency(Currency.getInstance("EUR"));

        return creditReceiptEntryBuilder;
    }

}
