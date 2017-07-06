/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.entities.jpa;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.Supplier;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "SIMPLE_INVOICE")
public class JPAPTSimpleInvoiceEntity extends JPAPTInvoiceEntity implements
		PTSimpleInvoiceEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "CLIENT_TYPE")
	protected CLIENTTYPE clientType;

	@Override
	public List<PTInvoiceEntry> getEntries() {
		return super.getEntries();
	}

	@Override
	public CLIENTTYPE getClientType() {
		return clientType;
	}

	@Override
	public void setClientType(CLIENTTYPE type) {
		this.clientType = type;
	}

}
