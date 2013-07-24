package com.premiumminds.billy.portugal.test.services.builders;

import java.util.Currency;

import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNoteEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTCreditNoteEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.test.fixtures.MockPTInvoiceEntity;

public class TestPTCreditNoteEntryBuilder extends PTAbstractTest{
	private static final String PT_CREDIT_NOTE_ENTRY_YML = "src/test/resources/PTCreditNoteEntry.yml";
	private static final String PT_INVOICE_YML = "src/test/resources/PTInvoice.yml";
	
	@Test
	public void doTest(){
		MockPTCreditNoteEntryEntity mock = this.createMockEntity(MockPTCreditNoteEntryEntity.class, PT_CREDIT_NOTE_ENTRY_YML);
		
		mock.setCurrency(Currency.getInstance("EUR"));
		
		Mockito.when(this.getInstance(DAOPTCreditNoteEntry.class).getEntityInstance()).thenReturn(new MockPTCreditNoteEntryEntity());
		
		MockPTInvoiceEntity mockInvoiceEntity = this.createMockEntity(MockPTInvoiceEntity.class, PT_INVOICE_YML);
		
		Mockito.when(this.getInstance(DAOPTInvoice.class).get(Matchers.any(UID.class))).thenReturn(mockInvoiceEntity);
		
		Mockito.when(
				this.getInstance(DAOPTProduct.class).get(
						Matchers.any(UID.class))).thenReturn(
				(PTProductEntity) mock.getProduct());

		Mockito.when(
				this.getInstance(DAOPTRegionContext.class).isSubContext(
						Matchers.any(Context.class),
						Matchers.any(Context.class))).thenReturn(true);
		
		mock.setReference((PTInvoice)mockInvoiceEntity);
		
		PTCreditNoteEntry.Builder builder = this.getInstance(PTCreditNoteEntry.Builder.class);
		
		builder
			.setCreditOrDebit(mock.getCreditOrDebit())
			.setDescription(mock.getDescription())
			.setReference(mock.getReference())
			.setReason(mock.getReason())
			.setQuantity(mock.getQuantity())
			.setShippingCostsAmount(mock.getShippingCostsAmount())
			.setUnitAmount(AmountType.WITH_TAX, mock.getUnitAmountWithTax(), Currency.getInstance("EUR"))
			.setUnitOfMeasure(mock.getUnitOfMeasure())
			.setProductUID(mock.getProduct().getUID())
			.setTaxPointDate(mock.getTaxPointDate());
		
		PTCreditNoteEntry entry = builder.build();

		if (entry.getAmountType().compareTo(AmountType.WITHOUT_TAX) == 0) {
			assertTrue(mock.getUnitAmountWithoutTax().compareTo(
					entry.getUnitAmountWithoutTax()) == 0);
		} else {
			assertTrue(mock.getUnitAmountWithTax().compareTo(
					entry.getUnitAmountWithTax()) == 0);
		}

		assertTrue(mock.getUnitDiscountAmount().compareTo(
				entry.getUnitDiscountAmount()) == 0);

		assertTrue(mock.getUnitTaxAmount().compareTo(
				entry.getUnitTaxAmount()) == 0);
		assertTrue(mock.getAmountWithTax().compareTo(
				entry.getAmountWithTax()) == 0);
		assertTrue(mock.getAmountWithoutTax().compareTo(
				entry.getAmountWithoutTax()) == 0);
		assertTrue(mock.getTaxAmount().compareTo(entry.getTaxAmount()) == 0);
		assertTrue(mock.getDiscountAmount().compareTo(
				entry.getDiscountAmount()) == 0);
		
		assertTrue(entry.getReason().equals(mock.getReason()));
		assertTrue(entry.getReference().equals(mock.getReference()));
		
	}		
}
