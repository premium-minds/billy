/**
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

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.util.DiscountType;

public interface GenericInvoiceEntryBuilder<TBuilder extends GenericInvoiceEntryBuilder<TBuilder, TEntry>, TEntry extends GenericInvoiceEntry>
        extends Builder<TEntry> {

    public static enum AmountType {
        WITH_TAX, WITHOUT_TAX
    }

    public <T extends ShippingPoint> TBuilder setShippingOrigin(Builder<T> originBuilder);

    public <T extends ShippingPoint> TBuilder setShippingDestination(Builder<T> destinationBuilder);

    public TBuilder setProductUID(UID productUID);

    public TBuilder setQuantity(BigDecimal quantity);

    public TBuilder setUnitAmount(AmountType type, BigDecimal amount);

    public TBuilder setUnitOfMeasure(String unit);

    public TBuilder setTaxPointDate(Date date);

    public TBuilder addDocumentReferenceUID(UID referenceUID);

    public TBuilder setDescription(String description);

    // public TBuilder setCreditOrDebit(CreditOrDebit creditOrDebit);

    public TBuilder setShippingCostsAmount(BigDecimal amount);

    public TBuilder setContextUID(UID uidContext);

    public TBuilder setTaxExemptionReason(String exemptionReason);

    public TBuilder setTaxExemptionCode(String exemptionCode);

    public TBuilder setDiscounts(DiscountType type, BigDecimal... discounts);

    public TBuilder setAmountType(AmountType type);

    public TBuilder setCurrency(Currency currency);

}
