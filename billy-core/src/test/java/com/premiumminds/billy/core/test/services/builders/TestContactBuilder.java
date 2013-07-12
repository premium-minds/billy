/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-core.
 * 
 * billy-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
	
	private static final String NAME = "name";
	private static final String TELEPHONE = "telephone";
	private static final String MOBILE = "mobile";
	private static final String FAX = "fax";
	private static final String EMAIL = "email";
	private static final String WEBSITE = "website";
	
	@Test
	public void doTest() {
		
		MockContactEntity mockContact = this.loadFixture(ContactEntity.class);
		
		DAOContact mockDaoContact = this.getMock(DAOContact.class);
		
		Mockito.when(mockDaoContact.getEntityInstance()).thenReturn(new MockContactEntity());
		
		Contact.Builder builder = new Contact.Builder(mockDaoContact);
		
		builder.setEmail(mockContact.getEmail()).setFax(mockContact.getFax()).setMobile(mockContact.getMobile()).setName(mockContact.getName()).setTelephone(mockContact.getTelephone()).setWebsite(mockContact.getWebsite());
		
		Contact contact = builder.build();
		
		assert(contact != null);
		assertEquals(NAME, contact.getName());
		assertEquals(TELEPHONE, contact.getTelephone());
		assertEquals(MOBILE, contact.getMobile());
		assertEquals(FAX, contact.getFax());
		assertEquals(EMAIL, contact.getEmail());
		assertEquals(WEBSITE, contact.getWebsite());
	}
	
	public MockContactEntity loadFixture(Class<ContactEntity> clazz) {
		MockContactEntity result = new MockContactEntity();
		
		result.email = EMAIL;
		result.fax = FAX;
		result.mobile = MOBILE;
		result.name = NAME;
		result.telephone = TELEPHONE;
		result.website = WEBSITE;
		
		return result;
	}

}
