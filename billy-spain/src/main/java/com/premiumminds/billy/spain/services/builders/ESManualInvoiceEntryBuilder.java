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
package com.premiumminds.billy.spain.services.builders;

import com.premiumminds.billy.spain.persistence.entities.ESGenericInvoiceEntity;
import java.math.BigDecimal;

import com.premiumminds.billy.spain.services.entities.ESGenericInvoiceEntry;

public interface ESManualInvoiceEntryBuilder<TBuilder extends ESManualInvoiceEntryBuilder<TBuilder, TEntry, TInvoice>,
    TEntry extends ESGenericInvoiceEntry, TInvoice extends ESGenericInvoiceEntity>
    extends ESGenericInvoiceEntryBuilder<TBuilder, TEntry, TInvoice> {

    public TBuilder setUnitTaxAmount(BigDecimal taxAmount);

    public TBuilder setAmount(AmountType type, BigDecimal amount);

    public TBuilder setTaxAmount(BigDecimal taxAmount);
}
