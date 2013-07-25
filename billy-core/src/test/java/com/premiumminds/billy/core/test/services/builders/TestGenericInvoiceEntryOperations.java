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
import java.math.MathContext;
import java.util.Currency;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.entities.ProductEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntity;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntryEntity;
import com.premiumminds.billy.core.util.BillyMathContext;

public class TestGenericInvoiceEntryOperations extends AbstractTest {

	private MathContext mc = BillyMathContext.get();
	private BigDecimal qnt = new BigDecimal("46");
	private static final String INVOICE_YML = "src/test/resources/GenericInvoice.yml";
	private static final String ENTRY_YML = "src/test/resources/GenericInvoiceEntry.yml";
	private BigDecimal tax = new BigDecimal("0.23");

	@Test
	public void doTest() {
		MockGenericInvoiceEntryEntity mock = this.createMockEntity(
				MockGenericInvoiceEntryEntity.class,
				TestGenericInvoiceEntryOperations.ENTRY_YML);
		mock.setCurrency(Currency.getInstance("EUR"));
		/*
		 * mock.unitAmountWithoutTax = (new BigDecimal("1")).divide(new
		 * BigDecimal("3"), mc); mock.unitTaxAmount =
		 * mock.unitAmountWithoutTax.multiply(tax, mc); mock.unitAmountWithTax =
		 * mock.unitAmountWithoutTax.add(mock.unitTaxAmount, mc);
		 * mock.amountWithoutTax = mock.unitAmountWithoutTax.multiply(qnt, mc);
		 * mock.amountWithTax = mock.unitAmountWithTax.multiply(qnt, mc);
		 * mock.taxAmount = mock.unitTaxAmount.multiply(qnt, mc);
		 */

		Mockito.when(
				this.getInstance(DAOGenericInvoiceEntry.class)
						.getEntityInstance()).thenReturn(
				new MockGenericInvoiceEntryEntity());

		Mockito.when(
				this.getInstance(DAOContext.class).isSubContext(
						Matchers.any(Context.class),
						Matchers.any(Context.class))).thenReturn(true);

		MockGenericInvoiceEntity invoiceMock = this.createMockEntity(
				MockGenericInvoiceEntity.class,
				TestGenericInvoiceEntryOperations.INVOICE_YML);
		invoiceMock.setCurrency(Currency.getInstance("EUR"));

		mock.getDocumentReferences().add(invoiceMock);

		Mockito.when(
				this.getInstance(DAOGenericInvoice.class).getEntityInstance())
				.thenReturn(invoiceMock);
		Mockito.when(
				this.getInstance(DAOContext.class).isSubContext(
						Matchers.any(Context.class),
						Matchers.any(Context.class))).thenReturn(true);
		Mockito.when(
				this.getInstance(DAOGenericInvoice.class).get(
						Matchers.any(UID.class))).thenReturn(invoiceMock);
		Mockito.when(
				this.getInstance(DAOProduct.class).get(Matchers.any(UID.class)))
				.thenReturn((ProductEntity) mock.getProduct());

		GenericInvoiceEntry.Builder builder = this
				.getInstance(GenericInvoiceEntry.Builder.class);

		builder.setCreditOrDebit(mock.getCreditOrDebit())
				.setDescription(mock.getDescription())
				.addDocumentReferenceUID(
						mock.getDocumentReferences().get(0).getUID())
				.setQuantity(mock.getQuantity())
				.setShippingCostsAmount(mock.getShippingCostsAmount())
				.setUnitAmount(AmountType.WITHOUT_TAX,
						mock.getUnitAmountWithoutTax(),
						Currency.getInstance("EUR"))
				.setUnitOfMeasure(mock.getUnitOfMeasure())
				.setProductUID(mock.getProduct().getUID())
				.setTaxPointDate(mock.getTaxPointDate());

		GenericInvoiceEntry entry = builder.build();

		Assert.assertTrue(entry
				.getAmountWithoutTax()
				.setScale(7, this.mc.getRoundingMode())
				.compareTo(
						mock.getAmountWithoutTax().setScale(7,
								this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(entry.getAmountWithoutTax().compareTo(
				mock.getUnitAmountWithoutTax().multiply(this.qnt, this.mc)) == 0);

		Assert.assertTrue(entry
				.getAmountWithoutTax()
				.setScale(7, this.mc.getRoundingMode())
				.compareTo(
						(mock.getAmountWithTax().subtract(mock.getTaxAmount(),
								this.mc)).setScale(7, this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(entry
				.getAmountWithTax()
				.setScale(7, this.mc.getRoundingMode())
				.compareTo(
						mock.getAmountWithTax().setScale(7,
								this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(entry
				.getAmountWithTax()
				.setScale(7, this.mc.getRoundingMode())
				.compareTo(
						mock.getUnitAmountWithTax().multiply(this.qnt, this.mc)
								.setScale(7, this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(entry
				.getAmountWithTax()
				.setScale(7, this.mc.getRoundingMode())
				.compareTo(
						(mock.getTaxAmount().add(mock.getAmountWithoutTax(),
								this.mc)).setScale(7, this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(entry
				.getTaxAmount()
				.setScale(7, this.mc.getRoundingMode())
				.compareTo(
						mock.getTaxAmount().setScale(7,
								this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(entry
				.getTaxAmount()
				.setScale(7, this.mc.getRoundingMode())
				.compareTo(
						(mock.getAmountWithTax().subtract(
								mock.getAmountWithoutTax(), this.mc)).setScale(
								7, this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(entry
				.getTaxAmount()
				.setScale(7, this.mc.getRoundingMode())
				.compareTo(
						mock.getUnitTaxAmount().multiply(this.qnt, this.mc)
								.setScale(7, this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(entry
				.getUnitAmountWithTax()
				.setScale(7, this.mc.getRoundingMode())
				.compareTo(
						mock.getUnitAmountWithTax().setScale(7,
								this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(entry
				.getUnitAmountWithTax()
				.setScale(7, this.mc.getRoundingMode())
				.compareTo(
						(mock.getUnitAmountWithoutTax().add(
								mock.getUnitAmountWithoutTax().multiply(
										new BigDecimal("0.23"), this.mc),
								this.mc)).setScale(7, this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(entry
				.getUnitAmountWithoutTax()
				.setScale(7, this.mc.getRoundingMode())
				.compareTo(
						mock.getUnitAmountWithoutTax().setScale(7,
								this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(entry
				.getUnitAmountWithoutTax()
				.setScale(7, this.mc.getRoundingMode())
				.compareTo(
						(mock.getUnitAmountWithTax().subtract(
								mock.getUnitTaxAmount(), this.mc)).setScale(7,
								this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(entry
				.getUnitTaxAmount()
				.setScale(7, this.mc.getRoundingMode())
				.compareTo(
						mock.getUnitTaxAmount().setScale(7,
								this.mc.getRoundingMode())) == 0);

		Assert.assertTrue(entry
				.getUnitTaxAmount()
				.setScale(7, this.mc.getRoundingMode())
				.compareTo(
						(mock.getUnitAmountWithTax().subtract(
								mock.getUnitAmountWithoutTax(), this.mc))
								.setScale(7, this.mc.getRoundingMode())) == 0);

		try {
			builder.setQuantity(new BigDecimal("-1"));
			entry = builder.build();
			assert (entry == null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertNotNull(e);

		}

		return;
	}

}
