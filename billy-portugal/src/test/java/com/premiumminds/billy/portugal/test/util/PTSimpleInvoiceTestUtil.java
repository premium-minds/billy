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

import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice;

public class PTSimpleInvoiceTestUtil extends PTInvoiceTestUtil {

	public PTSimpleInvoiceTestUtil(Injector injector) {
		super(injector, TYPE.FS);
	}

	@Override
	public PTInvoiceEntity getInvoiceEntity() {
		return this.getInvoiceEntity(PTInvoiceTestUtil.BUSINESS_UID,
				PTInvoiceTestUtil.CUSTOMER_UID,
				Arrays.asList(PTInvoiceTestUtil.PRODUCT_UID));
	}

	@Override
	public PTSimpleInvoiceEntity getInvoiceEntity(String businessUID,
			String customerUID, List<String> productUIDs) {
		return this.getInvoiceEntity(this.INVOICE_TYPE,
				PTInvoiceTestUtil.SERIE, PTInvoiceTestUtil.UID,
				PTInvoiceTestUtil.SERIE_NUMBER,
				PTInvoiceTestUtil.INVOICE_ENTRY_UID, businessUID, customerUID,
				productUIDs);
	}

	@Override
	public PTSimpleInvoiceEntity getInvoiceEntity(TYPE invoiceType,
			String serie, String uid, Integer seriesNumber, String entryUID,
			String businessUID, String customerUID, List<String> productUIDs) {

		PTSimpleInvoiceEntity invoice = this.getSimpleInvoiceEntity(
				invoiceType, entryUID, uid, businessUID, customerUID,
				productUIDs, SourceBilling.P);

		String formatedNumber = invoiceType.toString() + " " + serie + "/"
				+ seriesNumber;

		invoice.setSeries(serie);
		invoice.setSeriesNumber(seriesNumber);
		invoice.setNumber(formatedNumber);

		return invoice;
	}

	@Override
	public PTSimpleInvoiceEntity getSimpleInvoiceEntity(TYPE invoiceType,
			String entryUID, String uid, String businessUID,
			String customerUID, List<String> productUIDs, SourceBilling billing) {
		PTSimpleInvoice.Builder invoiceBuilder = this.injector
				.getInstance(PTSimpleInvoice.Builder.class);
		DAOPTBusiness daoPTBusiness = this.injector
				.getInstance(DAOPTBusiness.class);
		DAOPTCustomer daoPTCustomer = this.injector
				.getInstance(DAOPTCustomer.class);

		invoiceBuilder.clear();

		for (String productUID : productUIDs) {
			PTInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry
					.getInvoiceEntryBuilder(productUID);
			invoiceBuilder.addEntry(invoiceEntryBuilder);
		}

		PTBusinessEntity businessEntity = null;
		try {
			businessEntity = (PTBusinessEntity) daoPTBusiness.get(new UID(
					businessUID));
		} catch (NoResultException e) {
		}
		if (businessEntity == null) {
			businessEntity = this.business.getBusinessEntity(businessUID);
			daoPTBusiness.create(businessEntity);
		}

		PTCustomerEntity customerEntity = null;
		try {
			customerEntity = (PTCustomerEntity) daoPTCustomer.get(new UID(
					customerUID));
		} catch (NoResultException e) {
		}

		if (customerEntity == null) {
			customerEntity = this.customer.getCustomerEntity(customerUID);
			daoPTCustomer.create(customerEntity);
		}

		invoiceBuilder.setBilled(PTInvoiceTestUtil.BILLED)
				.setCancelled(PTInvoiceTestUtil.CANCELLED)
				.setSelfBilled(PTInvoiceTestUtil.SELFBILL)
				.setDate(new Date())
				.setSourceId(PTInvoiceTestUtil.SOURCE_ID)
				.setCreditOrDebit(CreditOrDebit.CREDIT)
				.setCustomerUID(new UID(customerUID)).setSourceBilling(billing)
				.setBusinessUID(new UID(businessUID));

		PTSimpleInvoiceEntity invoice = (PTSimpleInvoiceEntity) invoiceBuilder
				.build();
		invoice.setUID(new UID(uid));
		invoice.setType(invoiceType);

		List<PTInvoiceEntry> entries = invoice.getEntries();
		for (int i = 0; i < entries.size(); i++) {
			PTInvoiceEntryEntity invoiceEntry = (PTInvoiceEntryEntity) entries
					.get(i);

			invoiceEntry.setUID(new UID(entryUID));
			invoiceEntry.getDocumentReferences().add(invoice);
		}

		String formatedNumber = invoiceType.toString();
		invoice.setNumber(formatedNumber);
		invoice.setBusiness(businessEntity);

		invoice.setCustomer(customerEntity);

		invoice.setCurrency(Currency.getInstance("EUR"));

		return invoice;
	}

}
