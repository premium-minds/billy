/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy portugal (PT Pack).
 * 
 * billy portugal (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy portugal (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see
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
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;

public class PTInvoiceTestUtil {

	protected static final Boolean BILLED = false;
	protected static final Boolean CANCELLED = false;
	protected static final Boolean SELFBILL = false;
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
		this.invoiceEntry = new PTInvoiceEntryTestUtil(injector);
		this.business = new PTBusinessTestUtil(injector);
		this.customer = new PTCustomerTestUtil(injector);
	}

	public PTInvoiceTestUtil(Injector injector, TYPE type) {
		this.injector = injector;
		this.INVOICE_TYPE = type;
		this.invoiceEntry = new PTInvoiceEntryTestUtil(injector);
		this.business = new PTBusinessTestUtil(injector);
		this.customer = new PTCustomerTestUtil(injector);
	}

	public PTInvoiceEntity getInvoiceEntity() {
		return this.getInvoiceEntity(PTInvoiceTestUtil.BUSINESS_UID,
				PTInvoiceTestUtil.CUSTOMER_UID,
				Arrays.asList(PTInvoiceTestUtil.PRODUCT_UID));
	}

	public PTInvoiceEntity getInvoiceEntity(String businessUID,
			String customerUID, List<String> productUIDs) {
		return this.getInvoiceEntity(this.INVOICE_TYPE,
				PTInvoiceTestUtil.SERIE, PTInvoiceTestUtil.UID,
				PTInvoiceTestUtil.SERIE_NUMBER,
				PTInvoiceTestUtil.INVOICE_ENTRY_UID, businessUID, customerUID,
				productUIDs);
	}

	public PTInvoiceEntity getInvoiceEntity(TYPE invoiceType, String serie,
			String uid, Integer seriesNumber, String entryUID,
			String businessUID, String customerUID, List<String> productUIDs) {

		PTInvoiceEntity invoice = this.getSimpleInvoiceEntity(invoiceType,
				entryUID, uid, businessUID, customerUID, productUIDs,
				SourceBilling.P);

		String formatedNumber = invoiceType.toString() + " " + serie + "/"
				+ seriesNumber;

		invoice.setSeries(serie);
		invoice.setSeriesNumber(seriesNumber);
		invoice.setNumber(formatedNumber);

		return invoice;
	}

	public PTInvoiceEntity getSimpleInvoiceEntity(TYPE invoiceType,
			String entryUID, String uid, String businessUID,
			String customerUID, List<String> productUIDs, SourceBilling billing) {

		DAOPTBusiness daoPTBusiness = this.injector
				.getInstance(DAOPTBusiness.class);
		DAOPTCustomer daoPTCustomer = this.injector
				.getInstance(DAOPTCustomer.class);

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

		PTInvoiceEntity invoice = (PTInvoiceEntity) this.getInvoiceBuilder(
				businessUID, customerUID, billing, productUIDs).build();
		invoice.setUID(new UID(uid));
		invoice.setType(invoiceType);

		for (GenericInvoiceEntry invoiceEntry : invoice.getEntries()) {
			invoiceEntry.setUID(new UID(new Date().toString()));
			invoiceEntry.getDocumentReferences().add(invoice);
		}

		invoice.setBusiness(businessEntity);

		invoice.setCustomer(customerEntity);

		invoice.setCurrency(Currency.getInstance("EUR"));

		return invoice;
	}

	public PTInvoice.Builder getInvoiceBuilder(String businessUID,
			String customerUID, SourceBilling billing, List<String> productUIDs) {
		PTInvoice.Builder invoiceBuilder = this.injector
				.getInstance(PTInvoice.Builder.class);

		DAOPTBusiness daoPTBusiness = this.injector
				.getInstance(DAOPTBusiness.class);
		DAOPTCustomer daoPTCustomer = this.injector
				.getInstance(DAOPTCustomer.class);

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
		invoiceBuilder.clear();

		for (String productUID : productUIDs) {
			PTInvoiceEntry.Builder invoiceEntryBuilder = this.invoiceEntry
					.getInvoiceEntryBuilder(productUID);
			invoiceBuilder.addEntry(invoiceEntryBuilder);
		}

		return invoiceBuilder.setBilled(PTInvoiceTestUtil.BILLED)
				.setCancelled(PTInvoiceTestUtil.CANCELLED)
				.setSelfBilled(PTInvoiceTestUtil.SELFBILL).setDate(new Date())
				.setSourceId(PTInvoiceTestUtil.SOURCE_ID)
				.setCreditOrDebit(CreditOrDebit.CREDIT)
				.setCustomerUID(new UID(customerUID)).setSourceBilling(billing)
				.setBusinessUID(new UID(businessUID));
	}
}
