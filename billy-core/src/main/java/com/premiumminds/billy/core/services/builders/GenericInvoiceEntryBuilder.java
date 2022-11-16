/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.services.builders;

import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.util.DiscountType;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public interface GenericInvoiceEntryBuilder<TBuilder extends GenericInvoiceEntryBuilder<TBuilder, TEntry, TInvoice>,
        TEntry extends GenericInvoiceEntry, TInvoice extends GenericInvoice>
        extends Builder<TEntry> {

    enum AmountType {
        WITH_TAX, WITHOUT_TAX
    }

    <T extends ShippingPoint> TBuilder setShippingOrigin(Builder<T> originBuilder);

    <T extends ShippingPoint> TBuilder setShippingDestination(Builder<T> destinationBuilder);

    TBuilder setProductUID(StringID<Product> productUID);

    TBuilder setQuantity(BigDecimal quantity);

    TBuilder setUnitAmount(AmountType type, BigDecimal amount);

    TBuilder setUnitOfMeasure(String unit);

    TBuilder setTaxPointDate(Date date);

    TBuilder addDocumentReferenceUID(StringID<GenericInvoice> referenceUID);

    TBuilder setDescription(String description);

    TBuilder setShippingCostsAmount(BigDecimal amount);

    TBuilder setContextUID(StringID<Context> uidContext);

    TBuilder setTaxExemptionReason(String exemptionReason);

    TBuilder setTaxExemptionCode(String exemptionCode);

    TBuilder setDiscounts(DiscountType type, BigDecimal... discounts);

    TBuilder setAmountType(AmountType type);

    TBuilder setCurrency(Currency currency);

}
