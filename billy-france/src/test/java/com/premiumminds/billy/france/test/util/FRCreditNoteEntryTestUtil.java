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
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.persistence.entities.FRProductEntity;
import com.premiumminds.billy.france.services.entities.FRCreditNoteEntry;
import com.premiumminds.billy.france.services.entities.FRRegionContext;
import com.premiumminds.billy.france.util.Contexts;

public class FRCreditNoteEntryTestUtil {

    private static final BigDecimal AMOUNT = new BigDecimal(20);
    private static final Currency CURRENCY = Currency.getInstance("EUR");
    private static final BigDecimal QUANTITY = new BigDecimal(1);
    private static final String REASON = "Rotten potatoes";

    private Injector injector;
    private Contexts contexts;
    private FRRegionContext context;

    public FRCreditNoteEntryTestUtil(Injector injector) {
        this.injector = injector;
        this.contexts = new Contexts(injector);
    }

    public FRCreditNoteEntry.Builder getCreditNoteEntryBuilder(FRInvoiceEntity reference) {
        FRCreditNoteEntry.Builder creditNoteEntryBuilder = this.injector.getInstance(FRCreditNoteEntry.Builder.class);

        FRProductEntity newProduct = (FRProductEntity) this.injector.getInstance(DAOFRProduct.class)
                .create(new FRProductTestUtil(this.injector).getProductEntity());

        this.context = this.contexts.france().allRegions();

        creditNoteEntryBuilder.setUnitAmount(AmountType.WITH_TAX, FRCreditNoteEntryTestUtil.AMOUNT)
                .setTaxPointDate(new Date()).setDescription(newProduct.getDescription())
                .setQuantity(FRCreditNoteEntryTestUtil.QUANTITY).setUnitOfMeasure(newProduct.getUnitOfMeasure())
                .setProductUID(newProduct.getUID()).setContextUID(this.context.getUID())
                .setReason(FRCreditNoteEntryTestUtil.REASON).setReferenceUID(reference.getUID())
                .setCurrency(Currency.getInstance("EUR"));

        return creditNoteEntryBuilder;
    }

}
