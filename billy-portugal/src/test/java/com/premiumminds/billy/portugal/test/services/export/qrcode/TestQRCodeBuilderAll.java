package com.premiumminds.billy.portugal.test.services.export.qrcode;

import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.export.exceptions.RequiredFieldNotFoundException;
import com.premiumminds.billy.portugal.services.export.qrcode.PTContexts;
import com.premiumminds.billy.portugal.services.export.qrcode.QRCodeBuilder;
import com.premiumminds.billy.portugal.services.export.qrcode.QRCodeData;
import com.premiumminds.billy.portugal.services.export.qrcode.QRCodeData.QRCodeDataBuilder;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class TestQRCodeBuilderAll {

	@Test
	public void test(){
		final Integer seriesNumber = 2549;
		final String businessFinancialID = "511234566";
		final TYPE documentType = TYPE.FT;
		final boolean isCanceled = false;
		final boolean isBilled = true;
		final boolean isSelfBilled = false;
		final Date documentDate = Date.from(Instant.ofEpochSecond(1604402305));
		final String documentNumber = "FT A/"+seriesNumber;
		final String taxCode = "NOR";
		final long taxRateValue = 23;
		final TaxRateType taxRateType = TaxRateType.PERCENTAGE;
		final BigDecimal taxAmount = BigDecimal.valueOf(23);
		final BigDecimal amountWithTax = BigDecimal.valueOf(123);
		final BigDecimal amountWithoutTax = BigDecimal.valueOf(100);
		final String hash = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKMNOPQRSTUWXYZ";

		final UID portugalUID = Mockito.mock(UID.class);
		final UID continenteUID = Mockito.mock(UID.class);
		final UID azoresUID = Mockito.mock(UID.class);
		final UID madeiraUID = Mockito.mock(UID.class);
		final PTContexts ptContexts = new PTContexts(portugalUID, continenteUID, azoresUID, madeiraUID);

		final UID genericCustomerUID = Mockito.mock(UID.class);

		String result = null;
		try {
			QRCodeData qrCodeData = new QRCodeDataBuilder()
				.withSeriesNumber(seriesNumber)
				.withBusinessFinancialID(businessFinancialID)
				.withDocumentType(documentType)
				.withIsCancelled(isCanceled)
				.withIsBilled(isBilled)
				.withIsSelfBilled(isSelfBilled)
				.withDocumentDate(documentDate)
				.withDocumentNumber(documentNumber)
				.withEntries(generateOneEntryWithOneTax(amountWithoutTax, continenteUID, taxCode, taxRateValue, taxRateType))
				.withTaxAmount(taxAmount)
				.withAmountWithTax(amountWithTax)
				.withHash(hash)
				.withApplication(generateOneApplication())
				.withPTContexts(ptContexts)
				.withGenericCustomerUID(genericCustomerUID)
				.withCustomer(generateCustomer(genericCustomerUID))
				.withInvoiceSeries(generateInvoiceSeries(false))
				.build();

			result = QRCodeBuilder.generateQRCodeString(qrCodeData);
		} catch (RequiredFieldNotFoundException e) {
			Assert.fail();
		}

		Assert.assertNotNull(result);
		Assert.assertEquals(
			"A:511234566*B:999999990*C:PT*D:FT*E:F*F:20201103*G:FT A/2549*H:0*I1:PT*I7:100.00*I8:23.00*N:23.00*O:123.00*Q:akuE*R:452",
			result);



	}

	private InvoiceSeriesEntity generateInvoiceSeries(boolean hasSeriesUniqueCode) {
		final InvoiceSeriesEntity invoiceSeries = Mockito.mock(InvoiceSeriesEntity.class);

		final Optional<String> seriesUniqueCode = hasSeriesUniqueCode ? Optional.of("ATCUD123") : Optional.empty();
		Mockito.when(invoiceSeries.getSeriesUniqueCode()).thenReturn(seriesUniqueCode);

		return invoiceSeries;
	}

	private Customer generateCustomer(final UID customerUID) {
		Customer customer = Mockito.mock(Customer.class);
		Mockito.when(customer.getUID()).thenReturn(customerUID);

		final Address address = Mockito.mock(Address.class);
		Mockito.when(address.getISOCountry()).thenReturn("PT");
		Mockito.when(customer.getBillingAddress()).thenReturn(address);
		Mockito.when(customer.getTaxRegistrationNumber()).thenReturn("246456987");
		return customer;
	}

	private Collection<Application> generateOneApplication() {
		final Collection<Application> applications = new ArrayList<>();
		final PTApplicationEntity application = Mockito.mock(PTApplicationEntity.class);

		Mockito.when(application.getSoftwareCertificationNumber()).thenReturn(452);
		applications.add(application);
		return applications;
	}

	private List<GenericInvoiceEntry> generateOneEntryWithOneTax(
		final BigDecimal amountWithoutTax,
		final UID taxContextUID,
		final String taxCode,
		final long taxRateValue,
		final TaxRateType taxRateType)
	{
		final List<GenericInvoiceEntry> entries = new ArrayList<>();

		final GenericInvoiceEntry entry = Mockito.mock(GenericInvoiceEntry.class);
		final Collection<Tax> taxes = new ArrayList<>();
		final Tax tax = Mockito.mock(Tax.class);

		final PTRegionContext context = Mockito.mock(PTRegionContext.class);
		Mockito.when(context.getUID()).thenReturn(taxContextUID);
		Mockito.when(context.getRegionCode()).thenReturn("PT");

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
