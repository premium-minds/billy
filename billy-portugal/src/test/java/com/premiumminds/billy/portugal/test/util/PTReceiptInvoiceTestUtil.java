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
package com.premiumminds.billy.portugal.test.util;

import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTReceiptInvoice;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;

public class PTReceiptInvoiceTestUtil {

	protected static final Boolean BILLED = false;
	protected static final Boolean CANCELLED = false;
	protected static final Boolean SELFBILL = false;
	protected static final String SOURCE_ID = "SOURCE";
	protected static final String SERIE = "R";
	protected static final Integer SERIE_NUMBER = 1;
	protected static final int MAX_PRODUCTS = 5;

	protected TYPE INVOICE_TYPE;
	protected Injector injector;
	protected PTInvoiceEntryTestUtil invoiceEntry;
	protected PTBusinessTestUtil business;
	protected PTCustomerTestUtil customer;
	protected PTPaymentTestUtil payment;

	public PTReceiptInvoiceTestUtil(Injector injector) {
		this.injector = injector;
		this.INVOICE_TYPE = TYPE.FS;
		this.invoiceEntry = new PTInvoiceEntryTestUtil(injector);
		this.business = new PTBusinessTestUtil(injector);
		this.customer = new PTCustomerTestUtil(injector);
		this.payment = new PTPaymentTestUtil(injector);
	}

	public PTReceiptInvoiceEntity getReceiptInvoiceEntity() {
		return this.getReceiptInvoiceEntity(SourceBilling.P);
	}

	public PTReceiptInvoiceEntity getReceiptInvoiceEntity(SourceBilling billing) {
		PTReceiptInvoiceEntity invoice = (PTReceiptInvoiceEntity) this
				.getReceiptInvoiceBuilder(billing).build();
		invoice.setType(this.INVOICE_TYPE);

		return invoice;
	}

	public PTReceiptInvoice.Builder getReceiptInvoiceBuilder(
			SourceBilling billing) {
		PTReceiptInvoice.Builder invoiceBuilder = this.injector
				.getInstance(PTReceiptInvoice.Builder.class);

		DAOPTCustomer daoPTCustomer = this.injector
				.getInstance(DAOPTCustomer.class);

		PTBusinessEntity businessEntity = this.business.getBusinessEntity();

		PTCustomerEntity customerEntity = this.customer.getCustomerEntity();
		UID customerUID = daoPTCustomer.create(customerEntity).getUID();

		for (int i = 0; i < PTReceiptInvoiceTestUtil.MAX_PRODUCTS; ++i) {
			PTInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry
					.getInvoiceEntryBuilder();
			invoiceBuilder.addEntry(invoiceEntryBuilder);
		}

		return invoiceBuilder.setBilled(PTInvoiceTestUtil.BILLED)
				.setCancelled(PTInvoiceTestUtil.CANCELLED)
				.setSelfBilled(PTInvoiceTestUtil.SELFBILL).setDate(new Date())
				.setSourceId(PTInvoiceTestUtil.SOURCE_ID)
				.setCreditOrDebit(CreditOrDebit.CREDIT)
				.setCustomerUID(customerUID).setSourceBilling(billing)
				.setBusinessUID(businessEntity.getUID())
				.addPayment(payment.getPaymentBuilder());
	}

}
