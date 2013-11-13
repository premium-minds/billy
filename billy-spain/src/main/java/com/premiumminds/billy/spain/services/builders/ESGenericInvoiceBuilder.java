/**
 * Copyright (C) 2013 Premium Minds.
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

import com.premiumminds.billy.core.services.builders.GenericInvoiceBuilder;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoice;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoice.SourceBilling;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoiceEntry;

public interface ESGenericInvoiceBuilder<TBuilder extends ESGenericInvoiceBuilder<TBuilder, TEntry, TDocument>, TEntry extends ESGenericInvoiceEntry, TDocument extends ESGenericInvoice>
	extends GenericInvoiceBuilder<TBuilder, TEntry, TDocument> {

	public TBuilder setCancelled(boolean cancelled);

	public TBuilder setBilled(boolean billed);

	public TBuilder setChangeReason(String reason);

	public TBuilder setSourceBilling(SourceBilling sourceBilling);
}
