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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.entities.PTAddressEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTContactEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTBusinessEntity;

public class TestPTBusinessBuilder extends PTAbstractTest {

	private static final String PTBUSINESS_YML = YML_CONFIGS_DIR + "PTBusiness.yml";

	@Test
	public void doTest() {
		MockPTBusinessEntity mockBusiness = this.createMockEntity(
				MockPTBusinessEntity.class,
				TestPTBusinessBuilder.PTBUSINESS_YML);

		Mockito.when(this.getInstance(DAOPTBusiness.class).getEntityInstance())
				.thenReturn(new MockPTBusinessEntity());

		Mockito.when(
				this.getInstance(DAOPTRegionContext.class).get(
						Matchers.any(UID.class))).thenReturn(
				(PTRegionContextEntity) mockBusiness.getOperationalContext());

		PTBusiness.Builder builder = this.getInstance(PTBusiness.Builder.class);

		PTContact.Builder mockMainContactBuilder = this
				.getMock(PTContact.Builder.class);
		Mockito.when(mockMainContactBuilder.build()).thenReturn(
				(PTContactEntity) mockBusiness.getMainContact());

		PTApplication.Builder mockApplicationBuilder = this
				.getMock(PTApplication.Builder.class);
		Mockito.when(mockApplicationBuilder.build()).thenReturn(
				(PTApplicationEntity) mockBusiness.getApplications().get(0));

		PTAddress.Builder mockAddressBuilder = this
				.getMock(PTAddress.Builder.class);
		Mockito.when(mockAddressBuilder.build()).thenReturn(
				(PTAddressEntity) mockBusiness.getAddress());

		PTAddress.Builder mockShippingAddressBuilder = this
				.getMock(PTAddress.Builder.class);
		Mockito.when(mockShippingAddressBuilder.build()).thenReturn(
				(PTAddressEntity) mockBusiness.getShippingAddress());

		PTAddress.Builder mockBillingAddressBuilder = this
				.getMock(PTAddress.Builder.class);
		Mockito.when(mockBillingAddressBuilder.build()).thenReturn(
				(PTAddressEntity) mockBusiness.getBillingAddress());

		builder.setFinancialID(mockBusiness.getFinancialID())
				.setName(mockBusiness.getName())
				.setAddress(mockAddressBuilder)
				.setBillingAddress(mockBillingAddressBuilder)
				.setShippingAddress(mockShippingAddressBuilder)
				.addApplication(mockApplicationBuilder)
				.addContact(mockMainContactBuilder)
				.setWebsite(mockBusiness.getWebsiteAddress())
				.setOperationalContextUID(
						mockBusiness.getOperationalContext().getUID());

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
