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
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADProductEntity;
import com.premiumminds.billy.andorra.services.entities.ADCreditNoteEntry;
import com.premiumminds.billy.andorra.services.entities.ADRegionContext;
import com.premiumminds.billy.andorra.util.Contexts;

public class ESCreditNoteEntryTestUtil {

    private static final BigDecimal AMOUNT = new BigDecimal(20);
    private static final Currency CURRENCY = Currency.getInstance("EUR");
    private static final BigDecimal QUANTITY = new BigDecimal(1);
    private static final String REASON = "Rotten potatoes";

    private Injector injector;
    private Contexts contexts;
    private ADRegionContext context;

    public ESCreditNoteEntryTestUtil(Injector injector) {
        this.injector = injector;
        this.contexts = new Contexts(injector);
    }

    public ADCreditNoteEntry.Builder getCreditNoteEntryBuilder(ADInvoiceEntity reference) {
        ADCreditNoteEntry.Builder creditNoteEntryBuilder = this.injector.getInstance(ADCreditNoteEntry.Builder.class);

        ADProductEntity newProduct = (ADProductEntity) this.injector.getInstance(DAOADProduct.class)
                                                                    .create(new ESProductTestUtil(this.injector).getProductEntity());

        this.context = this.contexts.continent().albacete();

        creditNoteEntryBuilder.setUnitAmount(AmountType.WITH_TAX, ESCreditNoteEntryTestUtil.AMOUNT)
                .setTaxPointDate(new Date()).setDescription(newProduct.getDescription())
                .setQuantity(ESCreditNoteEntryTestUtil.QUANTITY).setUnitOfMeasure(newProduct.getUnitOfMeasure())
                .setProductUID(newProduct.getUID()).setContextUID(this.context.getUID())
                .setReason(ESCreditNoteEntryTestUtil.REASON).setReferenceUID(reference.getUID())
                .setCurrency(Currency.getInstance("EUR"));

        return creditNoteEntryBuilder;
    }

}
