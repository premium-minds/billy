/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.test.services.builders;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Matchers;

import static org.mockito.Mockito.*;

import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockContextEntity;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntity;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntryEntity;
import com.premiumminds.billy.core.test.fixtures.MockProductEntity;
import com.premiumminds.billy.core.test.fixtures.MockShippingPointEntity;
import com.premiumminds.billy.core.test.fixtures.MockTaxEntity;

public class TestGenericInvoiceEntryBuilder extends AbstractTest {

	@Test
	public void doTest() {
		MockGenericInvoiceEntryEntity mock = this.loadFixture(MockGenericInvoiceEntryEntity.class);
		when(getInstance(DAOGenericInvoiceEntry.class).getEntityInstance()).thenReturn(new MockGenericInvoiceEntryEntity());
		
		GenericInvoiceEntry.Builder builder = getInstance(GenericInvoiceEntry.Builder.class);
		builder
			.setCreditOrDebit(mock.getCreditOrDebit())
			.setDescription(mock.getDescription())
			.addDocumentReferenceUID(mock.getDocumentReferences().get(0).getUID())
			.setProductUID(mock.getProduct().getUID())
			.setQuantity(mock.getQuantity())
			.setShippingCostsAmount(mock.getShippingCostsAmount())
			.addTaxUID(mock.getTaxes().get(0).getUID())
			.setUnitAmount(AmountType.WITH_TAX, mock.getUnitAmountWithTax(), Currency.getInstance("EUR"))
			.setUnitOfMeasure(mock.getUnitOfMeasure())
			.setTaxPointDate(mock.getTaxPointDate());
		
		GenericInvoiceEntry entry = builder.build();
		
		assertTrue(mock.getUnitAmountWithTax().compareTo(entry.getUnitAmountWithTax()) == 0);
		assertTrue(mock.getUnitAmountWithoutTax().compareTo(entry.getUnitAmountWithoutTax()) == 0);
		assertTrue(mock.getUnitDiscountAmount().compareTo(entry.getUnitDiscountAmount()) == 0);
		assertTrue(mock.getUnitTaxAmount().compareTo(entry.getUnitTaxAmount()) == 0);
		assertTrue(mock.getAmountWithTax().compareTo(entry.getAmountWithTax()) == 0);
		assertTrue(mock.getAmountWithoutTax().compareTo(entry.getAmountWithoutTax()) == 0);
		assertTrue(mock.getTaxAmount().compareTo(entry.getTaxAmount()) == 0);
		assertTrue(mock.getDiscountAmount().compareTo(entry.getDiscountAmount()) == 0);
		
		return;
	}
	
	public MockGenericInvoiceEntryEntity loadFixture(Class<MockGenericInvoiceEntryEntity> clazz) {
		MockGenericInvoiceEntryEntity result = new MockGenericInvoiceEntryEntity();
		
		result.creditOrDebit = CreditOrDebit.CREDIT;
		result.currency = Currency.getInstance("EUR");
		result.description = "Description";
		      
		MockGenericInvoiceEntity invoiceRef = new MockGenericInvoiceEntity();
		invoiceRef.uid = new UID("uid_ref");
		when(getInstance(DAOGenericInvoice.class).get(Matchers.any(UID.class))).thenReturn(invoiceRef);
		List<GenericInvoice> references = Arrays.asList(new GenericInvoice[]{invoiceRef});
		result.references = references;

		result.number = 143;
		result.exchangeRateToDocumentCurrency = BigDecimal.ONE;
		result.amountWithoutTax = new BigDecimal("1590.082");
		result.amountWithTax = new BigDecimal("1955.80086");
		result.taxAmount = new BigDecimal("365.71886");
		result.discountAmount = BigDecimal.ZERO; //TODO add support for discounts
		result.quantity = new BigDecimal("46");
		result.shippingCostsAmount = BigDecimal.ZERO; //TODO add support for shipping costs
		result.shippingDestination = new MockShippingPointEntity();
		result.shippingOrigin = new MockShippingPointEntity();
		
		MockTaxEntity tax = new MockTaxEntity();
		tax.uid = new UID("uid_tax");
		tax.code = "VAT";
		tax.context = new MockContextEntity();
		tax.currency = Currency.getInstance("EUR");
		tax.taxRateType = TaxRateType.PERCENTAGE;
		tax.percentageRateValue = new BigDecimal("23"); //mistake
		tax.value = new BigDecimal("23");
		result.taxes = Arrays.asList(new Tax[]{tax});
		when(getInstance(DAOTax.class).get(Matchers.any(UID.class))).thenReturn(tax);
		
		MockProductEntity prod = new MockProductEntity();
		prod.uid = new UID("uid_prod");
		result.product = prod;
		when(getInstance(DAOProduct.class).get(Matchers.any(UID.class))).thenReturn(prod);
		
		result.taxExemptionReason = "exemption reason";
		result.taxPointDate =  new Date();
		result.unitAmountWithoutTax = new BigDecimal("34.567");
		result.unitAmountWithTax = new BigDecimal("42.51741");
		result.unitTaxAmount = new BigDecimal("7.95041");
		result.unitDiscountAmount = BigDecimal.ZERO; //TODO add support for shipping discounts;
		result.unitOfMeasure = "Kg";
		
		return result;
	}
	
}
