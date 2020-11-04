/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.export.qrcode;

import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.export.exceptions.RequiredFieldNotFoundException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class QRCodeData {

	private final Integer seriesNumber;
	private final String financialID;
	private final TYPE type;
	private final boolean cancelled;
	private final boolean billed;
	private final boolean selfBilled;
	private final Date date;
	private final String number;
	private final List<GenericInvoiceEntry> entries;
	private final BigDecimal taxAmount;
	private final BigDecimal amountWithTax;
	private final String hash;
	private final Collection<Application> application;
	private final PTContexts ptContexts;
	private final UID genericCustomerUID;
	private final Customer customer;
	private final InvoiceSeriesEntity invoiceSeries;

	protected QRCodeData(QRCodeDataBuilder builder){
		this.seriesNumber = builder.seriesNumber;
		this.financialID = builder.financialID;
		this.type = builder.type;
		this.cancelled = builder.cancelled;
		this.billed = builder.billed;
		this.selfBilled = builder.selfBilled;
		this.date = builder.date;
		this.number = builder.number;
		this.entries = builder.entries;
		this.taxAmount = builder.taxAmount;
		this.amountWithTax = builder.amountWithTax;
		this.hash = builder.hash;
		this.application = builder.application;
		this.ptContexts = builder.ptContexts;
		this.genericCustomerUID = builder.genericCustomerUID;
		this.customer = builder.customer;
		this.invoiceSeries = builder.invoiceSeries;
	}

	public Integer getSeriesNumber() {
		return seriesNumber;
	}

	public String getFinancialID() {
		return financialID;
	}

	public TYPE getType() {
		return type;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public boolean isBilled() {
		return billed;
	}

	public boolean isSelfBilled() {
		return selfBilled;
	}

	public Date getDate() {
		return date;
	}

	public String getNumber() {
		return number;
	}

	public List<GenericInvoiceEntry> getEntries() {
		return entries;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public BigDecimal getAmountWithTax() {
		return amountWithTax;
	}

	public String getHash() {
		return hash;
	}

	public Collection<Application> getApplication() {
		return application;
	}

	public PTContexts getPtContexts() {
		return ptContexts;
	}

	public UID getGenericCustomerUID() {
		return genericCustomerUID;
	}

	public Customer getCustomer() {
		return customer;
	}

	public InvoiceSeriesEntity getInvoiceSeries() {
		return invoiceSeries;
	}

	public static class QRCodeDataBuilder {

		Integer seriesNumber;
		String financialID;
		TYPE type;
		Boolean cancelled;
		Boolean billed;
		Boolean selfBilled;
		Date date;
		String number;
		List<GenericInvoiceEntry> entries;
		BigDecimal taxAmount;
		BigDecimal amountWithTax;
		String hash;
		Collection<Application> application;
		PTContexts ptContexts;
		UID genericCustomerUID;
		Customer customer;
		InvoiceSeriesEntity invoiceSeries;

		public QRCodeDataBuilder withSeriesNumber(final Integer seriesNumber) {
			this.seriesNumber = seriesNumber;
			return this;
		}

		public QRCodeDataBuilder withBusinessFinancialID(final String financialID) {
			this.financialID = financialID;
			return this;
		}

		public QRCodeDataBuilder withDocumentType(final TYPE type) {
			this.type = type;
			return this;
		}

		public QRCodeDataBuilder withIsCancelled(final boolean cancelled) {
			this.cancelled = cancelled;
			return this;
		}

		public QRCodeDataBuilder withIsBilled(final boolean billed) {
			this.billed = billed;
			return this;
		}

		public QRCodeDataBuilder withIsSelfBilled(final boolean selfBilled) {
			this.selfBilled = selfBilled;
			return this;
		}

		public QRCodeDataBuilder withDocumentDate(final Date date) {
			this.date = date;
			return this;
		}

		public QRCodeDataBuilder withDocumentNumber(final String number) {
			this.number = number;
			return this;
		}

		public QRCodeDataBuilder withEntries(final List<GenericInvoiceEntry> entries) {
			this.entries = entries;
			return this;
		}

		public QRCodeDataBuilder withTaxAmount(final BigDecimal taxAmount) {
			this.taxAmount = taxAmount;
			return this;
		}

		public QRCodeDataBuilder withAmountWithTax(final BigDecimal amountWithTax) {
			this.amountWithTax = amountWithTax;
			return this;
		}

		public QRCodeDataBuilder withHash(final String hash) {
			this.hash = hash;
			return this;
		}

		public QRCodeDataBuilder withApplication(final Collection<Application> application) {
			this.application = application;
			return this;
		}

		public QRCodeDataBuilder withPTContexts(final PTContexts ptContexts) {
			this.ptContexts = ptContexts;
			return this;
		}

		public QRCodeDataBuilder withGenericCustomerUID(final UID genericCustomerUID) {
			this.genericCustomerUID = genericCustomerUID;
			return this;
		}

		public QRCodeDataBuilder withCustomer(final Customer customer) {
			this.customer = customer;
			return this;
		}

		public QRCodeDataBuilder withInvoiceSeries(final InvoiceSeriesEntity invoiceSeries) {
			this.invoiceSeries = invoiceSeries;
			return this;
		}

		public QRCodeData build() throws RequiredFieldNotFoundException {
			validateThatAllFieldsArePresent();
			return new QRCodeData(this);
		}

		private void validateThatAllFieldsArePresent() throws RequiredFieldNotFoundException {
			for(Field f : this.getClass().getDeclaredFields()){
				try {
					if(!f.getType().isPrimitive() && f.get(this) == null){
						throw new RequiredFieldNotFoundException(f.getName());
					}
				} catch (IllegalAccessException e) {
					throw new RequiredFieldNotFoundException(f.getName());
				}
			}
		}

	}


}
