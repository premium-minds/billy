/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.builders;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTContact;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTContactEntity;

public class TestPTContactBuilder extends PTAbstractTest {

	private static final String PTCONTACT_YML = "src/test/resources/PTContact.yml";

	@Test
	public void doTest() {
		MockPTContactEntity mockContact = createMockEntity(
				MockPTContactEntity.class, PTCONTACT_YML);

		Mockito.when(getInstance(DAOPTContact.class).getEntityInstance())
				.thenReturn(new MockPTContactEntity());

		PTContact.Builder builder = getInstance(PTContact.Builder.class);

		builder.setEmail(mockContact.getEmail()).setFax(mockContact.getFax())
				.setMobile(mockContact.getMobile())
				.setName(mockContact.getName())
				.setTelephone(mockContact.getTelephone())
				.setWebsite(mockContact.getWebsite());

		PTContact contact = (PTContact) builder.build();

		assert (contact != null);
		assertEquals(mockContact.getEmail(), contact.getEmail());
		assertEquals(mockContact.getFax(), contact.getFax());
		assertEquals(mockContact.getMobile(), contact.getMobile());
		assertEquals(mockContact.getName(), contact.getName());
		assertEquals(mockContact.getTelephone(), contact.getTelephone());
		assertEquals(mockContact.getWebsite(), contact.getWebsite());

	}
}
