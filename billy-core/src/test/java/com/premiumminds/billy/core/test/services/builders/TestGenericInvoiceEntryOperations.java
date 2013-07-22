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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Date;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import org.junit.Test;
import org.mockito.Matchers;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.Product.ProductType;
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
import com.premiumminds.billy.core.util.BillyMathContext;

public class TestGenericInvoiceEntryOperations extends AbstractTest {
	private MathContext mc = BillyMathContext.get();
	private BigDecimal qnt = new BigDecimal("46");

	@Test
	public void doTest() {
		MockGenericInvoiceEntryEntity mock = this
				.loadEntryFixture(MockGenericInvoiceEntryEntity.class);
		
		when(getInstance(DAOGenericInvoiceEntry.class).getEntityInstance())
				.thenReturn(new MockGenericInvoiceEntryEntity());
		
		when(getInstance(DAOContext.class).isSubContext(Matchers.any(Context.class), Matchers.any(Context.class))).thenReturn(true);
		
		GenericInvoiceEntry.Builder builder = getInstance(GenericInvoiceEntry.Builder.class);
		builder.setCreditOrDebit(mock.getCreditOrDebit())
		.setDescription(mock.getDescription())
		.addDocumentReferenceUID(
				mock.getDocumentReferences().get(0).getUID())
		.setQuantity(mock.getQuantity())
		.setShippingCostsAmount(mock.getShippingCostsAmount())
		.setUnitAmount(AmountType.WITH_TAX,
				mock.getUnitAmountWithTax(),
				Currency.getInstance("EUR"))
		.setUnitOfMeasure(mock.getUnitOfMeasure())
		.setProductUID(mock.getProduct().getUID())
		.setTaxPointDate(mock.getTaxPointDate());

		GenericInvoiceEntry entry = builder.build();
		
		assertTrue(entry.getAmountWithoutTax().setScale(7, mc.getRoundingMode()).compareTo(
				mock.getAmountWithoutTax().setScale(7, mc.getRoundingMode())) == 0);
		
		assertTrue(entry.getAmountWithoutTax().compareTo(
				mock.getUnitAmountWithoutTax().multiply(qnt, mc)) == 0);
		
		assertTrue(entry.getAmountWithoutTax().setScale(7, mc.getRoundingMode()).compareTo(
				(mock.getAmountWithTax().subtract(mock.getTaxAmount(), mc)).setScale(7, mc.getRoundingMode())) == 0);
		
		assertTrue(entry.getAmountWithTax().setScale(7, mc.getRoundingMode()).compareTo(
				mock.getAmountWithTax().setScale(7, mc.getRoundingMode())
				) == 0);
		
		assertTrue(entry.getAmountWithTax().setScale(7, mc.getRoundingMode()).compareTo(
				mock.getUnitAmountWithTax().multiply(qnt, mc).setScale(7, mc.getRoundingMode())
				) == 0);
		
		assertTrue(entry.getAmountWithTax().setScale(7, mc.getRoundingMode()).compareTo(
					(mock.getTaxAmount().add(mock.getAmountWithoutTax(), mc)).setScale(7, mc.getRoundingMode())
					) == 0);	
		
		assertTrue(entry.getTaxAmount().setScale(7, mc.getRoundingMode()).compareTo(
				mock.getTaxAmount().setScale(7, mc.getRoundingMode())
				) == 0);
		
		assertTrue(entry.getTaxAmount().setScale(7, mc.getRoundingMode()).compareTo(
				(mock.getAmountWithTax().subtract(mock.getAmountWithoutTax(), mc)).setScale(7, mc.getRoundingMode())
						) == 0);
		
		assertTrue(entry.getTaxAmount().setScale(7, mc.getRoundingMode()).compareTo(
				mock.getUnitTaxAmount().multiply(qnt, mc).setScale(7, mc.getRoundingMode())
				) == 0);
		
		assertTrue(entry.getUnitAmountWithTax().setScale(7, mc.getRoundingMode()).compareTo(
				mock.getUnitAmountWithTax().setScale(7, mc.getRoundingMode())
				) == 0);
		
		assertTrue(entry.getUnitAmountWithTax().setScale(7, mc.getRoundingMode()).compareTo(
				(mock.getUnitAmountWithoutTax().add(mock.getUnitAmountWithoutTax().multiply(new BigDecimal("0.23"), mc), mc)).setScale(7, mc.getRoundingMode())
						) == 0);
		
		assertTrue(entry.getUnitAmountWithoutTax().setScale(7, mc.getRoundingMode()).compareTo(
				mock.getUnitAmountWithoutTax().setScale(7, mc.getRoundingMode())
				) == 0);
		
		assertTrue(entry.getUnitAmountWithoutTax().setScale(7, mc.getRoundingMode()).compareTo(
				(mock.getUnitAmountWithTax().subtract(mock.getUnitTaxAmount(), mc)).setScale(7, mc.getRoundingMode())
						) == 0);
		
		assertTrue(entry.getUnitTaxAmount().setScale(7, mc.getRoundingMode()).compareTo(
				mock.getUnitTaxAmount().setScale(7, mc.getRoundingMode())
				) == 0);
		
		assertTrue(entry.getUnitTaxAmount().setScale(7, mc.getRoundingMode()).compareTo(
				(mock.getUnitAmountWithTax().subtract(mock.getUnitAmountWithoutTax(), mc)).setScale(7, mc.getRoundingMode())
				) == 0);	
		
		try{
			builder.setQuantity(new BigDecimal("-1"));
			entry = builder.build();
			assert(entry == null);
			fail();
		}
		catch(IllegalArgumentException e){
			assertNotNull(e);
			
		}
		
		return;
	}
	
	MockContextEntity loadContextFixture(Class<MockContextEntity> clazz){
		MockContextEntity context = new MockContextEntity();
		
		context.setDescription("PT");
		context.setName("PT");
		context.setUID(new UID("Context_uid"));
		return context;
	}
	
	MockTaxEntity loadTaxFixture (Class<MockTaxEntity> clazz){
		MockTaxEntity tax = new MockTaxEntity();
		tax.setUID(new UID("UID_TAX"));
		tax.setCode("VAT");
		tax.setContext(this.loadContextFixture(MockContextEntity.class));
		tax.setCurrency(Currency.getInstance("EUR"));
		tax.setTaxRateType(TaxRateType.PERCENTAGE);
		tax.setPercentageRateValue(new BigDecimal("23"));
		tax.setValue(new BigDecimal("23"));
		tax.setDescription("Tax Description");
		tax.setDesignation("Tax designation");
		tax.setValidTo(Date.valueOf("2013-12-12"));
		tax.setValidFrom(Date.valueOf("2013-01-01"));
		
		return tax;
		
		
	}
	
	MockProductEntity loadProductFixture (Class<MockProductEntity> clazz){
		MockProductEntity product = new MockProductEntity();
		product.setUID(new UID("UID_Prod"));
		product.setProductCode("Product Code");
		product.setProductGroup("Product Group");
		product.setDescription("Description");
		product.setType(ProductType.GOODS);
		product.setCommodityCode("Commodity code");
		product.setNumberCode("Number Code");
		product.setValuationMethod("Validation Method");
		product.setUnitOfMeasure("Kg");
		product.taxes.add(loadTaxFixture(MockTaxEntity.class));
		
		return product;
	}

	public MockGenericInvoiceEntryEntity loadEntryFixture(
			Class<MockGenericInvoiceEntryEntity> clazz) {
		MockGenericInvoiceEntryEntity result = new MockGenericInvoiceEntryEntity();

		result.creditOrDebit = CreditOrDebit.CREDIT;
		result.currency = Currency.getInstance("EUR");
		result.description = "Description";

		MockGenericInvoiceEntity invoiceRef = new MockGenericInvoiceEntity();
		invoiceRef.uid = new UID("uid_ref");
		when(getInstance(DAOGenericInvoice.class).get(Matchers.any(UID.class)))
				.thenReturn(invoiceRef);
		List<GenericInvoice> references = Arrays
				.asList(new GenericInvoice[] { invoiceRef });
		result.references = references;

		result.number = 143;
		result.exchangeRateToDocumentCurrency = BigDecimal.ONE;
		
		//init vars
		BigDecimal taxValue = new BigDecimal("0.23");
		BigDecimal b1 = new BigDecimal("1");
		BigDecimal b2 = new BigDecimal("7");
		BigDecimal prodWithoutTax = b1.divide(b2, mc);
		BigDecimal unitValueTax = prodWithoutTax.multiply(taxValue, mc);
		BigDecimal prodWithTax = prodWithoutTax.add(unitValueTax, mc);
		BigDecimal totalTax = unitValueTax.multiply(qnt, mc);
		BigDecimal totalValueWithTax = prodWithTax.multiply(qnt, mc);
		BigDecimal totalValueWithoutTax = prodWithoutTax.multiply(qnt, mc);
		
		result.amountWithoutTax = totalValueWithoutTax;
		result.amountWithTax = totalValueWithTax;
		result.taxAmount = totalTax;
		result.discountAmount = BigDecimal.ZERO; // TODO add support for
													// discounts
		result.quantity = new BigDecimal("46");
		result.shippingCostsAmount = BigDecimal.ZERO; // TODO add support for
														// shipping costs
		result.shippingDestination = new MockShippingPointEntity();
		result.shippingOrigin = new MockShippingPointEntity();
		
		//MockTaxEntity tax = this.loadTaxFixture(MockTaxEntity.class);
		//result.taxes= Arrays.asList(new Tax[] { tax });
		
		//when(getInstance(DAOTax.class).get(Matchers.any(UID.class)))
			//	.thenReturn(tax);
		
		MockProductEntity prod = this.loadProductFixture(MockProductEntity.class);
		
		result.product = prod;
		when(getInstance(DAOProduct.class).get(Matchers.any(UID.class)))
				.thenReturn(prod);

		result.taxExemptionReason = "exemption reason";
		result.taxPointDate = new java.util.Date();
		result.unitAmountWithoutTax = prodWithoutTax;
		result.unitAmountWithTax = prodWithTax;
		result.unitTaxAmount = unitValueTax;
		result.unitDiscountAmount = BigDecimal.ZERO; // TODO add support for
														// shipping discounts;
		result.unitOfMeasure = "Kg";

		return result;
	}

}
