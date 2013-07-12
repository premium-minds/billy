package com.premiumminds.billy.core.test.services.builders;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockContactEntity;

public class TestContactBuilder extends AbstractTest {
	
	private static final String CONTACT_YML = "src/test/resources/Contact.yml";
	
	@Test
	public void doTest() {
		
		MockContactEntity mockContact = (MockContactEntity) createMockEntityFromYaml(MockContactEntity.class, CONTACT_YML);
		
		Mockito.when(getInstance(DAOContact.class).getEntityInstance()).thenReturn(new MockContactEntity());
		
		Contact.Builder builder = getInstance(Contact.Builder.class);
		
		builder.setEmail(mockContact.getEmail()).setFax(mockContact.getFax()).setMobile(mockContact.getMobile()).setName(mockContact.getName()).setTelephone(mockContact.getTelephone()).setWebsite(mockContact.getWebsite());
		
		Contact contact = builder.build();
		
		assert(contact != null);
		assertEquals(mockContact.getName(), contact.getName());
		assertEquals(mockContact.getTelephone(), contact.getTelephone());
		assertEquals(mockContact.getMobile(), contact.getMobile());
		assertEquals(mockContact.getFax(), contact.getFax());
		assertEquals(mockContact.getEmail(), contact.getEmail());
		assertEquals(mockContact.getWebsite(), contact.getWebsite());
	}
}
