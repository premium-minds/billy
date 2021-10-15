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
package com.premiumminds.billy.portugal.services.export.qrcode;

import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import java.math.BigDecimal;
import java.util.function.Function;
import java.util.stream.Stream;

public class TupleContextTypeAmount {
    public final Context context;
    public final String type;
    public final BigDecimal taxAmount;
    public final BigDecimal amountWithoutTaxes;

    TupleContextTypeAmount(
        final Context context, final String type, final BigDecimal taxAmount, final BigDecimal amountWithoutTaxes) {
        this.context = context;
        this.type = type;
        this.taxAmount = taxAmount;
        this.amountWithoutTaxes = amountWithoutTaxes;
    }

    public static Function<Tax, TupleContextTypeAmount> taxToTupleContextTypeAmount(final BigDecimal amountWithoutTaxes) {
        return tax -> {
            Context context = tax.getContext();
            String code = tax.getCode();
            BigDecimal taxValue = tax.getValue();
            final BigDecimal taxAmount;
            switch (tax.getTaxRateType()) {
                case FLAT:
                    taxAmount = taxValue;
                    break;
                case PERCENTAGE:
                    taxAmount = amountWithoutTaxes.multiply(taxValue.divide(new BigDecimal(100)));
                    break;
                case NONE:
                default:
                    taxAmount = BigDecimal.ZERO;
            }
            return new TupleContextTypeAmount(context, code, taxAmount, amountWithoutTaxes);
        };
    }

    public static Function<GenericInvoiceEntry, Stream<TupleContextTypeAmount>> getTupleContextTypeAmountStreamFunction() {
        return entry -> {
            BigDecimal amountWithoutTax = entry.getAmountWithoutTax();
            return entry.getTaxes().stream().map(TupleContextTypeAmount.taxToTupleContextTypeAmount(amountWithoutTax));
        };
    }
}
