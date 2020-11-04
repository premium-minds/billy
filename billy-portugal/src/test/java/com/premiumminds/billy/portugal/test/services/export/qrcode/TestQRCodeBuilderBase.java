/*
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
package com.premiumminds.billy.portugal.test.services.export.qrcode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import org.mockito.Mockito;

public class TestQRCodeBuilderBase {

	protected InvoiceSeriesEntity generateInvoiceSeries(boolean hasSeriesUniqueCode) {
		final InvoiceSeriesEntity invoiceSeries = Mockito.mock(InvoiceSeriesEntity.class);

		final Optional<String> seriesUniqueCode = hasSeriesUniqueCode ? Optional.of("ATCUD123") : Optional.empty();
		Mockito.when(invoiceSeries.getSeriesUniqueCode()).thenReturn(seriesUniqueCode);

		return invoiceSeries;
	}

	protected Customer generateCustomer(final UID customerUID) {
		Customer customer = Mockito.mock(Customer.class);
		Mockito.when(customer.getUID()).thenReturn(customerUID);

		final Address address = Mockito.mock(Address.class);
		Mockito.when(address.getISOCountry()).thenReturn("PT");
		Mockito.when(customer.getBillingAddress()).thenReturn(address);
		Mockito.when(customer.getTaxRegistrationNumber()).thenReturn("246456987");
		return customer;
	}

	protected Collection<Application> generateOneApplication() {
		final Collection<Application> applications = new ArrayList<>();
		final PTApplicationEntity application = Mockito.mock(PTApplicationEntity.class);

		Mockito.when(application.getSoftwareCertificationNumber()).thenReturn(452);
		applications.add(application);
		return applications;
	}

	protected List<GenericInvoiceEntry> generateOneEntryWithOneTax(
		final BigDecimal amountWithoutTax,
		final PTRegionContext context,
		final String taxCode,
		final long taxRateValue,
		final TaxRateType taxRateType)
	{
		final List<GenericInvoiceEntry> entries = new ArrayList<>();

		final GenericInvoiceEntry entry = Mockito.mock(GenericInvoiceEntry.class);
		final Collection<Tax> taxes = new ArrayList<>();
		final Tax tax = Mockito.mock(Tax.class);

		Mockito.when(tax.getContext()).thenReturn(context);
		Mockito.when(tax.getCode()).thenReturn(taxCode);
		Mockito.when(tax.getValue()).thenReturn(BigDecimal.valueOf(taxRateValue));
		Mockito.when(tax.getTaxRateType()).thenReturn(taxRateType);

		taxes.add(tax);

		Mockito.when(entry.getAmountWithoutTax()).thenReturn(amountWithoutTax);
		Mockito.when(entry.getTaxes()).thenReturn(taxes);

		entries.add(entry);
		return entries;
	}

}
