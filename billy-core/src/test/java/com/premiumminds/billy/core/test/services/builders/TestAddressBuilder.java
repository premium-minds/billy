package com.premiumminds.billy.core.test.services.builders;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOAddress;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockAddressEntity;

public class TestAddressBuilder extends AbstractTest {
	
	private static final String ADDRESS_YML = "src/test/resources/Address.yml";
	
	@Test
	public void doTest() {
		MockAddressEntity mockAddress = (MockAddressEntity) createMockEntityFromYaml(MockAddressEntity.class, ADDRESS_YML);
		
		DAOAddress mockDaoAddress = this.getMock(DAOAddress.class);
		
		Mockito.when(mockDaoAddress.getEntityInstance()).thenReturn(new MockAddressEntity());
		
		Address.Builder builder = new Address.Builder(mockDaoAddress);
		
		builder.setBuilding(mockAddress.getBuilding()).setCity(mockAddress.getCity()).setDetails(mockAddress.getDetails()).setISOCountry(mockAddress.getISOCountry()).setNumber(mockAddress.getNumber()).setPostalCode(mockAddress.getPostalCode()).setRegion(mockAddress.getRegion()).setStreetName(mockAddress.getStreetName());
		
		Address address = builder.build();
		
		assert(address != null);
		assertEquals(mockAddress.getStreetName(), address.getStreetName());
		assertEquals(mockAddress.getNumber(), address.getNumber());
		assertEquals(mockAddress.getDetails(), address.getDetails());
		assertEquals(mockAddress.getBuilding(), address.getBuilding());
		assertEquals(mockAddress.getCity(), address.getCity());
		assertEquals(mockAddress.getPostalCode(), address.getPostalCode());
		assertEquals(mockAddress.getRegion(), address.getRegion());
		assertEquals(mockAddress.getISOCountry(), address.getISOCountry());
	}
}
