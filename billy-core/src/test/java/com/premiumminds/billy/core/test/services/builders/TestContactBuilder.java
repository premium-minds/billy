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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockContactEntity;

public class TestContactBuilder extends AbstractTest {

	private static final String CONTACT_YML = "src/test/resources/Contact.yml";

	@Test
	public void doTest() {

		MockContactEntity mockContact = createMockEntity(
				MockContactEntity.class, CONTACT_YML);

		Mockito.when(getInstance(DAOContact.class).getEntityInstance())
				.thenReturn(new MockContactEntity());

		Contact.Builder builder = getInstance(Contact.Builder.class);

		builder.setEmail(mockContact.getEmail()).setFax(mockContact.getFax())
				.setMobile(mockContact.getMobile())
				.setName(mockContact.getName())
				.setTelephone(mockContact.getTelephone())
				.setWebsite(mockContact.getWebsite());

		Contact contact = builder.build();

		assert (contact != null);
		assertEquals(mockContact.getName(), contact.getName());
		assertEquals(mockContact.getTelephone(), contact.getTelephone());
		assertEquals(mockContact.getMobile(), contact.getMobile());
		assertEquals(mockContact.getFax(), contact.getFax());
		assertEquals(mockContact.getEmail(), contact.getEmail());
		assertEquals(mockContact.getWebsite(), contact.getWebsite());
	}
}
