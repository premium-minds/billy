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
package com.premiumminds.billy.andorra.services.builders;

import com.premiumminds.billy.andorra.persistence.entities.ADGenericInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADGenericInvoiceEntry;
import java.math.BigDecimal;

public interface ADManualInvoiceEntryBuilder<TBuilder extends ADManualInvoiceEntryBuilder<TBuilder, TEntry, TInvoice>,
    TEntry extends ADGenericInvoiceEntry, TInvoice extends ADGenericInvoiceEntity>
    extends ADGenericInvoiceEntryBuilder<TBuilder, TEntry, TInvoice>
{

    public TBuilder setUnitTaxAmount(BigDecimal taxAmount);

    public TBuilder setAmount(AmountType type, BigDecimal amount);

    public TBuilder setTaxAmount(BigDecimal taxAmount);
}
