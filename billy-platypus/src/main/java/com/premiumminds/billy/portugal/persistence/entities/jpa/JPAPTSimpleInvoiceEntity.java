/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.entities.jpa;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.util.PaymentMechanism;

@Entity
@Table(name = Config.TABLE_PREFIX + "SIMPLE_INVOICE")
public class JPAPTSimpleInvoiceEntity extends JPAPTInvoiceEntity implements
		PTSimpleInvoiceEntity {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<PTInvoiceEntry> getEntries() {
		return (List<PTInvoiceEntry>) super.getEntries();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public PaymentMechanism getPaymentMechanism() {
		return (PaymentMechanism) super.getPaymentMechanism();
	}
}
