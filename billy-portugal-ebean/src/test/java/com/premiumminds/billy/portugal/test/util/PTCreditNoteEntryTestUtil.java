/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.util;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.util.Contexts;

public class PTCreditNoteEntryTestUtil {

    private static final BigDecimal AMOUNT = new BigDecimal(20);
    private static final Currency CURRENCY = Currency.getInstance("EUR");
    private static final BigDecimal QUANTITY = new BigDecimal(1);
    private static final String REASON = "Rotten potatoes";

    private Injector injector;
    private Contexts contexts;
    private PTRegionContext context;

    public PTCreditNoteEntryTestUtil(Injector injector) {
        this.injector = injector;
        this.contexts = new Contexts(injector);
    }

    public PTCreditNoteEntry.Builder getCreditNoteEntryBuilder(PTInvoiceEntity reference) {
        PTCreditNoteEntry.Builder creditNoteEntryBuilder = this.injector.getInstance(PTCreditNoteEntry.Builder.class);

        PTProductEntity newProduct = (PTProductEntity) this.injector.getInstance(DAOPTProduct.class)
                .create(new PTProductTestUtil(this.injector).getProductEntity());

        this.context = this.contexts.portugal().allRegions();

        creditNoteEntryBuilder.setUnitAmount(AmountType.WITH_TAX, PTCreditNoteEntryTestUtil.AMOUNT)
                .setTaxPointDate(new Date()).setDescription(newProduct.getDescription())
                .setQuantity(PTCreditNoteEntryTestUtil.QUANTITY).setUnitOfMeasure(newProduct.getUnitOfMeasure())
                .setProductUID(newProduct.getUID()).setContextUID(this.context.getUID())
                .setReason(PTCreditNoteEntryTestUtil.REASON).setReferenceUID(reference.getUID())
                .setCurrency(Currency.getInstance("EUR"));

        return creditNoteEntryBuilder;
    }

}
