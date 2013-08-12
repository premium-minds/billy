/**
 * Copyright (C) 2013 Premium Minds.
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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTPayment;
import com.premiumminds.billy.portugal.util.PaymentMechanism;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "INVOICE")
public class JPAPTInvoiceEntity extends JPAPTGenericInvoiceEntity implements
	PTInvoiceEntity {

	private static final long	serialVersionUID	= 1L;

	@OneToMany(targetEntity = JPAPTPaymentEntity.class, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = Config.TABLE_PREFIX + "PAYMENTS", joinColumns = { @JoinColumn(name = "ID_INVOICE", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "ID_PAYMENT", referencedColumnName = "ID", unique = true)	})
	protected List<PTPayment> payments;
	
	public JPAPTInvoiceEntity() {
		super();
		payments = new ArrayList<PTPayment>();
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<PTInvoiceEntry> getEntries() {
		return (List<PTInvoiceEntry>) super.getEntries();
	}

	@Override
	public PaymentMechanism getPaymentMechanism() {
		return super.getPaymentMechanism();
	}

	@Override
	public List<PTPayment> getPayments() {
		return payments;
	}

}
