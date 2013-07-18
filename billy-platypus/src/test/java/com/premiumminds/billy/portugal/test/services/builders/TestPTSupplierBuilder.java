package com.premiumminds.billy.portugal.test.services.builders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTAddressEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTContactEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTSupplier;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTSupplierEntity;

public class TestPTSupplierBuilder extends PTAbstractTest {

	private static final String PTSUPPLIER_YML = "src/test/resources/PTSupplier.yml";

	@Test
	public void doTest() {
		MockPTSupplierEntity mockSupplier = createMockEntity(
				MockPTSupplierEntity.class, PTSUPPLIER_YML);

		Mockito.when(getInstance(DAOPTSupplier.class).getEntityInstance())
				.thenReturn(new MockPTSupplierEntity());

		PTAddress.Builder mockMainAddressBuilder = this
				.getMock(PTAddress.Builder.class);
		Mockito.when(mockMainAddressBuilder.build()).thenReturn(
				(PTAddressEntity) mockSupplier.getMainAddress());

		PTAddress.Builder mockBillingAddressBuilder = this
				.getMock(PTAddress.Builder.class);
		Mockito.when(mockBillingAddressBuilder.build()).thenReturn(
				(PTAddressEntity) mockSupplier.getBillingAddress());

		PTAddress.Builder mockShippingAddressBuilder = this
				.getMock(PTAddress.Builder.class);
		Mockito.when(mockShippingAddressBuilder.build()).thenReturn(
				(PTAddressEntity) mockSupplier.getShippingAddress());

		PTContact.Builder mockMainContactBuilder = this
				.getMock(PTContact.Builder.class);
		Mockito.when(mockMainContactBuilder.build()).thenReturn(
				(PTContactEntity) mockSupplier.getMainContact());

		PTContact.Builder mockContactBuilder1 = this
				.getMock(PTContact.Builder.class);
		Mockito.when(mockContactBuilder1.build()).thenReturn(
				(PTContactEntity) mockSupplier.getContacts().get(0));

		PTContact.Builder mockContactBuilder2 = this
				.getMock(PTContact.Builder.class);
		Mockito.when(mockContactBuilder2.build()).thenReturn(
				(PTContactEntity) mockSupplier.getContacts().get(1));

		PTSupplier.Builder builder = getInstance(PTSupplier.Builder.class);

		builder.addAddress(mockMainAddressBuilder)
				.addAddress(mockShippingAddressBuilder)
				.addAddress(mockBillingAddressBuilder)
				.addContact(mockMainContactBuilder)
				.setBillingAddress(mockBillingAddressBuilder)
				.setMainAddress(mockMainAddressBuilder)
				.setMainContact(mockMainContactBuilder)
				.setName(mockSupplier.getName())
				.setSelfBillingAgreement(mockSupplier.hasSelfBillingAgreement())
				.setTaxRegistrationNumber(
						mockSupplier.getTaxRegistrationNumber())
				.setShippingAddress(mockShippingAddressBuilder);

		PTSupplier supplier = builder.build();

		assertTrue(supplier != null);
		assertEquals(mockSupplier.getName(), supplier.getName());
		assertEquals(mockSupplier.getTaxRegistrationNumber(),
				supplier.getTaxRegistrationNumber());
		assertEquals(mockSupplier.getMainAddress(), supplier.getMainAddress());
		assertEquals(mockSupplier.getBankAccounts().size(), mockSupplier
				.getBankAccounts().size());
	}

}
