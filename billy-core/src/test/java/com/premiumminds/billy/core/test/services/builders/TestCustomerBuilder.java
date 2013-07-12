package com.premiumminds.billy.core.test.services.builders;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockContactEntity;
import com.premiumminds.billy.core.test.fixtures.MockCustomerEntity;


public class TestCustomerBuilder extends AbstractTest {
	
	private static final String CUSTOMER_YML = "src/test/resources/Customer.yml";

	@Test
	public void doTest() {
		MockCustomerEntity mockCustomer = (MockCustomerEntity) createMockEntityFromYaml(MockCustomerEntity.class, CUSTOMER_YML);
		
		DAOCustomer mockDaoCustomer = this.getMock(DAOCustomer.class);
		DAOContact mockDaoContact = this.getMock(DAOContact.class);
		
		Mockito.when(mockDaoCustomer.getEntityInstance()).thenReturn(new MockCustomerEntity());
		
		MockContactEntity contactRef = new MockContactEntity();
		contactRef.uid = new UID("uid_ref");
		Mockito.when(getInstance(DAOContact.class).get(Matchers.any(UID.class))).thenReturn(contactRef);
		
		Customer.Builder builder = new Customer.Builder(mockDaoCustomer, mockDaoContact);
		
		
		
//		builder.
	}

}
