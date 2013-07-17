package com.premiumminds.billy.portugal.test.services.builders;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTAddress;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTAddressEntity;

public class TestPTAddressBuilder extends PTAbstractTest {

	private static final String PTADDRESS_YML = "src/test/resources/PTAddress.yml";

	@Test
	public void doTest() {

		MockPTAddressEntity mockAddress = (MockPTAddressEntity) createMockEntity(
				generateMockEntityConstructor(MockPTAddressEntity.class),
				PTADDRESS_YML);

		Mockito.when(getInstance(DAOPTAddress.class).getEntityInstance())
				.thenReturn(new MockPTAddressEntity());

		PTAddress.Builder builder = getInstance(PTAddress.Builder.class);

		builder.setCity(mockAddress.getCity())
				.setDetails(mockAddress.getDetails())
				.setISOCountry(mockAddress.getISOCountry())
				.setNumber(mockAddress.getNumber())
				.setPostalCode(mockAddress.getPostalCode())
				.setRegion(mockAddress.getRegion())
				.setStreetName(mockAddress.getStreetName());
		
		PTAddress address = builder.build();
		
		assert(address != null);
		assertEquals(mockAddress.getCity(), address.getCity());
		assertEquals(mockAddress.getDetails(), address.getDetails());
		assertEquals(mockAddress.getISOCountry(), address.getISOCountry());
		assertEquals(mockAddress.getNumber(), address.getNumber());
		assertEquals(mockAddress.getPostalCode(), address.getPostalCode());
		assertEquals(mockAddress.getRegion(), address.getRegion());
		assertEquals(mockAddress.getStreetName(), address.getStreetName());
	}
}
