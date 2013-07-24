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
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.entities.ProductEntity;
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
	private static final String INVOICE_YML = "src/test/resources/GenericInvoice.yml";
	private static final String ENTRY_YML = "src/test/resources/GenericInvoiceEntry.yml";
	private BigDecimal tax = new BigDecimal("0.23");

	@Test
	public void doTest() {
		MockGenericInvoiceEntryEntity mock= createMockEntity(MockGenericInvoiceEntryEntity.class, ENTRY_YML);
		mock.setCurrency(Currency.getInstance("EUR"));
		/*mock.unitAmountWithoutTax = (new BigDecimal("1")).divide(new BigDecimal("3"), mc);
		mock.unitTaxAmount = mock.unitAmountWithoutTax.multiply(tax, mc);
		mock.unitAmountWithTax = mock.unitAmountWithoutTax.add(mock.unitTaxAmount, mc);
		mock.amountWithoutTax = mock.unitAmountWithoutTax.multiply(qnt, mc);
		mock.amountWithTax = mock.unitAmountWithTax.multiply(qnt, mc);
		mock.taxAmount = mock.unitTaxAmount.multiply(qnt, mc);*/
		
		
		when(getInstance(DAOGenericInvoiceEntry.class).getEntityInstance())
				.thenReturn(new MockGenericInvoiceEntryEntity());
		
		when(getInstance(DAOContext.class).isSubContext(Matchers.any(Context.class), Matchers.any(Context.class))).thenReturn(true);
		
		MockGenericInvoiceEntity invoiceMock = createMockEntity(MockGenericInvoiceEntity.class, INVOICE_YML);
		invoiceMock.setCurrency(Currency.getInstance("EUR"));

		mock.getDocumentReferences().add(invoiceMock);
		
		Mockito.when(getInstance(DAOGenericInvoice.class).getEntityInstance()).thenReturn(invoiceMock);
		Mockito.when(getInstance(DAOContext.class).isSubContext(Matchers.any(Context.class), Matchers.any(Context.class))).thenReturn(true);
		Mockito.when(getInstance(DAOGenericInvoice.class).get(Matchers.any(UID.class))).thenReturn(invoiceMock);
		when(getInstance(DAOProduct.class).get(Matchers.any(UID.class))).thenReturn((ProductEntity)mock.getProduct());
		
		
		GenericInvoiceEntry.Builder builder = getInstance(GenericInvoiceEntry.Builder.class);
		
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

}
