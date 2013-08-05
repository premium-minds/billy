/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy platypus (PT Pack).
 * 
 * billy platypus (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy platypus (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
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
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;

public class PTInvoiceTestUtil {

	protected static final Date DATE = new Date();
	protected static final Boolean BILLED = false;
	protected static final Boolean CANCELLED = false;
	protected static final Boolean SELFBILL = false;
	protected static final String HASH = "HASH";
	protected static final String SOURCE_ID = "SOURCE";
	protected static final String UID = "INVOICE";
	protected static final String SERIE = "A";
	protected static final Integer SERIE_NUMBER = 1;
	protected static final String INVOICE_ENTRY_UID = "INVOICE_ENTRY";
	protected static final String PRODUCT_UID = "PRODUCT_UID";
	protected static final String BUSINESS_UID = "BUSINESS_UID";
	protected static final String CUSTOMER_UID = "CUSTOMER_UID";

	protected TYPE INVOICE_TYPE;
	protected Injector injector;
	protected PTInvoiceEntryTestUtil invoiceEntry;
	protected PTBusinessTestUtil business;
	protected PTCustomerTestUtil customer;

	public PTInvoiceTestUtil(Injector injector) {
		this.injector = injector;
		this.INVOICE_TYPE = TYPE.FT;
		invoiceEntry = new PTInvoiceEntryTestUtil(injector);
		business = new PTBusinessTestUtil(injector);
		customer = new PTCustomerTestUtil(injector);
	}

	public PTInvoiceTestUtil(Injector injector, TYPE type) {
		this.injector = injector;
		this.INVOICE_TYPE = type;
		invoiceEntry = new PTInvoiceEntryTestUtil(injector);
		business = new PTBusinessTestUtil(injector);
		customer = new PTCustomerTestUtil(injector);
	}

	public PTInvoiceEntity getInvoiceEntity() {
		return getInvoiceEntity(BUSINESS_UID, CUSTOMER_UID,
				Arrays.asList(PRODUCT_UID));
	}

	public PTInvoiceEntity getInvoiceEntity(String businessUID,
			String customerUID, List<String> productUIDs) {
		return getInvoiceEntity(INVOICE_TYPE, SERIE, UID, SERIE_NUMBER,
				INVOICE_ENTRY_UID, businessUID, customerUID, productUIDs);
	}

	public PTInvoiceEntity getInvoiceEntity(TYPE invoiceType, String serie,
			String uid, Integer seriesNumber, String entryUID,
			String businessUID, String customerUID, List<String> productUIDs) {

		PTInvoiceEntity invoice = getSimpleInvoiceEntity(invoiceType, entryUID,
				uid, businessUID, customerUID, productUIDs);

		String formatedNumber = invoiceType.toString() + " " + serie + "/"
				+ seriesNumber;

		invoice.setSeries(serie);
		invoice.setSeriesNumber(seriesNumber);
		invoice.setNumber(formatedNumber);

		return invoice;
	}

	public PTInvoiceEntity getSimpleInvoiceEntity(TYPE invoiceType,
			String entryUID, String uid, String businessUID,
			String customerUID, List<String> productUIDs) {
		PTInvoice.Builder invoiceBuilder = injector
				.getInstance(PTInvoice.Builder.class);
		DAOPTBusiness daoPTBusiness = injector.getInstance(DAOPTBusiness.class);
		DAOPTCustomer daoPTCustomer = injector.getInstance(DAOPTCustomer.class);

		invoiceBuilder.clear();

		for (String productUID : productUIDs) {
			PTInvoiceEntry.Builder invoiceEntryBuilder = invoiceEntry
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
			businessEntity = business.getBusinessEntity(businessUID);
			daoPTBusiness.create(businessEntity);
		}

		PTCustomerEntity customerEntity = null;
		try {
			customerEntity = (PTCustomerEntity) daoPTCustomer.get(new UID(
					customerUID));
		} catch (NoResultException e) {
		}

		if (customerEntity == null) {
			customerEntity = customer.getCustomerEntity(customerUID);
			daoPTCustomer.create(customerEntity);
		}

		invoiceBuilder.setBilled(BILLED).setCancelled(CANCELLED)
				.setSelfBilled(SELFBILL).setDate(DATE).setSourceId(SOURCE_ID)
				.setCreditOrDebit(CreditOrDebit.CREDIT)
				.setCustomerUID(new UID(customerUID))
				.setBusinessUID(new UID(businessUID));

		PTInvoiceEntity invoice = (PTInvoiceEntity) invoiceBuilder.build();
		invoice.setUID(new UID(uid));
		invoice.setType(invoiceType);

		for (PTInvoiceEntry invoiceEntry : invoice.getEntries()) {
			invoiceEntry.setUID(new UID(new Date().toString()));
			invoiceEntry.getDocumentReferences().add(invoice);
		}

		invoice.setBusiness(businessEntity);

		invoice.setCustomer(customerEntity);

		invoice.setCurrency(Currency.getInstance("EUR"));

		return invoice;
	}
}
