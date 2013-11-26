/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.test.services.builders;

import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.spain.persistence.dao.DAOESApplication;
import com.premiumminds.billy.spain.persistence.entities.ESContactEntity;
import com.premiumminds.billy.spain.services.entities.ESApplication;
import com.premiumminds.billy.spain.services.entities.ESContact;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESApplicationEntity;

public class TestESApplicationBuilder extends ESAbstractTest {

	private static final String	ESAPPLICATION_YML	= AbstractTest.YML_CONFIGS_DIR
															+ "ESApplication.yml";

	@Test
	public void doTest() throws MalformedURLException {

		MockESApplicationEntity mockApplication = this.createMockEntity(
				MockESApplicationEntity.class,
				TestESApplicationBuilder.ESAPPLICATION_YML);

		Mockito.when(
				this.getInstance(DAOESApplication.class).getEntityInstance())
				.thenReturn(new MockESApplicationEntity());

		ESApplication.Builder builder = this
				.getInstance(ESApplication.Builder.class);

		ESContact.Builder mockContactBuilder = this
				.getMock(ESContact.Builder.class);
		Mockito.when(mockContactBuilder.build()).thenReturn(
				Mockito.mock(ESContactEntity.class));

		ESContact.Builder mockMainContactBuilder = this
				.getMock(ESContact.Builder.class);
		Mockito.when(mockMainContactBuilder.build()).thenReturn(
				Mockito.mock(ESContactEntity.class));

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

		ESApplication application = builder.build();

		assert (application != null);
		Assert.assertEquals(mockApplication.getName(), application.getName());
		Assert.assertEquals(mockApplication.getVersion(),
				application.getVersion());
		Assert.assertEquals(mockApplication.getDeveloperCompanyName(),
				application.getDeveloperCompanyName());
		Assert.assertEquals(mockApplication.getDeveloperCompanyTaxIdentifier(),
				application.getDeveloperCompanyTaxIdentifier());
		Assert.assertEquals(mockApplication.getWebsiteAddress(),
				application.getWebsiteAddress());
		assert (application.getContacts() != null);
		assert (application.getMainContact() != null);

	}
}
