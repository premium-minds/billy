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

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.util.Contexts;

public class PTInvoiceTestUtil {

	protected static final Boolean BILLED = false;
	protected static final Boolean CANCELLED = false;
	protected static final Boolean SELFBILL = false;
	protected static final String SOURCE_ID = "SOURCE";
	protected static final String SERIE = "A";
	protected static final Integer SERIE_NUMBER = 1;
	protected static final int MAX_PRODUCTS = 5;
	protected BigDecimal price = new BigDecimal("0.35");
	protected BigDecimal price2 = new BigDecimal("0.35");

	protected TYPE INVOICE_TYPE;
	protected Injector injector;
	protected PTInvoiceEntryTestUtil invoiceEntry;
	protected PTBusinessTestUtil business;
	protected PTCustomerTestUtil customer;

	public PTInvoiceTestUtil(Injector injector) {
		this.injector = injector;
		this.INVOICE_TYPE = TYPE.FT;
		this.invoiceEntry = new PTInvoiceEntryTestUtil(injector);
		this.business = new PTBusinessTestUtil(injector);
		this.customer = new PTCustomerTestUtil(injector);
	}

	public PTInvoiceEntity getInvoiceEntity() {
		return this.getInvoiceEntity(SourceBilling.P);
	}

	public PTInvoiceEntity getInvoiceEntity(SourceBilling billing) {
		PTInvoiceEntity invoice = (PTInvoiceEntity) this.getInvoiceBuilder(
				business.getBusinessEntity(), billing).build();
		invoice.setType(this.INVOICE_TYPE);

		return invoice;
	}

	public PTInvoice.Builder getInvoiceBuilder(PTBusinessEntity business,
			SourceBilling billing) {

		PTInvoice.Builder invoiceBuilder = this.injector
				.getInstance(PTInvoice.Builder.class);

		DAOPTCustomer daoPTCustomer = this.injector
				.getInstance(DAOPTCustomer.class);

		PTCustomerEntity customerEntity = this.customer.getCustomerEntity();
		UID customerUID = daoPTCustomer.create(customerEntity).getUID();

		invoiceBuilder.setCurrency(Currency.getInstance("EUR"));

		PTInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry
				.getInvoiceEntryBuilder();
		invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, price2);
		invoiceBuilder.addEntry(invoiceEntryBuilder);

		return invoiceBuilder.setBilled(PTInvoiceTestUtil.BILLED)
				.setCancelled(PTInvoiceTestUtil.CANCELLED)
				.setSelfBilled(PTInvoiceTestUtil.SELFBILL).setDate(new Date())
				.setSourceId(PTInvoiceTestUtil.SOURCE_ID)
				.setCreditOrDebit(CreditOrDebit.CREDIT)
				.setCustomerUID(customerUID).setSourceBilling(billing)
				.setBusinessUID(business.getUID());
	}

	public PTInvoiceEntity getDiferentRegionsInvoice() {
		PTInvoice.Builder invoiceBuilder = this.injector
				.getInstance(PTInvoice.Builder.class);

		DAOPTCustomer daoPTCustomer = this.injector
				.getInstance(DAOPTCustomer.class);

		PTCustomerEntity customerEntity = this.customer.getCustomerEntity();
		UID customerUID = daoPTCustomer.create(customerEntity).getUID();

		invoiceBuilder.setCurrency(Currency.getInstance("EUR"));
		
		PTInvoiceEntry.Builder invoiceEntryBuilder2 = this.invoiceEntry
				.getInvoiceOtherRegionsEntryBuilder("PT-20");
		invoiceEntryBuilder2.setUnitAmount(AmountType.WITH_TAX, price);
		invoiceBuilder.addEntry(invoiceEntryBuilder2);
		
		PTInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry
				.getInvoiceOtherRegionsEntryBuilder("PT-30");
		invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, price2);
		invoiceBuilder.addEntry(invoiceEntryBuilder);
		
		PTInvoiceEntry.Builder invoiceEntryBuilder3 = this.invoiceEntry
				.getInvoiceEntryBuilder();
		invoiceEntryBuilder3.setUnitAmount(AmountType.WITH_TAX, price);
		invoiceBuilder.addEntry(invoiceEntryBuilder3);
		
		

		invoiceBuilder.setBilled(PTInvoiceTestUtil.BILLED)
				.setCancelled(PTInvoiceTestUtil.CANCELLED)
				.setSelfBilled(PTInvoiceTestUtil.SELFBILL).setDate(new Date())
				.setSourceId(PTInvoiceTestUtil.SOURCE_ID)
				.setCreditOrDebit(CreditOrDebit.CREDIT)
				.setCustomerUID(customerUID).setSourceBilling(SourceBilling.P)
				.setBusinessUID(business.getBusinessEntity().getUID());
		
		return (PTInvoiceEntity) invoiceBuilder.build();
	}

	public PTInvoiceEntity getManyEntriesInvoice() {
		PTInvoice.Builder invoiceBuilder = this.injector
				.getInstance(PTInvoice.Builder.class);

		DAOPTCustomer daoPTCustomer = this.injector
				.getInstance(DAOPTCustomer.class);

		PTCustomerEntity customerEntity = this.customer.getCustomerEntity();
		UID customerUID = daoPTCustomer.create(customerEntity).getUID();

		invoiceBuilder.setCurrency(Currency.getInstance("EUR"));

		for(int i = 0; i < 9; i++){
			PTInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry
					.getInvoiceEntryBuilder();
			invoiceEntryBuilder.setUnitAmount(AmountType.WITH_TAX, price2);
			invoiceBuilder.addEntry(invoiceEntryBuilder);
		}
		
		invoiceBuilder.setBilled(PTInvoiceTestUtil.BILLED)
				.setCancelled(PTInvoiceTestUtil.CANCELLED)
				.setSelfBilled(PTInvoiceTestUtil.SELFBILL).setDate(new Date())
				.setSourceId(PTInvoiceTestUtil.SOURCE_ID)
				.setCreditOrDebit(CreditOrDebit.CREDIT)
				.setCustomerUID(customerUID).setSourceBilling(SourceBilling.P)
				.setBusinessUID(business.getBusinessEntity().getUID());
		
		return (PTInvoiceEntity) invoiceBuilder.build();
	}

}
