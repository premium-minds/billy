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
package com.premiumminds.billy.portugal.test.util;

import java.util.Currency;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice.CLIENTTYPE;

public class PTSimpleInvoiceTestUtil {

	protected static final Boolean BILLED = false;
	protected static final Boolean CANCELLED = false;
	protected static final Boolean SELFBILL = false;
	protected static final String SOURCE_ID = "SOURCE";
	protected static final String SERIE = "A";
	protected static final Integer SERIE_NUMBER = 1;
	protected static final int MAX_PRODUCTS = 5;

	protected TYPE						INVOICE_TYPE;
	protected Injector					injector;
	protected PTInvoiceEntryTestUtil	invoiceEntry;
	protected PTBusinessTestUtil		business;
	protected PTCustomerTestUtil		customer;
	protected PTPaymentTestUtil			payment;

	public PTSimpleInvoiceTestUtil(Injector injector) {
		this.injector = injector;
		this.INVOICE_TYPE = TYPE.FS;
		this.invoiceEntry = new PTInvoiceEntryTestUtil(injector);
		this.business = new PTBusinessTestUtil(injector);
		this.customer = new PTCustomerTestUtil(injector);
		this.payment = new PTPaymentTestUtil(injector);
	}

	public PTSimpleInvoiceEntity getSimpleInvoiceEntity() {
		return this.getSimpleInvoiceEntity(SourceBilling.P);
	}

	public PTSimpleInvoiceEntity getSimpleInvoiceEntity(SourceBilling billing) {
		PTSimpleInvoiceEntity invoice = (PTSimpleInvoiceEntity) this
				.getSimpleInvoiceBuilder(business.getBusinessEntity(), billing, CLIENTTYPE.CUSTOMER)
				.build();
		invoice.setType(this.INVOICE_TYPE);

		return invoice;
	}
	
	public PTSimpleInvoiceEntity getSimpleInvoiceEntity(SourceBilling billing, CLIENTTYPE clientType) {
		PTSimpleInvoiceEntity invoice = (PTSimpleInvoiceEntity) this
				.getSimpleInvoiceBuilder(business.getBusinessEntity(), billing, clientType)
				.build();
		invoice.setType(this.INVOICE_TYPE);

		return invoice;
	}

	public PTSimpleInvoice.Builder getSimpleInvoiceBuilder(
			PTBusinessEntity businessEntity, SourceBilling billing, CLIENTTYPE clientType) {
		PTSimpleInvoice.Builder invoiceBuilder = this.injector
				.getInstance(PTSimpleInvoice.Builder.class);

		DAOPTCustomer daoPTCustomer = this.injector
				.getInstance(DAOPTCustomer.class);

		PTCustomerEntity customerEntity = this.customer.getCustomerEntity();
		UID customerUID = daoPTCustomer.create(customerEntity).getUID();
		for (int i = 0; i < PTSimpleInvoiceTestUtil.MAX_PRODUCTS; ++i) {
			PTInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry
					.getInvoiceEntryBuilder();
			invoiceBuilder.addEntry(invoiceEntryBuilder);
		}

		return invoiceBuilder.setBilled(PTInvoiceTestUtil.BILLED)
				.setCancelled(PTInvoiceTestUtil.CANCELLED)
				.setSelfBilled(PTInvoiceTestUtil.SELFBILL).setDate(new Date())
				.setSourceId(PTInvoiceTestUtil.SOURCE_ID)
				.setCustomerUID(customerUID).setSourceBilling(billing)
				.setBusinessUID(businessEntity.getUID())
				.addPayment(payment.getPaymentBuilder())
				.setClientType(clientType);
	}

}
