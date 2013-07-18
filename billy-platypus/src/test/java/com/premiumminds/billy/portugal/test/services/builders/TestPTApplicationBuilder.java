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

import com.premiumminds.billy.core.persistence.dao.DAOApplication;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.portugal.persistence.entities.PTContactEntity;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTApplicationEntity;

public class TestPTApplicationBuilder extends PTAbstractTest {

	private static final String PTAPPLICATION_YML = "src/test/resources/PTApplication.yml";

	@Test
	public void doTest() {

		MockPTApplicationEntity mockApplication = createMockEntity(
				MockPTApplicationEntity.class, PTAPPLICATION_YML);

		Mockito.when(getInstance(DAOApplication.class).getEntityInstance())
				.thenReturn(new MockPTApplicationEntity());

		Application.Builder builder = getInstance(Application.Builder.class);

		PTContact.Builder mockContactBuilder = this
				.getMock(PTContact.Builder.class);
		Mockito.when(mockContactBuilder.build()).thenReturn(
				Mockito.mock(PTContactEntity.class));

		PTContact.Builder mockMainContactBuilder = this
				.getMock(PTContact.Builder.class);
		Mockito.when(mockMainContactBuilder.build()).thenReturn(
				Mockito.mock(PTContactEntity.class));

		builder.addContact(mockContactBuilder)
				.addContact(mockMainContactBuilder)
				.setDeveloperCompanyName(
						mockApplication.getDeveloperCompanyName())
				.setDeveloperCompanyTaxIdentifier(
						mockApplication.getDeveloperCompanyTaxIdentifier())
				.setMainContact(mockMainContactBuilder)
				.setName(mockApplication.getName())
				.setVersion(mockApplication.getVersion())
				.setWebsiteAddress(mockApplication.getWebsiteAddress());

		Application application = builder.build();

		assert (application != null);
		assertEquals(mockApplication.getName(), application.getName());
		assertEquals(mockApplication.getVersion(), application.getVersion());
		assertEquals(mockApplication.getDeveloperCompanyName(),
				application.getDeveloperCompanyName());
		assertEquals(mockApplication.getDeveloperCompanyTaxIdentifier(),
				application.getDeveloperCompanyTaxIdentifier());
		assertEquals(mockApplication.getWebsiteAddress(),
				application.getWebsiteAddress());
		assert (application.getContacts() != null);
		assert (application.getMainContact() != null);

	}
}
