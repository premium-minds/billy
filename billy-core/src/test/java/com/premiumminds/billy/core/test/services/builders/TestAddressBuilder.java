package com.premiumminds.billy.core.test.services.builders;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockAddressEntity;

public class TestAddressBuilder extends AbstractTest {

	@Test
	public void doTest() {
		
	}
	
	public MockAddressEntity loadFixture(Class<AddressEntity> clazz) {
		MockAddressEntity result = new MockAddressEntity();
		
		return result;
	}
}
