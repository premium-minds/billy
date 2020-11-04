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

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
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
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestQRCodeBuilderOnlyMadeira extends TestQRCodeBuilderBase{

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
		final BigDecimal taxAmount = BigDecimal.valueOf(39);
		final BigDecimal amountWithTax = BigDecimal.valueOf(139);
		final BigDecimal itemAmount = BigDecimal.valueOf(100);
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
				.withEntries(generateEntries(itemAmount, ptContexts))
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
			Assertions.fail();
		}

		Assertions.assertNotNull(result);
		Assertions.assertEquals(
			"A:511234566*B:999999990*C:PT*D:FT*E:F*F:20201103*G:FT A/2549*H:0*"
				+ "I1:PT-MA*I3:100.00*I4:5.00*I5:100.00*I6:12.00*I7:100.00*I8:22.00*"
				+ "N:39.00*O:139.00*Q:akuE*R:452",
			result);

	}

	private List<GenericInvoiceEntry> generateEntries(final BigDecimal amountWithoutTax, final PTContexts ptContexts) {
		List<GenericInvoiceEntry> result = new ArrayList<>();

		final PTRegionContext madeira = Mockito.mock(PTRegionContext.class);
		Mockito.when(madeira.getUID()).thenReturn(ptContexts.getMadeiraUID());
		Mockito.when(madeira.getRegionCode()).thenReturn("PT-30");

		result.addAll(generateOneEntryWithOneTax(amountWithoutTax, madeira,  "NOR", 22, TaxRateType.PERCENTAGE));
		result.addAll(generateOneEntryWithOneTax(amountWithoutTax, madeira,  "INT", 12, TaxRateType.PERCENTAGE));
		result.addAll(generateOneEntryWithOneTax(amountWithoutTax, madeira,  "RED", 5, TaxRateType.PERCENTAGE));

		return result;
	}

}
