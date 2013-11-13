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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.entities.ESAddressEntity;
import com.premiumminds.billy.spain.persistence.entities.ESApplicationEntity;
import com.premiumminds.billy.spain.persistence.entities.ESContactEntity;
import com.premiumminds.billy.spain.persistence.entities.ESRegionContextEntity;
import com.premiumminds.billy.spain.services.entities.ESAddress;
import com.premiumminds.billy.spain.services.entities.ESApplication;
import com.premiumminds.billy.spain.services.entities.ESBusiness;
import com.premiumminds.billy.spain.services.entities.ESContact;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESBusinessEntity;

public class TestESBusinessBuilder extends ESAbstractTest {

	private static final String	ESBUSINESS_YML	= AbstractTest.YML_CONFIGS_DIR
														+ "ESBusiness.yml";

	@Test
	public void doTest() {
		MockESBusinessEntity mockBusiness = this.createMockEntity(
				MockESBusinessEntity.class,
				TestESBusinessBuilder.ESBUSINESS_YML);

		Mockito.when(this.getInstance(DAOESBusiness.class).getEntityInstance())
				.thenReturn(new MockESBusinessEntity());

		Mockito.when(
				this.getInstance(DAOESRegionContext.class).get(
						Matchers.any(UID.class))).thenReturn(
				(ESRegionContextEntity) mockBusiness.getOperationalContext());

		ESBusiness.Builder builder = this.getInstance(ESBusiness.Builder.class);

		ESContact.Builder mockMainContactBuilder = this
				.getMock(ESContact.Builder.class);
		Mockito.when(mockMainContactBuilder.build()).thenReturn(
				(ESContactEntity) mockBusiness.getMainContact());

		ESApplication.Builder mockApplicationBuilder = this
				.getMock(ESApplication.Builder.class);
		Mockito.when(mockApplicationBuilder.build()).thenReturn(
				(ESApplicationEntity) mockBusiness.getApplications().get(0));

		ESAddress.Builder mockAddressBuilder = this
				.getMock(ESAddress.Builder.class);
		Mockito.when(mockAddressBuilder.build()).thenReturn(
				(ESAddressEntity) mockBusiness.getAddress());

		ESAddress.Builder mockShippingAddressBuilder = this
				.getMock(ESAddress.Builder.class);
		Mockito.when(mockShippingAddressBuilder.build()).thenReturn(
				(ESAddressEntity) mockBusiness.getShippingAddress());

		ESAddress.Builder mockBillingAddressBuilder = this
				.getMock(ESAddress.Builder.class);
		Mockito.when(mockBillingAddressBuilder.build()).thenReturn(
				(ESAddressEntity) mockBusiness.getBillingAddress());

		builder.setFinancialID(mockBusiness.getFinancialID(), ES_COUNTRY_CODE)
				.setName(mockBusiness.getName())
				.setAddress(mockAddressBuilder)
				.setBillingAddress(mockBillingAddressBuilder)
				.setShippingAddress(mockShippingAddressBuilder)
				.addApplication(mockApplicationBuilder)
				.addContact(mockMainContactBuilder, true)
				.setWebsite(mockBusiness.getWebsiteAddress())
				.setOperationalContextUID(
						mockBusiness.getOperationalContext().getUID())
				.setCommercialName(mockBusiness.getCommercialName());

		Business business = builder.build();

		Assert.assertTrue(business != null);

		Assert.assertEquals(mockBusiness.getFinancialID(),
				business.getFinancialID());
		Assert.assertEquals(mockBusiness.getName(), business.getName());
		Assert.assertEquals(mockBusiness.getWebsiteAddress(),
				business.getWebsiteAddress());
		Assert.assertEquals(mockBusiness.getAddress().getNumber(), business
				.getAddress().getNumber());

		Assert.assertTrue(business.getContacts() != null);

		Assert.assertTrue(business.getApplications() != null);
		Assert.assertEquals(mockBusiness.getApplications().size(), business
				.getApplications().size());
	}
}
